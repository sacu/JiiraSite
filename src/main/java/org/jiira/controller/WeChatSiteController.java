package org.jiira.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jiira.pojo.ad.AdIV;
import org.jiira.pojo.ad.AdNews;
import org.jiira.pojo.ad.AdNewsName;
import org.jiira.pojo.ad.WeBookCase;
import org.jiira.pojo.ad.WeUser;
import org.jiira.pojo.we.WeToken;
import org.jiira.service.AdIVService;
import org.jiira.service.AdMateService;
import org.jiira.service.AdNewsNameService;
import org.jiira.service.AdNewsService;
import org.jiira.service.WeBookCaseService;
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
	@Autowired
	private WeBookCaseService weBookCaseService = null;
	@Autowired
	private AdNewsNameService adNewsNameService = null;
	
	
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
	 * autopay 是页面判断是否是支付操作的
	 */
	@RequestMapping(value="/getNews")
	public ModelAndView getNews(HttpServletRequest request, @RequestParam(name="news_id") int news_id, 
			@RequestParam(name="openid") String openid, @RequestParam(name="autopay")boolean autopay,
			@RequestParam(name="autopay")boolean sautopay, @RequestParam(name="isdir")boolean isdir) {
		ModelAndView mv = new ModelAndView();
		WeUser weUser = (WeUser) request.getSession().getAttribute("weUser");
		boolean consume = true;
		if(null == weUser) {
			weUser = weUserService.selectWeUser(openid);
		}
		AdNews adNews = adNewsService.selectById(news_id);
		if(adNews.getConsume() > 0) {//是否需要花钱
			if(weBookCaseService.selectWeBookCase(openid, news_id) == null) {//是否未支付
				//如果autopay==true 表示该条是支付操作
				if(autopay || weUser.getAutopay() == CommandCollection.AUTO_PAY) {//是否开通自动支付
					if(weUser.getVouchers() >= adNews.getConsume()) {//是否足够支付
						consume = weUserService.updateWeUserVouchers(openid, weUser.getVouchers() - adNews.getConsume()) > 0;//支付
					} else {//显示充值页面
						mv.addObject("check", 1);
						consume = false;
					}
				} else {//显示支付页面
					mv.addObject("check", 2);
					consume = false;
				}
			}
		}
		mv.setView(new MappingJackson2JsonView());
		if(consume) {
			//插入已购买
			WeBookCase weBookCase = new WeBookCase();
			weBookCase.setOpenid(openid);
			weBookCase.setNewsid(news_id);
			weBookCase.setNameid(adNews.getName_id());
			weBookCaseService.ignoreWeBookCase(weBookCase);
			//这里要改
			weBookCaseService.updateBookCaseForRead(openid, adNews.getName_id(), adNews.getId());//设置阅读页
			mv.addObject("check", 0);
			//获取图文封面
//			AdIV adIV = ((AdIVService) adIVService).selectIVByMediaId(adNews.getThumb_media_id());
//			if(null != adIV) {
//				mv.addObject("thumb", adIV.getIV());
//			}
		} else {//如果各种原因不能查看，则移除内容
			adNews.setContent("");
		}
		//获取图书列表
		if(isdir && adNews.getType() == CommandCollection.BOOK_TYPE) {//如果是图书，则获取列表
			List<AdNews> dir = adNewsService.selectNewsByNameID(adNews.getName_id());//获取目录
			mv.addObject("dir", dir);
		} else {
			mv.addObject("dir", null);
		}
		mv.addObject("adNews", adNews);
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
		if(type == CommandCollection.BOOK_TYPE || type == CommandCollection.ALL_TYPE) {
			List<AdNewsName> adNewsName = adNewsNameService.selectNewsNameByLike(search);
			mv.addObject("adNewsName", adNewsName);
		}
		if(type == CommandCollection.ALL_TYPE || type != CommandCollection.BOOK_TYPE) {
			List<AdNews> adNews = adNewsService.selectNewsByTypeAndLike(type, search);//类型搜索
			mv.addObject("adNews", adNews);
		}
		mv.setView(new MappingJackson2JsonView());
	    return mv;
	}
	/**
	 * 获取新闻
	 * @param response
	 * autopay 是页面判断是否是支付操作的
	 */
	@RequestMapping(value="/getNewsName")
	public ModelAndView getNewsName(HttpServletRequest request,
			@RequestParam(name="name_id") int name_id, @RequestParam(name="openid") String openid) {
		AdNewsName adNewsName = adNewsNameService.selectNewsNameById(name_id);
		ModelAndView mv = new ModelAndView();
		mv.addObject("adNewsName", adNewsName);
		WeBookCase weBookCase = weBookCaseService.selectBookCaseForRead(openid, name_id);//获取阅读的页数
		int news_id;
		if(null == weBookCase) {
			List<AdNews> dir = adNewsService.selectNewsByNameID(name_id);//获取阅读的页数
			news_id = dir.get(0).getId();
		} else {
			news_id = weBookCase.getNewsid();
		}
		mv.addObject("news_id", news_id);
		mv.setView(new MappingJackson2JsonView());
	    return mv;
	}
}
