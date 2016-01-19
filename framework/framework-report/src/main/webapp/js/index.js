var Index = function() {
	var methodsInfoMap;
	var module_methodinfo_tpl_compiled;
	var formats_tpl_compiled;

	var initJuicerTpl = function() {

	};

	var obtainData = function() {

		var url = api.getQueryString("url");

		var param = api.getQueryString("param");
		
		param = param.replace(/,/g,"&");
		param = param.replace(/:/g,"=");
		param = param.replace(/{/g,"");
		param = param.replace(/}/g,"");
		
		api.domain_request_jsonp(url, param, function(jsonstr) {
			loadChart(jsonstr);
		});

	};

	var loadChart = function(chartdata) {
		$('#container').highcharts({
			title : {
				text : chartdata.title,
				x : -20 //center
			},
			subtitle : {
				text : chartdata.subTitle,
				x : -20
			},
			xAxis : {
				categories : chartdata.xCategories
			},
			yAxis : {
				title : {
					text : chartdata.yTitle
				},
				plotLines : [{
					value : 0,
					width : 1,
					color : '#808080'
				}]
			},
			tooltip : {
				valueSuffix : chartdata.valueSuffix
			},
			legend : {
				layout : 'vertical',
				align : 'right',
				verticalAlign : 'middle',
				borderWidth : 0
			},
			series : chartdata.series
		});
	}

	return {
		init : function() {
			App.init();
			initJuicerTpl();
			obtainData();
		}
	};

}();
