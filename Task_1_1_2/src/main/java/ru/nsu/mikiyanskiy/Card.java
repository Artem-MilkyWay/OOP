package ru.nsu.mikiyanskiy;

// описание карты из колоды
public class Card {
    // масти карт
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    // величина карты
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        private final int value;

        Rank(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Suit suit;
    private Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    //получить величину карты
    public int getValue() {
        return rank.getValue();
    }

    // переписывание ф-ции преобразования в строку, чтобы корректно выводить характеристики карты
    @Override
    public String toString() {
        return rank + " of " + suit + " " + "("+ rank.getValue()+ ")";
    }
}