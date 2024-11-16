package rustamscode.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rustamscode.model.AircraftType;
import rustamscode.model.FlightRecord;
import rustamscode.model.FlightSpecialist;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class FileDataReaderTest {

    private FileDataReader fileDataReader;

    @BeforeEach
    void setUp() {
        fileDataReader = new FileDataReader("src\\test\\java\\resources\\test_flight_records.json");
    }

    @Test
    void parseRecordsShouldReturnCorrectFlightRecords() {
        List<FlightRecord> expectedRecords = List.of(
                new FlightRecord(AircraftType.BOEING, "AB123",
                        LocalDateTime.of(2023, 11, 12, 10, 0),
                        LocalDateTime.of(2023, 11, 12, 14, 30),
                        List.of(new FlightSpecialist(1, "John Doe"))),
                new FlightRecord(AircraftType.AIRBUS, "CD456",
                        LocalDateTime.of(2023, 11, 13, 9, 30),
                        LocalDateTime.of(2023, 11, 13, 13, 0),
                        List.of(new FlightSpecialist(2, "Jane Smith")))
        );

        List<FlightRecord> actualRecords = fileDataReader.parseRecords();

        assertNotNull(actualRecords);
        assertEquals(expectedRecords.size(), actualRecords.size());
        assertEquals(expectedRecords, actualRecords);
    }

    @Test
    void parseRecordShouldThrowRuntimeExceptionOnInvalidFile() {
        fileDataReader = new FileDataReader("src\\test\\java\\resources\\invalid_file.json");

        assertThrows(RuntimeException.class, fileDataReader::parseRecords);
    }

}
