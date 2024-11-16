package rustamscode.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightRecord {
    AircraftType aircraftType;
    String airCraftNumber;
    LocalDateTime departureTime;
    LocalDateTime arrivalTime;
    List<FlightSpecialist> crew;
}
