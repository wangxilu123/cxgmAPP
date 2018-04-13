package com.cxgmerp.controller;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgmerp.common.RSResult;
import com.cxgmerp.domain.Permission;
import com.cxgmerp.service.PermissionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
public class PermissionController {

	@Autowired
	PermissionService permissionService;
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@RequestMapping(value = "/admin/permission", method = RequestMethod.GET)
	public ModelAndView getAdminPermission(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) throws SQLException {
		PageHelper.startPage(num, 10);
		PageHelper.orderBy("pid asc");
		List<Permission> permissions = permissionService.findListAll();
		PageInfo<Permission> pager = new PageInfo<>(permissions);
		request.setAttribute("pager", pager);
		return new ModelAndView("admin/resource_list");
	}
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@RequestMapping(value = "/resource/add", method = RequestMethod.GET)
	public ModelAndView getResouceAdd(HttpServletRequest request) throws SQLException {
		List<Permission> permissions = permissionService.findFatherResource();
		request.setAttribute("allFatherResource", permissions);
		return new ModelAndView("admin/resource_input");
	}
	
	@RequestMapping(value = "/resource/save", method = RequestMethod.POST)
	public ModelAndView resouceAdd(HttpServletRequest request,
			@RequestParam(value = "pid") Integer pid,
			@RequestParam(value = "resource.description") String description,
			@RequestParam(value = "resource.name") String name,
			@RequestParam(value = "resource.type") Integer type,
			@RequestParam(value = "resource.url") String url) throws SQLException {
		try {
			permissionService.insert(pid, description, name, type, url);
			ModelAndView mv = new ModelAndView("redirect:/admin/permission");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/permission");
			return new ModelAndView("admin/error");
		}
	}
	
	
	@RequestMapping(value = "/resource/update", method = RequestMethod.POST)
	public ModelAndView resouceUpdate(HttpServletRequest request,
			@RequestParam(value = "pid") Integer pid,
			@RequestParam(value = "resource.description") String description,
			@RequestParam(value = "resource.id") Integer id,
			@RequestParam(value = "resource.name") String name,
			@RequestParam(value = "resource.type") Integer type,
			@RequestParam(value = "resource.url") String url) throws SQLException {
		try {
			permissionService.update(Long.valueOf(id),pid, description, name, type, url);
			ModelAndView mv = new ModelAndView("redirect:/admin/permission");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/permission");
			return new ModelAndView("admin/error");
		}
	}
	
	@RequestMapping(value = "/resource/list", method = RequestMethod.POST)
	public ModelAndView adminList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword") String name,
			@RequestParam(value = "property") String property) throws SQLException {
		if (null != name && !"".equals(name) ) {
			PageHelper.startPage(1, 10);
			PageHelper.orderBy("pid asc");
			List<Permission> permissions  = permissionService.findByName(name);
			PageInfo<Permission> pager = new PageInfo<Permission>(permissions);
			request.setAttribute("pager", pager);
			return new ModelAndView("admin/resource_list");
		}
		return this.getAdminPermission(request, num);
	}
	
	@RequestMapping(value = "/resource/edit", method = RequestMethod.GET)
	public ModelAndView adminEdit(HttpServletRequest request) throws SQLException {
		String adminId = request.getParameter("id");
		Permission permission = permissionService.findById(Long.valueOf(adminId));
		List<Permission> permissions = permissionService.findFatherResource();
		request.setAttribute("allFatherResource", permissions);
		request.setAttribute("resource", permission);
		return new ModelAndView("admin/resource_input");
	}
	
	@RequestMapping(value = "/resource/delete", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String resourceDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] resourceIds = request.getParameterValues("ids");
		Integer resultDelete = permissionService.delete(resourceIds);
		if (resultDelete == 1) {
			rr.setMessage("删除成功！");
			rr.setCode("200");
			rr.setStatus("success");
		} else {
			Permission p =permissionService.findById(resultDelete.longValue());
			rr.setMessage(p.getName()+" 已被引用,无法删除！");
			rr.setCode("0");
			rr.setStatus("failure");
		}
		return JSONObject.fromObject(rr).toString();
	}
}
