package org.jiira.dao;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.AdUser;
import org.springframework.stereotype.Repository;
@Repository
public interface AdUserDao {
	
	/**
	 * 获取用户
	 */
	public AdUser getAdUser(@Param("userName")String userName, @Param("passWord")String passWord);
}