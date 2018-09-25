package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.RSResult;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Postage;
import com.cxgm.domain.Shop;
import com.cxgm.service.PostageService;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

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
		ModelAndView mv = new ModelAndView("redirect:/postage/pageList");
		return mv;
	}
	
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)	public ModelAndView toEdit(HttpServletRequest request,
			@RequestParam(value = "postageId", required = false) Integer postageId) throws SQLException, UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		
		Postage postage = postageService.findPostageById(postageId);
		
		request.setAttribute("postage", postage);
		request.setAttribute("postageId", postage.getId());
		
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		
		return new ModelAndView("postage/postage_add");
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request,
			@RequestParam(value = "postageId",required=false) Integer postageId,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "reduceMoney",required=false) String reduceMoney,
			@RequestParam(value = "satisfyMoney",required=false) String satisfyMoney)
			throws Exception {
		
		Postage postage = postageService.findPostageById(postageId);
        
		postage.setReduceMoney(new BigDecimal(reduceMoney));
		postage.setSatisfyMoney(new BigDecimal(satisfyMoney));
		postage.setShopId(shopId);
		
		Shop shop = shopService.findShopById(shopId);
		postage.setShopName(shop!=null?shop.getShopName():"");
		
		postageService.updatePostage(postage);
		ModelAndView mv = new ModelAndView("redirect:/postage/pageList");
		return mv;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String productDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] postageIds = request.getParameterValues("ids");
		
		int resultDelete = postageService.delete(postageIds);
		if (resultDelete == 1) {
			rr.setMessage("删除成功！");
			rr.setCode("200");
			rr.setStatus("success");
		} else {
			rr.setMessage("删除失败！");
			rr.setCode("0");
			rr.setStatus("failure");
		}
		return JSONObject.fromObject(rr).toString();
	}

}
