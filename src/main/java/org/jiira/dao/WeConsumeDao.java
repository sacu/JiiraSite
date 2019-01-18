package org.jiira.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface WeConsumeDao {
	public int ignoreWeConsume(@Param("openid")String openid, @Param("vouchers")int vouchers, @Param("transaction_id")String transaction_id);
}