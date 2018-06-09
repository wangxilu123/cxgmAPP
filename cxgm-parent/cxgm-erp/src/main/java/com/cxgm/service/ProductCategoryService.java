package com.cxgm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cxgm.dao.ProductCategoryMapper;
import com.cxgm.dao.ProductImageMapper;
import com.cxgm.domain.ProductCategory;
import com.cxgm.domain.ProductImage;
import com.cxgm.exception.TipException;

@Service
public class ProductCategoryService {

	@Autowired
	ProductCategoryMapper productCategoryDao;
	
	@Autowired
	ProductImageService productImageService;
	
	@Autowired
	ProductImageMapper productImageDao;
	
	public List<ProductCategory> findByGrade(Integer grade){
		Map<String,Object> map = new HashMap<>();
		map.put("grade", grade);
		List<ProductCategory> productCategoryList = productCategoryDao.findByCategory(map);
		return productCategoryList;
	}
	public ProductCategory findById(Long id) {
		return productCategoryDao.findById(id);
	}
	
	public List<ProductCategory> findByGradeAndParentId(Integer grade,Long parentId){
		Map<String,Object> map = new HashMap<>();
		map.put("grade", grade);
		map.put("parentId", parentId);
		List<ProductCategory> productCategoryList = productCategoryDao.findByCategory(map);
		return productCategoryList;
	}
	
	
	public List<ProductCategory> getProductCategory(Integer grade){
		List<ProductCategory> productCategoryTreeList = this.findByGrade(grade);
		for(ProductCategory pc : productCategoryTreeList) {
			List<ProductCategory> childOneCategory = this.findByGradeAndParentId(1, pc.getId());
			pc.setChildCategory(childOneCategory);
			for(ProductCategory p: childOneCategory) {
				List<ProductCategory> childTwoCategory = this.findByGradeAndParentId(2, p.getId());
				p.setChildCategory(childTwoCategory);
			}
		}
		return productCategoryTreeList;
	}
	
	@Transactional
	public void insert(Long parentId, String name,List<MultipartFile> files) {
		StringBuilder sb = new StringBuilder();
		ProductCategory productCategory = null;
		try {
			if(files!=null){
	            for(int i=0;i<files.size();i++){  
	                MultipartFile file = files.get(i);
	                if (file.getSize() > 0) {
	                    	Long imgUrl = productImageService.insertImages(file);
	                        sb.append(imgUrl);
	                        sb.append(",");
	                }else{
	                	if(sb.length()>0) {
	                		sb.deleteCharAt(sb.length()-1);
	                	}
	                }
	            } 
	        }
			if(parentId == 0) {
				productCategory = productCategoryDao.findByName(name);
				if(null != productCategory) {
					throw new TipException("类目名字已经存在");
				}
				productCategory = new ProductCategory();
				productCategory.setName(name);
				productCategory.setGrade(0);
				productCategory.setTreePath(sb.toString());
				productCategoryDao.insert(productCategory);
				
			}else {
				productCategory = productCategoryDao.findById(parentId);
				ProductCategory pcy = new ProductCategory();
				pcy.setName(name);
				pcy.setParentId(parentId);
				pcy.setDeleteFlag(false);
				if(productCategory.getGrade()==0) {
					pcy.setGrade(1);
				}else if(productCategory.getGrade()==1){
					pcy.setGrade(2);
				}
				pcy.setTreePath(sb.toString());
				productCategoryDao.insert(pcy);
			}
		}catch(Exception e) {
			e.printStackTrace();
			throw new TipException(e.getMessage());
		}
		
		
	}
	
	@Transactional
	public void update(Long id, Long parentId, String name, List<MultipartFile> files,String[] productImageIds) {
		StringBuilder sb = new StringBuilder();
		try {
			
			ProductCategory productCategory = productCategoryDao.findById(id);
			if(parentId == 0) {
				productCategory.setGrade(0);
				productCategory.setParentId(null);
			}else {
				ProductCategory pcy = productCategoryDao.findById(parentId);
				productCategory.setName(name);
				productCategory.setParentId(parentId);
				if(pcy.getGrade()==0) {
					productCategory.setGrade(1);
				}else if(pcy.getGrade()==1){
					productCategory.setGrade(2);
				}
			}
			if(files!=null){
                for(int i=0;i<files.size();i++){  
                    MultipartFile file = files.get(i);
                    if (file.getSize() > 0) {
	                    	Long imgUrl = productImageService.insertImages(file);
	                        sb.append(imgUrl);
	                        sb.append(",");
                    }
                } 
            }
        	if(null!=productImageIds) {
        		for(String productImageId:productImageIds) {
        			sb.append(productImageId);
            		sb.append(",");
            	}
        		sb.deleteCharAt(sb.length()-1);
        	}else {
        		if(sb.length()>0) {
            		sb.deleteCharAt(sb.length()-1);
            	}
        	}
			productCategory.setTreePath(sb.toString());
			productCategoryDao.update(productCategory);
		}catch(Exception e) {
			e.printStackTrace();
			throw new TipException(e.getMessage());
		}
		
		
	}
	
	@Transactional
	public int delete(Long id) {
		int resultDelete = 0;
		productCategoryDao.delete(id);
		resultDelete = 1;
		return resultDelete;
	}
	public List<ProductImage> getAllAttachmentImage(ProductCategory productCategory){
		List<ProductImage> productIamgeList = new ArrayList<>();
		if(productCategory.getTreePath()!=null && !productCategory.getTreePath().equals("")) {
			String[] imageIds = productCategory.getTreePath().split(",");
			
			for(String imageId:imageIds) {
				if(imageId!=null && !imageId.equals("")) {
					ProductImage pi = productImageDao.findById(Long.valueOf(imageId));
					productIamgeList.add(pi);
				}
			}
		}
		return productIamgeList;
	}
}
