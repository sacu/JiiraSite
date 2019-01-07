package org.jiira.dao;

import java.util.List;

import org.jiira.pojo.ad.WeUser;
import org.springframework.stereotype.Repository;
@Repository
public interface WeUserDao {
	public WeUser selectWeUser(String openid);
	public List<String> selectAll();
	public int insertWeUser(WeUser weUser);
	public int deleteWeUser(String openid);
	public int updateWeUserVouchers(String openid, int vouchers);
}