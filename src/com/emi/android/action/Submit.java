package com.emi.android.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Submit {
	  //post 请求  传参数
		 public static StringBuffer sendPostRequest(String parameter,String urls) {
		       //    String data = "width=50&height=100";
		        try {	            
		            URL url = new URL(urls);
		            URLConnection conn = url.openConnection();
		            conn.setDoOutput(true);
		            
//		            conn.setConnectTimeout(1000);
//		            conn.setReadTimeout(1000);
		            
		            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(),"UTF-8");
		            writer.write(parameter);
		            writer.flush();
		            
		            StringBuffer answer = new StringBuffer();
		            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		            String line;
		            while ((line = reader.readLine()) != null) {
		                answer.append(line);
		            }
		            writer.close();
		            reader.close();
		            System.out.println(answer);
		            return answer;
		            //System.out.println(answer.toString());
		        } catch (MalformedURLException ex) {
		            ex.printStackTrace();
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
				return null;
		 }
}
