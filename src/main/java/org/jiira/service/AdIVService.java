package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.AdIV;

public interface AdIVService {
	public List<AdIV> checkIV(List<String> ivs);
	public List<AdIV> selectIVs(String type);
	public AdIV selectIV(String iv);
	public int ignoreIV(List<String> ivs, String type);
	public int updateIV(String iv, String media_id, String url);
	public int deleteIV(String iv);
}
