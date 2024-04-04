package q1;

// Represents a playing card
public class Card {
    public static final String[] faces = {"Ace", "Deuce", "Three", "Four", "Five", "Six",
            "Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
    public static final String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private final String face;
    private final String suit;

    public Card(String face, String suit) {
        this.face = face;
        this.suit = suit;
    }

    // returns the value of the card(by face)
    public int getValue() {
        for (int i = 0; i < faces.length; i++) {
            if (this.face.equals(Card.faces[i])) {
                // + 1 because indexes start from 0 and values start from 1
                return i + 1;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return String.format("%s of %s", this.face, this.suit);
    }
}
