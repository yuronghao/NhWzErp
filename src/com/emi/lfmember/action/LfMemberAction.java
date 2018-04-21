package com.emi.lfmember.action;

import java.io.IOException;

import org.apache.struts2.util.TokenHelper;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.lfmember.bean.Consumption;
import com.emi.lfmember.bean.LfMember;
import com.emi.lfmember.bean.ReCharge;
import com.emi.lfmember.service.LfMemberService;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;

public class LfMemberAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9051697888199870960L;
	private LfMemberService lfMemberService;
	private LfMember lfmember;
	private ReCharge recharge;
	private Consumption consumption;
	public LfMemberService getLfMemberService() {
		return lfMemberService;
	}

	public void setLfMemberService(LfMemberService lfMemberService) {
		this.lfMemberService = lfMemberService;
	}
	
	

	public LfMember getLfmember() {
		return lfmember;
	}

	public void setLfmember(LfMember lfmember) {
		this.lfmember = lfmember;
	}

	public ReCharge getRecharge() {
		return recharge;
	}

	public void setRecharge(ReCharge recharge) {
		this.recharge = recharge;
	}

	public Consumption getConsumption() {
		return consumption;
	}

	public void setConsumption(Consumption consumption) {
		this.consumption = consumption;
	}

	public String lfindex(){
		return "lfindex";
	}
	/**
	 * 获取所有会员卡信息
	 * @return
	 */
	public String memberlist(){
		String keyWord = this.getParameter("keyWord");
		String state=this.getParameter("state");
		int pageIndex = getPageIndex();		
		int pageSize = Config.PAGESIZE_WEB;		
		/*	String columns = "";
		String condition = CommonUtil.combQuerySql(columns, memberid);	//过滤条件*/
		String condition="";
		if(!CommonUtil.isNullString(keyWord)){
			condition=condition+" and  (name like '%"+keyWord+"%' or phone like '%"+keyWord+"%'  or cardno like '%"+keyWord+"%')";
		}
		if(!CommonUtil.isNullObject(state)){
			condition=condition+" and state="+state;
			this.setRequstAttribute("state", Integer.parseInt(state));
		}
		PageBean pageBean = lfMemberService.getLfMemberList(pageIndex,pageSize,condition);
		this.setRequstAttribute("keyWord", keyWord);
		this.setRequstAttribute("data", pageBean);
		return "memberlist";
	}
	/**
	 * 跳至增加、修改会员页面
	 * @return
	 */
	public String showAddModifyMember(){
		String idstr=this.getRequest().getParameter("id");
		if(idstr!=null&&!"".equals(idstr.trim())){
			this.setRequstAttribute("member", lfMemberService.getLfMember(Integer.parseInt(idstr)));
		}
		return "addmodifymember";
	}
	
	public void checkMemberIsExists(){
		String type=getRequest().getParameter("datatype");
		String datavalue=getRequest().getParameter("datavalue");
		String id=getRequest().getParameter("memberid");
		if(type!=null&&datavalue!=null){
			try {
				LfMember member=null;
				if("1".equals(type)){
					 member=lfMemberService.getLfMemberByCardno(datavalue);
				}
				if("2".equals(type)){
					 member=lfMemberService.getLfMemberByPhone(datavalue);
				}
				if("3".equals(type)){
					 member=lfMemberService.getLfMemberByNumber(datavalue);
				}
				String ret="success";
				if(member!=null){
					if(id!=null&&member.getId()==Integer.parseInt(id)){
						 ret="success";
					}else{
						ret="error";
					}
				}
				getResponse().getWriter().write(ret);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 新增修改会员页面
	 * @return
	 */
	public String addmodifyMember(){
		if(!TokenHelper.validToken()){
			return memberlist();
		}
		if(lfmember!=null){
			if(lfmember.getId()>0){
				lfMemberService.updateLfMember(lfmember);
			}else{
				lfMemberService.addLfMember(lfmember);
			}
			return memberlist();
		}
		return null;
	}
	
	public String showRecharge(){
		return "showRecharge";
	}
	
	/**
	 * 根据cardno查询会员
	 */
	public void getMemberByCardno(){
		String cardno=getRequest().getParameter("cardno");
		if(cardno!=null){
			LfMember member=lfMemberService.getLfMemberByCardno(cardno);
			try {
				getResponse().getWriter().write(EmiJsonObj.fromObject(member).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void getMemberByNumber(){
		String phone=getRequest().getParameter("number");
		if(phone!=null){
			LfMember member=lfMemberService.getLfMemberByNumber(phone);
			try {
				getResponse().getWriter().write(EmiJsonObj.fromObject(member).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void getMemberByPhone(){
		String phone=getRequest().getParameter("phone");
		if(phone!=null){
			LfMember member=lfMemberService.getLfMemberByPhone(phone);
			try {
				getResponse().getWriter().write(EmiJsonObj.fromObject(member).toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 充值
	 * @return
	 */
	public String addRecharge(){
		if(TokenHelper.validToken()){
			if(recharge!=null){
				lfMemberService.addRecharge(recharge);
			}
		}
		return rechargeList(); 
	}
	/**
	 * 充值记录
	 * @return
	 */
	public String rechargeList(){
		String keyWord = this.getParameter("keyWord");
		String memberid = this.getParameter("memberid");
		int pageIndex = getPageIndex();		
		int pageSize = Config.PAGESIZE_WEB;		
		String columns = "memberid";
		String condition = CommonUtil.combQuerySql(columns, memberid);	//过滤条件
		if(!CommonUtil.isNullString(keyWord)){
			condition=" and (name like '%"+keyWord+"%' or phone like '%"+keyWord+"%'  or cardno like '%"+keyWord+"%')";
		}
		PageBean pageBean = lfMemberService.getReChargeList(pageIndex,pageSize,condition);
		this.setRequstAttribute("keyWord", keyWord);
		this.setRequstAttribute("data", pageBean);
		return "rechargeList";
	}
	/**
	 * 消费页面
	 * @return
	 */
	public String showConsumption(){
		return "showConsumption";
	}
	/**
	 * 消费列表
	 * @return
	 */
	public String consumptionList(){
		String keyWord = this.getParameter("keyWord");
		String memberid = this.getParameter("memberid");
		int pageIndex = getPageIndex();		
		int pageSize = Config.PAGESIZE_WEB;		
		String columns = "memberid";
		String condition = CommonUtil.combQuerySql(columns, memberid);	//过滤条件
		if(!CommonUtil.isNullString(keyWord)){
			condition=" and (name like '%"+keyWord+"%' or phone like '%"+keyWord+"%'  or cardno like '%"+keyWord+"%')";
		}
		PageBean pageBean = lfMemberService.getConsumptionList(pageIndex,pageSize,condition);
		this.setRequstAttribute("keyWord", keyWord);
		this.setRequstAttribute("data", pageBean);
		return "consumptionList";
		
	}
	/**
	 * 消费
	 * @return
	 */
	public String addConsumption(){
		if(TokenHelper.validToken()){
			if(consumption!=null){
				lfMemberService.addConsumption(consumption);
			}
		}
		return consumptionList(); 
	}
	
}
