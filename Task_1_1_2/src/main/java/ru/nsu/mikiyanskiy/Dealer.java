package ru.nsu.mikiyanskiy;

public class Dealer extends Player {
    public Dealer() {
        super("Dealer");
    }

    public void showInitialHand() {   // вывод положения дилера в игре до открытия второй карты
        System.out.println("    Dealer's cards: " + "["+hand.get(0) + ", <closed card>"+"]");
    }

    public void showHand(){
        System.out.println("    Dealer's cards: " + hand + " => " + getScore());
    }
}
