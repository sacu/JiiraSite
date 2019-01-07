package org.jiira.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdNewsType;

public interface AdNewsTypeService {
	public List<AdNewsType> selectNewsType();
	public int insertNewsType(String type);
	public int updateNewsType(@Param("id")int id, @Param("name")String name);
	public int deleteNewsType(int id);
}
