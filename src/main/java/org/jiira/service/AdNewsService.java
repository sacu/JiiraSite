package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.AdNews;

public interface AdNewsService {
	List<AdNews> selectNew();
	AdNews selectNews(int id);
	int insertNews(AdNews adNews);
	int updateNews(int id, String media_id);
	int deleteNews(int id);
}
