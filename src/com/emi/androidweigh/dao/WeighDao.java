package com.emi.androidweigh.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.common.dao.BaseDao;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaProviderCustomer;

public class WeighDao extends BaseDao {

	
	/**
	 * @category 待称重物料
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getToWeighList(String condition){
		String sql="select billDate dDate,billCode ccode,supplierUid,autoidForSynchro id from WM_ProcureArrival "
				+ " pa where "+condition;
		return this.queryForList(sql);
	}
	
	
	/**
	 * @category 待称重物料详情
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getToWeighDetail(String condition){
		String sql=" select autoidForSynchro autoid,goodsUid, pac.cfree1,aauserdefine.code cfree1Code,number iquantity,batch from WM_ProcureArrival_C pac "
				+ " left join AA_Goods gs on pac.goodsUid=gs.gid "
				+ " left join AA_UserDefine aauserdefine on aauserdefine.value = pac.cfree1 where   "+condition;
		return this.queryForList(sql);
	}
	
	/**
	 * @category 提交称重
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public int[] subWeigh(final JSONArray jsonArray){
		String sql="update WM_ProcureArrival_C set weighedNum=isnull(weighedNum,0)+? where autoidForSynchro=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				JSONObject jobj=(JSONObject)jsonArray.get(index);
				
				ps.setDouble(1, jobj.getDouble("lastWeight"));
				ps.setString(2, jobj.getString("autoid"));
			}
			
			@Override
			public int getBatchSize() {
				return jsonArray.size();
			}
		});
	}
	
	/**
	 * @category 提交称重,反填用友
	 *2016 2016年5月23日上午8:38:18
	 *List<Map>
	 *宋银海
	 */
	public int[] subWeighYonYou(final JSONArray jsonArray){
		String sql="update "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs set cDefine23=isnull(cDefine23,0)+? where Autoid=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				JSONObject jobj=(JSONObject)jsonArray.get(index);
				
				ps.setDouble(1, jobj.getDouble("lastWeight"));
				ps.setString(2, jobj.getString("autoid"));
			}
			
			@Override
			public int getBatchSize() {
				return jsonArray.size();
			}
		});
	}
	
	
	/**
	 * @category 工序领料称重列表
	 *2016 2016年6月27日上午10:13:30
	 *List<Map>
	 *宋银海
	 */
	public List<Map> getProcessTaskList(String condition){
		String sql=" select t.billCode,t.billgid,t.completetime,t.distributetime,t.gid,bt.billCode taskTypeCode,t.taskname from WM_Task t "
				+ " left join WM_BillType bt on t.taskTypeUid=bt.gid where "+condition;
		return this.queryForList(sql);
	}
	
	
}
