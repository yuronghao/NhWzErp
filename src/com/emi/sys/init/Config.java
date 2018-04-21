package com.emi.sys.init;

import java.sql.ResultSet;

import com.emi.common.util.DBHelper;


public class Config {

    private static Config config = null;

	public static int PAGESIZE_WEB = 8;// web端每页条数,初始化时会根据配置文件改变
	public static int PAGESIZE_MOB = 10;// 移动端每页条数,初始化时会根据配置文件改变
	public static String[] SYS_NAMES = {};// 参与权限管理的系统名,初始化时会根据配置文件改变
	
	public static String INTERFACETYPE;// 接口类型
	public static String INTERFACEADDRESS;//接口地址
//	public static String CACHESERVERIP;//缓存ip
//	public static String CACHEPORT;//缓存端口
	public static String PRINTFILE;//打印文件夹位置
	public static String PRINTMACHINE;//打印机
//	public static String EAICODE;//接口系统编码
	public static String BUSINESSDATABASE;//业务数据名
	public static String DEFAULT_DISPATCHING = "0"; //工序设置-默认派工对象类型  0：人员  1：组   2：委外商
	static String sql = null;  
    static DBHelper db1 = null;  
    static ResultSet ret = null; 
	
    // 2.step
    private Config(){
        System.out.println("CONFIG 初始化!");
        try {
			this.initParameter();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // 1.step
    public static void getInstance(){
        if( config == null ){
            config = new Config();            
        }       
    }
    
    // 3.step
    private void initParameter() throws Exception{
        System.out.println("读取配置文件信息 !!!");
		Configuration cfg = new Configuration("text.properties");
		String pageSize_web = cfg.getValue("pageSize.web");
		String pageSize_mob = cfg.getValue("pageSize.mobile");
		String rmSys = cfg.getValue("rm.sysNames");
		
		//String interfaceTYpe= cfg.getValue("interfaceType");
		//String interfaceAddress=cfg.getValue("interfaceAddress");
		sql = "select * from YM_Settings ";
		 String interfaceType="";
		 String interfaceAddress="";
		 String cacheserverIp="";
		 String cacheport="";
		 String printfile="";
		 String printmachine="";
        try {
			db1 = new DBHelper(sql);
			ret = db1.pst.executeQuery();
			while (ret.next()) {  
				if(ret.getString(3).equals("interfaceType"))
				interfaceType = ret.getString(4);  
				if(ret.getString(3).equals("interfaceAddress"))
					interfaceAddress = ret.getString(4);
				if(ret.getString(3).equals("cacheserverIp"))
					cacheserverIp = ret.getString(4);
				if(ret.getString(3).equals("cacheport"))
					cacheport = ret.getString(4);
				if(ret.getString(3).equals("printfile"))
					printfile = ret.getString(4);
				if(ret.getString(3).equals("printmachine"))
					printmachine = ret.getString(4);
			}
			ret.close();  
			db1.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
        
		Configuration cfg2 = new Configuration("jdbc.properties");
		String busiNessDataBase=cfg2.getValue("jdbc2.url");
		String[] dbs=busiNessDataBase.split("=");
		
		try {
			Config.PAGESIZE_MOB = Integer.parseInt(pageSize_mob);
			Config.PAGESIZE_WEB = Integer.parseInt(pageSize_web);
			Config.SYS_NAMES = rmSys.split(",");
			
			Config.INTERFACETYPE= interfaceType;
			Config.INTERFACEADDRESS= interfaceAddress;
//			Config.CACHESERVERIP= cacheserverIp;
//			Config.CACHEPORT= cacheport;
			Config.PRINTFILE= printfile;
			Config.PRINTMACHINE= printmachine;
			Config.BUSINESSDATABASE=dbs[1]+".dbo.";
		} catch (NumberFormatException e) {
			System.out.println("pageSize值有误，将自动设置成默认值");
		}finally{
			
		}
        
    }

}



