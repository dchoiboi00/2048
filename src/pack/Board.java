package pack;
/* Name: Yoo (Daniel) Choi
 * Net ID: ychoi42
 * Assignment Name: CSC 172 Project 1 2048
 * Lab Section: MW 2:00 - 3:15
 * I did not collaborate with anyone on this assignment.
 */

/* This class extends JComponent, containing the code for graphics and key listening.
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Board extends JComponent implements KeyListener{
	private static final long serialVersionUID = 1L;
	
	private Array array;
	public enum Direction{
		LEFT, RIGHT, UP, DOWN;
	}
	Direction direction;
	
	public Board() {
		addKeyListener(this);
		array = new Array();   //creating array will automatically create an empty array with 2 values
	}
	
	@Override
	public void keyPressed(KeyEvent e) {	
		System.out.println();
		System.out.println("----------------------------");
		System.out.println("pressed: " + e.getKeyChar());
		
		if(e.getKeyCode()==KeyEvent.VK_A){ //if left pressed
			direction = Direction.LEFT;
			doTurn();
		}
		if(e.getKeyCode()==KeyEvent.VK_D){//if right pressed
			direction = Direction.RIGHT;
			doTurn();
		}
		if(e.getKeyCode()==KeyEvent.VK_W){//if up pressed
			direction = Direction.UP;
			doTurn();
		}
		if(e.getKeyCode()==KeyEvent.VK_S){//if down pressed
			direction = Direction.DOWN;
			doTurn();
		}
		if(e.getKeyCode()==KeyEvent.VK_Q){   //if Q pressed, ask user if they want to quit
			System.out.println("\nUser pressed q to quit\n");
			int reply = JOptionPane.showConfirmDialog(null, "Do you want to quit this game?", "Quit", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION){
				System.exit(0);
			}
		}
		if(e.getKeyCode()==KeyEvent.VK_R){//if R pressed, ask user if they want to restart the game
			System.out.println("\nUser pressed r to restart\n");
			int reply = JOptionPane.showConfirmDialog(null, "Do you want to restart this game?", "Restart", JOptionPane.YES_NO_OPTION);
			if (reply == JOptionPane.YES_OPTION){
				array.reset();
				repaint();
			}
		}
	}

	public void doTurn() {
		//after a key is pressed, call the array methods
		array.moveAndMerge(direction);
		
		//Print info
		System.out.println(direction);
		System.out.println("Moved: " + array.moved);
		System.out.println("Merged: " + array.merged);
		if (!array.moved && !array.merged)
			System.out.println("INVALID move");
		else
			System.out.println("Valid move");
		
		//Finishing off one turn
		if (array.moved) {
			array.move(direction);  //we need to make sure the tiles are where they need to be because there may be empty gaps after a merge.
			array.addRandomTile();  //add tile
			array.moves += 1;
			repaint();
		} else if (array.merged) {  //notice 'else-if', this is if the board didn't move, but only merged (can happen in some situations)
			array.move(direction);  //just to be safe
			array.addRandomTile();  //still perform same actions, except 
			array.moves += 1;
			repaint();
			
			System.out.println("Only merged");
		}
		
		if (!array.playable()) {    //test if the board is playable. Run if it returns false
			int reply = JOptionPane.showConfirmDialog(null, "Game Over! Your number of moves:" +array.moves+ ". Largest tile:"+array.max+". Do you want to play again?", "Game over", JOptionPane.YES_NO_OPTION);
			if(reply == JOptionPane.YES_OPTION){
				array.reset();
				repaint();
			}
			else{
				System.exit(0);
			}
		}
		
		System.out.println("Moves: " + array.moves);
		System.out.println("Max value: " + array.max);
	}
	
	public void paintComponent(Graphics g) {
		for(int j=0;j<4;j++){
			for(int i=0;i<4;i++){
				if(array.array[j][i]==0) //set different color backgrounds for different numbers
					g.setColor(new Color(205,205,205));
				else if(array.array[j][i]==2)
					g.setColor(new Color(245,245,245));
				else if(array.array[j][i]==4)
					g.setColor(new Color(234,222,196));
				else if(array.array[j][i]==8)
					g.setColor(new Color(239,178,122));
				else if(array.array[j][i]==16)
					g.setColor(new Color(253,147,93));
				else if(array.array[j][i]==32)
					g.setColor(new Color(252,121,96));
				else if(array.array[j][i]==64)
					g.setColor(new Color(247,97,58));
				else if(array.array[j][i]==128)
					g.setColor(new Color(236,207,113));
				else if(array.array[j][i]==256)
					g.setColor(new Color(236,200,100));
				else if(array.array[j][i]==512)
					g.setColor(new Color(230,220,70));
				else{  //1024, 2048, 4096 and beyond
					g.setColor(Color.MAGENTA);
				}
				
				g.fillRoundRect(135+i*180, 125+j*180, 165, 165, 20, 20); //paint the background for each number
				
				//Painting the number
				if(array.array[j][i]!=0){
					if (array.array[j][i] == 2 || array.array[j][i] == 4)
						g.setColor(new Color(90,90,90));    //dark number for 2 and 4 
					else
						g.setColor(new Color(240,240,240)); //light number for 8 and above
					
					if (array.array[j][i] < 9) {
						g.setFont(new Font("BOLD",1,100));
						g.drawString(Integer.toString(array.array[j][i]), 188+180*i, 242+180*j);
					}
					else if (array.array[j][i] > 10 && array.array[j][i] <= 99) {
						g.setFont(new Font("BOLD",1,100));
						g.drawString(Integer.toString(array.array[j][i]), 160+180*i, 242+180*j);
					}
					else if (array.array[j][i] > 99 && array.array[j][i] <= 999) {
						g.setFont(new Font("BOLD",1,80));
						g.drawString(Integer.toString(array.array[j][i]), 150+180*i, 235+180*j);
					}
					else if (array.array[j][i] > 999) {
						g.setFont(new Font("BOLD",1,60));
						g.drawString(Integer.toString(array.array[j][i]), 149+180*i, 230+180*j);
					}
				}
			}
		}
		
		//Paint Moves
		g.setFont(new Font("BOLD",1,40));
		g.setColor(Color.BLACK);
		g.drawString("Moves:"+Integer.toString(array.moves),680,95);
		
		//Decoration
		g.setFont(new Font("BOLD",3,100));
		g.setColor(new Color(70,50,99));
		g.drawString("2048",100,95);
	}
	
	public static void main(String[] args){
		JFrame frame = new JFrame("CSC172: 2048 Game");
		Board board = new Board(); //create a tile
		Container c = frame.getContentPane();
		c.setBackground(Color.LIGHT_GRAY);
		frame.setSize(1000, 900); //set the size for the game
		frame.add(board);
		frame.addKeyListener(board); //board is our key listener
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true); //display the game frame
		
	}
	
	
	//Not useful
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
