package imd0412.parkinglot.calculator;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import imd0412.parkinglot.ParkingLotType;
import imd0412.parkinglot.exception.DateFormatException;
import imd0412.parkinglot.exception.InvalidDataException;

@RunWith(Parameterized.class)
public class CalculatorExceptionTest {
	
	@Rule
    public ExpectedException thrown = ExpectedException.none();
	
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[][] { { "2018/01/01 01:00", "2018.01.01 01:01", ParkingLotType.ShortTerm, DateFormatException.class },
								 { "2018.01.01 01:00", "2018/01/01 01:59", ParkingLotType.LongTerm, DateFormatException.class },
								 { "01.01.2018 01:00", "2018.01.01 02:00", ParkingLotType.LongTerm, DateFormatException.class},
								 { "2018.01.01 01:00", "01.01.2018 02:59", ParkingLotType.ShortTerm, DateFormatException.class},
								 { "2018.01.01 03:00", "2018.01.01 01:00", ParkingLotType.ShortTerm, InvalidDataException.class },
								 { "2018.01.02 00:00", "2018.01.01 23:59", ParkingLotType.ShortTerm, InvalidDataException.class },
								 { "2018.02.01 00:00", "2018.01.02 00:00", ParkingLotType.ShortTerm, InvalidDataException.class },
								 { "2018.01.01 00:00", "2017.01.02 23:59", ParkingLotType.ShortTerm, InvalidDataException.class },
								 { "2018.02.29 00:00", "2018.05.03 00:00", ParkingLotType.ShortTerm, InvalidDataException.class},
								 { "2018.01.01 00:00", "2018.11.31 23:59", ParkingLotType.ShortTerm, InvalidDataException.class },
								 { "2018.01.01 00:00", "2018.01.32 00:00", ParkingLotType.ShortTerm, InvalidDataException.class },
								 { "2018.01.01 00:00", "2018.13.01 23:59", ParkingLotType.LongTerm, InvalidDataException.class },
								 { "2018.01.01 00:00", "2020.01.02 00:00", ParkingLotType.LongTerm, InvalidDataException.class },
								 { "1969.01.01 00:00", "2018.01.02 23:59", ParkingLotType.LongTerm, InvalidDataException.class },
								 { "2018.00.01 00:00", "2018.01.03 00:00", ParkingLotType.LongTerm, InvalidDataException.class },
								 { "2018.01.00 00:00", "2018.01.07 23:59", ParkingLotType.LongTerm, InvalidDataException.class },
								 { "2019.01.01 00:00", "2018.01.08 00:00", ParkingLotType.LongTerm, InvalidDataException.class },
								 { "2018.01.01 00:00", "1969.01.30 23:59", ParkingLotType.LongTerm, InvalidDataException.class },
								 { "2018.01.01 00:00", "2018.01.31 00:00", null, NullPointerException.class },
								 { "2018.01.01 00:00", null, ParkingLotType.LongTerm, NullPointerException.class },
								 { null, "2018.01.07 23:59", ParkingLotType.VIP, NullPointerException.class },
								 });
	}

	@Parameter(0)
	public String checkin;
	@Parameter(1)
	public String checkout;
	@Parameter(2)
	public ParkingLotType type;
	@Parameter(3)
	public Class<? extends Exception> expectedException;

	@Test
	public void testDefineType() throws Exception {
		thrown.expect(expectedException);
		new Calculator().calculateParkingCost(checkin, checkout, type);
	}
}
