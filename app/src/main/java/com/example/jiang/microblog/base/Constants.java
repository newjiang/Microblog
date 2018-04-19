package com.example.jiang.microblog.base;

/**
 * Created by jiang on 2018/4/14.
 */

public interface Constants {

    //TODO 新浪微博APP_KEY
    public static final String APP_KEY = "1228872567";

    //TODO 回调页
    public static final String REDIRECT_URL = "http://www.sina.com";

    //TODO 应用权限
    public static final String SCOPE =
            "email,"
                    + "direct_messages_read,"
                    + "direct_messages_write,"
                    + "friendships_groups_read,"
                    + "friendships_groups_write,"
                    + "statuses_to_me_read,"
                    + "follow_app_official_microblog,"
                    + "invitation_write";

}