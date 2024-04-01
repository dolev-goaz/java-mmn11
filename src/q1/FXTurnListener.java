package q1;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FXTurnListener implements ITurnListener {
    @Override
    public void onTurnPlayed(Card firstPlayerCard, Card secondPlayerCard, int winner) {
        System.out.println("Turn played!");
        System.out.println(String.format("First player card- %s", firstPlayerCard));
        System.out.println(String.format("Second player card- %s", secondPlayerCard));
    }

    @Override
    public void onWarPlayed(ArrayList<Card> firstPlayerCards, ArrayList<Card> secondPlayerCards, int winner) {
        System.out.println("War played!");
        System.out.println(String.format("First player card- %s", joinCardsStr(firstPlayerCards)));
        System.out.println(String.format("Second player card- %s", joinCardsStr(secondPlayerCards)));
    }

    private String joinCardsStr(ArrayList<Card> cards) {
        return cards
                .stream()
                .map((card) -> card.toString())
                .collect(Collectors.joining(", "));
    }
}
