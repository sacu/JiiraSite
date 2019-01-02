package org.jiira.service.impl;

import org.jiira.dao.AdUserDao;
import org.jiira.pojo.Login;
import org.jiira.pojo.ad.AdUser;
import org.jiira.service.AdUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdUserServiceImpl implements AdUserService {
	@Autowired
	private AdUserDao adUserDao = null;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public AdUser getAdUser(Login login) {
		// TODO Auto-generated method stub
		return adUserDao.getAdUser(login.getUserName(), login.getPassWord());
	}
}
