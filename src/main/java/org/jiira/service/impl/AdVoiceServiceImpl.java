package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdVoiceDao;
import org.jiira.pojo.ad.AdVoice;
import org.jiira.service.AdMateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdVoiceServiceImpl implements AdMateService<AdVoice> {

	@Autowired
	private AdVoiceDao adVoiceDao = null;
	@Override
	public List<AdVoice> check(List<String> voices) {
		// TODO Auto-generated method stub
		return adVoiceDao.checkVoice(voices);
	}

	@Override
	public List<AdVoice> select() {
		// TODO Auto-generated method stub
		return adVoiceDao.selectVoices();
	}

	@Override
	public AdVoice selectById(String voice) {
		// TODO Auto-generated method stub
		return adVoiceDao.selectVoice(voice);
	}

	@Override
	public int ignore(List<String> voices) {
		// TODO Auto-generated method stub
		return adVoiceDao.ignoreVoice(voices);
	}

	@Override
	public int update(String voice, String media_id) {
		// TODO Auto-generated method stub
		return adVoiceDao.updateVoice(voice, media_id);
	}

	@Override
	public int delete(String voice) {
		// TODO Auto-generated method stub
		return adVoiceDao.deleteVoice(voice);
	}

	@Override
	public List<AdVoice> check(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(int id, String media_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(String id, String media_id, String url) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AdVoice selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AdVoice> selectByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(AdVoice ad) {
		// TODO Auto-generated method stub
		return 0;
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
}
