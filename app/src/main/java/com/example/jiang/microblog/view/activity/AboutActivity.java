package com.example.jiang.microblog.view.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.SpannableStringBuilder;
import android.view.MenuItem;

import com.example.jiang.microblog.R;
import com.example.jiang.microblog.base.BaseActivity;
import com.example.jiang.microblog.utils.TextColorTools;
import com.example.jiang.microblog.widget.PrinterView;
import com.zhy.changeskin.SkinManager;

public class AboutActivity extends BaseActivity {
    private PrinterView printer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SkinManager.getInstance().register(this);
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("关于");
        }
        setContentView(R.layout.activity_about);
        final String text = "\t\t\t\t新健微博，感谢您的使用！\n" +
                "\n" +
                "\t\t\t\t首先，这是本人的大学毕业设计课题，由本人独自完成开发完成，难免会有些bug，希望您的谅解。\n" +
                "\n" +
                "\t\t\t\t然后，应用的授权是基于Oauth2.0实现的，不会侵犯和泄露您的信息，可以放心使用。关于功能，由于时间和技术的有限，本人已经尽力实现能应用的功能了，希望您喜欢。\n" +
                "\n" +
                "\t\t\t\t最后，如果您有任何疑问或建议，可以联系本人，本人无限欢迎~\n" +
                "\n" +
                "\t\t\t\tE-MAIL:voidjiang@foxmail.com";
        final String key = "voidjiang@foxmail.com";
        printer = (PrinterView) findViewById(R.id.about);
        printer.setPrintText(text, 90);
        printer.startPrint();
        printer.setOnStopPrintListener(new PrinterView.OnStopPrintListener() {
            @Override
            public void onStopPrintListener() {
                SpannableStringBuilder highlight = TextColorTools.highlight(text, key);
                printer.setText(highlight);
            }
        });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        printer.stopPrint();
        SkinManager.getInstance().unregister(this);
    }
}
