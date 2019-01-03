package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdVideoDao;
import org.jiira.pojo.ad.AdVideo;
import org.jiira.service.AdVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdVideoServiceImpl implements AdVideoService {

	@Autowired
	private AdVideoDao adVideoDao = null;

	@Override
	public List<AdVideo> checkVideo(String videos) {
		// TODO Auto-generated method stub
		return adVideoDao.checkVideo(videos);
	}

	@Override
	public List<AdVideo> selectVideos() {
		// TODO Auto-generated method stub
		return adVideoDao.selectVideos();
	}

	@Override
	public AdVideo selectVideo(String video) {
		// TODO Auto-generated method stub
		return adVideoDao.selectVideo(video);
	}

	@Override
	public int ignoreVideo(String video, String title, String introduction) {
		// TODO Auto-generated method stub
		return adVideoDao.ignoreVideo(video, title, introduction);
	}

	@Override
	public int updateVideo(String video, String media_id) {
		// TODO Auto-generated method stub
		return adVideoDao.updateVideo(video, media_id);
	}

	@Override
	public int deleteVideo(String video) {
		// TODO Auto-generated method stub
		return adVideoDao.deleteVideo(video);
	}

}
