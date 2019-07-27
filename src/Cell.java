public class Cell implements Cloneable {

    static final char X = 'X', O = 'O', EMPTY = 'E';
    static final int CELL_SIZE = 100;
    static int CONTAINER_SIZE;
    private char symbol;
    private int row, column;
    private boolean disabled, diagonalR, diagonalL;

    Cell(char symbol, int row, int column) {
        this.symbol = symbol;
        this.row = row;
        this.column = column;
        disabled = false;
        diagonalR = row + column == CONTAINER_SIZE - 1;
        diagonalL = row == column;
    }

    char getSymbol() {
        return symbol;
    }

    void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }


    boolean isDisabled() {
        return disabled;
    }

    void disable() {
        this.disabled = true;
    }

    boolean isDiagonalR() {
        return diagonalR;
    }


    boolean isDiagonalL() {
        return diagonalL;
    }


    @Override
    public String toString() {
        return "( " + row + ", " + column + " )" + " disabled :" + disabled + " on a diagonal :" + (diagonalL || diagonalR);
    }
}
