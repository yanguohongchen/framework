package com.sea.dao.impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Entity
{
	public void fill(ResultSet set) throws SQLException
	{
		Field[] fields = EntityHolder.reflect(this.getClass());
		try
		{
			for (Field it : fields)
			{
				it.setAccessible(true);
				it.set(this, set.getObject(it.getName()));
			}
		} catch (Exception e)
		{
			throw new SQLException(e);
		}
	}
}
