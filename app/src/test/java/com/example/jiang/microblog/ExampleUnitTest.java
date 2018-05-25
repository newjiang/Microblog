package com.example.jiang.microblog;

import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.bean.Statuses;
import com.example.jiang.microblog.json.HosJson;
import com.example.jiang.microblog.json.MicroblogJson;
import com.example.jiang.microblog.utils.CrawlerTools;
import com.example.jiang.microblog.utils.ListUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void test(){
        String time = "Tue Aug 30 20:08:33 +0800 2011";
        Date d = new Date(time);
        //TODO 修改显示时间格式 如2018-01-01 00：00
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        System.out.println(format.format(d));
    }
    @Test
    public void test1(){
        Gson gson = new Gson();
        List<Hot> hots = gson.fromJson(HosJson.JSON, new TypeToken<List<Hot>>(){}.getType());
        System.out.println(hots);
    }

    @Test
    public void test2() {
        Gson gson = new Gson();
        Microblog m1 = gson.fromJson(MicroblogJson.JSON, Microblog.class);
        Microblog m2 = gson.fromJson(MicroblogJson.JSON, Microblog.class);
        List<Statuses> old = m1.getStatuses();
        List<Statuses> xin = m2.getStatuses();
        xin.remove(2);
        xin.remove(4);
        xin.get(1).setMid("1111111111111111");
        xin.get(3).setMid("3333333333333333");
        xin.get(5).setMid("5555555555555555");
        System.out.println("添加前" + old.size());
        for (Statuses m : old) {
            System.out.print(m.getMid() + "\t");
        }
        System.out.println("");
        ListUtils.add(old, xin, true);
        System.out.println();
        System.out.println("添加后");
        for (Statuses m : old) {
            System.out.print(m.getMid() + "\t");
        }
    }
    @Test
    public void test4(){
        List<Hot> search = CrawlerTools.findTopSearch();
        for (Hot u : search) {
            System.out.println(u.toString());
        }
    }
    @Test
    public void test5(){
        List<String> list = new ArrayList<>();
        if (list.isEmpty()) {
            System.out.println("0000000");
        } else {
            System.out.printf("1111111");
        }
    }


    @Test
    public void test6(){
        String url = "#米粉福利委员会#曾经有份大奖摆在我眼前，而我差点错过！幸好我眼疾手快，喜大普奔！ http://t.cn/R3jplAS";
        if (url.contains("http://t.cn")) {
            int i = url.lastIndexOf("http://t.cn");
            String substring = url.substring(0, i);
            System.out.println(substring);
        }else {
            System.out.println(url);
        }

        if (url.contains("http://t.cn")) {
            int i = url.lastIndexOf("http://t.cn");
            String substring = url.substring(i, url.length());
            System.out.println(substring);
        }else {
            System.out.println(url);
        }

    }

    @Test
    public void test7(){
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("123");
        list.clear();
        if (list.isEmpty()) {
            System.out.println("00000");
        } else {
            System.out.printf(list.toString());
        }
    }

}