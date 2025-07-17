package tripcalculation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.littlepay.model.CsvValidator;
import com.littlepay.model.Taps.TapTypes;

class CsvValidatorTest {

	@Test
	void testIsParsableDate() {
        assertTrue(CsvValidator.isParsableDate("22-01-2023 13:00:00"));
        assertFalse(CsvValidator.isParsableDate("22-01-2023 25:00:00")); 

	}

	@Test
	void testIsValidDate() {
		assertTrue(CsvValidator.isValidDate("22-01-2023 13:00:00"));
        assertFalse(CsvValidator.isValidDate("22-01-2023 25:00:00")); 
        assertFalse(CsvValidator.isValidDate(""));                    
        assertFalse(CsvValidator.isValidDate(null));
	}

	@Test
	void testIsValidTapType() {
		assertTrue(CsvValidator.isValidTapType("ON"));
		assertFalse(CsvValidator.isValidTapType("ONE"));


	}

	@Test
	void testIsValidPan() {
		assertTrue(CsvValidator.isValidPan("34343434343434"));
		assertFalse(CsvValidator.isValidPan("34343434##43434"));


	}

	

}
