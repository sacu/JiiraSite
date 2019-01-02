package org.jiira.service.impl;

import java.util.List;

import org.jiira.dao.AdNewsImageDao;
import org.jiira.pojo.ad.AdNewsImage;
import org.jiira.service.AdNewsImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdNewsImageServiceImpl implements AdNewsImageService {

	@Autowired
	private AdNewsImageDao adNewsImageDao = null;
	
	@Override
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
	public List<AdNewsImage> checkNewsImage(List<String> newsImages) {
		// TODO Auto-generated method stub
		return adNewsImageDao.checkNewsImage(newsImages);
	}

}
