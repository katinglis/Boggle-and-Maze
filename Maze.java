//========================================================================================================================
// "Games with Recursion" Assignment
// Katherine Inglis
// September 2017
// Java 8
// class Maze
//========================================================================================================================
// Problem Definition - To create two games, Boggle and Maze, both of which exercise the use of recursive methods.
//                     The project is broken into 3 classes: GamesWithRecution, Maze and Boggle.
//
//         class Maze: 		Reads a 7x11 Maze pattern from a file, allows the user to specify a start location and
//                          uses recursion to find an exit point (if possible).
//                

// Global identifiers for class GamesWithRecursion
//
//	- let ROWS represent the number of rows in the maze (type final integer)
//	- let COLS represent the number of columns in the maze (type final integer)
//	- let MAZE_DOT represents an open cell in the maze (type final integer)
//	- let MAZE_BORDER represents a border cell in the maze (type final integer)
//	- let MAZE_EXIT represents a maze exit cell (type final integer)
//	- let MAZE_START represents a maze start point (type final integer)
//	- let MAZE_MARK represent that a maze cell has been marked by findPath (type final integer)
//  - let _mazeStates represent the state of each cell in the maze (type int[][])
//  - let _gridLabels represent theJLabels used for GUI representation of maze (type JLabel[][])
//  - let _mazeInputFileName represent the maze file name (type String)
//  - let _xCoord represent the 'x' value for the starting point (type JTextField)
//  - let _yCoord represent the 'y' value for the starting point (type JTextField)
//  - let _applet represent the applet object reference (type GamesWithRecursion)

// Class Import List
//
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

//============================================================================================================
// Maze: Implement a maze game that allows mazes to be loaded, start points to be set and uses a recursive
//       algorithm to find the path, if any, from the start point to the exit.
//
public class Maze {
	
	// ---------------------
	// 'global' constants.  
	//
	final private int ROWS = 7;											// Number of rows in the maze
	final private int COLS = 11;										// Number of columns in the maze
	
	final private int MAZE_DOT 		= 0;								// Represents an open cell in the maze
	final private int MAZE_BORDER	= 1;								// Represents a border cell in the maze
	final private int MAZE_EXIT 	= 2;								// Represents a maze exit cell
	final private int MAZE_START 	= 3;								// Represents a maze Start point
	final private int MAZE_MARK 	= 4;								// Maze cell has been marked by findPath
	
	// -----------------------------
	// 'global' variables
	//
	private int _mazeStates[][] = new int[ROWS][COLS];					// The state of each cell in the maze
	private JLabel _gridLabels[][] = new JLabel[ROWS][COLS];			// JLabels used for GUI representation of maze
	private JTextField _mazeInputFileName = new JTextField("maze1", 8);	// Maze File Name
	private JTextField _xCoord = new JTextField("1", 2);				// 'x' value for the starting point
	private JTextField _yCoord = new JTextField("3", 2);				// 'y' value for the starting point
	private GamesWithRecursion _applet;									// The game applet object reference
	
	// -------------------------------------------------------
	// public CONSTRUCTOR
	// ------------------------------------------------------
	
	/** Maze Constructor:
	  * 	 This public constructor stores the applet object reference so the Maze class can access 
	  *      the applet to update the gui
	  *       @param  GamesWithRecurions applet - the applet class
	  *       @return void
	  */
  	public Maze(GamesWithRecursion applet) {
		_applet = applet;												// Save the applet object reference for future use.
	}

  	// -------------------------------------------------------
  	// Regular Methods
  	// -------------------------------------------------------
  	
	/** solveMaze Method:
	  *      This procedural method locates the start position and then calls the recursive method findPath 
	  *      to find one path from the Start to the Exit.
	  *       @param  void
	  *       @return void
	  */
	private void solveMaze() {	
		clearPath(); 													// clear any previous path
			
		boolean found = false;											// Track whether path is found, assume false
		for (int r = 0; r < ROWS && !found; r++) {						// Iterate through the rows of the maze
			for (int c = 0; c < COLS && !found; c++) {					// Iterate through the maze columns
				if (_mazeStates[r][c] == MAZE_START) {					// Check if current cell is the start position
					found = findPath(r, c);								// Recursively find path to exit
					_gridLabels[r][c].setText("S");						// Reset the Start Position text
					_gridLabels[r][c].setBackground(Color.GREEN);  		// Reset the Start Position color
					_mazeStates[r][c] = MAZE_START;						// Reset the Start Position state
				}
			}
		}
		if (!found) {													// If no path exists 
			_applet.setStatusMessage("Path Not FOUND!", Color.RED);		// Update the gui status message
	    }
	}
	
	/** findPath Method:
	  * 	 This recursive procedural method search for the path to the exit  
	  *       @param int r - the maze row number
	  *       @param int c - the maze column number 
	  *       @return void
	  */
	private boolean findPath(int r, int c) {
		
		// Recursive method's base cases that determine if the recursion should stop.  Stop
		// if the input is an illegal spot or if it is the exit
		//
		if (r < 0 || c < 0 || r >= ROWS || c >= COLS ) {				// Check that the coordinates are inside the grid
			return false;												// The location is outside the grid, so return false
		}
		if (_mazeStates[r][c] == MAZE_EXIT) {							// Check if the coordinates are for the exit
			_gridLabels[r][c].setBackground(Color.GREEN);				// Make the exit green
			return true;												// Return true because the exit is found 
		}
		if (_mazeStates[r][c] == MAZE_BORDER || 						// Check if the coordinates are at a border or
			_mazeStates[r][c] == MAZE_MARK) {							// Check if the coordinates are already on the path
			return false;												// Return false because the coordinates can't be on the path
		}
	
		// Mark the current cell to indicate that it is potentially on the path to the exit.
		//	
		_mazeStates[r][c] = MAZE_MARK;									// Mark that this cell is not currently on the path
		_gridLabels[r][c].setText("+");									// Set the text to a '+' sign
		_gridLabels[r][c].setBackground(Color.GREEN);					// Set the color to green
	
		// Recursive parts of the algorithm
		if (findPath(r-1, c) == true) {									// Recursively find path from cell to the left
			return true;												// Found exit cell, so return true
		}
		if (findPath(r, c-1) == true) { 								// Recursively find path from cell above
			return true;												// Found exit cell, so return true
		}
		if (findPath(r+1, c) == true) { 								// Recursively find path from cell to the right
			return true;												// Found exit cell, so return true
		}
		if (findPath(r, c+1) == true) { 								// Recursively find path from cell to the right
			return true;												// Found exit cell, so return true
		}
	
		// These coordinates are not on the path so unmark the coordinates
		//
		_mazeStates[r][c] = MAZE_DOT;									// Reset Cell state to a dot
		_gridLabels[r][c].setText(".");									// Reset Cell text to a dot
		_gridLabels[r][c].setBackground(Color.WHITE);					// Reset Cell color to white
	
		return false;													// Return false, these coordinates are not on path
	}
  	
	/** setupMaze Method:
	  *   This public procedural method is called from the applet to setup the Maze game.
	  *   It sets up both the input panel and the maze grid               
	  * 
	  *	    1) Calls setupInputPanel to set up the gui to read start position, or load a new file
	  *		2) Calls loadFileAndCreateMaze to load the maze from a file and display it in the applet
	  *
	  *       @param  none   
	  *       @return void
	  */
	public void setupMaze() {
		setupInputPanel();												// Set up Input Panel to get coords, start position, etc
		loadFileAndCreateMaze();										// Load the Maze from a File and display it in the applet
	}
	
	/**
	 *  setMazeStartCoord Method:
	  *   This procedural clears any previous path            
	  *
	  *       @param  none   
	  *       @return void
	  */
	private void clearPath() {
		for (int r = 0; r < ROWS; r++) {						// Iterate through the rows of the maze
			for (int c = 0; c < COLS; c++) {					// Iterate through the maze columns
	    		if (_mazeStates[r][c] == MAZE_MARK) {					// Reset old path cell
	    			_gridLabels[r][c].setBackground(Color.WHITE);	
	    			_mazeStates[r][c] = MAZE_DOT;						// Reset previous path to dot state
	    		}									
			}
		}
	}

	/** setMazeStartCoord Method:
	  *   This procedural method is called to change the Maze start coordinates.              
	  * 
	  *	    1) Calls parseInt to change the start coordinates from String to integer
	  *		2) Calls setStatusMessage to indicate whether change was successful
	  *     3) Calls _mazeStates and _mazeStates to update the maze start coordinates.
	  *
	  *       @param  none   
	  *       @return void
	  */
	private void setMazeStartCoord() {
		int r = 0;																		// Row index for new start coordinate
		int c = 0;																		// Column index for new start coordinate
		boolean valid = true;															// Track if the new coordinates are valid, assume yes
		
		clearPath();
		
		// Try block to read the start coordinates, convert them from String to int and catch
		// any conversion errors
		//
		try {
			c = Integer.parseInt(_xCoord.getText());									// Convert x coord from String to int
			r = Integer.parseInt(_yCoord.getText()); 									// Convert y coord from String to int
			
			// Check if the coordinates are valid, and set status message if they aren't
			//
			if (r < 0 || r >= ROWS || c < 0 || c >= COLS ||								// Check that coordinates are in maze
				(_mazeStates[r][c] != MAZE_DOT && 										// Check that cell is valid state
				 _mazeStates[r][c] != MAZE_START)) {									// Check that cell is valid state
				_applet.setStatusMessage("Coordinates Must Be Inside the Maze!", Color.RED);
				valid = false;															// Remember new coordinates are invalid
			}																			// End of 'if' statement
		}																				// End of 'try' statement
		catch (NumberFormatException e) {
			_applet.setStatusMessage("Coordinates Must be Numbers!",Color.RED);		 // Coordinates are not integers
			valid = false;																// Coordinates are invalid
		}																				// End of catch statement
		
		if (valid) {																	// If coordinates are valid
		    _applet.setStatusMessage(_applet.getDefaultMessage(), Color.BLACK);			// reset status message to the default
		    
		    // Reset the previous path and previous start coordinates
		    //
		    for (int i = 0; i < ROWS; i++) {											// for 'int i' loop
		    	for (int j = 0; j < COLS; j++) {										// for 'int j' loop
		    		_gridLabels[i][j].setBackground(Color.WHITE);						// Reset all cells to white
		    		if (_mazeStates[i][j] == MAZE_START || 			 					// Reset old Start cell
		    			_mazeStates[i][j] == MAZE_MARK) {								// Reset old path cell
		    			_gridLabels[i][j].setText(".");							    	// Reset previous path to dot text
		    			_mazeStates[i][j] = MAZE_DOT;									// Reset previous path to dot state
		    		}																	// End of 'if'
			   }																		// End of 'for j' loop
		    }																			// End of 'for i' loop
		
		    // Set the new start coordinates
		    //
		    _mazeStates[r][c] = MAZE_START;												// Set start position state
		    _gridLabels[r][c].setText("S");												// Set start position text
		    _gridLabels[r][c].setBackground(Color.GREEN);								// Set start position color
		}
	}
	
	/** loadFileAndCreateMaze Method:
	  *   This procedural method is called to change the Maze start coordinates.              
	  * 
	  *	    1) Calls clearPanel and setLayout to set maze grid layout
	  *		2) Calls BufferReader and readline to read maze file 
	  *     3) Calls setGridLabel to update new grid values
	  *     4) Calls Swing/awt methods to update new maze grid panel
	  *
	  *       @param  none   
	  *       @return void
	  */
	private void loadFileAndCreateMaze() {
		GamesWithRecursion.clearPanel(_applet.getGridPanel());							// clear old contents out of game grid
		_applet.getGridPanel().setLayout(new GridLayout(ROWS+1,COLS+1));				// set grid dimensions
		_applet.getGridPanel().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	// Add some padding
		
		try (BufferedReader br = new BufferedReader(new FileReader(_mazeInputFileName.getText()))) {
			Boolean fileOk = true;														// Keep track of whether the format is okay
			for (int r = 0; r < ROWS && fileOk; ++r ) {									// Loop through each row of maze
				String s = br.readLine();												// Read one line of the maze
			    if (s == null || s.length() != COLS) {									// Check length to avoid formating issues
			    	fileOk = false;														// Flag error for wrong row length
			    } 
			    else {
			    	// Create a label for the y axis value
			    	//
			    	JLabel l = new JLabel(String.valueOf(r), JLabel.CENTER);
					l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					_applet.getGridPanel().add(l);
					
					// Read each character from one of the input file and create grid entry information
					//
			    	for (int c = 0; c < COLS; c++) {
			    		l = new JLabel(s.substring(c, c+1), JLabel.CENTER);				// Get one character to add to cell
			    		_gridLabels[r][c] = l;											// Update new grid label
			    		_gridLabels[r][c].setOpaque(true);								// Make it opaque so background can change
			    		_gridLabels[r][c].setBackground(Color.WHITE);					// Set background white
			    		l.setBorder(BorderFactory.createLineBorder(Color.BLACK));		
			    		_applet.getGridPanel().add(l);									// Add new label to the grid
			    		
			    		// Switch on the character and update the grid state array
			    		//
			    		switch (s.charAt(c)) {											// Switch on char 
			    			case '.':										
			    				_mazeStates[r][c] = MAZE_DOT;							// Set Maze State to dot cell
			    				break;	
			    			case 'B':
			    				_mazeStates[r][c] = MAZE_BORDER;						// Set Maze State to border cell
			    				break;
			    			case 'X':
			    				_mazeStates[r][c] = MAZE_EXIT;							// Set Maze State to exit cell
			    				_gridLabels[r][c].setForeground(Color.MAGENTA);			// Color the 'X' symbol magenta 
			    				break;
			    			case 'S':
			    				_mazeStates[r][c] = MAZE_START;							// Set Maze state to start cell
			    				_gridLabels[r][c].setBackground(Color.GREEN);			// Color the start background green
			    				_xCoord.setText(String.valueOf(c)); 
			    				_yCoord.setText(String.valueOf(r));
			    				break;
			    			default:
			    				fileOk = false;											// Invalid character in maze
			    				break;
			    		}																// End of switch statement
			    	}																	// End of 'for c' loop
			    }																		// End of else block
			}																			// End of 'for r' loop
			    
			if (fileOk) {
				// Add one blank label at start of the y axis
				//
				JLabel l = new JLabel(" ", JLabel.CENTER);	
				l.setBorder(BorderFactory.createLineBorder(Color.BLACK));			
				_applet.getGridPanel().add(l);
				
				// Add a label for each y axis value
		    	for (int c=0; c<11; c++) {
		    		l = new JLabel(String.valueOf(c), JLabel.CENTER);					// create label for Y axis value
					l.setBorder(BorderFactory.createLineBorder(Color.BLACK));			// Add a black or grey border
					_applet.getGridPanel().add(l);
		    	}
				
			    _applet.setStatusMessage("Maze File Loaded", Color.GREEN);				// Display that the file loaded correctly
			}
			else {
			    _applet.setStatusMessage("Failed. Format Bad", Color.RED);				// Display that the file format was incorrect
			}
		} 																				// End of try statement
		catch (IOException e) {															// Catch any IO errors
			_applet.setStatusMessage("Failed. File not found", Color.RED);    			// Display that the file loading failed
		}																				// End of catch statement
	}
	
	/** setupInputPanel Method:
	  *   This procedural method is called to create the Input Panel which contains
	  *   buttons and text field to load a new maze, and textfields and a button to set new start coordinates               
	  * 
	  *	    1) Calls setupInputPanel to set up the gui to read start position, or load a new file
	  *		2) Calls the swing/awt methods to create Panels, Borders, Buttons, ActionListeners
	  *
	  *       @param  none   
	  *       @return void
	  */
	private void setupInputPanel() {	
		// Clear the Input Panel ... may contain Boggle fields
		//
		GamesWithRecursion.clearPanel(_applet.getInputPanel());							
		
		// Create the "Load Maze File" button
		//
		JButton loadButton = new JButton("Load Maze File:");	
		_applet.getInputPanel().add(loadButton);
		_applet.getInputPanel().add(_mazeInputFileName);
		loadButton.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) { loadFileAndCreateMaze();} } ); 
		
		// Create the "Find Path" Button
		JButton findPathButton = new JButton("Find Path");
		_applet.getInputPanel().add(findPathButton);
		findPathButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { solveMaze();} } );
		
		// Create the "Set Start Coords" Button
		JButton startCoord = new JButton("Set Start Coords:");
		startCoord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { setMazeStartCoord();} } );
		_applet.getInputPanel().add(startCoord);
		_applet.getInputPanel().add(_xCoord);
		_applet.getInputPanel().add(_yCoord);
	}
} 