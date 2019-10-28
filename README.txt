/* Name: Yoo (Daniel) Choi
 * Net ID: ychoi42
 * Assignment Name: CSC 172 Project 1: 2048
 * Lab Section: MW 2:00 - 3:15
 * I did not collaborate with anyone on this assignment.
 */

2048

						User's Guide
     Run Board.java. Press a, s, d, w for moves left, down, right, and up. The board will keep count of the number of valid moves made. You may
press q to quit, or press r to restart. Try to get the 2048 tile!

						Description
     There are two classes: Array.java and Board java. The array class holds the numerical values of the classes and has the methods move and 
merge. The Board class extends JComponent with paintComponent and contains the main method. It also is the key listener for user interaction.
Direction is created as an enumerated type variable.