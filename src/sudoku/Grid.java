package sudoku;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Represents a sudoku object.
 */
public class Grid {

	private final Cell[][] grid;
	private Grid initialGrid;

	public Grid getInitialGrid() {
		return initialGrid;
	}

	public void setInitialGrid(Grid initialGrid) {
		this.initialGrid = initialGrid;
	}

	public Grid(Cell[][] grid) {
		this.grid = grid;
	}

	/**
	 * Transforms a 2 dimensions array into a grid object.
	 * @param grid
	 * @return a sudoku grid
	 */
	public static Grid of(int[][] grid) {
		verifyGrid(grid);

		Cell[][] cells = new Cell[9][9];
		List<List<Cell>> rows = new ArrayList<>();
		List<List<Cell>> columns = new ArrayList<>();
		List<List<Cell>> boxes = new ArrayList<>();

		for (int i = 0; i < 9; i++) {
			rows.add(new ArrayList<Cell>());
			columns.add(new ArrayList<Cell>());
			boxes.add(new ArrayList<Cell>());
		}

		Cell lastCell = null;
		for (int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++) {
				Cell cell = new Cell(grid[row][column]);
				cells[row][column] = cell;

				rows.get(row).add(cell);
				columns.get(column).add(cell);
				boxes.get((row / 3) * 3 + column / 3).add(cell);

				if (lastCell != null) {
					lastCell.setNextCell(cell);
				}

				lastCell = cell;
			}
		}

		for (int i = 0; i < 9; i++) {
			List<Cell> row = rows.get(i);
			for (Cell cell : row) {
				List<Cell> rowNeighbors = new ArrayList<>(row);
				rowNeighbors.remove(cell);

				cell.setRowNeighbors(rowNeighbors);
			}

			List<Cell> column = columns.get(i);
			for (Cell cell : column) {
				List<Cell> columnNeighbors = new ArrayList<>(column);
				columnNeighbors.remove(cell);

				cell.setColumnNeighbors(columnNeighbors);
			}

			List<Cell> box = boxes.get(i);
			for (Cell cell : box) {
				List<Cell> boxNeighbors = new ArrayList<>(box);
				boxNeighbors.remove(cell);

				cell.setBoxNeighbors(boxNeighbors);
			}
		}
		return new Grid(cells);
	}
	
	/**
	 * Generate an empty sudoku grid.
	 * @return an empty sudoku.
	 */
	public static Grid emptyGrid() {
		int[][] emptyGrid = new int[9][9];
		return Grid.of(emptyGrid);
	}

	/**
	 * Check the properties for a valid sudoku grid.
	 * @param grid
	 */
	public static void verifyGrid(int[][] grid) {
		if (grid == null)
			throw new IllegalArgumentException("Grid must not be null");

		if (grid.length != 9)
			throw new IllegalArgumentException("Grid must have nine rows");

		for (int[] row : grid) {
			if (row.length != 9) {
				throw new IllegalArgumentException("Grid must have nine columns");
			}

			for (int value : row) {
				if (value < 0 || value > 9) {
					throw new IllegalArgumentException("Grid must contain values from 0-9");
				}
			}
		}
	}

	public int getSize() {
		return grid.length;
	}

	public Cell getCell(int row, int column) {
		return grid[row][column];
	}
	
	public void setCellValue(int row, int column, int value){
	    grid[row][column].setValue(value);
	  }
	
	/**
	 * Transforms a grid object into a 2 dimensions array.
	 * @return a  2 dimensions array
	 */
	public int[][] tab() {
		int[][] tab = new int[9][9];
		int size = grid.length;
		for (int row = 0; row < size; row++) {
			for (int column = 0; column < size; column++) {
				Cell cell = grid[row][column];
				tab[row][column] = cell.getValue();
			}
		}
		return tab;
	}

	public boolean isValidValueForCell(Cell cell, int value) {
		return isValidInRow(cell, value) && isValidInColumn(cell, value) && isValidInBox(cell, value);
	}

	private boolean isValidInRow(Cell cell, int value) {
		return !getRowValuesOf(cell).contains(value);
	}

	private boolean isValidInColumn(Cell cell, int value) {
		return !getColumnValuesOf(cell).contains(value);
	}

	private boolean isValidInBox(Cell cell, int value) {
		return !getBoxValuesOf(cell).contains(value);
	}

	private Collection<Integer> getRowValuesOf(Cell cell) {
		List<Integer> rowValues = new ArrayList<>();
		for (Cell neighbor : cell.getRowNeighbors())
			rowValues.add(neighbor.getValue());
		return rowValues;
	}

	private Collection<Integer> getColumnValuesOf(Cell cell) {
		List<Integer> columnValues = new ArrayList<>();
		for (Cell neighbor : cell.getColumnNeighbors())
			columnValues.add(neighbor.getValue());
		return columnValues;
	}

	private Collection<Integer> getBoxValuesOf(Cell cell) {
		List<Integer> boxValues = new ArrayList<>();
		for (Cell neighbor : cell.getBoxNeighbors())
			boxValues.add(neighbor.getValue());
		return boxValues;
	}

	public Optional<Cell> getFirstEmptyCell() {
		Cell firstCell = grid[0][0];
		if (firstCell.isEmpty()) {
			return Optional.of(firstCell);
		}

		return getNextEmptyCellOf(firstCell);
	}

	public Optional<Cell> getNextEmptyCellOf(Cell cell) {
		Cell nextEmptyCell = null;

		while ((cell = cell.getNextCell()) != null) {
			if (!cell.isEmpty()) {
				continue;
			}

			nextEmptyCell = cell;
			break;
		}

		return Optional.ofNullable(nextEmptyCell);
	}

	public boolean add(int col, int row, int value) {
		if (initialGrid.grid[row][col].isEmpty() && value != 0) {
			if (isValidValueForCell(grid[row][col], value)) {
				grid[row][col].setValue(value);
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return StringConverter.toString(this);
	}
	
	/**
	 * Represents a cell of a grid.	 *
	 */
	public static class Cell {
		private int value;
		private Collection<Cell> rowNeighbors;
		private Collection<Cell> columnNeighbors;
		private Collection<Cell> boxNeighbors;
		private Cell nextCell;

		public Cell(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public boolean isEmpty() {
			return value == 0;
		}

		public void setValue(int value) {
			this.value = value;
		}

		public Collection<Cell> getRowNeighbors() {
			return rowNeighbors;
		}

		public void setRowNeighbors(Collection<Cell> rowNeighbors) {
			this.rowNeighbors = rowNeighbors;
		}

		public Collection<Cell> getColumnNeighbors() {
			return columnNeighbors;
		}

		public void setColumnNeighbors(Collection<Cell> columnNeighbors) {
			this.columnNeighbors = columnNeighbors;
		}

		public Collection<Cell> getBoxNeighbors() {
			return boxNeighbors;
		}

		public void setBoxNeighbors(Collection<Cell> boxNeighbors) {
			this.boxNeighbors = boxNeighbors;
		}

		public Cell getNextCell() {
			return nextCell;
		}

		public void setNextCell(Cell nextCell) {
			this.nextCell = nextCell;
		}
	}
	
	/**
	 * Constructor for displaying a sudoku in the view.
	 */
	private static class StringConverter {
		public static String toString(Grid grid) {
			StringBuilder builder = new StringBuilder();
			int size = grid.getSize();

			printTopBorder(builder);
			for (int row = 0; row < size; row++) {
				builder.append((row + 1) + " ");
				printRowBorder(builder);
				for (int column = 0; column < size; column++) {
					printValue(builder, grid, row, column);
					printRightColumnBorder(builder, column + 1, size);
				}
				printRowBorder(builder);
				builder.append("\n");
				printBottomRowBorder(builder, row + 1, size);
			}
			printBottomBorder(builder);

			return builder.toString();
		}

		private static void printTopBorder(StringBuilder builder) {
			builder.append("  ??? A ??? B ??? C ??? D ??? E ??? F ??? G ??? H ??? I ???\n");
			builder.append("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\n");
		}

		private static void printRowBorder(StringBuilder builder) {
			builder.append("???");
		}

		private static void printValue(StringBuilder builder, Grid grid, int row, int column) {
			int value = grid.getCell(row, column).getValue();
			String output = value != 0 ? String.valueOf(value) : " ";
			builder.append(" " + output + " ");
		}

		private static void printRightColumnBorder(StringBuilder builder, int column, int size) {
			if (column == size) {
				return;
			}

			if (column % 3 == 0) {
				builder.append("???");
			} else {
				builder.append("???");
			}
		}

		private static void printBottomRowBorder(StringBuilder builder, int row, int size) {
			if (row == size) {
				return;
			}

			if (row % 3 == 0) {
				builder.append("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\n");
			} else {
				builder.append("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\n");
			}
		}

		private static void printBottomBorder(StringBuilder builder) {
			builder.append("?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????\n");
		}
	}
}