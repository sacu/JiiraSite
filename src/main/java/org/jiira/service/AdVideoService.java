package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.AdVideo;

public interface AdVideoService {
	public List<AdVideo> checkVideo(String video);
	public List<AdVideo> selectVideos();
	public AdVideo selectVideo(String video);
	public int ignoreVideo(String video, String title, String introduction);
	public int updateVideo(String video, String media_id);
	public int deleteVideo(String video);
}
