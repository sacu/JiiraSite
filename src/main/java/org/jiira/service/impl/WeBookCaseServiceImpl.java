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
	public WeBookCase selectWeBookCase(String openid, int newsid) {
		return weBookCaseDao.selectWeBookCase(openid, newsid);
	}
	@Override
	public int insertWeBookCase(WeBookCase weBookCase) {
		return weBookCaseDao.insertWeBookCase(weBookCase);
	}
	@Override
	public int ignoreWeBookCase(WeBookCase weBookCase) {
		// TODO Auto-generated method stub
		return weBookCaseDao.ignoreWeBookCase(weBookCase);
	}
	@Override
	public int updateBookCaseForRead(String openid, int name_id, int news_id) {
		// TODO Auto-generated method stub
		return weBookCaseDao.updateBookCaseForRead(openid, name_id, news_id);
	}
	@Override
	public WeBookCase selectBookCaseForRead(String openid, int nameid) {
		// TODO Auto-generated method stub
		return weBookCaseDao.selectBookCaseForRead(openid, nameid);
	}
}
