package com.cxgm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cxgm.common.DateKit;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.ProductTransfer;

@Service
public class ProductService {

	@Autowired
	ProductMapper productDao;
	@Autowired
	ProductImageMapper productImageDao;
	
	@Autowired
	ProductImageService productImageService;
	
	
	public List<ProductTransfer> findListAllWithCategory(Map<String,Object> map){
		return productDao.findListAllWithCategory(map);
	}
	
	@Transactional
	public void insert(String name,String productSn,String originPlace,String storageCondition,
			String descriptionWeight,String pid,String brand,
			BigDecimal price,BigDecimal marketPrice,
			Integer weight,String unit,Integer stock,
			boolean isMarketable,boolean isTop,String introduction,
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
        	product.setIsTop(isTop);
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
                    }else{
                    	if(sb.length()>0) {
                    		sb.deleteCharAt(sb.length()-1);
                    	}
                    }
                } 
            }
        	product.setImage(sb.toString());
        	productDao.insert(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	@Transactional
	public int delete(String[] productIds) {
		int resultDelete = 0;
		if (productIds != null && productIds.length > 0) {
			for(String productId : productIds) {
				productDao.delete(Long.valueOf(productId));
			}
		}
		resultDelete = 1;
		return resultDelete;
	}
	
	public ProductTransfer findById(Long id) {
		return productDao.findById(id);
	}
	
	public List<ProductImage> getAllAttachmentImage(ProductTransfer product){
		List<ProductImage> productIamgeList = new ArrayList<>();
		if(product.getImage()!=null && !product.getImage().equals("")) {
			String[] imageIds = product.getImage().split(",");
			
			for(String imageId:imageIds) {
				if(imageId!=null && !imageId.equals("")) {
					ProductImage pi = productImageDao.findById(Long.valueOf(imageId));
					productIamgeList.add(pi);
				}
			}
		}
		return productIamgeList;
	}
	
	@Transactional
	public void update(Integer id,String name,String productSn,String originPlace,String storageCondition,
			String descriptionWeight,String pid,String brand,
			BigDecimal price,BigDecimal marketPrice,
			Integer weight,String unit,Integer stock,
			boolean isMarketable,boolean isTop,String introduction,
			Integer shop,List<MultipartFile> files,String[] productImageIds) {
		StringBuilder sb = new StringBuilder();
        try {
        	Product product = productDao.findProductById(id.longValue());
        	product.setName(name);
        	product.setSn(productSn);
        	product.setOriginPlace(originPlace);
        	product.setStorageCondition(storageCondition);
        	product.setWeight(weight);
        	product.setUnit(unit);
        	product.setIsTop(isTop);
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
                    }
                } 
            }
        	if(null!=productImageIds) {
        		for(String productImageId:productImageIds) {
        			sb.append(productImageId);
            		sb.append(",");
            	}
        		sb.deleteCharAt(sb.length()-1);
        	}
        	
        	product.setImage(sb.toString());
        	productDao.update(product);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
