package com.example.jiang.microblog.test;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jiang.microblog.R;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.web_view);
        // 开启JavaScript支持
        webView.getSettings().setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "java_obj");

        // 设置WebView是否支持使用屏幕控件或手势进行缩放，默认是true，支持缩放
        webView.getSettings().setSupportZoom(true);

        // 设置WebView是否使用其内置的变焦机制，该机制集合屏幕缩放控件使用，默认是false，不使用内置变焦机制。
        webView.getSettings().setBuiltInZoomControls(true);

        // 设置是否开启DOM存储API权限，默认false，未开启，设置为true，WebView能够使用DOM storage API
        webView.getSettings().setDomStorageEnabled(true);

        // 触摸焦点起作用.如果不设置，则在点击网页文本输入框时，不能弹出软键盘及不响应其他的一些事件。
        webView.requestFocus();

        // 设置此属性,可任意比例缩放,设置webview推荐使用的窗口
        webView.getSettings().setUseWideViewPort(true);

        // 设置webview加载的页面的模式,缩放至屏幕的大小
        webView.getSettings().setLoadWithOverviewMode(true);

        // 加载链接
        webView.loadUrl("https://weibo.com/u/2493828355");

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // 在开始加载网页时会回调
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 拦截 url 跳转,在里边添加点击链接跳转或者操作
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // 在结束加载网页时会回调

                // 获取页面内容
                view.loadUrl("javascript:window.java_obj.showSource("
                        + "document.getElementsByTagName('html')[0].innerHTML);");

                // 获取解析<meta name="share-description" content="获取到的值">
                view.loadUrl("javascript:window.java_obj.showDescription("
                        + "document.querySelector('meta[name=\"share-description\"]').getAttribute('content')"
                        + ");");
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                // 加载错误的时候会回调，在其中可做错误处理，比如再请求加载一次，或者提示404的错误页面
                super.onReceivedError(view, errorCode, description, failingUrl);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {
                // 在每一次请求资源时，都会通过这个函数来回调
                return super.shouldInterceptRequest(view, request);
            }

        });
        
    }


    public final class InJavaScriptLocalObj
    {
        @JavascriptInterface
        public void showSource(String html) {
            System.out.println("====>html=" + html);
        }

        @JavascriptInterface
        public void showDescription(String str) {
            System.out.println("====>html=" + str);
        }
    }
}
