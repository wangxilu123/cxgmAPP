package com.cxgm.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.domain.AppUser;
import com.cxgm.service.AppUserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/appUser")
public class AppUserController {

	@Autowired
	private AppUserService appUserService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView shopList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		PageInfo<AppUser> pager = appUserService.findUserByPage(pageNum, pageSize);
		
		request.setAttribute("pager", pager);
		return new ModelAndView("appUser/user_list");
	}
	
}
