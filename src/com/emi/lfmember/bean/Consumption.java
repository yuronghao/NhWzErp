package com.emi.lfmember.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name = "lf_consumption")
public class Consumption implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8006581297032030750L;
	@EmiColumn(name = "id", increment = true)
	private int id;
	@EmiColumn(name = "memberid")
	private int memberid;
	@EmiColumn(name = "cardno")
	private String cardno;
	@EmiColumn(name = "amount")
	private BigDecimal amount;
	@EmiColumn(name = "ctime")
	private Timestamp ctime;
	@EmiColumn(name = "name")
	private String name;
	@EmiColumn(name = "phone")
	private String phone;
	@EmiColumn(name = "number")
	private String number;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getMemberid() {
		return memberid;
	}
	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}
	public String getCardno() {
		return cardno;
	}
	public void setCardno(String cardno) {
		this.cardno = cardno;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Timestamp getCtime() {
		return ctime;
	}
	public void setCtime(Timestamp ctime) {
		this.ctime = ctime;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	
}
