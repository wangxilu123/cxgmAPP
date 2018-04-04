package com.cxgmerp.controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgmerp.config.CoreMessageSource;

@RestController
public class LoginController{
    
    @Autowired
    private CoreMessageSource messageSource;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER')")
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ModelAndView getHello() throws SQLException{
        return new ModelAndView("views/common/index");
    }
    
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView getLogin() throws SQLException{
        
        return new ModelAndView("views/common/login");
    }
    
    /*@PreAuthorize("isAuthenticated()")*/
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView home() throws SQLException{
        
        return new ModelAndView("views/common/login");
        
    }
    
    @RequestMapping("/safetyOfficers")
    public ModelAndView safetyOfficers() throws SQLException{
        
        return new ModelAndView("home");
        
    }
    
    @RequestMapping(value = "/loginexpired", method = RequestMethod.GET)
    public String loginexpired() throws SQLException{
        
        return "被踢出了";
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
