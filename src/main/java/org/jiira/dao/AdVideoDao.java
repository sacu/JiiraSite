package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdVideo;
import org.springframework.stereotype.Repository;

@Repository
public interface AdVideoDao {
	/**
	 * 获取用户
	 */
	public List<AdVideo> checkVideo(@Param("video") String video);

	public List<AdVideo> selectVideos();

	public AdVideo selectVideo(@Param("video") String video);

	public int ignoreVideo(@Param("video") String video, @Param("title") String title,
			@Param("introduction") String introduction);

	public int updateVideo(@Param("video") String video, @Param("media_id") String media_id);

	public int deleteVideo(@Param("video") String video);
}
