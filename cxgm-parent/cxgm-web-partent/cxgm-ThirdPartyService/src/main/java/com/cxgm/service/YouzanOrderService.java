package com.cxgm.service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.OrderMapper;
import com.cxgm.domain.Order;
import com.youzan.open.sdk.client.auth.Token;
import com.youzan.open.sdk.client.core.DefaultYZClient;
import com.youzan.open.sdk.client.core.YZClient;
import com.youzan.open.sdk.client.oauth.model.OAuthToken;
import com.youzan.open.sdk.gen.v3_0_0.api.YouzanTradesSoldGet;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetParams;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanTradesSoldGetResult.TradeDetailV2;

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
	private YouzanShopService youzanShopService;
	/**
	 * 获取门店订单信息接口
	 */
	public  TradeDetailV2[] findYouZanOrder() {
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
			
			Order order = new Order();
			
			order.setOrderAmount(new BigDecimal(tradeDetailV2.getPayment()));
			order.setTotalAmount(new BigDecimal(tradeDetailV2.getTotalFee()));
			order.setPreferential(new BigDecimal(tradeDetailV2.getDiscountFee()));
			order.setRemarks(tradeDetailV2.getBuyerMessage());
			order.setOrderNum(tradeDetailV2.getTid());
			order.setOrderTime(tradeDetailV2.getCreated());
			order.setPayType(tradeDetailV2.getPayType());
			order.setStoreId(tradeDetailV2.getShopId().intValue());
			order.setStatus(tradeDetailV2.getStatus());
			order.setOrderResource("YOUZAN");
			orderMapper.insert(order);
			//查询订单详情商品信息
			tradeDetailV2.getOrders();
			
			//同步海信业务接口
		}
		return tradeDetail;
	}
}
