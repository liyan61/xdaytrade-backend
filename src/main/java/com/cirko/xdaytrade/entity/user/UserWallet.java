package com.cirko.xdaytrade.entity.user;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * 用户钱包表
 */
@Entity
public class UserWallet {
    @Id
    @JsonBackReference
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JsonBackReference
    private User user;

    /**
     * 余额
     */
    private Double balance = 0.0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
