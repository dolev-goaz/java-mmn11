package q1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class WarGameController {
    WarGame game;
    public void initialize() {
        ITurnListener listener = new FXTurnListener();
        this.game = new WarGame();
        game.initializeGame();
        game.addTurnListener(listener);
    }

    private static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private TextArea player1Log;

    @FXML
    private TextArea player2Log;

    @FXML
    private Button button;

    @FXML
    void onNextTurn(ActionEvent event) {
        if (game.isGameOver()) {
            return;
        }
        game.runTurn();
        if (game.isGameOver()) {
            this.button.setDisable(true);
        }
    }

}
