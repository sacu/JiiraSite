package org.jiira.config;

import org.springframework.context.ApplicationContext;

public class Application {

	private ApplicationContext applicationContext = null;
	private static Application instance;
	public static Application getInstance() {
		if(null == instance) {
			instance = new Application();
		}
		return instance;
	}
	private Application() {}
	public void setApplicationContext(ApplicationContext applicationContext) {
		System.out.print("初始化了###########################");
		this.applicationContext = applicationContext;
	}
	public ApplicationContext getContext() {
		return applicationContext;
	}
	public <T> T getBean(Class<T> name) {
		return applicationContext.getBean(name);
	}
}
