package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "markAtt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks attendance of all students in the "
                                               + "specified class with index numbers matching the user input\n"
                                               + "Parameters: d/DATE (DD/MM/YYYY) "
                                               + "c/CLASS (only one class should be entered)"
                                               + "ind/INDEX_NUMBERS (index numbers of students, separated by spaces)\n"
                                               + "Example: " + COMMAND_WORD + " d/26/10/2022 c/1A ind/1 5 8 10\n"
                                               + "Example: " + COMMAND_WORD + " d/05/08/2022 c/1B ind/4 7 9 10";

    private static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Attendance for students have been marked.";
    private static final String MESSAGE_MARK_ATTENDANCE_NONE = "No student's attendance has been marked.";

    private String date;
    private String studentClass;
    private List<String> indexNumbers;

    public MarkAttendanceCommand(String date, String studentClass, List<String> indexNumbers) {
        this.date = date;
        this.studentClass = studentClass;
        this.indexNumbers = indexNumbers;
    }

    public CommandResult execute(Model model) {
        boolean anyStudentsAttendanceMarked = false;
        String markedAttendance = "";
        requireNonNull(model);
        // Now we're iterating through the whole list of saved people in Watsons and checking against their
        // indexNumber and studentClass to check if they should be marked attendance. Not the most efficient.

        //Predicate<Person> personIsInClassPredicate = model.createPersonIsInClassPredicate(studentClass);
        //model.updateFilteredPersonList(personIsInClassPredicate);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        List<Person> latestList = model.getFilteredPersonList();
        for (Person person: latestList) {
            if (indexNumbers.stream().anyMatch(ind -> ind.equals(person.getIndexNumberValue()))) {
                if (person.getStudentClass().isSameClass(studentClass)) {
                    person.getAttendance().updateAttendance(date + " 1");
                    anyStudentsAttendanceMarked = true;
                    markedAttendance = markedAttendance + person.getName().toString()
                                       + " " + person.getAttendance().toString() + ", ";
                }
            }
        }
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        return (anyStudentsAttendanceMarked)
               ? new CommandResult(MESSAGE_MARK_ATTENDANCE_SUCCESS + " " + markedAttendance)
               : new CommandResult(MESSAGE_MARK_ATTENDANCE_NONE);
    }
}
