package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    public Player player;

    @BeforeEach
    void setUp() {
        player = new Player("John"); // Создаем нового игрока перед каждым тестом
    }

    // Тест 1: Проверка, что игрок может взять карту
    @Test
    void testTakeCard() {
        Card card = new Card(Card.Suit.HEARTS, Card.Rank.TEN);
        player.takeCard(card);
        assertEquals(1, player.hand.size());
        assertEquals(card, player.hand.get(0));
    }

    // Тест 2: Проверка корректного подсчета очков без тузов
    @Test
    void testGetScoreWithoutAces() {
        player.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN)); // 10 очков
        player.takeCard(new Card(Card.Suit.DIAMONDS, Card.Rank.SEVEN)); // 7 очков
        assertEquals(17, player.getScore());
    }

    // Тест 3: Проверка корректного подсчета очков с тузом (11 очков)
    @Test
    void testGetScoreWithAce() {
        player.takeCard(new Card(Card.Suit.SPADES, Card.Rank.ACE)); // Туз (11 очков)
        player.takeCard(new Card(Card.Suit.CLUBS, Card.Rank.NINE)); // 9 очков
        assertEquals(20, player.getScore());
    }

    // Тест 4: Проверка корректного подсчета очков с тузом как 1 (при превышении 21)
    @Test
    void testGetScoreWithAceAsOne() {
        player.takeCard(new Card(Card.Suit.SPADES, Card.Rank.ACE)); // Туз (11 очков)
        player.takeCard(new Card(Card.Suit.CLUBS, Card.Rank.TEN)); // 10 очков
        player.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN)); // 10 очков
        assertEquals(21, player.getScore());
    }

    // Тест 5: Проверка на проигрыш (перебор очков больше 21)
    @Test
    void testIsLoser() {
        player.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.KING)); // 10 очков
        player.takeCard(new Card(Card.Suit.CLUBS, Card.Rank.QUEEN)); // 10 очков
        player.takeCard(new Card(Card.Suit.SPADES, Card.Rank.THREE)); // 3 очка
        assertTrue(player.isLoser());
    }

    // Тест 6: Проверка на блекджек (ровно 21 с двумя картами)
    @Test
    void testHasBlackjack() {
        player.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE)); // Туз (11 очков)
        player.takeCard(new Card(Card.Suit.DIAMONDS, Card.Rank.JACK)); // Валет (10 очков)
        assertTrue(player.hasBlackjack());
    }
    // Тест 7: Проверка на отсутствие блекджека при 21, но больше чем две карты
    @Test
    void testNotBlackjackWithMoreThanTwoCards() {
        player.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.ACE)); // Туз (11 очков)
        player.takeCard(new Card(Card.Suit.DIAMONDS, Card.Rank.NINE)); // 9 очков
        player.takeCard(new Card(Card.Suit.CLUBS, Card.Rank.ACE)); // Еще один туз
        assertFalse(player.hasBlackjack());
    }
}
