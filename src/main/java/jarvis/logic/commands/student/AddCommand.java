package jarvis.logic.commands.student;

import static jarvis.logic.parser.CliSyntax.PREFIX_NAME;
import static java.util.Objects.requireNonNull;

import jarvis.logic.commands.Command;
import jarvis.logic.commands.CommandResult;
import jarvis.logic.commands.exceptions.CommandException;
import jarvis.model.Model;
import jarvis.model.student.Student;

/**
 * Adds a student to the student book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "addstudent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a student to JARVIS. "
            + "Parameters: "
            + PREFIX_NAME + "NAME\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe ";

    public static final String MESSAGE_SUCCESS = "New student added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This student already exists in JARVIS";

    private final Student toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Student}
     */
    public AddCommand(Student student) {
        requireNonNull(student);
        toAdd = student;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStudent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addStudent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}