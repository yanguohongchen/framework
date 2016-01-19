package com.sea.persistence.redis;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.google.gson.Gson;
import com.sea.dao.impl.RedisEntity;

public class RedisUtil
{
	private JedisCluster jedisCluster;

	/**
	 * 默认超时时间一个小时
	 */
	private int timeout = 60 * 60 * 1000;

	public RedisUtil(String hosts, GenericObjectPoolConfig poolConfig)
	{

		Set<HostAndPort> nodes = new HashSet<>();
		if (!hosts.equals(""))
		{
			String[] hostArr = hosts.split(",");
			for (String hostport : hostArr)
			{
				String[] temp = hostport.split(":");
				HostAndPort hostAndPort = new HostAndPort(temp[0], Integer.parseInt(temp[1]));
				nodes.add(hostAndPort);
			}
		}
		jedisCluster = new JedisCluster(nodes, poolConfig);
	}

	public JedisCluster getRedisClient()
	{
		return jedisCluster;
	}

	public void close() throws IOException
	{
		jedisCluster.close();
	}

	public void udateRedisEntityInfo(Object key, RedisEntity entity)
	{
		this.getRedisClient().set(entity.getRedisEntityPreKey() + key, new Gson().toJson(entity).toString());
		this.getRedisClient().expire(entity.getRedisEntityPreKey() + key, timeout);
	}

	public void fullRedisEntity(Object key, RedisEntity entity)
	{
		try
		{
			entity = new Gson().fromJson(this.getRedisClient().get(entity.getRedisEntityPreKey() + key), entity.getClass());
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}
}
