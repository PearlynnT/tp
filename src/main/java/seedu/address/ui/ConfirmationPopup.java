package seedu.address.ui;

import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;



/**
 * Controller for a confirmation popup
 */
public class ConfirmationPopup extends UiPart<Stage> {

    private static final String FXML = "ClearConfirmationPopup.fxml";

    @FXML
    private Button yesButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label confirmationMessage;

    private boolean isConfirmed = false;

    private Consumer<Boolean> confirmationCallback;



    /**
     * Creates a new ConfirmationPopup with a new internal Stage.
     */
    public ConfirmationPopup() {
        super(FXML, new Stage());
        // Make the stage modal
        getRoot().initModality(Modality.APPLICATION_MODAL);
        initialize();
    }

    public void setConfirmationCallback(Consumer<Boolean> callback) {
        this.confirmationCallback = callback;
    }

    /**
     * Shows the confirmation popup with the specified message.
     *
     */
    public void show() {
        confirmationMessage.setText("Are you sure you would like to delete all the data? \n"
                + "You will not be able to undo this action later.");
        getLogger().fine("Showing confirmation popup.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    @FXML
    private void initialize() {
        yesButton.setOnAction(event -> {
            isConfirmed = true;
            getRoot().close();
            if (confirmationCallback != null) {
                confirmationCallback.accept(true);
            }
        });

        cancelButton.setOnAction(event -> getRoot().close());
    }

    private static Logger getLogger() {
        return LogsCenter.getLogger(ConfirmationPopup.class);
    }

    /**
     * Returns true if the confirmation popup is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Focuses on the confirmation popup.
     */
    public void focus() {
        getRoot().requestFocus();
    }
}