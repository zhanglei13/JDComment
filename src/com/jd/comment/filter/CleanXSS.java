package com.jd.comment.filter;

/**
 * 
 * Class: CleanXSS <br/>
 * Description: 过滤url参数中的特殊字符，防止SQL注入
 * CreatedTimeime: 2015年3月31日 下午10:14:32 <br/>
 * @author zhanglei
 * @version V1.0
 */
public class CleanXSS {
	/**
	 * INJ_STR:定义有哪些特殊字符
	 */
	public static final String INJ_STR = "\" \\( \\) ' \\* % -- = \\+ < > \\\\ ; & insert update delete select truncate and or like create drop alter";

	/**
	 * 创建一个新的实例 CleanXSS.
	 */
	public CleanXSS() {
	}

	/**
	 * 名称：cleanXSS <br/>
	 * 描述：清除url中的特殊字符 <br/>
	 * 
	 * @param value
	 * @return
	 */
	public static String cleanXSS(String value) {
		if (value == null)
			return null;
		String inj_stra[] = "\" \\( \\) ' \\* % -- = \\+ < > \\\\ ; & insert update delete select truncate and or like create drop alter"
				.split(" ");
		for (int i = 0; i < inj_stra.length; i++)
			value = value.replaceAll(inj_stra[i], "");
		return value;
	}
}