package com.example.mywebdemo.event;

public class zoomEvent extends baseEvent {

    //viewpager当前的状态，放大或缩小

    private boolean  isMatchParent;  //viewpager 当前是不是 MatchParent

    public zoomEvent(boolean isMatchParent) {
        this.isMatchParent = isMatchParent;
    }

    public boolean isMatchParent() {
        return isMatchParent;
    }

    public void setIsMatchParent(boolean isMatchParent) {
        this.isMatchParent = isMatchParent;
    }
}