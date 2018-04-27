package com.example.jiang.microblog.view.search.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.view.search.adapter.RecommendAdapter;

import java.util.ArrayList;
import java.util.List;

public class MoreActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecommendAdapter adapter;
    private List<String> recommends; //TODO 热门搜索

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);
        recommends = new ArrayList<>();
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.more_recyclerview);
        adapter = new RecommendAdapter(MoreActivity.this, recommends);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MoreActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    private void initData() {
        recommends.add("东风26型导弹列装火箭军");
        recommends.add("杨幂 刘海");
        recommends.add("张杰 她就是小孩");
        recommends.add("尤物唤新季");
        recommends.add("奶茶肚");
        recommends.add("整容院员工去整鼻");
        recommends.add("画出爸妈离婚全过程");
        recommends.add("金莎朗掉入下水道骨折");
        recommends.add("嗯哼吐槽霍思燕方形脸");
        recommends.add("李敖私生女回应");
        recommends.add("谢娜为杨迪庆生");

        recommends.add("考研英语老师激情讲课");
        recommends.add("饺子化妆");
        recommends.add("惠若琪 甜蜜合影");
        recommends.add("陈立农 100万粉丝福利");
        recommends.add("谢霆锋脚纹解锁");
        recommends.add("中国顾客巴黎购物遭围殴");
        recommends.add("快递新规");
        recommends.add("中国顾客巴黎购物遭围殴");
        recommends.add("ninepercent 见面会");
        recommends.add("王者荣耀老夫子新皮肤");
        recommends.add("当了39次伴娘仍单身");
    }
}
