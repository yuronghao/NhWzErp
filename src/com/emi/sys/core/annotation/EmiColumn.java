package com.emi.sys.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
/**
 * 
 * @Title:数据库字段注解 (字段类型请打开本源码查询)
 * @Copyright: Copyright (c) v1.0
 * @Company: 江苏一米智能科技股份有限公司
 * @project name: 一米通讯
 * @author: 朱晓陈
 * @version: V1.0
 * @time: 2014年9月10日 上午11:27:17
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface EmiColumn{
// Types type();
	String name();				//列名
	boolean ID() default false;	//是否是id
	boolean increment() default false;	//是否新增的时候赋值
	boolean hasDefault() default false;//(不建议启用，不完善)是否有默认值 目前仅支持sqlserver int、varchar、nvarchar类型，默认值不要带'，以后有需要再拓展或修改
}

/** MySql 数据类型和  Java 数据类型对应一览
类型名称		显示长度	数据库类型				JAVA类型					JDBC类型索引(int)	描述
VARCHAR		L+N		VARCHAR					java.lang.String		12	 
CHAR		N		CHAR					java.lang.String		1	 
BLOB		L+N		BLOB					java.lang.byte[]		-4	 
TEXT		65535	VARCHAR					java.lang.String		-1	 
INTEGER		4		INTEGER UNSIGNED		java.lang.Long			4	 
TINYINT		3		TINYINT UNSIGNED		java.lang.Integer		-6	 
SMALLINT	5		SMALLINT UNSIGNED		java.lang.Integer		5	 
MEDIUMINT	8		MEDIUMINT UNSIGNED		java.lang.Integer		4	 
BIT			1		BIT						java.lang.Boolean		-7	 
BIGINT		20		BIGINT UNSIGNED			java.math.BigInteger	-5	 
FLOAT		4+8		FLOAT					java.lang.Float			7	 
DOUBLE		22		DOUBLE					java.lang.Double		8	 
DECIMAL		11		DECIMAL					java.math.BigDecimal	3	 
BOOLEAN		1		同TINYINT	 	 	 
ID			11		PK (INTEGER UNSIGNED)	java.lang.Long			4	 
DATE		10		DATE					java.sql.Date			91	 
TIME		8		TIME					java.sql.Time			92	 
DATETIME	19		DATETIME				java.sql.Timestamp		93	 
TIMESTAMP	19		TIMESTAMP				java.sql.Timestamp		93	 
YEAR		4		YEAR					java.sql.Date			91

SqlServer 数据类型和  Java 数据类型对应一览
 编号	数据库类型			JDBC类型				JDBC索引
1	int					java.lang.Integer	4	 
2	varchar				java.lang.String	12	 
3	char				java.lang.String	1	 
4	nchar				java.lang.String	1	 
5	nvarchar			java.lang.String	12	 
6	text				java.lang.String	-1	 
7	ntext				java.lang.String	-1	 
8	tinyint				java.lang.Integer	-6	 
9	int					java.lang.Integer	4	 
10	tinyint				java.lang.Integer	-6	 
11	smallint			java.lang.Short		5	 
12	bit					java.lang.Boolean	-7	 
13	bigint				java.lang.Long		-5	 
14	float				java.lang.Double	6	 
15	decimal				java.math.BigDecimal	3	 
16	money				java.math.BigDecimal	3	 
17	smallmoney			java.math.BigDecimal	3	 
18	numeric				java.math.BigDecimal	2	 
19	real				java.lang.Float		7	 
20	uniqueidentifier	java.lang.String	1	 
21	smalldatetime		java.sql.Timestamp	93	 
22	datetime			java.sql.Timestamp	93	 
23	timestamp			byte[]				-2	 
24	binary				byte[]				-2	 
25	varbinary			byte[]				-3	 
26	image				byte[]				-4	 
27	sql_variant			java.lang.String	12
*/