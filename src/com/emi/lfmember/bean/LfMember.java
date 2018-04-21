package com.emi.lfmember.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "lf_member")
public class LfMember implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6451912642094972072L;
	@EmiColumn(name = "id", increment = true,ID=true)
	private int id;
	@EmiColumn(name = "cardno")
	private String cardno;
	@EmiColumn(name = "name")
	private String name;
	@EmiColumn(name = "blance")
	private BigDecimal blance;
	@EmiColumn(name = "phone")
	private String phone;
	@EmiColumn(name = "ctime",hasDefault = true)
	private Timestamp ctime;
	@EmiColumn(name = "state")
	private int state;
	@EmiColumn(name = "number")
	private String number;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getBlance() {
		return blance;
	}
	public void setBlance(BigDecimal blance) {
		this.blance = blance;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Timestamp getCtime() {
		return ctime;
	}
	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	

}
