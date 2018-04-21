package com.emi.common.dao;

import java.util.List;

import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.sys.file.bean.Record_doc;

public class CommonFileUploadDao extends BaseDao{



	/**
	 * 添加文件记录
	 * 2015年7月1日 上午10:11:14
	 * @param doc
	 */
	public void addFile(Record_doc doc) {
		this.emiInsert(doc);
	}

	/**
	 * 更新数据的附件状态
	 * 2015年7月1日 上午10:30:45
	 * @param tableName
	 * @param recordId
	 * @param statusColumn
	 */
	public void updateDataStatus(String tableName, String recordId,String idColumnName,
			String statusColumn,boolean status) {
		String sql = "update "+tableName+" set "+statusColumn+"="+status+" where "
				+ " "+idColumnName+"='"+recordId+"' ";
		if(status){
			//如果是新增，字段值是true则不需要更新
			sql += " and "+statusColumn+"<>true";
		}
		this.update(sql);
	}

	/**
	 * 删除文件(多个id用逗号隔开)
	 * 2015年7月1日 下午7:56:51
	 * @param fileId
	 */
	public void deleteFile(String fileIds) {
		fileIds = "'"+fileIds.replaceAll(",", "','")+"'";
		String sql = "delete from record_doc where docid in ("+fileIds+")";
		this.update(sql);
	}

	/**
	 * 检测是否还有附件
	 * 2015年7月1日 下午8:12:55
	 * @param tableName
	 * @param recordId
	 * @return
	 */
	public boolean checkRecordFile(String tableName, String recordId) {
		String sql = "select count(1) from record_doc where tablename='"+tableName+"' and recordid='"+recordId+"'";
		int count = this.queryForInt(sql);
		return count>0;
	}

	/**
	 * 得到文件夹下的所有附件
	 * 2015年7月1日 下午8:13:15
	 * @param folderId
	 * @return
	 */
	public List<Record_doc> getFilesInFolder(String folderId) {
		String sql = "select "+CommonUtil.colsFromBean(Record_doc.class)+" from record_doc "
				+ " where folderid='"+folderId+"'";
		return this.emiQueryList(sql, Record_doc.class);
	}

	public Record_doc findDoc(String fileId) {
		return (Record_doc) this.emiFind(fileId, Record_doc.class);
	}

	/**
	 * 数据的所有文件
	 * 2015年7月8日 下午8:25:37
	 * @param tableName
	 * @param recordId 多个用逗号隔开
	 * @return
	 */
	public List<Record_doc> getRecordFiles(String tableName, String recordIds) {
		recordIds = "'"+recordIds.replaceAll(",", "','")+"'";
		String sql = "select "+CommonUtil.colsFromBean(Record_doc.class)+" from record_doc "
				+ " where tablename='"+tableName+"' and recordid in ("+recordIds+")";
		return this.emiQueryList(sql, Record_doc.class);
	}

}
