package com.ll.vmsystem.vo;

/**
 * Description:DriverBean
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public class DriverBean {

	
	
	public DriverBean() {
		//super();
	}
	public DriverBean(Integer id, String name, String password, String phone,
			String otherphone, Integer providerid, Integer carid,
			Integer freightid, Integer stopid, String state, String detail) {
		//super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.phone = phone;
		this.otherphone = otherphone;
		this.providerid = providerid;
		this.carid = carid;
		this.freightid = freightid;
		this.stopid = stopid;
		this.state = state;
		this.detail = detail;
	}
	private Integer id;
	private String name;
	private String password;
	private String phone;
	private String otherphone;
	private Integer providerid;
	private Integer carid;
	private Integer freightid;
	private Integer stopid;
	private String state;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOtherphone() {
		return otherphone;
	}
	public void setOtherphone(String otherphone) {
		this.otherphone = otherphone;
	}
	public Integer getProviderid() {
		return providerid;
	}
	public void setProviderid(Integer providerid) {
		this.providerid = providerid;
	}
	public Integer getCarid() {
		return carid;
	}
	public void setCarid(Integer carid) {
		this.carid = carid;
	}
	public Integer getFreightid() {
		return freightid;
	}
	public void setFreightid(Integer freightid) {
		this.freightid = freightid;
	}
	public Integer getStopid() {
		return stopid;
	}
	public void setStopid(Integer stopid) {
		this.stopid = stopid;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}
