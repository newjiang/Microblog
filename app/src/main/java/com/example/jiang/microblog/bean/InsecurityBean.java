package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

public class InsecurityBean {

    private boolean sexual_content;

    public boolean isSexual_content() {
        return sexual_content;
    }

    public void setSexual_content(boolean sexual_content) {
        this.sexual_content = sexual_content;
    }

    @Override
    public String toString() {
        return "InsecurityBean{" +
                "sexual_content=" + sexual_content +
                '}';
    }
}