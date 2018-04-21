package com.emi.cache.service.impl;

import java.util.TimerTask;

import com.emi.cache.dao.CacheCtrldao;
import com.emi.cache.service.CacheCtrlService;
import com.emi.cache.service.CacheTask;

public class CacheTaskImpl extends TimerTask implements CacheTask {
	private CacheCtrldao cacheCtrldao;

	public void setCacheCtrldao(CacheCtrldao cacheCtrldao) {
		this.cacheCtrldao = cacheCtrldao;
	}

	@Override
	public void Task() {
		 cacheCtrldao.setGoods();
		 cacheCtrldao.setUser();
		 cacheCtrldao.setDepartments();
		 cacheCtrldao.setPersons();
		 cacheCtrldao.setProviderCustomer();
		 cacheCtrldao.setStandardProcess();
		 cacheCtrldao.setWareHouses();
		 cacheCtrldao.setClassify();
		 cacheCtrldao.setAaGroup();
		 cacheCtrldao.setUnit();
		 cacheCtrldao.setMESEquipment();
		 cacheCtrldao.setMould();
		 cacheCtrldao.setMESWorkCenter();
		 cacheCtrldao.setGoodsAllocation();

		System.out.println("-----------结束缓存信息-----------------");
	}

	@Override
	public void run() {
		this.Task();
	}
}
