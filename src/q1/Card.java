package q1;

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

    public int getValue() {
        for (int i = 0; i < faces.length; i++) {
            if (this.face.equals(Card.faces[i])) {
                return i + 1;
            }
        }
        return -1;
    }
}
