package com.cxgm.controller;

import java.sql.SQLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.domain.Advertisement;
import com.cxgm.service.AdvertisementService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView shopList(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		PageInfo<Advertisement> pager = advertisementService.findByPage(pageNum, pageSize);
		
		request.setAttribute("pager", pager);
		return new ModelAndView("advertisement/advertisement_list");
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView shopToAdd(HttpServletRequest request) throws SQLException {
		
		return new ModelAndView("advertisement/advertisement_add");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request)
			throws InterruptedException {
		Advertisement advertisement= new Advertisement();
		
		advertisement.setCreateTime(new Date());
		advertisement.setAdverName(request.getParameter("adverName"));
		advertisement.setImageUrl(request.getParameter("imageUrl"));
		advertisement.setNotifyUrl(request.getParameter("notifyUrl"));
		advertisement.setPosition(request.getParameter("position"));
		advertisement.setProductCode(request.getParameter("productCode"));
		advertisement.setShopId(Integer.parseInt(request.getParameter("shopId")));
		advertisement.setType(request.getParameter("shopId"));
		
		advertisementService.addAdvertisement(advertisement);
		ModelAndView mv = new ModelAndView("redirect:/shop/list");
		return mv;
	}

}
