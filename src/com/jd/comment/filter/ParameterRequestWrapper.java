package com.jd.comment.filter;

import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 
 * Class: ParameterRequestWrapper <br/>
 * Description: TODO <br/>
 * CreatedTimeime: 2015年3月31日 下午10:14:47 <br/>
 * @author zhanglei
 * @version V1.0
 */
public class ParameterRequestWrapper extends HttpServletRequestWrapper {
	private Map params;

	public ParameterRequestWrapper(HttpServletRequest request, Map newParams) {
		super(request);
		params = newParams;
	}
	public Map getParameterMap() {
		return params;
	}

	public Enumeration getParameterNames() {
		Vector l = new Vector(params.keySet());
		return l.elements();
	}

	public String[] getParameterValues(String name) {
		Object v = params.get(name);
		if (v == null)
			return null;
		if (v instanceof String[])
			return (String[]) v;
		if (v instanceof String)
			return (new String[] { (String) v });
		else
			return (new String[] { v.toString() });
	}

	public String getParameter(String name) {
		Object v = params.get(name);
		if (v == null)
			return null;
		if (v instanceof String[]) {
			String strArr[] = (String[]) v;
			if (strArr.length > 0)
				return strArr[0];
			else
				return null;
		}
		if (v instanceof String)
			return (String) v;
		else
			return v.toString();
	}

}
