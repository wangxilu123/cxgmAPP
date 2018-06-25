package com.cxgm.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.SOAPException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cxgm.dao.HaixinGoodMapper;
import com.cxgm.domain.GOODS_DATA;
import com.cxgm.domain.HaixinGood;

/**
 * 有赞业务对接 User: CQL
 *
 */
@Primary
@Service
public class HaixinGoodService {

	@Autowired
	private ThirdPartyHaixinGoodService thirdPartyHaixinGoodService;
	
	@Autowired
	private HaixinGoodMapper haixinGoodMapper;
	
	/**
	 * 获取海信商品信息
	 * @throws IOException 
	 * @throws ServiceException 
	 * @throws SOAPException 
	 * @throws UnsupportedEncodingException 
	 */
	public  List<GOODS_DATA> findHaiXingoods() throws UnsupportedEncodingException, SOAPException, ServiceException, IOException {
		
		List<GOODS_DATA> goodList = thirdPartyHaixinGoodService.findGoods();
		
		 for(GOODS_DATA good : goodList){
			   
			   HaixinGood haixinGood = new  HaixinGood();
			   
			   haixinGood.setBarCode(good.getBARCODE());
			   haixinGood.setBrandName(good.getBRANDNAME());
			   haixinGood.setGoodCode(good.getPLUCODE());
			   haixinGood.setGoodName(good.getPLUNAME());
			   haixinGood.setShortName(good.getPLUABBRNAME());
			   haixinGood.setSpecifications(good.getSPEC());
			   haixinGood.setUnit(good.getUNIT());
			   
			   haixinGoodMapper.insert(haixinGood);
			   
		   }	
		
		return goodList;
		
	}
}
