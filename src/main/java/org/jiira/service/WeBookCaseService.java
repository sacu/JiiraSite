package org.jiira.service;

import org.jiira.pojo.ad.WeBookCase;

public interface WeBookCaseService {
	WeBookCase selectWeBookCase(String openid, int newsid);
	WeBookCase selectBookCaseForRead(String openid, int nameid);
	int insertWeBookCase(WeBookCase weBookCase);
	int ignoreWeBookCase(WeBookCase weBookCase);
	int updateBookCaseForRead(String openid, int name_id, int news_id);
}
