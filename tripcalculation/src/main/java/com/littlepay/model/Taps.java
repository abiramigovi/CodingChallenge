package com.littlepay.model;

import java.time.LocalDateTime;

public class Taps {
	
	private long id;
	private LocalDateTime startTime;
	private TapTypes tapType;
	private String stopId;
	private String comapnyId;
	private String busId;
	private String pan;
	
	
	
	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}



	public LocalDateTime getStartTime() {
		return startTime;
	}



	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}



	public TapTypes getTapType() {
		return tapType;
	}



	public void setTapType(TapTypes tapType) {
		this.tapType = tapType;
	}



	public String getStopId() {
		return stopId;
	}



	public void setStopId(String stopId) {
		this.stopId = stopId;
	}



	public String getComapnyId() {
		return comapnyId;
	}



	public void setComapnyId(String comapnyId) {
		this.comapnyId = comapnyId;
	}



	public String getBusId() {
		return busId;
	}



	public void setBusId(String busId) {
		this.busId = busId;
	}



	public String getPan() {
		return pan;
	}



	public void setPan(String pan) {
		this.pan = pan;
	}



	public enum TapTypes{
		ON,OFF
	}

}
