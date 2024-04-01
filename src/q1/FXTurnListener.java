package q1;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FXTurnListener implements ITurnListener {
    @Override
    public void onTurnPlayed(Card firstPlayerCard, Card secondPlayerCard, int winner) {
        System.out.println(String.format("Player 1 card- %s", firstPlayerCard));
        System.out.println(String.format("Player 2 card- %s", secondPlayerCard));
        System.out.println(String.format("Player %d won this round!\n", winner));
    }

    @Override
    public void onWarPlayed(ArrayList<Card> firstPlayerCards, ArrayList<Card> secondPlayerCards, int winner) {
        System.out.println(String.format("Player 1 cards-\n%s", joinCardsStr(firstPlayerCards)));
        System.out.println(String.format("Player 2 cards-\n%s", joinCardsStr(secondPlayerCards)));
        System.out.println(String.format("Player %d won this round!\n", winner));
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
