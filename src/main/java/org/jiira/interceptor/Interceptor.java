package org.jiira.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class Interceptor extends HandlerInterceptorAdapter {
	private Exc[] excludes;
	private Exc[] exceps;//例外
	public Interceptor() {
		String[] excs = exclude();
		int i = 0;
		excludes = new Exc[excs.length];
		for(;i < excs.length; ++i) {
			excludes[i] = new Exc(excs[i]);
		}
		excs = excep();
		exceps = new Exc[excs.length];
		for(i = 0;i < excs.length; ++i) {
			exceps[i] = new Exc(excs[i]);
		}
	}
	
	protected String[] exclude() {//拦截
		return new String[] {};
	}
	protected String[] excep() {//例外
		return new String[] {};
	}
	/**
	 * 测试用的
	 * @param uri
	 * @return
	 */
	public boolean check(String uri) {
		return true;
	}
	/**
	 * 检测入口
	 * @return
	 */
	protected boolean check(HttpServletRequest request) {
		//检测当前uri是否被拦截
		String uri = request.getRequestURI();
		//如果被拦截则返回true
		//全部否决则是未被拦截
		return !(checkExcep(uri) || !checkExclude(uri));
		
	}
	/**
	 * 检测拦截
	 * @param request
	 * @return
	 */
	private boolean checkExclude(String uri) {
		Exc exc;
		int end;
		for(int i = 0; i < excludes.length; ++i) {
			exc = excludes[i];
			if(exc.isFile && exc.uri.equals(uri)) {
				return true;
			} else if(uri.length() > exc.uri.length()) {//没有拦截地址长，则肯定不存在拦截地址
				end = exc.uri.length();
				if(uri.substring(0, end).equals(exc.uri)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 检测例外
	 * @return
	 */
	private boolean checkExcep(String uri) {
		String dir = uri.substring(0, uri.lastIndexOf('/'));
		Exc exc;
		for(int i = 0; i < exceps.length; ++i) {
			exc = exceps[i];
			if(exc.isFile && exc.uri.equals(uri)) {
				return true;
			} else if(exc.uri.equals(dir)) {
				return true;
			}
		}
		return false;
	}
	class Exc {
		public Exc(String uri) {
			int end = uri.length();
			if(isFile = !uri.substring(end - 1).equals("*")) {//最后一个是不是*
				this.uri = uri;
			} else {
				this.uri = uri.substring(0, end - 1);
			}
			
		}
		public boolean isFile;//是否为文件
		public String uri;//路径
	}
}

