
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class tennerGrid {
    //private int[][] grid;
    static int numRows=4;
    static int numColumns=10;
    static int consistency;
    static int assignments;
static Scanner input=new Scanner(System.in);
  

    // Method to generate an initial state adhering to the rules
    static int[][] generateInitialState() {

        int[][] grid=new int[numRows][numColumns];

        Arrays.setAll(grid, i -> new int[numColumns]);
        Arrays.stream(grid).forEach(row -> Arrays.fill(row, -1));
        for (int col = 0; col < numColumns; col++) {
            grid[numRows - 1][col] +=1;}

        Random random = new Random();

        // Generate random numbers and calculate column sums
        for (int col = 0; col < numColumns; col++) {
            for (int row = 0; row < numRows - 1; row++) {
                if (random.nextBoolean()) { // Randomly decide whether to fill the cell
                    
                        int num = random.nextInt(10); // Generate random number (0-9)
                        if ( isValid(grid,row,col,num,0) ){
                        grid[row][col] = num;
                        grid[numRows - 1][col] += num;} 
                        
                    }
            }}
            
            // Randomly add a number to the column sum 
            for (int col = 0; col < numColumns; col++) {
                if( grid[0][col] == -1 || grid[1][col]== -1 || grid[2][col] == -1  )
            grid[numRows - 1][col] += random.nextInt(20 - grid[numRows - 1][col]);
        }
        return grid;
    }
    
    
    
    static boolean solveTennerGrid(int[][] grid , int type) {
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
            if (isValid( grid, row, col, num, type)) {
                grid[row][col] = num;
                assignments++;
                if ( solveTennerGrid(grid,type) ) {
                    return true;
                }
                grid[row][col] = -1; // Backtrack
                assignments++;
            }
        }

        return false;
    }

    static boolean isValid(int [][] grid ,int row, int col, int num,int type) {
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
        if (type==1){
        sum += num;
        if (sum > grid[numRows -1][col])
            return false;
        if (row == numRows -2 && sum != grid[numRows -1][col])
            return false;
        return true;}
        if (type==2){
            // Forward checking: reduce the domain of neighboring variables
    

        }
        if (type==3){

            //use froward checking + mrv huerstic
        }

        return true;
     }


 


    // Method to print the gril
    static void printGrid(int [][] grid) {
       
        for (int[] row : grid) {
            System.out.println(Arrays.toString(row));
        }
    }

    public static void main(String[] args) {
        int[][] solver=generateInitialState();
        int f[][] = {
            {-1, 6,2,0,-1,-1,-1, 8, 5, 7}, {-1, 0,1,7, 8,-1,-1,-1, 9,-1}, { -1, 4,-1,-1, 2, -1, 3, 7, -1, 8}, { 13, 10, 8, 7, 19,16, 11, 19, 15, 17 }};
           // int l[][] = {
               // {-1, -1,5,3,-1,-1,6, -1, -1, -1}, {0, 7,0,4,6,5,-1,-1, 1,3}, { -1, 2,3,7, -1, 4, -1, 6, 5, -1}, { 10, 13, 17, 14, 8,16, 14, 17, 14, 12 }};
            
            System.out.println("Initial State:");
        printGrid(f);
        System.out.println("choose an option 1=backtracking \n 2=forward checking \n 3=forward checking with MRV ");
        int type= input.nextInt();
        System.out.println("solution:");
        
        solveTennerGrid(f,type);
        printGrid(f);
        System.out.println("/////////////:");
        System.out.println("Initial State:");
        printGrid(solver);


        long startTime = System.currentTimeMillis();
       if(solveTennerGrid(solver,type)){
       
        long endTime = System.currentTimeMillis();
        System.out.println("\nSolution Found:");
        printGrid(solver);
        System.out.println("\nNumber of Variable Assignments: " + assignments);
        System.out.println("Number of Consistency Checks: " + consistency);
        System.out.println("Time Used to Solve the Problem: " + (endTime - startTime) + " milliseconds");

}
else
      System.out.println("No solution found!");
        
    }}