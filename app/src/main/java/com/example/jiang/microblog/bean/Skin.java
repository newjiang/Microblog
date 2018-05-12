package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */
public class Skin {

    private int drawable;
    private String skinName;

    public Skin() {
    }

    public Skin(int drawable, String skinName) {
        this.drawable = drawable;
        this.skinName = skinName;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    @Override
    public String toString() {
        return "Skin{" +
                "drawable=" + drawable +
                ", skinName='" + skinName + '\'' +
                '}';
    }
}
