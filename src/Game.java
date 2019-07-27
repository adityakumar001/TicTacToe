import javax.swing.*;
import java.util.Random;

public class Game {

    static final int PLAYER_1_WINS = 0;
    static final int PLAYER_2_WINS = 1;
    static final int DRAWS_INDEX = 2;
    private static Game game;
    private int[] scores = {0, 0, 0};
    private int totalMatches = 0;
    private JFrame gameWindow;
    private Board board;
    private int matrixSize;
    private Player player1, player2;
    private Cell[] cells;
    private int nEmptyCells;


    private Game(int matrixSize) {
        this.matrixSize = matrixSize;
        Cell.CONTAINER_SIZE = matrixSize;
        cells = new Cell[matrixSize * matrixSize];

    }

    static Game getInstance() {
        if (game == null)
            game = new Game(3);
        return game;
    }

    public static void main(String[] args) {
        //Creates the game with a default configuration.
        getInstance().startGame();
    }

    int getTotalMatches() {
        return totalMatches;
    }

    void startGame() {
        setUpCells();
        nEmptyCells = cells.length;

        if (player2 == null && player1 == null) {
            player1 = new Player();
            player2 = new Player();
        }

        //Randomly chooses which player will go first...
        switch (new Random().nextInt(2)) {
            case 0:
                player1.setSymbol(Cell.X);
                player2.setSymbol(Cell.O);
                player1.setPlayed(false);
                break;
            case 1:
                player2.setSymbol(Cell.X);
                player1.setSymbol(Cell.O);
                player1.setPlayed(true);
                break;
        }

        if (gameWindow == null)
            gameWindow = new JFrame();
        else if (board != null)
            gameWindow.remove(board);

        board = new Board();
        gameWindow.add(board);
        gameWindow.pack();
        gameWindow.setResizable(false);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setLocationRelativeTo(null);
        gameWindow.revalidate();
        gameWindow.setVisible(true);

    }


    Cell[] getCells() {
        return cells;
    }

    void markCell(int row, int column) {
        Cell toMark = cells[row * matrixSize + column];
        if (!toMark.isDisabled()) {
            nEmptyCells--;
            if (player1.hasPlayed()) {
                toMark.setSymbol(player2.getSymbol());
                toMark.disable();
                player1.setPlayed(false);
            } else {
                toMark.setSymbol(player1.getSymbol());
                toMark.disable();
                player1.setPlayed(true);
            }

            if (checkRepeats(toMark)) {
                if (player1.getSymbol() == toMark.getSymbol()) {
                    scores[Game.PLAYER_1_WINS]++;
                } else if (player2.getSymbol() == toMark.getSymbol()) {
                    scores[Game.PLAYER_2_WINS]++;
                }
                totalMatches++;
                JOptionPane.showMessageDialog(board, toMark.getSymbol() + " wins !!!");
                reset();
            } else if (nEmptyCells == 0) {
                scores[Game.DRAWS_INDEX]++;
                totalMatches++;
                JOptionPane.showMessageDialog(board, "It's a draw !!!");
                reset();
            }
            logBoard();
        }

    }

    private void reset() {
        this.startGame();
    }

    private void setUpCells() {
        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                cells[matrixSize * row + col] = new Cell(Cell.EMPTY, row, col);
            }
        }
    }

    private void logBoard() {
        System.out.println(matrixSize);
        for (int row = 0; row < matrixSize; row++) {
            for (int col = 0; col < matrixSize; col++) {
                System.out.print(cells[matrixSize * row + col].getSymbol() + "\t");
            }
            System.out.println();
        }
    }

    Player getPlayer1() {
        return player1;
    }

    Player getPlayer2() {
        return player2;
    }

    //Return true if the clicked symbol won..
    private boolean checkRepeats(Cell clickedCell) {
        boolean repeatedRow = true, repeatedColumn = true, repeatedDiagonal = true;
        for (int i = 0; i < matrixSize; i++) {
            repeatedColumn &= cells[matrixSize * i + clickedCell.getColumn()].getSymbol() == clickedCell.getSymbol();
            repeatedRow &= cells[matrixSize * clickedCell.getRow() + i].getSymbol() == clickedCell.getSymbol();
            if (clickedCell.isDiagonalL()) {
                repeatedDiagonal &= cells[i * matrixSize + i].getSymbol() == clickedCell.getSymbol();
            } else if (clickedCell.isDiagonalR()) {
                repeatedDiagonal &= cells[i * matrixSize + matrixSize - i - 1].getSymbol()
                        == clickedCell.getSymbol();
            }
        }
        repeatedDiagonal &= clickedCell.isDiagonalL() || clickedCell.isDiagonalR();
        return repeatedRow || repeatedColumn || repeatedDiagonal;
    }

    int getMatrixSize() {
        return matrixSize;
    }

    void setMatrixSize(int i) {
        this.matrixSize = i;
        this.cells = new Cell[i * i];
    }

    int[] getScores() {
        return scores;
    }
}
