package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdNewsType;
import org.springframework.stereotype.Repository;

@Repository
public interface AdNewsTypeDao {
	public List<AdNewsType> selectNewsType();
	public int insertNewsType(String type);
	public int updateNewsType(@Param("id")int id, @Param("name")String name);
	public int deleteNewsType(int id);
}
