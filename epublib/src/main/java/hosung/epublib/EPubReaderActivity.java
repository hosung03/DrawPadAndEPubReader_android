package hosung.epublib;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

import java.util.ArrayList;
import java.util.List;

import hosung.epublib.model.Highlight;
import hosung.epublib.smil.AudioElement;
import hosung.epublib.smil.SmilFile;
import hosung.epublib.smil.TextElement;
import hosung.epublib.util.AppUtil;
import hosung.epublib.util.EpubManipulator;
import hosung.epublib.util.FileUtil;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;

public class EPubReaderActivity extends AppCompatActivity implements
        EPubPageFragment.EPubPageFragmentCallback, ConfigBottomSheetDialogFragment.ConfigDialogCallback {
    private static final String TAG = "EPubReaderActivity";
    public static final Bus BUS = new Bus(ThreadEnforcer.ANY);

    public enum EpubSourceType {
        RAW,
        ASSESTS,
        SD_CARD
    }

    private DirectionalViewpager mEPubPageViewPager;
    private EpubSourceType mEpubSourceType;
    private String mEpubFilePath;
    private String mEpubFileName;
    private int mEpubRawId;
    protected Book mBook;
    private String mBookeFilePath;
    private ArrayList<TOCReference> mTocReferences;
    private List<SpineReference> mSpineReferences;
    private List<TextElement> mTextElementList = new ArrayList<>();

    public boolean mIsSmilParsed = false;
    private int mChapterPosition = -1;
    private boolean mIsSmilAvailable;

    protected EPubPageFragmentAdapter mEPubPageFragmentAdapter;
    private int mWebViewScrollPosition;
    private ConfigBottomSheetDialogFragment mConfigBottomSheetDialogFragment;
    private boolean mIsbookOpened =false;

    private ProgressDialog progressDialog = null;

    private DrawerLayout mDrawLayout = null;
    private RecyclerView mTocList = null;
    private int mSelectedChapterPosition;

    protected boolean mIsLoaded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub_reader);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEpubSourceType = (EpubSourceType) getIntent().getExtras().getSerializable("EPubSourceType");
        if (mEpubSourceType.equals(EpubSourceType.RAW)) {
            mEpubRawId = getIntent().getExtras().getInt("EPubRawId");
        } else {
            mEpubFilePath = getIntent().getExtras().getString("EPubFilePath");
        }
        mEpubFileName = FileUtil.getEpubFilename(this, mEpubSourceType, mEpubFilePath, mEpubRawId);
        initBook();

        mDrawLayout = (DrawerLayout) findViewById(R.id.epubDrawer);
        mTocList = (RecyclerView) findViewById(R.id.epubTocList);

        BUS.register(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_epubreader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }else if (id == R.id.optmenuTOC) {
            if (mDrawLayout.isDrawerOpen(Gravity.LEFT)){
                mDrawLayout.closeDrawer(Gravity.LEFT, true);
            } else {
                mDrawLayout.openDrawer(Gravity.LEFT, true);
            }
            return true;
        } else if (id == R.id.optmenuFont) {
            mConfigBottomSheetDialogFragment = new ConfigBottomSheetDialogFragment();
            mConfigBottomSheetDialogFragment.show(getSupportFragmentManager(), mConfigBottomSheetDialogFragment.getTag());
            return true;
        } else if (id == R.id.optmenuHighlightList) {
            viewHighLightList(mBook.getTitle());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBook() {
        progressDialog = new ProgressDialog(EPubReaderActivity.this, R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("EPUB loading...");
        progressDialog.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                mBook = FileUtil.saveEpubFile(EPubReaderActivity.this, mEpubSourceType, mEpubFilePath,
                        mEpubRawId, mEpubFileName);
                mBookeFilePath = FileUtil.getEpubFilePath(mEpubSourceType, mEpubFilePath, mEpubFileName);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadBook();
                        progressDialog.dismiss();
                    }
                });
            }
        }).start();
    }

    private void loadBook() {
        configRecyclerViews();
        configEPubReder();
        parseSmil();

        loadTOC();
        mIsLoaded = true;
    }

    public void configRecyclerViews() {
        mTocReferences = (ArrayList<TOCReference>) mBook.getTableOfContents().getTocReferences();
        mSpineReferences = mBook.getSpine().getSpineReferences();
        setSpineReferenceTitle();
    }

    private void setSpineReferenceTitle() {
        for (int j = 0; j < mSpineReferences.size(); j++) {
            String href = mSpineReferences.get(j).getResource().getHref();
            for (int i = 0; i < mTocReferences.size(); i++) {
                if (mTocReferences.get(i).getResource().getHref().equalsIgnoreCase(href)) {
                    mSpineReferences.get(j).getResource()
                            .setTitle(mTocReferences.get(i).getTitle());
                    break;
                } else {
                    mSpineReferences.get(j).getResource().setTitle("");
                }
            }
        }
    }

    private void configEPubReder() {
        mEPubPageViewPager = (DirectionalViewpager) findViewById(R.id.epubPageViewPager);
        mEPubPageViewPager.addOnPageChangeListener(new DirectionalViewpager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //Log.d(TAG,"onPageScrolled");
            }

            @Override
            public void onPageSelected(int position) {
                mChapterPosition = position;
                //Log.d(TAG,"onPageSelected"+String.valueOf(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //Log.d(TAG,"onPageScrollStateChanged");
            }
        });

        if (mBook != null && mSpineReferences != null) {
            mEPubPageFragmentAdapter = new EPubPageFragmentAdapter(getSupportFragmentManager(),
                    mSpineReferences, mBook.getTitle(), mEpubFileName);
            mEPubPageViewPager.setAdapter(mEPubPageFragmentAdapter);
            if (AppUtil.checkPreviousBookStateExist(EPubReaderActivity.this, mBook)) {
                mEPubPageViewPager.setCurrentItem(AppUtil.getPreviousBookStatePosition(EPubReaderActivity.this, mBook));
            }
        }
    }

    private void parseSmil() {
        mIsSmilParsed = false;
        new Thread(new Runnable() {
            @Override
            public void run() {
                SmilFile smilFile = AppUtil.createSmilJson(EPubReaderActivity.this, mEpubFileName);
                if (smilFile != null) {
                    mTextElementList = smilFile.getTextSegments();
                    mIsSmilAvailable = true;
                    EPubReaderActivity.BUS.post(mTextElementList);
                } else {
                    mIsSmilAvailable = false;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIsSmilParsed = true;
                    }
                });
            }


        }).start();

    }

    @Override
    public void onBackPressed() {
        saveBookState();
        finish();
        super.onBackPressed();
    }

    @Override
    public void onOrentationChange(int orentation) {
        if (orentation == 0) {
            mEPubPageViewPager.setDirection(DirectionalViewpager.Direction.VERTICAL);
            if (mBook != null && mSpineReferences != null) {
                mEPubPageFragmentAdapter =
                        new EPubPageFragmentAdapter(getSupportFragmentManager(),
                                mSpineReferences, mBook.getTitle(), mEpubFileName);
                mEPubPageViewPager.setAdapter(mEPubPageFragmentAdapter);
                mEPubPageViewPager.setOffscreenPageLimit(1);
                mEPubPageViewPager.setCurrentItem(mChapterPosition);
            }
        } else {
            mEPubPageViewPager.setDirection(DirectionalViewpager.Direction.HORIZONTAL);
            if (mBook != null && mSpineReferences != null) {
                mEPubPageFragmentAdapter =
                        new EPubPageFragmentAdapter(getSupportFragmentManager(),
                                mSpineReferences, mBook.getTitle(), mEpubFileName);
                mEPubPageViewPager.setAdapter(mEPubPageFragmentAdapter);
                mEPubPageViewPager.setCurrentItem(mChapterPosition);
            }
        }
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void saveBookState() {
        AppUtil.saveBookState(EPubReaderActivity.this, mBook, mEPubPageViewPager.getCurrentItem(), mWebViewScrollPosition);
    }

    public boolean setPagerToPosition(int audioPosition) {
        String src = mTextElementList.get(audioPosition).getSrc();
        String[] temp = src.split("#");
        String href = "text//" + temp[0];
        String currentHref =
                mSpineReferences.get(mEPubPageViewPager.getCurrentItem())
                        .getResource().getHref();
        if (href.equalsIgnoreCase(currentHref)) {
            return false;
        } else {
            setPagerToPosition("text//" + temp[0]);
            return true;
        }
    }


    @Override
    public String getChapterHtmlContent(int position) {
        return readHTmlString(position);
    }

    @Override
    public void hideOrshowToolBar() {

    }

    @Override
    public void hideToolBarIfVisible() {

    }

    public void setPagerToPosition(String href) {
        for (int i = 0; i < mSpineReferences.size(); i++) {
            if (AppUtil.compareUrl(href, mSpineReferences.get(i).getResource().getHref())) {
                mEPubPageViewPager.setCurrentItem(i, true);
                break;
            }
        }
    }

    @Override
    public void checkHightlightCount(int count) {}

    @Override
    public void setLastWebViewPosition(int position) {
        mWebViewScrollPosition = position;
    }

    @Override
    public ArrayList<Highlight> getAllHighlights(String bookId) { return null; }

    @Override
    public void insertHighlight(Highlight highlight){}

    @Override
    public void updateHighlight(Highlight highlight) {}

    @Override
    public void updateHighlightStyle(String id, String style) {}

    @Override
    public void deleteHighlight(String id){}

    public void viewHighLightList(String bookId){}

    private String readHTmlString(int position) {
        String pageHref = mSpineReferences.get(position).getResource().getHref();
        String opfpath = AppUtil.getPathOPF(FileUtil.getEpubFolderPath(mEpubFileName), EPubReaderActivity.this);
        if (AppUtil.checkOPFInRootDirectory(FileUtil.getEpubFolderPath(mEpubFileName), EPubReaderActivity.this)) {
            pageHref = FileUtil.getEpubFolderPath(mEpubFileName) + "/" + pageHref;
        } else {
            pageHref = FileUtil.getEpubFolderPath(mEpubFileName) + "/" + opfpath + "/" + pageHref;
        }
        String html = EpubManipulator.readPage(pageHref);
        return html;
    }

    public Highlight setCurrentPagerPostion(Highlight highlight) {
        highlight.setCurrentPagerPostion(mEPubPageViewPager.getCurrentItem());
        return highlight;
    }

    public String getEpubFileName() {
        return mEpubFileName;
    }

    public boolean isSmilAvailable() {
        return mIsSmilAvailable;
    }

    public int getmChapterPosition() {
        return mChapterPosition;
    }

    public boolean isbookOpened() {
        return mIsbookOpened;
    }

    public void setIsbookOpened(boolean mIsbookOpened) {
        this.mIsbookOpened = mIsbookOpened;
    }


    public void loadTOC(){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.epubTocList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        if (mTocReferences != null) {
//            TOCAdapter tocAdapter = new TOCAdapter(mTocReferences, mSelectedChapterPosition);
//            recyclerView.setAdapter(tocAdapter);
//        }

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        //recyclerView.setHasFixedSize(true);
        if (mTocReferences != null) {
            TOCAdapter tocAdapter = new TOCAdapter(mTocReferences, mSelectedChapterPosition);
            recyclerView.setAdapter(tocAdapter);
        }
    }

    public class TOCAdapter extends RecyclerView.Adapter<TOCAdapter.ViewHolder> {
        private List<TOCReference> mTOCReferences;
        private int mSelectedChapterPosition;
        private boolean mIsNightMode;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tocTitleView;
            public View line;

            public ViewHolder(View v) {
                super(v);
                tocTitleView = (TextView) v.findViewById(R.id.chapter);
                line = v.findViewById(R.id.line1);
            }
        }

        public TOCAdapter(List<TOCReference> tocReferences, int selectedChapterPosition) {
            mTOCReferences = tocReferences;
            mSelectedChapterPosition = selectedChapterPosition;
            mIsNightMode = Config.getConfig().isNightMode();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row_chapter, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.tocTitleView.setText(mTOCReferences.get(position).getTitle());

            if(mSelectedChapterPosition!=-1) {
                if (!(mSelectedChapterPosition == position)) {
                    if (mIsNightMode) {
                        holder.tocTitleView.setTextColor(Color.WHITE);
                        holder.line.setBackgroundColor(Color.WHITE);
                    } else {
                        holder.tocTitleView.setTextColor(Color.BLACK);
                        holder.line.setBackgroundColor(Color.BLACK);
                    }
                } else {
                    holder.tocTitleView.setTextColor(Color.GREEN);
                    if (mIsNightMode) {
                        holder.line.setBackgroundColor(Color.WHITE);
                    }
                }
            }

            holder.tocTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String title = mSpineReferences.get(position).getResource().getId();
                    for (int i = 0; i < mSpineReferences.size(); i++) {
                        if (mSpineReferences.get(i).getResource().getId().equals(title)) {
                            mDrawLayout.closeDrawer(Gravity.LEFT,true);
                            mSelectedChapterPosition = i;
                            mChapterPosition = mSelectedChapterPosition;
                            int spineRefrencesPos = AppUtil.getSpineRefrecePos(mSpineReferences, mTocReferences.get(mChapterPosition));
                            mEPubPageViewPager.setCurrentItem(spineRefrencesPos);
                        }
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mTOCReferences.size();
        }
    }


}
