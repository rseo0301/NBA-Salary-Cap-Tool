package ui;

import exceptions.BoundaryErrorException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Roster;

import java.io.IOException;

// Represents the controller for the settings.fxml window. Allows the reader to make changes to the roster settings.
public class SettingsController {

    @FXML private TextField salaryCapField;
    @FXML private TextField taxApronField;
    @FXML private TextField taxLineField;
    @FXML private TextField salaryFloorField;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    private Roster roster;

    // MODIFIES: this
    // EFFECTS: displays current settings values
    public void displayRoster(Roster roster) {

        this.roster = roster;

        salaryCapField.setText(Long.toString(roster.getSalaryCap()));
        taxLineField.setText(Long.toString(roster.getTaxLine()));
        taxApronField.setText(Long.toString(roster.getTaxApron()));
        salaryFloorField.setText(Long.toString(roster.getSalaryFloor()));
    }

    // EFFECTS: calls setNewValues and openNewScene methods, catches Exception if wrong values are entered by user
    public void handleSaveButton(ActionEvent event) {
        try {
            setNewValues();
            openNewScene();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Incorrect Input");
            alert.setContentText("Please make sure your values are the correct type.");
            alert.showAndWait();
        }

    }

    // EFFECTS: calls openNewScene method on button press
    public void handleCancelButton(ActionEvent event) throws IOException {
        openNewScene();
    }

    // MODIFIES: roster
    // EFFECTS: sets new values for the roster, throws BoundaryErrorException if returnValueValidity returns false
    public void setNewValues() throws BoundaryErrorException {
        long salaryCap = Long.parseLong(salaryCapField.getText());
        long taxLine = Long.parseLong(taxLineField.getText());
        long taxApron = Long.parseLong(taxApronField.getText());
        long salaryFloor = Long.parseLong(salaryFloorField.getText());
        if (returnValueValidity(salaryCap, taxLine, taxApron, salaryFloor)) {
            roster.setSalaryCap(salaryCap);
            roster.setTaxLine(taxLine);
            roster.setTaxApron(taxApron);
            roster.setSalaryFloor(salaryFloor);
        } else {
            throw new BoundaryErrorException();
        }
    }

    // EFFECTS: returns true if the three inequalities are met, otherwise returns false
    public boolean returnValueValidity(Long salaryCap, Long taxLine, Long taxApron, long salaryFloor) {
        return (taxApron > taxLine) && (taxLine > salaryCap) && (salaryCap > salaryFloor);
    }

    // EFFECTS: closes settings window and opens new stage with mainMenu.fxml window
    public void openNewScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/mainMenu.fxml"));
        Parent mainMenuParent = loader.load();

        MainMenuController controller = loader.getController();
        controller.initRoster(roster);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        Stage window = new Stage();
        window.setScene(new Scene(mainMenuParent, 800, 400));
        window.setTitle("Main Menu");
        window.show();
    }

}
