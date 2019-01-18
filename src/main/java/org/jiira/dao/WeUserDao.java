package org.jiira.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.WeUser;
import org.springframework.stereotype.Repository;
@Repository
public interface WeUserDao {
	public WeUser selectWeUser(String openid);
	public List<String> selectAll();
	public int insertWeUser(WeUser weUser);
	public int deleteWeUser(String openid);
	public int updateWeUserBirthday(@Param("openid")String openid, @Param("birthday")String birthday);
	public int updateWeUserVouchers(@Param("openid")String openid, @Param("vouchers")int vouchers);
	public int updateWeUserAutoPay(String openid, int autopay);
}