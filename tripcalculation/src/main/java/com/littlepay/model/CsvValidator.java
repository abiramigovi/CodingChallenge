package com.littlepay.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CsvValidator {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
	        .ofPattern("dd-MM-yyyy HH:mm:ss");

	    public static boolean isParsableDate(String dateStr) {
	        try {
	            LocalDateTime.parse(dateStr, DATE_FORMATTER);
	            return true;
	        } catch (Exception e) {
	            return false;
	        }
	    }
	    public static boolean isValidDate(String dateStr) {
	        return isNotBlank(dateStr) && isParsableDate(dateStr);
	    }

	    public static boolean isValidTapType(String status) {
	        return isNotBlank(status) && (status.equalsIgnoreCase("ON") || status.equalsIgnoreCase("OFF"));
	    }

	   

	    public static boolean isValidPan(String cardNumber) {
	        return isNotBlank(cardNumber) && cardNumber.matches("\\d{13,19}");
	    }
	    public static boolean isNotBlank(String value) {
	        return value != null && !value.trim().isEmpty();
	    }
}
