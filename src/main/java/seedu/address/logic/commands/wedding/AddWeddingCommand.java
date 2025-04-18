package seedu.address.logic.commands.wedding;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.LogicMemory;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.WeddingModel;
import seedu.address.model.wedding.Wedding;

/**
 * Creates a new wedding draft in the Wedding Planner
 */
public class AddWeddingCommand extends Command {

    public static final String COMMAND_WORD = "new";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Creates a wedding draft\n"
        + "Parameters: n/WEDDING_NAME d/DATE\n"
        + "Example: " + COMMAND_WORD + " n/John & Mary d/25122025";
    public static final String MESSAGE_SUCCESS = "Wedding draft created! "
        + "Now add the bride using:\n"
        + "add n/NAME p/PHONE e/EMAIL a/ADDRESS t/bride";
    public static final String MESSAGE_DUPLICATE_WEDDING = "This wedding already exists.";
    public static final String MESSAGE_EXISTING_DRAFT = "Overwriting previous wedding draft.";

    private final Wedding draftWedding;

    /**
     * Creates an AddWeddingCommand to add the specified {@code Wedding}
     */
    public AddWeddingCommand(Wedding draftWedding) {
        requireNonNull(draftWedding);
        this.draftWedding = draftWedding;
    }

    @Override
    public CommandResult execute(WeddingModel model) throws CommandException {
        requireNonNull(model);

        // Check against existing weddings in permanent storage
        if (model.hasWedding(draftWedding)) {
            throw new CommandException(MESSAGE_DUPLICATE_WEDDING);
        }

        // Handle existing draft
        String feedback = MESSAGE_SUCCESS;
        if (model.hasDraftWedding()) {
            feedback = MESSAGE_EXISTING_DRAFT + "\n" + MESSAGE_SUCCESS;
        }

        // Set the new draft wedding
        model.setDraftWedding(draftWedding);
        LogicMemory.setDraftStage(LogicMemory.DraftState.ADDING_BRIDE);
        return new CommandResult(feedback);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddWeddingCommand)) {
            return false;
        }

        AddWeddingCommand otherAddCommand = (AddWeddingCommand) other;
        return draftWedding.equals(otherAddCommand.draftWedding);
    }
}
