package seedu.watson.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.watson.commons.core.GuiSettings;
import seedu.watson.model.person.Name;
import seedu.watson.model.person.Person;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    public Predicate<Person> createPersonIsInClassPredicate(String studentClass);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' watson book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' watson book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the Database
     */
    ReadOnlyDatabase getDatabase();

    /**
     * Replaces watson book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyDatabase addressBook);

    /**
     * Returns true if a person with the same identity as {@code person} exists in the watson book.
     */
    boolean hasPerson(Person person);

    Person getPersonByName(Name name);

    /**
     * Deletes the given person.
     * The person must exist in the watson book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the watson book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the watson book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the watson book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getFilteredPersonList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);

    /**
     * Sorts the list of students by grade
     */
    void sortListByGrade(boolean isInAscending, String subjectName);
}
