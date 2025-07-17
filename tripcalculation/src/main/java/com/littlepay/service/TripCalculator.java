package com.littlepay.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.littlepay.model.CostModel;
import com.littlepay.model.Route;
import com.littlepay.model.Taps;
import com.littlepay.model.Taps.TapTypes;
import com.littlepay.model.Trips;
import com.littlepay.model.Trips.TripStatus;

public class TripCalculator {
		
	
	private static final int MAX_TRAVEL_HOUR = 4;


	public List<Trips> calculateTrips(String fileName) {
		
		List<Trips> tripData=new ArrayList<>();
		CsvProcessor csvprocessor = new CsvProcessor();
		List<Taps> taps = csvprocessor.readCsv(fileName);
		
		if(taps!=null&&!taps.isEmpty()) {
		
		Map<String,List<Taps>> groupedRecords=taps.stream().collect(Collectors.groupingBy(t->t.getPan()+"|"+t.getBusId()+"|"+t.getComapnyId()));
		List<Taps> inValidTaps=new ArrayList<>();
		for(List<Taps> tapRecords:groupedRecords.values()) {
			Taps startTap=null;
			Taps endTap=null;
			
			List<Taps> sortedTaps=tapRecords.stream().sorted(Comparator.comparing(p->p.getStartTime())).collect(Collectors.toList());

			inValidTaps=new ArrayList<>();
			for(Taps t:sortedTaps){
				
				
				if(t.getTapType().equals(TapTypes.ON)) {
					if(startTap==null) {
						startTap=t;
					}else {
						System.out.println("Invalid two ONS");
						inValidTaps.add(startTap);
						startTap=t;
					}

				}else if(t.getTapType().equals(TapTypes.OFF)) {
					if(endTap==null) {
						endTap=t;
					}else {
						System.out.println("Invalid two OFFS");
						inValidTaps.add(endTap);
						endTap=t;
					}

				}
				
				if(startTap!=null&&endTap!=null) {
					Duration diff = Duration.between(startTap.getStartTime(), endTap.getStartTime());

					if(endTap.getStartTime().isAfter(startTap.getStartTime())&&diff.toHours()<=MAX_TRAVEL_HOUR){
						
						Trips trip=new Trips();
						long duration=diff.abs().getSeconds();
						String stopId1=startTap.getStopId().toUpperCase();
						String stopId2=endTap.getStopId().toUpperCase();
						

						BigDecimal amount=CostModel.getCost(stopId1,stopId2);
						setValuesforTrip(startTap, endTap, trip, duration, amount);
						if(!stopId1.equalsIgnoreCase(stopId2))
							trip.setStatus(TripStatus.COMPLETED);
						else if(stopId1.equalsIgnoreCase(stopId2)&&amount==BigDecimal.ZERO)
							trip.setStatus(TripStatus.CANCELLED);
						System.out.println("added-complete/cancelled");
						tripData.add(trip);
						
						
					}else {
						inValidTaps.add(startTap);
						inValidTaps.add(endTap);

					}
					startTap=null;
					endTap=null;
				}
				
			}
			
			if((startTap!=null&&endTap==null)||(startTap==null&&endTap!=null)) {
				TapTypes tapType=startTap!=null?startTap.getTapType():endTap.getTapType();
				
				
					Trips trip=new Trips();
					String stopId=startTap!=null?startTap.getStopId():endTap.getStopId();
					setValuesForTripForIncomplete(startTap, endTap, trip, stopId);
					setAmountForIncomplete(tapType, trip, stopId);
					System.out.println("added-incomplete -either one on /off");

					tripData.add(trip);
				
			}
			for(Taps t:inValidTaps) {
				
				Trips trip=new Trips();
				String stopId=t.getStopId();
				trip.setBusId(t.getBusId());
				trip.setComapnyId(t.getComapnyId());
				trip.setPan(t.getPan());
				trip.setDuration(0);
				trip.setStartedTime(t.getStartTime());
				trip.setFinishedTime(t.getStartTime());

				trip.setFromStopId(stopId);
				trip.setToStopId(stopId);
				setAmountForIncomplete(t.getTapType(), trip, stopId);
				System.out.println("added-invalid");

				tripData.add(trip);
			}
			
		}
		if(tripData!=null&&!tripData.isEmpty()) {
			List<Trips> sortedTripData=tripData.stream().sorted(Comparator.comparing(p->p.getStartedTime())).collect(Collectors.toList());
			csvprocessor.writeCsv("Trips.csv", sortedTripData);
			return sortedTripData;
		}
		}
		
		return tripData;
		
		
	}


	private void setAmountForIncomplete(TapTypes tapType, Trips trip, String stopId) {
		if(tapType.equals(TapTypes.ON)) {
		BigDecimal amount=CostModel.getMaxCostforIncompleteTrip(stopId.toUpperCase());

		trip.setChargedAmount(amount);
		
		trip.setStatus(TripStatus.INCOMPLETE);
		trip.setRemarks("Only tap ON record exists");

		}else {
			trip.setChargedAmount(BigDecimal.ZERO);
			
			trip.setStatus(TripStatus.INCOMPLETE);
			trip.setRemarks("Only tap OFF record exists");
		}
	}


	private void setValuesForTripForIncomplete(Taps startTap, Taps endTap, Trips trip, String stopId) {
		trip.setBusId(startTap!=null?startTap.getBusId():endTap.getBusId());
		trip.setComapnyId(startTap!=null?startTap.getComapnyId():endTap.getComapnyId());
		trip.setPan(startTap!=null?startTap.getPan():endTap.getPan());
		trip.setDuration(0);
		trip.setStartedTime(startTap!=null?startTap.getStartTime():endTap.getStartTime());
		trip.setFinishedTime(endTap!=null?endTap.getStartTime():startTap.getStartTime());
		trip.setFromStopId(stopId);
		trip.setToStopId(stopId);
	}


	private void setValuesforTrip(Taps startTap, Taps endTap, Trips trip, long duration, BigDecimal amount) {
		trip.setBusId(startTap.getBusId());
		trip.setComapnyId(startTap.getComapnyId());
		trip.setPan(startTap.getPan());
		trip.setDuration(duration);
		trip.setStartedTime(startTap.getStartTime());
		trip.setFinishedTime(endTap.getStartTime());
		trip.setFromStopId(startTap.getStopId());
		trip.setToStopId(endTap.getStopId());
		trip.setChargedAmount(amount);
	}

}
