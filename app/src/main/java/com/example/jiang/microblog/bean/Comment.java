package com.example.jiang.microblog.bean;

import java.util.List;

/**
 * Created by jiang on 2018/4/19.
 */

public class Comment {

    private boolean hasvisible;
    private long previous_cursor;
    private long next_cursor;
    private int total_number;
    private int since_id;
    private int max_id;
    private Statuses status;
    private List<CommentsBean> comments;
    private List<?> marks;

    public boolean isHasvisible() {
        return hasvisible;
    }

    public void setHasvisible(boolean hasvisible) {
        this.hasvisible = hasvisible;
    }

    public long getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(long previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public long getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(long next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public int getSince_id() {
        return since_id;
    }

    public void setSince_id(int since_id) {
        this.since_id = since_id;
    }

    public int getMax_id() {
        return max_id;
    }

    public void setMax_id(int max_id) {
        this.max_id = max_id;
    }

    public Statuses getStatus() {
        return status;
    }

    public void setStatus(Statuses status) {
        this.status = status;
    }

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public List<?> getMarks() {
        return marks;
    }

    public void setMarks(List<?> marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "hasvisible=" + hasvisible +
                ", previous_cursor=" + previous_cursor +
                ", next_cursor=" + next_cursor +
                ", total_number=" + total_number +
                ", since_id=" + since_id +
                ", max_id=" + max_id +
                ", status=" + status +
                ", comments=" + comments +
                ", marks=" + marks +
                '}';
    }
}
