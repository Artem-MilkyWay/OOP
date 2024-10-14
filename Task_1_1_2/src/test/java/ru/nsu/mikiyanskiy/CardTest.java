package ru.nsu.mikiyanskiy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * тесты для класса Карта
 */
class CardTest {
    @Test
    /**
     * проверка величин всех карт
     */
    void points_system_test() {
        Card card;

        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                card = new Card(suit, rank);
                assertEquals(card.getValue(),card.rank.getValue());
            }
        }
    }
}
