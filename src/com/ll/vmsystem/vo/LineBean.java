package com.ll.vmsystem.vo;

/**
 * Description:LineBean <br/>
 * Copyright (C), 2015-2020, LL_VMSystem <br/>
 * This program is protected by copyright laws. <br/>
 * Program Name:LL_VMSystem <br/>
 * Date:2015-01
 * 
 * @author LL luoxianminggg@163.com
 * @version 1.0
 */
public class LineBean {

	public LineBean() {
		// super();
	}

	public LineBean(Integer id, String name, Double a_longitude,
			Double a_latitude, Double b_longitude, Double b_latitude,
			Integer a_x, Integer a_y, Integer b_x, Integer b_y, Integer length,
			Double a, Double b, Double c, Integer carnum, String detail) {
		super();
		this.id = id;
		this.name = name;
		this.a_longitude = a_longitude;
		this.a_latitude = a_latitude;
		this.b_longitude = b_longitude;
		this.b_latitude = b_latitude;
		this.a_x = a_x;
		this.a_y = a_y;
		this.b_x = b_x;
		this.b_y = b_y;
		this.length = length;
		A = a;
		B = b;
		C = c;
		this.carnum = carnum;
		this.detail = detail;
	}

	private Integer id;
	private String name;
	private Double a_longitude;
	private Double a_latitude;
	private Double b_longitude;
	private Double b_latitude;
	private Integer a_x;
	private Integer a_y;
	private Integer b_x;
	private Integer b_y;
	private Integer length;
	private Double A;
	private Double B;
	private Double C;
	private Integer carnum;
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

	public Double getA_longitude() {
		return a_longitude;
	}

	public void setA_longitude(Double a_longitude) {
		this.a_longitude = a_longitude;
	}

	public Double getA_latitude() {
		return a_latitude;
	}

	public void setA_latitude(Double a_latitude) {
		this.a_latitude = a_latitude;
	}

	public Double getB_longitude() {
		return b_longitude;
	}

	public void setB_longitude(Double b_longitude) {
		this.b_longitude = b_longitude;
	}

	public Double getB_latitude() {
		return b_latitude;
	}

	public void setB_latitude(Double b_latitude) {
		this.b_latitude = b_latitude;
	}

	public Integer getA_x() {
		return a_x;
	}

	public void setA_x(Integer a_x) {
		this.a_x = a_x;
	}

	public Integer getA_y() {
		return a_y;
	}

	public void setA_y(Integer a_y) {
		this.a_y = a_y;
	}

	public Integer getB_x() {
		return b_x;
	}

	public void setB_x(Integer b_x) {
		this.b_x = b_x;
	}

	public Integer getB_y() {
		return b_y;
	}

	public void setB_y(Integer b_y) {
		this.b_y = b_y;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Double getA() {
		return A;
	}

	public void setA(Double a) {
		A = a;
	}

	public Double getB() {
		return B;
	}

	public void setB(Double b) {
		B = b;
	}

	public Double getC() {
		return C;
	}

	public void setC(Double c) {
		C = c;
	}

	public Integer getCarnum() {
		return carnum;
	}

	public void setCarnum(Integer carnum) {
		this.carnum = carnum;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

}