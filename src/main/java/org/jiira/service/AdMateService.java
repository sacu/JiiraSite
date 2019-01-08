package org.jiira.service;

import java.util.List;

public interface AdMateService <T> {
	List<T> check(String id);
	List<T> check(List<String> ids);
	
	List<T> select();
	List<T> selectByType(String type);
	T selectById(String id);
	T selectById(int id);
	
	int insert(T ad);
	int ignore(String id, String title, String introduction);
	int ignore(List<String> ids, String type);
	int ignore(List<String> voices);

	int update(int id, String media_id);
	int update(String id, String media_id);
	int update(String id, String media_id, String url);
	
	int delete(String id);
	int delete(int id);
}
