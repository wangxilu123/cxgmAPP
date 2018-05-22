package com.cxgm.domain;

import java.math.BigDecimal;

public class CategoryAndAmount {
    private Integer categoryId;

    private BigDecimal amount;

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

  
}