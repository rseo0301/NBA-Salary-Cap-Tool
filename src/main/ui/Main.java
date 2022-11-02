/*package ui;

import exceptions.BoundaryError;
import model.Player;
import model.Roster;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        new App();
    }
}*/

package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Roster;
import persistence.Reader;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

public class Main extends Application {

   // private static final String ROSTER_FILE = ;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("startMenu.fxml"));
        Parent startMenuParent = loader.load();
        StartMenuController controller = loader.getController();
        primaryStage.setTitle("NBA Salary Cap Tool");
        primaryStage.setScene(new Scene(startMenuParent, 800, 400));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}





