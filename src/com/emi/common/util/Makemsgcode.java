/**
 * 
 */
package com.emi.common.util;

import java.util.Random;

public class Makemsgcode {
	
	
	
public static String Makemsgcode(){
	
	//生成六位验证码
	
	  //创建整型型变量
    int number;
    //创建字符型变量
    char code = 0;
    //创建字符串变量并初始化为空
    String checkCode ="";
    //创建Random对象
    Random random = new Random();
    //使用For循环生成4个数字
    for (int i = 0; i <6; i++)
    {
        //生成一个随机数
        number = random.nextInt(10);
        //将数字转换成为字符型
        checkCode+=String.valueOf(number);
    }
	return checkCode;
  }


}
