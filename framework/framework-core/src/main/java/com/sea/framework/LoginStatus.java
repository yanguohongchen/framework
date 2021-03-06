package com.sea.framework;

public enum LoginStatus
{

	/**
	 * 生成令牌
	 */
	GENERATE_TOKEN("generate_token"),
	/**
	 * 无需登录
	 */
	NO_LOGIN("no_login"),
	/**
	 * 登录
	 */
	LOGIN("login");

	private String loginStatus;

	private LoginStatus(String loginStatus)
	{
		this.loginStatus = loginStatus;
	}

	@Override
	public String toString()
	{
		return loginStatus;
	}

}
