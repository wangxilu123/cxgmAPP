package com.cxgm.domain;

import java.math.BigDecimal;
import java.util.Date;

public class Product {
    private Long id;

    private String sn;

    private String name;

    private String fullName;

    private BigDecimal price;

    private BigDecimal cost;

    private BigDecimal marketPrice;

    private String image;

    private String unit;

    private Integer weight;

    private Integer stock;

    private Integer allocatedStock;

    private String stockMemo;

    private Long point;

    private Boolean isMarketable;

    private Boolean isList;

    private Boolean isTop;

    private Boolean isGift;

    private String memo;

    private String keyword;

    private String seoTitle;

    private String seoKeywords;

    private String seoDescription;

    private Float score;

    private Long totalScore;

    private Long scoreCount;

    private Long hits;

    private Long weekHits;

    private Long monthHits;

    private Long sales;

    private Long weekSales;

    private Long monthSales;

    private Date weekHitsDate;

    private Date monthHitsDate;

    private Date weekSalesDate;

    private Date monthSalesDate;

    private String originPlace;

    private String storageCondition;

    private Long productCategoryId;
    
    private Long productCategoryTwoId;
    
    private Long productCategoryThirdId;
    
    private String productCategoryName;
    
    private String productCategoryTwoName;
    
    private String productCategoryThirdName;
    
    private Integer shopId;

    private String createBy;

    private Date creationDate;

    private String lastUpdatedBy;

    private Date lastUpdatedDate;

    private Boolean deleteFlag;

    private String brandName;

    private String introduction;
    
    private String specifications;
    
    private Integer isHot;
    
    private String goodCode;
    
	public String getGoodCode() {
		return goodCode;
	}

	public void setGoodCode(String goodCode) {
		this.goodCode = goodCode;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName == null ? null : fullName.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getAllocatedStock() {
        return allocatedStock;
    }

    public void setAllocatedStock(Integer allocatedStock) {
        this.allocatedStock = allocatedStock;
    }

    public String getStockMemo() {
        return stockMemo;
    }

    public void setStockMemo(String stockMemo) {
        this.stockMemo = stockMemo == null ? null : stockMemo.trim();
    }

    public Long getPoint() {
        return point;
    }

    public void setPoint(Long point) {
        this.point = point;
    }

    public Boolean getIsMarketable() {
        return isMarketable;
    }

    public void setIsMarketable(Boolean isMarketable) {
        this.isMarketable = isMarketable;
    }

    public Boolean getIsList() {
        return isList;
    }

    public void setIsList(Boolean isList) {
        this.isList = isList;
    }

    public Boolean getIsTop() {
        return isTop;
    }

    public void setIsTop(Boolean isTop) {
        this.isTop = isTop;
    }

    public Boolean getIsGift() {
        return isGift;
    }

    public void setIsGift(Boolean isGift) {
        this.isGift = isGift;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword == null ? null : keyword.trim();
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle == null ? null : seoTitle.trim();
    }

    public String getSeoKeywords() {
        return seoKeywords;
    }

    public void setSeoKeywords(String seoKeywords) {
        this.seoKeywords = seoKeywords == null ? null : seoKeywords.trim();
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription == null ? null : seoDescription.trim();
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Long getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Long totalScore) {
        this.totalScore = totalScore;
    }

    public Long getScoreCount() {
        return scoreCount;
    }

    public void setScoreCount(Long scoreCount) {
        this.scoreCount = scoreCount;
    }

    public Long getHits() {
        return hits;
    }

    public void setHits(Long hits) {
        this.hits = hits;
    }

    public Long getWeekHits() {
        return weekHits;
    }

    public void setWeekHits(Long weekHits) {
        this.weekHits = weekHits;
    }

    public Long getMonthHits() {
        return monthHits;
    }

    public void setMonthHits(Long monthHits) {
        this.monthHits = monthHits;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public Long getWeekSales() {
        return weekSales;
    }

    public void setWeekSales(Long weekSales) {
        this.weekSales = weekSales;
    }

    public Long getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(Long monthSales) {
        this.monthSales = monthSales;
    }

    public Date getWeekHitsDate() {
        return weekHitsDate;
    }

    public void setWeekHitsDate(Date weekHitsDate) {
        this.weekHitsDate = weekHitsDate;
    }

    public Date getMonthHitsDate() {
        return monthHitsDate;
    }

    public void setMonthHitsDate(Date monthHitsDate) {
        this.monthHitsDate = monthHitsDate;
    }

    public Date getWeekSalesDate() {
        return weekSalesDate;
    }

    public void setWeekSalesDate(Date weekSalesDate) {
        this.weekSalesDate = weekSalesDate;
    }

    public Date getMonthSalesDate() {
        return monthSalesDate;
    }

    public void setMonthSalesDate(Date monthSalesDate) {
        this.monthSalesDate = monthSalesDate;
    }

    public String getOriginPlace() {
        return originPlace;
    }

    public void setOriginPlace(String originPlace) {
        this.originPlace = originPlace == null ? null : originPlace.trim();
    }

    public String getStorageCondition() {
        return storageCondition;
    }

    public void setStorageCondition(String storageCondition) {
        this.storageCondition = storageCondition == null ? null : storageCondition.trim();
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy == null ? null : lastUpdatedBy.trim();
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public Boolean getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Boolean deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName == null ? null : brandName.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Long getProductCategoryTwoId() {
		return productCategoryTwoId;
	}

	public void setProductCategoryTwoId(Long productCategoryTwoId) {
		this.productCategoryTwoId = productCategoryTwoId;
	}

	public Long getProductCategoryThirdId() {
		return productCategoryThirdId;
	}

	public void setProductCategoryThirdId(Long productCategoryThirdId) {
		this.productCategoryThirdId = productCategoryThirdId;
	}

	public String getProductCategoryName() {
		return productCategoryName;
	}

	public void setProductCategoryName(String productCategoryName) {
		this.productCategoryName = productCategoryName;
	}

	public String getProductCategoryTwoName() {
		return productCategoryTwoName;
	}

	public void setProductCategoryTwoName(String productCategoryTwoName) {
		this.productCategoryTwoName = productCategoryTwoName;
	}

	public String getProductCategoryThirdName() {
		return productCategoryThirdName;
	}

	public void setProductCategoryThirdName(String productCategoryThirdName) {
		this.productCategoryThirdName = productCategoryThirdName;
	}

	public String getSpecifications() {
		return specifications;
	}

	public void setSpecifications(String specifications) {
		this.specifications = specifications;
	}
	
	
}