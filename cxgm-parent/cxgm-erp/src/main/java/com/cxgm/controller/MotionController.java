package com.cxgm.controller;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cxgm.common.OSSClientUtil;
import com.cxgm.common.SystemConfig;
import com.cxgm.domain.Motion;
import com.cxgm.domain.Shop;
import com.cxgm.service.MotionService;
import com.cxgm.service.ShopService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping("/motion")
public class MotionController {

	@Autowired
	private MotionService motionService;
	
	@Autowired
	private ShopService shopService;
	
	@Autowired
	private OSSClientUtil ossClient;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView motionList(HttpServletRequest request,
			@RequestParam(value = "pageNum", defaultValue = "1" , required = false) Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10" , required = false) Integer pageSize){
		
		PageInfo<Motion> pager = motionService.findByPage(pageNum, pageSize);
		
		request.setAttribute("pager", pager);
		return new ModelAndView("motion/motion_list");
	}
	
	@RequestMapping(value = "/toAdd", method = RequestMethod.GET)
	public ModelAndView motionToAdd(HttpServletRequest request) throws SQLException {
		
		SystemConfig systemConfig = new SystemConfig();
		systemConfig.setUploadLimit(10);
		systemConfig.setAllowedUploadImageExtension("png,jpg");
		
		List<Shop> shopList = shopService.findListAll();
		request.setAttribute("shopList", shopList);
		request.setAttribute("systemConfig",systemConfig);
		return new ModelAndView("motion/motion_add");
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST )
	public ModelAndView save(HttpServletRequest request,
			@RequestParam(value = "motionName",required=false) String motionName,
			@RequestParam(value = "position",required=false) String position,
			@RequestParam(value = "productCode",required=false) String productCode,
			@RequestParam(value = "shopId",required=false) Integer shopId,
			@RequestParam(value = "type",required=false) String type)
			throws Exception {
		List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("motionImage");
		
		Motion motion= new Motion();
		
		motion.setCreateTime(new Date());
		motion.setMotionName(motionName);
		
		motion.setPosition(position);
		motion.setType(type);
		motion.setShopId(shopId);
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
		motion.setImageUrl(sb.toString());
		
		motionService.addMotion(motion);
		ModelAndView mv = new ModelAndView("redirect:/motion/list");
		return mv;
	}

}
