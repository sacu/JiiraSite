package org.jiira.dao;

import java.util.List;

import org.jiira.pojo.ad.AdNewsName;
import org.springframework.stereotype.Repository;

@Repository
public interface AdNewsNameDao {
	public List<AdNewsName> selectNewsName();
	public AdNewsName selectNewsNameById(int id);
	public List<AdNewsName> selectNewsNameByLike(String search_text);
	
	public int insertNewsName(AdNewsName adNewsName);
	public int updateNewsName(AdNewsName adNewsName);
	public int deleteNewsName(int id);
}
