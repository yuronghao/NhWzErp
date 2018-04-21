package com.emi.wms.servicedata.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.emi.android.bean.WmsGoods;
import com.emi.android.bean.WmsGoodsCfree;
import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.CurrentStock;
import com.emi.wms.bean.MesWmProcessRoutecGoods;
import com.emi.wms.bean.MesWmProduceProcessroutecGoods;
import com.emi.wms.bean.OM_MOMaterials;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.WmBatch;
import com.emi.wms.bean.WmMaterialout;
import com.emi.wms.bean.WmMaterialoutC;
import com.emi.wms.bean.WmOtherwarehouse;
import com.emi.wms.bean.WmPowarehouse;
import com.emi.wms.bean.WmPowarehouseC;
import com.emi.wms.bean.WmProductionwarehouse;
import com.emi.wms.bean.WmProductionwarehouseC;
import com.emi.wms.bean.WmSaleout;
import com.emi.wms.bean.WmSaleoutC;
import com.emi.wms.bean.YmRdStyle;
import com.emi.wms.bean.wmCall;
import com.emi.wms.bean.wmCallC;


public class PrintDaoEmi extends BaseDao {

	public List<Map> getAllTemplate(){
		
		String sql="select * from WM_PrintTemplate ";
		
		return this.queryForList(sql);
		
	}
	
	
	public Map getGoodsMapTplus(String gid){
		String sql="select shorthand from "+Config.BUSINESSDATABASE+"aa_inventory where id='"+gid+"'";
		return this.queryForMap(sql);
	}
	
}
