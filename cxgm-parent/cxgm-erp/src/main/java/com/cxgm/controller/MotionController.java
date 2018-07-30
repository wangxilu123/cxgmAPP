package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.OSSClientUtil;
import com.cxgm.common.RSResult;
import com.cxgm.common.SystemConfig;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Motion;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.Shop;
import com.cxgm.service.MotionService;
import com.cxgm.service.ProductService;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/motion")
public class MotionController {

	@Autowired
	private MotionService motionService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private OSSClientUtil ossClient;
	
	@Autowired
	ProductService productService;
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView motionList(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
	    
		PageInfo<Motion> pager = motionService.findByPage(admin.getShopId(),pageNum, pageSize);
		
	    request.setAttribute("admin", admin);
		request.setAttribute("pager", pager);
		return new ModelAndView("motion/motion_list");
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView motionToAdd(HttpServletRequest request) throws SQLException {
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
		
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		request.setAttribute("shopId",admin.getShopId());
		request.setAttribute("systemConfig",systemConfig);
		return new ModelAndView("motion/motion_add");
	}
	
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)	public ModelAndView motionToEdit(HttpServletRequest request,
			@RequestParam(value = "motionId", required = false) Integer motionId) throws SQLException, UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		
		Motion motion = motionService.findMotionById(motionId);
		
		//根据商品ID查询商品信息
		if(!"".equals(motion.getProductCode())&&motion.getProductCode()!=null){
			ProductTransfer product = productService.findById(Long.parseLong(motion.getProductCode()));
			
			motion.setGoodName(product!=null?product.getName():"");
		}
		
		request.setAttribute("motion", motion);
		request.setAttribute("motionId", motion.getId());
		
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		request.setAttribute("systemConfig",systemConfig);
		return new ModelAndView("motion/motion_add");
	}
	
	@RequestMapping(value = "/toProductList", method = RequestMethod.GET)
	public ModelAndView toProductList(HttpServletRequest request,
			@RequestParam(value = "num", defaultValue = "1") Integer num) throws SQLException {
		PageHelper.startPage(num, 10);
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
		
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
	
		Map<String,Object> map = new HashMap<>();
	    map.put("shopId", admin.getShopId());
		List<ProductTransfer> products = productService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> pager = new PageInfo<>(products);
		request.setAttribute("pager", pager);
		request.setAttribute("admin", admin);
		request.setAttribute("admin",admin);
		request.setAttribute("systemConfig",systemConfig);
		return new ModelAndView("motion/product_show");
	}
	
	@RequestMapping(value = "/product/list", method = RequestMethod.GET)
	public ModelAndView productList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1") Integer num,
			@RequestParam(value = "keyword",required=false) String name,
			@RequestParam(value = "property",required=false) String property) throws SQLException {
		if (null != name && !"".equals(name) ) {
			PageHelper.startPage(1, 10);
			SecurityContext ctx = SecurityContextHolder.getContext();  
		    Authentication auth = ctx.getAuthentication(); 
		    Admin admin = (Admin) auth.getPrincipal();
		    Map<String,Object> map = new HashMap<>();
		    map.put("shopId", admin.getShopId());
		    map.put("name", name);
			List<ProductTransfer> products = productService.findListAllWithCategory(map);
			PageInfo<ProductTransfer> pager = new PageInfo<>(products);
			request.setAttribute("pager", pager);
			request.setAttribute("admin",admin);
			return new ModelAndView("motion/product_show");
		}
		return this.toProductList(request, num);
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request,
			@RequestParam(value = "motionName",required=false) String motionName,
			@RequestParam(value = "position",required=false) String position,
			@RequestParam(value = "productIds",required=false) String productIds,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "type",required=false) String type,
			@RequestParam(value = "notifyUrl",required=false) String notifyUrl,
			@RequestParam(value = "urlType",required=false) String urlType,
			@RequestParam(value = "productId",required=false) String productId)
			throws Exception {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("motionImage");
		
		Motion motion= new Motion();
		
		motion.setCreateTime(new Date());
		motion.setMotionName(motionName);
		motion.setProductIds(productIds);
		motion.setNotifyUrl(notifyUrl);
		motion.setPosition(position);
		motion.setType(type);
		motion.setShopId(shopId);
		motion.setUrlType(urlType);
		motion.setProductCode(productId);
		StringBuilder sb = new StringBuilder();
		
		if(files!=null){
            for(int i=0;i<files.size();i++){  
                MultipartFile file = files.get(i);
                if (file.getSize() > 0) {
                	String name = ossClient.uploadImg2Oss(file);
            	    String imgUrl = ossClient.getImgUrl(name);
                        sb.append(imgUrl);
                        sb.append(",");
                }
            } 
        }
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
			motion.setImageUrl(sb.toString());
		}
		
		motionService.addMotion(motion);
		ModelAndView mv = new ModelAndView("redirect:/motion/list");
		return mv;
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String productDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] productIds = request.getParameterValues("ids");
		int resultDelete = motionService.delete(productIds);
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
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request,
			@RequestParam(value = "motionId",required=false) Integer motionId,
			@RequestParam(value = "motionName",required=false) String motionName,
			@RequestParam(value = "position",required=false) String position,
			@RequestParam(value = "productIds",required=false) String productIds,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "type",required=false) String type,
			@RequestParam(value = "notifyUrl",required=false) String notifyUrl,
			@RequestParam(value = "urlType",required=false) String urlType,
			@RequestParam(value = "productId",required=false) String productId)
			throws Exception {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("motionImage");
		
        Motion motion = motionService.findMotionById(motionId);
        
        motion.setMotionName(motionName);
        motion.setProductIds(productIds);
        motion.setNotifyUrl(notifyUrl);
        if(!"".equals(productId)&&productId!=null){
        	motion.setProductCode(productId);
        }
        StringBuilder sb = new StringBuilder();
		if(files!=null){
            for(int i=0;i<files.size();i++){  
                MultipartFile file = files.get(i);
                if (file.getSize() > 0) {
                	String name = ossClient.uploadImg2Oss(file);
            	    String imgUrl = ossClient.getImgUrl(name);
                        sb.append(imgUrl);
                        sb.append(",");
                }
            } 
        }
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
			motion.setImageUrl(sb.toString());
		}
		motionService.updateMotion(motion);
		ModelAndView mv = new ModelAndView("redirect:/motion/list");
		return mv;
	}

}
