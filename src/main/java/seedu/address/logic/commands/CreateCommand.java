package seedu.address.logic.commands;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.group.Group;

/**
 * Creates a new empty group.
 */
public class CreateCommand extends Command {

    public static final String COMMAND_WORD = "create";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new empty group.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Group created successfully! Group number is %1$s";

    @Override
    public CommandResult execute(Model model) {
        int number = generateGroupNumber(model);
        Group createdGroup = new Group(number);
        model.addGroup(createdGroup);

        return new CommandResult(String.format(MESSAGE_SUCCESS, createdGroup.getNumber()));
    }

    /**
     * Generates the next group number to be used when creating a new group.
     *
     * @param model
     * @return
     */
    private int generateGroupNumber(Model model) {
        int number;
        ReadOnlyAddressBook addressBook = model.getAddressBook();
        ObservableList<Group> groups = addressBook.getGroupList();

        if (groups.isEmpty()) {
            number = 1;
        } else {
            number = 2;
            List<Integer> groupNumbers = groups.stream()
                    .map(Group::getNumber).collect(Collectors.toList());
            while (groupNumbers.contains(number)) {
                number++;
            }
        }

        return number;
    }
}