//应用的初始化ｊｓ
var App = function()
{

	//juicer　参数配置
	var juicerInit = function()
	{
		juicer.set({
			'cache' : false,
			'strip' : false
		});
	};

	return {
		init : function()
		{
			juicerInit();
		}
	};
}();
