package com.cxgm.domain;

public class PromotionCoupon {
    private Long id;

    private Long promotions;

    private Long coupons;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPromotions() {
        return promotions;
    }

    public void setPromotions(Long promotions) {
        this.promotions = promotions;
    }

    public Long getCoupons() {
        return coupons;
    }

    public void setCoupons(Long coupons) {
        this.coupons = coupons;
    }
}