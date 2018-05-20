package com.example.jiang.microblog.utils;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiang on 2018/4/20.
 */

public class TextColorTools {

    public static final int COLOR = 0xFF007DE0;

    /**
     * 关键字高亮显示
     *
     * @param target 关键字
     * @param text   显示文字
     * @return spannable
     */
    public static SpannableStringBuilder highlight(String text, String target) {

        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        CharacterStyle span = null;

        Pattern p = Pattern.compile(target);
        Matcher m = p.matcher(text);
        while (m.find()) {
            span = new ForegroundColorSpan(COLOR);
            spannable.setSpan(span, m.start(), m.end(),
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannable;
    }

}
