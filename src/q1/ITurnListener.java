package q1;

import java.util.ArrayList;

// an interface of turn listeners for the WarGame
public interface ITurnListener {
    // is called when a regular turn was played
    void onTurnPlayed(Card firstPlayerCard, Card secondPlayerCard, int winner);
    // is called when a war turn was played
    void onWarPlayed(ArrayList<Card> firstPlayerCards, ArrayList<Card> secondPlayerCards, int winner);
    // is called when the game is finished
    void onGameOver(int winner);
}
