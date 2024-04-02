package q1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DeckOfCards {
    public static final int MAX_CARDS_IN_DECK = 52;
    private ArrayList<Card> cards;


    public DeckOfCards() {
        this(false);
    }

    public DeckOfCards(boolean fill) {
        this.cards = new ArrayList<>();
        if (!fill) return;
        for (int count = 0; count < MAX_CARDS_IN_DECK; count++) {
            this.cards.add(new Card(Card.faces[count % 13], Card.suits[count / 13]));
        }
    }

    // Shuffles the deck
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    // Removes and returns the first card from the deck.
    // If the deck is empty, returns null
    public Card dealCard() {
        if (cards.size() == 0) {
            return null;
        }
        return cards.remove(0);
    }

    // Inserts a single card to the deck
    public void insertEnd(Card card) {
        this.cards.add(card);
    }

    // Inserts a collection of cards to the deck
    public void insertEnd(Collection<Card> cards) {
        this.cards.addAll(cards);
    }

    // Clears the deck
    public void clear() {
        this.cards.clear();
    }

    // Checks if the deck is empty
    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

}
