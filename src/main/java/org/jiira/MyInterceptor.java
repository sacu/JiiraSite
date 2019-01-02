package org.jiira;

import org.jiira.interceptor.Interceptor;

public class MyInterceptor extends Interceptor {
	@Override
	protected String[] exclude() {//拦截
		return new String[] {"/a/b", "/a/b/*", "/d/c/*", "/d/c"};
	}
	@Override
	protected String[] excep() {//例外
		return new String[] {"/a/b", "/a/b/*"};
	}
}
