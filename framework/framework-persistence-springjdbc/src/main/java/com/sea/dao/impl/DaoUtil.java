package com.sea.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 单表读写工具
 * 
 * @author sea
 */
public class DaoUtil
{
	/**
	 * 持久化实体
	 * 
	 * @param dao
	 * @param tableName
	 * @param entity
	 * @return
	 */
	public static <E extends Entity> long persist(JdbcTemplate jdbcTemplate, final String tableName, final E entity)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator()
		{
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException
			{
				Field[] fields = EntityHolder.reflect(entity.getClass());

				StringBuilder sql = new StringBuilder();
				sql.append("insert into ").append(tableName).append(" (");
				for (Field it : fields)
				{
					sql.append(it.getName()).append(",");
				}
				sql.setCharAt(sql.length() - 1, ')');

				List<Object> params = new LinkedList<>();
				sql.append(" values (");
				for (Field it : fields)
				{
					sql.append("?,");
					try
					{
						params.add(it.get(entity));
					} catch (Exception e)
					{
						throw new SQLException(e);
					}
				}
				sql.setCharAt(sql.length() - 1, ')');

				System.out.println(sql.toString());

				PreparedStatement ps = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
				for (int i = 1; i <= params.size(); i++)
				{
					ps.setObject(i, params.get(i - 1));
				}
				return ps;
			}
		}, keyHolder);

		if (keyHolder.getKey() != null)
		{
			return keyHolder.getKey().longValue();
		}
		return 0L;
	}


	/**
	 * 读取一条记录
	 * 
	 * @param dao
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <E extends Entity> E loadById(JdbcTemplate dao, final Class<E> entityClass, String sql, Object[] params)
	{
		List<E> list = getList(dao, entityClass, sql, params);
		if (!list.isEmpty())
		{
			return list.get(0);
		}
		return null;
	}

	/**
	 * 读取列表
	 * 
	 * @param dao
	 * @param entityClass
	 * @param sql
	 * @param params
	 * @return
	 */
	public static <E extends Entity> List<E> getList(JdbcTemplate dao, final Class<E> entityClass, String sql, Object[] params)
	{
		List<E> list = dao.query(sql, params, new RowMapper<E>()
		{
			@Override
			public E mapRow(ResultSet set, int rowNo) throws SQLException
			{
				try
				{
					E item = entityClass.newInstance();
					item.fill(set);
					return item;
				} catch (Exception e)
				{
					throw new SQLException(e);
				}
			}
		});
		return list;
	}

}
