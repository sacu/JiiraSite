package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.AdNewsName;

public interface AdNewsNameService {
	public List<AdNewsName> selectNewsName();
	public AdNewsName selectNewsNameById(int id);
	public int insertNewsName(AdNewsName adNewsName);
	public int updateNewsName(AdNewsName adNewsName);
	public int deleteNewsName(int id);
}
