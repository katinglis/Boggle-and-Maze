//========================================================================================================================
// "Games with Recursion" Assignment
// Katherine Inglis
// September 2017
// Java 8
// class Boggle
//========================================================================================================================
// Problem Definition - To create two games, Boggle and Maze, both of which exercise the use of recursive methods.
//                     The project is broken into 3 classes: GamesWithRecution, Maze and Boggle
// Input - 
// Output - 
// Process - 
// 
//         class Boggle:	Uses recursion to implement a word search game (actually a search for contiguous letters) 
// 							on a 5x5 grid.
//

// Global identifiers for class GamesWithRecursion
//
//  - let _gridValues represent the integer representation of each character on the board (type int[][])
//  - let _gridLabels represent the text representation of each character on the board (type JLabel[][])
//  - let _rand represent the object reference to generate random characters for the board (type Random)
//  - let_word represent the input word text (type JTextField)
//  - let _applet represent the applet object reference (type GamesWithRecursion)

// Class Import List
//
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Boggle {
	
	// -----------------------------
	// 'global' variables
	//
	private int _dim;											// Boggle board game dimension
	private int _gridValues[][];								// Integer representation of each character on the board
	private JLabel _gridLabels[][];								// Text representation of each character on the board
	private Random _rand = new Random();						// Use to generate random characters for the board
	private JTextField _word = new JTextField("", 10);			// Input word text
	private GamesWithRecursion _applet;							// Game applet object reference
	
	// -------------------------------------------------------
	// public CONSTRUCTOR
	// ------------------------------------------------------
	
	/** Boggle Constructor:
	  * 	 This public constructor stores the applet object reference so the Boggle class can access 
	  *      the applet to update the gui and sets up the applet to be the Boggle game
	  *       @param  GamesWithRecurions - the applet class
	  *       @return void
	  */
	public Boggle(GamesWithRecursion applet, int dim) {
		_applet = applet;																	// Save the applet object reference for future use
		_dim = dim;																			// Save the board dimension
				
		_gridValues = new int [_dim][_dim];													// initialize the array of letters (as integers)
		_gridLabels = new JLabel[_dim][_dim];												// initialize the array of letters (as JLabels) 
	}
	
  	// -------------------------------------------------------
  	// Regular Methods
  	// -------------------------------------------------------

	/** wordCheck Method:
	  *      This procedural method finds the first letter from the input word and then calls the
	  *      recursive routine checkSurroundingCells to find the rest of the word.
	  *       @param  String word - sequence of letters to find
	  *       @return void
	  */
	private void wordCheck(String word) {
		
		// Reset each cell background to white to erase any previously found words
		//
		for (int r = 0; r < _dim; r++) {													// Loop through each row of the board
			for (int c = 0; c < _dim; c++) {												// Loop through each column of the board
				_gridLabels[r][c].setBackground(Color.WHITE);								// Reset background to white
				_gridLabels[r][c].setOpaque(true);											// Rest label to be opaque
			}																				// End of 'for c' loop
		}																					// End of 'for r' loop
			
		// Find the first letter of the word on the board
		//
		if (word.length() > 0) {
			char firstChar = word.charAt(0);												// First Letter of the word
			boolean found = false;															// Assume not found
			for (int r = 0; r < _dim && !found; r++) {										// Loop through each row of the board
				for (int c = 0; c < _dim && !found; c++) {									// Loop through each column of the board
					if (firstChar == _gridValues[r][c]) {									// Check if first letter is found
						boolean usedCells[][] = new boolean[_dim][_dim];					// Keep track of cells used
						if (checkSurroundingCells(1, r , c, word, usedCells)) {				// Call recursive routine
							_gridLabels[r][c].setBackground(Color.GREEN);					// Mark first letter cell as green
							found = true;													// Set found to true
							_applet.setStatusMessage("Found " + word + "!", Color.GREEN);	// Set Status Message to found!
						}																	// End of 'if' first letter
					}																		// End of 'if' found word
				}																			// End of 'for c'
			}																				// End of 'for r'
			
			if (!found) _applet.setStatusMessage("Did not find " + word + "!", Color.RED);	// Emit word not found status
		}																					// End if 'if'
	}
	
	/** checkSurroundingCells Method:
	  *      This recursive functional method finds contiguous sequences of letters on the Boggle board
	  *       @param  int wordIndex 		- current letter index in the word to be found
	  *       @param  int r					- board row index of current letter
	  *       @param  int c					- board column index of current letter
	  *       @param  String word			- word (sequence of letters) that is being matched
	  *       @param  boolean usedCells[][] - array of cell coordinates already used for matching a letter
	  *       @return void
	  */
	private boolean checkSurroundingCells(int wordIndex, int r, int c, String word, boolean usedCells[][]) {
		
		// Base case: If there's no more letters then we found the whole sequence
		//
		if (wordIndex == word.length()) {													// No more letters to find
			return true;																	// Recursion done: found word
		}
		
		usedCells[r][c] = true; 															// Mark that current cell is in use
		char nextChar = word.charAt(wordIndex);												// Find the next letter in the word
		
		// Look at the eight spots around for the next character
		//
		boolean found = false;																// Assume not found
		for (int r2 = r-1; r2 <= r+1 && !found ; r2++){										// Loop through 3 rows
			for (int c2 = c-1;  c2 <= c+1 && !found; c2++) {								// Loop through 3 columns
				if (r2 >= 0 && c2 >= 0 && r2 < _dim && c2 < _dim && 						// Check that location is on board
					!usedCells[r2][c2] &&													// Check that cell is not already in word
					_gridValues[r2][c2] == nextChar) { 										// Check that cell matches next letter
					if (checkSurroundingCells(wordIndex+1,r2,c2,word,usedCells)) { 			// Recursively look for next match
						_gridLabels[r2][c2].setBackground(Color.GREEN);						// Found: set cell background green
						found = true;														// Found: remember that we found it
					}
					else {
						usedCells[r2][c2] = false;  										// Not found, unmark used info
					}																		// End 'else'
				}																			// End 'if'
			}																				// End 'for c2' loop
		}																					// End 'for r2' loop
		return found;																		// Return found status
	}
	
	/** setupBoggle Method:
	  *   This public procedural method is called from the applet to setup the Boggle game.
	  *   It sets up both the input panel and the maze grid               
	  * 
	  *	    1) Calls Random.nextInt, and swing/awt methods to setup of the Boggle board
	  *		2) Calls setupBoogleInputPanel to set up the gui to read the input word
	  *
	  *       @param  none   
	  *       @return void
	  */
	public void setupBoggle() {
		GamesWithRecursion.clearPanel(_applet.getInputPanel());								// Clear input panel
		GamesWithRecursion.clearPanel(_applet.getGridPanel());								// Clear grid board panel
		
		_applet.getGridPanel().setLayout(new GridLayout(_dim,_dim));						// Set board dimensions
		_applet.getGridPanel().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		// Add some padding
		
		// Choose random letters and populate Boggle board
		//
		_applet.setStatusMessage(_applet.getDefaultMessage(), Color.BLACK);					// Reset to default status message
		for (int r = 0; r < _dim; r++) {													// Loop through rows
			for (int c = 0; c < _dim; c++) {												// Loop through columns
				_gridValues[r][c] = 'A' + _rand.nextInt(26);								// Pick random letters
			
				String s = String.valueOf((char)_gridValues[r][c]);							// String version of letter
				JLabel l = new JLabel(s, JLabel.CENTER);	 								// Create JLabel
				_gridLabels[r][c] = l;														// Add to array of letter Labels
				l.setBorder(BorderFactory.createLineBorder(Color.BLACK));					// Set Label border
				l.setOpaque(true);															// Set opaque so background can change
				l.setBackground(Color.WHITE);												// Set background to white
				_applet.getGridPanel().add(l);												// Add to applet board
			}																				// End 'for c'
		}																					// End 'for r'
		
		// Set up the input panel.
		//
		_applet.getInputPanel().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		// Add padding
		_applet.getInputPanel().add(new JLabel("Enter Contiguous Sequence Of Letters:"));
		_applet.getInputPanel().add(_word);
		_word.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { wordCheck(_word.getText()); }	} );
	}
}
