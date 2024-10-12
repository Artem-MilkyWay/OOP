package ru.nsu.mikiyanskiy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * тесты для класса Дилер
 */
class DealerTest {

    public Dealer dealer;
    public ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    public PrintStream originalOut = System.out;

    @BeforeEach
    /**
     * настройка перед каждым новом тестом
     */
    void setUp() {
        dealer = new Dealer(); // Создаем нового дилера перед каждым тестом
        System.setOut(new PrintStream(outputStream)); // Перенаправляем вывод в поток
    }

    /**
     * Тест 1: Проверка отображения начальной руки дилера
     */
    @Test
    void testShowInitialHand() {
        dealer.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        dealer.takeCard(new Card(Card.Suit.CLUBS, Card.Rank.ACE));

        dealer.showInitialHand();

        String expectedOutput = "   Dealer's cards: [TEN of HEARTS (10), <closed card>]";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }

    /**
     * Тест 2: Проверка отображения полной руки дилера
     */
    @Test
    void testShowHand() {
        dealer.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.TEN));
        dealer.takeCard(new Card(Card.Suit.CLUBS, Card.Rank.ACE));

        dealer.showHand();

        String expectedOutput = "    Dealer's cards: [TEN of HEARTS (10), ACE of CLUBS (11)] => 21";
        assertTrue(outputStream.toString().startsWith(expectedOutput));
    }

    /**
     * Тест 3: Проверка работы унаследованных методов
     */
    @Test
    void testDealerScore() {
        dealer.takeCard(new Card(Card.Suit.HEARTS, Card.Rank.KING)); // 10 очков
        dealer.takeCard(new Card(Card.Suit.DIAMONDS, Card.Rank.ACE)); // Туз (11 очков)

        assertEquals(21, dealer.getScore());
        assertTrue(dealer.hasBlackjack());
    }

    /**
     * Восстанавливаем стандартный вывод после тестов
     */
    @BeforeEach
    void getBack() {
        System.setOut(originalOut);
    }
}
