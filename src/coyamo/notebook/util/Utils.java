package coyamo.notebook.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	public static String translate(String str) throws Exception {
		String json = get(
				"http://fanyi.youdao.com/translate?&doctype=json&type=AUTO&i=" + URLEncoder.encode(str, "utf-8"));
		String pattStr = "(?<=\").*?(?=\")";
		Pattern pattern = Pattern.compile(pattStr);
		Matcher matcher = pattern.matcher(json);
		String result = "·­ÒëÊ§°Ü";
		while (matcher.find()) {
			result = matcher.group();
		}

		return result;
	}

	public static String get(String url) {
		if (url == null || url.trim().equals(""))
			return "";
		BufferedReader reader = null;
		try {
			URL url1 = new URL(url);
			URLConnection connection = url1.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer();
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				sb.append(temp);
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}