package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdVoiceDao;
import org.jiira.pojo.ad.AdVoice;
import org.jiira.service.AdVoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdVoiceServiceImpl implements AdVoiceService {

	@Autowired
	private AdVoiceDao adVoiceDao = null;
	@Override
	public List<AdVoice> checkVoice(List<String> voices) {
		// TODO Auto-generated method stub
		return adVoiceDao.checkVoice(voices);
	}

	@Override
	public List<AdVoice> selectVoices() {
		// TODO Auto-generated method stub
		return adVoiceDao.selectVoices();
	}

	@Override
	public AdVoice selectVoice(String voice) {
		// TODO Auto-generated method stub
		return adVoiceDao.selectVoice(voice);
	}

	@Override
	public int ignoreVoice(List<String> voices) {
		// TODO Auto-generated method stub
		return adVoiceDao.ignoreVoice(voices);
	}

	@Override
	public int updateVoice(String voice, String media_id) {
		// TODO Auto-generated method stub
		return adVoiceDao.updateVoice(voice, media_id);
	}

	@Override
	public int deleteVoice(String voice) {
		// TODO Auto-generated method stub
		return adVoiceDao.deleteVoice(voice);
	}
	
}
