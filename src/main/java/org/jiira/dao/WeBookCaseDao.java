package org.jiira.dao;

import org.apache.ibatis.annotations.Param;
import org.jiira.pojo.ad.WeBookCase;
import org.springframework.stereotype.Repository;
@Repository
public interface WeBookCaseDao {
	//判断是否购买
	public WeBookCase selectWeBookCase(@Param("openid")String openid, @Param("news_id")int news_id);
	//获取阅读页
	public WeBookCase selectBookCaseForRead(@Param("openid")String openid, @Param("name_id")int name_id);
	//普通插入购买
	public int insertWeBookCase(WeBookCase weBookCase);
	//判断后插入购买
	public int ignoreWeBookCase(WeBookCase weBookCase);
	//更新阅读页
	public int updateBookCaseForRead(@Param("openid")String openid, @Param("name_id")int name_id, @Param("news_id")int news_id);
}