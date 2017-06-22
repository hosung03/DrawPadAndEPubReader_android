package com.hosung.drawpadandepubreader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hosung.drawpadandepubreader.models.DrawNote;
import com.hosung.drawpadandepubreader.models.UserProfile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hosung.epublib.EPubReaderActivity;
import hosung.setionlibrary.SectionedRecyclerViewAdapter;
import hosung.setionlibrary.StatelessSection;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.SyncUser;

/**
 * Created by Hosung, Lee on 2017. 5. 23..
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_LOGIN = 0;
    private static final int REQUEST_DRAWPAD = 1;
    private static final int REQUEST_EPUBREAD = 2;

    public static String realmServerIP = "127.0.0.1"; // your Realm Object Server IP
    public static String realmID = "demo@localhost.io"; // your Login ID of the Realm Object Server
    public static String realmPasswd = "demo1234"; // your Login Password of the Realm Object Server

    static String syncServerURL = "realm://"+realmServerIP+":9080/~/DrawPad";
    static String syncAuthURL = "http://"+realmServerIP+":9080/auth";

    static final String DEFAULT_USER_NAME = "test";
    static final String DEFAULT_USER_EMAIL = "test@localhost.io";
    static final String DEFAULT_USER_PASSWORD = "1234";

    static final String DEFAULT_NOTE_TITLE = "New Note";

    public static boolean isSynced = false;

    public static String userEmail = null;

    public static List<EPubItem> lEPubList = null;
    public static List<DrawNoteItem> lDrawNoteList = null;

    private Realm realm;
    private SectionedRecyclerViewAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(userEmail==null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, REQUEST_LOGIN);
        } else {
            createList();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_LOGIN) {
            if (resultCode == RESULT_OK) {
                userEmail = data.getStringExtra("UserEmail");
                createList();
            }
        } if (requestCode == REQUEST_DRAWPAD) {
            if (resultCode == RESULT_OK) {
                updateList();
            }
        }
    }

    private void createList() {
        realm = Realm.getDefaultInstance();

        // List
        sectionAdapter = new SectionedRecyclerViewAdapter();
        if (lEPubList != null) lEPubList = null;
        lEPubList = getEPubsList();
        if (lDrawNoteList != null) lDrawNoteList = null;
        lDrawNoteList = getDrawNotesList();
        sectionAdapter.addSection(new ListSection(getString(R.string.epub_list), (List) lEPubList));
        sectionAdapter.addSection(new ListSection(getString(R.string.drawnote_list), (List) lDrawNoteList));

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.listview);

        GridLayoutManager glm = new GridLayoutManager(this, 2);
        glm.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (sectionAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        recyclerView.setLayoutManager(glm);
        recyclerView.setAdapter(sectionAdapter);

    }

    private void updateList(){
        realm = Realm.getDefaultInstance();

        sectionAdapter.removeAllSections();
        if (lEPubList != null) lEPubList = null;
        lEPubList = getEPubsList();
        if (lDrawNoteList != null) lDrawNoteList = null;
        lDrawNoteList = getDrawNotesList();
        sectionAdapter.addSection(new ListSection(getString(R.string.epub_list), (List) lEPubList));
        sectionAdapter.addSection(new ListSection(getString(R.string.drawnote_list), (List) lDrawNoteList));
        sectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed() {
        if (realm != null) {
            realm.close();
            realm = null;
        }

        MainActivity.logoff();

        finish();
        System.exit(0);
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (realm != null) {
            realm.close();
            realm = null;
        }
    }

    private List<EPubItem> getEPubsList() {
        List<String> arrayList = new ArrayList<>(Arrays.asList(getResources()
                .getStringArray(R.array.epubs)));

        List<EPubItem> listItems = new ArrayList<>();

        for (String str : arrayList) {
            String[] array = str.split("\\|");
            if(array[2].equals("null"))
                listItems.add(new EPubItem(array[0], array[1], ""));
            else
                listItems.add(new EPubItem(array[0], array[1], array[2]));
        }

        return listItems;
    }

    private List<DrawNoteItem> getDrawNotesList() {
        List<DrawNoteItem> drawnoteList = new ArrayList<>();

        RealmResults<DrawNote> results
                = realm.where(DrawNote.class).equalTo("user",userEmail).equalTo("saved",true).findAll();
        if(results != null && results.size() > 0) {
            for (DrawNote drawNote : results) {
                drawnoteList.add(new DrawNoteItem(drawNote.getTitle(),drawNote.getId()));
            }
        }
        return drawnoteList;
    }

    class ListSection extends StatelessSection {

        String title;
        List<ListItem> list;

        public ListSection(String title, List<ListItem> list) {
            super(R.layout.list_header, R.layout.list_item);

            this.title = title;
            this.list = list;
        }

        @Override
        public int getContentItemsTotal() {
            return list.size();
        }

        @Override
        public RecyclerView.ViewHolder getItemViewHolder(View view) {
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
            final ItemViewHolder itemHolder = (ItemViewHolder) holder;

            itemHolder.tvItemTitle.setText(list.get(position).getTitle());

            if (list.get(position) instanceof EPubItem) {
                EPubItem ePubItem = (EPubItem) list.get(position);
                try {
                    AssetManager assetManager = MainActivity.this.getAssets();
                    InputStream xmlStream = assetManager.open(ePubItem.getImgPath().toString());
                    Bitmap bitmap = BitmapFactory.decodeStream(xmlStream);
                    itemHolder.ivItemImg.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.e(TAG, "error :"+e.getMessage());
                }
            }

            itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = sectionAdapter.getPositionInSection(itemHolder.getAdapterPosition());
                    if (title.equals(getString(R.string.epub_list))) {
//                        Toast.makeText(MainActivity.this, "I am going to implement this feature soon.",
//                                Toast.LENGTH_SHORT).show();
                        EPubItem ePubItem = (EPubItem) list.get(position);

                        Intent intent = new Intent(MainActivity.this, EPubReaderActivity.class);
                        intent.putExtra("EPubFilePath", ePubItem.getFileName());
                        intent.putExtra("EPubSourceType", EPubReaderActivity.EpubSourceType.ASSESTS);
                        startActivityForResult(intent, REQUEST_EPUBREAD);
                    } else if (title.equals(getString(R.string.drawnote_list))) {
                        DrawNoteItem drawNoteItem = (DrawNoteItem) list.get(position);

                        Intent intent = new Intent(MainActivity.this, DrawPadActivity.class);
                        intent.putExtra("NoteID",drawNoteItem.getNoteId());
                        startActivityForResult(intent, REQUEST_DRAWPAD);
                    }
                }
            });
        }

        @Override
        public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
            return new HeaderViewHolder(view);
        }

        @Override
        public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
            HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

            headerHolder.tvTitle.setText(title);
            if (title.equals(getString(R.string.drawnote_list))) {
                headerHolder.btnAdd.setVisibility(View.VISIBLE);
            }

            headerHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(realm==null){
                        Toast.makeText(MainActivity.this,
                                "You should sign up!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        if (!title.equals(getString(R.string.drawnote_list))) return;

                        Intent intent = new Intent(MainActivity.this, DrawPadActivity.class);
                        intent.putExtra("NoteID",0);
                        startActivityForResult(intent, REQUEST_DRAWPAD);
                    }
                }
            });
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final Button btnAdd;

        public HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tv_list_title);
            btnAdd = (Button) view.findViewById(R.id.btn_list_add);
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final View rootView;
        private final TextView tvItemTitle;
        private final ImageView ivItemImg;

        public ItemViewHolder(View view) {
            super(view);

            rootView = view;
            tvItemTitle = (TextView) view.findViewById(R.id.tv_listitem_title);
            ivItemImg = (ImageView) view.findViewById(R.id.iv_listitem_img);
        }
    }

    class ListItem {
        String title;

        public ListItem(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
        public void setTitle(String title) {
            this.title = title;
        }
    }

    class EPubItem extends ListItem {
        String filename;
        String imgpath;

        public EPubItem(String title, String filename, String imgpath) {
            super(title);
            this.filename = "epub/"+filename;
            this.imgpath = "epub/"+imgpath;
        }

        public String getFileName() {
            return filename;
        }
        public void setFileName(String filename) {
            this.filename = filename;
        }

        public String getImgPath() {
            return imgpath;
        }
        public void setImgPath(String imgpath) {
            this.imgpath = imgpath;
        }
    }

    class DrawNoteItem extends ListItem {
        int noteid;

        public DrawNoteItem(String title, int noteid) {
            super(title);
            this.noteid = noteid;
        }

        public int getNoteId() {
            return noteid;
        }
        public void setNoteId(int noteid) {
            this.noteid = noteid;
        }
    }

    public static void logoff(){
        Realm realm = Realm.getDefaultInstance();
        RealmConfiguration realmConfig = realm.getConfiguration();
        realm.close();
        SyncUser user = SyncUser.currentUser();
        if (user != null) {
            user.logout();
        }
        Realm.deleteRealm(realmConfig);
    }

    public static void createInitialDataIfNeeded() {
        final Realm realm = Realm.getDefaultInstance();
        try {
            if (realm.where(UserProfile.class).count() != 0) {
                return;
            }
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    if (realm.where(UserProfile.class).count() == 0) {
                        final UserProfile userProfile = realm.createObject(UserProfile.class, 1);
                        userProfile.setName(MainActivity.DEFAULT_USER_NAME);
                        userProfile.setEmail(MainActivity.DEFAULT_USER_EMAIL);
                        userProfile.setPasswd(MainActivity.DEFAULT_USER_PASSWORD);
                    }
                }
            });
        } finally {
            realm.close();
        }
    }
}
