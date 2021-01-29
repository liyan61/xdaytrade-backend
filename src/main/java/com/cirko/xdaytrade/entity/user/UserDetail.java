package com.cirko.xdaytrade.entity.user;

import com.cirko.xdaytrade.entity.img.Img;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.Entity;
import javax.persistence.*;
import java.util.Date;

/**
 * 用户详情表
 */
@Entity
public class UserDetail {
    @Id
    @JsonBackReference
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private User user;

    /**
     * 昵称
     */
    private String nickName ;

    /**
     * 年龄
     */
    private int age = 0;

    /**
     * 生日
     */
    private Date birthday ;

    /**
     * 性别
     */
    private int sex;

    /**
     * 头像
     */
    @OneToOne
    private Img headerImg;

    /**
     * 个性签名
     */
    private String signature = "这个人很懒，什么都没写";

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Img getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(Img headerImg) {
        this.headerImg = headerImg;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
