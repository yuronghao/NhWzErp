package com.emi.lfmember.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.emi.lfmember.bean.Consumption;
import com.emi.lfmember.bean.LfMember;
import com.emi.lfmember.bean.ReCharge;
import com.emi.lfmember.dao.LfMemberDao;
import com.emi.lfmember.service.LfMemberService;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.util.NewSendTemplateSMS;

public class LfMemberServiceImpl implements LfMemberService{
	private LfMemberDao lfMemberDao;

	public LfMemberDao getLfMemberDao() {
		return lfMemberDao;
	}

	public void setLfMemberDao(LfMemberDao lfMemberDao) {
		this.lfMemberDao = lfMemberDao;
	}
	
	public LfMember getLfMemberByCardno(String cardno){
		return this.lfMemberDao.getLfMemberByCardno(cardno);
	}
	public LfMember getLfMemberByPhone(String phone){
		return this.lfMemberDao.getLfMemberByPhone(phone);
	}
	
	public LfMember getLfMemberByNumber(String number){
		return this.lfMemberDao.getLfMemberByNumber(number);
	}

	public PageBean getLfMemberList(int pageIndex, int pageSize, String condition){
		return lfMemberDao.getLfMemberList(pageIndex, pageSize, condition);
	}
	/**
	 * 增加会员数据
	 */
	public boolean addLfMember(LfMember member){
		member.setCtime(new Timestamp(((Date)(new Date())).getTime()));
		return lfMemberDao.addLfMember(member);
	}
	/**
	 * 更新会员数据
	 * @param member
	 * @return
	 */
	public boolean updateLfMember(LfMember member){
		return lfMemberDao.updateLfMember(member);
	}
	/**
	 * 根据id获取LfMember
	 * @param id
	 * @return
	 */
	public LfMember getLfMember(int id){
		return lfMemberDao.getLfMember(id);
	}
	/**
	 * 充值记录
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public  PageBean getReChargeList(int pageIndex, int pageSize, String condition){
		return lfMemberDao.getReChargeList(pageIndex, pageSize, condition);
	}
	/**
	 * 新增充值记录
	 * @param recharge
	 * @return
	 */
	public boolean addRecharge(ReCharge recharge){
		LfMember lfmember=lfMemberDao.getLfMember(recharge.getMemberid());
		if(lfmember!=null){
			lfmember.setBlance(lfmember.getBlance().add(recharge.getCharge()));
			lfMemberDao.updateLfMember(lfmember);
			recharge.setCtime(new Timestamp(((Date)(new Date())).getTime()));
			boolean ret= lfMemberDao.addRecharge(recharge);
			if(ret){
//100547		
				String endno=lfmember.getCardno();
				if(endno.length()>4){
					endno=endno.substring(endno.length()-4);
				}
				SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日  HH时mm分"); 
				String datrstr=sdf.format(new Date());
				NewSendTemplateSMS.sendRegMsg(lfmember.getPhone(), new String[]{endno,datrstr,String.valueOf(recharge.getCharge()),String.valueOf(lfmember.getBlance().doubleValue())},"103579");
				//NewSendTemplateSMS.sendRegMsg(lfmember.getPhone(), new String[]{String.valueOf(consumption.getAmount())});
			}
			return ret;
		}
		return false;
	}
	/**
	 * 查询消费记录
	 * @param pageIndex
	 * @param pageSize
	 * @param condition
	 * @return
	 */
	public  PageBean getConsumptionList(int pageIndex, int pageSize, String condition){
		return lfMemberDao.getConsumptionList(pageIndex, pageSize, condition);
	}
	/**
	 * 新增消费记录
	 * @param consumption
	 * @return
	 */
	public boolean addConsumption(Consumption consumption){
		LfMember lfmember=lfMemberDao.getLfMember(consumption.getMemberid());
		if(lfmember!=null){
			lfmember.setBlance(lfmember.getBlance().subtract(consumption.getAmount()));
			lfMemberDao.updateLfMember(lfmember);
			consumption.setCtime(new Timestamp(((Date)(new Date())).getTime()));
			boolean ret= lfMemberDao.addConsumption(consumption);
			if(ret){
				//【一米科技】尊敬的客户您好，您尾号为{1}的会员卡账户{2}消费支出{3}元，账上余额{4}元
				String endno=lfmember.getCardno();
				if(endno.length()>4){
					endno=endno.substring(endno.length()-4);
				}
				SimpleDateFormat sdf=new SimpleDateFormat("MM月dd日  HH时mm分"); 
				String datrstr=sdf.format(new Date());
				NewSendTemplateSMS.sendRegMsg(lfmember.getPhone(), new String[]{endno,datrstr,String.valueOf(consumption.getAmount()),String.valueOf(lfmember.getBlance().doubleValue())},"103580");
			}
			return ret;
		}
		return false;
	}
}
