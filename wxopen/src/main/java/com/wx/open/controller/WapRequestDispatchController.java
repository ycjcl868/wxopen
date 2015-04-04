package com.wx.open.controller;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wx.open.common.exception.BusiException;
import com.wx.open.common.util.HttpServletHelper;
import com.wx.open.common.util.StringUtil;
import com.wx.open.controller.event.WeiXinOpenAction;
import com.wx.open.controller.frame.ServiceInput;
import com.wx.open.controller.frame.ServiceMethod;
import com.wx.open.controller.frame.ServiceRegister;
import com.wx.open.controller.frame.ServiceResult;

/**
 * <pre>
 *  wap请求统一分发器
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
@Controller("wapRequestDispatchController")
public class WapRequestDispatchController {
	private static Logger logger = LoggerFactory.getLogger(WapRequestDispatchController.class);
	
	private static final String charsetName = "utf-8";
	
	@Autowired
	private ApplicationContext applicationContext;
	
	@PostConstruct
	public void init() throws ServletException {
		try {
			logger.info("WapRequestDispatchController.init ....");
			ServiceRegister.register(WeiXinOpenAction.class,applicationContext);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new ServletException(e);
		}
	}
	
	@RequestMapping("/activity/waprequest.htm")
	public ModelAndView doAction(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ServiceInput ecin = new ServiceInput();//入参
		ServiceResult result = null;//出参
		try {
			Map<String, Object> text = HttpServletHelper.read(request);
        	if(!StringUtil.isEmpty(text)){
        		ecin.decode(text);
        	}else{
        		ecin.decode(HttpServletHelper.read(request, "UTF-8"));
        	}
			logger.info("request message:{}",new String[]{ecin.getBusiParam().toJSONString()});
			
			ServiceMethod service = ServiceRegister.getService(ecin.getInterfaceCode());
			if(service == null){
				throw new RuntimeException("InterfaceCode【"+ecin.getInterfaceCode()+"】 is not exist");
			}
			
			result = (ServiceResult) service.getMethod().invoke(service.getInst(), ecin.getBusiParam());
			if (result == null) {
				throw new Exception("HBServiceResult is null");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			result = new ServiceResult();
			if (e.getCause() != null) {
				result.setError(e.getCause().getMessage());
			} else {
				result.setError(e.getMessage());
			}
			if (e.getCause() instanceof BusiException){
				result.setResult(((BusiException)e.getCause()).getRespCode());
			}
		} finally {
			try {
				result.setValue("callbackName", ecin.getCallbackName());
				logger.info("response message:" + result.toJSONString());
				if(ecin.getJsonp()){
					HttpServletHelper.write(response, result.toJSONNpString(ecin.getCallbackName()), charsetName);
				}else{
					HttpServletHelper.write(response, result.toJSONString(), charsetName);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(),e);
			}
		}
		return null;
	}
	
}
