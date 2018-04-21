package com.emi.flow.main.service.impl;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import com.emi.common.util.DateUtil;
import com.emi.flow.main.bean.FLOW_CodeRuleDetail;
import com.emi.flow.main.bean.FLOW_CodeRuleValue;
import com.emi.flow.main.dao.CodeRuleDao;
import com.emi.flow.main.service.CodeRuleService;

public class CodeRuleServiceImpl implements CodeRuleService {
	private CodeRuleDao codeRuleDao;

	public void setCodeRuleDao(CodeRuleDao codeRuleDao) {
		this.codeRuleDao = codeRuleDao;
	}

	@Override
	public String getNextRuleValue(String ruleId) {
		//获取按序号排序的规则详情
		List<FLOW_CodeRuleDetail> rdList = codeRuleDao.getRuleDetails(ruleId);
		String prefixCode = "";	//前缀
		String nextNumber = "";	//下一个数值
		int increment = 0;		//步长
		int initNumber = 1;		//自增初始值
		int valueLength = 0;	//数字长度
		//依次取出规则值
		for(FLOW_CodeRuleDetail rd : rdList){
			if(rd.getValueType().compareTo(0)==0){//字符串
				prefixCode += rd.getInitValue();
			}else if(rd.getValueType().compareTo(2)==0){//日期格式
				String format = rd.getFormat();
				prefixCode += DateUtil.dateToString(new Date(), format);
			}else if(rd.getValueType().compareTo(1)==0){//自增的数值，先取步长、初始值、长度
				increment = rd.getIncrement();
				initNumber = Integer.parseInt(rd.getInitValue());
				valueLength = rd.getValueLength();
			}
				
		}
		//根据前缀得到下一个值，并更新原值
		nextNumber = this.getNextNumber(ruleId,prefixCode,initNumber,increment,valueLength);
		
		return prefixCode+nextNumber;
	}
	
	/**
	 * @category 下一个自增值
	 * 2015年1月22日 上午10:42:04 
	 * @author 朱晓陈
	 * @return
	 */
	private String getNextNumber(String ruleId,String prefixCode, int initNumber,
			int increment,int valueLength) {
		int nextNumber = 0;
//		Integer curNumber = codeRuleDao.getCurNumber(prefixCode);
		//当前值对象
		FLOW_CodeRuleValue curValue = codeRuleDao.getCurValue(prefixCode);
		if(curValue == null){
			nextNumber = initNumber;
			//插入一条数据
			FLOW_CodeRuleValue rv = new FLOW_CodeRuleValue();
			rv.setCodeRuleId(ruleId);
			rv.setPrefixCode(prefixCode);
			rv.setCurNumber(nextNumber);
			codeRuleDao.insertRuleValue(rv);
		}else{
			nextNumber = curValue.getCurNumber()+1;
			curValue.setCurNumber(nextNumber);
			//更新数值
			codeRuleDao.updateRuleValue(curValue);
		}
		
		String nextStr = nextNumber+"";
		//根据长度补位
		if(valueLength>0){
			NumberFormat formatter = NumberFormat.getNumberInstance();     
			formatter.setMinimumIntegerDigits(valueLength);     
			formatter.setGroupingUsed(false);     
			nextStr = formatter.format(nextNumber); 
		}
		return nextStr;
	}

	public static void main(String[] args) {
		String apikey = "54b842a74aec9347aff85e16e72eee33";
		String encoding = "utf-8";
//		String info = URLEncoder.encode("你是谁",encoding);
//		String requestUrl = "http://www.tuling123.com/openapi/api?key="+apikey+"&info="+info;
	}
}
