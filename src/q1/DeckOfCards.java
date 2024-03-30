package q1;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;

public class DeckOfCards {
    private static final SecureRandom randomNumbers = new SecureRandom();
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

    public void shuffle() {
        for (int firstIndex = 0; firstIndex < cards.size(); firstIndex++) {
            int secondIndex = randomNumbers.nextInt(MAX_CARDS_IN_DECK);

            // swap first, second
            Card firstCard = this.cards.get(firstIndex);
            Card secondCard = this.cards.get(secondIndex);
            this.cards.set(firstIndex, secondCard);
            this.cards.set(secondIndex, firstCard);
        }
    }

    public Card dealCard() {
        final int lastCardIndex = cards.size() - 1;
        if (lastCardIndex < 0) {
            return null;
        }
        return cards.remove(lastCardIndex);
    }

    public void insertEnd(Card card) {
        this.cards.add(card);
    }

    public void insertEnd(Collection<Card> cards) {
        this.cards.addAll(cards);
    }

    public void clear() {
        this.cards.clear();
    }

    public boolean isEmpty() {
        return this.cards.isEmpty();
    }

}
