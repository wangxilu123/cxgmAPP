package com.cxgm.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.RSResult;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Role;
import com.cxgm.domain.Shop;
import com.cxgm.exception.TipException;
import com.cxgm.service.AdminService;
import com.cxgm.service.RoleService;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
public class AdminController {

	@Autowired
	AdminService adminService;

	@Autowired
	RoleService roleService;
	
	@Autowired
	private ShopService shopService;

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@RequestMapping(value = "/admin/admin", method = RequestMethod.GET)
	public ModelAndView getAdminMember(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) throws SQLException {
		PageHelper.startPage(num, 10);
		List<Admin> admins = adminService.findListAll();
		PageInfo<Admin> pager = new PageInfo<>(admins);
		request.setAttribute("pager", pager);
		return new ModelAndView("admin/admin_list");
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@RequestMapping(value = "/admin/add", method = RequestMethod.GET)
	public ModelAndView getAdminMemberAdd(HttpServletRequest request) throws SQLException {
		List<Role> roles = roleService.findListAll();
		request.setAttribute("allRole", roles);
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		return new ModelAndView("admin/admin_input");
	}

	@RequestMapping(value = "/admin/list", method = RequestMethod.POST)
	public ModelAndView adminList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword") String name,
			@RequestParam(value = "property") String property) throws SQLException {
		
			if (null != name && !"".equals(name) ) {
				PageHelper.startPage(1, 10);
				Admin admin = null;
				if(property.equals("username")) {
					admin = adminService.findByUserName(name);
				}else {
					admin = adminService.findByName(name);
				}
				List<Admin> admins = new ArrayList<>();
				admins.add(admin);
				PageInfo<Admin> pager = new PageInfo<Admin>(admins);
				request.setAttribute("pager", pager);
				return new ModelAndView("admin/admin_list");
			}
		return this.getAdminMember(request, num);
	}

	@RequestMapping(value = "/admin/save", method = RequestMethod.POST)
	public ModelAndView adminSave(HttpServletRequest request, @RequestParam(value = "admin.username") String username,
			@RequestParam(value = "admin.password") String password,
			@RequestParam(value = "rePassword") String rePassword, @RequestParam(value = "admin.name") String name,
			@RequestParam(value = "admin.isEnabled") Boolean isAccountEnabled,
			@RequestParam(value = "admin.email") String email,
			@RequestParam(value = "admin.department") String department,
			@RequestParam(value = "admin.shopId") Integer shopId) throws SQLException {
		String[] roleIds = request.getParameterValues("roleList.id");
		try {
			if ("".equals(rePassword) || null == rePassword) {
				throw new TipException("需要输入确认密码");
			} else {
				adminService.insert(username, password, name, isAccountEnabled, email, department, roleIds,shopId);
				ModelAndView mv = new ModelAndView("redirect:/admin/admin");
				return mv;
			}
		} catch (Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/admin");
			return new ModelAndView("admin/error");
		}

	}

	@RequestMapping(value = "/admin/update", method = RequestMethod.POST)
	public ModelAndView adminUpdate(HttpServletRequest request, @RequestParam(value = "admin.username") String username,
			@RequestParam(value = "admin.password") String password,
			@RequestParam(value = "rePassword") String rePassword, @RequestParam(value = "admin.name") String name,
			@RequestParam(value = "admin.isEnabled") Boolean isAccountEnabled,
			@RequestParam(value = "admin.email") String email,
			@RequestParam(value = "admin.department") String department,
			@RequestParam(value = "admin.shopId") Integer shopId) throws SQLException {
		String[] roleIds = request.getParameterValues("roleList.id");
		String id = request.getParameter("admin.id");
		try {
			if((password!=null && !"".equals(password)) && (null == rePassword || "".equals(rePassword))) {
				throw new TipException("需要输入确认密码");
			}else {
			adminService.update(username, password, name, isAccountEnabled, email, department, roleIds,
					Long.valueOf(id),shopId);
			ModelAndView mv = new ModelAndView("redirect:/admin/admin");
			return mv;
			}
		} catch (Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/admin");
			return new ModelAndView("admin/error");
		}
	}

	@RequestMapping(value = "/admin/delete", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String adminDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] adminIds = request.getParameterValues("ids");
		int resultDelete = adminService.delete(adminIds);
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

	@RequestMapping(value = "/admin/edit", method = RequestMethod.GET)
	public ModelAndView adminEdit(HttpServletRequest request) throws SQLException {
		String adminId = request.getParameter("id");
		Admin admin = adminService.findById(Long.valueOf(adminId));
		List<Role> roleList = roleService.findByUserName(admin.getUsername());
		admin.setRoleList(roleList);
		List<Role> roles = roleService.findListAll();
		request.setAttribute("allRole", roles);
		request.setAttribute("admin", admin);
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		return new ModelAndView("admin/admin_input");
	}
}
