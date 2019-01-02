package org.jiira.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;


public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	// Spring IoC环境配置
	@Override
	protected Class<?>[] getRootConfigClasses() {
		// 配置Spring IoC资源
		return new Class<?>[] { RootConfig.class };
	}

	// DispatcherServlet环境配置
	@Override
	protected Class<?>[] getServletConfigClasses() {
		// 加载Java配置类
		return new Class<?>[] { WebConfig.class };
	}

	// DispatchServlet拦截请求配置,应该拦截的是后缀
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
//		return new String[] { "*.sa" };
	}
}
