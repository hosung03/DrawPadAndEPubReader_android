package com.hosung.drawpadandepubreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.MenuItem;

import com.hosung.drawpadandepubreader.models.EPubHighLight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hosung.epublib.EPubReaderActivity;
import hosung.epublib.model.Highlight;
import hosung.epublib.model.ReloadData;
import hosung.epublib.model.WebViewPosition;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyEPubReaderActivity extends EPubReaderActivity {
    private static final String TAG = "MyEPubReaderActivity";
    public static final int ACTION_HIGHLIGHT = 1;

    private Handler mHandler;
    private Runnable mHandlerTask;
    public int curHighlightNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTask();
    }

    public void startTask(){
        if(mHandler==null) mHandler = new Handler();
        if(mHandlerTask==null) mHandlerTask = new Runnable() {
            @Override
            public void run() {
                if(!mIsLoaded){
                    mHandler.postDelayed(mHandlerTask, 1000*30);  // 20 second delay
                    return;
                }
                Log.d(TAG,"startTask");
                Realm realm = Realm.getDefaultInstance();
                RealmResults<EPubHighLight> results
                        = realm.where(EPubHighLight.class).equalTo("bookId",mBook.getTitle()).findAll();
                if(results != null && results.size() > 0) {
                    if(curHighlightNo != results.size()) {
                        BUS.post(new ReloadData());
                        curHighlightNo = results.size();
                    }
                }
                mHandler.postDelayed(mHandlerTask, 1000*10);  // 10 second delay
            }
        };
        mHandlerTask.run();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mHandler!=null){
            if(mHandlerTask!=null){
                mHandler.removeCallbacks(mHandlerTask);
                mHandlerTask = null;
            }
            mHandler = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(mHandler!=null){
            if(mHandlerTask!=null){
                mHandler.removeCallbacks(mHandlerTask);
                mHandlerTask = null;
            }
            mHandler = null;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mHandler!=null){
            if(mHandlerTask!=null){
                mHandler.removeCallbacks(mHandlerTask);
                mHandlerTask = null;
            }
            mHandler = null;
        }
    }

    @Override
    public void checkHightlightCount(int count) {
        if(curHighlightNo != count){
            curHighlightNo = count;
        }
    }

    @Override
    public ArrayList<Highlight> getAllHighlights(String bookId) {
        ArrayList<Highlight> highlights = new ArrayList<>();
        Realm realm = Realm.getDefaultInstance();
        RealmResults<EPubHighLight> results
                = realm.where(EPubHighLight.class).equalTo("bookId",bookId).findAll();
        if(results != null && results.size() > 0) {
            for (EPubHighLight ePubHighLight : results) {
                Highlight highlight = new Highlight();
                highlight.setId(ePubHighLight.getId());
                highlight.setBookId(ePubHighLight.getBookId());
                highlight.setContent(ePubHighLight.getContent());
                highlight.setContentPost(ePubHighLight.getContentPost());
                highlight.setContentPre(ePubHighLight.getContentPre());
                highlight.setDate(getDateTime(ePubHighLight.getDate()));
                highlight.setHighlightId(ePubHighLight.getHighlightId());
                highlight.setPage(ePubHighLight.getPage());
                highlight.setType(ePubHighLight.getType());
                if(ePubHighLight.getCurrentPagerPostion()!=null)
                    highlight.setCurrentPagerPostion(ePubHighLight.getCurrentPagerPostion());
                else
                    highlight.setCurrentPagerPostion(0);
                if(ePubHighLight.getCurrentWebviewScrollPos()!=null)
                    highlight.setCurrentWebviewScrollPos(ePubHighLight.getCurrentWebviewScrollPos());
                else
                    highlight.setCurrentWebviewScrollPos(0);
                highlight.setNote(ePubHighLight.getNote());

                highlights.add(highlight);
            }
        }

        return highlights;
    }

    @Override
    public void insertHighlight(Highlight highlight) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        int nextID = 1;
        try {
            nextID = realm.where(EPubHighLight.class).max("id").intValue() + 1;
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        EPubHighLight ePubHighLight = realm.createObject(EPubHighLight.class, nextID);
        ePubHighLight.setBookId(highlight.getBookId());
        ePubHighLight.setContent(highlight.getContent());
        ePubHighLight.setContentPost(highlight.getContentPost());
        ePubHighLight.setContentPre(highlight.getContentPre());
        ePubHighLight.setDate(getDateTimeString(highlight.getDate()));
        ePubHighLight.setHighlightId(highlight.getHighlightId());
        ePubHighLight.setPage(highlight.getPage());
        ePubHighLight.setType(highlight.getType());
        ePubHighLight.setCurrentPagerPostion(highlight.getCurrentPagerPostion());
        ePubHighLight.setCurrentWebviewScrollPos(highlight.getCurrentWebviewScrollPos());
        ePubHighLight.setNote(highlight.getNote());
        realm.commitTransaction();
    }

    @Override
    public void updateHighlight(Highlight highlight) {
        Realm realm = Realm.getDefaultInstance();
        EPubHighLight ePubHighLight = realm.where(EPubHighLight.class).equalTo("id", highlight.getId()).findFirst();
        realm.beginTransaction();
        ePubHighLight.setBookId(highlight.getBookId());
        ePubHighLight.setContent(highlight.getContent());
        ePubHighLight.setContentPost(highlight.getContentPost());
        ePubHighLight.setContentPre(highlight.getContentPre());
        ePubHighLight.setDate(getDateTimeString(highlight.getDate()));
        ePubHighLight.setHighlightId(highlight.getHighlightId());
        ePubHighLight.setPage(highlight.getPage());
        ePubHighLight.setType(highlight.getType());
        ePubHighLight.setCurrentPagerPostion(highlight.getCurrentPagerPostion());
        ePubHighLight.setCurrentWebviewScrollPos(highlight.getCurrentWebviewScrollPos());
        ePubHighLight.setNote(highlight.getNote());
        realm.commitTransaction();
    }

    @Override
    public void updateHighlightStyle(String id, String style) {
        Realm realm = Realm.getDefaultInstance();
        EPubHighLight ePubHighLight = realm.where(EPubHighLight.class).equalTo("id",id).findFirst();
        realm.beginTransaction();
        ePubHighLight.setType(style);
        realm.commitTransaction();
    }

    @Override
    public void deleteHighlight(String id){
        Realm realm = Realm.getDefaultInstance();
        EPubHighLight ePubHighLight = realm.where(EPubHighLight.class).equalTo("id",id).findFirst();
        realm.beginTransaction();
        ePubHighLight.deleteFromRealm();
        realm.commitTransaction();
    }

    @Override
    public void viewHighLightList(String bookId){
        Intent intent = new Intent(MyEPubReaderActivity.this, HighlightActivity.class);
        intent.putExtra("BookID", bookId);
        //startActivity(intent);
        startActivityForResult(intent, ACTION_HIGHLIGHT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ACTION_HIGHLIGHT && resultCode == RESULT_OK) {
            Highlight highlight = data.getParcelableExtra("highlight_selected");
            if (highlight != null) {

                // it made through iOS devices.
                if(highlight.getCurrentPagerPostion()==0 && highlight.getCurrentWebviewScrollPos()==0)
                    return;

                int position = highlight.getCurrentPagerPostion();
                mEPubPageViewPager.setCurrentItem(position);
                WebViewPosition webViewPosition = new WebViewPosition();
                webViewPosition.setWebviewPos(highlight.getCurrentWebviewScrollPos());
                BUS.post(webViewPosition);
            }
        }
    }

    public static String getDateTimeString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static Date getDateTime(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date1 = new Date();
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }
}
