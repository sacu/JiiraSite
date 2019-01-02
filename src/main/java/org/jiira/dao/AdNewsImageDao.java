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
	public List<AdNewsImage> checkNewsImage(List<String> newsImages);
	public List<AdNewsImage> selectNewsImages();
	public AdNewsImage selectNewsImage(@Param("newsImage")String newsImage);
	public int ignoreNewsImage(List<String> newsImages);
	public int updateNewsImage(@Param("newsImage")String newsImage, @Param("url")String url);
	public int deleteNewsImage(@Param("newsImage")String newsImage);
}
