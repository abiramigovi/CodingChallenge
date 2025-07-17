package tripcalculation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.littlepay.model.Taps;
import com.littlepay.service.CsvProcessor;

class TripCalculatorTest {

	@Test
	void testCalculateTrips() {
		CsvProcessor csvProcessor = new CsvProcessor();
        String fileName = "tapstest1.csv";
        List<Taps> taps = csvProcessor.readCsv(fileName);
        assertFalse(taps.isEmpty(), "Taps should not be empty");
	}
	
	@Test
	void testCalculateTripsforEmptyFile() {
		CsvProcessor csvProcessor = new CsvProcessor();
        String fileName = "taps1.csv";
        List<Taps> taps = csvProcessor.readCsv(fileName);
        assertTrue(taps.isEmpty(), "Taps should not be empty");
	}

}
