package org.jiira.service;

import org.jiira.pojo.Login;
import org.jiira.pojo.ad.AdUser;

public interface AdUserService {
	/**
	 * 获取管理员信息
	 * @param redPacketId
	 * @param unitAmount
	 */
	public AdUser getAdUser(Login login);
}
