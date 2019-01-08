package org.jiira.service;

import org.jiira.pojo.ad.AdIV;

public interface AdIVService extends AdMateService<AdIV> {
	public AdIV selectIVByMediaId(String media_id);
}
