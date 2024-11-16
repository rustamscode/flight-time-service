package rustamscode.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import rustamscode.model.FlightSpecialist;
import rustamscode.model.MonthlyFlightTime;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class OutputFileGenerator {
    FlightTimeCalculator flightTimeCalculator;

    public void generateOutput(String filePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

        Map<FlightSpecialist, List<MonthlyFlightTime>> data = flightTimeCalculator.calculate();
        mapper.writeValue(new File(filePath), data);

        log.info("Данные о летных часах были успешно записаны!");
    }
}
