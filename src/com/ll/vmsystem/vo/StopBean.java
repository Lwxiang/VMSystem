package com.ll.vmsystem.vo;

/**
 * Description:StopBean <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class StopBean {

	public StopBean() {
		// super();
	}

	public StopBean(Integer id, String name, Double longitude, Double latitude,
			Integer x, Integer y, Integer lineid, Integer distance,
			String state, Integer carid, String detail) {
		// super();
		this.id = id;
		this.name = name;
		this.longitude = longitude;
		this.latitude = latitude;
		this.x = x;
		this.y = y;
		this.lineid = lineid;
		this.distance = distance;
		this.state = state;
		this.carid = carid;
		this.detail = detail;
	}

	private Integer id;
	private String name;
	private Double longitude;
	private Double latitude;
	private Integer x;
	private Integer y;
	private Integer lineid;
	private Integer distance;
	private String state;
	private Integer carid;
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

	public Integer getLineid() {
		return lineid;
	}

	public void setLineid(Integer lineid) {
		this.lineid = lineid;
	}

	public Integer getDistance() {
		return distance;
	}

	public void setDistance(Integer distance) {
		this.distance = distance;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getCarid() {
		return carid;
	}

	public void setCarid(Integer carid) {
		this.carid = carid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}
