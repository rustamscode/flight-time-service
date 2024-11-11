package rustamscode.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Month;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyFlightTime {
    Month month;
    Long totalFlightHours;
    boolean hasMonthlyOverLimit;
    boolean hasWeeklyOverLimit;
    boolean hasDailyOverLimit;

}
