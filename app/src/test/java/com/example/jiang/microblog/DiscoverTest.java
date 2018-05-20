package com.example.jiang.microblog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

public class DiscoverTest {

    @Test
    public void test() {
        try {
            Document doc = Jsoup.connect("https://weibo.com/tv/v/GhocXDxLr?fid=1034:8e7a1ff0ce8464f5f9786961b08cc020").get();
            System.out.println(doc.html());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void length() {
        String s = "02e74f10e0327ad868d138f2b4fdd6f0";
        System.out.println(s.length());
    }
}