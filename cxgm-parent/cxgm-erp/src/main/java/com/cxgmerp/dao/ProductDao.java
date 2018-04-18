package com.cxgmerp.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.cxgmerp.domain.Product;
import com.cxgmerp.domain.ProductTransfer;

@Component
public class ProductDao extends BaseDaoImpl<Product,Long>{

	@Override
	public String getNameSpace() {
		return "sql.Product";
	}
   
	public List<ProductTransfer> findListAllWithCategory(){
		return getSqlSessionTemplate().selectList(this.getNameSpace()+".findListAllWithCategory");
	}
}