package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.AdVoice;

public interface AdVoiceService {
	public List<AdVoice> checkVoice(List<String> voices);
	public List<AdVoice> selectVoices();
	public AdVoice selectVoice(String voice);
	public int ignoreVoice(List<String> voices);
	public int updateVoice(String voice, String media_id);
	public int deleteVoice(String voice);
}
