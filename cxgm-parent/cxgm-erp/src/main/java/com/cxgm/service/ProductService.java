package com.cxgm.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cxgm.common.DateKit;
import com.cxgm.common.UmmessageSend;
import com.cxgm.dao.HaixinGoodMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.domain.HaixinGood;
import com.cxgm.domain.HaixinGoodExample;
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
	
	@Autowired
	HaixinGoodMapper haixinGoodMapper;
	
	
	public List<ProductTransfer> findListAllWithCategory(Map<String,Object> map){
		return productDao.findListAllWithCategory(map);
	}
	
	@Transactional
	public void insert(String name,String goodCode,String originPlace,
			String pid,
			BigDecimal price,
			boolean isMarketable,boolean isTop,String introduction,
			Integer shop,List<MultipartFile> files,BigDecimal originalPrice,Integer warrantyPeriod,String warrantDays,String brandName,String storageCondition,String fullName,String weight,String startTime,String endTime) {
		StringBuilder sb = new StringBuilder();
		//根据goodCode查询海信商品信息
		HaixinGoodExample  example= new HaixinGoodExample();
		
		example.createCriteria().andGoodCodeEqualTo(goodCode);
		
		List<HaixinGood> haixinGoodList = haixinGoodMapper.selectByExample(example);
		
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd"); 
        try {
        	Product product = new Product();
        	product.setBrandName(brandName);
        	product.setStorageCondition(storageCondition);
        	product.setSn(DateKit.generateSn());
        	product.setName(name);
        	product.setFullName(fullName);
        	product.setOriginPlace(originPlace);
        	product.setStartTime(!"".equals(startTime)?str.parse(startTime):null);
        	product.setEndTime(!"".equals(endTime)?str.parse(endTime):null);
        	if("0".equals(weight)){
        		product.setWeight(haixinGoodList.size()!=0?haixinGoodList.get(0).getSpecifications():"");
        	}else{
        		product.setWeight(weight+"Kg");
        	}
        	product.setUnit(haixinGoodList.size()!=0?haixinGoodList.get(0).getUnit():"");
        	product.setGoodCode(goodCode);
        	if(warrantyPeriod!=null&&warrantyPeriod!=0){
        		product.setWarrantyPeriod(warrantyPeriod+warrantDays);
        	}
        	product.setIsTop(isTop);
        	product.setBarCode(haixinGoodList.size()!=0?haixinGoodList.get(0).getBarCode():"");
        	ProductCategory productCategory = productCategoryService.findById(Long.valueOf(pid));
        	
        	if(productCategory.getGrade()==0) {
        		product.setProductCategoryId(productCategory.getId());
        		product.setProductCategoryName(productCategory.getName());
        		String imagesValue = productCategory.getTreePath();
            	String oneCategoryImage = imagesValue.split(",")[0];
            	if("".equals(oneCategoryImage)==false){
            		ProductImage oneCategoryProductImage= productImageDao.findById(Long.valueOf(oneCategoryImage));
                	String oneCategoryURL = oneCategoryProductImage.getUrl();
            		product.setDetailImage(oneCategoryURL);
            	}
        	}
        	if(productCategory.getGrade()==1) {
        		ProductCategory pcy = productCategoryService.findById(productCategory.getParentId());
        		product.setProductCategoryId(pcy.getId());
        		product.setProductCategoryName(pcy.getName());
        		product.setProductCategoryTwoId(productCategory.getId());
        		product.setProductCategoryTwoName(productCategory.getName());
        		String imagesValue = pcy.getTreePath();
            	String oneCategoryImage = imagesValue.split(",")[0];
            	if("".equals(oneCategoryImage)==false){
            		ProductImage oneCategoryProductImage= productImageDao.findById(Long.valueOf(oneCategoryImage));
                	String oneCategoryURL = oneCategoryProductImage.getUrl();
            		product.setDetailImage(oneCategoryURL);
            	}
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
        		String imagesValue = pc1.getTreePath();
            	String oneCategoryImage = imagesValue.split(",")[0];
            	if("".equals(oneCategoryImage)==false){
            		ProductImage oneCategoryProductImage= productImageDao.findById(Long.valueOf(oneCategoryImage));
                	String oneCategoryURL = oneCategoryProductImage.getUrl();
            		product.setDetailImage(oneCategoryURL);
            	}
        	}
        	product.setPrice(price);
        	product.setIsMarketable(isMarketable);
        	product.setIntroduction(introduction);
        	product.setShopId(shop);
        	product.setOriginalPrice(originalPrice);
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
        	Integer productId = productDao.insert(product);
        	//限时抢购消息推送
        	if(productId!=null&&originalPrice.compareTo(price)==1){
        		
        		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        	    
        	    Date nowTime =new Date();
        	    
        	    String time =sdf.format(nowTime);
        		
        		String content=name+"原价"+originalPrice+"元，"+"现价"+price+"元";
        		
        		Map <String, String> map = new HashMap <String, String>();
        		map.put("content", content);
        		map.put("time", time);
        		map.put("type", "0");
        		map.put("goodcode", product.getId().toString());
        		map.put("shopId", shop.toString());
        		JSONArray json = JSONArray.fromObject(map); 
        		
            	new UmmessageSend().sendMessage("限时抢购",json.toString());
        	}
        	
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
	public void update(Integer id,String name,String goodCode,String originPlace,
			String pid,
			BigDecimal price,
			boolean isMarketable,boolean isTop,String introduction,
			Integer shop,List<MultipartFile> files,String[] productImageIds,BigDecimal originalPrice,Integer warrantyPeriod,String warrantDays,String brandName,String storageCondition,String fullName,String weight,String startTime,String endTime) {
		StringBuilder sb = new StringBuilder();
		SimpleDateFormat str = new SimpleDateFormat("yyyy-MM-dd"); 
        try {
        	Product product = productDao.findProductById(id.longValue());
        	product.setName(name);
        	product.setOriginPlace(originPlace);
        	product.setBrandName(brandName);
        	product.setFullName(fullName);
        	product.setWeight(weight);
        	product.setStorageCondition(storageCondition);
        	product.setWarrantyPeriod(warrantyPeriod+warrantDays);
        	product.setStartTime(!"".equals(startTime)?str.parse(startTime):null);
        	product.setEndTime(!"".equals(endTime)?str.parse(endTime):null);
        	product.setIsTop(isTop);
        	if(pid==null || "".equals(pid)) {
        		throw new TipException("请选择商品分类");
        	}
        	ProductCategory productCategory = productCategoryService.findById(Long.valueOf(pid));
        	if(productCategory.getGrade()==0) {
        		product.setProductCategoryId(productCategory.getId());
        		product.setProductCategoryName(productCategory.getName());
        		String imagesValue = productCategory.getTreePath();
            	String oneCategoryImage = imagesValue.split(",")[0];
            	ProductImage oneCategoryProductImage= productImageDao.findById(Long.valueOf(oneCategoryImage));
            	String oneCategoryURL = oneCategoryProductImage.getUrl();
        		product.setDetailImage(oneCategoryURL);
        	}
        	if(productCategory.getGrade()==1) {
        		ProductCategory pcy = productCategoryService.findById(productCategory.getParentId());
        		product.setProductCategoryId(pcy.getId());
        		product.setProductCategoryName(pcy.getName());
        		product.setProductCategoryTwoId(productCategory.getId());
        		product.setProductCategoryTwoName(productCategory.getName());
        		String imagesValue = pcy.getTreePath();
            	String oneCategoryImage = imagesValue.split(",")[0];
            	ProductImage oneCategoryProductImage= productImageDao.findById(Long.valueOf(oneCategoryImage));
            	String oneCategoryURL = oneCategoryProductImage.getUrl();
        		product.setDetailImage(oneCategoryURL);
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
        		String imagesValue = pc1.getTreePath();
            	String oneCategoryImage = imagesValue.split(",")[0];
            	ProductImage oneCategoryProductImage= productImageDao.findById(Long.valueOf(oneCategoryImage));
            	String oneCategoryURL = oneCategoryProductImage.getUrl();
        		product.setDetailImage(oneCategoryURL);
        	}
        	product.setPrice(price);
        	product.setIsMarketable(isMarketable);
        	product.setIntroduction(introduction);
        	product.setShopId(shop);
        	product.setOriginalPrice(originalPrice);
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
	
	public long countByExample(boolean isMarketable) {
		return productDao.countByExample(isMarketable);
	}
}
