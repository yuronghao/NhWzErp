package com.emi.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;

import com.emi.common.action.YmUserTest;
import com.emi.common.bean.core.TreeNode;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.wms.bean.AAPriorAttribute;
import com.emi.wms.bean.AaBarcodeRule;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaGroup;
import com.emi.wms.bean.AaPerson;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaUserDefine;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.Equipment;
import com.emi.wms.bean.MesAaStation;
import com.emi.wms.bean.MesAaWorkcenter;
import com.emi.wms.bean.MesWmStandardprocess;
import com.emi.wms.bean.Mould;
import com.emi.wms.bean.WmBillType;
import com.emi.wms.bean.YmRdStyle;
import com.emi.wms.bean.YmUser;

@SuppressWarnings({"rawtypes","unchecked"})
public class EmiPluginDao extends BaseDao{
	public void test() {
		YmUserTest u = new YmUserTest();
		u.setBeginTimes(null);
		this.emiInsert(u);
	}

	String pk = "pk";
	/**
	 * @category 上张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @param condition 
	 * @return
	 */
	public String getNextPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+">(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" asc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}

	/**
	 * @category 下张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @return
	 */
	public String getPrePageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+"<(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" desc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}

	/**
	 * @category 首张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @return
	 */
	public String getLastPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+">(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" desc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}

	/**
	 * @category 末张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @return
	 */
	public String getFirstPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+"<(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" asc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}
	
	/*
	 * 将条件json转成sql
	 
	private String formatCondition(JSONObject condition_json) {
		String conditionSql = "";
		Iterator it = condition_json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
            String value = condition_json.getString(key);
            conditionSql += " and "+key+"='"+value+"'";
		}
		return conditionSql;
	}*/

	/**
	 * @category 调用本地存储过程 返回编码
	 *2016 2016年4月11日下午3:30:20
	 *String
	 *宋银海
	 */
	public String getBillId(final String billType, final String preFix) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection arg0) throws SQLException,DataAccessException {

				String psql = "{call YMProc_GetVouchGlide(?,?,?)}";
				String currentId=null;

				CallableStatement call = arg0.prepareCall(psql);

				call.setString(1, billType);
				call.setString(2, preFix);
				call.registerOutParameter(3, Types.NVARCHAR);
				call.execute();

	            currentId=call.getString(3);

	            call.close();
				return currentId;
			}
		}).toString();
	}
	
	
	/**
	 * @category 根据货位gid 查询仓库
	 *2016 2016年4月11日下午3:50:19
	 *AaWarehouse
	 *宋银海
	 */
	public AaWarehouse getAaWarehouse(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaWarehouse.class, "wh")+" from AA_WareHouse wh "
				+ " left join AA_GoodsAllocation ga on wh.gid=ga.whUid where 1=1 "+condition;
		return (AaWarehouse) this.emiQuery(sql, AaWarehouse.class);
	}

	/**
	 * @category 物料树
	 * 2016年4月14日 上午9:08:31 
	 * @author zhuxiaochen
	 * @param sobId
	 * @param orgId
	 * @return
	 */
	public List<TreeNode> getGoodsTree(String sobId, String orgId) {
		String sql = "select "+CommonUtil.colsFromBean(Classify.class)+" from classify where (isDelete=0 or isDelete is null) and styleGid='03' ";
		if(CommonUtil.notNullString(sobId)){
			sql += " and sobid='"+sobId+"' ";
		}
		if(CommonUtil.notNullString(orgId)){
			sql += " and orgid='"+orgId+"' ";
		}
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){
			@Override
			public TreeNode mapRow(ResultSet rs, int rowNum) throws SQLException {
				TreeNode node = new TreeNode();
				node.setId(CommonUtil.Obj2String(rs.getObject("gid")));
				node.setPid(CommonUtil.Obj2String(rs.getObject("parentid")));
				node.setName(CommonUtil.Obj2String(rs.getObject("classificationName")));
				return node;
			}
			
		});
	}

	/**
	 * @category 物料列表
	 * 2016年4月14日 上午9:33:47 
	 * @author zhuxiaochen
	 * @param classGid
	 * @param sobId
	 * @param orgId
	 * @param showTree 
	 * @return
	 */
	public PageBean getGoodsList(String classGid, String sobId, String orgId,int pageIndex,int pageSize, String showTree,String keyWord) {
		Map match = new HashMap();
		match.put("unitName", "unitName");
		String sql = "select "+CommonUtil.colsFromBean(AaGoods.class,"gs")+",u.unitName FROM AA_Goods gs left join unit u on gs.goodsunit=u.gid where gs.goodsunit=u.gid and orgGid='"+orgId+"' and sobGid='"+sobId+"'";
		if(!"0".equals(showTree)){//[0 隐藏树，查询所有]
			if(CommonUtil.notNullString(classGid)){
				sql += " and goodsSortUid='"+classGid+"' ";
			}
			
		}
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (gs.goodscode like '%"+keyWord.trim()+"%' or gs.goodsname like '%"+keyWord.trim()+"%' or gs.goodsstandard like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaGoods.class,match, "goodscode");
		
	}
	
	/**
	 * @category 工序列表
	 * 2016年4月21日 上午9:30:27 
	 * @author zhuxiaochen
	 * @param keyWord
	 * @param sobId
	 * @param orgId
	 * @param pageIndex
	 * @param pageSize
	 * @param showTree
	 * @return
	 */
	public PageBean getProcessList(String keyWord, String sobId, String orgId,
			int pageIndex, int pageSize, String showTree) {
		String sql = "select "+CommonUtil.colsFromBean(MesWmStandardprocess.class,"sp")+" FROM MES_WM_StandardProcess sp where (isDelete=0 or isDelete is null) and orgGid='"+orgId+"' and sobGid='"+sobId+"'";
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (opcode like '%"+keyWord.trim()+"%' or opname like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, MesWmStandardprocess.class, "opcode");
	}

	/**
	 * @category 工作组
	 * 2016年4月22日 上午9:48:41 
	 * @author zhuxiaochen
	 * @param keyWord
	 * @param sobId
	 * @param orgId
	 * @param pageIndex
	 * @param pageSize
	 * @param showTree
	 * @return
	 */
	public PageBean getWorkCenterList(String keyWord, String sobId,
			String orgId, int pageIndex, int pageSize, String showTree) {
		Map match = new HashMap();
		match.put("depCode", "depCode");
		match.put("depName", "depName");
		String sql = "select "+CommonUtil.colsFromBean(MesAaWorkcenter.class,"wc")+",d.depCode depCode,d.depName depName FROM MES_AA_WorkCenter wc left join AA_Department d on wc.depUid=d.gid where wc.orgGid='"+orgId+"' and wc.sobGid='"+sobId+"' and (wc.isDelete is null or wc.isDelete=0)";
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (wc.wccode like '%"+keyWord.trim()+"%' or wc.wcname like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, MesAaWorkcenter.class,match, "wccode");
	}
	/**
	 * 
	 * @category
	 * 2016年7月15日 下午4:47:26
	 * @author 杨峥铖
	 * @param keyWord
	 * @param sobId
	 * @param orgId
	 * @param pageIndex
	 * @param pageSize
	 * @param showTree
	 * @return
	 */
	public PageBean getProviderCustomerList(String keyWord, String sobId,
			String orgId, int pageIndex, int pageSize, String showTree) {
		Map match = new HashMap();
		String sql = "select "+CommonUtil.colsFromBean(AaProviderCustomer.class,"pc")+" FROM AA_Provider_Customer pc where pc.orgId='"+orgId+"' and pc.sobId='"+sobId+"' and (pc.isDel is null or pc.isDel=0)";
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaProviderCustomer.class,match, "");
	}
	
	/**
	 * 
	 * @category
	 * 2016年7月15日 下午4:47:26
	 * @author 杨峥铖
	 * @param keyWord
	 * @param sobId
	 * @param orgId
	 * @param pageIndex
	 * @param pageSize
	 * @param showTree
	 * @return
	 */
	public PageBean getPriorAttributeList(String keyWord, String sobId,
			String orgId, int pageIndex, int pageSize, String showTree) {
		Map match = new HashMap();
		String sql = "select "+CommonUtil.colsFromBean(AAPriorAttribute.class,"prior")+" FROM AA_PriorAttribute prior where 1=1 and prior.type='c' ";
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AAPriorAttribute.class,match, "");
	}
	
	public PageBean getWareHouseList(String keyWord, String sobId,
			String orgId, int pageIndex, int pageSize, String showTree) {
		Map match = new HashMap();
		String sql = "select "+CommonUtil.colsFromBean(AaWarehouse.class,"wh")+" FROM AA_WareHouse wh where wh.orgGid='"+orgId+"' and wh.sobGid='"+sobId+"'";
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (wh.whcode like '%"+keyWord.trim()+"%' or wh.whname like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaWarehouse.class,match, "whcode");
	}
	
	/**
	 * @category 查询单个物品
	 *2016 2016年4月14日下午3:11:46
	 *AaGoods
	 *宋银海
	 */
	public AaGoods getAaGoods(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaGoods.class)+" from AA_Goods where "+condition;
		return (AaGoods) this.emiQuery(sql, AaGoods.class);
	}

	
	/**
	 * @category 查询单个货位
	 *2016 2016年4月14日下午4:17:28
	 *AaGoodsallocation
	 *宋银海
	 */
	public AaGoodsallocation getAaGoodsallocation(String condition){
		Map match = new HashMap();
		match.put("whcode", "AaGoodsallocation.whcode");
		String sql="select "+CommonUtil.colsFromBean(AaGoodsallocation.class,"aagoodsallocation")+",(select whcode from AA_WareHouse aawarehouse where aawarehouse.gid = aagoodsallocation.whUid) whcode from AA_GoodsAllocation aagoodsallocation where "+condition;
		return (AaGoodsallocation) this.emiQuery(sql, AaGoodsallocation.class,match);
	}
	
	/**
	 * @category 获得条码规则
	 *2016 2016年5月24日下午2:15:45
	 *List<AaBarcodeRule>
	 *宋银海
	 */
	public List<AaBarcodeRule> getAaBarcodeRule(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaBarcodeRule.class)+" from AA_BarcodeRule where "+condition;
		return this.emiQueryList(sql, AaBarcodeRule.class);
	}
	
	
	/**
	 * @category 获得用户自定义列表
	 *2016 2016年5月24日下午2:15:45
	 *List<AaBarcodeRule>
	 *宋银海
	 */
	public List<AaUserDefine> getAaUserDefineList(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaUserDefine.class)+" from AA_UserDefine where "+condition;
		return this.emiQueryList(sql, AaUserDefine.class);
	}
	
	/**
	 * @category 获得用户自定义
	 *2016 2016年5月24日下午2:15:45
	 *List<AaBarcodeRule>
	 *宋银海
	 */
	public AaUserDefine getAaUserDefine(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaUserDefine.class)+" from AA_UserDefine where "+condition;
		return (AaUserDefine)this.emiQuery(sql, AaUserDefine.class);
	}
	
	/**
	 *@category 查询单个人员
	 *2016 2016年4月15日上午9:08:10
	 *AaPerson
	 *宋银海
	 */
	public AaPerson getAaPerson(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaPerson.class)+" from AA_Person where "+condition;
		return (AaPerson) this.emiQuery(sql, AaPerson.class);
	}
	
	
	/**
	 * @category 查询工作中心
	 *2016 2016年4月15日上午10:51:14
	 *MesAaWorkcenter
	 *宋银海
	 */
	public MesAaWorkcenter getMesAaWorkcenter(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesAaWorkcenter.class)+" from MES_AA_WorkCenter where "+condition;
		return (MesAaWorkcenter) this.emiQuery(sql, MesAaWorkcenter.class);
	}
	
	
	/**
	 * @category 查询工作组
	 *2016 2016年4月15日上午10:51:14
	 *MesAaWorkcenter
	 *宋银海
	 */
	public AaGroup getMesAaGroup(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaGroup.class)+" from AA_Group where "+condition;
		return (AaGroup) this.emiQuery(sql, AaGroup.class);
	}
	
	/**
	 *@category 查询单个工位
	 *2016 2016年4月15日上午9:29:11
	 *AaPerson
	 *宋银海
	 */
	public MesAaStation getMesAaStation(String condition){
		String sql="select "+CommonUtil.colsFromBean(MesAaStation.class)+" from MES_AA_Station where "+condition;
		return (MesAaStation) this.emiQuery(sql, MesAaStation.class);
	}
	
	
	/**
	 *@category 查询设备即工位
	 *2016 2016年4月15日上午9:29:11
	 *AaPerson
	 *宋银海
	 */
	public Equipment getEquipment(String condition){
		String sql="select "+CommonUtil.colsFromBean(Equipment.class)+" from equipment where "+condition;
		return (Equipment) this.emiQuery(sql, Equipment.class);
	}
	
	/**
	 * @category 获得类型表
	 *2016 2016年4月19日下午2:31:51
	 *List<WmBillType>
	 *宋银海
	 */
	public List<WmBillType> getWmBillType(String condition){
		String sql="select "+CommonUtil.colsFromBean(WmBillType.class)+" from WM_BillType where "+condition;
		return this.emiQueryList(sql, WmBillType.class);
	}

	

	/**
	 * @category 故意回滚
	 *2016 2016年4月22日下午3:08:26
	 *int
	 *宋银海
	 */
	public int rollBackWhenError(){
		String sql="update YM_User set a=1 where gid='1' ";
		return this.update(sql);
	}

	
	/**
	 * @category 获得参数
	 *2016 2016年4月23日上午9:28:33
	 *Map
	 *宋银海
	 */
	public Map getymsetting(String condition){
		String sql ="select paramValue from YM_Settings where "+condition;
		return (Map)this.queryForMap(sql);
	}
	
	/**
	 * @category 根据单据类型获取类别信息
	 * 宋银海
	 * @return
	 */
	public Map getDefaultRdstyleByBillType(String billTypeCode){
		String sql="select rd.gid,rd.crdCode,rd.crdName from wm_billType bt left join ym_rdstyle rd on bt.defaultRdstyleGid=rd.gid where bt.billcode='"+billTypeCode+"'";
		return (Map)this.queryForMap(sql);
	}

	/**
	 * @category 人员的部门树
	 * 2016年5月4日 下午4:34:17 
	 * @author zhuxiaochen
	 * @param sobId
	 * @param orgId
	 * @return
	 */
	public List<TreeNode> getPersonTree(String sobId, String orgId) {
		String sql = "select "+CommonUtil.colsFromBean(AaDepartment.class)+" from AA_Department where (isDel=0 or isDel is null) ";
		if(CommonUtil.notNullString(sobId)){
			sql += " and sobgId='"+sobId+"' ";
		}
		if(CommonUtil.notNullString(orgId)){
			sql += " and orggId='"+orgId+"' ";
		}
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){
			@Override
			public TreeNode mapRow(ResultSet rs, int rowNum) throws SQLException {
				TreeNode node = new TreeNode();
				node.setId(CommonUtil.Obj2String(rs.getObject("gid")));
				node.setPid(CommonUtil.Obj2String(rs.getObject("depParentUid")));
				node.setName(CommonUtil.Obj2String(rs.getObject("depName")));
				return node;
			}
			
		});
	}

	/**
	 * @category 人员列表
	 * 2016年5月4日 下午4:34:33 
	 * @author zhuxiaochen
	 * @param deptId
	 * @param sobId
	 * @param orgId
	 * @param pageIndex
	 * @param pageSize
	 * @param showTree
	 * @param keyWord
	 * @return
	 */
	public PageBean getPersonList(String deptId, String sobId, String orgId,
			int pageIndex, int pageSize, String showTree, String keyWord) {
		String sql = "select "+CommonUtil.colsFromBean(AaPerson.class)+" FROM AA_Person where orgGid='"+orgId+"' and sobGid='"+sobId+"'";
		if(!"0".equals(showTree)){//[不隐藏树，按部门查询]
			if(CommonUtil.notNullString(deptId)){
				sql += " and depGid='"+deptId+"' ";
			}
		}
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (percode like '%"+keyWord.trim()+"%' or pername like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaPerson.class, "perCode");
	}
	
	/**
	 * @category 用户列表
	 *2016 2016年12月9日下午5:13:33
	 *PageBean
	 *宋银海
	 */
	public PageBean getUserList(String deptId, String sobId, String orgId,
			int pageIndex, int pageSize, String showTree, String keyWord) {
		String sql = "select "+CommonUtil.colsFromBean(YmUser.class)+" FROM YM_User where orgGid='"+orgId+"' and sobGid='"+sobId+"'";
//		if(!"0".equals(showTree)){//[不隐藏树，按部门查询]
//			if(CommonUtil.notNullString(deptId)){
//				sql += " and depGid='"+deptId+"' ";
//			}
//		}
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (userCode like '%"+keyWord.trim()+"%' or userName like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, YmUser.class, "userCode");
	}
	
	

	public PageBean getGroupList(String keyWord, String sobId, String orgId,
			int pageIndex, int pageSize, String showTree) {
		String sql = "select "+CommonUtil.colsFromBean(AaGroup.class)+" FROM AA_Group where orgGid='"+orgId+"' and sobGid='"+sobId+"'";
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (code like '%"+keyWord.trim()+"%' or groupname like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, AaGroup.class, "code");
	}

	/**
	 * @category 设备
	 * 2016年5月5日 下午3:55:18 
	 * @author zhuxiaochen
	 * @param keyWord
	 * @param sobId
	 * @param orgId
	 * @param pageIndex
	 * @param pageSize
	 * @param showTree
	 * @return
	 */
	public PageBean getEquipmentList(String keyWord, String sobId,
			String orgId, int pageIndex, int pageSize, String showTree) {
		String sql = "select "+CommonUtil.colsFromBean(Equipment.class)+" FROM equipment where orgGid='"+orgId+"' and sobGid='"+sobId+"'";
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (equipmentcode like '%"+keyWord.trim()+"%' or equipmentname like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, Equipment.class, "equipmentcode");
	}
	
	/**
	 * @category 模具
	 * 2016年5月5日 下午3:55:18 
	 * @author zhuxiaochen
	 * @param keyWord
	 * @param sobId
	 * @param orgId
	 * @param pageIndex
	 * @param pageSize
	 * @param showTree
	 * @return
	 */
	public PageBean getMouldList(String keyWord, String sobId,String orgId, int pageIndex, int pageSize, String showTree) {
		String sql = "select "+CommonUtil.colsFromBean(Mould.class)+" FROM mould where orgGid='"+orgId+"' and sobGid='"+sobId+"'";
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (mouldcode like '%"+keyWord.trim()+"%' or mouldname like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, Mould.class, "mouldcode");
	}

	/**
	 * @category 得到部门
	 * 2016年5月25日 下午2:21:16 
	 * @author zhuxiaochen
	 * @param sobId
	 * @param orgId
	 * @return
	 */
	public List<TreeNode> getDepartmentTree(String sobId, String orgId) {
		return getPersonTree(sobId, orgId);
	}
	
	/**
	 * @category通过barcode 和工艺路线gid查询模具信息
	 * @param barcode
	 * @return
	 */
	public Mould getMouldByBarcode(String barcode,String produceProcessRoutecGid){
		String sql="SELECT m.* FROM mould m WITH (NoLock) LEFT JOIN MES_WM_ProduceProcessRouteCMould mwpm WITH (NoLock) ON mwpm.mouldGid=m.gid WHERE m.barcode='"+barcode.trim()+"' AND mwpm.routeCGid='"+produceProcessRoutecGid+"'";
		return (Mould)this.emiQuery(sql, Mould.class);
	}
	
	/**
	 * @category 根据订单号获取订单产品信息
	 *2016 2016年7月11日下午2:52:13
	 *PageBean
	 *宋银海
	 */
	public PageBean getProduceProductList(String condition,int pageIndex, int pageSize){
		String sql="SELECT po.billCode,po.billDate,poc.pk pocpk,poc.goodsUid,poc.startDate,poc.endDate,poc.number,poc.gid pocgid "+ 
					"from WM_ProduceOrder po LEFT JOIN WM_ProduceOrder_C poc on po.gid=poc.produceOrderUid where "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "pocpk");
	}
	
	/**
	 * @category 获得库存信息
	 * @param condition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageBean getAllocationStock(String condition,int pageIndex, int pageSize){
		String sql="select pk,gid,goodsUid,goodsCode,batch,number,assistNum,whCode,goodsAllocationUid,goodsAllocationCode,cfree1,cfree2 from WM_AllocationStock where "+condition;
		return this.emiQueryList(sql, pageIndex, pageSize, "goodsCode");
	}
	
	
	/**
	 * 
	 * @param uuid
	 * @return
	 * 获得uuid信息  防止重复提交
	 * 2017年4月10日
	 * Map
	 */
	public Map getUuidInfor(String uuid){
		String sql="select id,uuid from YM_UuidInfor where uuid='"+uuid+"'";
		return this.queryForMap(sql);
	}
	
	
	public int insertUuidInfor(String uuid){
		String sql="insert into YM_UuidInfor(uuid) values('"+uuid+"')";
		return this.update(sql);
	}
	
	
	/**
	 * 模具类别树
	 * @param sobId
	 * @param orgId
	 * @return
	 * 2017年5月22日
	 * List<TreeNode>
	 */
	public List<TreeNode> getMouldTree(String sobId, String orgId) {
		String sql = "select "+CommonUtil.colsFromBean(Classify.class)+" from classify where (isDelete=0 or isDelete is null) and styleGid='04' ";
		if(CommonUtil.notNullString(sobId)){
			sql += " and sobid='"+sobId+"' ";
		}
		if(CommonUtil.notNullString(orgId)){
			sql += " and orgid='"+orgId+"' ";
		}
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){
			@Override
			public TreeNode mapRow(ResultSet rs, int rowNum) throws SQLException {
				TreeNode node = new TreeNode();
				node.setId(CommonUtil.Obj2String(rs.getObject("gid")));
				node.setPid(CommonUtil.Obj2String(rs.getObject("parentid")));
				node.setName(CommonUtil.Obj2String(rs.getObject("classificationName")));
				return node;
			}
			
		});
	}
	
	
	
	public PageBean getMouldList(String classGid, String sobId, String orgId,int pageIndex,int pageSize, String showTree,String keyWord) {
		
		String sql = "select "+CommonUtil.colsFromBean(Mould.class)+" FROM mould where orgGid='"+orgId+"' and sobGid='"+sobId+"'";
		if(!"0".equals(showTree)){//[0 隐藏树，查询所有]
			if(CommonUtil.notNullString(classGid)){
				sql += " and mouldStyle='"+classGid+"' ";
			}
		}
		if(CommonUtil.notNullString(keyWord)){
			sql += " and (mouldcode like '%"+keyWord.trim()+"%' or mouldname like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, Mould.class, "mouldcode");
		
	}
	
	/**
	 * 
	* @Title: 出入库类别
	* @author zxl 2017年6月9日 上午10:28:36
	* @return List<TreeNode>
	 */
	public List<TreeNode> getRdStyleTree(String sobId, String orgId) {
		String sql = "select "+CommonUtil.colsFromBean(YmRdStyle.class)+" from YM_RdStyle where brdEnd=1  ";
		if(CommonUtil.notNullString(sobId)){
			sql += " and sobgId='"+sobId+"' ";
		}
		if(CommonUtil.notNullString(orgId)){
			sql += " and orggId='"+orgId+"' ";
		}
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){
			@Override
			public TreeNode mapRow(ResultSet rs, int rowNum) throws SQLException {
				TreeNode node = new TreeNode();
				node.setId(CommonUtil.Obj2String(rs.getObject("gid")));
				//node.setPid(CommonUtil.Obj2String(rs.getObject("depParentUid")));
				node.setName(CommonUtil.Obj2String(rs.getObject("crdName")));
				return node;
			}
			
		});
	}

	/**
	 * 
	* @Title: 出入库类别列表 
	* @author zxl 2017年6月9日 上午10:36:12
	* @return PageBean
	 */
	public PageBean getRdStyleList(int pageIndex, int pageSize, String showTree, String keyWord) {
		String sql = "select "+CommonUtil.colsFromBean(YmRdStyle.class)+" FROM YM_RdStyle RdStyle where brdEnd=1";
		if(CommonUtil.notNullString(keyWord)){
			sql += " where (crdName like '%"+keyWord.trim()+"%')";
		}
		return (PageBean) this.emiQueryList(sql, pageIndex, pageSize, YmRdStyle.class, "crdCode");
	}
}
