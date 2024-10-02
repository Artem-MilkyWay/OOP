package ru.nsu.mikiyanskiy;
import java.util.*;

// колода
public class Deck {
    public List<Card> cards;

    // конструктор колоды карт
    public Deck() {
        cards = new ArrayList<>();
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    // ф-ция перемешивания карт
    public void shuffle() {
        Collections.shuffle(cards);
    }

    // достать карту из колоды
    public Card getFromDeck() {
        return cards.remove(cards.size() - 1);
    }
}
