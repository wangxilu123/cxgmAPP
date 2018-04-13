package com.cxgmerp.controller;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgmerp.config.CoreMessageSource;
import com.cxgmerp.domain.Admin;

@RestController
public class LoginController{
    
    @Autowired
    private CoreMessageSource messageSource;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @RequestMapping(value = "/center", method = RequestMethod.GET)
    public ModelAndView getCenter(HttpServletRequest request) throws SQLException{
        SecurityContext ctx = SecurityContextHolder.getContext();  
        Authentication auth = ctx.getAuthentication(); 
    	 Admin admin = (Admin) auth.getPrincipal();
         request.setAttribute("admin", admin);
        return new ModelAndView("admin/admin_center");
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLogin() throws SQLException{
        
        return new ModelAndView("admin/admin_login");
    }
    
    /**  
     * 切换国际化语言<p>
     * lang=zh_CN 为中文<br>
     * lang=en_US 为英文
     * <p>
     * zh_CN和en_US 为国际化配置文件后缀
     * @Title changeLang  
     * @param lang
     * @return String   
     */  
    @GetMapping("/changeLang")
    public String changeLang(@RequestParam(required = false) String lang){
        return messageSource.getMessage("test.addSuccess");
    }

}
