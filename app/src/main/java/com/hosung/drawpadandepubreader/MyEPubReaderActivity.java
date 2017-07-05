package com.hosung.drawpadandepubreader;

import android.os.Bundle;

import com.hosung.drawpadandepubreader.models.EPubHighLight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import hosung.epublib.EPubReaderActivity;
import hosung.epublib.model.Highlight;
import io.realm.Realm;
import io.realm.RealmResults;

public class MyEPubReaderActivity extends EPubReaderActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                highlight.setBookId(ePubHighLight.getBookId());
                highlight.setContent(ePubHighLight.getContent());
                highlight.setContentPost(ePubHighLight.getContentPost());
                highlight.setContentPre(ePubHighLight.getContentPre());
                highlight.setDate(getDateTime(ePubHighLight.getDate()));
                highlight.setHighlightId(ePubHighLight.getHighlightId());
                highlight.setPage(ePubHighLight.getPage());
                highlight.setType(ePubHighLight.getType());
                highlight.setCurrentPagerPostion(ePubHighLight.getCurrentPagerPostion());
                highlight.setCurrentWebviewScrollPos(ePubHighLight.getCurrentWebviewScrollPos());
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
        ePubHighLight.setDate(highlight.getDate().toString());
        ePubHighLight.setHighlightId(highlight.getHighlightId());
        ePubHighLight.setPage(highlight.getPage());
        ePubHighLight.setType(highlight.getType());
        ePubHighLight.setCurrentPagerPostion(highlight.getCurrentPagerPostion());
        ePubHighLight.setCurrentWebviewScrollPos(highlight.getCurrentWebviewScrollPos());
        ePubHighLight.setNote(highlight.getNote());
        realm.commitTransaction();
    }

    public Date getDateTime(String date) {
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
