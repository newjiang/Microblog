package com.example.jiang.microblog.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.bean.User;
import com.example.jiang.microblog.json.UserJson;
import com.google.gson.Gson;
import com.sina.weibo.sdk.auth.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;

import org.litepal.crud.DataSupport;

import java.util.List;

public class TestActivity extends AppCompatActivity {

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Oauth2AccessToken token = AccessTokenKeeper.readAccessToken(this);
        textView = (TextView) findViewById(R.id.test_view);
        User user = new Gson().fromJson(UserJson.JSON, User.class);
        user.save();
        List<User> users = DataSupport.where("idstr = ?", token.getUid()).find(User.class);
        Log.e("TestActivity", users.get(0).toString());
        textView.setText(users.get(0).getName());
//        textView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                AlertDialog.Builder dialog = new AlertDialog.Builder(TestActivity.this);
//                dialog.setMessage("删除该评论?");
//                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(TestActivity.this, "取消", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(TestActivity.this, "确定", Toast.LENGTH_SHORT).show();
//                    }
//                });
//                dialog.show();
//                return true;
//            }
//        });
    }


}
