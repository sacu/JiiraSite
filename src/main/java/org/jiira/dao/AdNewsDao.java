package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdNews;
import org.springframework.stereotype.Repository;

@Repository
public interface AdNewsDao {
	public List<AdNews> selectNews();
	public AdNews selectNewsById(int id);
	public int insertNews(AdNews adNews);
	public int updateNews(@Param("id")int id, @Param("media_id")String media_id);
	public int deleteNews(int id);
}
