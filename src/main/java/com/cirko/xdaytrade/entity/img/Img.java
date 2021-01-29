package com.cirko.xdaytrade.entity.img;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

/**
 * 图片
 */
@Entity
public class Img {
    @Id
    @JsonBackReference
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 图片名称
     */
    private String name;

    /**
     * 图片地址
     */
    private String url;

    public Img() {}

    public Img(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
