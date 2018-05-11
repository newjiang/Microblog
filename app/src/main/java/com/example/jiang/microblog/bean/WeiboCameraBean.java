package com.example.jiang.microblog.bean;

import java.util.List;

/**
 * Created by jiang on 2018/5/12.
 */

public class WeiboCameraBean {
    private List<String> c;

    public List<String> getC() {
        return c;
    }

    public void setC(List<String> c) {
        this.c = c;
    }

    @Override
    public String toString() {
        return "WeiboCameraBean{" +
                "c=" + c +
                '}';
    }
}
