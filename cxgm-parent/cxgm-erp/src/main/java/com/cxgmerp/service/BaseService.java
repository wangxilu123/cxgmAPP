package com.cxgmerp.service;


import java.util.List;
import java.util.Map;

import com.cxgmerp.common.WebPage;

/**
 *
 * @param <T> 实体类
 * @param <ID> 实体类主键类型
 */
public interface BaseService<T,ID extends java.io.Serializable> {
	/**
	 * 插入
	 * @param t
	 * @return
	 */
	public abstract Integer  insert(T t);
	/**
	 * 更新
	 * @param t
	 * @return
	 */
	public abstract Integer  update(T t);
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public abstract Integer delete(ID id);
	
	/**
	 * 主键
	 * @param id
	 * @return
	 */
	public T findById(ID id);
	
	/**
	 * 查找全部
	 * @return
	 */
	public List<T> findListAll();
	
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	public abstract WebPage<T> findPage(Map<String, Object> map);
	
	/**
	 * 查找条件部分
	 * @return
	 */
	public List<T> findListAllWithMap(Map<String, Object> paramsMap);
	
	/**
	 * 依据实体属性验证实体是否存在
	 * @param paramsMap
	 * @return
	 */
	public boolean existsEntity(Map<String,Object> paramsMap);
	
}
