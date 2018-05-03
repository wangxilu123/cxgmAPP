package com.cxgm.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Promotion {
    private Long id;

    private String name;

    private String title;

    private Date beginDate;

    private Date endDate;

    private Integer minimumQuantity;

    private Integer maximumQuantity;

    private BigDecimal minimumPrice;

    private BigDecimal maximumPrice;

    private String priceExpression;

    private Boolean isFreeShipping;

    private Boolean isCouponAllowed;

    private Integer orders;

    private Date creationDate;

    private String introduction;
    
    private Integer shopId;
    
    private List<Coupon> couponList;
    
    public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getMinimumQuantity() {
        return minimumQuantity;
    }

    public void setMinimumQuantity(Integer minimumQuantity) {
        this.minimumQuantity = minimumQuantity;
    }

    public Integer getMaximumQuantity() {
        return maximumQuantity;
    }

    public void setMaximumQuantity(Integer maximumQuantity) {
        this.maximumQuantity = maximumQuantity;
    }

    public BigDecimal getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(BigDecimal minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public BigDecimal getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(BigDecimal maximumPrice) {
        this.maximumPrice = maximumPrice;
    }

    public String getPriceExpression() {
        return priceExpression;
    }

    public void setPriceExpression(String priceExpression) {
        this.priceExpression = priceExpression == null ? null : priceExpression.trim();
    }

    public Boolean getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(Boolean isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public Boolean getIsCouponAllowed() {
        return isCouponAllowed;
    }

    public void setIsCouponAllowed(Boolean isCouponAllowed) {
        this.isCouponAllowed = isCouponAllowed;
    }

    public Integer getOrders() {
        return orders;
    }

    public void setOrders(Integer orders) {
        this.orders = orders;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

	public List<Coupon> getCouponList() {
		return couponList;
	}

	public void setCouponList(List<Coupon> couponList) {
		this.couponList = couponList;
	}
}