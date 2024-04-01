import java.util.*;

class TennerGridCSP {
    int[][] grid;
    int[][] sums;
    int nRows, nCols;
    boolean[][] domain;

    public TennerGridCSP(int[][] grid, int[][] sums) {
        this.grid = grid;
        this.sums = sums;
        this.nRows = grid.length;
        this.nCols = grid[0].length;
        this.domain = new boolean[nRows][nCols * 9];
        initializeDomain();
    }

    private void initializeDomain() {
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                for (int k = 0; k < 9; k++) {
                    domain[i][j * 9 + k] = true; // Initialize domain to all true
                }
            }
        }
    }

    public boolean isConsistent(int[][] assignment, int row, int col, int num) {
        // Check row consistency
        for (int c = 0; c < nCols; c++) {
            if (c != col && assignment[row][c] == num) return false;
        }
        // Check column consistency
        int sum = 0;
        for (int r = 0; r < nRows; r++) {
            if (r != row && assignment[r][col] == num) return false;
            sum += assignment[r][col];
        }
        if (sum > sums[0][col]) return false; // Check column sum
        return true;
    }

    private void updateDomain(int[][] assignment, int row, int col, int num) {
        // Update domain based on assigned value
        for (int c = 0; c < nCols; c++) {
            if (c != col && domain[row][c * 9 + num - 1]) {
                domain[row][c * 9 + num - 1] = false;
            }
        }
        for (int r = 0; r < nRows; r++) {
            if (r != row && domain[r][col * 9 + num - 1]) {
                domain[r][col * 9 + num - 1] = false;
            }
        }
    }

    public boolean solveBacktracking(int[][] assignment) {
        return backtrack(assignment, 0, 0);
    }

    private boolean backtrack(int[][] assignment, int row, int col) {
        if (row == nRows) return true; // Base case, solution found
        int nextRow = (col == nCols - 1) ? row + 1 : row;
        int nextCol = (col + 1) % nCols;

        if (assignment[row][col] != 0) {
            return backtrack(assignment, nextRow, nextCol); // Skip filled cells
        }

        for (int num = 1; num <= 9; num++) {
            if (domain[row][col * 9 + num - 1] && isConsistent(assignment, row, col, num)) {
                assignment[row][col] = num;
                updateDomain(assignment, row, col, num);
                if (backtrack(assignment, nextRow, nextCol)) return true;
                assignment[row][col] = 0; // Undo assignment
                updateDomain(assignment, row, col, num); // Restore domain
            }
        }
        return false;
    }

    public boolean solveForwardChecking(int[][] assignment) {
        return forwardCheck(assignment, 0, 0);
    }

    private boolean forwardCheck(int[][] assignment, int row, int col) {
        if (row == nRows) return true; // Base case, solution found
        int nextRow = (col == nCols - 1) ? row + 1 : row;
        int nextCol = (col + 1) % nCols;

        if (assignment[row][col] != 0) {
            return forwardCheck(assignment, nextRow, nextCol); // Skip filled cells
        }

        for (int num = 1; num <= 9; num++) {
            if (domain[row][col * 9 + num - 1] && isConsistent(assignment, row, col, num)) {
                assignment[row][col] = num;
                updateDomain(assignment, row, col, num);
                if (forwardCheck(assignment, nextRow, nextCol)) return true;
                assignment[row][col] = 0; // Undo assignment
                updateDomain(assignment, row, col, num); // Restore domain
            }
        }
        return false;
    }

    public void printGrid(int[][] assignment) {
        for (int[] row : assignment) {
            System.out.println(Arrays.toString(row));
        }
    }
}

