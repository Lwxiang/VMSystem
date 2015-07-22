package com.ll.vmsystem.vo;

/**
 * Description:GateBean <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-02
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class GateBean {

	public GateBean() {
		// super();
	}

	public GateBean(Integer id, String name, String state, Integer waitingcars,
			String type, Double longitude, Double latitude, Integer lineid,
			Integer guardid, String detail, Integer x, Integer y) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.waitingcars = waitingcars;
		this.type = type;
		this.longitude = longitude;
		this.latitude = latitude;
		this.lineid = lineid;
		this.detail = detail;
		this.x = x;
		this.y = y;
		this.guardid = guardid;
	}

	private Integer id;
	private String name;
	private String state;
	private Integer waitingcars;
	private String type;
	private Double longitude;
	private Double latitude;
	private Integer lineid;
	private Integer guardid;

	public Integer getGuardid() {
		return guardid;
	}

	public void setGuardid(Integer guardid) {
		this.guardid = guardid;
	}

	private String detail;
	private Integer x;
	private Integer y;

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getWaitingcars() {
		return waitingcars;
	}

	public void setWaitingcars(Integer waitingcars) {
		this.waitingcars = waitingcars;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Integer getLineid() {
		return lineid;
	}

	public void setLineid(Integer lineid) {
		this.lineid = lineid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
