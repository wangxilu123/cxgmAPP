package com.cxgm.domain;

import java.util.List;

public class ShopCategory {
	
	private Long id;
	
	private String name;
	
	private String imageUrl;
	
	private Integer number;
	private List<ShopCategory> shopCategoryList;

	public Long getId() {
		return id;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ShopCategory> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(List<ShopCategory> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	

}
