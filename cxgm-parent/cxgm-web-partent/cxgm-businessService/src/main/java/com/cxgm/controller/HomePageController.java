package com.cxgm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.domain.ShopCategory;
import com.cxgm.service.HomePageService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description 类说明:
 * @author 作者 E-mail: wangxilu
 */
@Api(description = "用户首页相关接口")
@RestController
@RequestMapping("/homePage")
public class HomePageController {

	@Autowired
	private HomePageService homePageService;

	@ApiOperation(value = "根据门店ID查询商品一级分类", nickname = "根据门店ID查询商品一级分类")
	@GetMapping("/findFirstCategory")
	public ResultDto<List<ShopCategory>> findFirstCategory(HttpServletRequest request, 
			@RequestParam(value = "shopId", required = false) Integer shopId)
			throws InterruptedException {
		List<ShopCategory> result = homePageService.findShopOneCategory(shopId);
		return new ResultDto<>(200, "查询成功", result);
	}
	
	@ApiOperation(value = "根据门店ID和一级分类查询商品二级分类", nickname = "根据门店ID和一级分类查询商品二级分类")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "productCategoryId", value = "一级类别ID", required = false, paramType = "query", dataType = "int"),
    })
	@GetMapping("/findSecondCategory")
	public ResultDto<List<ShopCategory>> findSecondCategory(HttpServletRequest request, 
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam(value = "productCategoryId", required = false) Integer productCategoryId)
			throws InterruptedException {
		List<ShopCategory> result = homePageService.findShopTwoCategory(shopId,productCategoryId);
		return new ResultDto<>(200, "查询成功", result);
	}
	
	@ApiOperation(value = "根据门店ID和二级分类查询商品三级分类", nickname = "根据门店ID和二级分类查询商品三级分类")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "productCategoryTwoId", value = "二级类别ID", required = false, paramType = "query", dataType = "int"),
    })
	@GetMapping("/findThirdCategory")
	public ResultDto<List<ShopCategory>> findThirdCategory(HttpServletRequest request, 
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam(value = "productCategoryTwoId", required = false) Integer productCategoryTwoId)
			throws InterruptedException {
		List<ShopCategory> result = homePageService.findShopThreeCategory(shopId, productCategoryTwoId);
		return new ResultDto<>(200, "查询成功", result);
	}

	@ApiOperation(value = "根据门店ID和商品类别ID查询商品信息", nickname = "根据门店ID和商品类别ID查询商品信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "productCategoryTwoId", value = "二级类别ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
    })
	@GetMapping("/findProductByCategory")
	public ResultDto<PageInfo<ProductTransfer>> findProductByPage(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId,
			@RequestParam(value = "productCategoryId", required = false) Integer productCategoryId,
            @RequestParam(value = "productCategoryTwoId", required = false) Integer productCategoryTwoId,
            @RequestParam(value = "productCategoryThirdId", required = false) Integer productCategoryThirdId,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
		
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("shopId", shopId);
		map.put("productCategoryId", productCategoryId);
		map.put("productCategoryTwoId", productCategoryTwoId);
		map.put("productCategoryThirdId", productCategoryThirdId);
		List<ProductTransfer> list=homePageService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> page = new PageInfo<>(list);
		
		return new ResultDto<>(200, "查询成功", page);
	}
	
	@ApiOperation(value = "根据门店ID查询首页精品推荐", nickname = "根据门店ID查询首页精品推荐")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
    })
	@GetMapping("/findHotProduct")
	public ResultDto<PageInfo<ProductTransfer>> findHotProduct(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
		
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String,Object> map = new HashMap<>();
		map.put("shopId", shopId);
		map.put("isTop", 1);
		List<ProductTransfer> list=homePageService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> page = new PageInfo<>(list);
		
		return new ResultDto<>(200, "查询成功", page);
	}
	
	@ApiOperation(value = "根据门店ID查询首页新品上市", nickname = "根据门店ID查询首页新品上市")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
    })
	@GetMapping("/findNewProduct")
	public ResultDto<PageInfo<ProductTransfer>> findNewProduct(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
		
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("shopId", shopId);
		
		List<ProductTransfer> list=homePageService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> page = new PageInfo<>(list);
		
		return new ResultDto<>(200, "查询成功", page);
	}
	
	@ApiOperation(value = "根据门店ID查询首页热销推荐", nickname = "根据门店ID查询首页热销推荐")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
    })
	@GetMapping("/findHotProduct")
	public ResultDto<PageInfo<ProductTransfer>> findHotProduct(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId){
		
		PageHelper.startPage(1, 10);
		
		Map<String,Object> map = new HashMap<>();
		
		map.put("shopId", shopId);
		
		List<ProductTransfer> list=homePageService.findHotCategory(map);
		PageInfo<ProductTransfer> page = new PageInfo<>(list);
		
		return new ResultDto<>(200, "查询成功", page);
	}

}