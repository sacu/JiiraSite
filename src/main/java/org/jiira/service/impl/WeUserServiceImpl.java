package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.WeUserDao;
import org.jiira.pojo.ad.WeUser;
import org.jiira.service.WeUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeUserServiceImpl implements WeUserService {

	@Autowired
	private WeUserDao weUserDao = null;

	@Override
	public WeUser selectWeUser(String openid) {
		// TODO Auto-generated method stub
		return weUserDao.selectWeUser(openid);
	}
	
	@Override
	public List<String> selectAll(){
		return weUserDao.selectAll();
	}
	@Override
	public int updateWeUserBirthday(String openid, String birthday) {
		// TODO Auto-generated method stub
		return weUserDao.updateWeUserBirthday(openid, birthday);
	}

	@Override
	public int insertWeUser(WeUser weUser) {
		// TODO Auto-generated method stub
		return weUserDao.insertWeUser(weUser);
	}

	@Override
	public int deleteWeUser(String openid) {
		// TODO Auto-generated method stub
		return weUserDao.deleteWeUser(openid);
	}

	@Override
	public int updateWeUserVouchers(String openid, int vouchers) {
		// TODO Auto-generated method stub
		return weUserDao.updateWeUserVouchers(openid, vouchers);
	}
}
