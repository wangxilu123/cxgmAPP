package com.cxgm.controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.domain.Admin;
import com.cxgm.domain.Postage;
import com.cxgm.domain.Shop;
import com.cxgm.service.PostageService;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/postage")
public class PostageController {

	@Autowired
	PostageService postageService;
	
	@Autowired
	private ShopService shopService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView getPostage(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num,
			@RequestParam(value = "property",required=false) String property) throws SQLException {
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
	    
	    PageInfo<Postage> pager  = postageService.findByPage(num, 10);
	    
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		request.setAttribute("property",property);
		return new ModelAndView("postage/postage_list");
	}
	
	@RequestMapping(value = "/pageList", method = RequestMethod.GET)
	public ModelAndView productList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword",required=false) String name,
			@RequestParam(value = "property",required=false) String property) throws SQLException {
		if (null != name && !"".equals(name) ) {
			SecurityContext ctx = SecurityContextHolder.getContext();  
		    Authentication auth = ctx.getAuthentication(); 
		    Admin admin = (Admin) auth.getPrincipal();
		    request.setAttribute("admin", admin);
		    PageInfo<Postage> pager = postageService.findByList(num,10,name);
			request.setAttribute("pager", pager);
			request.setAttribute("property",property);
			request.setAttribute("keyword",name);
			return new ModelAndView("postage/postage_list");
		}
		return this.getPostage(request, num,property);
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView postageAdd(HttpServletRequest request) {
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
		
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		request.setAttribute("shopId",admin.getShopId());
		return new ModelAndView("postage/postage_add");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "reduceMoney",required=false) String reduceMoney,
			@RequestParam(value = "satisfyMoney",required=false) String satisfyMoney)
			throws Exception {
		
		Postage postage= new Postage();
		
		postage.setReduceMoney(new BigDecimal(reduceMoney));
		postage.setShopId(shopId);
		Shop shop = shopService.findShopById(shopId);
		postage.setShopName(shop.getShopName());
		postage.setSatisfyMoney(new BigDecimal(satisfyMoney));
		
		postageService.addPostage(postage);
		ModelAndView mv = new ModelAndView("redirect:/postage/postage_list");
		return mv;
	}
}
