package com.cxgm.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cxgm.common.DateKit;
import com.cxgm.dao.ProductMapper;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductTransfer;

@Service
public class ProductService {

	@Autowired
	ProductMapper productDao;
	
	@Autowired
	ProductImageService productImageService;
	
	public List<ProductTransfer> findListAllWithCategory(){
		return productDao.findListAllWithCategory();
	}
	
	@Transactional
	public void insert(String name,String productSn,String originPlace,String storageCondition,
			String descriptionWeight,String pid,String brand,
			BigDecimal price,BigDecimal marketPrice,
			Integer weight,String unit,Integer stock,
			boolean isMarketable,String introduction,
			Integer shop,List<MultipartFile> files) {
		StringBuilder sb = new StringBuilder();
        try {
        	Product product = new Product();
        	product.setName(name);
        	if(null == productSn || "".equals(productSn)) {
        		product.setSn(DateKit.generateSn());
        	}else {
        		product.setSn(productSn);
        	}
        	product.setOriginPlace(originPlace);
        	product.setStorageCondition(storageCondition);
        	product.setWeight(weight);
        	product.setUnit(unit);
        	product.setProductCategoryId(Long.valueOf(pid));
        	product.setBrandName(brand);
        	product.setPrice(price);
        	product.setMarketPrice(marketPrice);
        	product.setStock(stock);
        	product.setIsMarketable(isMarketable);
        	product.setIntroduction(introduction);
        	product.setShopId(shop);
        	if(files!=null){
                for(int i=0;i<files.size();i++){  
                    MultipartFile file = files.get(i);
                    if (file.getSize() > 0) {
	                    	Long imgUrl = productImageService.insertImages(file);
	                        sb.append(imgUrl);
	                        sb.append(",");
                    }else {
                    	sb.deleteCharAt(sb.length()-1);
                    }
                } 
            }
        	product.setImage(sb.toString());
        	productDao.insert(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
