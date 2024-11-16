package rustamscode.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import rustamscode.model.FlightRecord;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class FileDataReader {

    String filePath;

    public List<FlightRecord> parseRecords() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        List<FlightRecord> records;

        try {
            records = mapper.readValue(new File(filePath), new TypeReference<List<FlightRecord>>() {
            });
        } catch (IOException e) {
            log.info("Возникла проблема при парсинге информации о полете: {}", e.getMessage());
            throw new RuntimeException(e);
        }

        return records;
    }
}
