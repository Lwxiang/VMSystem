package com.ll.vmsystem.vo;

/**
 * Description:providerBean <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class ProviderBean {

	// 使用Integer， 使获取时可以设置为NULL
	private Integer id;
	private String password;
	private String name;
	private String principalname;
	private String principalphone;
	private String otherphone;
	private Integer drivernum;
	private String detail;

	public ProviderBean(Integer id, String password, String name,
			String principalname, String principalphone, String otherphone,
			Integer drivernum, String detail) {
		// super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.principalname = principalname;
		this.principalphone = principalphone;
		this.otherphone = otherphone;
		this.drivernum = drivernum;
		this.detail = detail;
	}

	public ProviderBean() {
		// super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrincipalname() {
		return principalname;
	}

	public void setPrincipalname(String principalname) {
		this.principalname = principalname;
	}

	public String getPrincipalphone() {
		return principalphone;
	}

	public void setPrincipalphone(String principalphone) {
		this.principalphone = principalphone;
	}

	public String getOtherphone() {
		return otherphone;
	}

	public void setOtherphone(String otherphone) {
		this.otherphone = otherphone;
	}

	public Integer getDrivernum() {
		return drivernum;
	}

	public void setDrivernum(Integer drivernum) {
		this.drivernum = drivernum;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void outPut() {
		System.out.println("this.id =" + String.format("%09d", id) + '\n'
				+ "this.password = " + password + '\n' + "this.name = " + name
				+ '\n' + "this.principalname = " + principalname + '\n'
				+ "this.principalphone = " + principalphone + '\n'
				+ "this.otherphone = " + otherphone + '\n'
				+ "this.drivernum = " + drivernum + '\n' + "this.detail = "
				+ detail);
	}

}
