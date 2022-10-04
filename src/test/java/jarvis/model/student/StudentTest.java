package jarvis.model.student;

import static jarvis.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static jarvis.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static jarvis.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static jarvis.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static jarvis.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static jarvis.testutil.Assert.assertThrows;
import static jarvis.testutil.TypicalPersons.ALICE;
import static jarvis.testutil.TypicalPersons.BOB;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import jarvis.testutil.PersonBuilder;

public class StudentTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Student student = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> student.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSameStudent(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameStudent(null));

        // same name, all other attributes different -> returns true
        Student editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameStudent(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSameStudent(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Student editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSameStudent(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSameStudent(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Student aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Student editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}