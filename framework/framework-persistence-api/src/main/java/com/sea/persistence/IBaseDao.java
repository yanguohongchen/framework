package com.sea.persistence;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author sea
 *
 * @param <T>
 * @param <ID>
 */
public interface IBaseDao<T, ID>
{

	/**
	 * 持久化实体
	 * 
	 * @param t
	 */
	public int save(T t);

	/**
	 * 根据id删除实体
	 * 
	 * @param id
	 */
	public int deleteById(T t);

	/**
	 * 修改实体
	 * 
	 * @param t
	 */
	public int update(T t);

	/**
	 * 根据id获取实体信息
	 * 
	 * @param id
	 * @return
	 */
	public T getEntity(T t);

	/**
	 * 获取列表的记录条数
	 * 
	 * @param param
	 * @return 记录数
	 */
	public Integer getCountBy(T t);

	/**
	 * 获取列表
	 * 
	 * @param parms
	 * @return
	 */
	public List<T> findList(T t);

	/**
	 * 分页获取实体列表
	 * 
	 * @param t
	 * @param pageNo
	 * @param pageSize
	 * @param sort
	 * @param dir
	 * @return
	 */
	public PageData<T> findListByPage(T t, int pageNo, int pageSize, String sort, String dir);
	
	
	/**
	 * 自定义查询条件
	 * @param parms
	 * @return
	 */
	List<T> findListByParams(String mapperName, Map<String, Object> parms);
	
	

}
