package com.emi.wms.basedata.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.bean.PageBean;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.wms.basedata.service.AttributeService;
import com.emi.wms.bean.AaBaseAttr;
import com.emi.wms.bean.AaBaseAttrDetail;
import com.emi.wms.bean.AaCategory;
import com.emi.wms.bean.AaSoulation;
import com.emi.wms.bean.AaSoulationdetail;
import com.emi.wms.bean.AaSoulationlist;

/**
 * 属性方案，基本属性，属性类别
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class AttributeAction extends BaseAction {
	private AttributeService attributeService;

	public void setAttributeService(AttributeService attributeService) {
		this.attributeService = attributeService;
	}

	public AttributeService getAttributeService() {
		return attributeService;
	}

	// ......................................................................属性方案....................................................................................//
	/**
	 * 
	 * @category 属性方案列表 2015年12月3日 上午10:19:49
	 * @return
	 */
	public String getAA_SoulationList() {
		try {
			int pageIndex = getPageIndex(); // 页码，从1开始
			int pageSize = getPageSize();// getPageSize(); //每页总条数
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String condition = " and s.sobGid='" + sobId + "' and s.orgGid='"
					+ orgId + "'";
			if (CommonUtil.notNullString(getParameter("sname"))) {
				condition += " and s.souname like '%" + getParameter("sname")
						+ "%'";
			}
			PageBean bean = attributeService.getAA_SoulationList(pageIndex,
					pageSize, condition);
			getRequest().setAttribute("data", bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "soulationList";
	}

	/**
	 * 返回添加或修改属性方案页面
	 * 
	 * @category 年12月3日 上午11:16:34
	 * @return
	 */
	public String getAA_SoulationAdd() {
		try {
			String gid = getRequest().getParameter("gid");
			AaSoulation aaSoulation = null;
			List<AaSoulationlist> aSoulationlist = null;
			List<AaSoulationdetail> aSoulationdetail = null;
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String condition = " and s.sobGid='" + sobId + "' and s.orgGid='"
					+ orgId + "'";
			List<AaBaseAttr> aaBaseAttr = attributeService.getAaBaseAttr("",
					condition);
			List<AaBaseAttrDetail> aaBaseAttrDetail = null;
			if (CommonUtil.notNullString(gid)) {
				aaSoulation = attributeService.findAaSoulation(gid);
				aSoulationlist = attributeService.findAaSoulationlist(gid);
			}
			if (CommonUtil.notNullObject(aaBaseAttr)) {//判断属性方案里的基本属性和属性值是否在基本属性中是否存在，存在加状态
				for (AaBaseAttr base : aaBaseAttr) {
					String consql = " and s.base_attr_Uid='" + base.getGid()
							+ "'";
					aaBaseAttrDetail = attributeService.getAaBaseAttrDetail("",
							consql);
					if (CommonUtil.notNullObject(aSoulationlist)) {
						for (AaSoulationlist list : aSoulationlist) {
							if (list.getBasepropertygid().equals(base.getGid())) {
								base.setCheckFlag(1);           //判断属性方案里的基本属性是否存在，存在加状态
							}
							aSoulationdetail = attributeService
									.findAaSoulationdetail(list.getGid());
							for (AaBaseAttrDetail detail : aaBaseAttrDetail) {
								for (AaSoulationdetail sdetail : aSoulationdetail) {
									if (detail.getGid().equals(
											sdetail.getBasevaluegid())) {
										detail.setCheckFlag(1);   ////判断属性方案里属性值是否在基本属性中是否存在，存在加状态
									}
								}
							}
						}
					}
					base.setDetailList(aaBaseAttrDetail);
				}
			}
			List<AaCategory> cate=attributeService.getAaCategory();
			getRequest().setAttribute("bean", aaSoulation);
			getRequest().setAttribute("list", aaBaseAttr);
			getRequest().setAttribute("gid", gid);
			getRequest().setAttribute("cate", cate);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "soulationAdd";
	}

	/**
	 * 更加基本属性gid获取属性值
	 * 
	 * @category 年12月8日 下午4:06:31
	 */
	public void findAaBaseAttrDetail() {
		String gid = getParameter("gid");
		String consql = " and s.base_attr_Uid='" + gid + "'";
		List<AaBaseAttrDetail> aaBaseAttrDetail = attributeService
				.getAaBaseAttrDetail("", consql);
		try {
			getResponse().getWriter().write(
					EmiJsonArray.fromObject(aaBaseAttrDetail).toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 添加或者修改属性方案
	 * 
	 * @category 年12月3日 上午11:16:22
	 */
	public void AddAA_Soulation() {
		try {
			String gid = getParameter("gid"); // 属性方案Gid
			String ugid = getParameter("bigModel"); // 基本属性Gid
			String bgid = getParameter("bsmallModel"); // 属性值Gid
			String sgid = getParameter("smallModel"); // 属性值所属属性的Gid
			String categid = getParameter("categid");
			String mid=getParameter("gid"); // 属性方案Gid
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String flag = getParameter("flag");
			String name = getParameter("name");
			String conditionSql = "";
			if (CommonUtil.isNullString(gid)) {
				conditionSql += " and s.souname='" + name + "' and s.orgGid='"
						+ orgId + "' and s.sobGid='" + sobId + "'";
			} else {
				conditionSql += " and s.gid<>'" + gid + "' and s.souname='" + name
						+ "' and s.orgGid='" + orgId + "' and s.sobGid='" + sobId
						+ "'";
			}
			PageBean bean = attributeService.getAA_SoulationList(0, 0,
					conditionSql);
			if (bean.getPageCount() > 0) {// 判断是否有相同的名称
				getResponse().getWriter().write("fail");
			} else
			{
				if (CommonUtil.isNullString(gid)) {
					gid = UUID.randomUUID().toString();
				}
				AaSoulation sou = new AaSoulation();
				sou.setCategorygid(categid);
				sou.setSouname(name);
				sou.setFlag(Integer.parseInt(flag));
				sou.setOrgGid(orgId);
				sou.setSobGid(sobId);
				sou.setGid(gid);
				sou.setIsdel(0);
				String[] tem = ugid.split(",");
				String[] temp = bgid.split(",");
				String[] temps = sgid.split(",");
				List<AaSoulationdetail> addDetaillist = new ArrayList<AaSoulationdetail>();
				List<AaSoulationlist> addlist = new ArrayList<AaSoulationlist>();
	
				for (int i = 0; i < tem.length; i++) {
	
					AaSoulationlist soulist = new AaSoulationlist();
					String soulistGid = UUID.randomUUID().toString();
					soulist.setBasepropertygid(tem[i]);
					soulist.setSoulationgid(gid);
					soulist.setGid(soulistGid);
					for (int j = 0; j < temp.length; j++) {
						if (tem[i].equals(temp[j])) {
							AaSoulationdetail detalist = new AaSoulationdetail();
							detalist.setSoulationlistgid(soulistGid);
							detalist.setBasevaluegid(temps[j]);
							addDetaillist.add(detalist);
						}
					}
					addlist.add(soulist);
				}
				boolean suc = false;
				if (CommonUtil.isNullString(mid)) {
					suc = attributeService.addList(sou, addlist, addDetaillist);
	
				} else {
					
					suc = attributeService.updateList(sou, addlist, addDetaillist,
							mid);
				}
				if (suc) {
					getResponse().getWriter().write("success");
				} else {
					getResponse().getWriter().write("error");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除属性方案
	 * 
	 * @category 年12月3日 上午11:16:10
	 */
	public void DeleteAA_Soulation() {
		try {
			String[] gid = getRequest().getParameterValues("strsum");
			boolean suc = attributeService.deleteAaSoulation(gid);
			if (suc) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ......................................................................属性值....................................................................................//
	/**
	 * 基本属性列表
	 * @category
	 * 2015年12月9日 上午9:09:26
	 * @return
	 */
	public String getAA_BaseAttrList() {
		try {
			int pageIndex = getPageIndex(); // 页码，从1开始
			int pageSize = getPageSize();// getPageSize(); //每页总条数
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String condition = " and c.sobGid='" + sobId + "' and c.orgGid='"
					+ orgId + "'";
			if (CommonUtil.notNullString(getParameter("sname"))) {
				condition += " and c.name like '%" + getParameter("sname")
						+ "%'";
				/* getRequest().setAttribute("name", getParameter("name")); */
			}
			PageBean bean = attributeService.getAA_BaseAttrList(pageIndex,
					pageSize, condition);
			getRequest().setAttribute("data", bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "baseAttrList";
	}
	/**
	 * 返回基本属性添加或者修改页面
	 * @category
	 * 2015年12月9日 上午9:09:55
	 * @return
	 */
	public String getAA_BaseAttrAdd() {
		try {
			String gid = getRequest().getParameter("gid");
			AaBaseAttr aaBaseAttr = null;
			if (CommonUtil.notNullString(gid)) {
				aaBaseAttr = attributeService.findAaBaseAttr(gid);
			}
			getRequest().setAttribute("bean", aaBaseAttr);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "baseAttrAdd";
	}
	/**
	 * 
	 * @category 添加或者修改基本属性
	 * 2015年12月9日 上午9:10:22
	 */
	public void AddAA_BaseAttr() {
		try {
			String name = getRequest().getParameter("name");
			String gid = getRequest().getParameter("gid");
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String conditionSql = "";
			boolean suc = false;
			if (CommonUtil.isNullString(gid)) {
				conditionSql += " and c.name='" + name + "' and c.orgGid='"
						+ orgId + "' and c.sobGid='" + sobId + "'";
			} else {
				conditionSql += " and c.gid<>'" + gid + "' and c.name='" + name
						+ "' and c.orgGid='" + orgId + "' and c.sobGid='" + sobId
						+ "'";
			}
			PageBean bean = attributeService.getAA_BaseAttrList(0, 0,
					conditionSql);
			AaBaseAttr aaBaseAttr = new AaBaseAttr();
			aaBaseAttr.setName(name);
			if (bean.getPageCount() > 0) {// 判断是否有相同的名称
				getResponse().getWriter().write("fail");
			} else {
				if (CommonUtil.isNullString(gid)) {
					aaBaseAttr.setOrgGid(orgId);
					aaBaseAttr.setSobGid(sobId);
					aaBaseAttr.setIsdel(0);
					suc = attributeService.addAaBaseAttr(aaBaseAttr);
				} else {
					aaBaseAttr.setGid(gid);
					suc = attributeService.updateAaBaseAttr(aaBaseAttr);
				}
				if (suc) {
					getResponse().getWriter().write("success");
				} else {
					getResponse().getWriter().write("error");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *  删除基本属性
	 * @category
	 * 2015年12月9日 上午9:10:40
	 */
	public void DeleteAA_BaseAttr() {
		try {
			String[] gid = getRequest().getParameterValues("strsum");
			boolean suc = attributeService.deleteAaBaseAttr(gid);
			if (suc) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  /**
   * 弹出查询基本属性页面
   * @category
   * 2015年12月9日 上午9:11:11
   * @return
   */
	public String getSerchAA_BaseAttr() {
		return "serchbaseAttr";
	}

	public String getAA_BaseAttrValList() {
		try {
			int pageIndex = getPageIndex(); // 页码，从1开始
			int pageSize = getPageSize();// getPageSize(); //每页总条数
			String condition = " and c.base_attr_Uid ='" + getParameter("gid")
					+ "' ";
			PageBean bean = attributeService.getAA_BaseAttrValList(pageIndex,
					pageSize, condition);
			getRequest().setAttribute("data", bean);
			getRequest().setAttribute("Ugid", getParameter("gid"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "baseAttrValList";
	}
	/**
	 * 返回基本属性值添加或者修改页面
	 * @category
	 * 2015年12月9日 上午9:11:36
	 * @return
	 */
	public String getAA_BaseValAttrAdd() {
		try {
			String gid = getRequest().getParameter("gid");
			String Ugid = getRequest().getParameter("Ugid");
			AaBaseAttrDetail aaBaseAttr = null;
			if (CommonUtil.notNullString(gid)) {
				aaBaseAttr = attributeService.findAaBaseValAttr(gid);
			}

			getRequest().setAttribute("bean", aaBaseAttr);
			getRequest().setAttribute("Ugid", Ugid);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "baseValAttrAdd";
	}
	/**
	 * 添加或者修改基本属性值
	 * @category
	 * 2015年12月9日 上午9:11:55
	 */
	public void AddAA_BaseValAttr() {
		try {
			String name = getRequest().getParameter("name");
			String gid = getRequest().getParameter("gid");
			String base_attr_Uid = getRequest().getParameter("Ugid");
			String conditionSql = "";
			boolean suc = false;
			if (CommonUtil.isNullString(gid)) {
				conditionSql += " and c.name='" + name
						+ "' and c.base_attr_Uid='" + base_attr_Uid + "'";
			} else {
				conditionSql += " and c.gid<>'" + gid + "' and c.name='" + name
						+ "' and c.base_attr_Uid='" + base_attr_Uid + "'";
			}
			PageBean bean = attributeService.getAA_BaseAttrValList(0, 0,
					conditionSql);
			AaBaseAttrDetail aaBaseAttr = new AaBaseAttrDetail();
			aaBaseAttr.setName(name);
			if (bean.getPageCount() > 0) {// 判断是否有相同的名称
				getResponse().getWriter().write("fail");
			} else {
				if (CommonUtil.isNullString(gid)) {
					aaBaseAttr.setIsdel(0);
					aaBaseAttr.setBase_attr_Uid(base_attr_Uid);
					suc = attributeService.addAaBaseValAttr(aaBaseAttr);
				} else {
					aaBaseAttr.setGid(gid);
					suc = attributeService.updateAaBaseValAttr(aaBaseAttr);
				}
				if (suc) {
					getResponse().getWriter().write("success");
				} else {
					getResponse().getWriter().write("error");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 删除基本属性值
	 * @category
	 * 2015年12月9日 上午9:12:17
	 */
	public void DeleteAA_BaseValAttr() {
		try {
			String[] gid = getRequest().getParameterValues("strsum");
			boolean suc = attributeService.deleteAaBaseValAttr(gid);
			if (suc) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ......................................................................属性类别....................................................................................//
	/**
	 * 属性类别列表
	 * 
	 * @category 年12月3日 上午10:20:05
	 * @return
	 */
	public String getAA_CategoryList() {
		try {
			int pageIndex = getPageIndex(); // 页码，从1开始
			int pageSize = getPageSize();// getPageSize(); //每页总条数
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String condition = " and c.sobGid='" + sobId + "' and c.orgGid='"
					+ orgId + "'";
			if (CommonUtil.notNullString(getParameter("sname"))) {
				condition += " and c.name like '%" + getParameter("sname")
						+ "%'";
				/* getRequest().setAttribute("name", getParameter("name")); */
			}
			PageBean bean = attributeService.getAA_CategoryList(pageIndex,
					pageSize, condition);
			getRequest().setAttribute("data", bean);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "categoryList";
	}

	/**
	 * 返回属性类别添加或者修改页面
	 * 
	 * @category 年12月3日 上午10:23:11
	 * @return
	 */
	public String getAA_CategoryAdd() {
		try {
			String gid = getRequest().getParameter("gid");
			AaCategory category = null;
			if (CommonUtil.notNullString(gid)) {
				category = attributeService.findAA_Category(gid);
			}
			getRequest().setAttribute("bean", category);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "categoryAdd";
	}

	/**
	 * 添加或者修改属性类别
	 * 
	 * @category 年12月3日 上午10:22:51
	 */
	public void AddAA_Category() {
		try {
			String name = getRequest().getParameter("name");
			String gid = getRequest().getParameter("gid");
			String orgId = getSession().get("OrgId").toString();
			String sobId = getSession().get("SobId").toString();
			String conditionSql = "";
			boolean suc = false;
			if (CommonUtil.isNullString(gid)) {
				conditionSql += " and c.name='" + name + "' and c.orgGid='"
						+ orgId + "' and c.sobGid='" + sobId + "'";
			} else {
				conditionSql += " and c.gid<>'" + gid + "' and c.name='" + name
						+ "' and c.orgGid='" + orgId + "' and c.sobGid='" + sobId
						+ "'";
			}
			PageBean bean = attributeService.getAA_CategoryList(0, 0,
					conditionSql);
			AaCategory category = new AaCategory();
			category.setName(name);
			if (bean.getPageCount() > 0) {// 判断是否有相同的类别
				getResponse().getWriter().write("fail");
			} else {
				if (CommonUtil.isNullString(gid)) {
					category.setOrgGid(orgId);
					category.setSobGid(sobId);
					category.setIsdel(0);
					suc = attributeService.addAaCategory(category);
				} else {
					category.setGid(gid);
					suc = attributeService.updateAaCategory(category);
				}
				if (suc) {
					getResponse().getWriter().write("success");
				} else {
					getResponse().getWriter().write("error");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除属性类别
	 * 
	 * @category 年12月3日 上午10:23:01
	 */
	public void DeleteAA_Category() {
		try {
			String[] gid = getRequest().getParameterValues("strsum");
			boolean suc = attributeService.deleteAaCategory(gid);
			if (suc) {
				getResponse().getWriter().write("success");
			} else {
				getResponse().getWriter().write("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 查询
	 * 
	 * @category 年12月7日 上午11:25:36
	 * @return
	 */
	public String getSerchAA_Category() {
		/*
		 * String name=getParameter("name"); getRequest().setAttribute("name",
		 * name);
		 */
		return "serchAACategory";
	}
}
