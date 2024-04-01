package q1;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FXTurnListener implements ITurnListener {
    @Override
    public void onTurnPlayed(Card firstPlayerCard, Card secondPlayerCard, int winner) {
        System.out.println(String.format("Player 1 card- %s", firstPlayerCard));
        System.out.println(String.format("Player 2 card- %s", secondPlayerCard));
        System.out.println(String.format("Player %d won this round!\n", winner));
    }

    @Override
    public void onWarPlayed(ArrayList<Card> firstPlayerCards, ArrayList<Card> secondPlayerCards, int winner) {
        System.out.println(String.format("Player 1 cards- %s", joinCardsStr(firstPlayerCards)));
        System.out.println(String.format("Player 2 cards- %s", joinCardsStr(secondPlayerCards)));
        System.out.println(String.format("Player %d won this round!\n", winner));
    }

    private String joinCardsStr(ArrayList<Card> cards) {
        return cards
                .stream()
                .map((card) -> card.toString())
                .collect(Collectors.joining(", "));
    }
}
