package org.jiira;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jiira.utils.CommandCollection;

public class Launch {
	public static void main(String[] args) {
//		"/a/b", "/a/b/*", "/d/c/*", "/d/c"
//		System.out.println(!a.check("/d")?"通过":"未通过"
		
		try {
			System.out.println(URLEncoder.encode(CommandCollection.WEB_NAME + "we/user", "utf-8"));
			System.out.println(URLEncoder.encode("http://188.131.228.192/we/redirect?redirect=we/user", "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
