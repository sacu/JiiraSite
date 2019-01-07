package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdNewsTypeDao;
import org.jiira.pojo.ad.AdNewsType;
import org.jiira.service.AdNewsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdNewsTypeServiceImpl implements AdNewsTypeService {

	@Autowired
	private AdNewsTypeDao adNewsTypeDao;
	
	@Override
	public List<AdNewsType> selectNewsType() {
		// TODO Auto-generated method stub
		return adNewsTypeDao.selectNewsType();
	}

	@Override
	public int insertNewsType(String type) {
		// TODO Auto-generated method stub
		return adNewsTypeDao.insertNewsType(type);
	}

	@Override
	public int updateNewsType(int id, String name) {
		// TODO Auto-generated method stub
		return adNewsTypeDao.updateNewsType(id, name);
	}

	@Override
	public int deleteNewsType(int id) {
		// TODO Auto-generated method stub
		return adNewsTypeDao.deleteNewsType(id);
	}

}
