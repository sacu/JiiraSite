package org.jiira;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.jiira.pojo.we.ai.voice.WeYTVoiceSay;
import org.jiira.pojo.we.pay.WePayRequest;
import org.jiira.utils.CommandCollection;
import org.jiira.we.DecriptUtil;
import org.jiira.we.WeGlobal;
import org.jiira.we.url.SAHttpKVO;

public class Launch {
	public static void main(String[] args) {
		ArrayList<SAHttpKVO> params = new ArrayList<>();
		params.add(new SAHttpKVO("appId", "wxbe33419389062baf"));
		String timeStamp = "1547627557";
		params.add(new SAHttpKVO("timeStamp", timeStamp));
		params.add(new SAHttpKVO("nonceStr", "65b36bb90f8b4dddae6cb41a2aabece3"));
		params.add(new SAHttpKVO("package", "prepay_id=wx16163235698182987a9554190336872441"));
		params.add(new SAHttpKVO("signType", "MD5"));
		System.out.println(DecriptUtil.ReqSignPay(params, false));
	}
}
