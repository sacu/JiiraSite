package org.jiira.dao;

import org.jiira.pojo.ad.WeBookCase;
import org.springframework.stereotype.Repository;
@Repository
public interface WeBookCaseDao {
	public WeBookCase selectWeBookCase(String openid);
	public int insertWeBookCase(WeBookCase weBookCase);
}