package org.jiira.service.impl;

import org.jiira.dao.WeConsumeDao;
import org.jiira.service.WeConsumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeConsumeServiceImpl implements WeConsumeService {

	@Autowired
	private  WeConsumeDao weConsumeDao = null;
	@Override
	public int ignoreWeConsume(String openid, int vouchers, String transaction_id) {
		// TODO Auto-generated method stub
		return weConsumeDao.ignoreWeConsume(openid, vouchers, transaction_id);
	}

}
