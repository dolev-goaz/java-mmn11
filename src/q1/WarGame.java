package q1;

import java.util.ArrayList;

public class WarGame {
    private static final int DOWN_CARDS_PER_WAR = 2;
    DeckOfCards firstPlayer;
    DeckOfCards secondPlayer;

    ArrayList<Card> turnAccumulator;

    public WarGame() {
        firstPlayer = new DeckOfCards();
        secondPlayer = new DeckOfCards();

        turnAccumulator = new ArrayList<Card>();
    }

    public void initializeGame() {
        firstPlayer.clear();
        secondPlayer.clear();
        DeckOfCards deck = new DeckOfCards(true);
        deck.shuffle();
        final int dealCount =  DeckOfCards.MAX_CARDS_IN_DECK / 2;

        for (int cardIndex = 0; cardIndex < dealCount; cardIndex++) {
            firstPlayer.insertEnd(deck.dealCard());
            secondPlayer.insertEnd(deck.dealCard());
        }
    }

    public Card dealCardWithPool(DeckOfCards deckOfCards) {
        Card c = deckOfCards.dealCard();
        if (c == null) {
            return null;
        }
        turnAccumulator.add(c);
        return c;
    }

    public int comparePlayerCards(Card firstPlayerCard, Card secondPlayerCard) {
        if (firstPlayerCard == null && secondPlayerCard == null) {
            // should NEVER happen
            // should probably raise exception in this case
            System.out.println("Both decks are out of cards");
            return -1;
        }
        if (firstPlayerCard == null) {
            return 2;
        }
        if (secondPlayerCard == null) {
            return 1;
        }

        int value1 = firstPlayerCard.getValue();
        int value2 = secondPlayerCard.getValue();
        if (value1 == value2) return 0;
        if (value1 > value2) return 1;
        return 2;
    }

    public int War() {
        for (int i = 0; i < DOWN_CARDS_PER_WAR; i++) {
            if (dealCardWithPool(firstPlayer) == null) {
                return 2;
            }
            if (dealCardWithPool(secondPlayer) == null) {
                return 1;
            }
        }

        Card c1 = dealCardWithPool(firstPlayer);
        Card c2 = dealCardWithPool(secondPlayer);
        int comparisonResult = comparePlayerCards(c1, c2);
        if (comparisonResult == 0) {
            return War();
        }
        return comparisonResult;
    }

    public int runTurn() {
        Card c1 = dealCardWithPool(firstPlayer);
        Card c2 = dealCardWithPool(secondPlayer);

        int comparisonResult = comparePlayerCards(c1, c2);
        if (comparisonResult == 0) {
            return War();
        }

        return comparisonResult;
    }

    public int runGame() {
        // not necessary to check if second deck is empty
        while (!firstPlayer.isEmpty() && !secondPlayer.isEmpty()) {
            turnAccumulator.clear();
            int winner = runTurn();

            DeckOfCards winnerDeck = winner == 1? firstPlayer: secondPlayer;
            winnerDeck.insertEnd(turnAccumulator);
        }

        return firstPlayer.isEmpty()? 2: 1;
    }
}
