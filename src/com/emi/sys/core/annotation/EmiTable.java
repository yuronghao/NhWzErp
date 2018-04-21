package com.emi.sys.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 
 * @Title:数据库表注解
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: 一米通讯
 * @author: 朱晓陈
 * @version: V1.0
 * @time: 2014年9月10日 上午11:27:50
 */
//在运行时运用注解
@Retention(RetentionPolicy.RUNTIME)
//定义一个注解的名字
public @interface EmiTable{
	 String name();	//表名
}