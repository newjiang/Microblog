package com.example.jiang.microblog.utils;

import android.util.Log;

import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.bean.Html;
import com.example.jiang.microblog.bean.Weibo;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiang on 2018//26.
 */

public class CrawlerTools {

    private static final String WEIBO_URL = "http://s.weibo.com/weibo/";
    private static final String USER_URL = "http://s.weibo.com/user/";
    private static final String HOT_URL = "http://s.weibo.com/top/summary?cate=realtimehot";

    /**
     * 根据关键词查找微博
     *
     * @param key
     * @return List<Weibo>
     */
    public static List<Weibo> findWeibo(String key) {
        List<Weibo> weibos = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        String weibo = "";//TODO 微博集合的html
        Response response;
        Request request = new Request.Builder().url(WEIBO_URL + key).build();
        try {
            response = client.newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            Elements scripts = doc.select("script");
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String str = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String substring = str.substring(1, str.length() - 1);
                    if (substring.length() > weibo.length()) {
                        weibo = substring;
                    }
                }
            }
            if (weibo == null || weibo.equals("")) {
                Log.e("html", "空");
            } else {
                Gson gson = new Gson();
                Html demo = gson.fromJson(weibo, Html.class);
                Elements es = Jsoup.parse(demo.getHtml()).select("div.feed_lists").select("div.WB_cardwrap");
                for (Element e : es) {
                    weibos.add(getWeibo(e));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weibos;
    }

    /**
     * 查找微博元素的数据
     *
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


    /**
     * 根据关键字查询用户
     *
     * @param key
     * @return
     */
    public static List<Account> findUser(String key) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(USER_URL + key).build();
        List<Account> accounts = new ArrayList<>();
        Response response;
        String user = "";
        try {
            response = client.newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            Elements scripts = doc.select("script");
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String s = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String sub = s.substring(1, s.length() - 1);
                    if (sub.length() > user.length()) {
                        user = sub;
                    }
                }
            }
            Gson gson = new Gson();
            Html h = gson.fromJson(user, Html.class);
            Document d = Jsoup.parse(h.getHtml());
            Elements es = d.select("div.list_person");
            for (Element e : es) {
                accounts.add(getAccountInfo(e));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    /**
     * 查找用户元素的数据
     *
     * @param e
     * @return
     */
    private static Account getAccountInfo(Element e) {
        String imageUrl = e.getElementsByClass("person_pic").select("img").attr("src");

        String name = e.getElementsByClass("person_name")
                .select("a").get(0).getElementsByClass("W_texta").html();

        String gender = "";

        String female = e.getElementsByClass("person_addr")
                .select("span").get(0).getElementsByClass("female").attr("title");
        String male = e.getElementsByClass("person_addr")
                .select("span").get(0).getElementsByClass("male").attr("title");
        if (male.equals("")) {
            gender = female;
        } else {
            gender = male;
        }

        String description = e.getElementsByClass("person_card").html();

        String friendsCount = e.getElementsByClass("person_num")
                .select("a").get(0).getElementsByClass("W_linkb").html();

        String followersCount = e.getElementsByClass("person_num")
                .select("a").get(1).getElementsByClass("W_linkb").html();

        String statusesCount = e.getElementsByClass("person_num")
                .select("a").get(2).getElementsByClass("W_linkb").html();

        return new Account(name, gender, imageUrl, description, friendsCount, followersCount, statusesCount);
    }


    /**
     * 搜索热搜
     *
     * @return
     */
    public static List<Hot> findTopSearch(){
        List<Hot> hots = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(HOT_URL).build();
        Response response;
        String html = "";
        try {
            response = client.newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            Elements scripts = doc.select("script");
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String s = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String sub = s.substring(1, s.length() - 1);
                    if (sub.length() > html.length()) {
                        html = sub;
                    }
                }
            }
            Gson gson = new Gson();
            Html h = gson.fromJson(html, Html.class);
            Document d = Jsoup.parse(h.getHtml());
            Elements tbody = d.select("tbody").select("tr");
            for (Element e : tbody) {
                //TODO 标题
                String title = e.select("td.td_02").select("div.rank_content").select("p.star_name").select("a").html();
                //TODO 热度程度
                String hot = e.select("td.td_02").select("div.rank_content").select("p.star_name").select("i.icon_txt").html();
                //TODO 搜索次数
                String count = e.select("td.td_03").select("p.star_num").select("span").html();
                hots.add(new Hot(title, hot, count));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hots;
    }
}
