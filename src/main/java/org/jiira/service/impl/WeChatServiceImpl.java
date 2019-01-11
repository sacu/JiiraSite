package org.jiira.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jiira.pojo.we.ai.text.WeTextSay;
import org.jiira.service.WeChatService;
import org.jiira.utils.CommandCollection;
import org.jiira.we.WeGlobal;
import org.jiira.we.message.WeChatMessage;
import org.jiira.we.process.HandleEvent;
import org.jiira.we.process.HandleImage;
import org.jiira.we.process.HandleText;
import org.jiira.we.process.HandleVoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

@Service
public class WeChatServiceImpl implements WeChatService {
	private static final Logger logger = LoggerFactory.getLogger(WeChatServiceImpl.class);
	
	public WeChatMessage getMessage(HttpServletRequest request) {
		SAXReader reader = new SAXReader();
		InputStream ins = null;
		try {
			ins = request.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = reader.read(ins);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		logger.error("###################解析前 : " + doc.asXML());
		
		Element root = doc.getRootElement();
		WeChatMessage msg = new WeChatMessage();
		msg.setUseTextSay(false);
		Method echoMethod;
		@SuppressWarnings("unchecked")
		List<Element> list = root.elements();
		for (Element ele : list) {
			try {
				echoMethod = msg.getClass().getMethod("set" + ele.getName(), new Class[] { String.class });
				if(null != echoMethod) {
					echoMethod.invoke(msg, new String[] { ele.getText() });
				}
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}
		try {
			ins.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return msg;
	}

	public String formatMessage(WeChatMessage msg) {
		XStream xstream = new XStream();
		xstream.alias("xml", msg.getClass());
		return xstream.toXML(msg);
	}

	public String handler(WeChatMessage msg) {
		switch (msg.getMsgType()) {
			case CommandCollection.MESSAGE_TEXT: {//文本消息
				HandleText.getInstance().process(msg);
				if(msg.getUseTextSay()) {
					WeTextSay r = WeGlobal.getInstance().getTextSay(msg.getContent());
					if(null == r) {
						msg.setContent("出错了……");
					} else if(r.getRet() == 0) {//成功
						msg.setContent(r.getAnswer());
					} else if(r.getRet() == 16394){
						msg.setContent("[" + msg.getContent() + "]是什么？额……我不知道诶~");
					} else {
						msg.setContent("没人点赞……今晚没有鸡腿儿吃了！" + r.getRet());
					}
				}
				break;
			}
			/**
			 * 图片"image";
			 */
			case CommandCollection.MESSAGE_IMAGE:{
				HandleImage.getInstance().process(msg);
				break;
			}
			 /**
			  * 图文消息
			  */
			case CommandCollection.MESSAGE_NEWS: {
				break;
			}
			/**
			 * 语音消息
			 */
			case CommandCollection.MESSAGE_VOICE: {
				HandleVoice.getInstance().process(msg);
				break;
			}
			/**
			 * 视频消息
			 */
			case CommandCollection.MESSAGE_VIDEO: {
			}
			/**
			 * 小视频消息
			 */
			case CommandCollection.MESSAGE_SHORTVIDEO: {
			}
			/**
			 * 地理位置消息
			 */
			case CommandCollection.MESSAGE_LOCATION: {
			}
			/**
			 * 链接消息
			 */
			case CommandCollection.MESSAGE_LINK: {

				break;
			}
			/**
			 * 事件推送消息
			 */
			case CommandCollection.MESSAGE_EVENT: {//如果有个别需求 就个别处理
				HandleEvent.getInstance().process(msg);
				String xml = formatMessage(msg);// 转成发送string
				return HandleEvent.getInstance().format(msg, xml);
			}
		}
		return formatMessage(msg);
	}
}
