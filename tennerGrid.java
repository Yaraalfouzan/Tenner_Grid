
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class tennerGrid {
    private int[][] grid;
    static int numRows=4;
    static int numColumns=10;
    static int consistency;
    private static boolean found;
    private static boolean[][][] domains;

    static int assignments;
static Scanner input=new Scanner(System.in);
  

    // Method to generate an initial state adhering to the rules

     // Method to generate an initial state adhering to the rules
     static int[][] generateInitialState() {

        int[][] grid=new int[numRows][numColumns];

        Arrays.setAll(grid, i -> new int[numColumns]);
        Arrays.stream(grid).forEach(row -> Arrays.fill(row, -1));
        for (int col = 0; col < numColumns; col++) {
            grid[numRows - 1][col] +=1;}

        Random random = new Random();

        for (int col = 0; col < numColumns; col++) {
            for (int row = 0; row < numRows - 1; row++) {
                    
                        int num = random.nextInt(10); // Generate random number (0-9)
                        int counter=1;
                         found=true;
                        while ( ! isValid(grid,row,col,num) ){
                        num = random.nextInt(10);
                        counter++;
                        if (counter==10){
                            found=false;
                        break;}
                    } 
                    if (found!=false){
                    grid[row][col] = num;
                    grid[numRows - 1][col] += num;}
                        
                    }}
                    for (int col = 0; col < numColumns; col++) {
                        for (int row = 0; row < numRows - 1; row++) {
                            if (grid[row][col] == -1)
                            return null;

            }}
            printGrid(grid);
            for (int col = 0; col < numColumns; col++) {
                for (int row = 0; row < numRows - 1; row++) {
            if (random.nextBoolean())
            grid[row][col] = -1;}}
            return grid;}
            
        
        
        public static boolean solveTennerGridWithForwardChecking(int[][] grid, boolean[][][] domains) {
            int row = -1, col = -1;
            boolean isEmpty = true;
            int rows = numRows-1, cols = numColumns;
        
            // Find an empty cell
            for (int i = 0; i <rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (grid[i][j] == -1) {
                        row = i;
                        col = j;
                        isEmpty = false;
                        break;
                    }
                }
                if (!isEmpty) break;
            }
        
            // If there is no empty cell, the grid is solved
            if (isEmpty) return true;
        
            // Try filling the empty cell with valid numbers from domain
            for (int num = 0; num < 10; num++) {
                if (domains[row][col][num] && isValid(grid, row, col, num)) {
                    grid[row][col] = num;
                    assignments++;
                    forwardCheckdomain(grid, domains, row, col, num, false);
                   
                        if (solveTennerGridWithForwardChecking(grid, domains)) {
                            return true;
                        }
                    
                    grid[row][col] = -1; // Backtrack
                    assignments++;
                    forwardCheckdomain(grid, domains, row, col, num, true);
                     
                }
            }
        
            return false;
        }
        
// Initialize domains for all cells to be true for all numbers 0-9 call it in main b4 applying forward checking
static void initializeDomains(boolean[][][] grid) {
    int rows = numRows-1;
    int cols = numColumns;
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            for (int num = 0; num < 10; num++) {
            grid[i][j][num] = true; // Initially, all numbers are valid options
            }
        }
    }
}
// Safe update helper function
static void safeUpdate(int r, int c, int n, boolean upd) {
    if (r >= 0 && r < numRows && c >= 0 && c < numColumns) {
        domains[r][c][n] = upd;
    }
}
static void forwardCheckdomain(int[][] grid, boolean[][][] domains, int row, int col, int num,boolean update) {
   
    // Update all columns in the given row for the specified number
    for (int j = 0; j < numColumns; j++) {
        domains[row][j][num] = update;
    }

     

    // update the cell diagonally up-left from the current cell
    safeUpdate(row - 1, col - 1, num, update);

    // update the cell diagonally down-right from the current cell
    safeUpdate(row + 1, col + 1, num, update);

    // update the cell diagonally down-left from the current cell
    safeUpdate(row + 1, col - 1, num, update);

    // update the cell diagonally up-right from the current cell
    safeUpdate(row - 1, col + 1, num, update);

    // update the cell directly above the current cell
    safeUpdate(row - 1, col, num, update);

    // update the cell directly below the current cell
    safeUpdate(row + 1, col, num, update);

    
}

    
    static boolean isValid(int [][] grid ,int row, int col, int num) {
        //checks that there is  no reptition of values in same row
        for (int i = 0; i < numColumns; i++) {
            consistency++;
            if (grid[row][i] == num) {
                return false;
        } }
       //checks no adjacent num are the same
            try{
            if (grid[row - 1][col] == num||grid[row + 1][col] == num || grid[row][col - 1] == num||(grid[row][col + 1] == num||grid[row + 1][col + 1] == num||
                grid[row - 1][col - 1] == num||grid[row + 1][col - 1] == num ||grid[row - 1][col + 1] == num)) {
                    consistency++;
                    return false;
                } } catch(Exception e){consistency--;}
       // check sum of column
        int sum = 0;
        for (int i = 0; i < numRows -1; i++) {
            if(grid[i][col]!=-1)
                sum += grid[i][col];
        }
        consistency++;
        //check sum after adding value to column
        
        sum += num;
        if (sum > grid[numRows -1][col])
            return false;
        if (row == numRows -2 && sum != grid[numRows -1][col])
            return false;
        return true;}
        
        
        public static boolean solveTennerGridWithForwardCheckingMRV(int[][] grid, boolean[][][] domains) {
            int minDomainSize = Integer.MAX_VALUE;
            int row = -1, col = -1;
            boolean isEmpty = true;
        
            // Find the cell with the minimum remaining values
            for (int i = 0; i < numRows - 1; i++) {
                for (int j = 0; j < numColumns; j++) {
                    if (grid[i][j] == -1) {
                        int validCount = countValidNumbers(domains[i][j]);
                        if (validCount < minDomainSize) {
                            minDomainSize = validCount;
                            row = i;
                            col = j;
                            isEmpty = false;
                        }
                    }
                }
            }
        
            // If there is no empty cell, the grid is solved
            if (isEmpty) return true;
        
            // Try filling the empty cell with valid numbers from the domain
            for (int num = 0; num < 10; num++) {
                if (domains[row][col][num] && isValid(grid, row, col, num)) {
                    grid[row][col] = num;
                    assignments++;
                    forwardCheckdomain(grid, domains, row, col, num, false);
        
                    if (solveTennerGridWithForwardCheckingMRV(grid, domains)) {
                        return true;
                    }
        
                    grid[row][col] = -1; // Backtrack
                    assignments++;
                    forwardCheckdomain(grid, domains, row, col, num, true);
                }
            }
        
            return false;
        }
        
        private static int countValidNumbers(boolean[] domain) {
            int count = 0;
            for (boolean valid : domain) {
                if (valid) count++;
            }
            return count;
        }
        

        public static boolean solveTennerGridbacktrack(int[][] grid ) {
            int row = -1;
            int col = -1;
            boolean isEmpty = true;
           
            // Find an empty cell
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    if (grid[i][j] == -1) {
                        row = i;
                        col = j;
                        isEmpty = false;
                        break;
                    }
                }
                if (!isEmpty) {
                    break;
                }
            }
    
            // If there is no empty cell, the grid is solved
            if (isEmpty) {
                return true;
            }
          
            // Try filling the empty cell with numbers from 0 to 9
            for (int num = 0; num <= 9; num++) {
                if (isValid( grid, row, col, num)) {
                    grid[row][col] = num;
                    assignments++;
                    if ( solveTennerGridbacktrack(grid) ) {
                        return true;
                    }
                    grid[row][col] = -1; // Backtrack
                    assignments++;
                }
            }
    
            return false;}
        

     

    // Method to print the gril
    static void printGrid(int [][] grid) {
       
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        long startTime,endTime;
        int[][] solver=generateInitialState();
        /*while(solver==null){
            solver=generateInitialState();
        }*/

        int f[][] = {
            {-1, 6,2,0,-1,-1,-1, 8, 5, 7}, {-1, 0,1,7, 8,-1,-1,-1, 9,-1}, { -1, 4,-1,-1, 2, -1, 3, 7, -1, 8}, { 13, 10, 8, 7, 19, 16, 11, 19, 15, 17 }};
    
            System.out.println("Initial State:");
        printGrid(f);
        /*if (solver!=null){
            System.out.println("Random intial state:");
            printGrid(solver);
        }*/
        int type;
        
        System.out.println("choose an option\n 1=backtracking \n 2=forward checking \n 3=forward checking with MRV ");
        type= input.nextInt();
        
        
        switch (type) {
            case 1:
            startTime = System.currentTimeMillis();
            solveTennerGridbacktrack(f);
            endTime = System.currentTimeMillis();
            
      
       
           
        if(solveTennerGridbacktrack(f)){
            
            System.out.println("solution of example:");
            printGrid(f);
            System.out.println("\nNumber of Variable Assignments: " + assignments);
            System.out.println("Number of Consistency Checks: " + consistency);
            System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
    
    }
    else
          System.out.println("No solution found!");
            
          /*if(solveTennerGridbacktrack(solver)){
            
            System.out.println("solution of random grid:");
            printGrid(solver);
            System.out.println("\nNumber of Variable Assignments: " + assignments);
            System.out.println("Number of Consistency Checks: " + consistency);
            System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
    
    }
    else
          System.out.println("No solution found!");*/
                
          break;
                case 2:
                domains = new boolean[numRows][numColumns][10]; // 3D array for domains

                initializeDomains(domains);

                startTime = System.currentTimeMillis();
                solveTennerGridWithForwardChecking(f,domains);
                endTime = System.currentTimeMillis();
                
          
           
    
            if(solveTennerGridWithForwardChecking(f,domains)){
           
                System.out.println("solution:");
                printGrid(f);
                System.out.println("\nNumber of Variable Assignments: " + assignments);
                System.out.println("Number of Consistency Checks: " + consistency);
                System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
        
        }
        else
              System.out.println("No solution found!");
                
            
                    break;
                
                case 3:
                domains = new boolean[numRows][numColumns][10]; // 3D array for domains

                initializeDomains(domains);

                startTime = System.currentTimeMillis();
                solveTennerGridWithForwardCheckingMRV(f,domains);
                endTime = System.currentTimeMillis();
                
          
           
    
            if(solveTennerGridWithForwardCheckingMRV(f,domains)){
           
                System.out.println("solution:");
                printGrid(f);
                System.out.println("\nNumber of Variable Assignments: " + assignments);
                System.out.println("Number of Consistency Checks: " + consistency);
                System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
        

            }
            else
              System.out.println("No solution found!");
            break;
             
           /*  case 4:
              System.out.println("Exit program...");
              break;*/
          default:
              System.out.println("Invalid input! Please enter a number from 1 to 3.");
              break;
            
        }
                
                
                
                
                
    }}
          