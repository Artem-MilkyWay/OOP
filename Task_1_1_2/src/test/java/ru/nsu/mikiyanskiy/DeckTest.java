package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

/**
 * тесты для класса Колода карт
 */
public class DeckTest {

    public Deck deck;

    @BeforeEach
    /**
     * настройка перед каждым тестом
     */
    void setUp() {
        deck = new Deck();  // Создаём новую колоду перед каждым тестом
    }

    @Test
    /**
     * Тест 1: Проверка, что колода изначально содержит 52 карты
     */
    void testDeckHas52Cards() {
        assertEquals(52, deck.cards.size());
    }


    @Test
    /**
     * // Тест 2: Проверка, что все карты уникальны в колоде
     */
    void testDeckContainsAllUniqueCards() {
        Set<Card> cardSet = new HashSet<>(deck.cards);
        assertEquals(52, cardSet.size());
    }

    @Test
    /**
     * Тест 3: Проверка, что колода перемешивается
     */
    void testDeckShufflesCorrectly() {
        Deck originalDeck = new Deck(); // Новая колода для сравнения
        deck.shuffle(); // Перемешиваем колоду
        assertNotEquals(originalDeck.cards, deck.cards);
    }


    @Test
    /**
     * Тест 4: Проверка, что извлекаемая карта уменьшается количество карт в колоде
     */
    void testGetFromDeckReducesSize() {
        int initialSize = deck.cards.size();
        deck.getFromDeck();  // Достаем одну карту
        assertEquals(initialSize - 1, deck.cards.size());
    }


    @Test
    /**
     * Тест 5: Проверка, что после извлечения всех карт колода пуста
     */
    void testDeckIsEmptyAfterAllCardsAreDrawn() {
        for (int i = 0; i < 52; i++) {
            deck.getFromDeck();
        }
        assertEquals(0, deck.cards.size(), "Колода должна быть пустой после извлечения всех карт");
    }
}
