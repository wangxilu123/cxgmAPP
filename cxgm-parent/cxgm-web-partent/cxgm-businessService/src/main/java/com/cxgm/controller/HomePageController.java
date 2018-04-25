package com.cxgm.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cxgm.common.ResultDto;
import com.cxgm.domain.ProductTransfer;
import com.cxgm.service.ProductCategoryService;
import com.cxgm.service.ProductService;
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
	private ProductCategoryService productCategoryService;

	@Autowired
	private ProductService productService;

	/*@ApiOperation(value = "根据门店ID查询商品一级分类", nickname = "根据门店ID查询商品一级分类")
	@PostMapping("/findFirstCategory")
	public ResultDto<Integer> register(HttpServletRequest request, @RequestBody RegisterEntity registerEntity)
			throws InterruptedException {
		ResultDto<Integer> result = userService.addUser(registerEntity);
		return result;
	}*/

	@ApiOperation(value = "根据门店ID和商品类别ID查询商品信息", nickname = "根据门店ID和商品类别ID查询商品信息")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "categoryId", value = "类别ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
    })
	@PostMapping("/findProductByCategory")
	public ResultDto<PageInfo<ProductTransfer>> findProductByPage(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
		
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String,Object> map = new HashMap<>();
		
		List<ProductTransfer> list=productService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> page = new PageInfo<>(list);
		
		return new ResultDto<>(200, "查询成功", page);
	}
	
	@ApiOperation(value = "根据门店ID查询首页精品推荐", nickname = "根据门店ID查询首页精品推荐")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
    })
	@PostMapping("/findHotProduct")
	public ResultDto<PageInfo<ProductTransfer>> findHotProduct(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
		
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String,Object> map = new HashMap<>();
		
		List<ProductTransfer> list=productService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> page = new PageInfo<>(list);
		
		return new ResultDto<>(200, "查询成功", page);
	}
	
	@ApiOperation(value = "根据门店ID查询首页新品上市", nickname = "根据门店ID查询首页新品上市")
	@ApiImplicitParams({
        @ApiImplicitParam(name = "shopId", value = "门店ID", required = false, paramType = "query", dataType = "int"),
        @ApiImplicitParam(name = "pageNum", value = "第几页，默认1", required = false, paramType = "query", dataType = "int"),
		@ApiImplicitParam(name = "pageSize", value = "每页多少条，默认10", required = false, paramType = "query", dataType = "int"),
    })
	@PostMapping("/findNewProduct")
	public ResultDto<PageInfo<ProductTransfer>> findNewProduct(HttpServletRequest request,
			@RequestParam(value = "shopId", required = false) Integer shopId,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false) Integer pageNum,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize){
		
		PageHelper.startPage(pageNum, pageSize);
		
		Map<String,Object> map = new HashMap<>();
		
		List<ProductTransfer> list=productService.findListAllWithCategory(map);
		PageInfo<ProductTransfer> page = new PageInfo<>(list);
		
		return new ResultDto<>(200, "查询成功", page);
	}

}