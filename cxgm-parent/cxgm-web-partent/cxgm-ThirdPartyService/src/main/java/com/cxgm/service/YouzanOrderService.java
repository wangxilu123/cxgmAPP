package com.cxgm.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.common.CodeUtil;
import com.cxgm.common.DateUtil;
import com.cxgm.dao.OrderMapper;
import com.cxgm.dao.OrderProductMapper;
import com.cxgm.dao.ShopMapper;
import com.cxgm.domain.Order;
import com.cxgm.domain.OrderExample;
import com.cxgm.domain.OrderProduct;
import com.cxgm.domain.OrderProductTransfer;
import com.cxgm.domain.Shop;
import com.cxgm.domain.ShopExample;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradesSoldGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult.TradeDetailV2;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult.TradeOrderV2;

/**
 * 有赞业务对接 User: CQL
 *
 */
@Primary
@Service
public class YouzanOrderService {

	@Autowired
	private OrderMapper orderMapper;
	@Autowired
	private ShopMapper shopMapper;
	
	@Autowired
	private YouzanShopService youzanShopService;
	
	@Autowired
	private OrderProductMapper orderProductMapper;
	
	@Autowired
	private ThirdPartyHaixinUplodGoodsService thirdPartyHaixinUplodGoodsService;
	
	@Autowired
	private ThirdPartyHaixinUplodOrderService thirdPartyHaixinUplodOrderService;
	/**
	 * 获取门店订单信息接口
	 * @throws IOException 
	 * @throws ServiceException 
	 * @throws SOAPException 
	 * @throws UnsupportedEncodingException 
	 */
	public  TradeDetailV2[] findYouZanOrder() throws UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		@SuppressWarnings("static-access")
		OAuthToken oAuthToken = youzanShopService.getToken();
		
		@SuppressWarnings("resource")
		YZClient client = new DefaultYZClient(new Token(oAuthToken.getAccessToken()));
		YouzanTradesSoldGetParams youzanTradesSoldGetParams = new YouzanTradesSoldGetParams();
		
        Date startCreated = new Date(new Date().getTime()-7200000);
		
		youzanTradesSoldGetParams.setStartCreated(startCreated);
		youzanTradesSoldGetParams.setEndCreated(new Date());
		YouzanTradesSoldGet youzanTradesSoldGet = new YouzanTradesSoldGet();
		youzanTradesSoldGet.setAPIParams(youzanTradesSoldGetParams);
		YouzanTradesSoldGetResult result = client.invoke(youzanTradesSoldGet);

		TradeDetailV2[] tradeDetail = result.getTrades();
		
		List<TradeDetailV2> list = Arrays.asList(tradeDetail);
		
		for(TradeDetailV2 tradeDetailV2 : list){
			String orderNum = DateUtil.formatDateTime2() + CodeUtil.genCodes(6);
			//根据有赞门店ID查询APP门店信息
			
			ShopExample  example = new ShopExample();
			
			example.createCriteria().andYzShopIdEqualTo(tradeDetailV2.getShopId().toString());
			
			List<Shop> shopList = shopMapper.selectByExample(example);
			
			OrderExample example1 = new OrderExample();
			
			example1.createCriteria().andYouzanNumEqualTo(tradeDetailV2.getTid());
			
			List<Order>  orderList = orderMapper.selectByExample(example1);
			
			if(orderList.size()==0){
				Order order = new Order();
				order.setOrderAmount(new BigDecimal(tradeDetailV2.getPayment()));
				order.setTotalAmount(new BigDecimal(tradeDetailV2.getTotalFee()));
				order.setPreferential(new BigDecimal(tradeDetailV2.getDiscountFee()));
				order.setRemarks(tradeDetailV2.getBuyerMessage());
				order.setOrderNum(orderNum);
				order.setOrderTime(tradeDetailV2.getCreated());
				order.setYouzanNum(tradeDetailV2.getTid());
				if("WEIXIN".equals(tradeDetailV2.getPayType())){
					order.setPayType("wx");
				}
				if("WEIXIN_DAIXIAO".equals(tradeDetailV2.getPayType())){
					order.setPayType("wx");
				}
				if("ALIPAY".equals(tradeDetailV2.getPayType())){
					order.setPayType("zfb");
				}else{
					order.setPayType("qt");
				}
				order.setHaixinShopCode(shopList.size()!=0?shopList.get(0).getHxShopId():null);
				order.setStoreId(shopList.size()!=0?shopList.get(0).getId():null);
				if("WAIT_BUYER_PAY".equals(tradeDetailV2.getStatus())){
					order.setStatus("0");
				}
				if("WAIT_SELLER_SEND_GOODS".equals(tradeDetailV2.getStatus())){
					order.setStatus("1");
				}
				
				if("WAIT_BUYER_CONFIRM_GOODS".equals(tradeDetailV2.getStatus())){
					order.setStatus("4");
				}
				if("TRADE_BUYER_SIGNED".equals(tradeDetailV2.getStatus())){
					order.setStatus("5");
				}
				if("TRADE_CLOSED".equals(tradeDetailV2.getStatus())){
					order.setStatus("7");
				}
				
				order.setOrderResource("YOUZAN");
				orderMapper.insert(order);
				//查询订单详情商品信息
				TradeOrderV2[] tradeist = tradeDetailV2.getOrders();
				
				for(int i=0;i<tradeist.length; i++){
					TradeOrderV2 tradeOrderV2 = tradeist[i];
					
					OrderProduct orderProduct= new OrderProduct();
					
					orderProduct.setOrderId(order.getId());
					orderProduct.setCreateTime(new Date());
					orderProduct.setGoodCode(tradeOrderV2.getOuterItemId());
					orderProduct.setProductName(tradeOrderV2.getTitle());
					orderProduct.setProductNum(tradeOrderV2.getNum().intValue());
					orderProduct.setHaixinUrl(tradeOrderV2.getPicPath());
					BigDecimal bigDecimal = new BigDecimal(tradeOrderV2.getPrice());
					orderProduct.setPrice(bigDecimal);
					orderProductMapper.insert(orderProduct);
				}
				
				List<OrderProductTransfer> productList = orderProductMapper.selectYouZanOrderDetail(order.getId());
				order.setProductDetails(productList);
				//同步海信业务接口
				if(!"0".equals(order.getStatus())||!"7".equals(order.getStatus())){
					String code = thirdPartyHaixinUplodOrderService.checkOrder(order.getOrderNum());
					if("1".equals(code)){
						thirdPartyHaixinUplodGoodsService.upload(order);
					}
				}
				
			}
			
		}
		return tradeDetail;
	}
}
