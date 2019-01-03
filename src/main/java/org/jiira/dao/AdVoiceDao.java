package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdVoice;
import org.springframework.stereotype.Repository;

@Repository
public interface AdVoiceDao {
	/**
	 * 获取用户
	 */
	public List<AdVoice> checkVoice(List<String> voices);
	public List<AdVoice> selectVoices();
	public AdVoice selectVoice(@Param("voice")String voice);
	public int ignoreVoice(@Param("list")List<String> voices);
	public int updateVoice(@Param("voice")String voice, @Param("media_id")String media_id);
	public int deleteVoice(@Param("voice")String voice);
}
