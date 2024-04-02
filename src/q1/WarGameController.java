package q1;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class WarGameController implements ITurnListener {
    WarGame game;
    public void initialize() {
        this.game = new WarGame();
        game.initializeGame();
        game.addTurnListener(this);
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
    private Text player1Header;

    @FXML
    private Text player2Header;

    @FXML
    private Button nextTurnButton;

    @FXML
    void onNextTurn(ActionEvent event) {
        if (game.isGameOver()) {
            // Should not happen, since we disable the button
            return;
        }
        game.runTurn();
        if (game.isGameOver()) {
            nextTurnButton.setDisable(true);
        }
    }

    @Override
    public void onTurnPlayed(Card firstPlayerCard, Card secondPlayerCard, int winner) {
        this.player1Log.setText(firstPlayerCard.toString());
        this.player2Log.setText(secondPlayerCard.toString());

        this.setWinner(winner);
    }

    @Override
    public void onWarPlayed(ArrayList<Card> firstPlayerCards, ArrayList<Card> secondPlayerCards, int winner) {
        this.player1Log.setText(joinCardsStr(firstPlayerCards));
        this.player2Log.setText(joinCardsStr(secondPlayerCards));
        this.setWinner(winner);
    }

    @Override
    public void onGameOver(int winner) {
        showAlert("Winner", String.format("Player %d won!", winner));
    }

    private void setWinner(int winnerIndex) {
        Text winnerHeader, loserHeader;

        if (winnerIndex == 1) {
            winnerHeader = this.player1Header;
            loserHeader = this.player2Header;
        } else {
            winnerHeader = this.player2Header;
            loserHeader = this.player1Header;
        }

        winnerHeader.setFill(Color.GREEN);
        winnerHeader.setUnderline(true);
        loserHeader.setFill(Color.RED);
        loserHeader.setUnderline(false);
    }

    private String joinCardsStr(ArrayList<Card> cards) {
        return IntStream.range(0, cards.size())
                .mapToObj(i -> {
                    boolean isCardCompared = (i % (WarGame.DOWN_CARDS_PER_WAR + 1)) == 0;
                    String prefix = isCardCompared? "": "\t"; // non-compared cards are pushed for better visualization
                    return prefix + cards.get(i).toString();
                })
                .collect(Collectors.joining("\n"));
    }
}
