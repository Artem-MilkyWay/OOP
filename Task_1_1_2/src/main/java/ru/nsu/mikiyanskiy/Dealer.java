package ru.nsu.mikiyanskiy;

/**
 * класс Дилера
 */
public class Dealer extends Player {
    public Dealer() {
        super("Dealer");
    }

    /**
     * вывод положения дилера в игре до открытия второй карты
     */
    public void showInitialHand() {   
        System.out.println("    Dealer's cards: " + "["+hand.get(0) + ", <closed card>"+"]");
    }

    /**
     * полноценный вывод
     */
    public void showHand(){
        System.out.println("    Dealer's cards: " + hand + " => " + getScore());
    }
}
