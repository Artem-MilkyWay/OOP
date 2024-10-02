package ru.nsu.mikiyanskiy;
import java.util.*;

public class Player {
    protected List<Card> hand;  // карты на руках игрока
    protected String name;

    public Player(String name) {
        this.name = name;
        hand = new ArrayList<>();
    }

    public void takeCard(Card card) {       // взять новую карту
        hand.add(card);
    }

    // получить количество очков на данный момент
    public int getScore() {
        int score = 0;
        int aces = 0;
        for (Card card : hand) {
            score += card.getValue();
            if (card.getValue() == 11) aces++;
        }
        while (score > 21 && aces > 0) {
            score -= 10;
            aces--;
        }
        return score;
    }

    // вывод текущего положения игрока ( карты на руках, кол-во очков)
    public void showHand() {
        System.out.println("    Your cards: " + hand + " => " + getScore());
    }

    public boolean isLoser() {
        return getScore() > 21;
    } // проверка на проигрыш

    public boolean hasBlackjack() {
        return getScore() == 21 && hand.size() == 2;
    } // проверка на блекджек
}

