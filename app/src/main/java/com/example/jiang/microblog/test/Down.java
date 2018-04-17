package com.example.jiang.microblog.test;

import java.io.Serializable;

/**
 * Created by jiang on 2018/3/21.
 */
//用于测试的数据，减少访问
public class Down implements Serializable {
    public static final String JSON =
            "{\n" +
                    "    \"statuses\": [\n" +
                    "        {\n" +
                    "            \"created_at\": \"Tue Jan 16 10:44:03 +0800 2018\",\n" +
                    "            \"id\": 4196766813730574,\n" +
                    "            \"mid\": \"4196766813730574\",\n" +
                    "            \"idstr\": \"4196766813730574\",\n" +
                    "            \"can_edit\": false,\n" +
                    "            \"text\": \"中国城市地铁建设速度.GIF \u200B\",\n" +
                    "            \"textLength\": 24,\n" +
                    "            \"source_allowclick\": 0,\n" +
                    "            \"source_type\": 1,\n" +
                    "            \"source\": \"<a href=\\\"http://app.weibo.com/t/feed/6vtZb0\\\" rel=\\\"nofollow\\\">微博 weibo.com</a>\",\n" +
                    "            \"favorited\": false,\n" +
                    "            \"truncated\": false,\n" +
                    "            \"in_reply_to_status_id\": \"\",\n" +
                    "            \"in_reply_to_user_id\": \"\",\n" +
                    "            \"in_reply_to_screen_name\": \"\",\n" +
                    "            \"pic_urls\": [\n" +
                    "                {\n" +
                    "                    \"thumbnail_pic\": \"http://wx1.sinaimg.cn/thumbnail/eccdc21dgy1fnhp4kekplg20ku0o1tip.gif\"\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"thumbnail_pic\": \"http://wx1.sinaimg.cn/thumbnail/eccdc21dgy1fnhp4kekplg20ku0o1tip.gif\",\n" +
                    "            \"bmiddle_pic\": \"http://wx1.sinaimg.cn/bmiddle/eccdc21dgy1fnhp4kekplg20ku0o1tip.gif\",\n" +
                    "            \"original_pic\": \"http://wx1.sinaimg.cn/large/eccdc21dgy1fnhp4kekplg20ku0o1tip.gif\",\n" +
                    "            \"geo\": null,\n" +
                    "            \"is_paid\": false,\n" +
                    "            \"mblog_vip_type\": 0,\n" +
                    "            \"user\": {\n" +
                    "                \"id\": 3972907549,\n" +
                    "                \"idstr\": \"3972907549\",\n" +
                    "                \"class\": 1,\n" +
                    "                \"screen_name\": \"笔戈科技\",\n" +
                    "                \"name\": \"笔戈科技\",\n" +
                    "                \"province\": \"44\",\n" +
                    "                \"city\": \"4\",\n" +
                    "                \"location\": \"广东 珠海\",\n" +
                    "                \"description\": \"魅族旗下笔戈科技，以笔为戈，刺破这世界的黑暗。主观、不独立、第一方。\",\n" +
                    "                \"url\": \"http://www.bigertech.com\",\n" +
                    "                \"profile_image_url\": \"http://tva1.sinaimg.cn/crop.0.0.800.800.50/eccdc21djw8f3i9dvdfpij20m80m80tu.jpg\",\n" +
                    "                \"cover_image\": \"http://ww1.sinaimg.cn/crop.0.0.920.300/eccdc21dgw1f1cw8pzndlj20pk08cdgz.jpg\",\n" +
                    "                \"cover_image_phone\": \"http://ww3.sinaimg.cn/crop.0.0.640.640.640/eccdc21dgw1f1bked0sa3j20u00u0gqd.jpg\",\n" +
                    "                \"profile_url\": \"bigertech\",\n" +
                    "                \"domain\": \"bigertech\",\n" +
                    "                \"weihao\": \"\",\n" +
                    "                \"gender\": \"m\",\n" +
                    "                \"followers_count\": 1976402,\n" +
                    "                \"friends_count\": 1587,\n" +
                    "                \"pagefriends_count\": 7,\n" +
                    "                \"statuses_count\": 3128,\n" +
                    "                \"favourites_count\": 119,\n" +
                    "                \"created_at\": \"Fri Mar 07 17:18:36 +0800 2014\",\n" +
                    "                \"following\": true,\n" +
                    "                \"allow_all_act_msg\": false,\n" +
                    "                \"geo_enabled\": false,\n" +
                    "                \"verified\": true,\n" +
                    "                \"verified_type\": 5,\n" +
                    "                \"remark\": \"\",\n" +
                    "                \"insecurity\": {\n" +
                    "                    \"sexual_content\": false\n" +
                    "                },\n" +
                    "                \"ptype\": 0,\n" +
                    "                \"allow_all_comment\": true,\n" +
                    "                \"avatar_large\": \"http://tva1.sinaimg.cn/crop.0.0.800.800.180/eccdc21djw8f3i9dvdfpij20m80m80tu.jpg\",\n" +
                    "                \"avatar_hd\": \"http://tva1.sinaimg.cn/crop.0.0.800.800.1024/eccdc21djw8f3i9dvdfpij20m80m80tu.jpg\",\n" +
                    "                \"verified_reason\": \"魅族旗下笔戈科技\",\n" +
                    "                \"verified_trade\": \"\",\n" +
                    "                \"verified_reason_url\": \"\",\n" +
                    "                \"verified_source\": \"\",\n" +
                    "                \"verified_source_url\": \"\",\n" +
                    "                \"verified_state\": 0,\n" +
                    "                \"verified_level\": 3,\n" +
                    "                \"verified_type_ext\": 0,\n" +
                    "                \"pay_remind\": 0,\n" +
                    "                \"pay_date\": \"\",\n" +
                    "                \"has_service_tel\": false,\n" +
                    "                \"verified_reason_modified\": \"魅族旗下笔戈科技\",\n" +
                    "                \"verified_contact_name\": \"\",\n" +
                    "                \"verified_contact_email\": \"\",\n" +
                    "                \"verified_contact_mobile\": \"\",\n" +
                    "                \"follow_me\": false,\n" +
                    "                \"like\": false,\n" +
                    "                \"like_me\": false,\n" +
                    "                \"online_status\": 0,\n" +
                    "                \"bi_followers_count\": 1074,\n" +
                    "                \"lang\": \"zh-cn\",\n" +
                    "                \"star\": 0,\n" +
                    "                \"mbtype\": 12,\n" +
                    "                \"mbrank\": 3,\n" +
                    "                \"block_word\": 0,\n" +
                    "                \"block_app\": 1,\n" +
                    "                \"credit_score\": 80,\n" +
                    "                \"user_ability\": 772,\n" +
                    "                \"urank\": 35,\n" +
                    "                \"story_read_state\": -1,\n" +
                    "                \"vclub_member\": 0\n" +
                    "            },\n" +
                    "            \"reposts_count\": 36,\n" +
                    "            \"comments_count\": 26,\n" +
                    "            \"attitudes_count\": 87,\n" +
                    "            \"pending_approval_count\": 0,\n" +
                    "            \"isLongText\": false,\n" +
                    "            \"mlevel\": 0,\n" +
                    "            \"visible\": {\n" +
                    "                \"type\": 0,\n" +
                    "                \"list_id\": 0\n" +
                    "            },\n" +
                    "            \"biz_feature\": 0,\n" +
                    "            \"hasActionTypeCard\": 0,\n" +
                    "            \"darwin_tags\": [],\n" +
                    "            \"hot_weibo_tags\": [],\n" +
                    "            \"text_tag_tips\": [],\n" +
                    "            \"rid\": \"1_0_1_2676249424161886151\",\n" +
                    "            \"userType\": 0,\n" +
                    "            \"more_info_type\": 0,\n" +
                    "            \"positive_recom_flag\": 0,\n" +
                    "            \"content_auth\": 0,\n" +
                    "            \"gif_ids\": \"eccdc21dgy1fnhp4kekplg20ku0o1tip|001gQY4Tlx07hpPppLZS010402000Mxv0k01|1022:231128879dc150630b114190a310e632b5f02e\",\n" +
                    "            \"is_show_bulletin\": 2,\n" +
                    "            \"comment_manage_info\": {\n" +
                    "                \"comment_permission_type\": -1,\n" +
                    "                \"approval_comment_type\": 0\n" +
                    "            }\n" +
                    "        }\n" +
                    "    ],\n" +
                    "    \"advertises\": [],\n" +
                    "    \"ad\": [],\n" +
                    "    \"hasvisible\": false,\n" +
                    "    \"previous_cursor\": 0,\n" +
                    "    \"next_cursor\": 4195825120240295,\n" +
                    "    \"total_number\": 150,\n" +
                    "    \"interval\": 2000,\n" +
                    "    \"uve_blank\": -1,\n" +
                    "    \"since_id\": 4196783653257743,\n" +
                    "    \"max_id\": 4195825120240295,\n" +
                    "    \"has_unread\": 0\n" +
                    "}";
}
