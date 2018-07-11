package com.cxgm.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.OSSClientUtil;
import com.cxgm.common.RSResult;
import com.cxgm.common.SystemConfig;
import com.cxgm.domain.Admin;
import com.cxgm.domain.Advertisement;
import com.cxgm.domain.Product;
import com.cxgm.domain.Shop;
import com.cxgm.service.AdvertisementService;
import com.cxgm.service.ProductService;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageInfo;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/advertisement")
public class AdvertisementController {

	@Autowired
	private AdvertisementService advertisementService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private OSSClientUtil ossClient;
	@Autowired
	private ProductService productService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView shopList(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
	    request.setAttribute("admin", admin);
		PageInfo<Advertisement> pager = advertisementService.findByPage(admin.getShopId(),pageNum, pageSize);
		
		
		request.setAttribute("pager", pager);
		return new ModelAndView("advertisement/advertisement_list");
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView shopToAdd(HttpServletRequest request) throws SQLException {
		
		SecurityContext ctx = SecurityContextHolder.getContext();  
	    Authentication auth = ctx.getAuthentication(); 
	    Admin admin = (Admin) auth.getPrincipal();
		
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		request.setAttribute("systemConfig",systemConfig);
		request.setAttribute("shopId",admin.getShopId());
		return new ModelAndView("advertisement/advertisement_add");
	}
	
	@RequestMapping(value = "/product/list", method = RequestMethod.POST)
	@ResponseBody
	public List<Product> haixinGood(HttpServletRequest request,@RequestParam(value = "goodName") String goodName,
			@RequestParam(value = "shopId") String shopId) {
		
		List<Product> list = new ArrayList<>();
		if(!"".equals(goodName)){
			HashMap<String, Object> map = new HashMap<>();
			
			map.put("goodName", goodName);
			map.put("shopId", shopId);
			list = productService.findProducts(map);
		}
		return list;
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request,
			@RequestParam(value = "adverName",required=false) String adverName,
			@RequestParam(value = "notifyUrl",required=false) String notifyUrl,
			@RequestParam(value = "position",required=false) String position,
			@RequestParam(value = "productId",required=false) String productId,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "type",required=false) String type,
			@RequestParam(value = "number",required=false) String number,
			@RequestParam(value = "onShelf",required=false) Integer onShelf)
			throws Exception {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("adverImages");
		
		Advertisement advertisement= new Advertisement();
		
		advertisement.setCreateTime(new Date());
		advertisement.setAdverName(adverName);
		advertisement.setNotifyUrl(notifyUrl);
		advertisement.setPosition(position);
		advertisement.setProductCode(productId);
		advertisement.setShopId(shopId);
		advertisement.setType(type);
		advertisement.setOnShelf(onShelf);
		advertisement.setNumber(Integer.parseInt(number));
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
				advertisement.setImageUrl(sb.toString());
			}
		advertisementService.addAdvertisement(advertisement);
		ModelAndView mv = new ModelAndView("redirect:/advertisement/list");
		return mv;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = "text/json;charset=UTF-8")
	public String productDelete(HttpServletRequest request) throws SQLException {
		RSResult rr = new RSResult();
		String[] productIds = request.getParameterValues("ids");
		int resultDelete = advertisementService.delete(productIds);
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

}
