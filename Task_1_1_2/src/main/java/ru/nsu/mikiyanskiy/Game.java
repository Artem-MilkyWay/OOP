package ru.nsu.mikiyanskiy;
import java.util.Scanner;

/**
 * основной класс самой игры 
 */
public class Game {
    public Deck deck;
    public Player player;
    public Dealer dealer;

    public Game() {
        deck = new Deck();
        player = new Player("You");
        dealer = new Dealer();
    }

    /**
     * функция запуска игры
     * @param numOfRoundsFlag Для тестов флаг устанавливается 0, тогда играется 1 раунд
     */
    public void start(int numOfRoundsFlag ) { 
        int round = 1;
        int playerWins = 0;
        int dealerWins = 0;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Blackjack!");

        // процесс игры
        while (true) {
            player.hand.clear();   // очистка списков карт игроков в начале каждого раунда
            dealer.hand.clear();
            System.out.println("Round " + round);
            round++;

            System.out.println("The dealer dealt the cards");

            // Начальная раздача
            player.takeCard(deck.getFromDeck());
            player.takeCard(deck.getFromDeck());
            dealer.takeCard(deck.getFromDeck());
            dealer.takeCard(deck.getFromDeck());

            player.showHand();
            dealer.showInitialHand();

            // Проверка на Блэкджек
            if (player.hasBlackjack() && dealer.hasBlackjack()) {
                dealerWins++;
                playerWins++;
                System.out.println("It's a tie. Score " + playerWins + ":" + dealerWins);
                if (numOfRoundsFlag==0) break;
                continue;
            } else if (player.hasBlackjack()) {
                playerWins++;
                System.out.println("You have Blackjack! You have won the round. Score " + playerWins + ":" + dealerWins);
                if (numOfRoundsFlag==0) break;
                continue;
            } else if (dealer.hasBlackjack()) {
                dealerWins++;
                System.out.println("Dealer has Blackjack! Dealer has won the round. Score " + playerWins + ":" + dealerWins);
                if (numOfRoundsFlag==0) break;
                continue;
            }

            System.out.println("Your move");
            System.out.println("-------");

            int flag =0; // проверка на перебор игрока

            // Ход игрока
            while (true) {
                System.out.print("Enter '1' to take the card, and '0' to stop...");
                String decision = scanner.nextLine();
                if (decision.equalsIgnoreCase("1")) {
                    player.takeCard(deck.getFromDeck());
                    System.out.println("You have opened the card "+ player.hand.get(player.hand.size()-1));
                    player.showHand();
                    dealer.showInitialHand();
                    if (player.isLoser()) {
                        flag = 1;
                        dealerWins++;
                        System.out.println("You've lost the round. Score " + playerWins + ":" + dealerWins);
                        break;
                    }
                } else {
                    break;
                }
            }
            if (flag == 1) {
                if (numOfRoundsFlag==0) break;// если игрок проиграл из-за перебора, переходим в следующий раунд
                continue;
            }

            // Ход дилера
            System.out.println("Dealer's move");
            System.out.println("-------");
            System.out.println("Dealer is opening closed card " + dealer.hand.get(1));

            player.showHand();
            dealer.showHand();

            while (dealer.getScore() < 17) {
                dealer.takeCard(deck.getFromDeck());
                System.out.println("Dealer is opening card " + dealer.hand.get(dealer.hand.size()-1));
                player.showHand();
                dealer.showHand();
            }


            // Определение победителя
            if (dealer.isLoser()) {
                playerWins++;
                System.out.println("You have won the round! Score " + playerWins + ":" + dealerWins );
                if (numOfRoundsFlag==0) break;
            } else if (player.getScore() > dealer.getScore()) {
                playerWins++;
                System.out.println("You have won the round! Score " + playerWins + ":" + dealerWins );
                if (numOfRoundsFlag==0) break;
            } else if (player.getScore() < dealer.getScore()) {
                dealerWins++;
                System.out.println("Dealer has won the round. Score " + playerWins + ":" + dealerWins);
                if (numOfRoundsFlag==0) break;
            } else {
                playerWins++;
                dealerWins++;
                System.out.println("It's a tie. Score " + playerWins + ":" + dealerWins);
                if (numOfRoundsFlag==0) break;
            }
        }
    }
}
