package pack;
/* Name: Yoo (Daniel) Choi
 * Net ID: ychoi42
 * Assignment Name: CSC 172 Project 1 2048
 * Lab Section: MW 2:00 - 3:15
 * I did not collaborate with anyone on this assignment.
 */

/* This class creates the 2-D array. This class stores the values of tiles and holds the move/merge methods. 
 */

import java.util.Random;

import pack.Board.Direction;

public class Array {
	//instance variables
	public int[][] array;
	private Random rand = new Random();
	public boolean merged = false;
	public boolean moved = false;
	int max = 4;         //because the game will not end with just 2's on the board
	private int count;   //number of tiles on the board
	int moves;           //moves in the round
	
	//Creates or resets the array
	public void createArray() {
		array = new int[4][4]; //create our 4x4 array
		for (int i = 0; i < 4; i++) {  //filling array with 0's
			for (int j = 0; j < 4; j++) {
				array[i][j] = 0;
			}
		}
		addRandomTile();
		addRandomTile();
		count = 2;  //start with two tiles
		moves = 0;  //set moves as 0
		max = 4;
	}
	
	//Spawn a new tile. 80% for 2, 20% for 4
	public void addRandomTile() {
		boolean keepSearching = true;
		int i, j;
		double val;
		
		while(keepSearching) {
			i = rand.nextInt(4);  //i and j are set to random values 0-3
			j = rand.nextInt(4);
			if (array[i][j] == 0) { //if the random position is empty, then run. If not, keepSearching remains true to look for an empty space
				val = rand.nextDouble();
				if (val < 0.8)  //80% of putting in 2
					array[i][j] = 2;
				else
					array[i][j] = 4;
				keepSearching = false;  //stops the while loop
				count++;                //increment count, number of tiles
			}
		}
		
		keepSearching = true;  //set it back to true for the next time this method is called
	}
	
	public Array() {        //constructor is called in Tile constructor
		this.createArray();
	}
	
	public void reset() {   //this can be called when user presses 'r'
		this.createArray();
	}
	
	//this is used in method playable() to create a testArray
	public void setArray(int[][] arr) {
		for(int i=0;i<4;i++){
			for(int j=0;j<4;j++){
				this.array[i][j] = arr[i][j];
				//arr[i][j] = this.array[i][j];
			}
		}
	}
	
	public boolean playable() { //call this method to check whether game is over
		if (count == 16) {
			Array testArray = new Array();  //create a testarray to copy the current array values over
			testArray.setArray(this.array);
			
			System.out.println("\nTesting Array Values:");
			for (int i = 0; i < 4; i++) {
				System.out.println();
				for (int j = 0; j < 4; j++)
					System.out.printf("%-5d",testArray.array[i][j]);
			}
			System.out.println("\n");
			
			Direction[] directions = Direction.values();
			for (Direction d : directions) {  //try moving and merging the testArray
				testArray.moveAndMerge(d);
			}
			
			//if after trying all directions, it didn't move or merge, it is not playable
			if (testArray.moved == false && testArray.merged == false)
				return false;
			else {  //if it can be moved or merged, it is playable
				System.out.println("Still playable");
				return true;
			}
			
		} else { //if there aren't 16 tiles, the game is still playable
			return true;
		}
	}
	
	//Step 1 of 2, move tiles d
	public void move(Direction d) {   
		moved = false;  //set moved back to false initially, since we don't know if it can move
		switch (d) {  //we have 4 directions
		case LEFT:
			for (int i = 0; i < 4; i++) {
				int emptySpaces = 0;   //internal counter of number of empty spaces; notice that it resets to 0 for each row
				for (int j = 0; j < 4; j++) {
					if (array[i][j] == 0) {   //if the space holds 0, we increment emptySpaces
						emptySpaces++;
					}
					else if (emptySpaces > 0) { //notice that it's an else-if, so it only runs when it's not 0
						//even if the first item is not 0, it will not run since emptySpaces has not been incremented
						array[i][j - emptySpaces] = array[i][j];  //shift item 'emptySpaces' number of places to the left
						array[i][j] = 0;
						moved = true; //indicate that something has been moved
					}
				}
			}
			break;
		case RIGHT:
			for (int i = 0; i < 4; i++) {
				int emptySpaces = 0;
				for (int j = 3; j >= 0; j--) {  //go from 3, 2, 1, 0
					if (array[i][j] == 0) { //start from the right side
						emptySpaces++;
					}
					else if (emptySpaces > 0) { 
						array[i][j + emptySpaces] = array[i][j];  //shift item 'emptySpaces' number of places to the right
						array[i][j] = 0;
						moved = true;
					}
				}
			}
			break;
		case UP: 
			for (int i = 0; i < 4; i++) {
				int emptySpaces = 0;
				for (int j = 0; j < 4; j++) {
					if (array[j][i] == 0) { //flip i and j to work downwards with one column 
						emptySpaces++;
					}
					else if (emptySpaces > 0) { 
						array[j - emptySpaces][i] = array[j][i];  //shift item 'emptySpaces' number of places up
						array[j][i] = 0;
						moved = true;
					}
				}
			}
			break;
		case DOWN:
			for (int i = 0; i < 4; i++) {
				int emptySpaces = 0;
				for (int j = 3; j >= 0; j--) { //go from 3, 2, 1, 0
					if (array[j][i] == 0) { //flip the RIGHT method, working upwards vertically
						emptySpaces++;
					}
					else if (emptySpaces > 0) { 
						array[j + emptySpaces][i] = array[j][i];  //shift item 'emptySpaces' number of places down
						array[j][i] = 0;
						moved = true;
					}
				}
			}
			break;
		}
	}
	
	public void merge(Direction d) {
		merged = false;   //set merged back to false initially, since we don't know if it merged
		switch (d) {  //again, 4 directions
		case LEFT:
			for (int i = 0; i < 4; i++) {
				for (int j = 1; j < 4; j++) {  //we start from 1, the second item from the left
					if (array[i][j] != 0 && array[i][j] == array[i][j-1]) {   //if it's not 0, and is equal to the tile to its left
						array[i][j-1] += array[i][j]; //put the sum in the left tile
						array[i][j] = 0;             //set the right tile to 0
						if(array[i][j-1] > max)  //if the new sum is greater than max tile, redefine max
							max = array[i][j-1];
						merged = true;  //indicate a merge happened
						count--;        //by merging, one tile became 0
					}
				}
			}
			break;
		case RIGHT:
			for (int i = 0; i < 4; i++) {
				for (int j = 2; j >= 0; j--) {  //we go from 2, 1, 0
					if (array[i][j] != 0 && array[i][j] == array[i][j+1]) {   //if it's not 0, and is equal to the tile to its right
						array[i][j+1] += array[i][j]; //put the sum in the right tile
						array[i][j] = 0;             //set the left tile to 0
						if(array[i][j+1] > max)  //if the new sum is greater than max tile, redefine max
							max = array[i][j+1];
						merged = true;  //indicate a merge happened
						count--;        //by merging, one tile became 0
					}
				}
			}
			break;
		case UP: 
			for (int i = 0; i < 4; i++) {
				for (int j = 1; j < 4; j++) {  //we go from 1, 2, 3 
					if (array[j][i] != 0 && array[j][i] == array[j-1][i]) {   //if it's not 0, and is equal to the tile above it
						array[j-1][i] += array[j][i]; //put the sum in the upper tile
						array[j][i] = 0;             //set the lower tile to 0
						if(array[j-1][i] > max)  //if the new sum is greater than max tile, redefine max
							max = array[j-1][i];
						merged = true;  //indicate a merge happened
						count--;        //by merging, one tile became 0
					}
				}
			}
			break;
		case DOWN:
			for (int i = 0; i < 4; i++) {
				for (int j = 2; j >= 0; j--) {  //we go from 2, 1, 0
					if (array[j][i] != 0 && array[j][i] == array[j+1][i]) {   //if it's not 0, and is equal to the tile below it
						array[j+1][i] += array[j][i]; //put the sum in the lower tile
						array[j][i] = 0;             //set the upper tile to 0
						if(array[j+1][i] > max)  //if the new sum is greater than max tile, redefine max
							max = array[j+1][i];
						merged = true;  //indicate a merge happened
						count--;        //by merging, one tile became 0
					}
				}
			}
			break;
		}
		
	}
	
	public void moveAndMerge(Direction d) {
		this.move(d);
		this.merge(d);
	}
}
