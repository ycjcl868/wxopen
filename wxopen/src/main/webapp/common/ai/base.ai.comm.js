var AI_BASE = {};

AI_BASE.date_ymd=/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-))$/;

AI_BASE.date_ymdhms=/^((((1[6-9]|[2-9]\d)\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\d|3[01]))|(((1[6-9]|[2-9]\d)\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\d|30))|(((1[6-9]|[2-9]\d)\d{2})-0?2-(0?[1-9]|1\d|2[0-8]))|(((1[6-9]|[2-9]\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\d):[0-5]?\d:[0-5]?\d$/;

AI_BASE.number=/^([0-9]+)$/;

AI_BASE.money=/^((([1-9][0-9]*)|([0-9]+\.[0-9]{1,2}))|(-([1-9][0-9]*)|([0-9]+\.[0-9]{1,2})))$/;

AI_BASE.mobile=/^1\d{10}$/;

/**
 * 异步ajax请求方法
 * 
 * @author jiejie.liao
 * @param service 服务地址
 * @param param json格式请求参数
 * @param callback 回调函数
 * @returns 无返回值
 * 
 */
AI_BASE.callService=function(service,param,callback){
	var callback=callback||function(){};
	$.ajax( {
		url : service,
		data:param,
		dataType:"json",
		type : "post",	
		success : function(data){
			data = {head:{respCode:data.code,respDesc:data.message},body:data.data};
			callback.apply(AI_BASE,[data]);
		},
		error:function(e,t){
			data = {head:{respCode:-1,respDesc:e.message},body:{}};
			callback.apply(AI_BASE,[data]);
		}
	});
};

/**
 * 同步ajax请求方法
 * 
 * @author jiejie.liao
 * @param service 服务地址
 * @param param json格式请求参数
 * @param callback 回调函数
 * @returns 无返回值
 * 
 */
AI_BASE.callSynService=function(service,param,callback){
	var callback=callback||function(){};
	$.ajax( {
		url : service,
		data:param,
		dataType:"json",
		type : "post",
		async : false,
		success : function(data){
			data = {head:{respCode:data.code,respDesc:data.message},body:data.data};
			callback.apply(AI_BASE,[data]);
		},
		error:function(e,t){
			data = {head:{respCode:-1,respDesc:e.message},body:{}};
			callback.apply(AI_BASE,[data]);
		}
	});
};

/**
 * 分页方法
 * 
 * @author jiejie.liao
 * @param container div容器ID,
 * @param count 记录总数
 * @param pageIndex 页面索引 从0开始
 * @param pageSize 页面大小
 * @param callback 回调函数
 * @returns 无返回值
 * 
 */
AI_BASE.pagination=function(container,count,pageIndex,pageSize,callback){
	var callback=callback||function(){};
	var pageSize=pageSize||20;
	$("#"+container).pagination(count, {
	    first_text: '第一页', 		//第一页
		prev_text: '上一页',          //上一页按钮里text
		next_text: '下一页',          //下一页按钮里text
		last_text: '最后一页',			//最后一页
		items_per_page: pageSize,   //显示条数
		current_page: pageIndex,    //当前页索引
		num_display_entries:8,      //连续分页主体部分分页条目数
		num_edge_entries:2,         //两侧首尾分页条目数
		callback: function(page_index, jq){
			var data = {firstResult:page_index*pageSize,maxResults:pageSize,currPage:page_index};
			callback.apply(AI_BASE,[data]);
		}
	});
};

/**
 * 校验字符是否为空，排除 null，“”，undefined
 * 
 * @author jiejie.liao
 * @param str 需要校验的字符
 * @returns {Boolean} ：true 空 false 非空
 * 
 */
AI_BASE.isEmpty=function(str){
	return (str == null || str == "" || str == "undefined");
};

/**
 * 校验字符是否不为空，排除 null，“”，undefined
 * 
 * @author jiejie.liao
 * @param str 需要校验的字符
 * @returns {Boolean} ：true 不空 false 空
 * 
 */
AI_BASE.isNotEmpty=function(str){
	return !AI_BASE.isEmpty(str);
};

/**
 * 校验是否数字，特别说明开头为0校验不过、空校验不过
 * 
 * @author jiejie.liao
 * @参数 {str} 需要校验的字符,
 * @返回 {Boolean} ：数字返回true,否则false
 */
AI_BASE.isNumerical=function(str){
	if(AI_BASE.isEmpty(str)){
		return false;
	}
	return AI_BASE.number.test(str);
};

/**
 * 校验是否不为数字，特别说明开头为0校验不过、空校验不过
 * 
 * @author jiejie.liao
 * @参数 {str} 需要校验的字符,
 * @返回 {Boolean} ：数字返回false,否则true
 */
AI_BASE.isNotNumerical=function(str){
	return !AI_BASE.isNumerical(str);
};

/**
 * 校验对象是否为空
 * 
 * @author jiejie.liao
 * @参数 {obj} 需要校验的对象.
 * @返回 {Boolean} ：true 空 false 非空
 */
AI_BASE.isEmptyObj=function(obj){
	for (var name in obj) {
		return false;
	}
	return true;
};

/**
 * 校验对象是否非空
 * 
 * @author jiejie.liao
 * @param obj 需要校验的对象.
 * @returns {Boolean} ：true 不空 false 空
 * 
 */
AI_BASE.isNotEmptyObj=function(obj){
	return !AI_BASE.isEmptyObj(obj);
};

/**
 * 去除字符空格
 * 
 * @author jiejie.liao
 * @param str 字符
 * @returns str 字符
 * 
 */
AI_BASE.trim=function(str){
	if(AI_BASE.isEmpty(str)){
		return "";
	}
	return $.trim(str);
};

/**
 * 将整形字符变成带两位小数字符
 * 
 * @author jiejie.liao
 * @param str 字符
 * @returns str 字符
 * 
 */
AI_BASE.float=function(str){
	str = AI_BASE.trim(str);
	if(AI_BASE.isEmpty(str)){
		return "0.00";
	}
	if(str == 0 || str == "0"){
		return "0.00";
	}
	if(AI_BASE.isNumerical(str)){
		return str + ".00";
	}
	return str;
};

/**
 * 校验时间格式
 * 
 * @author jiejie.liao
 * @param format 格式
 * @param date 时间字符
 * @returns {Boolean} ：true 正确 false 错误
 * 
 */
AI_BASE.isDate=function(format,date){
	return format.test(date);
};

/**
 * 校验金额格式是否正确
 * 
 * @author jiejie.liao
 * @param str 金额： 非0开头整数|非0开头负整数|(-)小数点仅两位
 * @returns {Boolean} ：true 正确 false 错误
 * 
 */
AI_BASE.isMoney=function(str){
	if(AI_BASE.isEmpty(str)){
		return false;
	}
	return AI_BASE.money.test(str);
};

/**
 * 校验金额格式是否正确
 * 
 * @author jiejie.liao
 * @param str 金额： 非0开头整数|非0开头负整数|(-)小数点仅两位
 * @returns {Boolean} ：true 不是金额 false 是金额
 * 
 */
AI_BASE.isNotMoney=function(str){
	return !AI_BASE.isMoney(str);
};

/**
 * 校验手机号码格式是否正确
 * 
 * @author jiejie.liao
 * @param str 手机号吗： 简单校验 1 开头并且保证11位
 * @returns {Boolean} ：true 正确 false 错误
 * 
 */
AI_BASE.isMobile=function(str){
	if(AI_BASE.isEmpty(str)){
		return false;
	}
	return AI_BASE.mobile.test(str);
};

/**
 * 
 * 对浮点数进行四舍五入，保留小数点后两位
 * @author jiejie.liao
 * @param amount 金额
 * 
 */
AI_BASE.currencyFormatted=function(amount) {
   var i = parseFloat(amount);
   if(isNaN(i)) {
	   i = 0.00;
   }
   
   var minus = '';
   if(i < 0) {
	   minus = '-';
   }
   
   i = Math.abs(i);
   i = parseInt((i + .005) * 100);
   i = i / 100;
   
   s = new String(i); 
   if(s.indexOf('.') < 0) {
	   s += '.00';
   }
   if(s.indexOf('.') == (s.length - 2)) {
	   s += '0';
   }
   
   s = minus + s; 
   return s; 
};

/**
 * 
 * 对金额进行千分分割
 * @author jiejie.liao
 * @param amount 金额
 * 
 */
AI_BASE.formatAmount=function(s){
    if(/[^0-9\.]/.test(s)){
    	return s;
    }
    
    s=s.replace(/^(\d*)$/,"$1.");
    s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
    s=s.replace(".",",");
    var re=/(\d)(\d{3},)/;
    while(re.test(s))
        s=s.replace(re,"$1,$2");
    s=s.replace(/,(\d\d)$/,".$1");
    return s.replace(/^\./,"0.");
};

/**
 * 
 * 对金额进行千分分割
 * @author jiejie.liao
 * @param amount 金额
 * 
 */
AI_BASE.onKeyPrice=function(t){
	var stmp = "";
	if(t.value==stmp) return;
	var ms = t.value.replace(/[^\d\.]/g,"")
  				  .replace(/(\.\d{2}).+$/,"$1")
  				  .replace(/^0+([1-9])/,"$1")
  				  .replace(/^0+$/,"0");
  var txt = ms.split(".");
  while(/\d{4}(,|$)/.test(txt[0]))
    txt[0] = txt[0].replace(/(\d)(\d{3}(,|$))/,"$1,$2");
  t.value = stmp = txt[0]+(txt.length>1?"."+txt[1]:"");
};

/**
 * 比较结束时间是否大于等于开始时间
 * 
 * @author jiejie.liao
 * @param format 日期格式
 * @param sDate 开始时间
 * @param eDate 结束时间
 * @returns {Boolean}
 * 
 */
AI_BASE.compareDate=function(format,sDate,eDate){
	if(AI_BASE.isEmpty(sDate)||AI_BASE.isEmpty(eDate)){//必须全部不为空，才能通过
		return false;
	}
	if(!AI_BASE.isDate(format,sDate)||!AI_BASE.isDate(format,eDate)){
		return false;
	}
	return new Date(eDate.replace("-", "/").replace("-", "/")) >= new Date(sDate.replace("-", "/").replace("-", "/"));
};

/**
 * 为特定ID绑定事件
 * 
 * @author jiejie.liao
 * @param id 
 * @param event 时间
 * @param fn 方法
 * 
 */
AI_BASE.bindEvent=function(id,event,fn){
	$("#"+id).unbind(event,fn);//先移除再绑定
	$("#"+id).bind(event,fn);
};

/**
 * 为特定ID解除绑定事件
 * 
 * @author jiejie.liao
 * @param id 
 * @param event 时间
 * @param fn 方法
 * 
 */
AI_BASE.unbindEvent=function(id,event,fn){
	$("#"+id).unbind(event,fn);
};

/**
 * 为特定ID绑定onChange事件
 * 
 * @author jiejie.liao
 * @param id 
 * @param control 控件
 * @param fn 方法
 * 
 */
AI_BASE.onChange=function(id,control,fn){
	$("#"+id).on(control,fn);
};

/**
 * 定时执行函数
 * 
 * @author jiejie.liao
 * @param fn 函数 
 * @param time 时间
 * 
 */
AI_BASE.timing=function(fn,time){
	setTimeout(fn,time);
};

/**
 * 获取当前URL参数
 * 
 * @author jiejie.liao
 * @param name 参数名
 * @returns 参数值
 * 
 */
AI_BASE.getParam=function(name){
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
 * 公共分享API
 * 
 * @author jiejie.liao
 * @param target 0 新浪微博、1 腾讯微博、2 QQ空间、3 人人网、4 开心网、5 豆瓣
 * @param img
 * @param content
 * @param url
 * @returns 分享URL
 * 
 */
AI_BASE.shareApi=function(target, img, content, url) {
	img = encodeURIComponent(img)||img;
	content = encodeURI(content)||content;
	url = encodeURIComponent(url)||url;
	switch (target) {
		case 0:
			return "http://v.t.sina.com.cn/share/share.php?pic=" + img + "&title="
					+ content + "&url=" + url + "&rcontent=";
		case 1:
			return "http://v.t.qq.com/share/share.php?title=" + content + "&pic="
					+ img + "&url=" + url;
		case 2:
			return "http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?title="
					+ content + "&pics=" + img + "&url=" + url;
		case 3:
			return "http://share.renren.com/share/buttonshare.do?title=" + content
					+ "&link=" + url + "&rcontent=";
		case 4:
			return "http://www.kaixin001.com/repaste/share.php?rtitle=" + content
					+ "&rurl=" + url + "&rcontent=";
		case 5:
			return "http://www.douban.com/recommend/?title=" + content + "&url="
					+ url + "&rcontent=";
		default:
			return "http://v.t.sina.com.cn/share/share.php?pic=" + img + "&title="
					+ content + "&url=" + url + "&rcontent=";
	}
};

AI_BASE.index=function(s1, s2, time){
    if(time == 1)
        return s1.indexOf(s2);
    else
        return s1.indexOf(s2, AI_BASE.index(s1, s2, time - 1) + 1);
};

AI_BASE.location=function(){
	var protocol = window.location.protocol;
	var host = window.location.host;
	var puls = window.location.pathname.substr(1,AI_BASE.index(window.location.pathname,"/",2));
	return protocol + "//" + host + "/" + puls;
};