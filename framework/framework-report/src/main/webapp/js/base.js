/**
 * 前端js基础框架，封装常用方法
 * @author sea
 */
var Base = function() {
	//请求base地址
	var requestUrl = ["http://localhost:8080/dp-web/", "http://localhost/"];

	var _getRequestUrl = function(index) {
		return requestUrl[index];
	};
	var _getFullParameter = function(params) {

		//获取用户信息

		// var sid = getSid();
		// if (sid == null || sid == "")
		// {
		// return params;
		// }
		//
		// var seed = Math.random() * 10000 + 1;
		// params.sid = sid;
		// params.seed = seed;
		// params.sbf = common.util.md5(sid + seed + common.user.api.getEncryptKey());
		// return params;

		return params;

	};

	var _request = function(block, serverIndex, action, params, care, callback) {
		if (params != null) {
			params = _getFullParameter(params);
		} else {
			params = _getFullParameter({

			});
		}

		//该代码jquery已经实现
		var parameter = "";
		for (var p in params) {
			if ( typeof (params[p]) != "function" && typeof (params[p]) != "Object") {
				if (params[p] instanceof Array) {
					for (var i = 0; i < params[p].length; i++) {
						parameter += p + "=" + params[p][i] + "&";
					}
				} else {
					if (params[p] != '') {
						parameter += p + "=" + params[p] + "&";
					}
				}
			}
		}

		if (parameter.charAt(parameter.length - 1) == '&') {
			parameter.substr(0, parameter.length - 1);
		}

		if (block) {
			//TODO:是否阻塞
			// common.ui.mask.show();
		}

		var ajaxurl = _getRequestUrl(serverIndex) + action;
		$.ajax({
			//地址格式
			// url : '192.168.253.166',
			url : ajaxurl,
			type : 'get',
			dataType : 'jsonp',
			jsonp : "callback",
			// type : 'post',
			// dataType : 'test'
			contentType : "application/json;charset=UTF-8",
			cache : 'false',
			data : parameter,
			// scriptCharset : 'utf-8',
			success : function(jsonstr) {
				if (callback != null) {
					if (jsonstr.code == undefined)//业务异常
					{
						callback(jsonstr);
					} else {
						alert("业务异常");
					}
				}

				//TODO:是否去掉這罩
				// if (callback != null) {
				// //判断回调函数是否为空。
				// //TODO:处理回调
				// //根据返回的状态，做响应（0-成功，1-业务异常，2-系统异常）
				// if (jsonstr.code == 0) {
				// //成功
				// callback(jsonstr, jsonstr.returnData, jsonstr.code, '操作成功');
				// } else if (jsonstr.code == 1) {
				// //业务异常
				// callback(jsonstr, jsonstr.returnData, jsonstr.code, msg);
				// } else if (jsonstr.code == 2) {
				// alert(jsonstr.msg);
				// } else {
				// alert("出错啦！！！");
				// }
				// }
			},
			error : function(jqXHR, textStatus, errorThrown) {
				//TODO:隐藏弹出框
				//TODO:提示消息
				alert(JSON.stringify(jqXHR) + "," + textStatus + "," + errorThrown);
				//						alert(jqXHR.responseText);
				//						alert("error");
			}
		});
	};

	//ip,port,method,param
	var domain_request = function(_url, params, callback) {
		if (params != null) {
			params = _getFullParameter(params);
		} else {
			params = _getFullParameter({

			});
		}

		$.ajax({
			type : "POST",
			contentType : "application/x-www-form-urlencoded",
			dataType : "html",
			url : _url, //这里是网址
			data : params,
			cache : 'false',
			timeout : 30000,
			success : function(jsonstr) {
				if (callback != null) {
					if (jsonstr.code == undefined) {
						callback(jsonstr);
					} else {//业务异常
						alert("业务异常");
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
	};

	var domain_request_jsonp = function(_url, params, callback) {
		if (params != null) {
			params = _getFullParameter(params);
		} else {
			params = _getFullParameter({

			});
		}

		$.ajax({
			type : "get",
			dataType : 'jsonp',
			jsonp : "callback",
			contentType : "application/json;charset=UTF-8",
			url : _url, //这里是网址
			data : params,
			cache : 'false',
			timeout : 30000,
			success : function(jsonstr) {
				if (callback != null) {
					if (jsonstr.code == undefined) {
						callback(jsonstr);
					} else {//业务异常
						alert("业务异常");
					}
				}
			},
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				alert(errorThrown);
			}
		});
	};

	var _loadRes = function(serverIndex, action, callback) {
		$.ajax({
			url : action,
			type : 'get',
			dataType : 'html',
			jsonp : "callback",
			cache : 'false',
			success : function(jsonstr) {
				if (callback != null) {
					callback(jsonstr);
				}
			},
			error : function(jqXHR, textStatus, errorThrown) {
				// var info = "";
				// info += "网络请求错误:\n\n";
				// info += "url :" + _getRequestUrl(serverIndex) + action + "\n\n";
				// info += "status :" + XMLHttpRequest.status + "\n\n";
				// info += "textStatus :" + textStatus + "\n\n";
				// alert(info);
			},
		});
	};

	//从url中获取参数
	var _getQueryString = function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null)
			return unescape(r[2]);
		return null;
	};

	var api = {
		domain_request : function(_url, _param, callback) {
			domain_request(_url, _param, callback);
		},

		getRequestUrl : function(serverindex) {
			_getRequestUrl(serverindex);
		},

		domain_request_jsonp : function(_url, _param, callback) {
			domain_request_jsonp(_url, _param, callback);
		},

		getFullParameter : function(param) {
			_getFullParameter();
		},

		ajax_request : function(action, params, care, callback) {
			_request(false, 0, action, params, care, callback);
		},

		ajax_requestBlock : function(action, params, care, callback) {
			_request(true, 0, action, params, care, callback);
		},
		ajax_requestIndex : function(serverIndex, action, params, care, callback) {
			_request(false, serverIndex, action, params, care, callback);
		},
		requestIndexBlock : function(serverIndex, action, params, care, callback) {
			_request(true, serverIndex, action, params, care, callback);
		},
		loadRes : function(serverIndex, action, callback) {
			_loadRes(serverIndex, action, callback);
		},
		md5 : function(txt) {
			return hex_md5(txt);
		},
		getQueryString : function(name) {
			return _getQueryString(name);
		}
	};

	//将api挂在到window下，便于全局调用
	this.api = api;

}();
//立刻执行
