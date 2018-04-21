package com.emi.lfmember.dao.impl;
import java.util.HashMap;
import java.util.Map;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.lfmember.bean.Consumption;
import com.emi.lfmember.bean.LfMember;
import com.emi.lfmember.bean.ReCharge;
import com.emi.lfmember.dao.LfMemberDao;
import com.emi.sys.core.bean.PageBean;


public class LfMemberDaoImpl extends BaseDao  implements LfMemberDao{
	/**
	 * 查询会员数据
	 */
	public PageBean getLfMemberList(int pageIndex, int pageSize, String condition) {
		String sql = "select "+CommonUtil.colsFromBean(LfMember.class)+" from lf_member where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "id desc");
	}
	/**
	 * 
	 */
	public LfMember getLfMemberByCardno(String cardno){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("cardno", cardno);
		return (LfMember)this.emiFindByMultiCloumn(param,LfMember.class);
	}
	/**
	 * 
	 */
	public LfMember getLfMemberByPhone(String phone){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("phone", phone);
		return (LfMember)this.emiFindByMultiCloumn(param,LfMember.class);
	}
	
	public LfMember getLfMemberByNumber(String number){
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("number", number);
		return (LfMember)this.emiFindByMultiCloumn(param,LfMember.class);
	}
	/**
	 * 增加会员数据
	 */
	public boolean addLfMember(LfMember member){
		return this.emiInsert(member);
	}
	/**
	 * 更新会员数据
	 * @param member
	 * @return
	 */
	public boolean updateLfMember(LfMember member){
		return this.emiUpdate(member);
	}
	/**
	 * 根据id获取LfMember
	 * @param id
	 * @return
	 */
	public LfMember getLfMember(int id){
		return (LfMember)this.emiFind(String.valueOf(id), LfMember.class);
	}
	/**
	 * 充值记录
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public  PageBean getReChargeList(int pageIndex, int pageSize, String condition){
		String sql = "select "+CommonUtil.colsFromBean(ReCharge.class)+" from lf_recharge where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "id desc");
	}
	/**
	 * 新增充值记录
	 * @param recharge
	 * @return
	 */
	public boolean addRecharge(ReCharge recharge){
		return this.emiInsert(recharge);
	}
	/**
	 * 查询消费记录
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public  PageBean getConsumptionList(int pageIndex, int pageSize, String condition){
		String sql = "select "+CommonUtil.colsFromBean(Consumption.class)+" from lf_consumption where 1=1 "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "id desc");
	}
	/**
	 * 新增消费记录
	 * @param consumption
	 * @return
	 */
	public boolean addConsumption(Consumption consumption){
		return this.emiInsert(consumption);
	}
	
	
}
