package com.emi.common.service;

import java.util.List;

import com.emi.common.dao.CommonFileUploadDao;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.FileUploadUtils;
import com.emi.sys.file.bean.Record_doc;
import com.emi.sys.util.SysPropertites;

public class CommonFileUploadService {
	private CommonFileUploadDao commonFileUploadDao;
	private static String statusColumn = "doc";

	public void setCommonFileUploadDao(CommonFileUploadDao commonFileUploadDao) {
		this.commonFileUploadDao = commonFileUploadDao;
	}



	/**
	 * 添加文件记录,同时更新记录的附件字段 将‘是否有附件’字段更新为true 2015年7月1日 上午10:10:16
	 * 
	 * @param doc
	 */
	public void addFile(Record_doc doc, String tableName, String recordId,
			String idColumnName,String statusCol) {
		if(CommonUtil.isNullString(statusCol)){
			statusCol = statusColumn;
		}
		// 1、添加记录
		commonFileUploadDao.addFile(doc);
		// 2、修改数据状态
		if (CommonUtil.notNullString(statusCol)) {
			commonFileUploadDao.updateDataStatus(tableName, recordId,
					idColumnName, statusCol, true);
		}

	}
	public void addFile(Record_doc doc, String tableName, String recordId,
			String idColumnName) {
		addFile(doc, tableName, recordId, idColumnName,null);
	}

	/**
	 * 删除文件 2015年7月1日 下午7:39:58
	 * 
	 * @param fileId
	 * @param recordId
	 * @param tableName
	 * @param idColumnName
	 */
	public void deleteFile(String fileId, String recordId, String tableName,
			String idColumnName) {
		Record_doc doc = commonFileUploadDao.findDoc(fileId);
		// 1、删除文件（数据库、磁盘）
		commonFileUploadDao.deleteFile(fileId);
		FileUploadUtils.delFile(doc.getUrl());
		// 2、检查是否还有附件，没有附件则更新业务数据
		boolean hasFile = commonFileUploadDao.checkRecordFile(tableName,
				recordId);
		if (!hasFile) {
			commonFileUploadDao.updateDataStatus(tableName, recordId,
					idColumnName, statusColumn, false);
		}
	}

	public List<Record_doc> getDocList(String tableName, String recordIds) {
		List<Record_doc> docs = commonFileUploadDao.getRecordFiles(tableName,
				recordIds);
		return docs;
	}

	/**
	 * 删除数据下的所有文件 2015年7月8日 下午8:16:22 recordIds 多个用逗号隔开
	 */
	public void deleteAllRecordFiles(String tableName, String recordIds) {
		// 文件列表
		List<Record_doc> docs = commonFileUploadDao.getRecordFiles(tableName,
				recordIds);
		// 删除数据库
		String docIds = "";
		for (Record_doc doc : docs) {
			docIds += doc.getDocid() + ",";
		}
		docIds = CommonUtil.cutLastString(docIds, ",");
		commonFileUploadDao.deleteFile(docIds);
		// 删除磁盘文件
		for (Record_doc doc : docs) {
			FileUploadUtils.delFile(doc.getUrl());
		}
	}

	public List<Record_doc> getFilesInFolder(String folderId) {
		return commonFileUploadDao.getFilesInFolder(folderId);
	}

	public Record_doc findDoc(String fileId) {
		return commonFileUploadDao.findDoc(fileId);
	}

	/**
	 * 记录是否有附件 2015年7月16日 下午4:55:03
	 * 
	 * @return
	 */
	public boolean checkRecordHasFile(String tableName, String recordId) {
		return commonFileUploadDao.checkRecordFile(tableName, recordId);
	}
	
	/**
	 * 获取所有附件
	 * 2015年10月22日 上午9:26:31
	 * @return
	 */
	public List<Record_doc> getRecordFiles(String tableName, String recordIds) {
		return commonFileUploadDao.getRecordFiles( tableName,  recordIds);
	}
}