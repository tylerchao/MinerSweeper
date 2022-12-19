package logic;

/**
 * This class is used to perform certain sort of operations on cells,
 * like making cell covered, uncovered and suspected when there is an action occured on the
 * gui, there are also certain kind of methods to get the information from this class
 * and make use of it in Minesweeper class to build our logic
 *
 * @author chien-hsun, husnain
 */
public class Cell {

    /**
     * an protected enum CellState with three elements for
     * the three states of the cell covered, suspected and uncovered
     */
    protected enum CellState {
        COVERED, SUSPECTED, UNCOVERED
    }

    private CellState currentStateOfCell;  // the current state of the cell
    private boolean containsBomb;       //  if it contains a bomb
    private int numOfNeighborBombs;    //  the number of bombs on the 8 neighboring cells

    /**
     * one only with hasBomb as parameter. Initial state of a cell should be covered.
     *
     * @param hasBomb true if has the bomb
     */
    Cell(boolean hasBomb) {
        this.containsBomb = hasBomb;
        this.currentStateOfCell = CellState.COVERED;  //Initial state of a cell should be covered.
    }

    /**
     * one solely for testing, with hasBomb and the current state of the cell
     * constructor for testing
     *
     * @param currentStateOfCell the current state of the cell which can be covered,
     *                           uncovered or suspected
     */
    Cell(boolean hasBomb, CellState currentStateOfCell) {
        this(hasBomb);
        this.currentStateOfCell = currentStateOfCell;
    }


    /**
     * if the cell has a bomb
     * (decide on proper parameters and return types)
     *
     * @return true if it contains a bomb
     */
    public boolean hasBomb() {
        return containsBomb;
    }


    /**
     * returns the number of neighbouring bombs for this cell (at most 8)
     * (decide on proper parameters and return types)
     *
     * @return number of bombs in the neighbours
     */
    public int getNoOfBombsNeighboured() {
        if (this.numOfNeighborBombs > 8) {
            throw new AssertionError();
        }
        return this.numOfNeighborBombs;
    }


    /**
     * if the cell is suspected of containing a bomb
     * (decide on proper parameters and return types)
     *
     * @return true if it is suspected
     */
    public boolean isSuspected() {
        return currentStateOfCell == CellState.SUSPECTED;
    }

    /**
     * if the cell has been uncovered
     * (decide on proper parameters and return types)
     *
     * @return true if it is uncovered
     */
    public boolean isUncovered() {
        return currentStateOfCell == CellState.UNCOVERED;
    }

    /**
     * a cell is marked correctly if it is suspected and contains a bomb OR if it not marked as suspected and does not
     * contain bomb
     * (decide on proper parameters and return types)
     *
     * @return true if it is marked correctly
     */
    public boolean isMarkedCorrectly() {
        return (isSuspected() && hasBomb()) || (!isSuspected() && !hasBomb());
    }


    /**
     * uncovers the cell (changes the state)
     * (decide on proper parameters and return types)
     */
    public void uncover() {
        this.currentStateOfCell = CellState.UNCOVERED;
    }

    /**
     * sets the number of neighbouring bombs for this cell
     * (decide on proper parameters and return types)
     * setter
     *
     * @param numOfNeighborBombs no of bombs in the neighbours
     */
    public void setNoOfBombsNeighboured(int numOfNeighborBombs) {
        this.numOfNeighborBombs = numOfNeighborBombs;
    }

    /**
     * changes the state for the cell (from covered to suspected and the other way around)
     * (decide on proper parameters and return types)
     */
    public void toggleSuspected() {
        if (this.currentStateOfCell == CellState.COVERED) {
            this.currentStateOfCell = CellState.SUSPECTED;
        } else if (isSuspected()) {
            this.currentStateOfCell = CellState.COVERED;
        }
    }

    /**
     * start with three states and check the bomb
     *
     * @return cells information in the form of string
     */
    @Override
    public String toString() {
        String output = "";

        if (currentStateOfCell == CellState.COVERED) {
            output = "?";
        } else if (currentStateOfCell == CellState.SUSPECTED) {
            output = "S";
        } else if (currentStateOfCell == CellState.UNCOVERED) {
            output = "!";
        }

        if (hasBomb()) {
            output = output + "B";
        }
        return output;
    }
}
