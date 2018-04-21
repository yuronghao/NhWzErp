package com.emi.androidweigh.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.androidweigh.dao.WeighDao;
import com.emi.cache.service.CacheCtrlService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.FileUploadUtils;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaProviderCustomer;

public class WeighService {

	private CacheCtrlService cacheCtrlService;
	private WeighDao weighDao;

	public void setWeighDao(WeighDao weighDao) {
		this.weighDao = weighDao;
	}
	
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}



	/** 
	 * @category 待称重物料
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getToWeighList(){
		String condition=" pa.stateForSynchro=2  and  pa.gid in (select procureArrivalUid from WM_ProcureArrival_C pac "
				+ " LEFT JOIN AA_Goods gs on pac.goodsUid=gs.gid where isnull(gs.invWeight,0)<>0 and isnull(weighedNum,0)=0 and number>isnull(putinNumber,0)  )   order by billDate desc ";
		List<Map> maps=weighDao.getToWeighList(condition);
		for(Map m:maps){
			AaProviderCustomer p=cacheCtrlService.getProviderCustomer(m.get("supplierUid").toString());
			m.put("dDate", m.get("dDate").toString().substring(0, 10));
			m.put("cvenCode", p.getPcname());
		}
		
		return maps;
	}
	
	
	/**
	 * @category 待称重物料详情
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getToWeighDetail(String id){
		String condition=" pac.idForSynchro ='"+id+"' and isnull(gs.invWeight,0)<>0 and isnull(weighedNum,0)=0 ";
		List<Map> maps=weighDao.getToWeighDetail(condition);
		for(Map m:maps){
			AaGoods goods=cacheCtrlService.getGoods(m.get("goodsUid").toString());
			m.put("cinvCode",goods.getGoodscode());
			m.put("cinvName", goods.getGoodsname());
			m.put("cInvStd", CommonUtil.Obj2String(goods.getGoodsstandard()));
			m.put("unitName", goods.getUnitName());
			m.put("invWeight", goods.getInvWeight());//存货单净重
			m.put("weightRate", goods.getWeightRate());//误差百分比
			m.put("grossWeight", goods.getGrossWeight());//框重
			m.put("minPackNum", goods.getMinPackNum());//最小包装量 
		}
		
		return maps;
	}
	
	/**
	 * @category 提交称重
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public boolean subWeigh(JSONArray jsonArray) throws Exception{
		
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
			
			BigDecimal num=CommonUtil.str2BigDecimal(prowctJson.get("iquantity"));//物品个数
			BigDecimal weight=CommonUtil.str2BigDecimal(prowctJson.get("weight"));//毛重
			BigDecimal grossWeight=CommonUtil.str2BigDecimal(prowctJson.get("grossWeight"));//单个框子重
			BigDecimal grossNum=CommonUtil.str2BigDecimal(prowctJson.get("grossNum"));//框子个数
			
			
//			BigDecimal grossWeight=CommonUtil.isNullObject(aaGoods.getGrossWeight())?new BigDecimal(0):aaGoods.getGrossWeight() ;//单个框子重
//			BigDecimal minPackNum=CommonUtil.isNullObject(aaGoods.getMinPackNum())?new BigDecimal(1):aaGoods.getMinPackNum();//最小包装量
//			BigDecimal allGrossNum=BigDecimal.valueOf(Math.ceil(num.divide(minPackNum, 2, BigDecimal.ROUND_HALF_UP).doubleValue()));//所有框子个数 +1取整数
//			BigDecimal allGrossWeight=allGrossNum.multiply(grossWeight);//所有框子重
			
			prowctJson.put("lastWeight", weight.subtract(grossWeight.multiply(grossNum)).toPlainString());
		}
		
		weighDao.subWeigh(jsonArray);
		weighDao.subWeighYonYou(jsonArray);
		return true;
	}
	
	
	/**
	 * @category 工序领料称重列表
	 *2016 2016年6月27日上午10:08:35
	 *void
	 *宋银海
	 */
	public List<Map> getProcessTaskList(String taskTypeCode){
		String condition=" bt.billCode='"+taskTypeCode+"' and t.state<>2 ";
		return weighDao.getProcessTaskList(condition);
	}
	
	
	//创建打印文件
	public boolean createFile(JSONObject json){
		try{
			String cinvCode = json.getString("cinvCode");       //存货编码
			String cinvName = json.getString("cinvName");       //存货名称
			String cInvStd = json.getString("cInvStd");       //规格型号
			String unitName = json.getString("unitName");       //单位名称
			String batch ="";
			if(json.containsKey("batch")){
				batch=json.getString("batch");       //批次
			}
					
			String cfree1Code = "";       //自由项1编号
			if(json.containsKey("cfree1Code")){
				cfree1Code = json.getString("cfree1Code");       //自由项1编号
			}
			
			String cfree1 = "";       //自由项1名称
			if(json.containsKey("cfree1")){
				cfree1 = json.getString("cfree1");       //自由项1名称
			}
			
			String dDate= json.getString("dDate");          //日期
			String iquantity= json.getString("iquantity");  //数量
			String minPackNum= json.getString("minPackNum");//最小包装量
			String weight= json.getString("weight");        //重量
			String printerName=json.getString("printerName");//打印机名称
			String printerTimes=json.getString("printerTimes");//打印次数
			String grossNum=json.getString("grossNum");//框数
			String arrivalCode=json.getString("orderCode");//到货单号
			
			BigDecimal netWeight=new BigDecimal(weight).divide(new BigDecimal(grossNum),2, BigDecimal.ROUND_HALF_UP);//净重
			
			String ss;
			String template="template90501";//90*50 带批次
			if(CommonUtil.isNullObject(batch)){
				template="template90502";//90*50 不带批次
			}
			if(!CommonUtil.isNullString(cfree1)){
				ss = printerName+"|"+template+"|sntext="+cinvName+"|sncode="+cinvCode+"&"+cfree1Code+"|snbatch="+batch+"|cfree="+cfree1+"|standard="+cInvStd+"|date="+dDate+"|amount="+minPackNum+"|weight="+netWeight.toPlainString()+"|arrivalCode="+arrivalCode;
			}else{
				ss = printerName+"|"+template+"|sntext="+cinvName+"|sncode="+cinvCode+"|snbatch="+batch+"|cfree="+cfree1+"|standard="+cInvStd+"|date="+dDate+"|amount="+minPackNum+"|weight="+netWeight.toPlainString()+"|arrivalCode="+arrivalCode;
			}
			
			for(int i=0;i<Integer.parseInt(printerTimes);i++){
//				File uploadFile=  new File(Config.PRINTFILE+UUID.randomUUID()+".txt");
//				File uploadFile=new FileOutputStream(name, append);
//				FileUploadUtils.uploadForName(UUID.randomUUID()+".txt", Config.PRINTFILE, uploadFile);
				createFile(ss, Config.PRINTFILE);
			}
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	
	public  boolean createFile(String content,String filepath){
		BufferedWriter bw = null;
		FileWriter fw =null;
		String filename=UUID.randomUUID()+".txt";
		File file=new File(filepath+filename);
		try{
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			bw.write(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				 if(bw!=null){
				   bw.close();
				 }
				 if(fw!=null){
					 fw.close();
				 }
				} catch (IOException e) {
				    e.printStackTrace();
			 }
		}
		if(file!=null&&file.exists()){
			return true;
		}
		return false;
	}
	
	
	
}
