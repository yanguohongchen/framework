package com.sea.framework;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import com.sea.exception.DeniedException;

public class Session
{

	private final static Logger logger = LoggerFactory.getLogger(Session.class.getName());

	protected Map<String, String[]> params = new HashMap<>();

	public Session(HttpServletRequest request, HttpServletResponse response, Object handler)
	{
		if (handler instanceof HandlerMethod)
		{
			HandlerMethod handler2 = (HandlerMethod) handler;
			FireAuthority fireAuthority = handler2.getMethodAnnotation(FireAuthority.class);

			if (fireAuthority != null)
			{
				if (fireAuthority.loginStatus().equals(LoginStatus.GENERATE_TOKEN))
				{// 生成token
					sign(request);
				} else if (fireAuthority.loginStatus().equals(LoginStatus.LOGIN))
				{
					auth(request);
				} else if (fireAuthority.role() != null && fireAuthority.role()[0].equals("auth"))
				{
					// /TODO:获取用户信息，获取权限，比对。配置角色名字
					logger.info("认证通过！");
				} else
				{
					throw new DeniedException("权限验证失败！");
				}
			}

		}
	}

	private void sign(HttpServletRequest request)
	{
		// 生成token，并存放到session中
	}

	private void auth(HttpServletRequest request) 
	{
		String token = request.getHeader("token");
		if (token == null || token.equals(""))
		{
			throw new DeniedException("对不起，请先登录！");
		} else
		{
			// TODO:对token进行解析
			Session session = AppContext.getSession();
		}
	}
	
}
