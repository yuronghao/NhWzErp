
package com.emi.sys.util;
import java.util.HashMap;
import java.util.Set;

import com.cloopen.rest.sdk.CCPRestSDK; 
import com.cloopen.rest.sdk.CCPRestSDK.BodyType; 
public class NewSendTemplateSMS {
	
	private static String ACCOUNT_SID="8a48b551493ffbd401494117e16e015c";
//	private static String AUTH_TOKEN="da90aaca0dae11e5ac73ac853d9f54f2";
	private static String AUTH_TOKEN="79fd064aa81e445581308a23c77e782c";
	
	
	private static String TEMPLATE_ID = "6096";//"9901";
	private static String APP_ID = "aaf98f89493ff1d30149411d80940158";
	private static String SERVER_IP = "app.cloopen.com";
	private static String SERVER_PORT = "8883";
	
	
	
	
//	private static String ACCOUNT_SID="8a48b551493ffbd401494117e16e015c";
//	private static String AUTH_TOKEN="79fd064aa81e445581308a23c77e782c";
//	
//	private static String TEMPLATE_ID = "6096";//"9901";
//	private static String APP_ID = "8a48b551493ffbd4014941180561015e";
//	private static String SERVER_IP = "sandboxapp.cloopen.com";
//	private static String SERVER_PORT = "8883";
	

	 public static void sendRegMsg(String phoneNumber,String[] msg,String templateid) {
	 HashMap result = null; 
	 CCPRestSDK restAPI = new CCPRestSDK();
	 restAPI.init(SERVER_IP, SERVER_PORT);
	 // 初始化服务器地址和端口，沙盒环境配置成sandboxapp.cloopen.com，生产环境配置成app.cloopen.com，端口都是8883. 
	 restAPI.setAccount(ACCOUNT_SID, AUTH_TOKEN);
	 // 初始化主账号名称和主账号令牌，登陆云通讯网站后，可在"控制台-应用"中看到开发者主账号ACCOUNT SID和 
	 //主账号令牌AUTH TOKEN。
	 restAPI.setAppId(APP_ID);
	 // 初始化应用ID，如果是在沙盒环境开发，请配置"控制台-应用-测试DEMO"中的APPID。
	 //如切换到生产环境，请使用自己创建应用的APPID
	 result = restAPI.sendTemplateSMS(phoneNumber, templateid ,msg);
	 System.out.println("SDKTestGetSubAccounts result=" + result); 
	 if("000000".equals(result.get("statusCode"))){
	 //正常返回输出data包体信息（map）
	 HashMap data = (HashMap) result.get("data");
	 
	 Set<String> keySet = data.keySet();
	 for(String key:keySet){ 
	 Object object = data.get(key); 
	 System.out.println(key +" = "+object); 
	 }
	 }else{
	 //异常返回输出错误码和错误信息
	 System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
	 }
	 }
	 
	 
//	 public static void main(String[] args) {
//		 try{
//			 String[] abc = new String[2];
//			 abc[0] ="1";
//			 abc[1] ="2";
//
//			 sendRegMsg("15162822835",abc);
//		 }catch(Exception e){
//			 e.printStackTrace();
//		 }
//	}
	 
	 //测试环境
//	 public static void main(String[] args) {
//		 HashMap<String, Object> result = null;
//
//			CCPRestSDK restAPI = new CCPRestSDK();
//			restAPI.init("sandboxapp.cloopen.com", "8883");// 初始化服务器地址和端口，格式如下，服务器地址不需要写https://
//			restAPI.setAccount("8a48b551493ffbd401494117e16e015c", "79fd064aa81e445581308a23c77e782c");// 初始化主帐号和主帐号TOKEN
//			restAPI.setAppId("8a48b551493ffbd4014941180561015e");// 初始化应用ID
//			result = restAPI.sendTemplateSMS("15162822835","1" ,new String[]{"模板内容1","模板内容2"});
//
//			System.out.println("SDKTestSendTemplateSMS result=" + result);
//			if("000000".equals(result.get("statusCode"))){
//				//正常返回输出data包体信息（map）
//				HashMap<String,Object> data = (HashMap<String, Object>) result.get("data");
//				Set<String> keySet = data.keySet();
//				for(String key:keySet){
//					Object object = data.get(key);
//					System.out.println(key +" = "+object);
//				}
//			}else{
//				//异常返回输出错误码和错误信息
//				System.out.println("错误码=" + result.get("statusCode") +" 错误信息= "+result.get("statusMsg"));
//			}
//		}

	
	 }

