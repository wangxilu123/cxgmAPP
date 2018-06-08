package com.cxgm.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cxgm.dao.ArticleMapper;
import com.cxgm.domain.Article;

@Service
public class ArticleService {

	@Autowired
	ArticleMapper articleDao;
	
	public List<Article> findArticlesByParam(Map<String,Object> map){
		return articleDao.findArticlesWithParam(map);
	}
	
	@Transactional
	public int insert(String title,boolean isTop,boolean isPublication,String content,Integer shopId,String author) {
		Article article = new Article();
		article.setContent(content);
		article.setCreationDate(new Date());
		article.setIsPublication(isPublication);
		article.setIsTop(isTop);
		article.setShopId(shopId);
		article.setTitle(title);
		article.setCreateBy(author);
		return articleDao.insert(article);
	}
	
	@Transactional
	public int update(Long id,String title,boolean isTop,boolean isPublication,String content) {
		Article article = articleDao.selectByPrimaryKey(id);
		article.setContent(content);
		article.setLastUpdatedDate(new Date());
		article.setIsPublication(isPublication);
		article.setIsTop(isTop);
		article.setTitle(title);
		return articleDao.updateByPrimaryKeySelective(article);
	}
	
	public Article select(Long id) {
		return articleDao.selectByPrimaryKey(id);
	}
}
