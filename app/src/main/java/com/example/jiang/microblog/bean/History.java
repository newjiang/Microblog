package com.example.jiang.microblog.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jiang on 2018/4/26.
 */

public class History extends DataSupport implements Serializable {
    private String history;

    public History() {
    }

    public History(String history) {
        this.history = history;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    @Override
    public String toString() {
        return "History{" +
                "history='" + history + '\'' +
                '}';
    }
}
