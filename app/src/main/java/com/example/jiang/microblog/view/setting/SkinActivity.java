package com.example.jiang.microblog.view.setting;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.Skin;
import com.example.jiang.microblog.view.setting.adapter.SkinAdapter;

import java.util.ArrayList;
import java.util.List;

public class SkinActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SkinAdapter adapter;

    private List<Skin> skins;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("点击确认皮肤");
        }
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.skin_recyclerview);
        adapter = new SkinAdapter(this, skins);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    private void initData() {
        skins = new ArrayList<>();
        skins.add(new Skin(R.color.yellow, "yellow"));
        skins.add(new Skin(R.color.orangered, "orangered"));
        skins.add(new Skin(R.color.aliceblue, "aliceblue"));
        skins.add(new Skin(R.color.fuchsia, "fuchsia"));
        skins.add(new Skin(R.color.violet, "violet"));
        skins.add(new Skin(R.color.saddlebrown, "saddlebrown"));
        skins.add(new Skin(R.color.iconCoverDark, "iconCoverDark"));
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
