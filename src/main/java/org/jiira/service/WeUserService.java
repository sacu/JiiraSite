package org.jiira.service;

import java.util.List;

import org.jiira.pojo.ad.WeUser;

public interface WeUserService {
	WeUser selectWeUser(String openid);
	List<String> selectAll();
	int insertWeUser(WeUser weUser);
	int deleteWeUser(String openid);
	int updateWeUserVouchers(String openid, int vouchers);
}
