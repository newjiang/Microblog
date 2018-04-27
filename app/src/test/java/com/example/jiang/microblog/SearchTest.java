package com.example.jiang.microblog;

import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.bean.Html;
import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println(doc.html());
            for (Element script : scripts) {
                if (script.html().contains("STK && STK.pageletM && STK.pageletM.view")) {
                    String str = script.html().replace("STK && STK.pageletM && STK.pageletM.view", "");
                    String substring = str.substring(1, str.length() - 1);
                    if (substring.length() > html.length()) {
                        html = substring;
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        Html h = gson.fromJson(html, Html.class);
        Document doc = Jsoup.parse(h.getHtml());
        Elements select = doc.select("div.feed_lists");
        Elements elements = select.select("div.WB_cardwrap");
        for (Element e : elements) {
            getWeibo(e);
        }
    }

    private void getWeibo(Element e) {
        String mid = ""; //TODO 微博的mid
        String header = "" ; //TODO 用户头像
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
//TODO mid
        mid = e.select("div").get(1).attr("mid");
        System.out.println("mid:" + mid);
//TODO 头像
        header = e.getElementsByClass("face").select("a").select("img").attr("src");
        System.out.println("头像：" + header);
//TODO 用户名字
        name = e.getElementsByClass("feed_content").select("a.name_txt").html();
        System.out.println("名字：" + name);

//TODO 微博文字内容
        content = e.getElementsByClass("feed_content").select("p.comment_txt").html();
        System.out.println("微博内容：" + content);
// TODO 微博配图
        Elements picUrls = e.getElementsByClass("feed_content").select("li.WB_pic");
        for (Element s : picUrls) {
            pictureList.add(s.select("img").attr("src"));
        }
        System.out.println("配图"+pictureList.toString());

//TODO 转发微博微博的作者
        Elements feedContent = e.getElementsByClass("feed_content").select("div.comment_info");
        retweetedUser = feedContent.select("a.W_texta").html();
        System.out.println("转发微博的作者：" + retweetedUser);
//TODO 转发微博微博的内容
        retweetedContent = feedContent.select("p.comment_txt").html();
        System.out.println("转发微博的作者：" + retweetedContent);
//TODO 转发微博微博的配图
        Elements piclist = e.getElementsByClass("feed_content").select("div.comment_info").select("div.WB_media_wrap");
        Elements retweeted = piclist.select("ul.WB_media_a").select("li");
        for (Element s : retweeted) {
            retweetedPicture.add(s.select("img").attr("src"));
        }
        System.out.println("转发微博的配图：" + retweetedPicture.toString());


// TODO 发布时间    来源
        Elements sources = e.getElementsByClass("feed_from").select("a");
        for (int i = 0; i < sources.size(); i++) {
            if (i == 0) {
                time = e.getElementsByClass("feed_from").select("a").get(0).html();
                System.out.println("时间：" + time);
            } else {
                from = e.getElementsByClass("feed_from").select("a").get(1).html();
                System.out.println("来源：" + from);
            }
        }
////TODO 点赞数 转发数 评论数
        Elements action = e.getElementsByClass("feed_action").select("ul.feed_action_info");
        Elements li = action.select("li");
        for (int i = 0; i < li.size(); i++) {
            if (i == 1) {
                redirect = li.get(1).select("em").html();
                System.out.println("转发"+redirect);
            } else if (i == 2) {
                comment = li.get(2).select("em").html();
                System.out.println("评论"+comment);
            } else if (i == 3) {
                like = li.get(3).select("em").html();
                System.out.println("点赞" + like);
            }
        }
        System.out.println("++++++++++++++++++++++++++++++++++++++++++");
    }

    public void testFindUser() {
        String html = "";
        try {
            Document doc = Jsoup.connect("http://s.weibo.com/user/hhhhh&Refer=weibo_user").get();
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
        Html h = gson.fromJson(html, Html.class);
        try {
            Document doc = Jsoup.parse(h.getHtml());
            Elements elements = doc.select("div.list_person");
            for (Element account : elements) {
                getAccountInfo(account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getAccountInfo(Element element) {
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


    @Test
    public void findTopSearch(){
        List<Hot> hots = new ArrayList<>();
        String html = "";
        try {
            Document doc = Jsoup.connect("http://s.weibo.com/top/summary?cate=realtimehot").get();
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
                String title = e.select("td.td_02").select("div.rank_content").select("p.star_name").select("a").html();
                String hot = e.select("td.td_02").select("div.rank_content").select("p.star_name").select("i.icon_txt").html();
                String count = e.select("td.td_03").select("p.star_num").select("span").html();
                hots.add(new Hot(title, hot, count));
            }

            String toJson = new Gson().toJson(hots);
            System.out.println(toJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}