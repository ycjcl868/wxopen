/**
 * 调用微信公众平台接口
 * @author jiejie.liao
 * @see http://mp.weixin.qq.com/wiki/home/index.html
 */
var wxopen = (function($,wxopen){
	wxopen.service = "../../activity/waprequest.htm";//服务地址
	wxopen.debug = true;//是否开启调试模式
	wxopen.timestamp = "";//生成签名的时间戳
	wxopen.nonceStr = "";//生成签名的随机串
	wxopen.signature = "";//签名
	wxopen.shareData = {};//分享内容
	//必填，需要使用的JS接口列表，所有JS接口列表见附录2
	wxopen.jsApiList = 
		['checkJsApi',
        'onMenuShareTimeline',
	    'onMenuShareAppMessage',
	    'onMenuShareQQ',
	    'onMenuShareWeibo',
	    'hideMenuItems',
	    'showMenuItems',
	    'hideAllNonBaseMenuItem',
	    'showAllNonBaseMenuItem',
	    'translateVoice',
	    'startRecord',
	    'stopRecord',
	    'onRecordEnd',
	    'playVoice',
	    'pauseVoice',
	    'stopVoice',
	    'uploadVoice',
	    'downloadVoice',
	    'chooseImage',
	    'previewImage',
	    'uploadImage',
	    'downloadImage',
	    'getNetworkType',
	    'openLocation',
	    'getLocation',
	    'hideOptionMenu',
	    'showOptionMenu',
	    'closeWindow',
	    'scanQRCode',
	    'chooseWXPay',
	    'openProductSpecificView',
	    'addCard',
	    'chooseCard',
	    'openCard'
	];
	
	/*微信API*/
	wxopen.API={
		oauth2:"https://open.weixin.qq.com/connect/oauth2/authorize"
	};
	
	/**
	 * 初始微信JS-SDK
	 */
	wxopen.initJSSDK=function(data,jsonp){
		wxopen.shareData = data||wxopen.shareData;//分享信息
		
		var data = {};
		data["interfaceCode"] = "weiXinOpen.config";
		data["appid"] = wxopen.shareData.appid;
		data["url"] = window.location.href;
		wxopen.callServ(wxopen.service,data,callServiceBack,jsonp);
		
		//回调函数
		function callServiceBack(data){
			wxopen.timestamp = data.body.timestamp;
			wxopen.nonceStr = data.body.nonceStr;
			wxopen.signature = data.body.signature;
			wxopen.config();
		}
	};
	
	/**
	 * OAuth2.0鉴权、网页授权登陆<br/>
	 * 微信登陆则跳转至微信授权登陆页面，否则不做任何处理
	 */
	wxopen.initOAuth2=function(data){
		if(wxopen.isWeiXin){
			window.location=wxopen.authorize(data);
		}
	};
	
	/**
	 * 通过config接口注入权限验证配置
	 */
	wxopen.config=function(){
		/*
		 * 通过config接口注入权限验证配置
		 */
		wx.config({
		    debug: wxopen.debug, //开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId:wxopen.shareData.appid, //必填，公众号的唯一标识
		    timestamp:wxopen.timestamp, //必填，生成签名的时间戳
		    nonceStr:wxopen.nonceStr, //必填，生成签名的随机串
		    signature:wxopen.signature,//必填，签名，见附录1
			jsApiList:wxopen.jsApiList//必填，需要使用的JS接口列表，所有JS接口列表见附录2
		});
		
		/*
		 * config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，
		 * config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。
		 * 对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		 */
		wx.ready(function () {
			wxopen.onMenuShareAppMessage();//获取“分享给朋友”按钮点击状态及自定义分享内容接口
			wxopen.onMenuShareTimeline()//获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
		});
		
		/*
		 * config信息验证失败会执行error函数，如签名过期导致验证失败，具体错误信息可以打开config的debug模式查看，
		 * 也可以在返回的res参数中查看，对于SPA可以在这里更新签名。
		 */
		wx.error(function (res) {
			if(wxopen.debug){
				alert(res.errMsg);
			}
		});
	};
	
	/**
	 * 获取“分享给朋友”按钮点击状态及自定义分享内容接口
	 */
	wxopen.onMenuShareAppMessage=function(){
		wx.onMenuShareAppMessage({
			title: wxopen.shareData.title,
			desc: wxopen.shareData.desc,
			link: wxopen.shareData.link,
			imgUrl: wxopen.shareData.imgUrl,
			trigger: function (res) {
				wxopen.doCallback('shareAppMessage',['trigger',res]);
			},
			success: function (res) {
				wxopen.doCallback('shareAppMessage',['success',res]);
			},
			cancel: function (res) {
				wxopen.doCallback('shareAppMessage',['cancel',res]);
			},
			fail: function (res) {
				wxopen.doCallback('shareAppMessage',['fail',res]);
			}
		});
	};
	
	/**
	 * 获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
	 */
	wxopen.onMenuShareTimeline=function(){
		wx.onMenuShareTimeline({
			title: wxopen.shareData.title,
			link: wxopen.shareData.link,
			imgUrl: wxopen.shareData.imgUrl,
			trigger: function (res) {
				wxopen.doCallback('shareTimeline',['trigger',res]);
			},
			success: function (res) {
				wxopen.doCallback('shareTimeline',['success',res]);
			},
			cancel: function (res) {
				wxopen.doCallback('shareTimeline',['cancel',res]);
			},
			fail: function (res) {
				wxopen.doCallback('shareTimeline',['fail',res]);
			}
		});
	};
	
	/**
	 * 用户同意授权，获取code、
	 * 微信环境则返回获取code链接，否则返回默认链接
	 */
	wxopen.authorize=function(data){
		var appid = data.appid||"";//公众号的唯一标识
		var redirectUri = data.redirectUri||"";//授权后重定向的回调链接地址，请使用urlencode对链接进行处理
		/*
		 * 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo 
		 * （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息）
		 */
		var scope = data.scope||"snsapi_base ";
		var state = data.state||"";//重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
		var defaultUri = data.defaultUri||"";//非微信浏览器打开默认Uri,若为空则不进行处理
		
		var url = "";
		if(wxopen.isWeiXin){
			var strBuf = new StringBuffer();
			strBuf.append(wxopen.API.oauth2)
				  .append("?").append("appid=").append(appid)
				  .append("&").append("redirect_uri=").append(encodeURIComponent(redirectUri))
				  .append("&").append("response_type=").append("code")
				  .append("&").append("scope=").append(scope)
				  .append("&").append("state=").append(state)
				  .append("#wechat_redirect");
			url = strBuf.toString();
		}else{
			url = defaultUri;
		}
		return url;
	};
	
	/**
	 * 拉取用户信息 snsapiBase
	 */
	wxopen.snsapiBase=function(appid,code,jsonp,fn){
		var data = {};
		data["interfaceCode"] = "weiXinOpen.snsapiBase";
		data["appid"] = appid;
		data["code"] = code;
		wxopen.callServ(wxopen.service,data,callServiceBack,jsonp);
		
		//回调函数
		function callServiceBack(data){
			var data = data.body||{};
			wxopen.doCallback(fn,[data]);
		}
	};
	
	/**
	 * 拉取用户信息 snsapiUserinfo
	 */
	wxopen.snsapiUserinfo =function(appid,code,jsonp,fn){
		var data = {};
		data["interfaceCode"] = "weiXinOpen.snsapiUserinfo";
		data["appid"] = appid;
		data["code"] = code;
		wxopen.callServ(wxopen.service,data,callServiceBack,jsonp);
		
		//回调函数
		function callServiceBack(data){
			var data = data.body||{};
			wxopen.doCallback(fn,[data]);
		}
	};
	
	/**
	 * 调用服务
	 */
	wxopen.callServ=function(service,param,callback,jsonp){
		jsonp = jsonp||false;
		if(jsonp){//跨域调用
			wxopen.callJSONPService(service,param,callback);
		}else{//非跨域调用
			wxopen.callService(service,param,callback);
		}
	};
	
	/**
	 * 跨域调用后台服务
	 */
	wxopen.callJSONPService=function(service,param,callback){
		var callback=callback||function(){};
		var param = param||{};
		param["jsonp"] = true;
		$.ajax({
			url : service,
			data:param,
			dataType:"jsonp",
			type : "get",
			jsonp: "callbackName",//传递给请求处理程序或页面的，用以获得jsonp回调函数名的参数名(默认为:callback)
			jsonpCallback:"success",//自定义的jsonp回调函数名称，默认为jQuery自动生成的随机函数名
			success : function(data){
				data = {head:{respCode:data.code,respDesc:data.message},body:data.data};
				callback.apply(this,[data]);
			},
			error:function(e,t){
				data = {head:{respCode:-1,respDesc:e.message},body:{}};
				callback.apply(this,[data]);
			}
		});
	};
	
	/**
	 * 调用后台服务
	 */
	wxopen.callService=function(service,param,callback){
		var callback=callback||function(){};
		$.ajax( {
			url : service,
			data:param,
			dataType:"json",
			type : "post",	
			success : function(data){
				data = {head:{respCode:data.code,respDesc:data.message},body:data.data};
				callback.apply(this,[data]);
			},
			error:function(e,t){
				data = {head:{respCode:-1,respDesc:e.message},body:{}};
				callback.apply(this,[data]);
			}
		});
	};
	
	/**
	 * 回调函数
	 */
	wxopen.doCallback=function(fn,args){
  		try {
  			if(typeof fn == 'undefined' || fn == '' || fn == null){
  				return;
  			}
  			if(typeof fn == 'function'){//方法存在
	  			fn.apply(this, args);
	  		}
  			if(typeof fn == 'string'){
  				fn = eval(fn);
  				fn.apply(this, args);
  			}
		} catch (e) {
			if(wxopen.debug){
				alert(e);
			}
		}
  	};
  	
  	/**
  	 * 判断是否微信环境
  	 */
  	wxopen.isWeiXin=function(){
  	    var ua = navigator.userAgent.toLowerCase();
  	    if(ua.match(/MicroMessenger/i)=="micromessenger") {
  	        return true;
  	    } else {
  	        return false;
  	    }
  	}();
  	
  	/**
  	 * 获取当前URL参数
  	 */
  	wxopen.getParam=function(name){
  	    var search = document.location.search;
  	    var pattern = new RegExp("[?&]"+name+"\=([^&]+)", "g");
  	    var matcher = pattern.exec(search);
  	    var items = null;
  	    if(null != matcher){
  	        try{
  	        	items = decodeURIComponent(decodeURIComponent(matcher[1]));
  	        }catch(e){
  	            try{
  	            	items = decodeURIComponent(matcher[1]);
  	            }catch(e){
  	            	items = matcher[1];
  	            }
  	        }
  	    }
  	    return items;
  	};
	
  	/**
  	 * 空校验
  	 */
  	wxopen.isEmpty=function(str){
  		return (str == null || str == "" || str == "undefined");
  	};
  	
  	/**
  	 * StringBuffer
  	 */
  	function StringBuffer() {
  		this._strings = new Array;     
	}
	StringBuffer.prototype.append = function(str){
		this._strings.push(str);
		return this;
	};
	StringBuffer.prototype.toString = function(){
	 	return this._strings.join("");
	};
  	
	return wxopen;
})($,{});