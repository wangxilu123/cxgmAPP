package com.cxgmerp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgmerp.dao.ProductDao;
import com.cxgmerp.domain.ProductTransfer;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao;
	
	public List<ProductTransfer> findListAllWithCategory(){
		return productDao.findListAllWithCategory();
	}
	
}
