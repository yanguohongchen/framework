var Index = function()
{
	var methodsInfoMap;
	var module_methodinfo_tpl_compiled;
	var formats_tpl_compiled;

	var initJuicerTpl = function()
	{
		var params_tpl = $("#params_tpl").html();
		var module_methodinfo_tpl = $("#module_methodinfo_tpl").html();
		var formats_tpl = $("#formats_tpl").html();

		//注册自定义函数
		juicer.register('jsonToString', jsonToString);
		juicer.register('dealParam', dealParam);
		juicer.register('dealFormats', dealFormats);

		//编译
		module_methodinfo_tpl_compiled = juicer(module_methodinfo_tpl);
		params_tpl_compiled = juicer(params_tpl);
		formats_tpl_compiled = juicer(formats_tpl);
	};

	var obtainMethodsInfoMap = function()
	{
		var param = {};

		api.ajax_request("/api/obtainMethodsInfoMap", param, true, function(jsonstr)
		{
			var jsondata = JSON.stringify(jsonstr);

			$.each(jsonstr, function(key, value)
			{
				var mapdata = {
					mapkey : key,
					mapvalue : value
				};
				var html = module_methodinfo_tpl_compiled.render(mapdata);

				$("#content").append(html);
			});
		});
	};

	var dealFormats = function(formatsJson)
	{
		var formats = {
			formats : formatsJson
		};

		var html = formats_tpl_compiled.render(formats);

		return html;
	};
	var dealParam = function(paramJson)
	{
		var params = {
			params : paramJson
		};
		var html = params_tpl_compiled.render(params);
		return html;
	};

	var jsonToString = function(jsondata)
	{
		var jsonstr = JSON.stringify(jsondata);
		return jsonstr;
	};

	return {
		init : function()
		{
			App.init();
			initJuicerTpl();
			obtainMethodsInfoMap();
		}
	};

}();
