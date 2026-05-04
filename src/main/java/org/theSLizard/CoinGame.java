package org.theSLizard;

public class CoinGame {

    public static String gameWinnerOptimised(int numberOfCoins, String currentPlayer) {
        String nextPlayer = (currentPlayer.equals("you")) ? "them" : "you";
        if((numberOfCoins - 1) % 3 == 0) {
            return nextPlayer;
        } else {
            return currentPlayer;
        }
    }


    public static String gameWinner(int numberOfCoins, String currentPlayer) {
        if (numberOfCoins <= 0) {
            return currentPlayer;
        }

        String nextPlayer = (currentPlayer.equals("you")) ? "them" : "you";

        // Check both possible moves (taking 1 or 2 coins)
        boolean canWinWithMove1 = gameWinner(numberOfCoins - 1, nextPlayer).equals(currentPlayer);
        boolean canWinWithMove2 = gameWinner(numberOfCoins - 2, nextPlayer).equals(currentPlayer);

        if (canWinWithMove1 || canWinWithMove2) {
            return currentPlayer;
        } else {
            return nextPlayer;
        }
    }
}
