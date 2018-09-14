package com.cxgm.controller;

import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.ResultDto;
import com.cxgm.config.CoreMessageSource;
import com.cxgm.domain.Admin;
import com.cxgm.domain.AdminLogin;
import com.cxgm.domain.OrderExample;
import com.cxgm.service.AdminService;
import com.cxgm.service.OrderServiceErp;
import com.cxgm.service.ProductService;

import io.swagger.annotations.ApiOperation;

@RestController
public class LoginController{
	
	@Autowired
    private CoreMessageSource messageSource;
    
    @Autowired
    private AdminService adminService;
    
    @Autowired
    private OrderServiceErp orderService;
    @Autowired
    private ProductService productService;
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MANAGER','ROLE_USER')")
    @RequestMapping(value = "/center", method = RequestMethod.GET)
    public ModelAndView getCenter(HttpServletRequest request) throws SQLException{
        SecurityContext ctx = SecurityContextHolder.getContext();  
        Authentication auth = ctx.getAuthentication(); 
    	 Admin admin = (Admin) auth.getPrincipal();
         request.setAttribute("admin", admin);
        return new ModelAndView("admin/admin_center");
    }
    @RequestMapping(value = "/admin/home", method = RequestMethod.GET)
    public ModelAndView adminCenter(HttpServletRequest request) throws SQLException{
        SecurityContext ctx = SecurityContextHolder.getContext();  
        Authentication auth = ctx.getAuthentication();
    	 Admin admin = (Admin) auth.getPrincipal();
    	 OrderExample orderExample = new OrderExample();
    	 orderExample.setOrderByClause("order_time desc");
    	 OrderExample hadOrderExample = new OrderExample();
    	 hadOrderExample.createCriteria().andStatusEqualTo("0");
    	 
    	 OrderExample finishOrderExample = new OrderExample();
    	 finishOrderExample.createCriteria().andStatusIn(Arrays.asList("5","7"));
    	 
    	 long totalOrderCount = orderService.countByExample(orderExample);
    	 long hadOrderCount = orderService.countByExample(hadOrderExample);
    	 long finishOrderCount = orderService.countByExample(finishOrderExample);
    	 
    	 long haveMarketable = productService.countByExample(true);
    	 long unMarketable = productService.countByExample(false);
    	 
    	 request.setAttribute("totalOrderCount", totalOrderCount);
    	 request.setAttribute("hadOrderCount", hadOrderCount);
    	 request.setAttribute("finishOrderCount", finishOrderCount);
    	 request.setAttribute("haveMarketable", haveMarketable);
    	 request.setAttribute("unMarketable", unMarketable);
         request.setAttribute("admin", admin);
        return new ModelAndView("admin/admin_index");
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
    
    @ApiOperation(value = "分拣配送员工登录接口", nickname = "分拣配送员工登录接口")
	@PostMapping("/staffLogin")
	public ResultDto<AdminLogin> staffLogin(HttpServletRequest request, HttpServletResponse response,@RequestBody AdminLogin  admin) {
		
		//根据用户名密码查询员工信息
		AdminLogin adminLogin = adminService.findAdmin(admin.getRealName(), admin.getUsername(), admin.getPassword());
		
		if (adminLogin !=null) {
			return new ResultDto<>(200, "登录成功！", adminLogin);
		} else {
			return new ResultDto<>(201, "员工号，姓名或密码错误！");
		}
	}
    
    @GetMapping("/toH5")
    public ModelAndView toH5(@RequestParam(required = false) String lang){
        return new ModelAndView("coupon/index");
    }
    
    @GetMapping("/turnLoad")
    public ModelAndView turnLoad(@RequestParam(required = false) String lang){
        return new ModelAndView("coupon/turnLoad");
    }

}
