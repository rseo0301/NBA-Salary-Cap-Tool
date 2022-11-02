package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Roster;
import persistence.Reader;

import java.io.File;
import java.io.IOException;

// Represents the controller for the startMenu.fxml window. Allows the reader to either
// create or load a previous roster.
public class StartMenuController {

    @FXML private TextField rosterNameTextBox;
    @FXML private Button continueButton;
    public Roster roster;
    private static final String ROSTER_FILE = "./data/roster.txt";

    // MODIFIES: this
    // EFFECTS: Creates a new Roster with the Non-Empty String in rosterNameTextBox
    public void handleContinueButton(ActionEvent event) throws IOException {
        if (!rosterNameTextBox.getText().isEmpty()) {
            Roster roster = new Roster();
            roster.setName(rosterNameTextBox.getText());
            loadWindow(roster);
        }
    }

    // MODIFIES: this
    // EFFECTS:Tries to load roster, throws IOException when no roster.txt file is found
    public void loadSavedRoster() throws IOException {
        roster = Reader.readRoster(new File(ROSTER_FILE));
        loadWindow(roster);
    }

    // EFFECTS: Tries to load saved roster, catches IOException if no roster is found and displays error message
    public void handleLoadSaveButton(ActionEvent event) {
        try {
            loadSavedRoster();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setTitle("No Roster Found");
            alert.setContentText("There was no saved roster found.");
            alert.showAndWait();
        }
    }

    // EFFECTS: Passes roster to MainMenuController, and opens mainMenu
    public void loadWindow(Roster roster) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/mainMenu.fxml"));
        Parent mainMenuParent = loader.load();

        //Passes roster to mainMenu
        MainMenuController controller = loader.getController();
        controller.initRoster(roster);

        //Gets window of startMenu and sets mainMenu as the new scene
        Stage window = (Stage) continueButton.getScene().getWindow();
        window.setScene(new Scene(mainMenuParent, 800, 400));
        window.setTitle("Main Menu");
        window.show();
    }
}