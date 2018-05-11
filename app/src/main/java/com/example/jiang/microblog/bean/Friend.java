package com.example.jiang.microblog.bean;

import java.util.List;

/**
 * Created by jiang on 2018/5/4.
 */

public class Friend {

    private int next_cursor;
    private int previous_cursor;
    private int total_number;
    private List<User> users;

    public int getNext_cursor() {
        return next_cursor;
    }

    public void setNext_cursor(int next_cursor) {
        this.next_cursor = next_cursor;
    }

    public int getPrevious_cursor() {
        return previous_cursor;
    }

    public void setPrevious_cursor(int previous_cursor) {
        this.previous_cursor = previous_cursor;
    }

    public int getTotal_number() {
        return total_number;
    }

    public void setTotal_number(int total_number) {
        this.total_number = total_number;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "next_cursor=" + next_cursor +
                ", previous_cursor=" + previous_cursor +
                ", total_number=" + total_number +
                ", users=" + users +
                '}';
    }
}
