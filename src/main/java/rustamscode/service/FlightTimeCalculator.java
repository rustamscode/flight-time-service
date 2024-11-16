package rustamscode.service;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import rustamscode.model.FlightRecord;
import rustamscode.model.FlightSpecialist;
import rustamscode.model.MonthlyFlightTime;

import java.time.Duration;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightTimeCalculator {
    FileDataReader fileDataReader;

    public Map<FlightSpecialist, List<MonthlyFlightTime>> calculate() {
        List<FlightRecord> flights = fileDataReader.parseRecords();
        Map<FlightSpecialist, List<MonthlyFlightTime>> data = new HashMap<>();

        Map<FlightSpecialist, Map<Month, Long>> specialistMonthMinutes = mapRecordToMonthlyFlightTime(flights);

        for (var entry : specialistMonthMinutes.entrySet()) {
            FlightSpecialist specialist = entry.getKey();

            for (var nestedEntry : entry.getValue().entrySet()) {
                if (!data.containsKey(specialist)) {
                    data.put(specialist, new ArrayList<>());
                }

                MonthlyFlightTime monthlyFlightTime = new MonthlyFlightTime();
                monthlyFlightTime.setMonth(nestedEntry.getKey());
                monthlyFlightTime.setTotalFlightHours(nestedEntry.getValue());
                monthlyFlightTime.setHasMonthOverLimit(nestedEntry.getValue() > 80);

                data.get(specialist).add(monthlyFlightTime);
            }
        }

        return data;
    }

    public Map<FlightSpecialist, Map<Month, Long>> mapRecordToMonthlyFlightTime(List<FlightRecord> flights) {
        Stream<Map.Entry<FlightSpecialist, FlightRecord>> entryStream = flights.stream()
                .flatMap(flight -> flight.getCrew().stream()
                        .map(member -> Map.entry(member, flight)));

        Map<FlightSpecialist, Map<Month, Long>> specialistMonthMinutes = entryStream.
                collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.groupingBy(entry -> entry.getValue().getDepartureTime().getMonth(),
                                Collectors.summingLong(entry -> Duration.between(
                                                entry.getValue().getDepartureTime(), entry.getValue().getArrivalTime())
                                        .toMinutes()))));

        return specialistMonthMinutes;
    }
}
