package rustamscode.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Month;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyFlightTime {
    Month month;
    Long totalFlightHours;
    boolean hasMonthOverLimit;
}
