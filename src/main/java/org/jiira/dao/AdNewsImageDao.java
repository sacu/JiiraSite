package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdNewsImage;
import org.springframework.stereotype.Repository;

@Repository
public interface AdNewsImageDao {
	/**
	 * 获取用户
	 */
	public List<AdNewsImage> checkNewsImage(@Param("newsImages") List<String> newsImages);
}
