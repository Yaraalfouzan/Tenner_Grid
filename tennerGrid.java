
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class tennerGrid {
    //private int[][] grid;
    static int numRows=4;
    static int numColumns=10;
    static int consistency;
    private static boolean found;

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
            
          

            static boolean simpleBacktrack(int grid[][], int row, int col)
            {
                   /*if we have reached the end of gird
                   we are returning true to avoid further
                   backtracking         */
                if (row == numRows - 1 && col == numColumns)
                    return true;
                // Check if columns value becomes 10 ,
                // we move to next row
                // and column start from 0
                if (col == numColumns) {
        row++;
        col = 0; }
                /* Check if the current position
                   of the grid already
                   contains value >0, we iterate
                   for next column*/
                if (grid[row][col] != -1)
                    return simpleBacktrack(grid, row, col + 1);
                for (int num = 0; num < 10; num++) {
                    /* Check if it is safe to place
                       the num (0-9) in the
                       given row ,col ->we move to next column*/
                    if (isValid(grid, row, col, num)) {
                         /* assigning the num in the current
                         (row,col) position of the grid and
                         assuming our assigned num in the position
                         is correct */
                        grid[row][col] = num;
                        assignments++;
                        // Checking for next
                        // possibility with next column
                        if (simpleBacktrack(grid, row, col + 1))
        
        return true;
        }
                grid[row][col] = -1;
                assignments++;
            }
            return false;
        }
        static boolean solveTennerGrid(int[][] grid ) {
            int row = -1;
            int col = -1;
            boolean isEmpty = true;
    
            // Find an empty cell
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j <numColumns; j++) {
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
                    if ( solveTennerGrid(grid) ) {
                        return true;
                    }
                    grid[row][col] = -1; // Backtrack
                    assignments++;
                }
            }
    
            return false;
        }
    

    static boolean solveForwardChecking(int[][] grid, int row, int col, boolean[][][] possibilities) {
        if (row == numRows - 1 && col == numColumns)
            return true;
        if (col == numColumns) {
            row++;
            col = 0;
        }
        if (grid[row][col] != -1)
            return solveForwardChecking(grid, row, col + 1, possibilities);
        for (int num = 0; num < 10; num++) {
            if (possibilities[row][col][num] && isValid(grid, row, col, num)) {
                grid[row][col] = num;
                assignments++;
                boolean[][][] updatedPossibilities = updatePossibilities(grid, row, col, possibilities, num);
                if (solveForwardChecking(grid, row, col + 1, updatedPossibilities))
                    return true;
                grid[row][col] = -1;
                assignments++;
            }
        }
        return false;
    }

    static boolean[][][] updatePossibilities(int[][] grid, int row, int col, boolean[][][] possibilities, int num) {
        boolean[][][] updatedPossibilities = new boolean[numRows][numColumns][10];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.arraycopy(possibilities[i][j], 0, updatedPossibilities[i][j], 0, 10);
            }
        }
        for (int j = 0; j < numColumns; j++) {
            updatedPossibilities[row][j][num] = false;
        }
        try {
            if (grid[row - 1][col] == num)
                updatedPossibilities[row - 1][col][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        try {
            if (grid[row + 1][col] == num)
                updatedPossibilities[row + 1][col][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        try {
            if (grid[row][col - 1] == num)
                updatedPossibilities[row][col - 1][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        try {
            if (grid[row][col + 1] == num)
                updatedPossibilities[row][col + 1][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        try {
            if (grid[row + 1][col + 1] == num)
                updatedPossibilities[row + 1][col + 1][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        try {
            if (grid[row - 1][col - 1] == num)
                updatedPossibilities[row - 1][col - 1][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        try {
            if (grid[row + 1][col - 1] == num)
                updatedPossibilities[row + 1][col - 1][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        try {
            if (grid[row - 1][col + 1] == num)
                updatedPossibilities[row - 1][col + 1][num] = false;
        } catch (Exception e) {
            consistency--;
        }
        return updatedPossibilities;
    }

    static boolean solveForwardCheckingMRV(int[][] grid, int row, int col, boolean[][][] possibilities) {
        if (row == numRows) {
            row = 0;
            col++;
            if (col == numColumns)
                return true; // We have filled the entire grid
        }
    
        // Find the cell with minimum remaining values (MRV)
        int[] nextCell = findNextCell(grid, possibilities);
        if (nextCell == null)
            return false;
    
        row = nextCell[0];
        col = nextCell[1];
    
        for (int num = 0; num < 10; num++) {
            if (possibilities[row][col][num] && isValid(grid, row, col, num)) {
                grid[row][col] = num;
                assignments++;
                boolean[][][] updatedPossibilities = updatePossibilities(grid, row, col, possibilities, num);
                if (solveForwardCheckingMRV(grid, row + 1, col, updatedPossibilities))
                    return true;
                grid[row][col] = -1; // Backtrack
                assignments++;
            }
        }
        return false;
    }
    

    static int[] findNextCell(int[][] grid, boolean[][][] possibilities) {
        int[] nextCell = null;
        int minPossibilities = Integer.MAX_VALUE;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (grid[i][j] == -1) {
                    int countPossibilities = countTrueValues(possibilities[i][j]);
                    if (countPossibilities < minPossibilities) {
                        minPossibilities = countPossibilities;
                        nextCell = new int[]{i, j};
                    }
                }
            }
        }
        return nextCell;
    }

    static int countTrueValues(boolean[] arr) {
        int count = 0;
        for (boolean value : arr) {
            if (value) {
                count++;
            }
        }
        return count;
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
       

      
        

     

    // Method to print the gril
    static void printGrid(int [][] grid) {
       
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        long startTime,endTime;
        int[][] solver=generateInitialState();
        int f[][] = {
            {-1, 6,2,0,-1,-1,-1, 8, 5, 7}, {-1, 0,1,7, 8,-1,-1,-1, 9,-1}, { -1, 4,-1,-1, 2, -1, 3, 7, -1, 8}, { 13, 10, 8, 7, 19,16, 11, 19, 15, 17 }};
           // int l[][] = {
               // {-1, -1,5,3,-1,-1,6, -1, -1, -1}, {0, 7,0,4,6,5,-1,-1, 1,3}, { -1, 2,3,7, -1, 4, -1, 6, 5, -1}, { 10, 13, 17, 14, 8,16, 14, 17, 14, 12 }};
            
            System.out.println("Initial State:");
        printGrid(f);
        System.out.println("choose an option\n 1=backtracking \n 2=forward checking \n 3=forward checking with MRV ");
        int type= input.nextInt();
        
        
        switch (type) {
            case 1:
            startTime = System.currentTimeMillis();
            solveTennerGrid(f);
            endTime = System.currentTimeMillis();
            
      
       

        if(solveTennerGrid(f)){
       
            System.out.println("solution:");
            printGrid(f);
            System.out.println("\nNumber of Variable Assignments: " + assignments);
            System.out.println("N1umber of Consistency Checks: " + consistency);
            System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
    
    }
    else
          System.out.println("No solution found!");
            
        
                break;
            case 2:
            boolean [][][] pos1 = new boolean[numRows][numColumns][10];
        for(int i=0; i<numRows-1; i++)
            for(int j=0; j<numColumns; j++)
                for(int num=0; num<10; num++)
                    pos1[i][j][num]=true;
            startTime = System.currentTimeMillis();
            solveForwardChecking(f,0,0,pos1);
            endTime = System.currentTimeMillis();
     
       

        if(solveForwardChecking(f,0,0,pos1)){
           
            System.out.println("solution:");
            printGrid(f);
            System.out.println("\nNumber of Variable Assignments: " + assignments);
            System.out.println("N1umber of Consistency Checks: " + consistency);
            System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
    
    }
    else
          System.out.println("No solution found!");
            
                
                break;
            
                case 3: 
                boolean[][][] pos2 = new boolean[numRows][numColumns][10];
                for (int i = 0; i < numRows; i++) {
                    for (int j = 0; j < numColumns; j++) {
                        for (int num = 0; num < 10; num++) {
                            pos2[i][j][num] = true;
                        }
                    }
                }
                startTime = System.currentTimeMillis();
                if (solveForwardCheckingMRV(f, 0, 0, pos2)) {
                    endTime = System.currentTimeMillis();
                    System.out.println("Solution:");
                    printGrid(f);
                    System.out.println("\nNumber of Variable Assignments: " + assignments);
                    System.out.println("Number of Consistency Checks: " + consistency);
                    System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
                } else {
                    System.out.println("No solution found!");
                }
                break;
                case 4:
            startTime = System.currentTimeMillis();
            simpleBacktrack(f, 0, 0);
            endTime = System.currentTimeMillis();
            
      
       

        if(            simpleBacktrack(f, 0, 0)        ){
       
            System.out.println("solution:");
            printGrid(f);
            System.out.println("\nNumber of Variable Assignments: " + assignments);
            System.out.println("N1umber of Consistency Checks: " + consistency);
            System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
    
    }
    else
          System.out.println("No solution found!");
            
        
                break;
            
    }
            
        }
    }
       // solveTennerGrid(f);
        /*printGrid(f);
        System.out.println("/////////////:");
        System.out.println("Initial State:");
        printGrid(solver);


        long startTime = System.currentTimeMillis();
       if(solveTennerGrid(f)){
       
        long endTime = System.currentTimeMillis();
        System.out.println("\nSolution Found:");
        printGrid(solver);
        System.out.println("\nNumber of Variable Assignments: " + assignments);
        System.out.println("N1umber of Consistency Checks: " + consistency);
        System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");

}
else
      System.out.println("No solution found!");
        
    }}*/