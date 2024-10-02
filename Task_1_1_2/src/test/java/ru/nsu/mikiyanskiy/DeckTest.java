package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

public class DeckTest {

    public Deck deck;

    @BeforeEach
    void setUp() {
        deck = new Deck();  // Создаём новую колоду перед каждым тестом
    }

    // Тест 1: Проверка, что колода изначально содержит 52 карты
    @Test
    void testDeckHas52Cards() {
        assertEquals(52, deck.cards.size());
    }

    // Тест 2: Проверка, что все карты уникальны в колоде
    @Test
    void testDeckContainsAllUniqueCards() {
        Set<Card> cardSet = new HashSet<>(deck.cards);
        assertEquals(52, cardSet.size());
    }

    // Тест 3: Проверка, что колода перемешивается
    @Test
    void testDeckShufflesCorrectly() {
        Deck originalDeck = new Deck(); // Новая колода для сравнения
        deck.shuffle(); // Перемешиваем колоду
        assertNotEquals(originalDeck.cards, deck.cards);
    }

    // Тест 4: Проверка, что извлекаемая карта уменьшается количество карт в колоде
    @Test
    void testGetFromDeckReducesSize() {
        int initialSize = deck.cards.size();
        deck.getFromDeck();  // Достаем одну карту
        assertEquals(initialSize - 1, deck.cards.size());
    }

    // Тест 5: Проверка, что после извлечения всех карт колода пуста
    @Test
    void testDeckIsEmptyAfterAllCardsAreDrawn() {
        for (int i = 0; i < 52; i++) {
            deck.getFromDeck();
        }
        assertEquals(0, deck.cards.size(), "Колода должна быть пустой после извлечения всех карт");
    }
}
