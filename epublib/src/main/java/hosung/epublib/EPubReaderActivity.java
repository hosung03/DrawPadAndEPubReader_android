package hosung.epublib;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.animation.LinearInterpolator;

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
    private Toolbar mToolbar;

    private EpubSourceType mEpubSourceType;
    private String mEpubFilePath;
    private String mEpubFileName;
    private int mEpubRawId;
    private Book mBook;
    private String mBookeFilePath;
    private ArrayList<TOCReference> mTocReferences;
    private List<SpineReference> mSpineReferences;
    private List<AudioElement> mAudioElementArrayList;
    private List<TextElement> mTextElementList = new ArrayList<>();

    public boolean mIsActionBarVisible;
    public boolean mIsSmilParsed = false;
    private int mChapterPosition;
    private boolean mIsSmilAvailable;
    private EPubPageFragmentAdapter mEPubPageFragmentAdapter;
    private int mWebViewScrollPosition;
    //private ConfigBottomSheetDialogFragment mConfigBottomSheetDialogFragment;
    //private AudioViewBottomSheetDailogFragment mAudioBottomSheetDialogFragment;
    private boolean mIsbookOpened =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epub_reader);

        mEpubSourceType = (EpubSourceType) getIntent().getExtras().getSerializable("EPubSourceType");
        if (mEpubSourceType.equals(EpubSourceType.RAW)) {
            mEpubRawId = getIntent().getExtras().getInt("EPubRawId");
        } else {
            mEpubFilePath = getIntent().getExtras().getString("EPubFilePath");
        }
        mEpubFileName = FileUtil.getEpubFilename(this, mEpubSourceType, mEpubFilePath, mEpubRawId);
        initBook();
    }

    private void initBook() {
        Log.d(TAG,"initBook");
//        final Dialog pgDailog = ProgressDialog.show(FolioActivity.this,
//                getString(R.string.please_wait));
        new Thread(new Runnable() {
            @Override
            public void run() {
                mBook = FileUtil.saveEpubFile(EPubReaderActivity.this, mEpubSourceType, mEpubFilePath,
                        mEpubRawId, mEpubFileName);
                mBookeFilePath = FileUtil.getFolioEpubFilePath(mEpubSourceType, mEpubFilePath, mEpubFileName);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadBook();
                        //if (pgDailog != null && pgDailog.isShowing()) pgDailog.dismiss();
                    }
                });
            }
        }).start();
//
//        new DbAdapter(FolioActivity.this)
    }

    private void loadBook() {
        configRecyclerViews();
        configEPubReder();
        parseSmil();
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
//        ((TextView) findViewById(R.id.lbl_center))
//                .setText(mSpineReferences.get(0).getResource().getTitle());
    }

    private void configEPubReder() {
        mEPubPageViewPager = (DirectionalViewpager) findViewById(R.id.epubPageViewPager);
        mEPubPageViewPager.addOnPageChangeListener(new DirectionalViewpager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mChapterPosition = position;
                //((TextView) findViewById(R.id.lbl_center)).setText(mSpineReferences.get(position).getResource().getTitle());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                    mAudioElementArrayList = smilFile.getAudioSegments();
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

    public AudioElement getElement(int position) {
        if (mAudioElementArrayList != null) {
            return mAudioElementArrayList.get(position);
        } else {
            return null;
        }
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
//        if (mAudioBottomSheetDialogFragment != null) {
//            mAudioBottomSheetDialogFragment.unRegisterBus();
//            mAudioBottomSheetDialogFragment.stopAudioIfPlaying();
//            mAudioBottomSheetDialogFragment = null;
//        }
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
        if (mIsActionBarVisible) {
            toolbarAnimateHide();
        } else {
            toolbarAnimateShow(1);
        }
    }

    @Override
    public void hideToolBarIfVisible() {
        if (mIsActionBarVisible) {
            toolbarAnimateHide();
        }
    }

    private String readHTmlString(int position) {
        String pageHref = mSpineReferences.get(position).getResource().getHref();
        String opfpath = AppUtil.getPathOPF(FileUtil.getFolioEpubFolderPath(mEpubFileName), EPubReaderActivity.this);
        if (AppUtil.checkOPFInRootDirectory(FileUtil.getFolioEpubFolderPath(mEpubFileName), EPubReaderActivity.this)) {
            pageHref = FileUtil.getFolioEpubFolderPath(mEpubFileName) + "/" + pageHref;
        } else {
            pageHref = FileUtil.getFolioEpubFolderPath(mEpubFileName) + "/" + opfpath + "/" + pageHref;
        }
        String html = EpubManipulator.readPage(pageHref);
        return html;
    }

    public void setPagerToPosition(String href) {
        for (int i = 0; i < mSpineReferences.size(); i++) {
            if (AppUtil.compareUrl(href, mSpineReferences.get(i).getResource().getHref())) {
                mEPubPageViewPager.setCurrentItem(i, true);
                toolbarAnimateHide();
                break;
            }
        }
    }

    private void toolbarAnimateShow(final int verticalOffset) {
        mToolbar.animate()
                .translationY(0)
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationStart(Animator animation) {
                        toolbarSetElevation(verticalOffset == 0 ? 0 : 1);
                    }
                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mIsActionBarVisible) {
                            toolbarAnimateHide();
                        }
                    }
                });
            }
        }, 10000);

        mIsActionBarVisible = true;
    }

    private void toolbarAnimateHide() {
        mToolbar.animate()
                .translationY(-mToolbar.getHeight())
                .setInterpolator(new LinearInterpolator())
                .setDuration(180)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarSetElevation(0);
                    }
                });
        mIsActionBarVisible = false;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void toolbarSetElevation(float elevation) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mToolbar.setElevation(elevation);
        }
    }

    public Highlight setCurrentPagerPostion(Highlight highlight) {
        highlight.setCurrentPagerPostion(mEPubPageViewPager.getCurrentItem());
        return highlight;
    }

    //@Override
    public void setLastWebViewPosition(int position) {
        mWebViewScrollPosition = position;
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
}
