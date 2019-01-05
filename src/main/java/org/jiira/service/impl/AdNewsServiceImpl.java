package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdNewsDao;
import org.jiira.pojo.ad.AdNews;
import org.jiira.service.AdMateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdNewsServiceImpl implements AdMateService<AdNews> {

	@Autowired
	private AdNewsDao adNewsDao;
	@Override
	public List<AdNews> select() {
		// TODO Auto-generated method stub
		return adNewsDao.selectNews();
	}

	@Override
	public AdNews selectById(int id) {
		// TODO Auto-generated method stub
		return adNewsDao.selectNewsById(id);
	}

	@Override
	public int insert(AdNews adNews) {
		// TODO Auto-generated method stub
		return adNewsDao.insertNews(adNews);
	}

	@Override
	public int update(int id, String media_id) {
		// TODO Auto-generated method stub
		return adNewsDao.updateNews(id, media_id);
	}

	@Override
	public int update(int id, String media_id, String url) {
		// TODO Auto-generated method stub
		return adNewsDao.updateNewsAndUrl(id, media_id, url);
	}
	
	
	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return adNewsDao.deleteNews(id);
	}
	@Override
	public List<AdNews> selectOderByDesc(int limit) {
		// TODO Auto-generated method stub
		return adNewsDao.selectOderByDesc(limit);
	}

	@Override
	public List<AdNews> check(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdNews> check(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdNews> selectByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdNews selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ignore(String id, String title, String introduction) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int ignore(List<String> ids, String type) {
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
	public int delete(String id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdNews selectIVByMediaId(String media_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(String id, String media_id, String url) {
		// TODO Auto-generated method stub
		return 0;
	}


}
