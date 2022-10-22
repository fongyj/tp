package jarvis.logic.commands;

import static jarvis.commons.util.CollectionUtil.requireAllNonNull;
import static jarvis.logic.parser.CliSyntax.PREFIX_END_DATE_TIME;
import static jarvis.logic.parser.CliSyntax.PREFIX_LESSON;
import static jarvis.logic.parser.CliSyntax.PREFIX_START_DATE_TIME;
import static java.util.Objects.requireNonNull;

import java.util.List;

import jarvis.logic.commands.exceptions.CommandException;
import jarvis.model.LessonAttendance;
import jarvis.model.LessonDesc;
import jarvis.model.LessonNotes;
import jarvis.model.Model;
import jarvis.model.Student;
import jarvis.model.Studio;
import jarvis.model.StudioParticipation;
import jarvis.model.TimePeriod;

/**
 * Adds a studio lesson to the lesson book.
 */
public class AddStudioCommand extends Command {

    public static final String COMMAND_WORD = "addstudio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a studio lesson to JARVIS.\n"
            + "Parameters: "
            + PREFIX_LESSON + "LESSON_DESC "
            + PREFIX_START_DATE_TIME + "START_DATE_TIME "
            + PREFIX_END_DATE_TIME + "END_DATE_TIME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_LESSON + "Studio 3 "
            + PREFIX_START_DATE_TIME + "2022-10-12T14:00 "
            + PREFIX_END_DATE_TIME + "2022-10-12T16:00 ";

    public static final String MESSAGE_SUCCESS = "New studio added: %1$s";
    public static final String MESSAGE_DUPLICATE_STUDIO = "This studio already exists in JARVIS";
    public static final String MESSAGE_TIME_PERIOD_CLASH = "This studio overlaps with another lesson in JARVIS";
    public static final String MESSAGE_START_DATE_AFTER_END_DATE =
            "This studio's end date time must be after start date time";

    private final LessonDesc studioDesc;
    private final TimePeriod studioPeriod;

    /**
     * Creates an AddStudioCommand to add the specified {@code Studio}
     * with {@code studioDesc, studioPeriod}
     */
    public AddStudioCommand(LessonDesc studioDesc, TimePeriod studioPeriod) {
        requireAllNonNull(studioDesc, studioPeriod);
        this.studioDesc = studioDesc;
        this.studioPeriod = studioPeriod;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredStudentList(Model.PREDICATE_SHOW_ALL_STUDENTS);
        List<Student> allStudentList = model.getFilteredStudentList();

        LessonAttendance studioAttendance = new LessonAttendance(allStudentList);
        LessonNotes studioNotes = new LessonNotes(allStudentList);
        StudioParticipation studioParticipation = new StudioParticipation(allStudentList);

        Studio studioToAdd = new Studio(studioDesc, studioPeriod,
                studioAttendance, studioParticipation, studioNotes);

        if (model.hasLesson(studioToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STUDIO);
        } else if (model.hasPeriodClash(studioToAdd)) {
            throw new CommandException(MESSAGE_TIME_PERIOD_CLASH);
        }

        model.addLesson(studioToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, studioToAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same object
            return true;
        }

        if (!(other instanceof AddStudioCommand)) { // instanceof handles nulls
            return false;
        }

        AddStudioCommand ac = (AddStudioCommand) other;

        return studioDesc.equals(ac.studioDesc) && studioPeriod.equals(ac.studioPeriod);
    }


}