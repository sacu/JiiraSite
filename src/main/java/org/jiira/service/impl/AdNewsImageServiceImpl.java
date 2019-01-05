package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdNewsImageDao;
import org.jiira.pojo.ad.AdNewsImage;
import org.jiira.service.AdMateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdNewsImageServiceImpl implements AdMateService<AdNewsImage> {

	@Autowired
	private AdNewsImageDao adNewsImageDao = null;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<AdNewsImage> check(List<String> newsImages) {
		// TODO Auto-generated method stub
		return adNewsImageDao.checkNewsImage(newsImages);
	}

	@Override
	public int ignore(List<String> newsImages) {
		// TODO Auto-generated method stub
		return adNewsImageDao.ignoreNewsImage(newsImages);
	}

	@Override
	public List<AdNewsImage> select() {
		// TODO Auto-generated method stub
		return adNewsImageDao.selectNewsImages();
	}

	@Override
	public AdNewsImage selectById(String newsImage) {
		// TODO Auto-generated method stub
		return adNewsImageDao.selectNewsImage(newsImage);
	}

	@Override
	public int update(String newsImage, String url) {
		// TODO Auto-generated method stub
		return adNewsImageDao.updateNewsImage(newsImage, url);
	}

	@Override
	public int delete(String newsImage) {
		// TODO Auto-generated method stub
		return adNewsImageDao.deleteNewsImage(newsImage);
	}

	@Override
	public List<AdNewsImage> check(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ignore(String id, String title, String introduction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String id, String media_id, String url) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AdNewsImage> selectByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ignore(List<String> ids, String type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdNewsImage selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(AdNewsImage ad) {
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

	@Override
	public List<AdNewsImage> selectOderByDesc(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdNewsImage selectIVByMediaId(String media_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(int id, String media_id, String url) {
		// TODO Auto-generated method stub
		return 0;
	}

}
