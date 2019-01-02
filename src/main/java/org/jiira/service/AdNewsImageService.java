package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.AdNewsImage;

public interface AdNewsImageService {
	public List<AdNewsImage> checkNewsImage(List<String> newsImages);
	public List<AdNewsImage> selectNewsImages();
	public AdNewsImage selectNewsImage(String newsImage);
	public int ignoreNewsImage(List<String> newsImages);
	public int updateNewsImage(String newsImage, String url);
	public int deleteNewsImage(String newsImage);
}
