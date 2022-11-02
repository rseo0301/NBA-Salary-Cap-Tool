package ui;

import exceptions.BelowMinimumException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.Player;
import model.Roster;

import java.io.IOException;

// Represents the controller for the addPlayer.fxml JavaFX Window. This class allows the reader to enter fields
// for a player, and the controller then initializes the player and adds him to the roster.

public class AddPlayerController {

    @FXML private TextField nameTextBox;
    @FXML private TextField ageTextBox;
    @FXML private ChoiceBox<String> yearsChoiceBox;
    @FXML private ChoiceBox<String> exceptionChoiceBox;
    @FXML private ChoiceBox<Integer> contractChoiceBox;
    @FXML private TextField salaryTextBox;
    @FXML private Button cancelButton;
    @FXML private Button addPlayerButton;
    private Roster roster;
    Player player;

    @FXML
    public void initialize() {
        //Populate yearsChoiceBox
        for (int x = 1; x < 10; x++) {
            yearsChoiceBox.getItems().add(String.valueOf(x));
        }
        yearsChoiceBox.getItems().add("10+");

        //Populate exceptionChoiceBox
        exceptionChoiceBox.getItems().add("None");
        exceptionChoiceBox.getItems().add("Minimum Exception");

        //Populate contractChoiceBox
        for (int x = 1; x < 6; x++) {
            contractChoiceBox.getItems().add(x);
        }
    }

    public void initRoster(Roster roster) {
        this.roster = roster;
    }

    public void handleCancelButton(ActionEvent actionEvent) throws IOException {
        openNewScene();
    }

    public void handleAddPlayerButton(ActionEvent event) {
        //If none of the fields are empty
        if (!yearsChoiceBox.getSelectionModel().isEmpty() && !exceptionChoiceBox.getSelectionModel().isEmpty()
                && !contractChoiceBox.getSelectionModel().isEmpty() && !nameTextBox.getText().isEmpty()
                && !ageTextBox.getText().isEmpty() && !salaryTextBox.getText().isEmpty()) {

            try {
                Player player = returnPlayer();
                // returns null if not all fields are completed
                if (player != null) {
                    roster.addPlayer(player);
                    openNewScene();
                }
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Incorrect Input");
                alert.setContentText("Please make sure your values are the correct type.");
                alert.showAndWait();
            }
        } else {
            Alert alertIncomplete = new Alert(Alert.AlertType.ERROR);
            alertIncomplete.setHeaderText(null);
            alertIncomplete.setTitle("Incomplete Fields");
            alertIncomplete.setContentText("You must complete all fields.");
            alertIncomplete.showAndWait();
        }
    }

    public Player returnPlayer() {

        try {
            String name = nameTextBox.getText();
            int age = Integer.parseInt(ageTextBox.getText());
            long salary = Long.parseLong(salaryTextBox.getText());
            int contractYears = contractChoiceBox.getSelectionModel().getSelectedItem();
            int years;
            if (yearsChoiceBox.getSelectionModel().getSelectedItem().equals("10+")) {
                years = 10;
            } else {
                years = Integer.parseInt(yearsChoiceBox.getSelectionModel().getSelectedItem());
            }

            player = new Player(name, age, years, salary, contractYears, roster);

            return player;
        } catch (BelowMinimumException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Incorrect Input");
            alert.setContentText("The entered salary is below the minimum wage for the player.");
            alert.showAndWait();
        }

        return null;
    }

    public void openNewScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/mainMenu.fxml"));
        Parent mainMenuParent = loader.load();

        MainMenuController controller = loader.getController();
        controller.initRoster(roster);

        Stage stage = (Stage) addPlayerButton.getScene().getWindow();
        stage.close();
        Stage window = new Stage();
        window.setScene(new Scene(mainMenuParent, 800, 400));
        window.setTitle("Main Menu");
        window.show();
    }

    public void handleExceptionSelected(ActionEvent event) {
        displayMinimumSalary();
    }

    public void handleYearsSelected(ActionEvent event) {
        displayMinimumSalary();
    }

    public void displayMinimumSalary() {
        if (!exceptionChoiceBox.getSelectionModel().isEmpty()
                && !yearsChoiceBox.getSelectionModel().isEmpty()) {
            if (exceptionChoiceBox.getSelectionModel().getSelectedItem().equals("Minimum Exception")) {
                if (yearsChoiceBox.getSelectionModel().getSelectedItem().equals("10+")) {
                    salaryTextBox.setText(Long.toString(roster.getMinimumSalary().get(10)));
                } else {
                    salaryTextBox.setText(Long.toString(roster.getMinimumSalary().get(
                            Integer.parseInt(yearsChoiceBox.getSelectionModel().getSelectedItem()))));
                }
            }
        }
    }
}
