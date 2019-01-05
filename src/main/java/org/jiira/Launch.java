package org.jiira;

public class Launch {
	public static void main(String[] args) {
//		"/a/b", "/a/b/*", "/d/c/*", "/d/c"
		MyInterceptor a = new MyInterceptor();
//		System.out.println(!a.check("/d")?"通过":"未通过"
		
		String url = "https://mp.weixin.qq.com/s?__biz=Mzg5MzAwOTYyOA==&mid=100000054&idx=1&sn=b3ffe3184db7668c978ba97326d1b822&chksm=40342a857743a39355694360893f4a73b7098397d6076cb2244f7d4341e5940a25bf74e0e01a&token=1728691950&lang=zh_CN#rd";
		System.out.println(url.length());
	}
}
