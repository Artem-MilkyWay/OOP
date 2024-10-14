package ru.nsu.mikiyanskiy;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * тесты для самой игры
 */
public class GameTest {
    private Game game;
    private  ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private  PrintStream originalOut = System.out;

    @BeforeEach
    /**
     * настройка перед каждым тестом
     */
    void setUp() {
        game = new Game(); // Создаем новый экземпляр игры
        System.setOut(new PrintStream(outputStream)); // Перенаправляем вывод в поток
    }

    @Test
    /**
     * проверка на победу игрока получением БлекДжека
     */
    void testPlayerWinsWithBlackjack() {
        // Настраиваем колоду так, чтобы игрок получил Блэкджек
        game.deck = new Deck() {
            public List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.SPADES, Card.Rank.ACE),
                    new Card(Card.Suit.HEARTS, Card.Rank.TWO),
                    new Card(Card.Suit.DIAMONDS, Card.Rank.TEN),
                    new Card(Card.Suit.CLUBS, Card.Rank.ACE)
            ));
            
            @Override
            /**
             * переписываем getFromDeck на время теста, чтобы карты доставались из измененной колоды
             */
            public Card getFromDeck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        // Запускаем игру
        game.start(0);

        String output = outputStream.toString();
        assertTrue(output.contains("You have Blackjack!"));
    }

    @Test
    /**
     * проверка проигрыша игрока из-за перебора
     */
    void testPlayerLosesRound() {
        // Настраиваем колоду так, чтобы игрок перебрал
        game.deck = new Deck() {
            public List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.SPADES, Card.Rank.TWO),
                    new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                    new Card(Card.Suit.SPADES, Card.Rank.TEN),
                    new Card(Card.Suit.HEARTS, Card.Rank.TEN),
                    new Card(Card.Suit.CLUBS, Card.Rank.TEN)
            ));

            @Override
            /**
             * переписываем getFromDeck на время теста, чтобы карты доставались из измененной колоды
             */
            public Card getFromDeck() {
                return testDeck.remove(testDeck.size()-1);
            }
        };

        // Симулируем ввод: игрок берет карту и затем проигрывает
        System.setIn(new ByteArrayInputStream("1\n".getBytes()));

        // Запускаем игру
        game.start(0);

        // Проверяем вывод на наличие сообщения о проигрыше игрока
        String output = outputStream.toString();
        assertTrue(output.contains("You've lost the round."));
    }

    
    @Test
    /**
     * ничья по блек джеку
     */
    void testGameEndsInTie() {
        // Настраиваем колоду так, чтобы была ничья
        game.deck = new Deck() {
            public List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                    new Card(Card.Suit.SPADES, Card.Rank.TEN),
                    new Card(Card.Suit.HEARTS, Card.Rank.EIGHT),
                    new Card(Card.Suit.CLUBS, Card.Rank.TEN)
            ));

            @Override
            /**
             * переписываем getFromDeck на время теста, чтобы карты доставались из измененной колоды
             */
            public Card getFromDeck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        // Симулируем ввод: игрок завершает ход
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));

        // Запускаем игру
        game.start(0);

        // Проверяем вывод на наличие сообщения о ничьей
        String output = outputStream.toString();
        assertTrue(output.contains("It's a tie."));
    }
    
    @Test
    /**
     * дилер проигрывает по перебору
     */
    void testDealerLosesRound() {

        // Настраиваем колоду
        game.deck = new Deck() {
            public List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.SPADES, Card.Rank.EIGHT),
                    new Card(Card.Suit.SPADES, Card.Rank.SIX),
                    new Card(Card.Suit.SPADES, Card.Rank.TEN),
                    new Card(Card.Suit.HEARTS, Card.Rank.EIGHT),
                    new Card(Card.Suit.CLUBS, Card.Rank.TEN)
            ));

            @Override
            /**
             * переписываем getFromDeck на время теста, чтобы карты доставались из измененной колоды
             */
            public Card getFromDeck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        // Игрок отказывается брать еще карты
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));

        // Запускаем игру
        game.start(0);

        // Проверяем вывод на наличие сообщения о выигрыше
        String output = outputStream.toString();
        assertTrue(output.contains("You have won the round!"));
    }

    
    @Test
    /**
     * игрок выигрывает по очкам в конце раунда ( обычный случай )
     */
    void playerWinByScoreDefault() {

        // Настраиваем колоду
        game.deck = new Deck() {
            public List<Card> testDeck = new ArrayList<>(Arrays.asList(
                    new Card(Card.Suit.HEARTS, Card.Rank.THREE),
                    new Card(Card.Suit.SPADES, Card.Rank.THREE),
                    new Card(Card.Suit.SPADES, Card.Rank.SIX),
                    new Card(Card.Suit.SPADES, Card.Rank.TEN),
                    new Card(Card.Suit.HEARTS, Card.Rank.EIGHT),
                    new Card(Card.Suit.CLUBS, Card.Rank.TEN)
            ));

            @Override
            /**
             * переписываем getFromDeck на время теста, чтобы карты доставались из измененной колоды
             */
            public Card getFromDeck() {
                return testDeck.remove(testDeck.size() - 1);
            }
        };

        // игрок берет одну карту, диллер берет одну карту
        System.setIn(new ByteArrayInputStream("1\n0\n".getBytes()));

        // Запускаем игру
        game.start(0);

        // проверяем наличие вывода о выигрыше
        String output = outputStream.toString();
        assertTrue(output.contains("You have won the round!"));
    }
    
    @BeforeEach
    /**
     * Восстанавливаем стандартный вывод после тестов
     */
    void getBack() {
        System.setOut(originalOut);
    }
}
