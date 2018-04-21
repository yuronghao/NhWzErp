package com.emi.common.util;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

public class HttpRequester {

	public static String send(String urlString, Map<String, Object> xml) {
		String msg = null;
		try {
			PostMethod postMethod = new PostMethod(urlString);
			postMethod.getParams().setParameter(
					"http.protocol.content-charset", "utf-8");
			NameValuePair[] data = new NameValuePair[xml.size()];
			Iterator it = xml.keySet().iterator();
			int index = 0;
			while (it.hasNext()) {
				String key = String.valueOf(it.next());
				String value = String.valueOf(xml.get(key));
				NameValuePair nvp = new NameValuePair(key, value);
				data[index] = nvp;
				index++;
			}
			postMethod.setRequestBody(data);

			HttpClient httpClient = new HttpClient();
			int connectTimeOut = 20;
			httpClient.setConnectionTimeout(connectTimeOut * 1000);

			httpClient.executeMethod(postMethod);
			msg = new String(postMethod.getResponseBodyAsString().getBytes(
					"8859_1"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return msg;
	}
}