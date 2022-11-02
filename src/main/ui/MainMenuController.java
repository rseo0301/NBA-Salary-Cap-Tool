package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Player;
import model.Roster;
import persistence.Writer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

// Represents the controller for the mainMenu.fxml window. Can view the roster details and players,
// also gives access to adding players, changing settings, and saving the roster.
public class MainMenuController {

    private static final String ROSTER_FILE = "./data/roster.txt";
    @FXML private Label rosterLabel;
    @FXML private TableView<Player> tableView;
    @FXML private TableColumn<Player, String> name;
    @FXML private TableColumn<Player, Integer> age;
    @FXML private TableColumn<Player, Long> one;
    @FXML private TableColumn<Player, Long> two;
    @FXML private TableColumn<Player, Long> three;
    @FXML private TableColumn<Player, Long> four;
    @FXML private TableColumn<Player, Long> five;
    @FXML private MenuItem addPlayerButton;
    @FXML private MenuItem settingsButton;
    @FXML private TableView<Roster> rosterTableView;
    @FXML private TableColumn<Roster, Integer> rosterSize;
    @FXML private TableColumn<Roster, Long> totalOne;
    @FXML private TableColumn<Roster, Long> totalTwo;
    @FXML private TableColumn<Roster, Long> totalThree;
    @FXML private TableColumn<Roster, Long> totalFour;
    @FXML private TableColumn<Roster, Long> totalFive;
    @FXML private Label capLabel;
    @FXML private Label lineLabel;
    @FXML private Label apronLabel;
    @FXML private Label floorLabel;

    private Roster roster;
    ObservableList<Player> players = FXCollections.observableArrayList();
    ObservableList<Roster> rosters = FXCollections.observableArrayList();

    // MODIFIES: this
    // EFFECTS: Passes roster to this class and initializes the PropertyValueFactory for each TableColumn
    public void initRoster(Roster roster) {
        this.roster = roster;
        rosterLabel.setText(roster.getName() + "'s Team");

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        one.setCellValueFactory(new PropertyValueFactory<>("yearOne"));
        two.setCellValueFactory(new PropertyValueFactory<>("yearTwo"));
        three.setCellValueFactory(new PropertyValueFactory<>("yearThree"));
        four.setCellValueFactory(new PropertyValueFactory<>("yearFour"));
        five.setCellValueFactory(new PropertyValueFactory<>("yearFive"));

        rosterSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        totalOne.setCellValueFactory(new PropertyValueFactory<>("totalOne"));
        totalTwo.setCellValueFactory(new PropertyValueFactory<>("totalTwo"));
        totalThree.setCellValueFactory(new PropertyValueFactory<>("totalThree"));
        totalFour.setCellValueFactory(new PropertyValueFactory<>("totalFour"));
        totalFive.setCellValueFactory(new PropertyValueFactory<>("totalFive"));

        tableView.setItems(getPlayers());
        rosterTableView.setItems(getRoster());
        setSettingsValues();
    }

    // MODIFIES: this
    // EFFECTS: Returns each player in roster and adds to players field
    public ObservableList<Player> getPlayers() {
        players.addAll(roster.getPlayerList());
        return players;
    }

    // MODIFIES: this
    // EFFECTS: Returns the roster and adds to rosters field
    public ObservableList<Roster> getRoster() {
        rosters.add(roster);
        return rosters;
    }

    // MODIFIES: this
    // EFFECTS: Displays the roster settings
    public void setSettingsValues() {
        capLabel.setText(Long.toString(roster.getSalaryCap()));
        lineLabel.setText(Long.toString(roster.getTaxLine()));
        apronLabel.setText(Long.toString(roster.getTaxApron()));
        floorLabel.setText(Long.toString(roster.getSalaryFloor()));

        if (roster.isBelowFloor()) {
            floorLabel.setUnderline(true);
        }
        if (roster.isAboveCap()) {
            capLabel.setUnderline(true);
        }
        if (roster.isAboveLine()) {
            lineLabel.setUnderline(true);
        }
        if (roster.isAboveApron()) {
            apronLabel.setUnderline(true);
        }
    }

    // Work in Progress
    public void changeFontColor() {
        if (roster.getTotalOne() >= roster.getSalaryFloor()) {
          //  floorLabel.setStyle("-fx-color: red;");
            floorLabel.setTextFill(Color.web("#0076a3"));
        } else {
            floorLabel.setStyle("-fx-color: black;");
        }
    }

    // EFFECTS: Opens addPlayer.fxml stage if roster is not full, otherwise display error message
    public void handleAddPlayer(ActionEvent event) throws IOException {
        if (!roster.isRosterMaxed()) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ui/addPlayer.fxml"));
            Parent addPlayerParent = loader.load();

            AddPlayerController controller = loader.getController();
            controller.initRoster(roster);

            Stage window = (Stage)addPlayerButton.getParentPopup().getOwnerWindow();
            window.close();
            Stage stage = new Stage();
            stage.setTitle("Add Player");
            stage.setScene(new Scene(addPlayerParent, 400, 260));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } else {
            Alert rosterFull = new Alert(Alert.AlertType.WARNING);
            rosterFull.setTitle("Roster full");
            rosterFull.setHeaderText(null);
            rosterFull.setContentText("Your roster is full.");
            rosterFull.showAndWait();
        }
    }

    // EFFECTS: Opens settings.fxml stage
    public void handleSettings(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/ui/settings.fxml"));
        Parent settingsParent = loader.load();

        SettingsController controller = loader.getController();
        controller.displayRoster(roster);

        Stage window = (Stage)settingsButton.getParentPopup().getOwnerWindow();
        window.close();
        Stage stage = new Stage();
        stage.setTitle("Settings");
        stage.setScene(new Scene(settingsParent, 300, 200));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // EFFECTS: calls saveRoster method on button press
    public void handleSaveButton(ActionEvent event) {
        saveRoster();
    }

    // EFFECTS: writes roster data on file
    private void saveRoster() {
        try {
            Writer writer = new Writer(new File(ROSTER_FILE));
            writer.write(roster);
            for (Player p : roster.getPlayerList()) {
                writer.write(p);
            }
            writer.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save");
            alert.setHeaderText(null);
            alert.setContentText("Accounts saved to file " + ROSTER_FILE);
            alert.showAndWait();
        } catch (FileNotFoundException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText(null);
            error.setContentText("Unable to save accounts to " + ROSTER_FILE);
            error.showAndWait();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }
}
