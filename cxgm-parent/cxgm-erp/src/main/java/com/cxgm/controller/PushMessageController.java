package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.SystemConfig;
import com.cxgm.common.UmmessageSend;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Product;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.PushMessage;
import com.cxgm.domain.Shop;
import com.cxgm.service.ProductService;
import com.cxgm.service.PushMessageService;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONArray;

@RestController
@RequestMapping("/pushMessage")
public class PushMessageController {

	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView shopList(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
	    request.setAttribute("admin", admin);
		PageInfo<PushMessage> pager = pushMessageService.findByPage(admin.getShopId(),pageNum, pageSize);
		
		
		request.setAttribute("pager", pager);
		return new ModelAndView("pushMessage/pushMessage_list");
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView shopToAdd(HttpServletRequest request) throws SQLException {
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
		
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		request.setAttribute("systemConfig",systemConfig);
		request.setAttribute("shopId",admin.getShopId());
		return new ModelAndView("pushMessage/pushMessage_add");
	}
	
	@RequestMapping(value = "/toSend", method = RequestMethod.GET)
	public ModelAndView toSend(HttpServletRequest request,
			@RequestParam(value = "pushMessageId",required=false) Integer pushMessageId) throws SQLException {
		    
		    //根据ID查询消息
		    PushMessage  pushMessage = pushMessageService.findMessageById(pushMessageId);
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	    
    	    Date nowTime =new Date();
    	    
    	    String time =sdf.format(nowTime);
    		
    		Map <String, String> map = new HashMap <String, String>();
      		map.put("content", pushMessage.getDescription());
    		map.put("time", time);
    		map.put("urlType", pushMessage.getPushType());
    		map.put("notifyUrl", pushMessage.getNotifyUrl()!=null&&!"".equals(pushMessage.getNotifyUrl())?pushMessage.getNotifyUrl():"");
    		map.put("goodcode", pushMessage.getProductId()!=null&&!"".equals(pushMessage.getProductId())?pushMessage.getProductId().toString():"");
    		map.put("shopId", pushMessage.getShopId().toString());
    		JSONArray json = JSONArray.fromObject(map); 
    		
        	new UmmessageSend().sendMessage(pushMessage.getPushType(),json.toString());
        	
        	pushMessage.setIsPush("1");
        	pushMessageService.updateMessage(pushMessage);
        	ModelAndView mv = new ModelAndView("redirect:/pushMessage/list");
		return mv;
	}
	
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)	public ModelAndView advertisementToEdit(HttpServletRequest request,
			@RequestParam(value = "pushMessageId", required = false) Integer pushMessageId) throws SQLException, UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		
		PushMessage pushMessage = pushMessageService.findMessageById(pushMessageId);
		
		//根据商品ID查询商品信息
		if(!"".equals(pushMessage.getProductId())&&pushMessage.getProductId()!=null){
			ProductTransfer product = productService.findById(pushMessage.getProductId().longValue());
			
			pushMessage.setGoodName(product!=null?product.getName():"");
		}
		
		request.setAttribute("pushMessage", pushMessage);
		request.setAttribute("pushMessageId", pushMessage.getId());
		
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		request.setAttribute("systemConfig",systemConfig);
		return new ModelAndView("pushMessage/pushMessage_add");
	}
	
	@RequestMapping(value = "/product/list", method = RequestMethod.POST)
	@ResponseBody
	public List<Product> haixinGood(HttpServletRequest request,@RequestParam(value = "goodName") String goodName,
			@RequestParam(value = "shopId") String shopId) {
		
		List<Product> list = new ArrayList<>();
		if(!"".equals(goodName)){
			HashMap<String, Object> map = new HashMap<>();
			
			map.put("goodName", goodName);
			map.put("shopId", shopId);
			list = productService.findProducts(map);
		}
		return list;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request,
			@RequestParam(value = "name",required=false) String name,
			@RequestParam(value = "notifyUrl",required=false) String notifyUrl,
			@RequestParam(value = "description",required=false) String description,
			@RequestParam(value = "productId",required=false) Integer productId,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "pushType",required=false) String pushType,
			@RequestParam(value = "urlType",required=false) String urlType,
			@RequestParam(value = "isPush",required=false) String isPush)
			throws Exception {
		
		PushMessage pushMessage= new PushMessage();
		
		pushMessage.setDescription(description);
		pushMessage.setIsPush(isPush);
		pushMessage.setName(name);
		pushMessage.setNotifyUrl(notifyUrl);
		pushMessage.setProductId(productId);
		pushMessage.setPushType(pushType);
		pushMessage.setUrlType(urlType);
		pushMessage.setShopId(shopId);
		
		pushMessageService.addPushMessage(pushMessage);
		ModelAndView mv = new ModelAndView("redirect:/pushMessage/list");
		return mv;
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request,
			@RequestParam(value = "pushMessageId",required=false) Integer pushMessageId,
			@RequestParam(value = "name",required=false) String name,
			@RequestParam(value = "notifyUrl",required=false) String notifyUrl,
			@RequestParam(value = "description",required=false) String description,
			@RequestParam(value = "productId",required=false) Integer productId,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "pushType",required=false) String pushType,
			@RequestParam(value = "urlType",required=false) String urlType,
			@RequestParam(value = "isPush",required=false) String isPush)
			throws Exception {
		
		PushMessage pushMessage = pushMessageService.findMessageById(pushMessageId);
        
		pushMessage.setDescription(description);
		pushMessage.setNotifyUrl(notifyUrl);
		pushMessage.setName(name);
        if(!"".equals(productId)&&productId!=null){
        	pushMessage.setProductId(productId);
        }
        pushMessageService.updateMessage(pushMessage);
		ModelAndView mv = new ModelAndView("redirect:/pushMessage/list");
		return mv;
	}

}
