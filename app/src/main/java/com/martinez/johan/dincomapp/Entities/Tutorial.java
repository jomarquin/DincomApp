package com.martinez.johan.dincomapp.Entities;

import java.io.Serializable;

public class Tutorial implements Serializable {

    private String dName;
    private String linkVideo;

    public Tutorial() {
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getLinkVideo() {
        return linkVideo;
    }

    public void setLinkVideo(String linkVideo) {
        this.linkVideo = linkVideo;
    }

}
