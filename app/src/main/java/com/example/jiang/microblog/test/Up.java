package com.example.jiang.microblog.test;

import java.io.Serializable;

/**
 * Created by jiang on 2018/3/21.
 */

//用于测试的数据，减少访问
public class Up implements Serializable {
    public static final String JSON =
            "{\n" +
                    "    \"statuses\": [\n" +
                    "        {\n" +
                    "            \"created_at\": \"Tue Jan 16 11:50:58 +0800 2018\",\n" +
                    "            \"id\": 4196783653257743,\n" +
                    "            \"mid\": \"4196783653257743\",\n" +
                    "            \"idstr\": \"4196783653257743\",\n" +
                    "            \"can_edit\": false,\n" +
                    "            \"text\": \"测试测试 http://t.cn/ROvQOPS \u200B\",\n" +
                    "            \"textLength\": 28,\n" +
                    "            \"source_allowclick\": 1,\n" +
                    "            \"source_type\": 1,\n" +
                    "            \"source\": \"<a href=\\\"http://app.weibo.com/t/feed/503Oti\\\" rel=\\\"nofollow\\\">魅族 MX4</a>\",\n" +
                    "            \"favorited\": false,\n" +
                    "            \"truncated\": false,\n" +
                    "            \"in_reply_to_status_id\": \"\",\n" +
                    "            \"in_reply_to_user_id\": \"\",\n" +
                    "            \"in_reply_to_screen_name\": \"\",\n" +
                    "            \"pic_urls\": [\n" +
                    "                {\n" +
                    "                    \"thumbnail_pic\": \"http://wx3.sinaimg.cn/thumbnail/94a4cd03gy1fnib1hscrjj20zk0qotby.jpg\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"thumbnail_pic\": \"http://wx3.sinaimg.cn/thumbnail/94a4cd03gy1fnib1y5a5nj20hs0hsad7.jpg\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"thumbnail_pic\": \"http://wx4.sinaimg.cn/thumbnail/94a4cd03gy1fnib1yur6xj20hs0hsjvb.jpg\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"thumbnail_pic\": \"http://wx2.sinaimg.cn/thumbnail/94a4cd03gy1fnib214qkbj20qo0zktgo.jpg\"\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"thumbnail_pic\": \"http://wx3.sinaimg.cn/thumbnail/94a4cd03gy1fnib1hscrjj20zk0qotby.jpg\",\n" +
                    "            \"bmiddle_pic\": \"http://wx3.sinaimg.cn/bmiddle/94a4cd03gy1fnib1hscrjj20zk0qotby.jpg\",\n" +
                    "            \"original_pic\": \"http://wx3.sinaimg.cn/large/94a4cd03gy1fnib1hscrjj20zk0qotby.jpg\",\n" +
                    "            \"geo\": {\n" +
                    "                \"type\": \"Point\",\n" +
                    "                \"coordinates\": [\n" +
                    "                    23.14684,\n" +
                    "                    113.45759\n" +
                    "                ]\n" +
                    "            },\n" +
                    "            \"is_paid\": false,\n" +
                    "            \"mblog_vip_type\": 0,\n" +
                    "            \"user\": {\n" +
                    "                \"id\": 2493828355,\n" +
                    "                \"idstr\": \"2493828355\",\n" +
                    "                \"class\": 1,\n" +
                    "                \"screen_name\": \"new新健\",\n" +
                    "                \"name\": \"new新健\",\n" +
                    "                \"province\": \"44\",\n" +
                    "                \"city\": \"1\",\n" +
                    "                \"location\": \"广东 广州\",\n" +
                    "                \"description\": \"\uD83D\uDC7B\uD83D\uDC7B\uD83D\uDC7B\",\n" +
                    "                \"url\": \"\",\n" +
                    "                \"profile_image_url\": \"http://tvax4.sinaimg.cn/crop.0.0.1068.1068.50/94a4cd03ly8fn5hr4numoj20to0ton01.jpg\",\n" +
                    "                \"cover_image_phone\": \"http://ww1.sinaimg.cn/crop.0.0.640.640.640/549d0121tw1egm1kjly3jj20hs0hsq4f.jpg\",\n" +
                    "                \"profile_url\": \"u/2493828355\",\n" +
                    "                \"domain\": \"\",\n" +
                    "                \"weihao\": \"\",\n" +
                    "                \"gender\": \"m\",\n" +
                    "                \"followers_count\": 294,\n" +
                    "                \"friends_count\": 114,\n" +
                    "                \"pagefriends_count\": 0,\n" +
                    "                \"statuses_count\": 83,\n" +
                    "                \"favourites_count\": 0,\n" +
                    "                \"created_at\": \"Sun Nov 27 09:03:15 +0800 2011\",\n" +
                    "                \"following\": false,\n" +
                    "                \"allow_all_act_msg\": true,\n" +
                    "                \"geo_enabled\": true,\n" +
                    "                \"verified\": false,\n" +
                    "                \"verified_type\": -1,\n" +
                    "                \"remark\": \"\",\n" +
                    "                \"insecurity\": {\n" +
                    "                    \"sexual_content\": false\n" +
                    "                },\n" +
                    "                \"ptype\": 0,\n" +
                    "                \"allow_all_comment\": true,\n" +
                    "                \"avatar_large\": \"http://tvax4.sinaimg.cn/crop.0.0.1068.1068.180/94a4cd03ly8fn5hr4numoj20to0ton01.jpg\",\n" +
                    "                \"avatar_hd\": \"http://tvax4.sinaimg.cn/crop.0.0.1068.1068.1024/94a4cd03ly8fn5hr4numoj20to0ton01.jpg\",\n" +
                    "                \"verified_reason\": \"\",\n" +
                    "                \"verified_trade\": \"\",\n" +
                    "                \"verified_reason_url\": \"\",\n" +
                    "                \"verified_source\": \"\",\n" +
                    "                \"verified_source_url\": \"\",\n" +
                    "                \"follow_me\": false,\n" +
                    "                \"like\": false,\n" +
                    "                \"like_me\": false,\n" +
                    "                \"online_status\": 0,\n" +
                    "                \"bi_followers_count\": 97,\n" +
                    "                \"lang\": \"zh-cn\",\n" +
                    "                \"star\": 0,\n" +
                    "                \"mbtype\": 0,\n" +
                    "                \"mbrank\": 0,\n" +
                    "                \"block_word\": 0,\n" +
                    "                \"block_app\": 0,\n" +
                    "                \"credit_score\": 80,\n" +
                    "                \"user_ability\": 1024,\n" +
                    "                \"urank\": 21,\n" +
                    "                \"story_read_state\": -1,\n" +
                    "                \"vclub_member\": 0\n" +
                    "            },\n" +
                    "            \"annotations\": [\n" +
                    "                {\n" +
                    "                    \"place\": {\n" +
                    "                        \"lon\": 113.45759,\n" +
                    "                        \"poiid\": \"8008644011201000000\",\n" +
                    "                        \"title\": \"大沙街区\",\n" +
                    "                        \"type\": \"checkin\",\n" +
                    "                        \"lat\": 23.14684\n" +
                    "                    },\n" +
                    "                    \"client_mblogid\": \"8337509c-9d6b-4b24-b35b-e0cd9cdbddc3\"\n" +
                    "                },\n" +
                    "                {\n" +
                    "                    \"mapi_request\": true\n" +
                    "                }\n" +
                    "            ],\n" +
                    "            \"reposts_count\": 0,\n" +
                    "            \"comments_count\": 0,\n" +
                    "            \"attitudes_count\": 0,\n" +
                    "            \"pending_approval_count\": 0,\n" +
                    "            \"isLongText\": false,\n" +
                    "            \"mlevel\": 0,\n" +
                    "            \"visible\": {\n" +
                    "                \"type\": 0,\n" +
                    "                \"list_id\": 0\n" +
                    "            },\n" +
                    "            \"biz_ids\": [\n" +
                    "                100101\n" +
                    "            ],\n" +
                    "            \"biz_feature\": 4294967300,\n" +
                    "            \"hasActionTypeCard\": 0,\n" +
                    "            \"darwin_tags\": [],\n" +
                    "            \"hot_weibo_tags\": [],\n" +
                    "            \"text_tag_tips\": [],\n" +
                    "            \"rid\": \"0_0_1_2676249424161886151\",\n" +
                    "            \"userType\": 0,\n" +
                    "            \"more_info_type\": 0,\n" +
                    "            \"positive_recom_flag\": 0,\n" +
                    "            \"content_auth\": 0,\n" +
                    "            \"gif_ids\": \"\",\n" +
                    "            \"is_show_bulletin\": 2,\n" +
                    "            \"comment_manage_info\": {\n" +
                    "                \"comment_manage_button\": 1,\n" +
                    "                \"comment_permission_type\": 0,\n" +
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
