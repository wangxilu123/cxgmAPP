package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.domain.Shop;
import com.cxgm.domain.ThirdOrg;
import com.cxgm.service.ShopService;
import com.cxgm.service.ThirdPartyHaixinOrgService;
import com.cxgm.service.YouzanShopService;
import com.github.pagehelper.PageInfo;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanMultistoreOfflineSearchResult.AccountShopOffline;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private YouzanShopService youzanShopService;
	
	@Autowired
	private ThirdPartyHaixinOrgService thirdPartyHaixinOrgService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView shopList(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		PageInfo<Shop> pager = shopService.findShopByPage(pageNum, pageSize);
		
		request.setAttribute("pager", pager);
		return new ModelAndView("shop/shop_list");
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView shopToAdd(HttpServletRequest request) throws SQLException, UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		
		@SuppressWarnings("static-access")
		List<AccountShopOffline> yzShopList = youzanShopService.findYouzanShopList();
		request.setAttribute("yzShopList", yzShopList);
		
		List<ThirdOrg> hxShopList  = thirdPartyHaixinOrgService.findOrg();
		
		request.setAttribute("hxShopList", hxShopList);
		return new ModelAndView("shop/shop_add");
	}
	
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)	public ModelAndView shopToEdit(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId) throws SQLException {
		
		Shop shop = shopService.findShopById(shopId);
		request.setAttribute("shop", shop);
		return new ModelAndView("shop/shop_add");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request)
			throws InterruptedException {
		Shop shop= new Shop();
		String[] lonlat=request.getParameter("lonlat").split(",");
		
		shop.setShopName(request.getParameter("shopName"));
		shop.setAliPartnerid(request.getParameter("aliPartnerid"));
		shop.setAliPrivatekey(request.getParameter("aliPrivatekey"));
		shop.setDescription(request.getParameter("description"));
		shop.setDimension(lonlat[1]);
		shop.setLongitude(lonlat[0]);
		shop.setElectronicFence(request.getParameter("electronicFence"));
		/*shop.setImageUrl(request.getParameter("imageUrl"));*/
		shop.setOwner(request.getParameter("owner"));
		shop.setShopAddress(request.getParameter("shopAddress"));
		shop.setWeixinApikey(request.getParameter("weixinApikey"));
		shop.setWeixinMchid(request.getParameter("weixinMchid"));
		shop.setYzShopId(Integer.parseInt(request.getParameter("yzShopId")));
		shop.setHxShopId(Integer.parseInt(request.getParameter("hxShopId")));
		shopService.addShop(shop);
		ModelAndView mv = new ModelAndView("redirect:/shop/list");
		return mv;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, @RequestBody Shop shop)
			throws InterruptedException {
		
		shopService.updateShop(shop);
		ModelAndView mv = new ModelAndView("redirect:/shop/list");
		return mv;
	}

}
