package rustamscode.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FlightSpecialist {
    String id;
    String name;
    List<FlightRecord> flights;
}
