package org.jiira.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdNewsImage;

public interface AdNewsImageService {
	public List<AdNewsImage> checkNewsImage(@Param("files") List<String> newsImages);
}
