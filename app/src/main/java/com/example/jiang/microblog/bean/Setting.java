package com.example.jiang.microblog.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jiang on 2018/5/9.
 */

public class Setting extends DataSupport implements Serializable {

    private int id;     //id
    private boolean isNotification;//是否提醒
    private boolean isRing;//是否有铃声
    private boolean isVibrate;//是否有震动
    private boolean isShareByDefault;//是否通过客户端分享
    private String ring;//铃声
    private String skinFuffix;//皮肤后缀

    private long userId;

    public Setting() {
    }

    public Setting(boolean isNotification, boolean isRing, boolean isVibrate, boolean isShareByDefault, String ring, String skinFuffix, long userId) {
        this.isNotification = isNotification;
        this.isRing = isRing;
        this.isVibrate = isVibrate;
        this.isShareByDefault = isShareByDefault;
        this.ring = ring;
        this.skinFuffix = skinFuffix;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isNotification() {
        return isNotification;
    }

    public void setNotification(boolean notification) {
        isNotification = notification;
    }

    public boolean isRing() {
        return isRing;
    }

    public void setRing(boolean ring) {
        isRing = ring;
    }

    public boolean isVibrate() {
        return isVibrate;
    }

    public void setVibrate(boolean vibrate) {
        isVibrate = vibrate;
    }

    public boolean isShareByDefault() {
        return isShareByDefault;
    }

    public void setShareByDefault(boolean shareByDefault) {
        isShareByDefault = shareByDefault;
    }

    public String getRing() {
        return ring;
    }

    public void setRing(String ring) {
        this.ring = ring;
    }

    public String getSkinFuffix() {
        return skinFuffix;
    }

    public void setSkinFuffix(String skinFuffix) {
        this.skinFuffix = skinFuffix;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Setting{" +
                "id=" + id +
                ", isNotification=" + isNotification +
                ", isRing=" + isRing +
                ", isVibrate=" + isVibrate +
                ", isShareByDefault=" + isShareByDefault +
                ", ring='" + ring + '\'' +
                ", skinFuffix='" + skinFuffix + '\'' +
                ", userId=" + userId +
                '}';
    }
}
