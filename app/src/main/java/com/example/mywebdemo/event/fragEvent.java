package com.example.mywebdemo.event;

public class fragEvent  extends baseEvent{

    //用于传递 fragment的tag
    String fragTag;

    public fragEvent(String fragTag) {
        this.fragTag = fragTag;
    }

    public String getFragTag() {
        return fragTag;
    }

    public void setFragTag(String fragTag) {
        this.fragTag = fragTag;
    }


}
