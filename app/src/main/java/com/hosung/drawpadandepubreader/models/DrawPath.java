/*
 * Copyright 2016 Realm Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hosung.drawpadandepubreader.models;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by Hosung, Lee on 2017. 5. 30..
 */

public class DrawPath extends RealmObject {
    private boolean saved = false;
    private boolean completed = false;
    private Integer color = 0;
    private Integer bushsize = 0;
    private RealmList<DrawPoint> points;

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getBushsize() {
        return bushsize;
    }

    public void setBushsize(Integer bushsize) {
        this.bushsize = bushsize;
    }

    public RealmList<DrawPoint> getPoints() {
        return points;
    }

    public void setPoints(RealmList<DrawPoint> points) {
        this.points = points;
    }
}
