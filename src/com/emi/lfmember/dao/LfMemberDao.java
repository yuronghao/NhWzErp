package com.emi.lfmember.dao;

import com.emi.lfmember.bean.Consumption;
import com.emi.lfmember.bean.LfMember;
import com.emi.lfmember.bean.ReCharge;
import com.emi.sys.core.bean.PageBean;

public interface LfMemberDao {
	/**
	 * 查询会员列表
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public PageBean getLfMemberList(int pageIndex, int pageSize, String condition);
	
	public LfMember getLfMemberByCardno(String cardno);
	/**
	 * 增加人员
	 * @param member
	 * @return
	 */
	public boolean addLfMember(LfMember member);
	public LfMember getLfMemberByPhone(String phone);
	public LfMember getLfMemberByNumber(String number);
	/**
	 * 更新会员
	 * @param member
	 * @return
	 */
	public boolean updateLfMember(LfMember member);
	/**
	 * 根据id获取LfMember
	 * @param id
	 * @return
	 */
	public LfMember getLfMember(int id);
	/**
	 * 充值记录
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public  PageBean getReChargeList(int pageIndex, int pageSize, String condition);
	/**
	 * 新增充值记录
	 * @param recharge
	 * @return
	 */
	public boolean addRecharge(ReCharge recharge);
	/**
	 * 查询消费记录
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public  PageBean getConsumptionList(int pageIndex, int pageSize, String condition);
	/**
	 * 新增消费记录
	 * @param consumption
	 * @return
	 */
	public boolean addConsumption(Consumption consumption);
	
}
