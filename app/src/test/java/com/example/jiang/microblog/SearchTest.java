package com.example.jiang.microblog;

import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.test.Demo;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class SearchTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testALL() {
        String html = "";
        try {
            Document doc = Jsoup.connect("http://s.weibo.com/weibo/hhhhh").get();
            Elements scripts = doc.select("script");
            int i = 1;
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String str = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String substring = str.substring(1, str.length() - 1);
                    if (substring.length() > html.length()) {
                        html = substring;
                    }
                }
                i++;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Demo demo = gson.fromJson(html, Demo.class);
        System.out.println(demo.getHtml());
    }

    @Test
    public void testFindUser() {
        String html = "";
        try {
            Document doc = Jsoup.connect("http://s.weibo.com/user/hhhhh&Refer=weibo_user").get();
            Elements scripts = doc.select("script");
            int i = 1;
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String str = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String substring = str.substring(1, str.length() - 1);
                    if (substring.length() > html.length()) {
                        html = substring;
                    }
                }
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Demo demo = gson.fromJson(html, Demo.class);
        try {
            Document doc = Jsoup.parse(demo.getHtml());
            Elements elements = doc.select("div.list_person");
            for (Element e : elements) {
                getUserInfo(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUserInfo(Element element) {
//TODO 头像
        String imageUrl = element.getElementsByClass("person_pic").select("img").attr("src");
//TODO 名字
        String name = element.getElementsByClass("person_name")
                .select("a").get(0).getElementsByClass("W_texta").html();
//TODO 性别
        String gender = "";
        String female = element.getElementsByClass("person_addr")
                .select("span").get(0).getElementsByClass("female").attr("title");
        String male = element.getElementsByClass("person_addr")
                .select("span").get(0).getElementsByClass("male").attr("title");
        if (male.equals("")) {
            gender = female;
        } else {
            gender = male;
        }
//TODO 描述
        String description = element.getElementsByClass("person_card").html();
//TODO 粉丝、关注、微博
        String friendsCount = element.getElementsByClass("person_num")
                .select("a").get(0).getElementsByClass("W_linkb").html();

        String followersCount = element.getElementsByClass("person_num")
                .select("a").get(1).getElementsByClass("W_linkb").html();

        String statusesCount = element.getElementsByClass("person_num")
                .select("a").get(2).getElementsByClass("W_linkb").html();

        Account account = new Account(name, gender, imageUrl, description, friendsCount, followersCount, statusesCount);
        System.out.println(account.toString());
    }

}