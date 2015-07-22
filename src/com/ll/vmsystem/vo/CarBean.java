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
	 * �����������ʱ���з������˽�Ŀ�ĵظ�ΪĿ��ж���㣬�Է��������ж�
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

	// ֮����Щ�����Ƕ�����ӵģ��ڷ�������̨����ʱ���������ط���freight�ȱ��л�ȡ�����ݣ��������������Ա������
	// �����������˽⳵�������顣
	private String providerName;
	private String driverName;
	// �������һ���׶ε���ʼʱ�䣬����������Ŵ��ȴ�������԰����ж����ʼ�������е�״̬�Ŀ�ʼʱ�䣬���ڿͻ������м���
	// ��ǰ�ĸ���ʱ��
	private Timestamp starttime;
	// freight�����ȼ������ڳ������﷽��ۿ�������ͬʱ��car��freight����ȡ����������ͬʱ�鿴������Ϣ�أ�
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
