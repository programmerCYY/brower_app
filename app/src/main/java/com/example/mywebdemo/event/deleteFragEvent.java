package com.example.mywebdemo.event;

public class deleteFragEvent extends baseEvent {

    //如果fragment 上滑到一半，就会发出删除信号

    String fragTag;

    public deleteFragEvent(String fragTag) {
        this.fragTag = fragTag;
    }

    public String getFragTag() {
        return fragTag;
    }

    public void setFragTag(String fragTag) {
        this.fragTag = fragTag;
    }
}