package org.jiira.service.impl;

import org.jiira.dao.WeBookCaseDao;
import org.jiira.pojo.ad.WeBookCase;
import org.jiira.service.WeBookCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeBookCaseServiceImpl implements WeBookCaseService {

	@Autowired
	private WeBookCaseDao weBookCaseDao = null;

	@Override
	public WeBookCase selectWeBookCase(String openid) {
		return weBookCaseDao.selectWeBookCase(openid);
	}
	@Override
	public int insertWeBookCase(WeBookCase weBookCase) {
		return weBookCaseDao.insertWeBookCase(weBookCase);
	}
}
