package com.example.jiang.microblog;

import com.example.jiang.microblog.bean.Hot;
import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.json.HosJson;
import com.example.jiang.microblog.json.MicroblogJson;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;

import java.text.SimpleDateFormat;
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
        List<Microblog.StatusesBean> old = m1.getStatuses();
        List<Microblog.StatusesBean> xin = m2.getStatuses();
        xin.remove(2);
        xin.remove(4);
        xin.get(3).setMid("12111111111111");
        System.out.println("添加前");
        for (Microblog.StatusesBean m : old) {
            System.out.print(m.getMid() + "\t");
        }
        for (int i = 0; i < xin.size(); i++) {
            int k = 0;
            int count = 0;
            for (int j = 0; j < old.size(); j++) {
                if (xin.get(i).getMid().equals(old.get(j).getMid())) {
                    old.remove(j);
                    old.add(j, xin.get(i));
                } else {
                    count = ++k;
                    if (count == old.size()) {
                        old.add(0, xin.get(i));
                    }
                }
            }
        }
        System.out.println();
        System.out.println("添加后");
        for (Microblog.StatusesBean m : old) {
            System.out.print(m.getMid() + "\t");
        }
    }

}