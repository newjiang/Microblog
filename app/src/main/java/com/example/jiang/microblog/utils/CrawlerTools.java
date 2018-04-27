package com.example.jiang.microblog.utils;

import android.util.Log;

import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.bean.Weibo;
import com.example.jiang.microblog.test.Demo;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiang on 2018/4/26.
 */

public class  CrawlerTools {
    /**
     * 根据关键词查找微博
     *
     * @param key
     * @return List<Weibo>
     */
    public static List<Weibo> findWeibo(String key) {
        List<Weibo> weibos = new ArrayList<>();
        String html = "";
        try {
            Document doc = Jsoup.connect("http://s.weibo.com/weibo/" + key).get();
            Log.e("doc", doc.toString());
            Elements scripts = doc.select("script");
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String str = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String substring = str.substring(1, str.length() - 1);
                    if (substring.length() > html.length()) {
                        html = substring;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (html == null || html.equals("")) {
            Log.e("html", "空");
        } else {
            Gson gson = new Gson();
            Demo demo = gson.fromJson(html, Demo.class);
            Document doc = Jsoup.parse(demo.getHtml());
            Elements select = doc.select("div.feed_lists");
            Elements elements = select.select("div.WB_cardwrap");
            for (Element e : elements) {
                weibos.add(getWeibo(e));
            }
        }
        return weibos;
    }

    /**
     * 查找微博元素的数据
     * @param e
     * @return Weibo
     */
    private static Weibo getWeibo(Element e) {
        String mid = ""; //TODO 微博的mid
        String header = ""; //TODO 用户头像
        String name = ""; //TODO 用户名字
        String content = ""; //TODO 微博文字内容
        List<String> pictureList = new ArrayList<>(); //TODO 微博配图
        String time = ""; //TODO 发布时间
        String from = ""; //TODO 来源
        String like = ""; //TODO 点赞数
        String redirect = ""; //TODO 转发数
        String comment = ""; //TODO 评论数
        String retweetedUser = ""; //TODO 转发微博微博的作者
        String retweetedContent = ""; //TODO 转发微博文字内容
        List<String> retweetedPicture = new ArrayList<>(); //TODO 转发微博配图
        mid = e.select("div").get(1).attr("mid");
        header = e.getElementsByClass("face").select("a").select("img").attr("src");
        name = e.getElementsByClass("feed_content").select("a.name_txt").html();
        content = e.getElementsByClass("feed_content").select("p.comment_txt").html();
        Elements picUrls = e.getElementsByClass("feed_content").select("li.WB_pic");
        for (Element s : picUrls) {
            pictureList.add(s.select("img").attr("src"));
        }
        Elements feedContent = e.getElementsByClass("feed_content").select("div.comment_info");
        retweetedUser = feedContent.select("a.W_texta").html();
        retweetedContent = feedContent.select("p.comment_txt").html();
        Elements piclist = e.getElementsByClass("feed_content").select("div.comment_info").select("div.WB_media_wrap");
        Elements retweeted = piclist.select("ul.WB_media_a").select("li");
        for (Element s : retweeted) {
            retweetedPicture.add(s.select("img").attr("src"));
        }
        Elements sources = e.getElementsByClass("feed_from").select("a");
        for (int i = 0; i < sources.size(); i++) {
            if (i == 0) {
                time = e.getElementsByClass("feed_from").select("a").get(0).html();
            } else {
                from = e.getElementsByClass("feed_from").select("a").get(1).html();
            }
        }
        Elements action = e.getElementsByClass("feed_action").select("ul.feed_action_info");
        Elements li = action.select("li");
        for (int i = 0; i < li.size(); i++) {
            if (i == 1) {
                redirect = li.get(1).select("em").html();
            } else if (i == 2) {
                comment = li.get(2).select("em").html();
            } else if (i == 3) {
                like = li.get(3).select("em").html();
            }
        }
        return new Weibo(mid, header, name, content, pictureList, time, from, like, redirect, comment, retweetedUser, retweetedContent, retweetedPicture);
    }

    public static List<Account> findAccount(String key) {
        List<Account> accounts = new ArrayList<>();
        String html = "";
        try {
            Document doc = Jsoup.connect("http://s.weibo.com/user/" + key + "&Refer=weibo_user").get();
            Elements scripts = doc.select("script");
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String str = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String substring = str.substring(1, str.length() - 1);
                    if (substring.length() > html.length()) {
                        html = substring;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Demo demo = gson.fromJson(html, Demo.class);
        try {
            Document doc = Jsoup.parse(demo.getHtml());
            Elements elements = doc.select("div.list_person");
            for (Element account : elements) {
                accounts.add(getAccountInfo(account));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accounts;
    }

    private static Account getAccountInfo(Element element) {
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

        return new Account(name, gender, imageUrl, description, friendsCount, followersCount, statusesCount);
    }

}
