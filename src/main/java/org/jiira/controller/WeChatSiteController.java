package org.jiira.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNews;
import org.jiira.pojo.ad.WeUser;
import org.jiira.pojo.we.WeToken;
import org.jiira.service.AdIVService;
import org.jiira.service.AdMateService;
import org.jiira.service.AdNewsService;
import org.jiira.service.WeChatService;
import org.jiira.service.WeUserService;
import org.jiira.utils.CommandCollection;
import org.jiira.we.DecriptUtil;
import org.jiira.we.collection.StrComparator;
import org.jiira.we.message.WeChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * 微信的
 * @author time
 *
 */
@Controller
@RequestMapping("/we")
public class WeChatSiteController {
	// 日志记录
	private static final Logger logger = LoggerFactory.getLogger(WeChatSiteController.class);

	@Autowired
	private WeChatService weChatService = null;
	@Autowired
	private AdNewsService adNewsService = null;
	@Autowired
	private AdMateService<AdIV> adIVService = null;
	@Autowired
	private WeUserService weUserService = null;
	
	/**
	 * 微信token验证
	 * 
	 * @param roleName
	 * @param note
	 * @return
	 */
	@RequestMapping(value = "as", method = RequestMethod.GET)
	public void weChat(PrintWriter out, WeToken weToken) {
		logger.error(weToken.getSignature() + "|" + weToken.getTimestamp() + "|" + weToken.getNonce() + "|"
				+ weToken.getEchostr());
		/**
		 * 组装 token、timestamp、nonce，进行字典序排序，中后两个值是微信给的 然后使用排序后的值进行sha1加密
		 * 使用sha1的加密值和signature最比较，如果比较成功，则证明是微信的请求，并且表示“接入生效”
		 */
		ArrayList<String> sort = new ArrayList<>(3);
		sort.add(CommandCollection.Token);
		sort.add(weToken.getTimestamp());
		sort.add(weToken.getNonce());
		Collections.sort(sort, new StrComparator());
		String _token = "";
		for (int i = 0; i < 3; ++i) {
			_token += sort.get(i);
		}
		logger.warn("##############开始微信接入##############");
		logger.warn("token:" + CommandCollection.Token);
		logger.warn("appID:" + CommandCollection.AppID);
		logger.warn("appsecret:" + CommandCollection.Appsecret);
		logger.warn("signature:" + weToken.getSignature());
		logger.warn("timestamp:" + weToken.getTimestamp());
		logger.warn("nonce:" + weToken.getNonce());
		logger.warn("echostr:" + weToken.getEchostr());
		logger.warn("字典序:" + _token);
		// 组装
		String digest = DecriptUtil.SHA1(_token);
		logger.warn("sha1加密:" + digest);
		if (digest.equals(weToken.getSignature())) {
			logger.warn("接入成功，返回echostr");
			out.print(weToken.getEchostr());
		} else {
			logger.warn("接入失败Error");
			out.print("Error");
		}
		out.flush();
		out.close();
	}
/**
 * 微信的消息回复
 * @param response
 * @param request
 */
	@RequestMapping(value = "as", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public void weChat(HttpServletResponse response, HttpServletRequest request) {
		// 解析用户发来的数据
		logger.warn("收到消息#################");
		WeChatMessage msg = weChatService.getMessage(request);
		msg.print(logger);
		response.setCharacterEncoding("utf-8");
		PrintWriter out = null;
		try {
			msg.identityConvert();// 转换发送者和接收者身份
			String strMsg = weChatService.handler(msg);// 执行修改msg内容
			out = response.getWriter();
			out.print(strMsg);
			logger.warn("回复消息 : " + strMsg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (null != out) {
				out.flush();
				out.close();
			}
		}
	}
	

	/**
	 * 获取新闻
	 * @param response
	 */
	@RequestMapping(value="/getNews")
	public ModelAndView getNews(@RequestParam(name="news_id") int news_id, 
			@RequestParam(name="openid") String openid) {
		ModelAndView mv = new ModelAndView();
		AdNews adNews = adNewsService.selectById(news_id);
		boolean consume = false;
		if(adNews.getConsume() > 0) {
			WeUser weUser = weUserService.selectWeUser(openid);
			if(weUser.getVouchers() >= adNews.getConsume()) {
				consume = weUserService.updateWeUserVouchers(openid, weUser.getVouchers() - adNews.getConsume()) > 0;
			}
		} else {
			consume = true;
		}
		mv.setView(new MappingJackson2JsonView());
		if(consume) {
			AdIV adIV = ((AdIVService) adIVService).selectIVByMediaId(adNews.getThumb_media_id());
			mv.addObject("check", 1);
			mv.addObject("adNews", adNews);
			mv.addObject("thumb", adIV.getIV());
		} else {
			mv.addObject("check", 0);
			mv.addObject("info", "需要付费:" + adNews.getConsume() + ",才能观看");
		}
	    return mv;
	}
	@RequestMapping(value="/getNewsList")
	public ModelAndView getNewsList() {
		ModelAndView mv = new ModelAndView();
		List<AdNews> adNews = adNewsService.selectNewsByLevel(CommandCollection.LEVEL_LOWER);//暂时排除了文章类
		mv.addObject("adNews", adNews);
		mv.setView(new MappingJackson2JsonView());
	    return mv;
	}
	@RequestMapping(value="/getNewsSearch")
	public ModelAndView getNewsSearch(int type, String search) {
		ModelAndView mv = new ModelAndView();
		List<AdNews> adNews = adNewsService.selectNewsByTypeAndLike(type, search);//类型搜索
		mv.addObject("adNews", adNews);
		mv.setView(new MappingJackson2JsonView());
	    return mv;
	}
	
}
