package q1;

import java.util.ArrayList;

public interface ITurnListener {
    void onTurnPlayed(Card firstPlayerCard, Card secondPlayerCard, int winner);
    void onWarPlayed(ArrayList<Card> firstPlayerCards, ArrayList<Card> secondPlayerCards, int winner);
    void onGameOver(int winner);
}
