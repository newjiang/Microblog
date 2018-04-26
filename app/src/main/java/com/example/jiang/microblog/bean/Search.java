package com.example.jiang.microblog.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jiang on 2018/4/26.
 */

public class Search extends DataSupport implements Serializable {

    private String search;

    public Search() {
    }

    public Search(String search) {
        this.search = search;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "Search{" +
                "search='" + search + '\'' +
                '}';
    }
}
