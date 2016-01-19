package com.sea.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;

import com.sea.exception.BaseDaoException;
import com.sea.persistence.IBaseDao;
import com.sea.persistence.PageData;

public class BaseDao<T, ID> extends SqlSessionDaoSupport implements IBaseDao<T, ID>
{

	public static final String SQLNAME_SEPARATOR = ".";

	public static final String SQL_SAVE = "insertSelective";
	public static final String SQL_UPDATE = "updateByPrimaryKeySelective";
	public static final String SQL_GETBYID = "selectByPrimaryKey";
	public static final String SQL_DELETEBYID = "deleteByPrimaryKey";
	public static final String SQL_DELETEBYIDS = "deleteByIds";
	public static final String SQL_FINDPAGEBY = "findPageBy";
	public static final String SQL_FINDLISTBY = "findListBy";
	public static final String SQL_GETCOUNTBY = "getCountBy";

	private static final String SORT_NAME = "SORT";

	private static final String DIR_NAME = "DIR";

	/**
	 * mybatis支持多数据源。所以数据源需要自己注入
	 */
	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory)
	{
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	/**
	 * 获取默认SqlMapping命名空间。 使用泛型参数中业务实体类型的全限定名作为默认的命名空间。
	 * 如果实际应用中需要特殊的命名空间，可由子类重写该方法实现自己的命名空间规则。
	 * 
	 * @return 返回命名空间字符串
	 */
	@SuppressWarnings("unchecked")
	protected String getDefaultSqlNamespace()
	{
		Class<T> clazz = ReflectGeneric.getInterfaceType(this.getClass());

		String nameSpace = clazz.getName();
		return nameSpace;
	}

	/**
	 * SqlMapping命名空间
	 */
	private String sqlNamespace = getDefaultSqlNamespace();

	/**
	 * 将SqlMapping命名空间与给定的SqlMapping名组合在一起。
	 * 
	 * @param sqlName
	 *            SqlMapping名
	 * @return 组合了SqlMapping命名空间后的完整SqlMapping名
	 */
	protected String getSqlName(String sqlName)
	{
		return sqlNamespace + SQLNAME_SEPARATOR + sqlName;
	}

	/**
	 * 获取SqlMapping命名空间
	 * 
	 * @return SqlMapping命名空间
	 */
	public String getSqlNamespace()
	{
		return sqlNamespace;
	}

	/**
	 * 设置SqlMapping命名空间。 此方法只用于注入SqlMapping命名空间，以改变默认的SqlMapping命名空间，
	 * 不能滥用此方法随意改变SqlMapping命名空间。
	 * 
	 * @param sqlNamespace
	 *            SqlMapping命名空间
	 */
	public void setSqlNamespace(String sqlNamespace)
	{
		this.sqlNamespace = sqlNamespace;
	}

	/**
	 * 生成主键值。 默认情况下什么也不做； 如果需要生成主键，需要由子类重写此方法根据需要的方式生成主键值。
	 * 
	 * @param ob
	 *            要持久化的对象
	 */
	protected void generateId(T ob)
	{

	}

	@Override
	public int save(T t)
	{
		generateId(t);
		return this.getSqlSession().insert(getSqlName(SQL_SAVE), t);
	}

	@Override
	public int deleteById(T t)
	{
		return this.getSqlSession().delete(getSqlName(SQL_DELETEBYID), t);
	}

	@Override
	public int update(T t)
	{
		return this.getSqlSession().update(getSqlName(SQL_UPDATE), t);

	}

	@SuppressWarnings("unchecked")
	@Override
	public T getEntity(T t)
	{
		return (T) this.getSqlSession().selectOne(getSqlName(SQL_GETBYID), t);
	}

	private Integer getCountBy(Map<String, Object> parms)
	{
		return (Integer) this.getSqlSession().selectOne(getSqlName(SQL_GETCOUNTBY), parms);
	}

	@Override
	public Integer getCountBy(T t)
	{
		Map<String, Object> paramMap = null;
		try
		{
			paramMap = BeanMapUtil.bean2Map(t);
		} catch (Exception e)
		{
			throw new BaseDaoException("获取参数失败", e);
		}
		return getCountBy(paramMap);
	}

	private List<T> findList(Map<String, Object> parms)
	{
		return this.getSqlSession().selectList(getSqlName(SQL_FINDLISTBY), parms);
	}

	public List<T> findList(T t)
	{
		Map<String, Object> paramMap = null;
		try
		{
			paramMap = BeanMapUtil.bean2Map(t);
		} catch (Exception e)
		{
			throw new BaseDaoException("获取参数失败", e);
		}
		return findList(paramMap);
	}

	private PageData<T> findListByPage(Map<String, Object> parms, int pageNo, int pageSize)
	{

		PageData<T> pageData = new PageData<T>();
		// 获取满足条件的记录总数，没有记录时返回空页数据
		int count = getCountBy(parms);
		if (count < 1)
		{
			return pageData;
		}

		// 分页条件
		int start = getStartOfPage(pageNo, pageSize) - 1;
		RowBounds rowBound = new RowBounds(start, pageSize);
		List<T> result = this.getSqlSession().selectList(getSqlName(SQL_FINDPAGEBY), parms, rowBound);
		pageData.setData(result);
		pageData.setPageNo(start);
		pageData.setPageSize(pageSize);
		pageData.setTotal(count);
		return pageData;
	}

	/**
	 * 根据页大小（每页数据个数）获取给定页号的第一条数据在总数据中的位置（从1开始）
	 * 
	 * @param pageNo
	 *            给定的页号
	 * @param pageSize
	 *            页大小（每页数据个数）
	 * @return 给定页号的第一条数据在总数据中的位置（从1开始）
	 */
	private int getStartOfPage(int pageNo, int pageSize)
	{
		int startIndex = (pageNo - 1) * pageSize + 1;
		if (startIndex < 1)
			startIndex = 1;
		return startIndex;
	}

	@Override
	public PageData<T> findListByPage(T t, int pageNo, int pageSize, String sort, String dir)
	{

		Map<String, Object> paramMap = null;
		try
		{
			paramMap = BeanMapUtil.bean2Map(t);
		} catch (Exception e)
		{
			throw new BaseDaoException("获取参数失败", e);
		}

		if (sort != null)
		{
			paramMap.put(SORT_NAME, sort);
			// 默认asc
			if (dir == null || dir.equals(""))
			{
				dir = "asc";
			}
			paramMap.put(DIR_NAME, dir);
		}

		return findListByPage(paramMap, pageNo, pageSize);
	}

	@Override
	public List<T> findListByParams(String mapperName,Map<String, Object> parms)
	{
		return this.getSqlSession().selectList(getSqlName(mapperName), parms);
	}

}
