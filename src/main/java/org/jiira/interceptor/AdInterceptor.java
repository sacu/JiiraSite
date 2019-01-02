package org.jiira.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

public class AdInterceptor extends Interceptor{
	private static final Logger logger = LoggerFactory.getLogger(AdInterceptor.class);
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
		logger.error("preHandle");
		logger.error("URI : " + request.getRequestURI());
		if(check(request) && null == request.getSession().getAttribute("adUser")) {
			modelAndView.setViewName("redirect:/ad");
			logger.error("拦截");
		}
		logger.error("放行");
	}
	@Override
	protected String[] exclude() {//拦截
		return new String[] {"/ad/*"};
	}
	@Override
	protected String[] excep() {//例外
		return new String[] {"/ad/index"};
	}
}
