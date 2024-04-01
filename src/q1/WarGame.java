package q1;

import java.util.ArrayList;
import java.util.Collection;

public class WarGame {
    private static final int FIRST_PLAYER_INDEX = 1;
    private static final int SECOND_PLAYER_INDEX = 2;
    private static final int DOWN_CARDS_PER_WAR = 2;

    private Collection<ITurnListener> cardListeners = new ArrayList<>();

    DeckOfCards firstPlayerDeck;
    DeckOfCards secondPlayerDeck;

    ArrayList<Card> firstPlayerPlayedCards;
    ArrayList<Card> secondPlayerPlayedCards;

    public WarGame() {
        firstPlayerDeck = new DeckOfCards();
        secondPlayerDeck = new DeckOfCards();

        firstPlayerPlayedCards = new ArrayList<Card>();
        secondPlayerPlayedCards = new ArrayList<Card>();
    }

    public void initializeGame() {
        firstPlayerDeck.clear();
        secondPlayerDeck.clear();
        DeckOfCards deck = new DeckOfCards(true);
        deck.shuffle();
        final int dealCount =  DeckOfCards.MAX_CARDS_IN_DECK / 2;

        for (int cardIndex = 0; cardIndex < dealCount; cardIndex++) {
            firstPlayerDeck.insertEnd(deck.dealCard());
            secondPlayerDeck.insertEnd(deck.dealCard());
        }
    }

    public Card dealCard(DeckOfCards deckOfCards, int playerIndex) {
        Card c = deckOfCards.dealCard();
        if (c == null) {
            return null;
        }
        Collection<Card> accumulator = (playerIndex == FIRST_PLAYER_INDEX)
                ? firstPlayerPlayedCards
                : secondPlayerPlayedCards;
        accumulator.add(c);
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
            if (dealCard(firstPlayerDeck, FIRST_PLAYER_INDEX) == null) {
                return 2;
            }
            if (dealCard(secondPlayerDeck, SECOND_PLAYER_INDEX) == null) {
                return 1;
            }
        }

        Card c1 = dealCard(firstPlayerDeck, FIRST_PLAYER_INDEX);
        Card c2 = dealCard(secondPlayerDeck, SECOND_PLAYER_INDEX);
        int comparisonResult = comparePlayerCards(c1, c2);
        if (comparisonResult == 0) {
            return War();
        }
        return comparisonResult;
    }

    public int runTurn() {
        Card c1 = dealCard(firstPlayerDeck, FIRST_PLAYER_INDEX);
        Card c2 = dealCard(secondPlayerDeck, SECOND_PLAYER_INDEX);

        int comparisonResult = comparePlayerCards(c1, c2);
        if (comparisonResult == 0) {
            int warResult = War();
            notifyListeners_OnWarPlayed(warResult);
            return warResult;
        }
        notifyListeners_OnTurnPlayed(comparisonResult);
        return comparisonResult;
    }

    public int runGame() {
        // NOTE: not really necessary to check if second deck is empty
        while (!firstPlayerDeck.isEmpty() && !secondPlayerDeck.isEmpty()) {
            firstPlayerPlayedCards.clear();
            secondPlayerPlayedCards.clear();

            int winner = runTurn();

            DeckOfCards winnerDeck = winner == 1? firstPlayerDeck : secondPlayerDeck;
            winnerDeck.insertEnd(firstPlayerPlayedCards);
            winnerDeck.insertEnd(secondPlayerPlayedCards);
        }

        return firstPlayerDeck.isEmpty()? 2: 1;
    }


    // Method to register listeners
    public void addTurnListener(ITurnListener listener) {
        cardListeners.add(listener);
    }

    // Method to notify listeners about turn being played
    private void notifyListeners_OnTurnPlayed(int winner) {
        // NOTE: assumes both players have played one card.
        Card firstPlayerCard = this.firstPlayerPlayedCards.get(0);
        Card secondPlayerCard = this.secondPlayerPlayedCards.get(0);

        for (ITurnListener listener : cardListeners) {
            listener.onTurnPlayed(firstPlayerCard, secondPlayerCard, winner);
        }
    }

    // Method to notify listeners about turn being played
    private void notifyListeners_OnWarPlayed(int winner) {
        for (ITurnListener listener : cardListeners) {
            listener.onWarPlayed(this.firstPlayerPlayedCards, this.secondPlayerPlayedCards, winner);
        }
    }
}
