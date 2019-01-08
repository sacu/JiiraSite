package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.AdNews;

public interface AdNewsService extends AdMateService<AdNews> {
	public List<AdNews> selectNewsByLevel(int level);//级别限制拉取模式
	public List<AdNews> selectNewsByType(int type);//类型限制拉取模式
	public List<AdNews> selectOderByLevelAndDesc(int level, int limit);//级别+limit条数排序
	public List<AdNews> selectNewsByTypeAndLike(int type, String search_text);//类型限制+模糊查询
}
