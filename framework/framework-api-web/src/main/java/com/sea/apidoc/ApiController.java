package com.sea.apidoc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sea.apidoc.Description;
import com.sea.apidoc.MethodInfo;

@RestController
@RequestMapping("api")
public class ApiController
{

	@Autowired
	private ApiService apiService;

	@Description(value = "获取模块信息")
	@ResponseBody
	@RequestMapping("obtainModuleMethodInfo")
	public List<MethodInfo> obtainModuleMethodInfo(String module)
	{
		return apiService.obtainModuleMethodInfo(module);
	}

	@RequestMapping("obtainMethodInfo")
	@ResponseBody
	public List<MethodInfo> obtainMethodInfo()
	{
		return apiService.obtainMethodInfo();
	}

	@Description(value = "获取模块信息map")
	@RequestMapping("obtainMethodsInfoMap")
	@ResponseBody
	public Map<String, List<MethodInfo>> obtainMethodsInfoMap()
	{
		return apiService.obtainMethodsInfoMap();
	}

}
