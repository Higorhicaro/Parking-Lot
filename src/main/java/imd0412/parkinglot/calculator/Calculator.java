package imd0412.parkinglot.calculator;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.zip.DataFormatException;

import imd0412.parkinglot.Constants;
import imd0412.parkinglot.ParkingLotType;
import imd0412.parkinglot.exception.DateFormatException;
import imd0412.parkinglot.exception.InvalidDataException;
import imd0412.parkinglot.exception.InvalidDataType;

public class Calculator {
	/**
	 * Calculates the staying cost in the parking lot.
	 * 
	 * @param checkin
	 *            String representing check-in date. String follows the format
	 *            "yyyy.MM.dd HH:mm".
	 * @param checkout
	 *            String representing check-out date. String follows the format
	 *            "yyyy.MM.dd HH:mm".
	 * @param type
	 * @return
	 * @throws InvalidDataException 
	 */
	Float calculateParkingCost(String checkin, String checkout,
			ParkingLotType type) throws Exception {
		LocalDateTime checkinTime;
		LocalDateTime checkoutTime;
		
		try {
			checkinTime = LocalDateTime.parse(checkin, Constants.DATE_FORMATTER);
			checkoutTime = LocalDateTime.parse(checkout, Constants.DATE_FORMATTER);
		} catch (DateTimeException excep){
			throw new DateFormatException();
		}
		LocalDateTime dataMin = LocalDateTime.parse("1970.01.01 00:00", Constants.DATE_FORMATTER);
		LocalDateTime dataMaxIn = LocalDateTime.parse("2018.12.31 23:59", Constants.DATE_FORMATTER);
		LocalDateTime dataMaxOut = LocalDateTime.parse("2019.12.31 23:59", Constants.DATE_FORMATTER);
		
		
		// Extrair dados do objeto data
		int year = checkinTime.getYear();
		int month = checkinTime.getMonth().getValue();
		int dayOfMonth = checkinTime.getDayOfMonth();
		int hour = checkinTime.getHour();
		int minute = checkinTime.getMinute();
		
		int year2 = checkoutTime.getYear();
		int month2 = checkoutTime.getMonth().getValue();
		int dayOfMonth2 = checkoutTime.getDayOfMonth();
		int hour2 = checkoutTime.getHour();
		int minute2 = checkoutTime.getMinute();
		
		
		if(dayOfMonth > 31 || dayOfMonth < 0 || dayOfMonth2 >31 || dayOfMonth2 < 0) {
			throw new InvalidDataException("Dia errado", InvalidDataType.InvalidDay);
		}
		
		if(month == 4 || month == 6 || month == 9 || month == 11) {
			if(dayOfMonth == 31) {
				throw new InvalidDataException("Dia errado", InvalidDataType.InvalidDay);
			}
		}
		
		if(month2 == 4 || month2 == 6 || month2 == 9 || month2 == 11) {
			if(dayOfMonth2 == 31) {
				throw new InvalidDataException("Dia errado", InvalidDataType.InvalidDay);
			}
		}
		
		if(month == 2) {
			if(year % 4 != 0) {
				if(dayOfMonth > 28) {
					throw new InvalidDataException("Dia errado", InvalidDataType.InvalidDay);
				}
			} else {
				if(dayOfMonth > 29) {
					throw new InvalidDataException("Dia errado", InvalidDataType.InvalidDay);
				}
			}
		}
		
		if(month2 == 2) {
			if(year2 % 4 != 0) {
				if(dayOfMonth2 > 28) {
					throw new InvalidDataException("Dia errado", InvalidDataType.InvalidDay);
				}
			} else {
				if(dayOfMonth2 > 29) {
					throw new InvalidDataException("Dia errado", InvalidDataType.InvalidDay);
				}
			}
		}
		
		if(month > 12 || month < 1 || month2 > 12 || month2 < 1) {
			throw new InvalidDataException("Mes errado", InvalidDataType.InvalidMonth);
		} 
		
		if(Duration.between(dataMin, checkinTime).toMinutes() < 0 || Duration.between(dataMin, checkoutTime).toMinutes() < 0) {
			throw new InvalidDataException("Data inferior ao limite", InvalidDataType.NonexistentDate);
		}
		
		if(Duration.between(dataMaxIn, checkinTime).toMinutes() > 0 || Duration.between(dataMaxOut, checkoutTime).toMinutes() > 0) {
			throw new InvalidDataException("Data superior ao limite", InvalidDataType.NonexistentDate);
		}
			
		Float valorFinal = 0.0f;
		
		// Calcular a diferença entre dois objetos data
		Duration duration = Duration.between(checkinTime, checkoutTime);
		long days = duration.toDays();
		long hours = duration.toHours();
		long minutes = duration.toMinutes();
		System.out.printf("Dias: %d - Horas: %d \n", days, hours);
			
		if(type == ParkingLotType.ShortTerm) {
			valorFinal = 8.0f;
			if(hours > 0) {
				valorFinal = valorFinal + ((hours) * 2);
				if(days > 0 && days < 7) {
					valorFinal = valorFinal + (50 * (days));
				} else if (days >= 7) {
					valorFinal = valorFinal + 350 + (30 * (days - 7));
				}
			}
		} else if(type == ParkingLotType.LongTerm) {
			valorFinal = 70.0f;
			if(days > 0 && days < 7) {
				valorFinal = valorFinal + (50 * days);
			} else if (days >= 7) {
				valorFinal = valorFinal + 350 + (30 * (days - 7));
				if(days >= 30) {
					valorFinal = valorFinal + (500 * (days / 30));
				}
			}
		} else {
			valorFinal = 500.0f;
			if(days >= 7 && days < 14) {
				valorFinal = valorFinal + ((days - 6) * 100 );
			} else if (days >= 14) {
				valorFinal = valorFinal + 700 + ((days - 13) * 80);
			}
		}
		
		
		return valorFinal;
		
		
	}
}
