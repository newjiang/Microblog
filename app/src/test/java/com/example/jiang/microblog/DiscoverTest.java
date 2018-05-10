package com.example.jiang.microblog;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.io.IOException;

public class DiscoverTest {

    @Test
    public void test() {
        try {
            Document doc = Jsoup.connect("https://weibo.com/?category=1760").get();
            System.out.println(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}