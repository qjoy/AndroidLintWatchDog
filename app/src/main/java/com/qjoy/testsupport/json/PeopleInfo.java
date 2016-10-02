package com.qjoy.testsupport.json;

/**
 * @author AleXQ
 * @Date 16/9/22
 * @Description: 用户信息
 */

/**
 * lint检查提示
 * 应该继承自HunmanInfo,而不是HunmanInfoError
 */
public class PeopleInfo extends HunmanInfoError{
	public String name;
	public String school;
	public String birthday;
}
