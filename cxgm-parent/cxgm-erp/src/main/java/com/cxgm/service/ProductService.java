package com.cxgm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cxgm.dao.ProductMapper;
import com.cxgm.domain.ProductTransfer;

@Service
public class ProductService {

	@Autowired
	ProductMapper productDao;
	
	public List<ProductTransfer> findListAllWithCategory(){
		return productDao.findListAllWithCategory();
	}
	
}
