package org.jiira.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class WeInterceptor extends Interceptor{
	private static final Logger logger = LoggerFactory.getLogger(WeInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//继续执行方法
		return true;
	}/**
	 * This implementation is empty.
	 */
	@Override
	public void postHandle(
			HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
					throws Exception {
		String ua = request.getHeader("User-Agent").toLowerCase();
//		 && null == request.getSession().getAttribute("weUser")
		if(ua.indexOf("micromessenger") == -1 && check(request)) {
			modelAndView.setViewName("redirect:/we/error");
//			logger.error("拦截 : " + request.getRequestURI());
		} else {
//			logger.error("放行");
		}
	}
	@Override
	protected String[] exclude() {//拦截
		return new String[] {"/we/*"};
	}
	@Override
	protected String[] excep() {//例外
		return new String[] {"/we/error", "/we/c", "/we/redirect", "/we/callpay"};
	}
}
