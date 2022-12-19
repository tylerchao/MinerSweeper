package logic;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests for the cell class published with the assignment.
 *
 * @author cei
 */
public class PubCellTest {

    @Test
    public void testCellConstructor_Bomb() {
        Cell bombCell = new Cell(true);
        assertFalse(bombCell.isUncovered());
        assertTrue(bombCell.hasBomb());
        assertEquals(0, bombCell.getNoOfBombsNeighboured());
        assertFalse(bombCell.isSuspected());
        assertFalse(bombCell.isMarkedCorrectly());
    }

    @Test
    public void testCellConstructor_NoBomb() {
        Cell noBombCell = new Cell(false);
        assertFalse(noBombCell.isUncovered());
        assertFalse(noBombCell.hasBomb());
        assertEquals(0, noBombCell.getNoOfBombsNeighboured());
        assertFalse(noBombCell.isSuspected());
        assertTrue(noBombCell.isMarkedCorrectly());
    }

    @Test
    public void test_TestCellConstructor_Bomb_Covered() {
        Cell bombCell = new Cell(true, Cell.CellState.COVERED);
        assertFalse(bombCell.isUncovered());
        assertTrue(bombCell.hasBomb());
        assertEquals(0, bombCell.getNoOfBombsNeighboured());
        assertFalse(bombCell.isSuspected());
        assertFalse(bombCell.isMarkedCorrectly());
    }

    @Test
    public void test_TestCellConstructor_NoBomb_Covered() {
        Cell noBombCell = new Cell(false);
        assertFalse(noBombCell.isUncovered());
        assertFalse(noBombCell.hasBomb());
       assertEquals(0, noBombCell.getNoOfBombsNeighboured());
        assertFalse(noBombCell.isSuspected());
        assertTrue(noBombCell.isMarkedCorrectly());
    }

    @Test
    public void test_TestCellConstructor_Bomb_Suspected() {
        Cell bombCell = new Cell(true, Cell.CellState.SUSPECTED);
        assertFalse(bombCell.isUncovered());
        assertTrue(bombCell.hasBomb());
        assertEquals(0, bombCell.getNoOfBombsNeighboured());
        assertTrue(bombCell.isSuspected());
        assertTrue(bombCell.isMarkedCorrectly());
    }

    @Test
    public void test_TestCellConstructor_NoBomb_Suspected() {
        Cell noBombCell = new Cell(false, Cell.CellState.SUSPECTED);
        assertFalse(noBombCell.isUncovered());
        assertFalse(noBombCell.hasBomb());
       assertEquals(0, noBombCell.getNoOfBombsNeighboured());
        assertTrue(noBombCell.isSuspected());
        assertFalse(noBombCell.isMarkedCorrectly());
    }

    @Test
    public void test_TestCellConstructor_Bomb_Uncovered() {
        Cell bombCell = new Cell(true, Cell.CellState.UNCOVERED);
       assertTrue(bombCell.isUncovered());
        assertTrue(bombCell.hasBomb());
       assertEquals(0, bombCell.getNoOfBombsNeighboured());
        assertFalse(bombCell.isSuspected());
        assertFalse(bombCell.isMarkedCorrectly());
    }

    @Test
    public void test_TestCellConstructor_NoBomb_Uncovered() {
        Cell noBombCell = new Cell(false, Cell.CellState.UNCOVERED);
        assertTrue(noBombCell.isUncovered());
        assertFalse(noBombCell.hasBomb());
        assertEquals(0, noBombCell.getNoOfBombsNeighboured());
        assertFalse(noBombCell.isSuspected());
        assertTrue(noBombCell.isMarkedCorrectly());
    }

    @Test
    public void test_toggleSuspected_StartingWithCovered() {
        Cell cell = new Cell(true, Cell.CellState.COVERED);
        cell.toggleSuspected();
        assertFalse(cell.isUncovered());
        assertTrue(cell.isSuspected());
    }

    @Test
    public void test_toggleSuspected_StartingWithSuspected() {
        Cell cell = new Cell(true, Cell.CellState.SUSPECTED);
        cell.toggleSuspected();
        assertFalse(cell.isUncovered());
        assertFalse(cell.isSuspected());
    }

    @Test
    public void test_toggleSuspected_CellAlreadyUncovered() {
        Cell cell = new Cell(true, Cell.CellState.UNCOVERED);
        cell.toggleSuspected();
        assertTrue(cell.isUncovered());
        assertFalse(cell.isSuspected());
    }

}