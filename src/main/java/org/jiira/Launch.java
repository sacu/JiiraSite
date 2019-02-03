package org.jiira;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.jiira.utils.XMLUtil;
import org.jiira.we.DecriptUtil;
import org.jiira.we.url.SAHttpKVO;

public class Launch {
	public static void main(String[] args) {
		js(3);
		js(10);
		js(50);
		js(100);
		js(300);
		js(500);
	}
	private static void js(int money) {
		int extra = (int)(Math.floor(money / 10.0f) * 100);//先获取每多10元的奖励10
		extra += (int)(Math.floor(money / 50.0f) * 1000);//再获取每多50元的奖励50
		money = money * 100 + extra;//最后充值
		System.out.println("充值金额 : " + money);
	}
}
