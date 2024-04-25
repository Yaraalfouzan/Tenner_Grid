import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class tennerGrid {
    static int numRows=4;
    static int numColumns=10;
    static int consistency;
    static int assignments;
    private static boolean found;
    private static boolean[][][] domains;
    static Scanner input=new Scanner(System.in);

  

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
                        while ( ! isValid(grid,row,col,num,0) ){
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
                    if (domains[row][col][num] && isValid(grid, row, col, num,1)) {
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
                if (domains[row][col][num] && isValid(grid, row, col, num,1)) {
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

        static void resetCounters(){
            consistency = 0;
            assignments = 0;
        }
        
    
    
    static boolean solveTennerGridbacktrack(int[][] grid ) {
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
            if (isValid( grid, row, col, num,1)) {
                grid[row][col] = num;
                assignments++;
                if ( solveTennerGridbacktrack(grid) ) {
                    return true;
                }
                grid[row][col] = -1; // Backtrack
                assignments++;
            }
        }

        return false;
    }

    static boolean isValid(int [][] grid ,int row, int col, int num,int type) {
        for (int i = 0; i < numColumns; i++) {
            if (type!=0)
            consistency++;
            if (grid[row][i] == num) {
                return false;
        } }
       
            try{
            if (grid[row - 1][col] == num||grid[row + 1][col] == num || grid[row][col - 1] == num||(grid[row][col + 1] == num||grid[row + 1][col + 1] == num||
                grid[row - 1][col - 1] == num||grid[row + 1][col - 1] == num ||grid[row - 1][col + 1] == num)) {
                    if (type!=0)
                    consistency++;
                    return false;
                } } catch(Exception e){ if (type!=0) consistency--;}
       
       if (type==1){
       int sum = 0;
        for (int i = 0; i < numRows -1; i++) {
            if(grid[i][col]!=-1)
                sum += grid[i][col];
        }
        sum += num;
        if (sum > grid[numRows -1][col])
            return false;
        if (row == numRows -2 && sum != grid[numRows -1][col])
            return false;
        return true;}

        return true;
     }


 


    // Method to print the gril
    static void printGrid(int [][] grid) {
       
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        int[][] f=generateInitialState();
        
        while(f==null){
            f=generateInitialState();
        }


        System.out.println("Initial State:");

        if (f!=null){
            printGrid(f); 
        }
        
        int[][] back = new int[f.length][];
        int[][] Fc = new int[f.length][];
        int[][] Mrv = new int[f.length][];
        
        // Copy contents of f to back, Fc, and Mrv
        for (int i = 0; i < f.length; i++) {
            back[i] = Arrays.copyOf(f[i], f[i].length);
            Fc[i] = Arrays.copyOf(f[i], f[i].length);
            Mrv[i] = Arrays.copyOf(f[i], f[i].length);
        }

    
      
            long startTime,endTime;
           

            startTime = System.currentTimeMillis();
            solveTennerGridbacktrack(back);
            endTime = System.currentTimeMillis();
            
      
       
           
        if(solveTennerGridbacktrack(back)){
            System.out.println("solution of example with backtrack:");
            printGrid(back);
            System.out.println("\nNumber of Variable Assignments: " + assignments);
            System.out.println("Number of Consistency Checks: " + consistency);
            System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");
    
    }
    else
          System.out.println("No solution found!");
            

          resetCounters();
        
          long FstartTime,FendTime;

          
          domains = new boolean[numRows][numColumns][10]; // 3D array for domains

          initializeDomains(domains);
          

          FstartTime = System.currentTimeMillis();
          solveTennerGridWithForwardChecking(Fc,domains);
          FendTime = System.currentTimeMillis();
          
    
          

      if(solveTennerGridWithForwardChecking(Fc,domains)){
     
          System.out.println("solution with FC:");
          printGrid(Fc);
          System.out.println("\nNumber of Variable Assignments: " + assignments);
          System.out.println("Number of Consistency Checks: " + consistency);
          System.out.println("Time Used to Solve the Problem: " + (FendTime - FstartTime) + " milliseconds");
  
  }
  else
        System.out.println("No solution found!");
          
      
        resetCounters();
              
    
              long MstartTime,MendTime;
            

              domains = new boolean[numRows][numColumns][10]; // 3D array for domains

              initializeDomains(domains);

              MstartTime = System.currentTimeMillis();
              solveTennerGridWithForwardCheckingMRV(Mrv,domains);
              MendTime = System.currentTimeMillis();
              
        
         
  
          if(solveTennerGridWithForwardCheckingMRV(Mrv,domains)){
         
              System.out.println("solution with FC+MRV:");
              printGrid(Mrv);
              System.out.println("\nNumber of Variable Assignments: " + assignments);
              System.out.println("Number of Consistency Checks: " + consistency);
              System.out.println("Time Used to Solve the Problem: " + (MendTime - MstartTime) + " milliseconds");
      

          }
          else
            System.out.println("No solution found!");
        
    }}
