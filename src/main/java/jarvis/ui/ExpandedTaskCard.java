package jarvis.ui;

import jarvis.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays the full information of a {@code Task}.
 */
public class ExpandedTaskCard extends UiPart<Region> {

    private static final String FXML = "ExpandedTaskCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Task task;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label done;
    @FXML
    private Label taskDesc;
    @FXML
    private Label taskDeadline;

    /**
     * Creates a {@code TaskCard} with the given {@code Task} and index to display.
     */
    public ExpandedTaskCard(Task task, int displayedIndex) {
        super(FXML);
        this.task = task;
        id.setText(displayedIndex + ". ");
        if (task.isDone()) {
            done.setText("[X]");
        } else {
            done.setText("[ ]");
        }
        taskDesc.setText(task.getDesc().taskDesc);
        taskDeadline.setText(task.getDeadline().toString());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExpandedTaskCard)) {
            return false;
        }

        // state check
        ExpandedTaskCard card = (ExpandedTaskCard) other;
        return id.getText().equals(card.id.getText())
                && task.equals(card.task);
    }
}