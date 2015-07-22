package com.ll.vmsystem.vo;

import java.sql.Timestamp;

/**
 * Description:FreightBean
 * <br/>Copyright (C), 2015-2020, LL_VMSystem
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LL_VMSystem
 * <br/>Date:2015-01
 * @author  LL  luoxianminggg@163.com
 * @version  1.0
 */
public class FreightBean {
	
	
	public FreightBean(Integer id, Integer providerId, Integer driverId,
			Integer driverPriority, Integer managerPriority,
			Integer cargoPriority, Integer priority, Timestamp submitTime,
			Timestamp confirmTime, Timestamp expectedTime,
			Timestamp arrivedTime, Timestamp startTime, Timestamp finishedTime,
			String state, Integer cargoId, String cargoAmount, String detail) {
		//super();
		this.id = id;
		this.providerId = providerId;
		this.driverId = driverId;
		this.driverPriority = driverPriority;
		this.managerPriority = managerPriority;
		this.cargoPriority = cargoPriority;
		this.priority = priority;
		this.submitTime = submitTime;
		this.confirmTime = confirmTime;
		this.expectedTime = expectedTime;
		this.arrivedTime = arrivedTime;
		this.startTime = startTime;
		this.finishedTime = finishedTime;
		this.state = state;
		this.cargoId = cargoId;
		this.cargoAmount = cargoAmount;
		this.detail = detail;
	}
	
	public FreightBean() {
		//super();
	}
	
	private Integer id;
	private Integer providerId;
	private Integer driverId;
	private Integer driverPriority;
	private Integer managerPriority;
	private Integer cargoPriority;
	private Integer priority;
	private Timestamp submitTime;
	private Timestamp confirmTime;
	private Timestamp expectedTime;
	private Timestamp arrivedTime;
	private Timestamp startTime;
	private Timestamp finishedTime;
	private String state;
	private Integer cargoId;
	private String cargoAmount;
	private String detail;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getProviderId() {
		return providerId;
	}
	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}
	public Integer getDriverId() {
		return driverId;
	}
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	public Integer getDriverPriority() {
		return driverPriority;
	}
	public void setDriverPriority(Integer driverPriority) {
		this.driverPriority = driverPriority;
	}
	public Integer getManagerPriority() {
		return managerPriority;
	}
	public void setManagerPriority(Integer managerPriority) {
		this.managerPriority = managerPriority;
	}
	public Integer getCargoPriority() {
		return cargoPriority;
	}
	public void setCargoPriority(Integer cargoPriority) {
		this.cargoPriority = cargoPriority;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Timestamp getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Timestamp submitTime) {
		this.submitTime = submitTime;
	}
	public Timestamp getConfirmTime() {
		return confirmTime;
	}
	public void setConfirmTime(Timestamp confirmTime) {
		this.confirmTime = confirmTime;
	}
	public Timestamp getExpectedTime() {
		return expectedTime;
	}
	public void setExpectedTime(Timestamp expectedTime) {
		this.expectedTime = expectedTime;
	}
	public Timestamp getArrivedTime() {
		return arrivedTime;
	}
	public void setArrivedTime(Timestamp arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	public Timestamp getStartTime() {
		return startTime;
	}
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}
	public Timestamp getFinishedTime() {
		return finishedTime;
	}
	public void setFinishedTime(Timestamp finishedTime) {
		this.finishedTime = finishedTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getCargoId() {
		return cargoId;
	}
	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}
	public String getCargoAmount() {
		return cargoAmount;
	}
	public void setCargoAmount(String cargoAmount) {
		this.cargoAmount = cargoAmount;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	
	
	
}
