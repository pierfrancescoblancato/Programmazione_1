public class Main {
    public static void main(String[] args) {

        TicTacToe game = new TicTacToe('o');

        printGrid(game.getGrid());
        System.out.println("Giocatore iniziale: " + game.getCurrentPlayer());
        System.out.println();

        // Esempio di partita
        playMove(game, 0, 0); // o
        playMove(game, 1, 1); // x
        playMove(game, 0, 1); // o
        playMove(game, 2, 2); // x
        playMove(game, 0, 2); // o vince

        if (game.isEnd()) {
            if (game.getWinner() != ' ') {
                System.out.println("Vincitore: " + game.getWinner());
            } else {
                System.out.println("Pareggio");
            }
        }
    }

    private static void playMove(TicTacToe game, int row, int col) {
        System.out.println("Mossa di " + game.getCurrentPlayer() + ": (" + row + ", " + col + ")");
        game.move(row, col);
        printGrid(game.getGrid());
        System.out.println();
    }

    private static void printGrid(char[][] grid) {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(grid[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
}
