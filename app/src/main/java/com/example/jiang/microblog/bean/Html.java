package com.example.jiang.microblog.bean;

import java.util.List;

/**
 * Created by jiang on 2018/4/24.
 */

public class Html {

    private String pid;
    private String html;
    private List<String> js;
    private List<String> css;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    @Override
    public String toString() {
        return "Html{" +
                "pid='" + pid + '\'' +
                ", html='" + html + '\'' +
                ", js=" + js +
                ", css=" + css +
                '}';
    }
}
