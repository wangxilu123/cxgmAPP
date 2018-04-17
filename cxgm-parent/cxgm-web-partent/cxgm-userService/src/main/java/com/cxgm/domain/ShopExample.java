package com.cxgm.domain;

import java.util.ArrayList;
import java.util.List;

public class ShopExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShopExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNull() {
            addCriterion("shop_name is null");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNotNull() {
            addCriterion("shop_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopNameEqualTo(String value) {
            addCriterion("shop_name =", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotEqualTo(String value) {
            addCriterion("shop_name <>", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThan(String value) {
            addCriterion("shop_name >", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_name >=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThan(String value) {
            addCriterion("shop_name <", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThanOrEqualTo(String value) {
            addCriterion("shop_name <=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLike(String value) {
            addCriterion("shop_name like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotLike(String value) {
            addCriterion("shop_name not like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameIn(List<String> values) {
            addCriterion("shop_name in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotIn(List<String> values) {
            addCriterion("shop_name not in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameBetween(String value1, String value2) {
            addCriterion("shop_name between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotBetween(String value1, String value2) {
            addCriterion("shop_name not between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNull() {
            addCriterion("shop_address is null");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNotNull() {
            addCriterion("shop_address is not null");
            return (Criteria) this;
        }

        public Criteria andShopAddressEqualTo(String value) {
            addCriterion("shop_address =", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotEqualTo(String value) {
            addCriterion("shop_address <>", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThan(String value) {
            addCriterion("shop_address >", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThanOrEqualTo(String value) {
            addCriterion("shop_address >=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThan(String value) {
            addCriterion("shop_address <", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThanOrEqualTo(String value) {
            addCriterion("shop_address <=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLike(String value) {
            addCriterion("shop_address like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotLike(String value) {
            addCriterion("shop_address not like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressIn(List<String> values) {
            addCriterion("shop_address in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotIn(List<String> values) {
            addCriterion("shop_address not in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressBetween(String value1, String value2) {
            addCriterion("shop_address between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotBetween(String value1, String value2) {
            addCriterion("shop_address not between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNull() {
            addCriterion("longitude is null");
            return (Criteria) this;
        }

        public Criteria andLongitudeIsNotNull() {
            addCriterion("longitude is not null");
            return (Criteria) this;
        }

        public Criteria andLongitudeEqualTo(String value) {
            addCriterion("longitude =", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotEqualTo(String value) {
            addCriterion("longitude <>", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThan(String value) {
            addCriterion("longitude >", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeGreaterThanOrEqualTo(String value) {
            addCriterion("longitude >=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThan(String value) {
            addCriterion("longitude <", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLessThanOrEqualTo(String value) {
            addCriterion("longitude <=", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeLike(String value) {
            addCriterion("longitude like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotLike(String value) {
            addCriterion("longitude not like", value, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeIn(List<String> values) {
            addCriterion("longitude in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotIn(List<String> values) {
            addCriterion("longitude not in", values, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeBetween(String value1, String value2) {
            addCriterion("longitude between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andLongitudeNotBetween(String value1, String value2) {
            addCriterion("longitude not between", value1, value2, "longitude");
            return (Criteria) this;
        }

        public Criteria andDimensionIsNull() {
            addCriterion("dimension is null");
            return (Criteria) this;
        }

        public Criteria andDimensionIsNotNull() {
            addCriterion("dimension is not null");
            return (Criteria) this;
        }

        public Criteria andDimensionEqualTo(String value) {
            addCriterion("dimension =", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotEqualTo(String value) {
            addCriterion("dimension <>", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionGreaterThan(String value) {
            addCriterion("dimension >", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionGreaterThanOrEqualTo(String value) {
            addCriterion("dimension >=", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionLessThan(String value) {
            addCriterion("dimension <", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionLessThanOrEqualTo(String value) {
            addCriterion("dimension <=", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionLike(String value) {
            addCriterion("dimension like", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotLike(String value) {
            addCriterion("dimension not like", value, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionIn(List<String> values) {
            addCriterion("dimension in", values, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotIn(List<String> values) {
            addCriterion("dimension not in", values, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionBetween(String value1, String value2) {
            addCriterion("dimension between", value1, value2, "dimension");
            return (Criteria) this;
        }

        public Criteria andDimensionNotBetween(String value1, String value2) {
            addCriterion("dimension not between", value1, value2, "dimension");
            return (Criteria) this;
        }

        public Criteria andImageUrlIsNull() {
            addCriterion("image_url is null");
            return (Criteria) this;
        }

        public Criteria andImageUrlIsNotNull() {
            addCriterion("image_url is not null");
            return (Criteria) this;
        }

        public Criteria andImageUrlEqualTo(String value) {
            addCriterion("image_url =", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotEqualTo(String value) {
            addCriterion("image_url <>", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThan(String value) {
            addCriterion("image_url >", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlGreaterThanOrEqualTo(String value) {
            addCriterion("image_url >=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThan(String value) {
            addCriterion("image_url <", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLessThanOrEqualTo(String value) {
            addCriterion("image_url <=", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlLike(String value) {
            addCriterion("image_url like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotLike(String value) {
            addCriterion("image_url not like", value, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlIn(List<String> values) {
            addCriterion("image_url in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotIn(List<String> values) {
            addCriterion("image_url not in", values, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlBetween(String value1, String value2) {
            addCriterion("image_url between", value1, value2, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andImageUrlNotBetween(String value1, String value2) {
            addCriterion("image_url not between", value1, value2, "imageUrl");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceIsNull() {
            addCriterion("electronic_fence is null");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceIsNotNull() {
            addCriterion("electronic_fence is not null");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceEqualTo(String value) {
            addCriterion("electronic_fence =", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceNotEqualTo(String value) {
            addCriterion("electronic_fence <>", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceGreaterThan(String value) {
            addCriterion("electronic_fence >", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceGreaterThanOrEqualTo(String value) {
            addCriterion("electronic_fence >=", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceLessThan(String value) {
            addCriterion("electronic_fence <", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceLessThanOrEqualTo(String value) {
            addCriterion("electronic_fence <=", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceLike(String value) {
            addCriterion("electronic_fence like", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceNotLike(String value) {
            addCriterion("electronic_fence not like", value, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceIn(List<String> values) {
            addCriterion("electronic_fence in", values, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceNotIn(List<String> values) {
            addCriterion("electronic_fence not in", values, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceBetween(String value1, String value2) {
            addCriterion("electronic_fence between", value1, value2, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andElectronicFenceNotBetween(String value1, String value2) {
            addCriterion("electronic_fence not between", value1, value2, "electronicFence");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidIsNull() {
            addCriterion("weixin_mchid is null");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidIsNotNull() {
            addCriterion("weixin_mchid is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidEqualTo(String value) {
            addCriterion("weixin_mchid =", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidNotEqualTo(String value) {
            addCriterion("weixin_mchid <>", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidGreaterThan(String value) {
            addCriterion("weixin_mchid >", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_mchid >=", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidLessThan(String value) {
            addCriterion("weixin_mchid <", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidLessThanOrEqualTo(String value) {
            addCriterion("weixin_mchid <=", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidLike(String value) {
            addCriterion("weixin_mchid like", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidNotLike(String value) {
            addCriterion("weixin_mchid not like", value, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidIn(List<String> values) {
            addCriterion("weixin_mchid in", values, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidNotIn(List<String> values) {
            addCriterion("weixin_mchid not in", values, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidBetween(String value1, String value2) {
            addCriterion("weixin_mchid between", value1, value2, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinMchidNotBetween(String value1, String value2) {
            addCriterion("weixin_mchid not between", value1, value2, "weixinMchid");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyIsNull() {
            addCriterion("weixin_apikey is null");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyIsNotNull() {
            addCriterion("weixin_apikey is not null");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyEqualTo(String value) {
            addCriterion("weixin_apikey =", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyNotEqualTo(String value) {
            addCriterion("weixin_apikey <>", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyGreaterThan(String value) {
            addCriterion("weixin_apikey >", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyGreaterThanOrEqualTo(String value) {
            addCriterion("weixin_apikey >=", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyLessThan(String value) {
            addCriterion("weixin_apikey <", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyLessThanOrEqualTo(String value) {
            addCriterion("weixin_apikey <=", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyLike(String value) {
            addCriterion("weixin_apikey like", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyNotLike(String value) {
            addCriterion("weixin_apikey not like", value, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyIn(List<String> values) {
            addCriterion("weixin_apikey in", values, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyNotIn(List<String> values) {
            addCriterion("weixin_apikey not in", values, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyBetween(String value1, String value2) {
            addCriterion("weixin_apikey between", value1, value2, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andWeixinApikeyNotBetween(String value1, String value2) {
            addCriterion("weixin_apikey not between", value1, value2, "weixinApikey");
            return (Criteria) this;
        }

        public Criteria andAliPartneridIsNull() {
            addCriterion("ali_partnerid is null");
            return (Criteria) this;
        }

        public Criteria andAliPartneridIsNotNull() {
            addCriterion("ali_partnerid is not null");
            return (Criteria) this;
        }

        public Criteria andAliPartneridEqualTo(String value) {
            addCriterion("ali_partnerid =", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridNotEqualTo(String value) {
            addCriterion("ali_partnerid <>", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridGreaterThan(String value) {
            addCriterion("ali_partnerid >", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridGreaterThanOrEqualTo(String value) {
            addCriterion("ali_partnerid >=", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridLessThan(String value) {
            addCriterion("ali_partnerid <", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridLessThanOrEqualTo(String value) {
            addCriterion("ali_partnerid <=", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridLike(String value) {
            addCriterion("ali_partnerid like", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridNotLike(String value) {
            addCriterion("ali_partnerid not like", value, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridIn(List<String> values) {
            addCriterion("ali_partnerid in", values, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridNotIn(List<String> values) {
            addCriterion("ali_partnerid not in", values, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridBetween(String value1, String value2) {
            addCriterion("ali_partnerid between", value1, value2, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPartneridNotBetween(String value1, String value2) {
            addCriterion("ali_partnerid not between", value1, value2, "aliPartnerid");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyIsNull() {
            addCriterion("ali_privatekey is null");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyIsNotNull() {
            addCriterion("ali_privatekey is not null");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyEqualTo(String value) {
            addCriterion("ali_privatekey =", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyNotEqualTo(String value) {
            addCriterion("ali_privatekey <>", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyGreaterThan(String value) {
            addCriterion("ali_privatekey >", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyGreaterThanOrEqualTo(String value) {
            addCriterion("ali_privatekey >=", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyLessThan(String value) {
            addCriterion("ali_privatekey <", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyLessThanOrEqualTo(String value) {
            addCriterion("ali_privatekey <=", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyLike(String value) {
            addCriterion("ali_privatekey like", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyNotLike(String value) {
            addCriterion("ali_privatekey not like", value, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyIn(List<String> values) {
            addCriterion("ali_privatekey in", values, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyNotIn(List<String> values) {
            addCriterion("ali_privatekey not in", values, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyBetween(String value1, String value2) {
            addCriterion("ali_privatekey between", value1, value2, "aliPrivatekey");
            return (Criteria) this;
        }

        public Criteria andAliPrivatekeyNotBetween(String value1, String value2) {
            addCriterion("ali_privatekey not between", value1, value2, "aliPrivatekey");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}