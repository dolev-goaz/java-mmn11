package q1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import q1.FXTurnListener;
import q1.ITurnListener;
import q1.WarGame;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("WarGame.fxml"));
        primaryStage.setTitle("War Game");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();

        ITurnListener listener = new FXTurnListener();
        WarGame game = new WarGame();
        game.initializeGame();
        game.addTurnListener(listener);
        int winner = game.runGame();
        showAlert("Game Over",  String.format("Player %d is the winner!", winner));
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
