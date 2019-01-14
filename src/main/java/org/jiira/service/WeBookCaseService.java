package org.jiira.service;

import org.jiira.pojo.ad.WeBookCase;

public interface WeBookCaseService {
	WeBookCase selectWeBookCase(String openid);
	int insertWeBookCase(WeBookCase weBookCase);
}
