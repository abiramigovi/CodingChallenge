package com.littlepay.model;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Trips {
	
	private static final String EMPTY_STRING = "";
	private  LocalDateTime startedTime;
	private  LocalDateTime finishedTime;
	private long duration;
	private String fromStopId;
	private String toStopId;
	private BigDecimal chargedAmount;
	private String companyId;
	private String busId;
	private String pan;
	private TripStatus status;
	private String remarks=EMPTY_STRING;
	
	public LocalDateTime getStartedTime() {
		return startedTime;
	}

	public void setStartedTime(LocalDateTime startedTime) {
		this.startedTime = startedTime;
	}

	public LocalDateTime getFinishedTime() {
		return finishedTime;
	}

	public void setFinishedTime(LocalDateTime finishedTime) {
		this.finishedTime = finishedTime;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getFromStopId() {
		return fromStopId;
	}

	public void setFromStopId(String fromStopId) {
		this.fromStopId = fromStopId;
	}

	public String getToStopId() {
		return toStopId;
	}

	public void setToStopId(String toStopId) {
		this.toStopId = toStopId;
	}

	public BigDecimal getChargedAmount() {
		return chargedAmount;
	}

	public void setChargedAmount(BigDecimal chargedAmount) {
		this.chargedAmount = chargedAmount;
	}

	public String getComapnyId() {
		return companyId;
	}

	public void setComapnyId(String comapnyId) {
		this.companyId = comapnyId;
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

	public TripStatus getStatus() {
		return status;
	}

	public void setStatus(TripStatus status) {
		this.status = status;
	}

	public enum TripStatus{
		COMPLETED,INCOMPLETE,CANCELLED
	}

	public String[] toCsvRow() {
		DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        NumberFormat moneyFormatter = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String startTime=null;
        String finishTime=null;
        if(finishedTime!=null)
        	finishTime=String.valueOf(dtFormatter.format(finishedTime));
        if(startedTime!=null)
        	startTime=String.valueOf(dtFormatter.format(startedTime));
        
        return new String[]{startTime,finishTime,
        		String.valueOf(duration),fromStopId,toStopId,String.valueOf(moneyFormatter.format(chargedAmount)),companyId,busId,pan,String.valueOf(status),remarks};
    }

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
