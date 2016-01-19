package com.sea.apidoc;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.sea.apidoc.MethodInfo;
import com.sea.apidoc.MyClassUtils;

@Component
public class ApiService
{
	private MyClassUtils myclass;

	public ApiService()
	{
		myclass = new MyClassUtils(new String[]{});
		myclass.scanAnnotation();
	}

	public List<MethodInfo> obtainModuleMethodInfo(String module)
	{
		return myclass.getMethodsInfoMap().get(module);
	}

	public List<MethodInfo> obtainMethodInfo()
	{
		return myclass.getMethodsInfoList();
	}

	public Map<String, List<MethodInfo>> obtainMethodsInfoMap()
	{
		return myclass.getMethodsInfoMap();
	}

	public static void main(String[] args)
	{
		MyClassUtils myclass = new MyClassUtils(new String[]{});
		myclass.scanAnnotation();
	}

}
