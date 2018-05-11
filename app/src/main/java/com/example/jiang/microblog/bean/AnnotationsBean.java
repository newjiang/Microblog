package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

public class AnnotationsBean {

    private String client_mblogid;
    private boolean mapi_request;

    public String getClient_mblogid() {
        return client_mblogid;
    }

    public void setClient_mblogid(String client_mblogid) {
        this.client_mblogid = client_mblogid;
    }

    public boolean isMapi_request() {
        return mapi_request;
    }

    public void setMapi_request(boolean mapi_request) {
        this.mapi_request = mapi_request;
    }

    @Override
    public String toString() {
        return "AnnotationsBean{" +
                "client_mblogid='" + client_mblogid + '\'' +
                ", mapi_request=" + mapi_request +
                '}';
    }
}