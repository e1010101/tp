package seedu.address.model.person.subject;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void isValidAttendanceInput() {
        // null name
        assertThrows(NullPointerException.class, () -> Attendance.isValidAttendance(null));

        // invalid name
        assertFalse(Attendance.isValidAttendance("")); // empty string
        assertFalse(Attendance.isValidAttendance("13/2/2022 1")); // no prefixes
        assertFalse(Attendance.isValidAttendance("date/13/2/2022 1")); // no "attendance/" prefix
        assertFalse(Attendance.isValidAttendance("attendance/13/2/2022 date/1")); // swapped prefixes
        assertFalse(Attendance.isValidAttendance("date/13/2 attendance/1")); // invalid date

        // valid name
    }
}
