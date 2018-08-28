package imd0412.parkinglot.calculator;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import imd0412.parkinglot.ParkingLotType;

@RunWith(Parameterized.class)
public class CalculatorTest
{
	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[][] { { "2018.01.01 01:00", "2018.01.01 01:01", ParkingLotType.ShortTerm, 8.0f }, //1
								 { "2018.01.01 01:00", "2018.01.01 01:59", ParkingLotType.ShortTerm, 8.0f }, //1
								 { "2018.01.01 01:00", "2018.01.01 02:00", ParkingLotType.ShortTerm, 10.0f }, //2
								 { "2018.01.01 01:00", "2018.01.01 02:59", ParkingLotType.ShortTerm, 10.0f }, //3
								 { "2018.01.01 01:00", "2018.01.01 03:00", ParkingLotType.ShortTerm, 12.0f }, //4
								 { "2018.01.01 00:00", "2018.01.01 23:59", ParkingLotType.ShortTerm, 54.0f }, //5
								 { "2018.01.01 00:00", "2018.01.02 00:00", ParkingLotType.ShortTerm, 106.0f }, //6
								 { "2018.01.01 00:00", "2018.01.02 23:59", ParkingLotType.ShortTerm, 152.0f }, //7
								 { "2018.01.01 00:00", "2018.01.03 00:00", ParkingLotType.ShortTerm, 204.0f }, //8
								 { "2018.01.01 00:00", "2018.01.08 23:59", ParkingLotType.ShortTerm, 740.0f }, //9
								 { "2018.01.01 00:00", "2018.01.09 00:00", ParkingLotType.ShortTerm, 772.0f }, //10
								 { "2018.01.01 00:00", "2018.01.01 23:59", ParkingLotType.LongTerm, 70.0f }, //11
								 { "2018.01.01 00:00", "2018.01.02 00:00", ParkingLotType.LongTerm, 120.0f }, //12
								 { "2018.01.01 00:00", "2018.01.02 23:59", ParkingLotType.LongTerm, 120.0f }, //13
								 { "2018.01.01 00:00", "2018.01.03 00:00", ParkingLotType.LongTerm, 170.0f }, //14
								 { "2018.01.01 00:00", "2018.01.08 23:59", ParkingLotType.LongTerm, 420.0f }, //15
								 { "2018.01.01 00:00", "2018.01.09 00:00", ParkingLotType.LongTerm, 450.0f }, //16
								 { "2018.01.01 00:00", "2018.01.30 23:59", ParkingLotType.LongTerm, 1080.0f }, //17
								 { "2018.01.01 00:00", "2018.01.31 00:00", ParkingLotType.LongTerm, 1610.0f }, //18
								 { "2018.01.01 00:00", "2018.03.02 00:00", ParkingLotType.LongTerm, 3010.0f }, //19
								 { "2018.01.01 00:00", "2018.01.07 23:59", ParkingLotType.VIP, 500.0f }, //20
								 { "2018.01.01 00:00", "2018.01.08 00:00", ParkingLotType.VIP, 600.0f }, //21
								 { "2018.01.01 00:00", "2018.01.14 23:59", ParkingLotType.VIP, 1200.0f }, //22
								 { "2018.01.01 00:00", "2018.01.15 00:00", ParkingLotType.VIP, 1280.0f }, //23
								 });
	}

	@Parameter(0)
	public String checkin;
	@Parameter(1)
	public String checkout;
	@Parameter(2)
	public ParkingLotType type;
	@Parameter(3)
	public Float expectedTotal;

	@Test
	public void testDefineType() throws Exception {
		Float returnedTotal = new Calculator().calculateParkingCost(checkin, checkout, type);
		assertEquals(expectedTotal, returnedTotal);
	}
}
