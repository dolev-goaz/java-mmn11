package q1;

import java.util.ArrayList;
import java.util.Collection;

public class WarGame {
    private static final int DRAW = 0;
    private static final int FIRST_PLAYER_INDEX = 1;
    private static final int SECOND_PLAYER_INDEX = 2;
    public static final int DOWN_CARDS_PER_WAR = 2;

    private Collection<ITurnListener> cardListeners = new ArrayList<>();

    DeckOfCards firstPlayerDeck;
    DeckOfCards secondPlayerDeck;

    // these collect the cards played by each player during the turn
    ArrayList<Card> firstPlayerPlayedCards;
    ArrayList<Card> secondPlayerPlayedCards;

    public WarGame() {
        firstPlayerDeck = new DeckOfCards();
        secondPlayerDeck = new DeckOfCards();

        firstPlayerPlayedCards = new ArrayList<Card>();
        secondPlayerPlayedCards = new ArrayList<Card>();
    }

    // Extracted from the constructor to allow multiple games to be played on the same instance
    public void initializeGame() {
        firstPlayerDeck.clear();
        secondPlayerDeck.clear();

        // Initialize player decks
        DeckOfCards deck = new DeckOfCards(true);
        deck.shuffle();
        final int dealCount =  DeckOfCards.MAX_CARDS_IN_DECK / 2;
        for (int cardIndex = 0; cardIndex < dealCount; cardIndex++) {
            firstPlayerDeck.insertEnd(deck.dealCard());
            secondPlayerDeck.insertEnd(deck.dealCard());
        }
    }

    // If the deck is not empty, deals a card from the provided player's deck./
    // Returns null if the deck is empty.
    private Card dealCard(int playerIndex) {
        // NOTE: We're passing only the index since we need to fetch two variables relevant to the player,
        // so it's cleaner this way.
        DeckOfCards playerDeck = (playerIndex == FIRST_PLAYER_INDEX)
                ? this.firstPlayerDeck
                : this.secondPlayerDeck;
        Card c = playerDeck.dealCard();
        if (c == null) {
            return null;
        }
        Collection<Card> accumulator = (playerIndex == FIRST_PLAYER_INDEX)
                ? this.firstPlayerPlayedCards
                : this.secondPlayerPlayedCards;
        accumulator.add(c);
        return c;
    }

    // Compares the cards of the two cards, taking into account whether one of them is null.
    private int comparePlayerCards(Card firstPlayerCard, Card secondPlayerCard) {
        // if both decks were empty
        if ((firstPlayerCard == null) && (secondPlayerCard == null)) {
            // NOTE: this should NEVER happen
            // could raise an exception
            System.out.println("Both decks are out of cards");
            return -1;
        }
        // if first deck is empty
        if (firstPlayerCard == null) {
            return SECOND_PLAYER_INDEX;
        }
        // if second deck is empty
        if (secondPlayerCard == null) {
            return FIRST_PLAYER_INDEX;
        }

        // no deck was empty- compare the actual values
        int value1 = firstPlayerCard.getValue();
        int value2 = secondPlayerCard.getValue();
        if (value1 == value2) return DRAW; // tie
        if (value1 > value2) return FIRST_PLAYER_INDEX; // player 1 won
        return SECOND_PLAYER_INDEX; // player 2 won
    }

    // Initiates a war/new war between the players
    private int initiateWar() {
        // draw DOWN_CARDS_PER_WAR cards from each deck
        for (int i = 0; i < DOWN_CARDS_PER_WAR; i++) {
            Card downCard1 = dealCard(FIRST_PLAYER_INDEX);
            Card downCard2 = dealCard(SECOND_PLAYER_INDEX);
            if (downCard1 == null && downCard2 == null) {
                // both players ran out of cards at the same time
                return DRAW;
            }
            if (downCard1 == null) {
                // first player ran out of cards while second player didn't
                return SECOND_PLAYER_INDEX;
            }
            if (downCard2 == null) {
                // second player ran out of cards while first player didn't
                return FIRST_PLAYER_INDEX;
            }
        }
        // Compare the top cards
        Card c1 = dealCard(FIRST_PLAYER_INDEX);
        Card c2 = dealCard(SECOND_PLAYER_INDEX);
        int comparisonResult = comparePlayerCards(c1, c2);
        if (comparisonResult == 0) {
            return initiateWar();
        }
        return comparisonResult;
    }

    private int runTurnInner() {
        Card c1 = dealCard(FIRST_PLAYER_INDEX);
        Card c2 = dealCard(SECOND_PLAYER_INDEX);

        int comparisonResult = comparePlayerCards(c1, c2);
        if (comparisonResult == DRAW) {
            int warResult = initiateWar();
            notifyListeners_OnWarPlayed(warResult);
            return warResult;
        }
        notifyListeners_OnTurnPlayed(comparisonResult);
        return comparisonResult;
    }

    public void runTurn() {
        if (this.isGameOver()) {
            notifyListeners_OnGameOver();
            return;
        }
        firstPlayerPlayedCards.clear();
        secondPlayerPlayedCards.clear();

        int winner = runTurnInner();

        if (isGameOver()) {
            // should handle winner = 0 case
            notifyListeners_OnGameOver();
            return;
        }
        DeckOfCards winnerDeck = winner == 1? firstPlayerDeck : secondPlayerDeck;
        winnerDeck.insertEnd(firstPlayerPlayedCards);
        winnerDeck.insertEnd(secondPlayerPlayedCards);
    }

    public boolean isGameOver() {
        return firstPlayerDeck.isEmpty() || secondPlayerDeck.isEmpty();
    }
    public int getWinner() {
        if (this.firstPlayerDeck.isEmpty() && this.secondPlayerDeck.isEmpty()) {
            return DRAW;
        }
        return this.firstPlayerDeck.isEmpty()? SECOND_PLAYER_INDEX: FIRST_PLAYER_INDEX;
    }

    // Here, we use listeners in order to send events from the WarGame instance.
    // This way, we can decouple the actual drawing logic from the WarGame runner.
    // NOTE: Could put this section on the top of the file, chose to put it at the bottom here because it isn't directly
    // related to the actual logic

    // Register a new listener
    public void addTurnListener(ITurnListener listener) {
        cardListeners.add(listener);
    }

    // Notify listeners about the turn being played
    private void notifyListeners_OnTurnPlayed(int winner) {
        // NOTE: assumes both players have played one card.
        Card firstPlayerCard = this.firstPlayerPlayedCards.get(0);
        Card secondPlayerCard = this.secondPlayerPlayedCards.get(0);

        for (ITurnListener listener : cardListeners) {
            listener.onTurnPlayed(firstPlayerCard, secondPlayerCard, winner);
        }
    }

    // Notify listeners about a war being played
    private void notifyListeners_OnWarPlayed(int winner) {
        for (ITurnListener listener : cardListeners) {
            listener.onWarPlayed(this.firstPlayerPlayedCards, this.secondPlayerPlayedCards, winner);
        }
    }

    // Notify listeners about the game's winner
    private void notifyListeners_OnGameOver() {
        int winner = this.getWinner();
        for (ITurnListener listener : cardListeners) {
            listener.onGameOver(winner);
        }
    }

}
