package com.cxgm.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.OSSClientUtil;
import com.cxgm.common.SystemConfig;
import com.cxgm.domain.Shop;
import com.cxgm.domain.ThirdOrg;
import com.cxgm.service.ShopService;
import com.cxgm.service.ThirdPartyHaixinOrgService;
import com.cxgm.service.YouzanShopService;
import com.github.pagehelper.PageInfo;
import com.youzan.open.sdk.gen.v3_0_0.model.YouzanMultistoreOfflineSearchResult.AccountShopOffline;

@RestController
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private ShopService shopService;
	
	@Autowired
	private YouzanShopService youzanShopService;
	
	@Autowired
	private ThirdPartyHaixinOrgService thirdPartyHaixinOrgService;
	
	@Autowired
	private OSSClientUtil ossClient;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView shopList(HttpServletRequest request,
			@RequestParam(value = "pageNumber", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		PageInfo<Shop> pager = shopService.findShopByPage(pageNum, pageSize);
		
		request.setAttribute("pager", pager);
		return new ModelAndView("shop/shop_list");
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView shopToAdd(HttpServletRequest request) throws SQLException, UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		
		@SuppressWarnings("static-access")
		List<AccountShopOffline> yzShopList = youzanShopService.findYouzanShopList();
		request.setAttribute("yzShopList", yzShopList);
		
		List<ThirdOrg> hxShopList  = thirdPartyHaixinOrgService.findOrg();
		request.setAttribute("systemConfig",systemConfig);
		request.setAttribute("hxShopList", hxShopList);
		return new ModelAndView("shop/shop_add");
	}
	
	@RequestMapping(value = "/toEdit", method = RequestMethod.GET)	public ModelAndView shopToEdit(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId) throws SQLException {
		
		Shop shop = shopService.findShopById(shopId);
		request.setAttribute("shop", shop);
		return new ModelAndView("shop/shop_add");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request,
			@RequestParam(value = "shopName",required=false) String shopName,
			@RequestParam(value = "owner",required=false) String owner,
			@RequestParam(value = "shopAddress",required=false) String shopAddress,
			@RequestParam(value = "yzShopId",required=false) String yzShopId,
			@RequestParam(value = "hxShopId",required=false) String hxShopId,
			@RequestParam(value = "description",required=false) String description)
			throws Exception {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("shopImages");
		
		Shop shop= new Shop();
		String[] lonlat=request.getParameter("lonlat").split(",");
		
		shop.setShopName(shopName);
		shop.setDescription(description);
		shop.setDimension(lonlat[1]);
		shop.setLongitude(lonlat[0]);
		shop.setOwner(owner);
		shop.setShopAddress(shopAddress);
		shop.setYzShopId(yzShopId);
		shop.setHxShopId(hxShopId);
		
		
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
			sb.deleteCharAt(sb.length()-1);
			shop.setImageUrl(sb.toString());
		shopService.addShop(shop);
		ModelAndView mv = new ModelAndView("redirect:/shop/list");
		return mv;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ModelAndView update(HttpServletRequest request, @RequestBody Shop shop)
			throws InterruptedException {
		
		shopService.updateShop(shop);
		ModelAndView mv = new ModelAndView("redirect:/shop/list");
		return mv;
	}

}
