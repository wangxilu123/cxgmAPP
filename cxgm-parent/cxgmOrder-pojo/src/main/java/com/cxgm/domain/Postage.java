package com.cxgm.domain;

import java.math.BigDecimal;

public class Postage {
    private Integer id;

    private Integer shopId;

    private String shopName;

    private BigDecimal reduceMoney;

    private BigDecimal satisfyMoney;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public BigDecimal getReduceMoney() {
        return reduceMoney;
    }

    public void setReduceMoney(BigDecimal reduceMoney) {
        this.reduceMoney = reduceMoney;
    }

    public BigDecimal getSatisfyMoney() {
        return satisfyMoney;
    }

    public void setSatisfyMoney(BigDecimal satisfyMoney) {
        this.satisfyMoney = satisfyMoney;
    }
}