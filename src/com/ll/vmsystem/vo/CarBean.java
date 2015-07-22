package com.ll.vmsystem.vo;

import java.sql.Timestamp;

/**
 * Description:CarBean <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class CarBean {

	public CarBean() {
		// super();
	}

	public CarBean(Integer id, String carno, Integer driverid,
			Double longitude, Double latitude, Integer x, Integer y,
			Timestamp lastltime, String cartype, String state,
			Integer destination, Integer lineid, Integer distance, String detail) {
		// super();
		this.id = id;
		this.carno = carno;
		this.driverid = driverid;
		this.longitude = longitude;
		this.latitude = latitude;
		this.x = x;
		this.y = y;
		this.lastltime = lastltime;
		this.cartype = cartype;
		this.state = state;
		this.destination = destination;
		this.lineid = lineid;
		this.distance = distance;
		this.detail = detail;
	}

	private Integer id;
	private String carno;
	private Integer driverid;
	private Double longitude;
	private Double latitude;
	private Integer x;
	private Integer y;
	private Timestamp lastltime;
	private String cartype;
	private String state;
	/**
	 * 车辆到达大门时，有服务器端将目的地改为目的卸货点，以方便门卫判断
	 */
	private Integer destination;
	private Integer lineid;
	private Integer distance;
	private String detail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCarno() {
		return carno;
	}

	public void setCarno(String carno) {
		this.carno = carno;
	}

	public Integer getDriverid() {
		return driverid;
	}

	public void setDriverid(Integer driverid) {
		this.driverid = driverid;
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

	public Timestamp getLastltime() {
		return lastltime;
	}

	public void setLastltime(Timestamp lastltime) {
		this.lastltime = lastltime;
	}

	public String getCartype() {
		return cartype;
	}

	public void setCartype(String cartype) {
		this.cartype = cartype;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getDestination() {
		return destination;
	}

	public void setDestination(Integer destination) {
		this.destination = destination;
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

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	// 之后这些属性是额外添加的，在服务器后台处理时，从其他地方如freight等表中获取的数据，用来传输给管理员和门卫
	// 让其清晰的了解车辆的详情。
	private String providerName;
	private String driverName;
	// 这里放置一个阶段的起始时间，如出发，大门处等待，进入园区，卸货开始，出库中等状态的开始时间，而在客户端自行计算
	// 当前的更新时间
	private Timestamp starttime;
	// freight的优先级，放在车辆这里方便观看，还是同时将car和freight都获取，门卫可以同时查看两个信息呢？
	private Integer proirity;

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public Timestamp getStarttime() {
		return starttime;
	}

	public void setStarttime(Timestamp starttime) {
		this.starttime = starttime;
	}

	public Integer getProirity() {
		return proirity;
	}

	public void setProirity(Integer proirity) {
		this.proirity = proirity;
	}

	private String stopname;
	private String stopstate;

	public String getStopname() {
		return stopname;
	}

	public void setStopname(String stopname) {
		this.stopname = stopname;
	}

	public String getStopstate() {
		return stopstate;
	}

	public void setStopstate(String stopstate) {
		this.stopstate = stopstate;
	}

}
