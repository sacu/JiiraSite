package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdNews;
import org.springframework.stereotype.Repository;

@Repository
public interface AdNewsDao {
	public List<AdNews> selectNewsAll();//弃用
	public List<AdNews> selectNewsByNameID(@Param("name_id")int name_id);//获取目录
	
	public List<AdNews> selectNewsByLevel(int level);//级别限制拉取模式
	public List<AdNews> selectNewsByType(int type);//类型限制拉取模式
	public List<AdNews> selectNewsByTypeAndLike(@Param("type")int type, @Param("search_text")String search_text);//类型限制+模糊查询
	public List<AdNews> selectOderByLevelAndDesc(@Param("level")int level, @Param("rows")int rows);//级别+rows条数排序
	
	public AdNews selectNewsById(int id);
	public int insertNews(AdNews adNews);
	public int updateNews(@Param("id")int id, @Param("media_id")String media_id);
	public int deleteNews(int id);
}
