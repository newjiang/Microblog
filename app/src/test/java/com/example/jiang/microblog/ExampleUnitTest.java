package com.example.jiang.microblog;

import com.example.jiang.microblog.bean.Microblog;
import com.example.jiang.microblog.json.MicroblogJson;
import com.google.gson.Gson;

import org.junit.Test;

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
        Gson gson = new Gson();
        Microblog microblog = gson.fromJson(MicroblogJson.JSON, Microblog.class);
        Microblog.StatusesBean bean = microblog.getStatuses().get(0);
        Microblog.StatusesBean.UserBean user = bean.getUser();
        String remark = user.getRemark();

        System.out.println(remark.equals(""));

    }
}