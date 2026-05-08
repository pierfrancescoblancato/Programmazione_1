public class TicTacToe {
    private char[][] grid = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };
    private char currentPlayer = 'o';
    private int nOfMove = 0;

    private boolean end = false;
    private char winner = ' ';

    public TicTacToe(char currentPlayer) {
        if (currentPlayer == 'x' || currentPlayer == 'o') {
            this.currentPlayer = currentPlayer;
        }
    }

    public void move(int row, int column) {
        if (end) return; // evita mosse dopo la fine

        if (this.grid[row][column] == ' ') {
            this.grid[row][column] = this.currentPlayer;
            nOfMove++; // incrementa SUBITO

            // controlla vittoria
            if (hasWon(this.currentPlayer)) {
                this.end = true;
                this.winner = this.currentPlayer;
                return;
            }

            // controlla pareggio
            if (nOfMove == 9) {
                this.end = true;
                return;
            }

            // cambia giocatore
            this.currentPlayer = (this.currentPlayer == 'o') ? 'x' : 'o';
        }
    }

    public boolean hasWon(char player) {
        // check rows
        for (int row = 0; row < 3; row++) {
            if (grid[row][0] == player && grid[row][1] == player && grid[row][2] == player)
                return true;
        }
        // check columns
        for (int col = 0; col < 3; col++) {
            if (grid[0][col] == player && grid[1][col] == player && grid[2][col] == player)
                return true;
        }
        // check diagonals
        if (grid[0][0] == player && grid[1][1] == player && grid[2][2] == player)
            return true;
        if (grid[0][2] == player && grid[1][1] == player && grid[2][0] == player)
            return true;

        return false;
    }

    public char getWinner() {
        return this.winner;
    }

    public boolean isEnd() {
        return this.end;
    }

    public char getCurrentPlayer() {
        return this.currentPlayer;
    }

    public char[][] getGrid() {
        return this.grid;
    }
}
