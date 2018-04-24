package com.example.jiang.microblog.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.view.share.ShareActivity;
import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.share.WbShareCallback;
import com.sina.weibo.sdk.share.WbShareHandler;

public class TestShareActivity extends AppCompatActivity implements View.OnClickListener ,WbShareCallback {

    private AuthInfo mAuthInfo;

    private WbShareHandler shareHandler;

    private Button share_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_share);
    }
    @Override
    public void onClick(View v) {
        sendMultiMessage(true, true);
    }
    /**
     * 第三方应用发送请求消息到微博，唤起微博分享界面。
     */
    private void sendMultiMessage(boolean hasText, boolean hasImage) {
        WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        if (hasText) {
            Toast.makeText(TestShareActivity.this, "文字", Toast.LENGTH_SHORT).show();
            weiboMessage.textObject = getTextObj();
        }
        if (hasImage) {
            Toast.makeText(TestShareActivity.this, "图片", Toast.LENGTH_SHORT).show();
            weiboMessage.imageObject = getImageObj();
        }
        shareHandler.shareMessage(weiboMessage, false);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        shareHandler.doResultIntent(intent, this);
    }

    /**
     * 获取分享的文本模板。
     */
    private String getSharedText() {
        int formatId = R.string.weibosdk_demo_share_text_template;
        String format = getString(formatId);
        String text = format;
        text = "share mircoblog testing";
        return text;
    }

    /**
     * 创建文本消息对象。
     * @return 文本消息对象。
     */
    private TextObject getTextObj() {
        TextObject textObject = new TextObject();
        textObject.text = getSharedText();
        textObject.title = "xxxx";
        textObject.actionUrl = "http://www.baidu.com";
        return textObject;
    }

    /**
     * 创建图片消息对象。
     * @return 图片消息对象。
     */
    private ImageObject getImageObj() {
        ImageObject imageObject = new ImageObject();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo);
        int byteCount = bitmap.getByteCount();
        if (byteCount >= 2097152) {
            Log.e("byteCount", "大大大大");
        }
        imageObject.setImageObject(bitmap);
        boolean b = imageObject.checkArgs();
        if (b) {
            Log.e("真真真", "真真真");
        } else {
            Log.e("假假假", "假假假");
        }

        return imageObject;
    }

    @Override
    public void onWbShareSuccess() {
        Log.e("onWbShareSuccess", "分享成功");
    }

    @Override
    public void onWbShareCancel() {
        Log.e("onWbShareCancel", "取消分享");
    }

    @Override
    public void onWbShareFail() {
        Log.e("onWbShareFail", "分享失败");
    }
}
