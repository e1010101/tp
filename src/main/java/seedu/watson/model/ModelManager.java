package seedu.watson.model;

import static java.util.Objects.requireNonNull;
import static seedu.watson.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.watson.commons.core.GuiSettings;
import seedu.watson.commons.core.LogsCenter;
import seedu.watson.model.person.Name;
import seedu.watson.model.person.Person;

/**
 * Represents the in-memory model of the watson book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final Database database;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;

    /**
     * Initializes a ModelManager with the given database and userPrefs.
     */
    public ModelManager(ReadOnlyDatabase addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with watson book: " + addressBook + " and user prefs " + userPrefs);

        this.database = new Database(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.database.getPersonList());
    }

    public ModelManager() {
        this(new Database(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getDatabaseFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setDatabaseFilePath(addressBookFilePath);
    }

    //=========== Database ================================================================================

    @Override
    public ReadOnlyDatabase getDatabase() {
        return database;
    }

    @Override
    public void setAddressBook(ReadOnlyDatabase database) {
        this.database.resetData(database);
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return database.hasPerson(person);
    }

    @Override
    public Person getPersonByName(Name name) {
        requireNonNull(name);
        ObservableList<Person> personList = database.getPersonList();
        for (Person person : personList) {
            if (person.getName().equals(name)) {
                return person;
            }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void deletePerson(Person target) {
        database.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        database.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        database.setPerson(target, editedPerson);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * sorts the student list by grade
     * @param isInAscending if true the list is set to ascending order, else descending
     */
    @Override
    public void sortListByGrade(boolean isInAscending, String subjectName) {
        this.database.sortByGrade(isInAscending, subjectName);
    }

    @Override
    public Predicate<Person> createPersonIsInClassPredicate(String studentClass) {
        return person -> person.getStudentClass().isSameClass(studentClass);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return database.equals(other.database)
               && userPrefs.equals(other.userPrefs)
               && filteredPersons.equals(other.filteredPersons);
    }

}
