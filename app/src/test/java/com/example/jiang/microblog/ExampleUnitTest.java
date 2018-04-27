package com.example.jiang.microblog;

import com.example.jiang.microblog.bean.Account;
import com.example.jiang.microblog.bean.Weibo;
import com.example.jiang.microblog.utils.CrawlerTools;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(random.nextInt(10));
        }
    }
    @Test
    public void test2(){
        List<Weibo> hhh = CrawlerTools.findWeibo("hhh");
        System.out.println(hhh.toString());
        List<Account> accounts = CrawlerTools.findAccount("hhh");
        System.out.println(accounts.toString());
    }
}