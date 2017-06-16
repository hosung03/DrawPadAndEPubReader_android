package com.hosung.drawpadandepubreader.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;


/**
 * Created by Hosung, Lee on 2017. 5. 30..
 */

public class DrawNote extends RealmObject {
    @PrimaryKey
    private int id;
    private boolean saved = false;
    @Required
    private String user;
    private String title;
    private RealmList<DrawPath> paths;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public RealmList<DrawPath> getPaths() {
        return paths;
    }

    public void setPoints(RealmList<DrawPath> paths) {
        this.paths = paths;
    }
}
