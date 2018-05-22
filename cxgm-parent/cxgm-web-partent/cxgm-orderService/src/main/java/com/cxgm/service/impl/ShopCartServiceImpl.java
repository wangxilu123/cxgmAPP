package com.cxgm.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.CouponMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.dao.ProductMapper;
import com.cxgm.dao.PromotionMapper;
import com.cxgm.dao.ShopCartMapper;
import com.cxgm.domain.Coupon;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductImage;
import com.cxgm.domain.Promotion;
import com.cxgm.domain.ShopCart;
import com.cxgm.domain.ShopCartExample;
import com.cxgm.service.ShopCartService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Primary
@Service
public class ShopCartServiceImpl implements ShopCartService {

	@Autowired
	private ShopCartMapper mapper;

	@Autowired
	private ProductMapper productMapper;

	@Autowired
	private CouponMapper couponMapper;
	
	@Autowired
	private ProductImageMapper productImageMapper;
	
	@Autowired
	private PromotionMapper promotionMapper;

	@Override
	public Integer addShopCart(ShopCart shopCart) {
		mapper.insert(shopCart);
		return shopCart.getId();
	}

	@Override
	public Integer updateShopCart(ShopCart shopCart) {

		ShopCartExample example = new ShopCartExample();

		example.createCriteria().andIdEqualTo(shopCart.getId())
				.andUserIdEqualTo(shopCart.getUserId());

		return mapper.updateByExample(shopCart, example);
	}

	@Override
	public Integer deleteShopCart(String shopCartIds, Integer userId) {

		String[] ids = shopCartIds.split(",");

		for (int i = 0; i < ids.length; i++) {

			ShopCartExample example = new ShopCartExample();

			example.createCriteria().andIdEqualTo(Integer.parseInt(ids[i]))
					.andUserIdEqualTo(userId);

			mapper.deleteByExample(example);
		}
		return userId;
	}

	@Override
	public PageInfo<ShopCart> shopCartList(Integer pageNum, Integer pageSize,
			Integer userId) {

		PageHelper.startPage(pageNum, pageSize);

		ShopCartExample example = new ShopCartExample();

		example.createCriteria().andUserIdEqualTo(userId);

		List<ShopCart> result = mapper.selectByExample(example);
		if (result.size() != 0) {
			for (ShopCart shopCart : result) {
				// 根据商品唯一标识查询商品信息
				HashMap<String, Object> map = new HashMap<String, Object>();

				map.put("shopId", shopCart.getShopId());
				map.put("goodCode", shopCart.getGoodCode());
				List<Product> product = productMapper.findProducts(map);

				if(product.size()!=0){
					Integer weight = product.get(0).getWeight();

					String unit = product.get(0).getUnit();
					
					shopCart.setSpecifications((weight!=null&&unit!=null)?(weight + "/" + unit):"");
					
					if(product.get(0).getImage()!=null&&"".equals(product.get(0).getImage())==false){
						String[] imageIds = product.get(0).getImage().split(",");
						
						ProductImage image = productImageMapper.findById(Long.valueOf(imageIds[0]));
						shopCart.setImageUrl(image!=null?image.getUrl():"");
					}
					shopCart.setPrice(product.get(0).getPrice());
					shopCart.setOriginalPrice(product.get(0).getOriginalPrice());
					shopCart.setCategoryId(product.get(0).getProductCategoryTwoId()!=null?product.get(0).getProductCategoryTwoId().intValue():null);

				}
				
				// 根据商品唯一标识查询商品优惠券信息
				HashMap<String, Object> map1 = new HashMap<String, Object>();

				map1.put("shopId", shopCart.getShopId());
				map1.put("goodCode", shopCart.getGoodCode());

				List<Coupon> coupon = couponMapper.findCouponsWithParam(map1);

				if(coupon.size()!=0){
					shopCart.setCoupon(coupon.get(0).getName());
					shopCart.setCouponId(coupon.get(0).getId().intValue());
				}
				
				//根据商品ID和门店ID查询促销信息
				List<Promotion> promotionList = promotionMapper.findByProductId(map);
				
				shopCart.setPromotionList(promotionList);
			}
		}

		PageInfo<ShopCart> page = new PageInfo<ShopCart>(result);

		return page;
	}

}
