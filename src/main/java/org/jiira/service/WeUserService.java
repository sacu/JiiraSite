package org.jiira.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.WeUser;

public interface WeUserService {
	WeUser selectWeUser(String openid);
	List<String> selectAll();
	int insertWeUser(WeUser weUser);
	int deleteWeUser(String openid);
	int updateWeUserBirthday(@Param("openid")String openid, @Param("birthday")String birthday);
	int updateWeUserVouchers(String openid, int vouchers);
	int updateWeUserAutoPay(String openid, int autopay);
}
