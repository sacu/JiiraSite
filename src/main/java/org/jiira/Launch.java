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
		String xml = "<xml>\r\n" + 
				"	<appid><![CDATA[wxbe33419389062baf]]></appid>\r\n" + 
				"	<bank_type><![CDATA[CFT]]></bank_type>\r\n" + 
				"	<cash_fee><![CDATA[1]]></cash_fee>\r\n" + 
				"	<fee_type><![CDATA[CNY]]></fee_type>\r\n" + 
				"	<is_subscribe><![CDATA[Y]]></is_subscribe>\r\n" + 
				"	<mch_id><![CDATA[1523670331]]></mch_id>\r\n" + 
				"	<nonce_str><![CDATA[7eb8823d75814f0aaf3f282adea70010]]></nonce_str>\r\n" + 
				"	<openid><![CDATA[o3JwK6I-dL64v6LRb_RSLR_xsEiI]]></openid>\r\n" + 
				"	<out_trade_no><![CDATA[1547696835]]></out_trade_no>\r\n" + 
				"	<result_code><![CDATA[SUCCESS]]></result_code>\r\n" + 
				"	<return_code><![CDATA[SUCCESS]]></return_code>\r\n" + 
				"	<sign><![CDATA[CF6071CCCC4842D5E21D93480C058F65]]></sign>\r\n" + 
				"	<time_end><![CDATA[20190117114719]]></time_end><total_fee>1</total_fee>\r\n" + 
				"	<trade_type><![CDATA[JSAPI]]></trade_type>\r\n" + 
				"	<transaction_id><![CDATA[4200000257201901171410741999]]></transaction_id>\r\n" + 
				"</xml>";
		// 解析xml成map
	    SAXReader saxReader = new SAXReader();
	    Document document = null;
		try {
			document = saxReader.read(new ByteArrayInputStream(xml.getBytes()));
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        Map<String, Object> m = XMLUtil.Dom2Map(document);
	    // 过滤空 设置 TreeMap
	    SortedMap<String, Object> packageParams = new TreeMap<String, Object>();
	    Iterator<String> it = m.keySet().iterator();
	    while (it.hasNext()) {
	        String parameter = (String) it.next();
	        Object parameterValue = m.get(parameter);
	        String v = "";
	        if (null != parameterValue) {
	            v = ((String) parameterValue).trim();
	        }
	        packageParams.put(parameter, v);
	    }
		
		Set<Entry<String, Object>> entry1=packageParams.entrySet();
		Iterator<Entry<String, Object>> aaa = entry1.iterator();
		ArrayList<SAHttpKVO> params = new ArrayList<>();
		while(aaa.hasNext())
		{
			Entry<String, Object> entry = aaa.next();
			if(!entry.getKey().equals("sign")) {
				params.add(new SAHttpKVO(entry.getKey(), entry.getValue().toString()));
			}
		}
		System.out.print(DecriptUtil.ReqSignPay(params, false));
	}
}
