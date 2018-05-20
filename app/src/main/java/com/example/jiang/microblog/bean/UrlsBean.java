package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/20.
 */

public class UrlsBean {
    private boolean result;
    private String url_short;
    private String url_long;
    private int transcode;
    private int type;

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getUrl_short() {
        return url_short;
    }

    public void setUrl_short(String url_short) {
        this.url_short = url_short;
    }

    public String getUrl_long() {
        return url_long;
    }

    public void setUrl_long(String url_long) {
        this.url_long = url_long;
    }

    public int getTranscode() {
        return transcode;
    }

    public void setTranscode(int transcode) {
        this.transcode = transcode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UrlsBean{" +
                "result=" + result +
                ", url_short='" + url_short + '\'' +
                ", url_long='" + url_long + '\'' +
                ", transcode=" + transcode +
                ", type=" + type +
                '}';
    }
}
