package com.zhao.greendao.entity;

/**
 * Created by zhao on 2017/3/15.
 */

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private String password;
    private int score;
    private int test1;
    private int test2;
    @Transient
    private int tempUsageCount; // not persisted
    @Generated(hash = 1177396967)
    public User(Long id, String name, String password, int score, int test1,
            int test2) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.score = score;
        this.test1 = test1;
        this.test2 = test2;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getTest1() {
        return this.test1;
    }
    public void setTest1(int test1) {
        this.test1 = test1;
    }
    public int getTest2() {
        return this.test2;
    }
    public void setTest2(int test2) {
        this.test2 = test2;
    }
}