package com.cxgm.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cxgm.domain.Product;
import com.cxgm.domain.ProductTransfer;


public interface ProductMapper {

	public List<ProductTransfer> findListAllWithCategory();
}