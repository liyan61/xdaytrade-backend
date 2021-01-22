package com.cirko.xdaytrade.entity.trade;

import com.cirko.xdaytrade.entity.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "t_order")
public class Order {
    @Id
    @JsonBackReference
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String orderNumber;                     // 订单编号
    private String orderName;                       // 订单名称9 7
    private Integer status;                         // 状态
    private Date createTime;                        // 创建时间
    private String body;                            // 订单描述
    private String spbillCreateIp;                  // 发起人IP
    private String attach;                          // 附件数据主要用于商户携带订单的自定义数据
    private Date payTime;                           // 付款时间
    private Double totalPrice;                      // 订单价格

    @ManyToOne
    @JsonIgnore
    private User user;                              // 关联客户

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSpbillCreateIp() {
        return spbillCreateIp;
    }

    public void setSpbillCreateIp(String spbillCreateIp) {
        this.spbillCreateIp = spbillCreateIp;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
