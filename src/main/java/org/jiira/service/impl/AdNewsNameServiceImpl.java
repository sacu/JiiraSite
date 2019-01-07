package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdNewsNameDao;
import org.jiira.pojo.ad.AdNewsName;
import org.jiira.service.AdNewsNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdNewsNameServiceImpl implements AdNewsNameService {

	@Autowired
	private AdNewsNameDao adNewsNameDao = null;
	
	@Override
	public List<AdNewsName> selectNewsName() {
		// TODO Auto-generated method stub
		return adNewsNameDao.selectNewsName();
	}

	@Override
	public AdNewsName selectNewsNameById(int id) {
		// TODO Auto-generated method stub
		return adNewsNameDao.selectNewsNameById(id);
	}

	@Override
	public int insertNewsName(AdNewsName adNewsName) {
		// TODO Auto-generated method stub
		return adNewsNameDao.insertNewsName(adNewsName);
	}

	@Override
	public int updateNewsName(AdNewsName adNewsName) {
		// TODO Auto-generated method stub
		return adNewsNameDao.updateNewsName(adNewsName);
	}

	@Override
	public int deleteNewsName(int id) {
		// TODO Auto-generated method stub
		return adNewsNameDao.deleteNewsName(id);
	}

}
