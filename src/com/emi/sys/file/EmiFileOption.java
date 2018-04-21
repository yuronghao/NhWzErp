package com.emi.sys.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.common.util.FileUploadUtils;
import com.emi.sys.file.bean.YM_File;
import com.emi.sys.util.SysPropertites;

public class EmiFileOption extends BaseDao{
	/**
	 * @category 插入附件记录
	 * 2015年1月30日 下午3:53:46 
	 * @author 朱晓陈
	 * @return
	 */
	public boolean insertFile(YM_File file){
		file.setUploadTime(new Timestamp(System.currentTimeMillis()));
		return this.emiInsert(file);
	}
	
	/**
	 * @category 上传文件
	 * 2015年1月30日 下午3:59:31 
	 * @author 朱晓陈
	 * @return
	 */
	public boolean uploadFile(File file,String uploadPath){
		String suffix = FileUploadUtils.getExtension(file.getName());	//后缀名
		String fileName = UUID.randomUUID().toString()+"."+suffix;		//生成随机文件名
		
		boolean suc = FileUploadUtils.upload(fileName, uploadPath, file);//保存到磁盘
		if(suc){
			YM_File ym_file = new YM_File(file.length(), fileName, suffix, uploadPath+fileName);
			suc = insertFile(ym_file);	//保存文件记录
		}
		return suc;
	}
	
	/**
	 * 根据情况，创建文件夹,并上传文件
	 * 2015年2月2日 下午2:58:28 
	 * @author 朱晓陈
	 * @param file
	 * @param basePath
	 * @return 附件id
	 */
	public String createAndUpload(File file,String fileName,String basePath) throws IOException{
		String fileId = "";
		String suffix = FileUploadUtils.getExtension(fileName);	//后缀名
		String realfileName = UUID.randomUUID().toString()+"."+suffix;		//生成随机文件名
		String uploadPath = createBaseFolder(basePath);	//文件所在文件夹
		String realpath = uploadPath + realfileName;	//真实文件路径
		File realFile = new File(realpath);
		if(!realFile.exists()){	
			realFile.createNewFile();
		}
		boolean suc = FileUploadUtils.upload(realfileName, uploadPath, file);//保存到磁盘
		if(suc){
			String gId = UUID.randomUUID().toString();
			YM_File ym_file = new YM_File(file.length(), fileName, suffix, realpath);
			ym_file.setGid(gId);
			suc = insertFile(ym_file);	//保存文件记录
			
			fileId = gId;
		}
		return fileId;
	}
	
	/**
	 * @category 获取文件信息
	 * 2015年1月30日 下午4:59:06 
	 * @author 朱晓陈
	 * @param fileId
	 * @return
	 */
	public YM_File findFile(String fileId){
		return (YM_File) this.emiFind(fileId, YM_File.class);
	}
	
	public static byte[] downloadFile(String filePath){
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try 
        {
            in = new FileInputStream(filePath);        
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return data;//返回字节数组
    }
	
	/**
	 * @category 按月份创建文件夹
	 * 2015年2月2日 下午2:56:46 
	 * @author 朱晓陈
	 * @param basePath
	 * @return
	 */
	public static String createBaseFolder(String basePath){
		Date nowDate = new Date();
		String year = DateUtil.dateToString(nowDate, "yyyy");
		String month = DateUtil.dateToString(nowDate, "MM");
		String folder = basePath + year+File.separator+month+File.separator;
		File imgFolder = new File(folder);
		if(!imgFolder.exists()){
			imgFolder.mkdirs();
		}
		return folder;
	}
	
	//文件根目录
	public static String getBasePath(){
		if(CommonUtil.WindowOrLinux()){
			return SysPropertites.get("file.root");
		}else{
			return SysPropertites.get("linux.file.root");
		}
		
	}
}
