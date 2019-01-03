package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdIVDao;
import org.jiira.pojo.ad.AdIV;
import org.jiira.service.AdIVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdIVServiceImpl implements AdIVService {

	@Autowired
	private AdIVDao adIVDao = null;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<AdIV> checkIV(List<String> ivs) {
		// TODO Auto-generated method stub
		return adIVDao.checkIV(ivs);
	}

	@Override
	public int ignoreIV(List<String> ivs, String type) {
		// TODO Auto-generated method stub
		return adIVDao.ignoreIV(ivs, type);
	}

	@Override
	public List<AdIV> selectIVs(String type) {
		// TODO Auto-generated method stub
		return adIVDao.selectIVs(type);
	}

	@Override
	public AdIV selectIV(String iv) {
		// TODO Auto-generated method stub
		return adIVDao.selectIV(iv);
	}

	@Override
	public int updateIV(String Image, String media_id, String url) {
		// TODO Auto-generated method stub
		return adIVDao.updateIV(Image, media_id, url);
	}

	@Override
	public int deleteIV(String iv) {
		// TODO Auto-generated method stub
		return adIVDao.deleteIV(iv);
	}

}
