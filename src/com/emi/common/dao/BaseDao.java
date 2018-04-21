package com.emi.common.dao;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.emi.cache.util.MemCachedCtrl;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
import com.emi.sys.core.bean.PageBean;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class BaseDao extends JdbcDaoSupport {
	
	
	static MemCachedCtrl memCache = null;
	public static final String DB_TYPE_SQLSERVER = "sqlServer";
	public static final String DB_TYPE_ORACLE = "oracle";
	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_POSTGRE = "postgre";
	
	public static final String CUR_DB_TYPE = DB_TYPE_SQLSERVER;//目前使用的数据库

	public static MemCachedCtrl getMemCache() {
		return memCache;
	}

	private boolean initConfig() {
		// TODO 初始化配置文件
		return true;
	}

	public void init() {
		if (initConfig()) {
			memCache = MemCachedCtrl.getInstance();
			memCache.init();
			
		}
	}

	public List queryForList(String sql) {
		System.out.println(sql);
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List queryForList(String sql, int pageIndex, int pageSize) {
		sql = sizeSql(sql, pageIndex, pageSize);
		System.out.println(sql);
		return this.getJdbcTemplate().queryForList(sql);
	}

	/**
	 * 
	 * 2014年6月17日 上午9:26:13 朱晓陈
	 * 
	 * @param sql
	 * @param pageIndex
	 * @param pageSize
	 * @param sortSql
	 *            填入特殊排序sql，如：'cName asc,dTime desc'。如有as取的别名，用别名
	 * @return
	 */
	public List queryForList(String sql, int pageIndex, int pageSize,
			String sortSql) {
		sql = sizeSql(sql, pageIndex, pageSize, sortSql);
		System.out.println(sql);
		return this.getJdbcTemplate().queryForList(sql);
	}

	public List queryForList(String sql, Object[] args, int[] argTypes,
			int pageIndex, int pageSize) {
		sql = sizeSql(sql, pageIndex, pageSize);
		System.out.println(sql);
		return this.getJdbcTemplate().queryForList(sql, args, argTypes);
	}

	public Map queryForMap(String sql) {
		System.out.println(sql);
		List list = this.getJdbcTemplate().queryForList(sql);
		if (list.size() > 0) {
			return (Map) list.get(0);
		} else {
			return null;
		}
	}

	public void execute(String sql) {
		System.out.println(sql);
		this.getJdbcTemplate().execute(sql);
	}

	public int queryForInt(String sql) {
		System.out.println(sql);
		return this.getJdbcTemplate().queryForInt(sql);
	}

	public long queryForLong(String sql) {
		return this.getJdbcTemplate().queryForLong(sql);
	}

	public int update(String sql) {
		System.out.println(sql);
		return this.getJdbcTemplate().update(sql);
	}

	public int update(String sql, Object[] args, int[] argTypes) {
		System.out.println(sql);
		return this.getJdbcTemplate().update(sql, args, argTypes);
	}

	public int[] bathUpdate(String[] sqls) {
		for (String sql : sqls) {
			System.out.println(sql);
		}
		return this.getJdbcTemplate().batchUpdate(sqls);
	}

	public boolean batchUpdate(String[] sqls) {
		boolean success = true;
		int[] ia = bathUpdate(sqls);
		for (int i : ia) {
			if (i < 1) {
				success = false;
				break;
			}
		}
		return success;
	}

	public int[] batchUpdate(String sql, BatchPreparedStatementSetter pss) {
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, pss);
	}

	/**
	 * 加上分页sql 2014年5月15日 下午5:07:40 朱晓陈
	 * 
	 * @param sql
	 *            注意：使用sqlserver时select的字段必须包含pk
	 * @param index
	 *            页码 (从1开始)
	 * @param pagesize
	 *            每页条数
	 * @return String
	 */
	private String sizeSql(String sql, int index, int pagesize) {
		return sizeSql(sql, index, pagesize, null);
	}

	/**
	 * 加上分页sql 2014年5月15日 下午5:07:40 朱晓陈
	 * 
	 * @param sql
	 *            注意：使用sqlserver时select的字段必须包含id
	 * @param index
	 *            页码 (从1开始)
	 * @param pagesize
	 *            每页条数
	 * @param sortSql
	 *            排序sql
	 * @return String
	 */
	private String sizeSql(String sql, int index, int pagesize, String sortSql) {
		int start = (index - 1) * pagesize + 1;
		int end = index * pagesize;
		boolean flag = false;
		StringBuffer size = new StringBuffer();
		if (start != -1 && end != -1) {
			if (start >= 0 && end > 0 && end - start >= 0) {
				flag = true;
				if (DB_TYPE_SQLSERVER.equals(CUR_DB_TYPE)) {
					// sqlserver 分页
					size.append(" select *  from ( select *, ROW_NUMBER() OVER(order by ");
					if (CommonUtil.isNullString(sortSql)) {
						size.append("pk");
					} else {
						size.append(sortSql);
					}
					size.append(") as RowNo from (");
					size.append(sql)
							.append(") main_sql ) as final_sql where RowNo >= ")
							.append(start);
					size.append(" and RowNo <= ").append(end);
					;
				}
				if (DB_TYPE_ORACLE.equals(CUR_DB_TYPE)) {
					// oracle 分页
					size.append(" select * from ( ");
					size.append("  select main_sql.*,RowNum rn from ( ");
					size.append(sql).append(") main_sql where RowNum <= ")
							.append(end).append(" ) ");
					size.append(" where rn >= ").append(start)
							.append(" ORDER BY rn");
					;
				}
				if (DB_TYPE_MYSQL.equals(CUR_DB_TYPE) || DB_TYPE_POSTGRE.equals(CUR_DB_TYPE)) {
					// Mysql\PostgreSql 分页
					size.append(" select * from ( ");
					size.append(sql).append(") main_sql ");
					if (!CommonUtil.isNullString(sortSql)) {
						size.append(" ORDER BY ");
					}
					size.append(sortSql);
					size.append(" limit ").append(pagesize).append(" offset ")
							.append(start - 1);
				}
			}
		}

		if (flag) {
			return size.toString();
		} else {
			if (CommonUtil.isNullString(sortSql)) {
				return sql;
			} else {
				// 有设置排序则加入排序sql
				size.append(" select * from ( ");
				size.append(sql).append(") main_sql ORDER BY ");
				size.append(sortSql);
				return size.toString();
			}
		}
	}

	/***************************************** 以下是EMI数据库操作框架 **************************************************/
	/*private String getFromSql(String sql) {
		String fromSql = "";
		int fromIndex = sql.toUpperCase().indexOf(" FROM ");
		fromSql = sql.substring(fromIndex);
		int khIndex = sql.indexOf("(");
		if (khIndex >= 0 && khIndex < fromIndex && fromIndex >= 0) {
			fromSql = getFromSql(fromSql.substring(5));
		}
		return fromSql;
	}*/
	
	private String getFromSql(String sql){
		String fromSql = "";
		int fromIndex = sql.toUpperCase().indexOf(" FROM ");
		fromSql = sql.substring(fromIndex);
		int khIndex = sql.toUpperCase().indexOf("(SELECT");
		if(khIndex >=0 && khIndex < fromIndex && fromIndex>=0){
			fromSql = getFromSql(fromSql.substring(5));
		}
		return fromSql;
	}

	// private PageBean doPageBean(String sql, int pageIndex, int pageSize, List
	// list){
	// String fromsql = getFromSql(sql);//截掉前面的查询字段
	// String count_sql = "select count(1) "+fromsql;
	// int count = queryForInt(count_sql);
	// PageBean pb = new PageBean();
	// pb.setPageIndex(pageIndex);
	// pb.setPageSize(pageSize);
	// pb.setTotalCount(count);
	// pb.setPageCount((int)Math.ceil((double)count / (double)pageSize));
	// pb.setList(list);
	// return pb;
	// }

	private PageBean doPageBean(String sql, int pageIndex, int pageSize,
			List list) {
		String fromsql = getFromSql(sql);// 截掉前面的查询字段
		int count = 0;
		if (pageIndex <= 0 || pageSize <= 0) {
			count = list.size();
		} else {
			String count_sql = "";
			if (sql.toUpperCase().indexOf(" UNION ALL ") > 0 || sql.toUpperCase().indexOf(" GROUP BY ") > 0) {
				count_sql = "select count(1) cnt from (" + sql + ") as t";
			}else if(sql.toUpperCase().trim().indexOf("SELECT DISTINCT")>=0){
				count_sql = "select count(1) cnt from (" + sql + ") as t";
			} else {
				count_sql = "select count(1) cnt " + fromsql;
			}
			count = queryForInt(count_sql);
		}
		PageBean pb = new PageBean();
		pb.setPageIndex(pageIndex);
		pb.setPageSize(pageSize);
		pb.setTotalCount(count);
		pb.setPageCount((int) Math.ceil((double) count / (double) pageSize));
		pb.setList(list);
		return pb;
	}

	/**
	 * @param sql
	 * @param classes
	 *            查询结果实体类数组
	 * @return List map的key为实体名，value为实体内容。<br>
	 *         结构：[{{"User",user},{"Department":department}},...]
	 */
	public List emiQueryList(String sql, final Class[] classes) {
		return emiQueryList(sql, classes, null);
	}

	/**
	 * @param sql
	 * @param clazz
	 *            查询结果实体类
	 * @return List
	 */
	public List emiQueryList(String sql, final Class<?> clazz) {
		return emiQueryList(sql, new Class[] { clazz }, null);
	}

	/**
	 * @param sql
	 * @param clazz
	 *            查询结果实体类
	 * @param match
	 *            自定义匹配值 例：map.put("userName","User.name")
	 * @return List
	 */
	public List emiQueryList(String sql, final Class<?> clazz,
			final Map<String, String> match) {
		return emiQueryList(sql, new Class[] { clazz }, match);
	}

	public List emiQueryList(String sql, final Class<?> clazz,
			final Map<String, String> match, final boolean cache, String att) {
		return emiQueryList(sql, new Class[] { clazz }, match, cache, att);
	}

	public Object emiQuery(String sql, final Class[] classes) {
		return emiQuery(sql, classes, null);
	}

	public Object emiQuery(String sql, final Class<?> clazz) {
		return emiQuery(sql, new Class[] { clazz }, null);
	}

	public Object emiQuery(String sql, final Class<?> clazz,
			final Map<String, String> match) {
		return emiQuery(sql, new Class[] { clazz }, match);
	}

	/*
	 * 带分页
	 */
	public PageBean emiQueryList(String sql, int pageIndex, int pageSize,
			final Class[] classes, final Map<String, String> match,
			String sortSql) {
		String size_sql = sizeSql(sql, pageIndex, pageSize, sortSql);
		List list = emiQueryList(size_sql, classes, match);
		return doPageBean(sql, pageIndex, pageSize, list);
	}

	public PageBean emiQueryList(String sql, int pageIndex, int pageSize,
			final Class<?> clazz, final Map<String, String> match,
			String sortSql) {
		return emiQueryList(sql, pageIndex, pageSize, new Class[] { clazz },
				match, sortSql);
	}

	public PageBean emiQueryList(String sql, int pageIndex, int pageSize,
			final Class[] classes, String sortSql) {
		return emiQueryList(sql, pageIndex, pageSize, classes, null, sortSql);
	}

	public PageBean emiQueryList(String sql, int pageIndex, int pageSize,
			final Class<?> clazz, String sortSql) {
		return emiQueryList(sql, pageIndex, pageSize, new Class[] { clazz },
				null, sortSql);
	}

	/*
	 * 带分页，非实体list
	 */
	public PageBean emiQueryList(String sql, int pageIndex, int pageSize,
			String sortSql) {
		String size_sql = sizeSql(sql, pageIndex, pageSize, sortSql);
		List list = queryForList(size_sql);
		return doPageBean(sql, pageIndex, pageSize, list);
	}

	public Object emiQuery(String sql, final Class[] classes,
			final Map<String, String> match) {
		List list = emiQueryList(sql, classes, match);
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 更新数据 2014年8月1日 上午9:43:53 朱晓陈
	 * 
	 * @param beanOrList
	 *            对象或list
	 * @return
	 */
	public boolean emiUpdate(Object beanOrList) {
		if (beanOrList == null) {
			throw new RuntimeException("the update object can not be null");
		}
		List<Object> list = new ArrayList<Object>();
		if (!(beanOrList instanceof ArrayList)) {
			list.add(beanOrList);
		} else {
			list = (List<Object>) beanOrList;
		}
		return emiUpdate_core(list, false);
	}

	/**
	 * 更新数据 2014年8月1日 上午9:43:53 朱晓陈
	 * 
	 * @param beanOrList
	 *            对象或list
	 * @param saveNull
	 *            是否将对象的null值更新入库
	 * @return
	 */
	public boolean emiUpdate(Object beanOrList, boolean saveNull) {
		if (beanOrList == null) {
			throw new RuntimeException("the update object can not be null");
		}
		List<Object> list = new ArrayList<Object>();
		if (!(beanOrList instanceof ArrayList)) {
			list.add(beanOrList);
		} else {
			list = (List<Object>) beanOrList;
		}
		return emiUpdate_core(list, saveNull);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 新增数据 2014年8月1日 上午9:43:53 朱晓陈
	 * 
	 * @param beanOrList
	 *            对象或list
	 * @return
	 */
	public boolean emiInsert(Object beanOrList) {
		if (beanOrList == null) {
			throw new RuntimeException("the insert object can not be null");
		}
		List<Object> list = new ArrayList<Object>();
		if (!(beanOrList instanceof ArrayList)) {
			list.add(beanOrList);
		} else {
			list = (List<Object>) beanOrList;
		}
		return emiInsert_core(list);
	}

	/**
	 * @param sql
	 * @param classes
	 *            查询结果实体类数组
	 * @param match
	 *            自定义匹配值 例：map.put("userName","User.name")
	 * @return List map的key为实体名，value为实体内容。<br>
	 *         结构：[{{"User",user},{"Department":department}},...]
	 */
	public List emiQueryList(String sql, final Class[] classes,
			final Map<String, String> match) {
		return emiQueryList(sql, classes, match, false, null);
	}

	/**
	 * @param sql
	 * @param classes
	 *            查询结果实体类数组
	 * @param match
	 *            自定义匹配值 例：map.put("userName","User.name")
	 * @return List map的key为实体名，value为实体内容。<br>
	 *         结构：[{{"User",user},{"Department":department}},...] cache 是否植入缓存
	 *         att需要植入缓存的模块
	 */
	public List emiQueryList(String sql, final Class[] classes,
			final Map<String, String> match, final boolean cache,
			final String att) {
		
		System.out.println(DateUtil.dateToString(new Date(),
				"yyyy-MM-dd HH:mm:ss") + " sql:" + sql);
		this.init();
		return (List) this.getJdbcTemplate().query(sql,
				new RowMapper<Object>() {
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						String catchColumn = "";
						Object retObj = null; // 返回的值，单个对象或map
						try {
							for (int i = 0; i < classes.length; i++) {
								Class clazz = classes[i];
								if (!clazz.isAnnotationPresent(EmiTable.class)) {
									throw new Exception(
											"table not found from class:"
													+ clazz.getName()
													+ " you may miss the @EmiTable declare.");
								}
								// EmiTable
								// table=(EmiTable)clazz.getAnnotation(EmiTable.class);
								// String tableName=table.name(); //表名
								Object obj = clazz.newInstance(); // new对象
								String beanName = clazz.getSimpleName();

								/* 自动匹配值 */
								Field[] fields = obj.getClass()
										.getDeclaredFields();
								Map<String, Class<?>> types = new HashMap<String, Class<?>>();
								String gId = null;
								for (int j = 0; j < fields.length; j++) {
									Field field = fields[j];
									String autoPro = field.getName();
									Class<?> type = field.getType(); 
									types.put(autoPro, type); // 存储type
									// 加注解的才自动注入值
									if (field
											.isAnnotationPresent(EmiColumn.class)) {
										EmiColumn column = (EmiColumn) field
												.getAnnotation(EmiColumn.class);
										String columnName = column.name(); // 列名
										String setMtd = "set"
												+ autoPro.substring(0, 1)
														.toUpperCase()
												+ autoPro.substring(1);
										Object val = rs.getObject(columnName);
										catchColumn = columnName;
										Method method = obj.getClass()
												.getMethod(setMtd,
														new Class[] { type });
										method.invoke(obj, new Object[] { val });
										if (column.ID()) {
											gId = CommonUtil.Obj2String(val);
										}
									}
								}

								/* 自定义匹配值 */
								if (match != null) {
									for (String key : match.keySet()) {
										String pro = match.get(key);
										if (pro.indexOf(".") > 0) {
											String proBean = pro.substring(0,
													pro.indexOf("."));
											String proP = pro.substring(pro
													.indexOf(".") + 1);
											if (proBean.equals(beanName)) {
												String setMtd = "set"
														+ proP.substring(0, 1)
																.toUpperCase()
														+ proP.substring(1);
												Object val = rs.getObject(key);
												if (val != null) {
													Method method = obj
															.getClass()
															.getMethod(
																	setMtd,
																	new Class[] { types
																			.get(proP) });
													method.invoke(
															obj,
															new Object[] { val });
												}
											}
										}else{
											String proP = pro;
											String setMtd = "set"
													+ proP.substring(0, 1)
															.toUpperCase()
													+ proP.substring(1);
											Object val = rs.getObject(key);
											if (val != null) {
												Method method = obj
														.getClass()
														.getMethod(
																setMtd,
																new Class[] { types
																		.get(proP) });
												method.invoke(
														obj,
														new Object[] { val });
											}
										}
									}
								}
								if (classes.length == 1) {
									retObj = obj;
									// 植入缓存
					
									if (cache == true && att != null && gId!=null) {
										String key = att + "_" + gId;
										boolean suc = memCache.set(key, retObj);
										//System.out.println(suc);
									}
										
								} else {
									retObj = new HashMap<String, Object>();
									((Map) retObj).put(beanName, obj);
								}
							}
						} catch (IllegalArgumentException e) {
							System.out.println("[" + catchColumn + "]字段类型异常");
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return retObj;
					}
				});
	}

	public List emiQueryListColum(String sql, final Class[] classes,
			final Map<String, String> match, final boolean cache,
			final String att) {
		System.out.println(DateUtil.dateToString(new Date(),
				"yyyy-MM-dd HH:mm:ss") + " sql:" + sql);
		init();
		return (List) this.getJdbcTemplate().query(sql,
				new RowMapper<Object>() {
					public Object mapRow(ResultSet rs, int rowNum)
							throws SQLException {

						String catchColumn = "";
						Object retObj = null; // 返回的值，单个对象或map
						try {
							for (int i = 0; i < classes.length; i++) {
								Class clazz = classes[i];
								// 判断是否是注解
								if (!clazz.isAnnotationPresent(EmiTable.class)) {
									throw new Exception(
											"table not found from class:"
													+ clazz.getName()
													+ " you may miss the @EmiTable declare.");
								}
								Object obj = clazz.newInstance(); // new对象

								// 获取类名
								String beanName = clazz.getSimpleName();
								/* 自动匹配值 */// 获取类的所有属性
								Field[] fields = obj.getClass()
										.getDeclaredFields();

								Map<String, Class<?>> types = new HashMap<String, Class<?>>();
								String gId = null;
								Annotation[] annotation = obj.getClass()
										.getDeclaredAnnotations();
								if (annotation.length == 2) {
									for (int j = 0; j < fields.length; j++) {
										Field field = fields[j];
										String autoPro = field.getName();
										Class<?> type = field.getType();
										types.put(autoPro, type); // 存储type
										// 加注解的才自动注入值
										if (field
												.isAnnotationPresent(EmiColumn.class)) {
											EmiColumn column = (EmiColumn) field
													.getAnnotation(EmiColumn.class);
											if (column.ID()) {
												for (int p = 0; p < fields.length; p++) {
													// 实体类属性
													Field fieldp = fields[p];
													// 实体类的名字取出来
													String autoProp = fieldp
															.getName();
													// 拼get方法
													String getMtd = "get"
															+ autoPro
																	.substring(
																			0,
																			1)
																	.toUpperCase()
															+ autoPro
																	.substring(1);
													// 去实体类找get方法
													Method method = obj
															.getClass()
															.getMethod(getMtd);
													// 执行get方法
													Object val = method
															.invoke(obj);
													EmiColumn columnn = (EmiColumn) fieldp
															.getAnnotation(EmiColumn.class);

													if (!columnn.ID()) {
														// 如果不是gId则赋值
														break;
													}
												}

											}
										}
									}
								}

								if (classes.length == 1) {
									retObj = obj;
									// 植入缓存
									if (cache == true && att != null && gId !=null) {
										String key = att + "_" + gId;
										memCache.set(key, retObj);
									}

								} else {
									retObj = new HashMap<String, Object>();
									((Map) retObj).put(beanName, obj);
								}
							}
						} catch (IllegalArgumentException e) {
							System.out.println("[" + catchColumn + "]字段类型异常");
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						return retObj;
					}
				});
	}
	public Object emiFindByMultiCloumn(Map<String,Object> param, Class<?> clazz) {
		EmiTable table = (EmiTable) clazz.getAnnotation(EmiTable.class);
		String tableName = table.name();
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer sel_sql = new StringBuffer();
		StringBuffer sb=new StringBuffer("");
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			// 加注解的才自动注入值
			if (field.isAnnotationPresent(EmiColumn.class)) {
				EmiColumn column = (EmiColumn) field
						.getAnnotation(EmiColumn.class);
				String columnName = column.name(); // 列名
				sel_sql.append(columnName).append(",");
			}
		}
		Iterator itor=param.keySet().iterator();
		while(itor.hasNext()){
			String key=String.valueOf(itor.next());
			String value=String.valueOf(param.get(key));
			if(sb.toString().length()>0)sb.append(" and ");
			sb.append(key).append("='").append(value).append("'");
		}
		String sql = "";
		if (sel_sql.length() > 0&&sb.toString().length()>0) {
			sql = "select " + sel_sql.substring(0, sel_sql.length() - 1)
					+ " from " + tableName + " where " + sb.toString();
		} else {
			throw new RuntimeException("select sql can not be empty string");
		}
		return emiQuery(sql, clazz);
	}
	public Object emiFind(String gid, Class<?> clazz) {
		EmiTable table = (EmiTable) clazz.getAnnotation(EmiTable.class);
		String tableName = table.name();
		Field[] fields = clazz.getDeclaredFields();
		StringBuffer sel_sql = new StringBuffer();
		String idColumn = "";
		for (int j = 0; j < fields.length; j++) {
			Field field = fields[j];
			// 加注解的才自动注入值
			if (field.isAnnotationPresent(EmiColumn.class)) {
				EmiColumn column = (EmiColumn) field
						.getAnnotation(EmiColumn.class);
				String columnName = column.name(); // 列名
				boolean isId = column.ID();
				if (isId) {
					idColumn = columnName;
				}
				sel_sql.append(columnName).append(",");
			}
		}
		String sql = "";
		if (sel_sql.length() > 0) {
			sql = "select " + sel_sql.substring(0, sel_sql.length() - 1)
					+ " from " + tableName + " where " + idColumn + "='" + gid
					+ "'";
		} else {
			throw new RuntimeException("select sql can not be empty string");
		}
		return emiQuery(sql, clazz);
	}

	/**
	 * @author 李小伟
	 * @title 查询单个缓存
	 * @param key
	 * 
	 */
	public Object emi_core_cache_get(String key) {
		this.init();
		return memCache.get(key);
	}

	/**
	 * @author 李小伟
	 * @Title 根据一组ID查询一组缓存
	 * @param keys
	 * @return
	 */
	public List<Object> emi_core_cache_getList(String[] keys) {
		this.init();
		Map<String, Object> objMap = (HashMap<String, Object>) memCache
				.getMulti(keys);
		Object object = null;
		String key = null;
		List<Object> listObj = new ArrayList<Object>();
		for (int i = 0; i < keys.length; i++) {
			key =keys[i];
			object = objMap.get(key);
			listObj.add(object);
		}
		return listObj;
	}

	/**
	 * @author 李小伟
	 * @title 新增时单个插入缓存
	 * @param obj
	 * @param att
	 * @param ddl=0 增加 ddl=1删除
	 * @param gIdCache 数据库中查出的gID
	 * @return
	 */
	public boolean emi_core_cache(Object obj, String att, int ddl,
			String gIdCache) {
		this.init();
		boolean setCache = false;
		String gId = null;
		String key = null;
		if (obj != null) {
			// 得到属性
			Field[] fields = obj.getClass().getDeclaredFields();
			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				// 加注解的才自动注入值
				if (field.isAnnotationPresent(EmiColumn.class)) {
					EmiColumn column = (EmiColumn) field
							.getAnnotation(EmiColumn.class);
					boolean isId = column.ID(); // 判断是否是ID
					if (isId) {
						String autoPro = field.getName();
						String getMtd = "get"
								+ autoPro.substring(0, 1).toUpperCase()
								+ autoPro.substring(1);// 取出方法名
						Method method;// 方法
						try {
							method = obj.getClass().getMethod(getMtd);
							Object val = method.invoke(obj);// 执行方法（getGid)
							gId = val.toString();

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			key = att + "_" + gId;
		}
		if (gIdCache != null && gIdCache != "") {
			key = att + "_" + gIdCache;
		}
		/**
		 * ddl=0 增加 ddl=1删除
		 * */
		switch (ddl) {
		case 0:
			setCache = memCache.set(key, obj);
			break;
		case 1:
			setCache = memCache.delete(key);
			break;
		default:
			break;
		}
		return setCache;
	}

	/**
	 * 新增数据 2014年8月1日 上午9:43:53 朱晓陈
	 * 
	 * @param list
	 *            对象list
	 * @return
	 */

	private boolean emiInsert_core(final List<Object> list) {
		try {
			StringBuffer sql = new StringBuffer(); // sql
			if (list == null || list.size() == 0) {
				return true;
			}
			Object obj = list.get(0);
			EmiTable table = (EmiTable) obj.getClass().getAnnotation(
					EmiTable.class);
			String tableName = table.name();
			Field[] fields = obj.getClass().getDeclaredFields();

			StringBuffer columns = new StringBuffer();
			StringBuffer values = new StringBuffer();
			for (int j = 0; j < fields.length; j++) {
				Field field = fields[j];
				// 加注解的才自动注入值
				if (field.isAnnotationPresent(EmiColumn.class)) {
					EmiColumn column = (EmiColumn) field
							.getAnnotation(EmiColumn.class);
					String columnName = column.name(); // 列名
					boolean increment = column.increment();
					if (increment) {
						// 自增长跳过
						continue;
					}
					columns.append(columnName).append(",");
					values.append("?,");
				}
			}
			String columnSql = "";
			String valueSql = "";
			if (sql.length() == 0 && columns.length() > 0) {
				columnSql = "(" + columns.substring(0, columns.length() - 1)
						+ ")";
				valueSql = "values(" + values.substring(0, values.length() - 1)
						+ ")";
				// 拼接sql
				sql.append("insert into ").append(tableName);
				sql.append(columnSql).append(" ").append(valueSql);
			}
			
			int[] i = batchUpdate(sql.toString(),
					new BatchPreparedStatementSetter() {
						@Override
						public int getBatchSize() {
							return list.size();
						}

						@Override
						public void setValues(PreparedStatement ps, int i)
								throws SQLException {
							try {
								DatabaseMetaData dbmd = null;
								ResultSet rs = null;
								Object valObj = list.get(i);

								Field[] fields = valObj.getClass()
										.getDeclaredFields();
								int index = 1;
//								String gId = null;
								EmiTable table = (EmiTable) valObj.getClass().getAnnotation(
										EmiTable.class);
								for (int n = 0; n < fields.length; n++) {
									Field field = fields[n];
									// 加注解的才自动注入值
									if (field
											.isAnnotationPresent(EmiColumn.class)) {
										EmiColumn column = (EmiColumn) field
												.getAnnotation(EmiColumn.class);
										if (column.increment()) {
											// 自增长跳过
											continue;
										}
										String autoPro = field.getName();
										String getMtd = "get"
												+ autoPro.substring(0, 1)
														.toUpperCase()
												+ autoPro.substring(1);
										Method method = valObj.getClass()
												.getMethod(getMtd);
										Object val = method.invoke(valObj);
										
										// 如果gid字段值为空，自动生成
										boolean isId = column.ID();
										if(isId && (field.getType()==String.class) && (val==null || val.equals(""))){
											val = UUID.randomUUID().toString();
											
											Class<?> type = field.getType();
											String setMtd = "set"+autoPro.substring(0,1).toUpperCase()+autoPro.substring(1);
											Method method_set = valObj.getClass().getMethod(setMtd,new Class[]{type});
											method_set.invoke(valObj, new Object[]{val});
										}
										//有默认值的就设置成默认值
										if(val!=null || !column.hasDefault()){
											// 设置值
											Class<?> type = field.getType();
											if(type.equals(Date.class) && val!=null){
												type = Timestamp.class;
												val = new Timestamp(((Date)val).getTime());
											}
											ps.setObject(index, val);
										}else{
											if(rs==null){
												dbmd = ps.getConnection().getMetaData();
												rs = dbmd.getColumns(null, null, table.name(), column.name());
											}
											ps.setObject(index, columnDefValue(rs,"sqlServer"));
										}
										index++;
									}
								}

							} catch (Exception e) {
								e.printStackTrace();
								try {
									throw e;
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
					});

		} catch (Exception e) {

			e.printStackTrace();
			throw e;

		}

		return true;
	}
	
	private Object columnDefValue(ResultSet rs,String db_type) throws Exception{
		String final_def = null;
		if(rs.next()){
			String col_def = rs.getString("COLUMN_DEF");
			int data_type = rs.getInt("DATA_TYPE");//字段类型
			if(col_def!=null){
				if(DB_TYPE_SQLSERVER.equals(db_type)){//SqlServer的默认值做如下处理
					if(data_type==Types.VARCHAR){
						final_def = col_def.substring(2,col_def.length()-2).replaceAll("''", "'");
					}
					if(data_type==Types.NVARCHAR){
						final_def = col_def.substring(3,col_def.length()-2).replaceAll("''", "'");
					}
					if(data_type==Types.INTEGER){
						final_def = col_def.substring(2,col_def.length()-2);
					}
					//其他待拓展
				}
			}
		}
		return final_def;
	}

	/**
	 * 新增数据 2014年8月1日 上午9:43:53 朱晓陈
	 * 
	 * @param list
	 *            对象list
	 * @param saveNull
	 *            是否保存null
	 * @return
	 */
	private boolean emiUpdate_core(final List<Object> list, boolean saveNull) {
		boolean flag = true;
		try {
			int size = list.size();
			if (list == null || size == 0) {
				return flag;
			}
			String[] sqls = new String[size];
			for (int i = 0; i < size; i++) {
				Object obj = list.get(i);
				StringBuffer sql = new StringBuffer(); // sql
				sql.append("update ");
				EmiTable table = (EmiTable) obj.getClass().getAnnotation(
						EmiTable.class);
				String tableName = table.name();
				Field[] fields = obj.getClass().getDeclaredFields();
				String where = "";

				sql.append(tableName + " set ");
				for (int j = 0; j < fields.length; j++) {
					Field field = fields[j];
					// 加注解的才自动注入值
					if (field.isAnnotationPresent(EmiColumn.class)) {
						EmiColumn column = (EmiColumn) field
								.getAnnotation(EmiColumn.class);
						boolean isId = column.ID();
						if (column.increment()&&!isId) {
							// 自增长的不能改变值，跳过
							continue;
						}
						String columnName = column.name(); // 列名
						String autoPro = field.getName();
						String getMtd = "get"
								+ autoPro.substring(0, 1).toUpperCase()
								+ autoPro.substring(1);
						Method method = obj.getClass().getMethod(getMtd);
						Object val = method.invoke(obj);
						if (!isId) {
							if (val != null) {
								sql.append(columnName).append("=")
										.append(formatValue(val)).append(",");
							} else {
								if (saveNull) {
									sql.append(columnName).append("=")
											.append(formatValue(val))
											.append(",");
								}
							}

						} else {
							where = " where " + columnName + "='" + val + "'";
						}
					}
				}
				if (sql.lastIndexOf(",") == sql.length() - 1) {
					sql = new StringBuffer(sql.substring(0, sql.length() - 1));
				}
				sql.append(where);
				sqls[i] = sql.toString();
			}
			flag = batchUpdate(sqls);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				throw e;
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return false;
		}

		return flag;
	}

	public String formatValue(Object obj) {
		String str = "";
		if (obj != null) {
			if (obj instanceof Timestamp) {
				// 时间类型单独转换
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				str = "'" + sdf.format(new Date(((Timestamp) obj).getTime()))
						+ "'";
			}else if (obj instanceof Date) {
				// 时间类型单独转换
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy/MM/dd HH:mm:ss");
				str = "'" + sdf.format((Date)obj)
						+ "'";
			}else if (obj instanceof BigDecimal) {
				// decimal类型单独转换
				str = ((BigDecimal)obj).stripTrailingZeros().toPlainString();
			} else {
				str = "'" + obj.toString().replaceAll("'", "''") + "'";
			}
		} else {
			str = "null";
		}
		return str;
	}

	/**
	 * 获取单号的存储过程
	 * @category
	 * 2015年12月10日 上午8:21:17
	 * @param billType 输入参数
	 * @param preFix 输入参数
	 * @return
	 */
	public String getCallBillId(final String billType, final String preFix) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection arg0) throws SQLException,
					DataAccessException {
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
	 * seq_name 示例： pv.seq_table
	 *
	 * @category 得到id最大值 2014年12月1日 下午8:47:16
	 * @author 朱晓陈
	 * @param seq_name
	 * @return
	 */
	public String nextSeqVal(final String seq_name) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {

			@Override
			public Object doInConnection(Connection arg0) throws SQLException,
					DataAccessException {

				String psql = "{call _nextval(?,?)}";
				String currentId=null;

				CallableStatement call = arg0.prepareCall(psql);

				call.setString(1, seq_name);
				call.registerOutParameter(2, Types.VARCHAR);
				call.execute();


	            currentId=call.getString("outcur");

	            call.close();
				return currentId;
			}


		}).toString();

	}
	
	

	public static void main(String[] args) {
//		String sql = "SELECT "
//					+"	isnull(doc.dispatchingObj,roc.dispatchingObj) dispatchingObj, "
//					+"	isnull(doc.personUnitVendorGid,roc.personUnitVendorGid) personUnitVendorGid," 
//					+"    SUM(doc.disNum) disNum, "
//					+"    SUM(roc.reportOkNum) reportOkNum, "
//					+"    SUM(prc.realPrice * roc.reportOkNum) totalPrice "
//					+"FROM "
//					+"(SELECT t_m.dispatchingObj,t.produceProcessRouteCGid,t.gid,t.disNum,t.personUnitVendorGid FROM MES_WM_DispatchingOrderC t "
//					+"	LEFT JOIN MES_WM_DispatchingOrder t_m ON t.disGid = t_m.gid WHERE 1=1 ) doc "
//					+"FULL JOIN "
//					+"(SELECT t2_m.dispatchingObj,t2.produceProcessRouteCGid,t2.gid,t2.reportOkNum,t2.personUnitVendorGid,t2.discGid FROM MES_WM_ReportOrderC t2 "
//					+"	LEFT JOIN MES_WM_ReportOrder t2_m ON t2.rptGid = t2_m.gid WHERE 1=1) roc "
//					+"ON doc.gid = roc.discGid "
//					+"LEFT JOIN MES_WM_ProduceProcessRouteC prc ON ISNULL(doc.produceProcessRouteCGid,roc.produceProcessRouteCGid) = prc.gid "
//					+"LEFT JOIN MES_WM_ProduceProcessRoute pr ON pr.gid = prc.produceRouteGid "
//					+"LEFT JOIN WM_ProduceOrder_C poc ON poc.gid = pr.produceCuid "
//					+"LEFT JOIN WM_ProduceOrder po ON po.gid = pr.produceUid "
//					+"GROUP BY isnull(doc.dispatchingObj,roc.dispatchingObj),isnull(doc.personUnitVendorGid,roc.personUnitVendorGid)";
//		String out = getFromSql(sql);
//		System.out.println(out);
	}
	
	

}
