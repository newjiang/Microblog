package com.example.jiang.microblog;

import com.example.jiang.microblog.json.Up;
import com.example.jiang.microblog.utils.TimeFormat;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    public void testTime(){
        Date d = new Date("Sun Apr 15 19:35:46 +0800 2011");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        String f = TimeFormat.format(d);
        if (f.equals(TimeFormat.FLAG)) {
            String format1 = format.format(d);
            System.out.println(format1);
        } else {
            System.out.println(f);
        }
    }

    @Test
    public void jsonTest() {
        System.out.println(Up.JSON);
    }
//    @Test
//    public void stringTest(String str){
//        int start = str.indexOf('@');
//        int end = str.indexOf(':');
//        String key = str.substring(start, end);
//        System.out.println(key);
//    }

}