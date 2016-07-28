package com.jd.comment.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * 
 * Class: InjectFilter <br/>
 * Description: TODO <br/>
 * CreatedTimeime: 2015年3月31日 下午10:14:40 <br/>
 * @author zhanglei
 * @version V1.0
 */
public class InjectFilter implements Filter {
	private static final Logger logger = Logger.getLogger(InjectFilter.class);

	public InjectFilter() {
	}

	public void init(FilterConfig filterconfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		Enumeration params = req.getParameterNames();
		String sql = "";
		while (params.hasMoreElements()) {
			String name = params.nextElement().toString();
			String value[] = req.getParameterValues(name);
			for (int i = 0; i < value.length; i++)
				sql = (new StringBuilder(String.valueOf(sql))).append(value[i])
						.toString();

		}
		Map map = req.getParameterMap();
		Map tmp = new HashMap();
		Iterator itr = map.keySet().iterator();
//		logger.info("\u53C2\u6570\u5FAA\u73AF\u5F00\u59CB");
		String key;
		String values[];
		for (; itr.hasNext(); tmp.put(key, values)) {
			key = itr.next().toString();
			values = req.getParameterValues(key);
			if (!key.equals("FILEDATA") && values != null) {
				for (int i = 0; i < values.length; i++) {
//					logger.debug((new StringBuilder("参数:")).append(key)
//							.append("=====>").append(values[i]).toString());
					// values[i] = CleanXSS.cleanXSS(values[i]);
					// logger.info((new
					// StringBuilder("*********")).append(key).append("=====").append(values[i]).toString());
				}

			}
		}
//		logger.info("\u53C2\u6570\u5FAA\u73AF\u7ED3\u675F");
		req = new ParameterRequestWrapper(req, tmp);
		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {
	}

}
