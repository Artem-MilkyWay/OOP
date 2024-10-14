package ru.nsu.mikiyanskiy;

/**
 *  описание карты из колоды
 */
public class Card {
    // масти карт
    public enum Suit {
        HEARTS, DIAMONDS, CLUBS, SPADES
    }

    /**
     * величина карты
     */
    public enum Rank {
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), JACK(10), QUEEN(10), KING(10), ACE(11);

        public final int value;

        Rank(int value) {
            this.value = value;
        }

        /**
         * получить величину карты
         * @return величину 
         */
        public int getValue() {
            return value;
        }
    }

    public Suit suit;
    public Rank rank;

    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * получить величину карты
     * @return величина
     */
    public int getValue() {
        return rank.getValue();
    }
    
    
    @Override
    /**
     * переписывание ф-ции преобразования в строку, чтобы корректно выводить характеристики карты
     * @return вывод карты 
     */
    public String toString() {
        return rank + " of " + suit + " " + "("+ rank.getValue()+ ")";
    }
}
