package com.ll.vmsystem.vo;

/**
 * Description:CargoBean
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public class CargoBean {

	
	public CargoBean() {
		//super();
	}
	public CargoBean(Integer id, String name, Integer cargoPriority,
			Integer stopId, Integer providerId, String detail) {
		//super();
		this.id = id;
		this.name = name;
		this.cargoPriority = cargoPriority;
		this.stopId = stopId;
		this.providerId = providerId;
		this.detail = detail;
	}
	private Integer id;
	private String name;
	private Integer cargoPriority;
	private Integer stopId;
	private Integer providerId;
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
	public Integer getCargoPriority() {
		return cargoPriority;
	}
	public void setCargoPriority(Integer cargoPriority) {
		this.cargoPriority = cargoPriority;
	}
	public Integer getStopId() {
		return stopId;
	}
	public void setStopId(Integer stopId) {
		this.stopId = stopId;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
}
