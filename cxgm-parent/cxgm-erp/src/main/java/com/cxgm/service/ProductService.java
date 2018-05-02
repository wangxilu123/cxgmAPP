package com.cxgm.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.cxgm.domain.ProductCategory;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.ShopCategory;
import com.cxgm.exception.TipException;

@Service
public class ProductService {

	@Autowired
	ProductMapper productDao;
	@Autowired
	ProductImageMapper productImageDao;
	
	@Autowired
	ProductImageService productImageService;
	@Autowired
	ProductCategoryService productCategoryService;
	
	
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
        	ProductCategory productCategory = productCategoryService.findById(Long.valueOf(pid));
        	if(productCategory.getGrade()==0) {
        		product.setProductCategoryId(productCategory.getId());
        		product.setProductCategoryName(productCategory.getName());
//        		product.setProductCategoryTwoId(productCategory.getId());
//        		product.setProductCategoryTwoName(productCategory.getName());
//        		product.setProductCategoryThirdId(productCategory.getId());
//        		product.setProductCategoryThirdName(productCategory.getName());
        	}
        	if(productCategory.getGrade()==1) {
        		ProductCategory pcy = productCategoryService.findById(productCategory.getParentId());
        		product.setProductCategoryId(pcy.getId());
        		product.setProductCategoryName(pcy.getName());
        		product.setProductCategoryTwoId(productCategory.getId());
        		product.setProductCategoryTwoName(productCategory.getName());
//        		product.setProductCategoryThirdId(productCategory.getId());
//        		product.setProductCategoryThirdName(productCategory.getName());
        	}
        	if(productCategory.getGrade()==2) {
        		ProductCategory pc2 = productCategoryService.findById(productCategory.getParentId());
        		ProductCategory pc1 = productCategoryService.findById(pc2.getParentId());
        		product.setProductCategoryId(pc1.getId());
        		product.setProductCategoryName(pc1.getName());
        		product.setProductCategoryTwoId(pc2.getId());
        		product.setProductCategoryTwoName(pc2.getName());
        		product.setProductCategoryThirdId(productCategory.getId());
        		product.setProductCategoryThirdName(productCategory.getName());
        	}
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
        	if(pid==null || "".equals(pid)) {
        		throw new TipException("请选择商品分类");
        	}
        	ProductCategory productCategory = productCategoryService.findById(Long.valueOf(pid));
        	if(productCategory.getGrade()==0) {
        		product.setProductCategoryId(productCategory.getId());
        		product.setProductCategoryName(productCategory.getName());
//        		product.setProductCategoryTwoId(productCategory.getId());
//        		product.setProductCategoryTwoName(productCategory.getName());
//        		product.setProductCategoryThirdId(productCategory.getId());
//        		product.setProductCategoryThirdName(productCategory.getName());
        	}
        	if(productCategory.getGrade()==1) {
        		ProductCategory pcy = productCategoryService.findById(productCategory.getParentId());
        		product.setProductCategoryId(pcy.getId());
        		product.setProductCategoryName(pcy.getName());
        		product.setProductCategoryTwoId(productCategory.getId());
        		product.setProductCategoryTwoName(productCategory.getName());
//        		product.setProductCategoryThirdId(productCategory.getId());
//        		product.setProductCategoryThirdName(productCategory.getName());
        	}
        	if(productCategory.getGrade()==2) {
        		ProductCategory pc2 = productCategoryService.findById(productCategory.getParentId());
        		ProductCategory pc1 = productCategoryService.findById(pc2.getParentId());
        		product.setProductCategoryId(pc1.getId());
        		product.setProductCategoryName(pc1.getName());
        		product.setProductCategoryTwoId(pc2.getId());
        		product.setProductCategoryTwoName(pc2.getName());
        		product.setProductCategoryThirdId(productCategory.getId());
        		product.setProductCategoryThirdName(productCategory.getName());
        	}
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
	/**
	 * 获取商店的所有的一级类目二级类目三级类目
	 * @param shopId
	 * @return
	 */
	public List<ShopCategory> findAllCategory(Integer shopId){
		List<ShopCategory> shopCategoryOnes = productDao.findShopCategory(shopId);
		for(ShopCategory shopCategoryOne : shopCategoryOnes) {
			Map<String,Object> map = new HashMap<>();
			map.put("shopId", shopId);
			map.put("productCategoryId", shopCategoryOne.getId());
			List<ShopCategory> shopCategoryTwos = productDao.findShopCategoryTwo(map);
			shopCategoryOne.setShopCategoryList(shopCategoryTwos);
			for(ShopCategory shopCategoryTwo : shopCategoryTwos) {
				Map<String,Object> map1 = new HashMap<>();
				map1.put("shopId", shopId);
				map1.put("productCategoryId", shopCategoryOne.getId());
				map1.put("productCategoryTwoId", shopCategoryTwo.getId());
				List<ShopCategory> shopCategoryThirds = productDao.findShopCategoryThird(map1);
				shopCategoryTwo.setShopCategoryList(shopCategoryThirds);
			}
		}
		return shopCategoryOnes;
	}
	
	/**
	 * 根据输入的条件查询商品
	 * @return
	 */
	public List<Product> findProducts(Map<String,Object> map){
		return productDao.findProducts(map);
	}
}
