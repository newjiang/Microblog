package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

public class VisibleBean {

    private int type;
    private long list_id;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public long getList_id() {
        return list_id;
    }

    public void setList_id(long list_id) {
        this.list_id = list_id;
    }

    @Override
    public String toString() {
        return "VisibleBean{" +
                "type=" + type +
                ", list_id=" + list_id +
                '}';
    }
}