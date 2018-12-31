package org.jiira.we;

import java.util.Comparator;

public class StrComparator implements Comparator<String> {
	@Override
	public int compare(String o1, String o2) {
		try {
			// 取得比较对象的汉字编码，并将其转换成字符串
			String s1 = new String(o1.getBytes("GB2312"), "ISO-8859-1");
			String s2 = new String(o2.getBytes("GB2312"), "ISO-8859-1");
			// 运用String类的 compareTo（）方法对两对象进行比较
			return s1.compareTo(s2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
