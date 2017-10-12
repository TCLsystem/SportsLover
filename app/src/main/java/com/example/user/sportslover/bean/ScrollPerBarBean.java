package com.example.user.sportslover.bean;

import java.io.Serializable;

/**
 * Created by user on 17-10-10.
 */

public class ScrollPerBarBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String bottomString;
    private int ratio;
    private String topString;
    private int actualHeight;// 实际高度
    private boolean flag;// 记录是不是点击状态
    private int leftX, rightX, topY;// 记录左右上边距

    public String getBottomString() {
        return bottomString;
    }

    public void setBottomString(String bottomString) {
        this.bottomString = bottomString;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public String getTopString() {
        return topString;
    }

    public void setTopString(String topString) {
        this.topString = topString;
    }

    public ScrollPerBarBean(String bottomString, int ratio, String topString) {
        super();
        this.bottomString = bottomString;
        this.ratio = ratio;
        this.topString = topString;
    }

    public int getActualHeight() {
        return actualHeight;
    }

    public void setActualHeight(int actualHeight) {
        this.actualHeight = actualHeight;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public int getTopY() {
        return topY;
    }

    public void setTopY(int topY) {
        this.topY = topY;
    }
}
