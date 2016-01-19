package com.sea.framework;

public class Client
{
	// sid
	public String sid;
	// 用户id
	public long userId;
	public String username;
	// 客户端唯一标识
	public String cuid;
	// 时间戳
	public long timestamp;
	// app版本
	public String version;
	// 语言
	public String language;
	// imei
	public String imei;
	// 网络连接类型
	public String netType;
	// imsi
	public String imsi;
	// 请求签名
	public String sign;
	// 机型
	public String device;
	// 国家
	public String country;

	protected void init(Session session)
	{
		this.sid = this.getString(session, "sid");
	}

	private String getString(Session session, String key)
	{
		String[] val = session.params.get(key);
		if (val != null && val.length > 0)
		{
			return val[0];
		}
		return "";
	}

	private int getInt(Session session, String key)
	{
		String[] val = session.params.get(key);
		if (val != null && val.length > 0)
		{
			try
			{
				return Integer.parseInt(val[0]);
			} catch (Exception e)
			{
				return 0;
			}
		}
		return 0;
	}

	private long getLong(Session session, String key)
	{
		String[] val = session.params.get(key);
		if (val != null && val.length > 0)
		{
			try
			{
				return Long.parseLong(val[0]);
			} catch (Exception e)
			{
				return 0;
			}
		}
		return 0;
	}
}
