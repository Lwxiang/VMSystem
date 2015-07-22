package com.ll.vmsystem.vo;

import java.sql.Timestamp;

/**
 * Description:ManagerBean
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-02
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public class ManagerBean {

	
	public ManagerBean() {
		super();
	}
	public ManagerBean(Integer id, String name, String phone, String password,
			String otherphone, Timestamp lastlogin, String detail) {
		//super();
		this.id = id;
		this.name = name;
		this.phone = phone;
		this.password = password;
		this.otherphone = otherphone;
		this.lastlogin = lastlogin;
		this.detail = detail;
	}
	private Integer id;
	private String name;
	private String phone;
	private String password;
	private String otherphone;
	private Timestamp lastlogin;
	private String detail;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getOtherphone() {
		return otherphone;
	}
	public void setOtherphone(String otherphone) {
		this.otherphone = otherphone;
	}
	public Timestamp getLastlogin() {
		return lastlogin;
	}
	public void setLastlogin(Timestamp lastlogin) {
		this.lastlogin = lastlogin;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
}
