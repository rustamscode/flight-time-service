package rustamscode.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rustamscode.model.AircraftType;
import rustamscode.model.FlightRecord;
import rustamscode.model.FlightSpecialist;
import rustamscode.model.MonthlyFlightTime;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Month;

class FlightTimeCalculatorTest {

    private FlightTimeCalculator flightTimeCalculator;

    @BeforeEach
    void setUp() {
        FileDataReader mockFileDataReader = new FileDataReader("test_path") {
            @Override
            public List<FlightRecord> parseRecords() {
                FlightSpecialist specialist1 = new FlightSpecialist(1, "John Doe");
                FlightSpecialist specialist2 = new FlightSpecialist(2, "Jane Smith");

                FlightRecord flight1 = new FlightRecord(
                        AircraftType.BOEING,
                        "AB123",
                        LocalDateTime.of(2023, 11, 1, 10, 0),
                        LocalDateTime.of(2023, 11, 1, 14, 0), // 4 часа
                        List.of(specialist1)
                );

                FlightRecord flight2 = new FlightRecord(
                        AircraftType.BOEING,
                        "CD456",
                        LocalDateTime.of(2023, 11, 2, 9, 0),
                        LocalDateTime.of(2023, 11, 2, 11, 30), // 2.5 часа
                        List.of(specialist1, specialist2)
                );

                return List.of(flight1, flight2);
            }
        };

        flightTimeCalculator = new FlightTimeCalculator(mockFileDataReader);
    }

    @Test
    void calculate_shouldReturnCorrectData() {
        Map<FlightSpecialist, List<MonthlyFlightTime>> result = flightTimeCalculator.calculate();

        assertNotNull(result);
        assertEquals(2, result.size());

        FlightSpecialist specialist1 = new FlightSpecialist(1, "John Doe");
        List<MonthlyFlightTime> specialist1Times = result.get(specialist1);
        assertEquals(1, specialist1Times.size());
        MonthlyFlightTime novemberTime1 = specialist1Times.get(0);
        assertEquals(Month.NOVEMBER, novemberTime1.getMonth());
        assertEquals(390, novemberTime1.getTotalFlightHours());

        FlightSpecialist specialist2 = new FlightSpecialist(2, "Jane Smith");
        List<MonthlyFlightTime> specialist2Times = result.get(specialist2);
        assertEquals(1, specialist2Times.size());
        MonthlyFlightTime novemberTime2 = specialist2Times.get(0);
        assertEquals(Month.NOVEMBER, novemberTime2.getMonth());
        assertEquals(150, novemberTime2.getTotalFlightHours()); // 2.5 часа = 150 минут
    }

    @Test
    void calculate_shouldReturnEmptyMapForNoFlights() {
        flightTimeCalculator = new FlightTimeCalculator(new FileDataReader("src\\test\\java\\resources\\empty_map.json"));

        Map<FlightSpecialist, List<MonthlyFlightTime>> result = flightTimeCalculator.calculate();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}


