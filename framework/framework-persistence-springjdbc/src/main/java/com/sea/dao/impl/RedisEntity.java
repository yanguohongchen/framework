package com.sea.dao.impl;

public abstract class RedisEntity extends Entity
{
	public String getRedisEntityPreKey()
	{
		return this.getClass().getSimpleName();
	}
}
