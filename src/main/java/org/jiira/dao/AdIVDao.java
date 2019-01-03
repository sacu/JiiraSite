package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdIV;
import org.springframework.stereotype.Repository;

@Repository
public interface AdIVDao {
	/**
	 * 获取用户
	 */
	public List<AdIV> checkIV(List<String> ivs);
	public List<AdIV> selectIVs(@Param("type")String type);
	public AdIV selectIV(@Param("iv")String iv);
	public int ignoreIV(@Param("list")List<String> ivs, @Param("type")String type);
	public int updateIV(@Param("iv")String iv, @Param("media_id")String media_id, @Param("url")String url);
	public int deleteIV(@Param("iv")String iv);
}
