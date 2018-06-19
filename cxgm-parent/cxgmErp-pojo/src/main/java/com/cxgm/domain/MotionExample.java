package com.cxgm.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MotionExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MotionExample() {
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
        
        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
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

        public Criteria andProductIdsIsNull() {
            addCriterion("product_ids is null");
            return (Criteria) this;
        }

        public Criteria andProductIdsIsNotNull() {
            addCriterion("product_ids is not null");
            return (Criteria) this;
        }

        public Criteria andProductIdsEqualTo(String value) {
            addCriterion("product_ids =", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsNotEqualTo(String value) {
            addCriterion("product_ids <>", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsGreaterThan(String value) {
            addCriterion("product_ids >", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsGreaterThanOrEqualTo(String value) {
            addCriterion("product_ids >=", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsLessThan(String value) {
            addCriterion("product_ids <", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsLessThanOrEqualTo(String value) {
            addCriterion("product_ids <=", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsLike(String value) {
            addCriterion("product_ids like", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsNotLike(String value) {
            addCriterion("product_ids not like", value, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsIn(List<String> values) {
            addCriterion("product_ids in", values, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsNotIn(List<String> values) {
            addCriterion("product_ids not in", values, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsBetween(String value1, String value2) {
            addCriterion("product_ids between", value1, value2, "productIds");
            return (Criteria) this;
        }

        public Criteria andProductIdsNotBetween(String value1, String value2) {
            addCriterion("product_ids not between", value1, value2, "productIds");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("position is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("position is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(String value) {
            addCriterion("position =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(String value) {
            addCriterion("position <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(String value) {
            addCriterion("position >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(String value) {
            addCriterion("position >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(String value) {
            addCriterion("position <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(String value) {
            addCriterion("position <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLike(String value) {
            addCriterion("position like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotLike(String value) {
            addCriterion("position not like", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<String> values) {
            addCriterion("position in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<String> values) {
            addCriterion("position not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(String value1, String value2) {
            addCriterion("position between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(String value1, String value2) {
            addCriterion("position not between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(Integer value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(Integer value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(Integer value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(Integer value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(Integer value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<Integer> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<Integer> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(Integer value1, Integer value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(Integer value1, Integer value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andOnShelfIsNull() {
            addCriterion("on_shelf is null");
            return (Criteria) this;
        }

        public Criteria andOnShelfIsNotNull() {
            addCriterion("on_shelf is not null");
            return (Criteria) this;
        }

        public Criteria andOnShelfEqualTo(Integer value) {
            addCriterion("on_shelf =", value, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfNotEqualTo(Integer value) {
            addCriterion("on_shelf <>", value, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfGreaterThan(Integer value) {
            addCriterion("on_shelf >", value, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfGreaterThanOrEqualTo(Integer value) {
            addCriterion("on_shelf >=", value, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfLessThan(Integer value) {
            addCriterion("on_shelf <", value, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfLessThanOrEqualTo(Integer value) {
            addCriterion("on_shelf <=", value, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfIn(List<Integer> values) {
            addCriterion("on_shelf in", values, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfNotIn(List<Integer> values) {
            addCriterion("on_shelf not in", values, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfBetween(Integer value1, Integer value2) {
            addCriterion("on_shelf between", value1, value2, "onShelf");
            return (Criteria) this;
        }

        public Criteria andOnShelfNotBetween(Integer value1, Integer value2) {
            addCriterion("on_shelf not between", value1, value2, "onShelf");
            return (Criteria) this;
        }

        public Criteria andMotionNameIsNull() {
            addCriterion("motion_name is null");
            return (Criteria) this;
        }

        public Criteria andMotionNameIsNotNull() {
            addCriterion("motion_name is not null");
            return (Criteria) this;
        }

        public Criteria andMotionNameEqualTo(String value) {
            addCriterion("motion_name =", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameNotEqualTo(String value) {
            addCriterion("motion_name <>", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameGreaterThan(String value) {
            addCriterion("motion_name >", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameGreaterThanOrEqualTo(String value) {
            addCriterion("motion_name >=", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameLessThan(String value) {
            addCriterion("motion_name <", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameLessThanOrEqualTo(String value) {
            addCriterion("motion_name <=", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameLike(String value) {
            addCriterion("motion_name like", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameNotLike(String value) {
            addCriterion("motion_name not like", value, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameIn(List<String> values) {
            addCriterion("motion_name in", values, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameNotIn(List<String> values) {
            addCriterion("motion_name not in", values, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameBetween(String value1, String value2) {
            addCriterion("motion_name between", value1, value2, "motionName");
            return (Criteria) this;
        }

        public Criteria andMotionNameNotBetween(String value1, String value2) {
            addCriterion("motion_name not between", value1, value2, "motionName");
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