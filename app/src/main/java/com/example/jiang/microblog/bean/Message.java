package com.example.jiang.microblog.bean;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by jiang on 2018/4/28.
 */

public class Message extends DataSupport implements Serializable {

    private int status;      //TODO 未读微博
    private int follower;   //TODO 新粉丝
    private int cmt;         //TODO 新评论
    private int mention_status;     //TODO 新提及我的微博数
    private int mention_cmt;        //TODO 新提及我的评论数
    private int dm;
    private int chat_group_pc;
    private int chat_group_client;
    private int group;
    private int notice;
    private int invite;
    private int badge;
    private int photo;
    private int all_mention_status;
    private int attention_mention_status;
    private int all_mention_cmt;
    private int attention_mention_cmt;
    private int all_cmt;
    private int attention_cmt;
    private int all_follower;
    private int attention_follower;
    private int page_friends_to_me;
    private int chat_group_notice;
    private int hot_status;
    private int chat_group_total;
    private int message_flow_aggregate;
    private int message_flow_unaggregate;
    private int voip;
    private int message_flow_agg_at;
    private int message_flow_agg_repost;
    private int message_flow_agg_comment;
    private int message_flow_agg_attitude;
    private int pc_viedo;
    private int status_24unread;
    private int message_flow_aggr_wild_card;
    private int message_flow_unaggr_wild_card;
    private int fans_group_unread;
    private int message_flow_follow;
    private int message_flow_unfollow;
    private int likeuser;
    private int addfriends;
    private int message_flow_attitude_unread;
    private int message_flow_follow_attitude_unread;
    private int double_flow;

    public Message() {
    }

    public Message(int status, int follower, int cmt, int mention_cmt, int mention_status) {
        this.status = status;
        this.follower = follower;
        this.cmt = cmt;
        this.mention_cmt = mention_cmt;
        this.mention_status = mention_status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFollower() {
        return follower;
    }

    public void setFollower(int follower) {
        this.follower = follower;
    }

    public int getCmt() {
        return cmt;
    }

    public void setCmt(int cmt) {
        this.cmt = cmt;
    }

    public int getDm() {
        return dm;
    }

    public void setDm(int dm) {
        this.dm = dm;
    }

    public int getChat_group_pc() {
        return chat_group_pc;
    }

    public void setChat_group_pc(int chat_group_pc) {
        this.chat_group_pc = chat_group_pc;
    }

    public int getChat_group_client() {
        return chat_group_client;
    }

    public void setChat_group_client(int chat_group_client) {
        this.chat_group_client = chat_group_client;
    }

    public int getMention_status() {
        return mention_status;
    }

    public void setMention_status(int mention_status) {
        this.mention_status = mention_status;
    }

    public int getMention_cmt() {
        return mention_cmt;
    }

    public void setMention_cmt(int mention_cmt) {
        this.mention_cmt = mention_cmt;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getNotice() {
        return notice;
    }

    public void setNotice(int notice) {
        this.notice = notice;
    }

    public int getInvite() {
        return invite;
    }

    public void setInvite(int invite) {
        this.invite = invite;
    }

    public int getBadge() {
        return badge;
    }

    public void setBadge(int badge) {
        this.badge = badge;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getAll_mention_status() {
        return all_mention_status;
    }

    public void setAll_mention_status(int all_mention_status) {
        this.all_mention_status = all_mention_status;
    }

    public int getAttention_mention_status() {
        return attention_mention_status;
    }

    public void setAttention_mention_status(int attention_mention_status) {
        this.attention_mention_status = attention_mention_status;
    }

    public int getAll_mention_cmt() {
        return all_mention_cmt;
    }

    public void setAll_mention_cmt(int all_mention_cmt) {
        this.all_mention_cmt = all_mention_cmt;
    }

    public int getAttention_mention_cmt() {
        return attention_mention_cmt;
    }

    public void setAttention_mention_cmt(int attention_mention_cmt) {
        this.attention_mention_cmt = attention_mention_cmt;
    }

    public int getAll_cmt() {
        return all_cmt;
    }

    public void setAll_cmt(int all_cmt) {
        this.all_cmt = all_cmt;
    }

    public int getAttention_cmt() {
        return attention_cmt;
    }

    public void setAttention_cmt(int attention_cmt) {
        this.attention_cmt = attention_cmt;
    }

    public int getAll_follower() {
        return all_follower;
    }

    public void setAll_follower(int all_follower) {
        this.all_follower = all_follower;
    }

    public int getAttention_follower() {
        return attention_follower;
    }

    public void setAttention_follower(int attention_follower) {
        this.attention_follower = attention_follower;
    }

    public int getPage_friends_to_me() {
        return page_friends_to_me;
    }

    public void setPage_friends_to_me(int page_friends_to_me) {
        this.page_friends_to_me = page_friends_to_me;
    }

    public int getChat_group_notice() {
        return chat_group_notice;
    }

    public void setChat_group_notice(int chat_group_notice) {
        this.chat_group_notice = chat_group_notice;
    }

    public int getHot_status() {
        return hot_status;
    }

    public void setHot_status(int hot_status) {
        this.hot_status = hot_status;
    }

    public int getChat_group_total() {
        return chat_group_total;
    }

    public void setChat_group_total(int chat_group_total) {
        this.chat_group_total = chat_group_total;
    }

    public int getMessage_flow_aggregate() {
        return message_flow_aggregate;
    }

    public void setMessage_flow_aggregate(int message_flow_aggregate) {
        this.message_flow_aggregate = message_flow_aggregate;
    }

    public int getMessage_flow_unaggregate() {
        return message_flow_unaggregate;
    }

    public void setMessage_flow_unaggregate(int message_flow_unaggregate) {
        this.message_flow_unaggregate = message_flow_unaggregate;
    }

    public int getVoip() {
        return voip;
    }

    public void setVoip(int voip) {
        this.voip = voip;
    }

    public int getMessage_flow_agg_at() {
        return message_flow_agg_at;
    }

    public void setMessage_flow_agg_at(int message_flow_agg_at) {
        this.message_flow_agg_at = message_flow_agg_at;
    }

    public int getMessage_flow_agg_repost() {
        return message_flow_agg_repost;
    }

    public void setMessage_flow_agg_repost(int message_flow_agg_repost) {
        this.message_flow_agg_repost = message_flow_agg_repost;
    }

    public int getMessage_flow_agg_comment() {
        return message_flow_agg_comment;
    }

    public void setMessage_flow_agg_comment(int message_flow_agg_comment) {
        this.message_flow_agg_comment = message_flow_agg_comment;
    }

    public int getMessage_flow_agg_attitude() {
        return message_flow_agg_attitude;
    }

    public void setMessage_flow_agg_attitude(int message_flow_agg_attitude) {
        this.message_flow_agg_attitude = message_flow_agg_attitude;
    }

    public int getPc_viedo() {
        return pc_viedo;
    }

    public void setPc_viedo(int pc_viedo) {
        this.pc_viedo = pc_viedo;
    }

    public int getStatus_24unread() {
        return status_24unread;
    }

    public void setStatus_24unread(int status_24unread) {
        this.status_24unread = status_24unread;
    }

    public int getMessage_flow_aggr_wild_card() {
        return message_flow_aggr_wild_card;
    }

    public void setMessage_flow_aggr_wild_card(int message_flow_aggr_wild_card) {
        this.message_flow_aggr_wild_card = message_flow_aggr_wild_card;
    }

    public int getMessage_flow_unaggr_wild_card() {
        return message_flow_unaggr_wild_card;
    }

    public void setMessage_flow_unaggr_wild_card(int message_flow_unaggr_wild_card) {
        this.message_flow_unaggr_wild_card = message_flow_unaggr_wild_card;
    }

    public int getFans_group_unread() {
        return fans_group_unread;
    }

    public void setFans_group_unread(int fans_group_unread) {
        this.fans_group_unread = fans_group_unread;
    }

    public int getMessage_flow_follow() {
        return message_flow_follow;
    }

    public void setMessage_flow_follow(int message_flow_follow) {
        this.message_flow_follow = message_flow_follow;
    }

    public int getMessage_flow_unfollow() {
        return message_flow_unfollow;
    }

    public void setMessage_flow_unfollow(int message_flow_unfollow) {
        this.message_flow_unfollow = message_flow_unfollow;
    }

    public int getLikeuser() {
        return likeuser;
    }

    public void setLikeuser(int likeuser) {
        this.likeuser = likeuser;
    }

    public int getAddfriends() {
        return addfriends;
    }

    public void setAddfriends(int addfriends) {
        this.addfriends = addfriends;
    }

    public int getMessage_flow_attitude_unread() {
        return message_flow_attitude_unread;
    }

    public void setMessage_flow_attitude_unread(int message_flow_attitude_unread) {
        this.message_flow_attitude_unread = message_flow_attitude_unread;
    }

    public int getMessage_flow_follow_attitude_unread() {
        return message_flow_follow_attitude_unread;
    }

    public void setMessage_flow_follow_attitude_unread(int message_flow_follow_attitude_unread) {
        this.message_flow_follow_attitude_unread = message_flow_follow_attitude_unread;
    }

    public int getDouble_flow() {
        return double_flow;
    }

    public void setDouble_flow(int double_flow) {
        this.double_flow = double_flow;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status=" + status +
                ", follower=" + follower +
                ", cmt=" + cmt +
                ", dm=" + dm +
                ", chat_group_pc=" + chat_group_pc +
                ", chat_group_client=" + chat_group_client +
                ", mention_status=" + mention_status +
                ", mention_cmt=" + mention_cmt +
                ", group=" + group +
                ", notice=" + notice +
                ", invite=" + invite +
                ", badge=" + badge +
                ", photo=" + photo +
                ", all_mention_status=" + all_mention_status +
                ", attention_mention_status=" + attention_mention_status +
                ", all_mention_cmt=" + all_mention_cmt +
                ", attention_mention_cmt=" + attention_mention_cmt +
                ", all_cmt=" + all_cmt +
                ", attention_cmt=" + attention_cmt +
                ", all_follower=" + all_follower +
                ", attention_follower=" + attention_follower +
                ", page_friends_to_me=" + page_friends_to_me +
                ", chat_group_notice=" + chat_group_notice +
                ", hot_status=" + hot_status +
                ", chat_group_total=" + chat_group_total +
                ", message_flow_aggregate=" + message_flow_aggregate +
                ", message_flow_unaggregate=" + message_flow_unaggregate +
                ", voip=" + voip +
                ", message_flow_agg_at=" + message_flow_agg_at +
                ", message_flow_agg_repost=" + message_flow_agg_repost +
                ", message_flow_agg_comment=" + message_flow_agg_comment +
                ", message_flow_agg_attitude=" + message_flow_agg_attitude +
                ", pc_viedo=" + pc_viedo +
                ", status_24unread=" + status_24unread +
                ", message_flow_aggr_wild_card=" + message_flow_aggr_wild_card +
                ", message_flow_unaggr_wild_card=" + message_flow_unaggr_wild_card +
                ", fans_group_unread=" + fans_group_unread +
                ", message_flow_follow=" + message_flow_follow +
                ", message_flow_unfollow=" + message_flow_unfollow +
                ", likeuser=" + likeuser +
                ", addfriends=" + addfriends +
                ", message_flow_attitude_unread=" + message_flow_attitude_unread +
                ", message_flow_follow_attitude_unread=" + message_flow_follow_attitude_unread +
                ", double_flow=" + double_flow +
                '}';
    }
}
