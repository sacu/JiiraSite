package org.jiira.we;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author sacu
 *
 * @time 2018年1月30日 下午1:41:26
 *
 * @mail saculer@hotmail.com
 *
 *       instructions:
 */
public class SAURLConnection {
	private static final Logger logger = LoggerFactory.getLogger(SAURLConnection.class);
	private String cookie;
	private final int timeout = 30000;
	private final int HTML_SUCCESS = 200;

	protected SAURLConnection() {
		cookie = null;
		CookieManager cm = new CookieManager();
		cm.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);
		CookieHandler.setDefault(cm);
	}

	private static SAURLConnection instance;

	public static SAURLConnection getInstance() {
		if (null == instance) {
			instance = new SAURLConnection();
		}
		return instance;
	}

	protected HttpURLConnection getConnection(String url) {
		try {
			URL _url = new URL(url);
			return (HttpURLConnection) _url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("消息机器人连接异常");
		}
		return null;
	}

	// 处理http请求 requestUrl为请求地址 requestMethod请求方式，值为"GET"或"POST"
	public SAHTML GetRequest(SAHttpTable options) {
		StringBuffer buffer = null;
		SAHTML h = new SAHTML();
		h.setCode(0);
		HttpURLConnection gconn = null;
		try {
			int i, len;
			ArrayList<SAHttpKVO> kvos;
			SAHttpKVO kvo;
			kvos = options.getParams();
			len = kvos.size();
			String paramStr = options.getURL();
			if (len > 0) {
				paramStr += "?";
				for (i = 0; i < len; ++i) {
					kvo = kvos.get(i);
					if (i == len - 1) {
						paramStr += kvo.getKey() + "=" + URLEncoder.encode(kvo.getValue(), "utf-8");
					} else {
						paramStr += kvo.getKey() + "=" + URLEncoder.encode(kvo.getValue(), "utf-8") + "&";
					}
				}
			}
			gconn = getConnection(paramStr);
			gconn.setDoOutput(true);
			gconn.setDoInput(true);
			gconn.setConnectTimeout(timeout);
			gconn.setReadTimeout(timeout);
			if (len > 0) {// 消息头之类的
				for (i = 0; i < len; ++i) {
					kvo = kvos.get(i);
					gconn.addRequestProperty(kvo.getKey(), kvo.getValue());
				}
			}
			gconn.addRequestProperty("Cache-Control", "no-cache");
			gconn.setRequestMethod(options.getMethod());// get&post
			h.setMethod(options.getMethod());

			kvos = options.getPropertys();
			len = kvos.size();

			if (null != cookie) {
				gconn.setRequestProperty("Cookie", "Cookie: " + cookie);
			}
			gconn.connect();
			cookie = gconn.getHeaderField("Set-Cookie");// 取到所用的Cookie
			int statusCode = gconn.getResponseCode();
			h.setCode(statusCode);
			if (statusCode == HTML_SUCCESS) {
				// 读取服务器端返回的内容
				InputStream is = gconn.getInputStream();
				InputStreamReader isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				buffer = new StringBuffer();
				String line = null;
				while ((line = br.readLine()) != null) {
					buffer.append(line);
				}
				h.setBody(buffer.toString());
				return h;
			} else {
				buffer = null;
				h.setBody("服务器太差了！搞得我思维都短路了……");
				return h;
			}
		} catch (Exception e) {
			h.setBody("服务器太差了！该给老板申请找经费了……");
			return h;
			// e.printStackTrace();
		} finally {
			try {
				if (null != gconn) {
					gconn.disconnect();
					gconn = null;
				}
			} catch (NullPointerException e) {
				logger.error("消息机器人关闭异常");
			}
		}
	}

	public SAHTML PostRequest(SAHttpTable options) {
		int i, len;
		ArrayList<SAHttpKVO> kvos;
		SAHttpKVO kvo;
		SAHTML h = new SAHTML();
		// 尝试发送请求
		HttpURLConnection pconn = null;
		try {
			h.setCode(0);
			pconn = getConnection(options.getURL());
			h.setMethod(options.getMethod());
			//// POST 只能为大写，严格限制，post会不识别
			pconn.setRequestMethod(options.getMethod());
			pconn.setDoOutput(true);
			pconn.setDoInput(true);
			pconn.setUseCaches(true);

			pconn.setConnectTimeout(timeout);
			pconn.setReadTimeout(timeout);

			kvos = options.getPropertys();
			len = kvos.size();
			if (len > 0) {
				for (i = 0; i < len; ++i) {
					kvo = kvos.get(i);
					pconn.setRequestProperty(kvo.getKey(), kvo.getValue());
				}
			}
			if (null != cookie) {
				pconn.setRequestProperty("Cookie", "Cookie: " + cookie);
			}
			System.setProperty("https.protocols", "TLSv1");
			pconn.connect();
			if (options.isUseJson()) {
				OutputStreamWriter out = new OutputStreamWriter(pconn.getOutputStream(), "UTF-8"); // utf-8编码
				out.append(options.getJson());
				out.flush();
				out.close();
			} else {
				DataOutputStream out = new DataOutputStream(pconn.getOutputStream());
				kvos = options.getParams();
				len = kvos.size();
				// 构建请求参数
				StringBuffer sb = new StringBuffer();
				if (len > 0) {
					for (i = 0; i < len; ++i) {
						kvo = kvos.get(i);
						sb.append(kvo.getKey());
						sb.append("=");
						sb.append(URLEncoder.encode(kvo.getValue(), "utf-8"));
						if (i < len - 1) {
							sb.append("&");
						}
					}
				}
				out.writeBytes(sb.toString());
				out.flush();
				out.close();
			}
			int statusCode = pconn.getResponseCode();
			h.setCode(statusCode);
			if (statusCode != HTML_SUCCESS) {
				h.setBody("服务器太差了！搞得我思维都短路了……");
				return h;
			}
			cookie = pconn.getHeaderField("Set-Cookie");// 取到所用的Cookie

			StringBuffer buffer = new StringBuffer();
			// 一定要有返回值，否则无法把请求发送给server端。
			BufferedReader br = new BufferedReader(new InputStreamReader(pconn.getInputStream(), "UTF-8"));
			String temp;
			while ((temp = br.readLine()) != null) {
				buffer.append(temp);
				buffer.append("\n");
			}
			h.setBody(buffer.toString());
			return h;
		} catch (Exception e) {
			h.setBody("服务器太差了！该给老板申请找经费了……" + e.getMessage());
			return h;
			// e.printStackTrace();
		} finally {
			try {
				if (null != pconn) {
					pconn.disconnect();
					pconn = null;
				}
			} catch (NullPointerException e) {
				logger.error("消息机器人关闭异常");
			}
		}
	}

	/**
	 * 用来上传图片的
	 * 
	 * @param options
	 * @return
	 */
	public SAHTML PostRequest(SAHttpTable options, File file, boolean isVideo) {
		SAHTML h = new SAHTML();
		HttpURLConnection conn = getConnection(options.getURL());
		h.setMethod(options.getMethod());
		//// POST 只能为大写，严格限制，post会不识别
		BufferedReader reader = null;
		OutputStream out = null;
		try {
			conn.setRequestMethod(options.getMethod());
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setUseCaches(!isVideo);
			conn.setConnectTimeout(timeout);
			conn.setReadTimeout(timeout);
			// 设置请求头信息
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Charset", "UTF-8");
			// 设置边界
            String BOUNDARY;
            if(isVideo) {
                conn.setRequestProperty("Cache-Control", "no-cache");
            	BOUNDARY = "-----------------------------" + System.currentTimeMillis();
            } else {
            	BOUNDARY = "----------" + System.currentTimeMillis();
            }
			conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
			// 获得输出流
			out = new DataOutputStream(conn.getOutputStream());
			// 请求正文信息
			// 第一部分：
			out.write(("--" + BOUNDARY + "\r\n").getBytes("utf-8")); // 必须多两道线
			
			if(isVideo) {
				out.write(("Content-Disposition: form-data;name=\"media\";filename=\"" + file.getName() + "\"\r\n").getBytes("utf-8"));
				out.write("Content-Type:video/mp4\r\n\r\n".getBytes("utf-8"));//类型
			} else {
				out.write(("Content-Disposition: form-data;name=\"media\";filelength=\""+file.length()+"\";filename=\""+ file.getName() + "\"\r\n").getBytes("utf-8"));
				out.write(("Content-Type:application/octet-stream\r\n\r\n").getBytes("utf-8"));//类型
			}
			// 输出表头
			// 文件正文部分
			// 把文件已流文件的方式 推入到url中
			DataInputStream in = new DataInputStream(new FileInputStream(file));
			int bytes = 0;
			byte[] bufferOut = new byte[1024];
			while ((bytes = in.read(bufferOut)) != -1) {
				out.write(bufferOut, 0, bytes);
			}
			in.close();
			if (options.isUseJson()) {//在结尾前写入媒体数据
				out.write(("--" + BOUNDARY + "\r\n").getBytes("utf-8"));// 定义最后数据分隔线
				//第二部分 写入附带值部分
				out.write("Content-Disposition: form-data; name=\"description\";\r\n\r\n".getBytes("utf-8"));
				out.write(options.getJson().getBytes("utf-8"));
//				out.write(String.format("{\"title\":\"%s\", \"introduction\":\"%s\"}","你好", "我是谁").getBytes("utf-8"));
				out.write(("\r\n--" + BOUNDARY + "--\r\n\r\n").getBytes("utf-8"));
			} else {
				out.write(("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8"));// 定义最后数据分隔线
			}
			out.flush();
			StringBuffer buffer = new StringBuffer();
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			h.setBody(buffer.toString());
			return h;
		} catch (IOException e) {
			h.setBody("服务器太差了！该给老板申请找经费了……" + e.getMessage());
			return h;
			// TODO Auto-generated catch block
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (reader != null) {
					out.close();
				}
				if(null != conn) {
					conn.disconnect();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
