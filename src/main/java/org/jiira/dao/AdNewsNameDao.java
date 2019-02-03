package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdNewsName;
import org.springframework.stereotype.Repository;

@Repository
public interface AdNewsNameDao {
	public List<AdNewsName> selectNewsName();
	public AdNewsName selectNewsNameById(int id);
	public List<AdNewsName> selectNewsNameByLike(@Param("search_text")String search_text, @Param("type_id")int type_id);
	
	public int insertNewsName(AdNewsName adNewsName);
	public int updateNewsName(AdNewsName adNewsName);
	public int deleteNewsName(int id);
}
