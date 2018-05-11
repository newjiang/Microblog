package com.example.jiang.microblog.bean;

/**
 * Created by jiang on 2018/5/12.
 */

public class CommentsBean {
    private String created_at;
    private long id;
    private long rootid;
    private int floor_number;
    private String text;
    private int disable_reply;
    private int source_allowclick;
    private int source_type;
    private String source;
    private User user;
    private String mid;
    private String idstr;
    private Statuses status;
    private ReplyCommentBean reply_comment;
    private String reply_original_text;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRootid() {
        return rootid;
    }

    public void setRootid(long rootid) {
        this.rootid = rootid;
    }

    public int getFloor_number() {
        return floor_number;
    }

    public void setFloor_number(int floor_number) {
        this.floor_number = floor_number;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getDisable_reply() {
        return disable_reply;
    }

    public void setDisable_reply(int disable_reply) {
        this.disable_reply = disable_reply;
    }

    public int getSource_allowclick() {
        return source_allowclick;
    }

    public void setSource_allowclick(int source_allowclick) {
        this.source_allowclick = source_allowclick;
    }

    public int getSource_type() {
        return source_type;
    }

    public void setSource_type(int source_type) {
        this.source_type = source_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public ReplyCommentBean getReply_comment() {
        return reply_comment;
    }

    public void setReply_comment(ReplyCommentBean reply_comment) {
        this.reply_comment = reply_comment;
    }

    public String getReply_original_text() {
        return reply_original_text;
    }

    public void setReply_original_text(String reply_original_text) {
        this.reply_original_text = reply_original_text;
    }

    @Override
    public String toString() {
        return "CommentsBean{" +
                "created_at='" + created_at + '\'' +
                ", id=" + id +
                ", rootid=" + rootid +
                ", floor_number=" + floor_number +
                ", text='" + text + '\'' +
                ", disable_reply=" + disable_reply +
                ", source_allowclick=" + source_allowclick +
                ", source_type=" + source_type +
                ", source='" + source + '\'' +
                ", user=" + user +
                ", mid='" + mid + '\'' +
                ", idstr='" + idstr + '\'' +
                ", status=" + status +
                ", reply_comment=" + reply_comment +
                ", reply_original_text='" + reply_original_text + '\'' +
                '}';
    }
}
