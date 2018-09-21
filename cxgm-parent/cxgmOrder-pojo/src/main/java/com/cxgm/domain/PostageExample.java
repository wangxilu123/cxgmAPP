package com.cxgm.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PostageExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PostageExample() {
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

        public Criteria andReduceMoneyIsNull() {
            addCriterion("reduce_money is null");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyIsNotNull() {
            addCriterion("reduce_money is not null");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyEqualTo(BigDecimal value) {
            addCriterion("reduce_money =", value, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyNotEqualTo(BigDecimal value) {
            addCriterion("reduce_money <>", value, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyGreaterThan(BigDecimal value) {
            addCriterion("reduce_money >", value, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("reduce_money >=", value, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyLessThan(BigDecimal value) {
            addCriterion("reduce_money <", value, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("reduce_money <=", value, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyIn(List<BigDecimal> values) {
            addCriterion("reduce_money in", values, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyNotIn(List<BigDecimal> values) {
            addCriterion("reduce_money not in", values, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reduce_money between", value1, value2, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andReduceMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("reduce_money not between", value1, value2, "reduceMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyIsNull() {
            addCriterion("satisfy_money is null");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyIsNotNull() {
            addCriterion("satisfy_money is not null");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyEqualTo(BigDecimal value) {
            addCriterion("satisfy_money =", value, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyNotEqualTo(BigDecimal value) {
            addCriterion("satisfy_money <>", value, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyGreaterThan(BigDecimal value) {
            addCriterion("satisfy_money >", value, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("satisfy_money >=", value, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyLessThan(BigDecimal value) {
            addCriterion("satisfy_money <", value, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyLessThanOrEqualTo(BigDecimal value) {
            addCriterion("satisfy_money <=", value, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyIn(List<BigDecimal> values) {
            addCriterion("satisfy_money in", values, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyNotIn(List<BigDecimal> values) {
            addCriterion("satisfy_money not in", values, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("satisfy_money between", value1, value2, "satisfyMoney");
            return (Criteria) this;
        }

        public Criteria andSatisfyMoneyNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("satisfy_money not between", value1, value2, "satisfyMoney");
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