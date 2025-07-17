package com.littlepay;

import java.util.List;

import com.littlepay.model.Trips;
import com.littlepay.service.TripCalculator;

public class MainApplication {

	private static final String INPUT_FILE = "taps.csv";

	public static void main(String[] args) {


	TripCalculator tripCalculator=new TripCalculator();
	List<Trips> trips=tripCalculator.calculateTrips(INPUT_FILE);
	//trips.stream().map(p->p.getPan()+"---"+p.getChargedAmount()+"-"+p.getStatus()+"-"+p.getFromStopId()+"-"+p.getToStopId()+"-"+p.getStartedTime()+"="+p.getFinishedTime()+"-"+p.getRemarks()).forEach(System.out::println);
	

	}

}
