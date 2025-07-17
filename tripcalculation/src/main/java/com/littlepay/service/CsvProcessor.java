package com.littlepay.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.littlepay.model.CsvValidator;
import com.littlepay.model.Taps;
import com.littlepay.model.Taps.TapTypes;
import com.littlepay.model.Trips;

public class CsvProcessor {
	
	private List<Taps> tapData;

	
	public List<Taps> getTapData() {
		return tapData;
	}

	public void setTapData(List<Taps> tripData) {
		this.tapData = tripData;
	}
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	public List<Taps> readCsv(String fileName) {
		
	    List<String> invalidRecords = new ArrayList<>();

		
		List<Taps> taps=new ArrayList<>();
		
		
		 try  {
			 
			 InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
			 if(is==null) {
				  System.err.println("Error: CSV file not found at path: " + fileName);
			 }else {
			 BufferedReader br = new BufferedReader(new InputStreamReader(is));
	            String line;
	            boolean isHeader = true;

	            while ((line = br.readLine()) != null) {
	                if (isHeader) {
	                    isHeader = false;
	                    continue; // skip header
	                }

	                String[] fields = line.split(",");
	                if (fields.length < 7) {
	                    invalidRecords.add("Too few fields: " + line);
	                    continue;
	                }
	                String id     = fields[0].trim();
	                String dateTime      = fields[1].trim();
	                String tapType        = fields[2].trim();
	                String stopId   = fields[3].trim();
	                String companyId  = fields[4].trim();
	                String busId  = fields[5].trim();
	                String pan  = fields[6].trim();
	                
	                boolean isValid = CsvValidator.isNotBlank(id)
	                &&CsvValidator.isValidDate(dateTime)
	                        && CsvValidator.isValidTapType(tapType)
	                        && CsvValidator.isNotBlank(stopId)
	                        && CsvValidator.isNotBlank(companyId)
	                        && CsvValidator.isNotBlank(busId)
	                        && CsvValidator.isValidPan(pan);
	                if(isValid) {
	                	Taps tap=new Taps();
	                    tap.setId(Integer.parseInt(fields[0].trim()));
	                    tap.setStartTime(LocalDateTime.parse(fields[1].trim(), formatter));
	                    tap.setTapType(TapTypes.valueOf(fields[2].trim()));
	                    tap.setStopId(fields[3].trim());
	                    tap.setComapnyId(fields[4].trim());
	                    tap.setBusId(fields[5].trim());
	                    tap.setPan(fields[6].trim());
	                    taps.add(tap);
	                }else {
	                	 invalidRecords.add("Invalid record: " + line);
	                     continue;
	                }
	                  
	                
	            }
	            if(!invalidRecords.isEmpty()) {
	            	System.out.println("\nInvalid Records:");
		            invalidRecords.forEach(System.out::println);
	            }
			 }
	            

	        } catch (FileNotFoundException e) {
	            System.err.println("Error CSV file not found at path: " + fileName);
	           
	        } catch (IOException e) {
	            System.err.println(" Error reading file: " + e.getMessage());
	        }catch (Exception e) {
	            e.printStackTrace();
	        }
		 return taps;
		
    }
	
	

    public void writeCsv(String fileName, List<Trips> data) {
    	
    	if(data!=null&&!data.isEmpty()) {
    	
    	try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            writer.println("Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID,PAN, Status,Remarks"); // header

            for (Trips trip : data) {
                writer.println(String.join(",", trip.toCsvRow()));
            }

            System.out.println("CSV file written: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    	}
    }

}
