package com.cxgm.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.AdvertisementMapper;
import com.cxgm.dao.MotionMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.dao.PromotionMapper;
import com.cxgm.dao.ShopCartMapper;
import com.cxgm.domain.Advertisement;
import com.cxgm.domain.AdvertisementExample;
import com.cxgm.domain.Motion;
import com.cxgm.domain.MotionExample;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.Promotion;
import com.cxgm.domain.ShopCart;
import com.cxgm.domain.ShopCartExample;
import com.cxgm.domain.ShopCategory;
import com.cxgm.service.HomePageService;

@Primary
@Service
public class HomePageServiceImpl implements HomePageService {

	@Autowired
	private ProductMapper productDao;
	
	@Autowired
	private ProductImageMapper productImageMapper;
	
	@Autowired
	private AdvertisementMapper advertisementMapper;
	
	@Autowired
	private PromotionMapper promotionMapper;
	
	@Autowired
	private MotionMapper motionMapper;
	
	@Autowired
	private ShopCartMapper shopCartMapper;
	
	@Override
	public List<ProductTransfer> findListAllWithCategory(Map<String,Object> map,Integer userId){
		
		List<ProductTransfer> list = productDao.findListAllWithCategory(map);
		
		for(ProductTransfer productTransfer : list){
			if(productTransfer.getImage()!=null&&"".equals(productTransfer.getImage())==false){
				String[] imageIds = productTransfer.getImage().split(",");
				
				ProductImage image = productImageMapper.findById(Long.valueOf(imageIds[0]));
				
				productTransfer.setImage(image!=null?image.getUrl():"");
			}
			//根据商品ID和门店ID查询促销信息
			List<Promotion> promotionList = promotionMapper.findByProductId(map);
			
			productTransfer.setPromotionList(promotionList);
			
			//根据商品ID和门店ID查询购物车信息
			if(userId!=null){
				
				ShopCartExample example = new ShopCartExample();
				example.createCriteria().andShopIdEqualTo(productTransfer.getShopId()).andProductIdEqualTo(productTransfer.getId().intValue()).andUserIdEqualTo(userId);
				
				List<ShopCart> cartList = shopCartMapper.selectByExample(example);
				if(cartList!=null&&cartList.size()!=0){
					productTransfer.setShopCartNum(cartList.get(0).getGoodNum());
				}
				
			}
			
			
		}
		return list;
	}
	
	@Override
	public List<ProductTransfer> findHotCategory(Map<String,Object> map){
		return productDao.findHotCategory(map);
	}

	@Override
	public List<ShopCategory> findShopOneCategory(Integer shopId) {
		
		return productDao.findShopCategory(shopId);
	}
	
	@Override
	public List<ShopCategory> findShopTwoCategory(Integer shopId,Integer productCategoryId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("shopId", shopId);
		map.put("productCategoryId", productCategoryId);
		return productDao.findShopCategoryTwo(map);
	}
	
	@Override
	public List<ShopCategory> findShopThreeCategory(Integer shopId,Integer productCategoryTwoId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("shopId", shopId);
		map.put("productCategoryTwoId", productCategoryTwoId);
		return productDao.findShopCategoryThird(map);
	}
	
	@Override
	public List<Advertisement> findAdvertisement(Integer shopId) {
		
		AdvertisementExample example = new AdvertisementExample();
		
		example.createCriteria().andShopIdEqualTo(shopId).andOnShelfEqualTo(1);
		
		return advertisementMapper.selectByExample(example);
	}
	
	@Override
	public List<Motion> findMotions(Integer shopId) {
		
		MotionExample example = new MotionExample();
		
		example.createCriteria().andShopIdEqualTo(shopId).andOnShelfEqualTo(1);
		
		List<Motion> motionList = motionMapper.selectByExample(example);
		if(motionList.size()!=0){
			
			for(Motion motion : motionList){
				List<ProductTransfer> productList = new ArrayList<>();
				
				if("".equals(motion.getProductIds())==false&&motion.getProductIds()!=null){
					String[] ids = motion.getProductIds().split(",");
					
					for(int i=0;i<ids.length;i++){
						//根据商品ID查询商品信息
						
						ProductTransfer product = productDao.findById(Long.parseLong(ids[i]));
						
						productList.add(product);
					}
				}
				motion.setProductList(productList);
			}
		}
		return  motionList;
	}

	@Override
	public ProductTransfer findProductDetail(Integer productId,Integer shopId,Integer userId) {
		ProductTransfer product = productDao.findById((long)productId);
		
		if(product.getImage()!=null&&"".equals(product.getImage())==false){
			String[] imageIds = product.getImage().split(",");
			
			List<ProductImage> imageList = new ArrayList<>();
			
			for(int i=0;i<imageIds.length;i++){
				
				ProductImage productImage = productImageMapper.findById(Long.valueOf(imageIds[i]));
				
				imageList.add(productImage);
			}
			product.setProductImageList(imageList);
		}
		
		//根据商品ID和门店ID查询购物车信息
		if(userId!=null){
			
			ShopCartExample example = new ShopCartExample();
			example.createCriteria().andShopIdEqualTo(shopId).andProductIdEqualTo(productId).andUserIdEqualTo(userId);
			
			List<ShopCart> cartList = shopCartMapper.selectByExample(example);
			if(cartList!=null&&cartList.size()!=0){
				product.setShopCartNum(cartList.get(0).getGoodNum());
			}
			
		}
		
		return product;
	}

	@Override
	public List<ProductTransfer> pushProducts(Integer productCategoryTwoId,Integer productCategoryThirdId, Integer shopId, Integer userId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("shopId", shopId);
		if(productCategoryTwoId!=null&&"".equals(productCategoryTwoId)==false){
			map.put("productCategoryTwoId", productCategoryTwoId);
		}
		if(productCategoryThirdId!=null&&"".equals(productCategoryThirdId)==false){
			map.put("productCategoryThirdId", productCategoryThirdId);
		}
		
		List<ProductTransfer> list = productDao.findPushProduct(map);
		
		for(ProductTransfer productTransfer : list){
			if(productTransfer.getImage()!=null&&"".equals(productTransfer.getImage())==false){
				String[] imageIds = productTransfer.getImage().split(",");
				
				ProductImage image = productImageMapper.findById(Long.valueOf(imageIds[0]));
				
				productTransfer.setImage(image!=null?image.getUrl():"");
			}
			//根据商品ID和门店ID查询促销信息
			List<Promotion> promotionList = promotionMapper.findByProductId(map);
			
			productTransfer.setPromotionList(promotionList);
			
			//根据商品ID和门店ID查询购物车信息
			if(userId!=null){
				
				ShopCartExample example = new ShopCartExample();
				example.createCriteria().andShopIdEqualTo(shopId).andProductIdEqualTo(productTransfer.getId().intValue()).andUserIdEqualTo(userId);
				
				List<ShopCart> cartList = shopCartMapper.selectByExample(example);
				if(cartList!=null&&cartList.size()!=0){
					productTransfer.setShopCartNum(cartList.get(0).getGoodNum());
				}
			}
		}
		
		return list;
	}
}
