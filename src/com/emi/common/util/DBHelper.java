/**
 * 
 */
package com.emi.common.util;

import java.sql.Connection;  
import java.sql.DriverManager;  
import java.sql.PreparedStatement;  
import java.sql.SQLException;  

import org.apache.tools.ant.types.CommandlineJava.SysProperties;

import com.emi.sys.init.Configuration;
import com.emi.sys.util.SysPropertites;
  
public class DBHelper {  
//    public static final String url = "jdbc:mysql://192.168.2.112:3306/sun_city_platform_test";  
//    public static final String name = "com.mysql.jdbc.Driver";  
//    public static final String user = "suncity";  
//    public static final String password = "sc2015";  
  
    public Connection conn = null;  
    public PreparedStatement pst = null;  
  
    public DBHelper(String sql) {  
        try {  
        	Configuration cfg = new Configuration("jdbc.properties");
        	String url = cfg.getValue("jdbc.url") ;
            String name =  cfg.getValue("jdbc.driverClassName") ;
            String user =  cfg.getValue("jdbc.username") ;
            String password =  cfg.getValue("jdbc.password") ;
            Class.forName(name);//指定连接类型  
            conn = DriverManager.getConnection(url, user, password);//获取连接  
            pst = conn.prepareStatement(sql);//准备执行语句  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
  
    public void close() {  
        try {  
            this.conn.close();  
            this.pst.close();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
    }  
}  
