package com.littlepay.model;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CostModel {
	
	public static Map<Route,BigDecimal> tripCost=new HashMap<>();
	public static Set<String> stops=new HashSet<>();

	
	static {
		
		stops.add("STOP1");
		stops.add("STOP2");
		stops.add("STOP3");

		
		tripCost.put(new Route("STOP1","STOP2"), new BigDecimal("3.25"));
		tripCost.put(new Route("STOP2","STOP3"), new BigDecimal("5.50"));
		tripCost.put(new Route("STOP1","STOP3"), new BigDecimal("7.30"));

		
		
	}
	
	public static Map<Route, BigDecimal> getTripCosts() {
        return Collections.unmodifiableMap(tripCost);
    }

    public static BigDecimal getCost(String from, String to) {
        return tripCost.getOrDefault(new Route(from, to), BigDecimal.ZERO);
    }
    public static BigDecimal getMaxCostforIncompleteTrip(String from) {
    	
    	Set<String> allOtherStops =new HashSet<>(stops);
    	allOtherStops.remove(from);
    	BigDecimal maxCharge=BigDecimal.ZERO;
    	
    	for(String s:allOtherStops) {
    		maxCharge=maxCharge.max(getCost(from, s));
    	}
    	
        return maxCharge;
    }
    

}
