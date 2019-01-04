package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdIVDao;
import org.jiira.pojo.ad.AdIV;
import org.jiira.service.AdMateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdIVServiceImpl implements AdMateService<AdIV> {

	@Autowired
	private AdIVDao adIVDao = null;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<AdIV> check(List<String> ivs) {
		// TODO Auto-generated method stub
		return adIVDao.checkIV(ivs);
	}

	@Override
	public int ignore(List<String> ivs, String type) {
		// TODO Auto-generated method stub
		return adIVDao.ignoreIV(ivs, type);
	}

	@Override
	public List<AdIV> selectByType(String type) {
		// TODO Auto-generated method stub
		return adIVDao.selectIVs(type);
	}

	@Override
	public AdIV selectById(String iv) {
		// TODO Auto-generated method stub
		return adIVDao.selectIV(iv);
	}

	@Override
	public int update(String image, String media_id, String url) {
		// TODO Auto-generated method stub
		return adIVDao.updateIV(image, media_id, url);
	}

	@Override
	public int delete(String iv) {
		// TODO Auto-generated method stub
		return adIVDao.deleteIV(iv);
	}

	@Override
	public List<AdIV> check(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdIV> select() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ignore(String id, String title, String introduction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ignore(List<String> voices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String id, String media_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdIV selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(AdIV ad) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(int id, String media_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

}
