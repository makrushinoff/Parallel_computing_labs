package lab5.view;

import javafx.application.Application;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("ShipWars mini");
        Stage startStage = getIntroStage(stage);
    }

    private Stage getIntroStage(Stage primaryStage) {
        Stage startStage = new Stage();
        startStage.setTitle(primaryStage.getTitle());
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(850, 650);

        Text welcomeText = new Text("Welcome to ShipWars!");
        welcomeText.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 18));
        Text inputCodeText = new Text("Input room code to join, or create new room");
        welcomeText.setFont(Font.font("verdana", FontWeight.NORMAL, FontPosture.REGULAR, 15));
        TextField inputCodeTextField = new TextField();
        return null;
    }

}
