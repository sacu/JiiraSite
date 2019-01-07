package org.jiira.config;

import java.util.List;

import org.jiira.service.WeUserService;
import org.jiira.utils.CommandCollection;
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
		//在这里初始化一下USER_OPENID
		WeUserService weUserService = getBean(WeUserService.class);
		List<String> openids = weUserService.selectAll();
		for(int i = 0; i < openids.size(); ++i) {
			CommandCollection.PutOpenID(openids.get(i));
		}
	}
	public ApplicationContext getContext() {
		return applicationContext;
	}
	public <T> T getBean(Class<T> name) {
		return applicationContext.getBean(name);
	}
}
