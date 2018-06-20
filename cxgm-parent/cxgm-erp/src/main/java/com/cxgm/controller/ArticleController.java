package com.cxgm.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.cxgm.domain.Article;
import com.cxgm.service.ArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
public class ArticleController {

	@Autowired
	ArticleService articleService;
	
	@RequestMapping(value = "/admin/article", method = RequestMethod.GET)
	public ModelAndView getArticle(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) {
		PageHelper.startPage(num, 10);
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		List<Article> articles = articleService.findArticlesByParam(map);
		PageInfo<Article> pager = new PageInfo<>(articles);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		return new ModelAndView("admin/article_list");
	}
	
	
	@RequestMapping(value = "/article/list", method = RequestMethod.POST)
	public ModelAndView articleList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword") String name, @RequestParam(value = "property") String property)
			throws SQLException {
		if (null != name && !"".equals(name)) {
			PageHelper.startPage(1, 10);
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			Admin admin = (Admin) auth.getPrincipal();
			Map<String, Object> map = new HashMap<>();
			map.put("name", name);
			map.put("shopId", admin.getShopId());
			List<Article> articles = articleService.findArticlesByParam(map);
			PageInfo<Article> pager = new PageInfo<>(articles);
			request.setAttribute("pager", pager);
			request.setAttribute("admin", admin);
			return new ModelAndView("admin/article_list");
		}
		return this.getArticle(request, num);
	}
	
	@RequestMapping(value = "/article/add", method = RequestMethod.GET)
	public ModelAndView couponAdd(HttpServletRequest request) {
		SecurityContext ctx = SecurityContextHolder.getContext();
		Authentication auth = ctx.getAuthentication();
		Admin admin = (Admin) auth.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		map.put("shopId", admin.getShopId());
		request.setAttribute("shopId", admin.getShopId());
		return new ModelAndView("admin/article_input");
	}
	
	@RequestMapping(value = "/article/save", method = RequestMethod.POST)
	public ModelAndView articleSave(HttpServletRequest request, 
			@RequestParam(value = "article.title") String title,
			@RequestParam(value = "article.isTop") boolean isTop,
			@RequestParam(value = "article.isPublication") boolean isPublication,
			@RequestParam(value = "article.content",required=false) String content,
			@RequestParam(value = "article.shop") Integer shopId) throws SQLException {
		try {
			SecurityContext ctx = SecurityContextHolder.getContext();
			Authentication auth = ctx.getAuthentication();
			Admin admin = (Admin) auth.getPrincipal();
			articleService.insert(title, isTop, isPublication, content, shopId,admin.getId().toString());
			ModelAndView mv = new ModelAndView("redirect:/admin/article");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/article");
			return new ModelAndView("admin/error");
		}
	}
	@RequestMapping(value = "/article/edit", method = RequestMethod.GET)
	public ModelAndView articleEdit(HttpServletRequest request) {
		String id = request.getParameter("id");
		Article article = articleService.select(Long.valueOf(id));
		request.setAttribute("article", article);
		return new ModelAndView("admin/article_input");
	}
	
	@RequestMapping(value = "/article/update", method = RequestMethod.POST)
	public ModelAndView articleUpdate(HttpServletRequest request, 
			@RequestParam(value = "article.title") String title,
			@RequestParam(value = "article.isTop") boolean isTop,
			@RequestParam(value = "article.isPublication") boolean isPublication,
			@RequestParam(value = "article.content",required=false) String content,
			@RequestParam(value = "article.id") Long id) throws SQLException {
		try {
			articleService.update(id, title, isTop, isPublication, content);
			ModelAndView mv = new ModelAndView("redirect:/admin/article");
			return mv;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessages", e.getMessage());
			request.setAttribute("redirectionUrl", "/admin/article");
			return new ModelAndView("admin/error");
		}
	}
}
