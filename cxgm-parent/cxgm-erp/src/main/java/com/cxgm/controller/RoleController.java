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

import com.cxgm.common.JSONObjectConfig;
import com.cxgm.common.RSResult;
import com.cxgm.domain.Permission;
import com.cxgm.domain.Role;
import com.cxgm.service.PermissionRoleService;
import com.cxgm.service.PermissionService;
import com.cxgm.service.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
public class RoleController {

	@Autowired
	RoleService roleService;
	@Autowired
	PermissionService permissionService;
	
	@Autowired
	PermissionRoleService permissionRoleService;
	
	
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@RequestMapping(value = "/admin/role", method = RequestMethod.GET)
	public ModelAndView getAdminRole(HttpServletRequest request,@RequestParam(value="num",defaultValue="1") Integer num) throws SQLException {
		PageHelper.startPage(num, 10);
		List<Role> roles = roleService.findListAll();
		 PageInfo<Role> rolePage = new PageInfo<Role>(roles);
		request.setAttribute("pager", rolePage);
		return new ModelAndView("admin/role_list");
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
	@RequestMapping(value = "/role/add", method = RequestMethod.GET)
	public ModelAndView roleAdd(HttpServletRequest request) throws SQLException {
		List<Permission> permissions = permissionService.findListAll();
		request.setAttribute("allResource", permissions);
		return new ModelAndView("admin/role_input");
	}
	
	@RequestMapping(value = "/role/list", method = RequestMethod.POST)
	public ModelAndView roleList(HttpServletRequest request,@RequestParam(value="pageNumber",defaultValue="1") Integer num,@RequestParam(value="keyword") String name) throws SQLException {
		if(null!=name && !"".equals(name)) {
			PageHelper.startPage(1, 10);
			Role role = roleService.selectByName(name);
			List<Role> roles = new ArrayList<>();
			roles.add(role);
			PageInfo<Role> rolePage = new PageInfo<Role>(roles);
			request.setAttribute("pager", rolePage);
			return new ModelAndView("admin/role_list");
		}
		return this.getAdminRole(request, num);
	}
	
	
	@RequestMapping(value = "/role/save", method = RequestMethod.POST)
	public ModelAndView roleSave(HttpServletRequest request,@RequestParam(value="role.name") String name,@RequestParam(value="role.value") String value,
			@RequestParam(value="role.description") String description,
			@RequestParam(value="role.id") Integer id) throws SQLException {
		String[] resourceIds = request.getParameterValues("resourceIds");
		try {
			roleService.insert(name, value, description, resourceIds);
			ModelAndView mv = new ModelAndView("redirect:/admin/role");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/role");
			return new ModelAndView("admin/error");
		}
	}
	
	@RequestMapping(value = "/role/edit", method = RequestMethod.GET)
	public ModelAndView roleEdit(HttpServletRequest request) throws SQLException {
		String roleId = request.getParameter("id");
		Role role = roleService.selectById(Long.valueOf(roleId));
		List<Permission> rolePermissions = permissionService.findByRole(Long.valueOf(roleId));
		role.setResourceList(rolePermissions);
		request.setAttribute("role", role);
		List<Permission> permissions = permissionService.findListAll();
		request.setAttribute("allResource", permissions);
		return new ModelAndView("admin/role_input");
	}
	
	@RequestMapping(value = "/role/update", method = RequestMethod.POST)
	public ModelAndView roleUpdate(HttpServletRequest request,@RequestParam(value="role.name") String name,@RequestParam(value="role.value") String value,
			@RequestParam(value="role.description") String description,
			@RequestParam(value="role.id") Integer id) throws SQLException {
		String[] resourceIds = request.getParameterValues("resourceIds");
		String roleId = request.getParameter("role.id");
		try {
			roleService.update(name, value, description, resourceIds, Long.valueOf(roleId));
			ModelAndView mv = new ModelAndView("redirect:/admin/role");
			return mv;
		}catch(Exception e) {
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/role");
			return new ModelAndView("admin/error");
		}
	}
	
	
	@RequestMapping(value = "/role/search", method = RequestMethod.POST)
	public ModelAndView roleSearch(HttpServletRequest request,@RequestParam(value="keyword") String name) throws SQLException {
		PageHelper.startPage(1, 10);
		Role role = roleService.selectByName(name);
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		PageInfo<Role> rolePage = new PageInfo<Role>(roles);
		request.setAttribute("pager", rolePage);
		return new ModelAndView("admin/role_list");
	}
	@RequestMapping(value = "/role/delete", method = RequestMethod.GET,produces = "text/json;charset=UTF-8")
	public String roleDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] roleIds = request.getParameterValues("ids");
		int resultDelete = roleService.delete(roleIds);
		if(resultDelete == 1) {
			rr.setMessage("删除成功！");
			rr.setCode("200");
			rr.setStatus("success");
		}else if(resultDelete == 0) {
			rr.setMessage("删除失败！");
			rr.setCode("0");
			rr.setStatus("failure");
		}else {
			rr.setMessage(resultDelete+" : 此角色被管理员引用中,不能删除!");
			rr.setCode(resultDelete+"");
			rr.setStatus("failure");
		}
		return JSONObject.fromObject(rr, JSONObjectConfig.getTime())
				.toString();
	}
	
	
}
