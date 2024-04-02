package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // FOR q1
//        Parent root = FXMLLoader.load(getClass().getResource("../q1/WarGame.fxml"));
//        primaryStage.setTitle("War Game");
        // FOR q2
        Parent root = FXMLLoader.load(getClass().getResource("../q2/matrix.fxml"));
        primaryStage.setTitle("Matrix");
        primaryStage.setScene(new Scene(root));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
