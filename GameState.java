/**
* Name: Divneet Kaur
* CSE8B login : cs8bwi20im
* Date: 2020 February 6th
*Sources: Lecture notes, Introduction to Java Programming book, Lecture podcasts, piazza, tutor/TA hours
*/
/**
 * This file is designed to create a Matrix class that contains methods
 * to manipulate the matrix and ability to add, multiply and 
 * transpose matrices.
 * @author Divneet Kaur
 */

import java.util.Random;

/**
 * This class is used to create a 2048 board and implement various methods to make a 2048 game. 
 * It has methods for getting and setting a board and the score, generating random tiles and adding them at empty places in the board.
 *  It also has a slide down method which can make the tiles slide down if it is possible. 
 * At the end it tells if the game is over.
 */

public class GameState{
    private Random rng; //used this everytime I wanted to generate a number so that I didn't need to create a new Random object every time.
    private int[][] board; //to keep track of the board. For tiles that had no value (empty), there is a 0 in board.
    private int score; //to keep track of the score

    /** 
	 * Default constructor that will initialize the instance 
	 * variables row, column to 0 and the 2D integer array to null.
	 * @param numRows  The number of Rows in the board.
     * @param numCols The number of Cols in the board.
	 */
    public GameState (int numRows, int numCols){
        this.board = new int[numRows][numCols]; //creates a new board
        score = 0; //sets game score to 0
        rng = new Random(Config.RANDOM_SEED); //RandomSeed leads to same sequence of numbers when random object is made
    } 
    
    /** 
	 * Returns a deep copy of the the board of the 2048 game
     * @return The current state of 2048 game board as a 2D int array
	 */
    public int[][] getBoard(){
        int row = this.board.length;
        int col = this.board[0].length;
        int[][] latestBoard = new int[row][col]; //new board is created

		// loop over the entire matrix and copy elements one by one
		for(int i = 0; i < row ; i++){
		    for(int j = 0; j < col; j++){
                if(this.board[i][j]!= 0){
                    latestBoard[i][j] = this.board[i][j]; //sets latestboard to this.board if there are no empty tiles in this.board
                }else{
                    latestBoard[i][j] = 0; //sets latestboard to 0
                }
				
			}
        } 
        return latestBoard;
    }

    /** 
	 * Sets the value of the board by taking in a 2D array as a parameter
	 * @param  newBoard a 2D int array to copy the values from into the this.board
	 */

    public void setBoard (int[][] newBoard){
        // exits out if the argument passed in is null
        if(newBoard == null){
            return;
        }
        int row = newBoard.length;
        int col = newBoard[0].length;
        this.board = new int[row][col];
        //loops over the game board setting values of every element of this.board
        for(int i = 0; i< row; i++){
            for(int j = 0; j< col; j++){
                this.board[i][j] = newBoard[i][j];
            }
        }
    }

    /** 
	 * Returns the current score of the 2048 game as an integer
     * @return The current score of 2048 game 
	 */
    public int getScore(){
        return this.score;
    }

    /** 
	 * Sets the value of the score of the 2048 game
	 * @param newScore an integer for the current score to be set to  
	 */
    public void setScore(int newScore){
        this.score = newScore;
    }

     /** 
	 * Returns any random integer number between 0 and bound-1 both inclusive
	 * @param bound an integer which is the bound from which the rollRNG returns a random int
     * @return returns an random integer between 0(inclusive) and bound(exclusive)
	 */
    protected int rollRNG (int bound){
        return rng.nextInt(bound); 
    }

    /** 
	 * Adds and returns either tile 2 or 4 depending on the 70 and 30 % probability respectively 
     * @return returns a 2 with probability TWO_PROB or 4 with remaining probability
	 */

    protected int randomTile (){
        int randomTiles = rollRNG(100); //returns random int between 0 and 100
        //if random number is between (0,70), 2(Config.TWO_PROB) is returned 
        //else 4 (Config.FOUR_TILE) is returned

        if (randomTiles >= 0 && randomTiles <= Config.TWO_PROB){ 
            return Config.TWO_TILE;
        } else {
            return Config.FOUR_TILE;
        }
    }

    /** 
	 * Return the number of empty tiles on the board
     * @return return the number of empty tiles on the board
	 */

    protected int countEmptyTiles(){
        int countEmpty = 0;
        //loops over gameBoard to see if there are empty tiles and 
        //increases countEmpty's value everytime
        for ( int i = 0 ; i < this.board.length; i++){
            for(int j = 0; j < this.board[0].length; j++){
                if(this.board[i][j] == 0){
                    countEmpty += 1;
                }
            } 

        } 
        return countEmpty;
    }

     /** 
	 * Adds a new tile to the board and returns the type of the tile added, either 2 or 4 .Return 0 if you are unable to add any tile.
     * depending on what randomTile() returns
     * Return 0 if you are unable to add any tile.
     * @return returns the type of the tile added, either 2 or 4 depending on what randomTile() returns 
     * and 0 if no tile is added
	 */

    protected int addTile() {
    int checkingEmpty = countEmptyTiles(); //stores number of empty tiles in variable checkingEmpty
    //if no empty tiles then returns 0
    if (checkingEmpty == 0) { 
        return 0;
    }else {
        //creates a random number within (0,checkingEmpty)
        int ranNumTile = rollRNG((checkingEmpty)); 
        //loops over game board and increases variable count if a 0 tile is found in the board
        int count = 0;
        for (int i = 0; i < this.board.length; i++) {
        for (int j = 0; j < this.board[0].length; j++){
            if (this.board[i][j] == 0) {
                count = count + 1;
                //if count is same as the random number generated earlier then tile is added
                if (count == (ranNumTile+1)) {
                // Add tile 
                // +1 because ranNumTile will return values between 0 and bound (exclusive)
                    int addingTile = randomTile();
                    this.board[i][j] = addingTile;
                    return addingTile; 
                } 
            }
        }
        }
    } 
    return 0;
    }

    /** 
	 * Rotate the board counter-clockwise once
	 */
    protected void rotateCounterClockwise (){
         //creates newBoard with switched rows and cols than the original board
        int[][] newBoard = new int[this.board[0].length][this.board.length]; 
        
        //loops over the board and fills in values to create a board which has been rotated counterclockwise (transpore)
        for (int i = 0; i < this.board[0].length; i ++){
            for( int j = 0 ; j < this.board.length; j++){
                newBoard[i][j] = this.board[j][this.board[0].length-i-1];      
            }
        } 
        this.board = newBoard;
    }

    /** 
	 * Returns true if sliding down of a tile in the 2048 game board is possible and false otherwise.
     * @return return true if sliding down is possible and false otherwise.
	 */
    protected boolean canSlideDown (){ 
        for(int i = 0; i < (this.board.length-1); i ++){
            for(int j = 0; j < (this.board[i].length); j++){ 
                //loops over board to see if two adjacent tiles are in same column are same
                if (this.board[i][j] == this.board[i+1][j]){ 
                    //if both tiles are 0 then the code skips over them
                    if(this.board[i][j] == 0 && this.board[i+1][j] == 0){ 
                        continue;
                    }
                    return true;
                } 
                //if there is one 0 and one number tile then canSlideDown is true
                else if(this.board[i][j] != 0 && this.board[i+1][j] == 0 ) { 
                    return true;
                }              
            }
        } 
    return false;

    }
    /** 
	 * Returns true if there is no movement possible in any direction in the 2048 game board else it returns false
     * @return returns true if there is no movement possible in any direction else it returns false
	 */
    public boolean isGameOver(){ 
        //if tiles can slide down then game is not over
        if(canSlideDown() == true){
            return false;
        } 
        // rotates the board by starting at 0 for 4 times and checks if the tiles can slide down
        int timesRotated = 0;
        for(int i = 0; i < 4; i++){
            this.rotateCounterClockwise();
            timesRotated += 1;
            if(this.canSlideDown()){ //if tiles can slide down the rotates board back to original board and returns game isnt over
                for( int j = 0; j<(4 -timesRotated); j++){
                    this.rotateCounterClockwise();
                    return false;
                }
            }
        }
        return true;  
    }
    
    /** 
	 * Returns true if sliding down is successful which changed the board by moving a tile and return false otherwise (the board didn't change).
     * @return returns true if sliding down is successful false otherwise
	 */
    protected boolean slideDown() {
       
        boolean hasChanged = false;
        // 1. Loop over each row from bottom to top and find non-zero numbers
        // 2. Move all non-zero numbers to the bottom most position or edge of the board

        //loop over columns from left to right
        for(int i = 0; i < this.board[0].length; i++){
            // loop over rows from bottom to top
            for(int j = this.board.length - 1; j > 0; j--){
                if(this.board[j][i] == 0 && this.board[j-1][i] > 0){
                    hasChanged = true;
                    this.board[j][i] = this.board[j-1][i];
                    this.board[j-1][i] = 0;
                    j = this.board.length;
                }
            }
        }

        // 2. (Non Zero Numbers)
        for (int i = 0; i < this.board[0].length; i++){
            for(int j = this.board.length - 1; j > 0; j--){
                if(this.board[j][i] == this.board[j-1][i] && this.board[j][i] != 0){
                    hasChanged = true;
                    //Creates a new tile with the value
                    //of the merged tiles
                    this.board[j][i] += this.board[j-1][i];
                    this.score += this.board[j][i];
                    //Moves all tiles to the left of the
                    //merged tile over by one
                    for(int k = j - 1; k > 0; k--){
                        this.board[k][i] = this.board[k-1][i];
                    }
                    //Sets leftmost tile to 0
                    this.board[0][i] = 0;
                }
            }
        }

        return hasChanged;
    }


    /** 
	 * Slides the board and returns true and adds a random tile if sliding down is successful and return false otherwise 
     * @return returns true if move is successful and false otherwise
	 */
    public boolean move (Direction dir){
        //dir parameter is null returns false
        if (dir == null){
            return false;
        } else{

        //stores number of times to rotate in variable and rotates the board that many times
        int timesToRotate = dir.getRotationCount();
        for(int i = 0;i < (timesToRotate); i++ ){
            this.rotateCounterClockwise(); }
        //checks if tile can slide down
        boolean b = this.canSlideDown();
        if(b){
            this.slideDown();
        }
        //after tiles slide down rotates board back to orignal position
        for(int j = 0; j < 4-timesToRotate; j++){
            this.rotateCounterClockwise();
        }
        // add tiles if tiles can slide down
        if(b){
            this.addTile(); 
        }
        return b;
    }
} 

    /** 
	 * Returns the board as string and not a reference
     * @return returns the board as string and not a reference
	 */
    public String toString () {
        StringBuilder outputString = new StringBuilder();
        outputString.append(String.format("Score: %d\n", getScore()));
        for (int row = 0; row < getBoard().length; row++) {
            for (int column = 0; column < getBoard()[0].length; column++) {
                outputString.append(getBoard()[row][column] == 0 ? "    -" :
                String.format("%5d", getBoard()[row][column]));
            }
            outputString.append("\n");
        }  
        return outputString.toString();
    }

    public static void main(String[] args) {
        int[][] board = { { 2, 0, 2 }, { 2, 0, 2 }, { 0, 0, 2 } }; 
        GameState game1 = new GameState(3, 3);
        System.out.print(game1);
        game1.setBoard(board);
        int a = game1.randomTile();
        System.out.print(a);
        int b = game1.addTile();
        System.out.println(b);
        boolean c = game1.canSlideDown();
        System.out.print(c);
        game1.rotateCounterClockwise();
        System.out.print(game1);
        boolean d = game1.isGameOver();
        System.out.print(d);
        boolean f = game1.move(Direction.RIGHT);
        System.out.print(f);
        boolean e = game1.slideDown();
        System.out.print("Return Slidedown " + e);

    }
}