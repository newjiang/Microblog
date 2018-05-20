package com.example.jiang.microblog.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jiang.microblog.R;

public class TestActivity extends AppCompatActivity {
    WebView webView;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        webView = (WebView) findViewById(R.id.web_view);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl("http://video.weibo.com/show?fid=1034:8e7a1ff0ce8464f5f9786961b08cc020");
        textView = (TextView) findViewById(R.id.test_view);

        SpannableString spanableInfo = new SpannableString("这是一个测试"+": "+"点击我");
        spanableInfo.setSpan(new Clickable(clickListener),8,11,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanableInfo);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private View.OnClickListener clickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            Toast.makeText(TestActivity.this, textView.getText(),Toast.LENGTH_SHORT).show();
        }
    };

    class Clickable extends ClickableSpan{
        private final View.OnClickListener mListener;

        public Clickable(View.OnClickListener l) {
            mListener = l;
        }
        @Override
        public void onClick(View v) {
            mListener.onClick(v);
        }
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.red));
        }
    }
}
