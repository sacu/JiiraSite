package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdVideoDao;
import org.jiira.pojo.ad.AdVideo;
import org.jiira.service.AdMateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdVideoServiceImpl implements AdMateService<AdVideo> {

	@Autowired
	private AdVideoDao adVideoDao = null;

	@Override
	public List<AdVideo> check(String videos) {
		// TODO Auto-generated method stub
		return adVideoDao.checkVideo(videos);
	}

	@Override
	public List<AdVideo> select() {
		// TODO Auto-generated method stub
		return adVideoDao.selectVideos();
	}

	@Override
	public AdVideo selectById(String video) {
		// TODO Auto-generated method stub
		return adVideoDao.selectVideo(video);
	}

	@Override
	public int ignore(String video, String title, String introduction) {
		// TODO Auto-generated method stub
		return adVideoDao.ignoreVideo(video, title, introduction);
	}

	@Override
	public int update(String video, String media_id) {
		// TODO Auto-generated method stub
		return adVideoDao.updateVideo(video, media_id);
	}

	@Override
	public int delete(String video) {
		// TODO Auto-generated method stub
		return adVideoDao.deleteVideo(video);
	}

	@Override
	public List<AdVideo> check(List<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ignore(List<String> voices) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String id, String media_id, String url) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<AdVideo> selectByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int ignore(List<String> ids, String type) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdVideo selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(AdVideo ad) {
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
	public List<AdVideo> selectOderByDesc(int limit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AdVideo selectIVByMediaId(String media_id) {
		// TODO Auto-generated method stub
		return null;
	}
}
