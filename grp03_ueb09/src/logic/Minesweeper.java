package logic;


/**
 * The class Minesweeper is responsible for the logical aspects of the game.
 * The class is used to build the whole logic of the game, certain methods to perform
 * certain sort of operations and set the information in the setter methods in cells
 * and get the information from gui and perform actions based upon that information
 * then also set some actions on the gui using the class javafx
 *
 * @author chien-hsun, husnain
 */
public class Minesweeper {

    /**
     * Connection to the gui.
     */
    private GUIConnector gui;

    /**
     * a two-dimensional array with cells (see class Cell below)
     */
    private Cell[][] cells;


    /**
     * a boolean if the game has ended - once a game has ended, the user may not change anything on the field
     */
    private boolean endgame;

    /**
     * getter for cells
     *
     * @return cells in array
     */
    public Cell[][] getCells() {
        return cells;
    }

    /**
     * one that gets a GUIConnector, the number of columns, the number of rows and the chance to hit a bomb
     */
    public Minesweeper(GUIConnector gui, int columns, int rows, int chanceToHitBomb) {
        endgame = false;
        this.gui = gui;
        this.cells = new Cell[columns][rows];
        int totalBombs = 0; // to pass total number of bombs inside the field to javafx
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                cells[col][row] = new Cell(false);
                if (((float) Math.random()) < ((float) chanceToHitBomb / 100)) {
                    cells[col][row] = new Cell(true);
                    totalBombs++;
                }

            }
        }

        for (int row = 0; row < cells.length; row++) {      // -1 ~ 2
            for (int col = 0; col < cells[row].length; col++) {
                cells[col][row].setNoOfBombsNeighboured(getNoOfBombsNeighboured(row, col));
            }
        }

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                gui.coverCell(i, j);
            }
        }

        gui.displayNoOfBombs(totalBombs);
    }


    /**
     * one solely for testing that gets a gui connector and a String representation of the cells.
     * <p>
     * Careful: in the String the cells are given in the order in which they would be displayed on the screen (first
     * line in the string = first line on the screen).
     * Due to the structure of arrays in Java, this means you cannot directly take position in the String
     * -> position in the array. String consists of multiple lines (one line for each line of the game field),
     * and the line consists for every column of the game field at most two characters seperated through a whitespace.
     * The first character of these at most two characters gives you
     * the state of the cell (? -> covered, S -> suspected, ! -> uncovered).
     * If the cell contains a bomb the second character is 'B' - if the cell does not contain a bomb,
     * there is no second character.
     * Example: ~~~ ? ? ? ! ?B S ? ? ? ?B ? S ~~~ Refer to the tests to see more about
     * the behaviour of this constructor.
     * Tip: Use String.split() to split the given String in lines and then to split each line into individual cells.
     * You may also want to use String.endsWith() and String.startsWith().
     * <p>
     */
    Minesweeper(GUIConnector gui, String stringOfCells) {
        this.gui = gui;
        String[] afterFirstSplit1 = stringOfCells.split("\n");
        this.cells = new Cell[afterFirstSplit1.length][afterFirstSplit1.length];
        for (int i = 0; i < afterFirstSplit1.length; i++) {
            String[] arrayToStoreEverySecSplit2 = afterFirstSplit1[i].split(" ");
            for (int j = 0; j < arrayToStoreEverySecSplit2.length; j++) {

                boolean flag = false;
                if (arrayToStoreEverySecSplit2[j].endsWith("B")) {
                    flag = true;
                }
                if (arrayToStoreEverySecSplit2[j].startsWith("?")) {
                    cells[j][i] = new Cell(flag, Cell.CellState.COVERED);
                } else if (arrayToStoreEverySecSplit2[j].startsWith("S")) {
                    cells[j][i] = new Cell(flag, Cell.CellState.SUSPECTED);
                } else if (arrayToStoreEverySecSplit2[j].startsWith("!")) {
                    cells[j][i] = new Cell(flag, Cell.CellState.UNCOVERED);
                }
            }
        }

        for (int row = 0; row < cells.length; row++) {      // -1 ~ 2
            for (int col = 0; col < cells[row].length; col++) {
                cells[col][row].setNoOfBombsNeighboured(getNoOfBombsNeighboured(row, col));
            }
        }

    }

    /**
     * called if the user wants to uncover the cell at the given position (x,y).
     * If the user chooses a cell that has already been uncovered, nothing happens.
     * Same goes for suspected cells.
     *
     * @param x column
     * @param y Row
     */
    public void uncover(int x, int y) {

        if (!endgame && !cells[y][x].isUncovered() && !cells[y][x].isSuspected()) {

            if (!cells[y][x].hasBomb()) {
                cells[y][x].uncover();
                int bombs = cells[y][x].getNoOfBombsNeighboured();
                gui.uncoverCell(x, y, cells[y][x].hasBomb(), bombs);
                if (bombs == 0) {
                    uncoveredNeighboringCell(x, y);
                }
            } else {
                // it has the bomb
                endgame = true;
                gui.gameHasEnded(false);
                uncoveringAllCells();
            }
        }
    }

    /**
     * additional method
     * uncovering all cells when there is a bomb hit by the player in the field
     */
    private void uncoveringAllCells() {
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                gui.uncoverCell(i, j, cells[j][i].hasBomb(), cells[j][i].getNoOfBombsNeighboured());
            }
        }
    }

    /**
     * additional method
     * We strongly recommend to add additional private methods, e.g. \
     * for recursively uncover neighbouring cells when necessary.
     * <p>
     * // show the neighbours
     * // if any of the neighbour is empty show the neighbour of the empty cell
     * // keep on doing with all
     *
     * @param x col
     * @param y Row
     */
    private void uncoveredNeighboringCell(int x, int y) {

        for (int row = x - 1; row < x + 2; row++) {
            for (int col = y - 1; col < y + 2; col++) {
                if (areValidCoords(row, col)) {     // checking valid coordinate
                    int neighboursBomb = cells[col][row].getNoOfBombsNeighboured();
                    if (!cells[col][row].isUncovered()) {
                        cells[col][row].uncover();
                        gui.uncoverCell(row, col, cells[col][row].hasBomb(), neighboursBomb);
                        if (neighboursBomb == 0) {    // get into another empty block
                            uncoveredNeighboringCell(row, col);
                        }
                    }
                }
            }
        }
    }


    /**
     * called if the user suspects a bomb at the given position (x,y). Once a cell has been marked as suspected,
     * * it cannot be uncovered anymore. An uncovered cell cannot be marked as suspected
     * <p>
     * <p>
     * is in its neighbours, and it is also empty Are we suppose to open then suspected box as well
     * if it does't contain a bomb,
     * <p>
     * // maybe we need Cell[][] array as return type
     * //Using toggleSuspected, but need condition to check
     * //using additional check before calling toggle method
     * // using isMarkedCorrectly
     */
    public void suspectBomb(int x, int y) {
        if (!endgame && !cells[y][x].isUncovered()) {
            if (!cells[y][x].isSuspected()) {
                cells[y][x].toggleSuspected();
                gui.markCellAsSuspected(x, y);
            } else {
                cells[y][x].toggleSuspected();
                gui.coverCell(x, y);
            }
            if (won()) {
                endgame = true;
                gui.gameHasEnded(true);
            }
        }


    }


    /**
     * determines the number of bombs neighbouring the cell at the given position
     * <p>
     * // x is coloms // y is rows
     *
     * @param x col
     * @param y Row
     * @return total number of bombs in the neighbours
     */
    int getNoOfBombsNeighboured(int x, int y) {
        int neighboursBombCounter = 0;
        for (int xFromUs = x - 1; xFromUs < x + 2; xFromUs++) {
            for (int yFromUs = y - 1; yFromUs < y + 2; yFromUs++) {
                if (areValidCoords(xFromUs, yFromUs)) {
                    if (cells[yFromUs][xFromUs].hasBomb()) {
                        neighboursBombCounter++;
                        if (xFromUs == x && yFromUs == y) {
                            neighboursBombCounter--;
                        }

                    }
                }
            }

        }
        return neighboursBombCounter;
    }


    /**
     * if the given coordinates (x,y) are valid one for the current field
     * // by using this methods for counting bombs
     *
     * @param x col
     * @param y Row
     * @return true if x and y are valid co-ordinated for the field
     */
    boolean areValidCoords(int x, int y) {
        return (0 <= x && x < cells.length && 0 <= y && y < cells.length);
    }

    /**
     * checks if all cell with bombs are marked as suspected and no other cell without a bomb is marked as suspected.
     * // Discription of marked correctly
     * a cell is marked correctly if it is suspected and contains a bomb OR if it not marked as suspected and does not
     * * contain bomb
     * * (decide on proper parameters and return types)
     *
     * @return true if someone won the game
     */
    boolean won() {
        boolean gameWon = true;
        for (int i = 0; i < cells.length && gameWon; i++) {
            for (int j = 0; j < cells[i].length && gameWon; j++) {
                if (!cells[j][i].isMarkedCorrectly()) {
                    gameWon = false;
                }
            }
        }
        return gameWon;
    }

    /**
     * @return String information the form of string
     */
    @Override
    public String toString() {
        String output = "";
        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[row].length; col++) {
                output = output + cells[row][col].toString();
                if (col != cells[row].length - 1) {
                    output = output + " ";
                }
            }
            output = output + "\n";
        }
        return output;
    }


}