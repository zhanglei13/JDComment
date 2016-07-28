package com.jd.comment.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.jd.comment.util.Constants;

/**
 * 
 * Class: LoginFilter <br/>
 * Description: TODO <br/>
 * CreatedTimeime: 2015年3月31日 下午10:14:24 <br/>
 * @author zhanglei
 * @version V1.0
 */
public class LoginFilter implements Filter {
	private final static Logger logger = Logger.getLogger(LoginFilter.class);
	List listurl = new ArrayList();

	@Override
	public void destroy() {
		listurl.clear();
	}
	
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		String url1 = request.getServletPath()
				+ (request.getPathInfo() == null ? "" : request.getPathInfo());
		if (listurl.contains(url1) || url1.contains(Constants.STATIC_RESOURCE)) {
			arg2.doFilter(request, response);
		} else if (session.getAttribute(Constants.SESSION_USERINFO_KEY) == null
				|| "".equals(session
						.getAttribute(Constants.SESSION_USERINFO_KEY))) {
			String url = request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + request.getContextPath()
					+ Constants.REDIRECT_URL;
			// response.sendRedirect(url);
			response.setContentType("text/html;charset=UTF-8");
			java.io.PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<script>");
			out.println("alert('session不存在，请先登入！');window.open ('" + url
					+ "','_top')");
			out.println("</script>");
			out.println("</html>");
			logger.info("session:过期..........");
		} else {
			arg2.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		String urlStr = arg0.getInitParameter("listurl");
		if (urlStr != null) {
			StringTokenizer st = new StringTokenizer(urlStr, ";");
			listurl.clear();
			while (st.hasMoreTokens()) {
				listurl.add(st.nextToken());
			}
		}
	}

}
