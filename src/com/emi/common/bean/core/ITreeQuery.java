package com.emi.common.bean.core;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.sys.core.bean.PageBean;

public interface ITreeQuery {
	/**
	 * @category 树节点的查询方法
	 * 2016年4月13日 下午3:54:22 
	 * @author zhuxiaochen
	 * @return
	 */
	public abstract List<TreeNode> getQueryTree();
	
	/**
	 * @category 点击节点显示右侧列表的查询方法
	 * 2016年4月13日 下午3:54:57 
	 * @author zhuxiaochen
	 * @param 
	 * @return
	 */
	public abstract PageBean getQueryList();
	
	/**
	 * @category 右侧列表字段的初始化,map里面放置key:字段英文名  value:字段中文名
	 * 2016年4月13日 下午3:55:30 
	 * String[] 里放置 ：name-字段英文名、desc-字段中文名、width-宽度、check-是否用于checkbox('1','0')
	 * @author zhuxiaochen
	 * @return
	 */
	public abstract List<JSONObject> getColumns();
	
}
