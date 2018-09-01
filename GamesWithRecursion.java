//========================================================================================================================
// "Games with Recursion" Assignment
// Katherine Inglis
// September 2017
// Java 8
// class GamesWithRecursion
//========================================================================================================================
// Problem Definition - To create two games, Boggle and Maze, both of which exercise the use of recursive methods.
//                     The project is broken into 3 classes: GamesWithRecution, Maze and Boggle
//
//         class GamesWithRecursion: implements an JApplet, with 5 panels that are used to display the two games.
//                                  
//                
// Global identifiers for class GamesWithRecursion
//
//	- let PANEL_WIDTH represent the width of the applet (type final integer)
//	- let PANEL_HEIGHT represent the height of the applet (type final integer)
//	- let _gridPanel represent the center grid panel (type JPanel)
//	- let _statusMessage represent the JLabel used to display error and status messages (type JLabel)
//	- let _titleLabel represent the JLabel used to display MAZE or BOGGLE title message (type JLabel)
//	- let _gridPanel represent the JPanel used for the center game grid panel (type JPanel)
//	- let _inputPanel represent the JPanel used for game input (type JPanel)
//	- let _defaultMessage represent default status message (type String)

// Class Import List
//
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

// ============================================================================================================
// GamesWithRecursion:	Extends the JApplet class so that Maze and Boggle can be implemented as applet games.
//                      Five panels are created:
//                      	- Title panel: display the game title and author name   
//							- Menu panel: with options to play the two games, display help or exit.
//                          - Grid panel: to display the game grids and the help text
//                          - Input panel: for game input
//							- Status panel: for error or success messages
//						The class also provides a small number of public methods so that Maze and Boggle can 
//                      access the Game, Input and Status panels.
//
public class GamesWithRecursion extends JApplet {
	
	// ----------------------------
	// 'global' constants.  Having these defined at the top makes them easily accessible to change if needed
	//
	private final int PANEL_WIDTH = 550;							// constant: The width of the applet
	private final int PANEL_HEIGHT = 400;							// constant: The height of the applet
	
	// -----------------------------
	// field/instance/'global' variables that are used across multiple functions.  Many of these can't be passed as parameters
	// because they are used in method calls from Action Listeners.
	//
	private JLabel _statusMessage;									// JLabel: to display error and status messages
	private JLabel _titleLabel;										// JLabel: to display MAZE or BOGGLE title message
	
	private JPanel _gridPanel = new JPanel();						// JPanel: The center game grid panel
	private JPanel _inputPanel = new JPanel(); 						// JPanel: Used for Game input
	
	private String _defaultMessage = "Have Fun Playing!!";			// String: Default Status Message
	
	// --------------------------------------------------------------------
	// JApplet 'init' method 
	// --------------------------------------------------------------------
	
	/** init Method:
	  *  This procedural method is called from the Applet controller to initialize the Applet                
	  * 
	  *	    1) Calls setSize to set the size of the main panel
	  *		2) Calls 4 methods to create the main panels
	  *     3) Creates the Boggle and Maze game objects
	  *
	  *       @param 	none  
	  *     	
	  *       @return void
	  */
	public void init() {
		setSize(PANEL_WIDTH, PANEL_HEIGHT);							// Set panel width and height
		
		add(getGridPanel(), BorderLayout.CENTER);					// Add Grid Panel to the center
		add(createMenuPanel(), BorderLayout.WEST);					// Add Menu Panel to the left side
		add(createSouthPanel(), BorderLayout.SOUTH);				// Add Answer Buttons to the bottom
		add(createTitlePanel(), BorderLayout.NORTH);				// Add the Title Panel to the top
		
		playMazePressed();											// Have maze be the default game
	}
	
	// --------------------------------------------------------------------
	// public Accessor Methods.  Accessible by Boggle and Maze
	// --------------------------------------------------------------------
	
	/** getInputPanel Method:
	  *    This functional accessor method returns the input panel. It's declared public so that 
	  *    Boggle and Maze class can access it.
	  *       @param	none        	
	  *       @return JPanel - the south panel which contains the Input panel and Status Panel
	  */
	public JPanel getInputPanel() { 
		return _inputPanel; 										// Return the input panel
	}
	
	/** getGridPanel Method:
	  *    This functional accessor method returns the main game grid panel. It's declared public so that 
	  *    Boggle and Maze class can access it.
	  *       @param	none         	
	  *       @return JPanel - the main game grid panel
	  */
	public JPanel getGridPanel() { 
		return _gridPanel; 											// Return the main game grid panel
	}
	
	/** getDefaultMessage Method:
	  *    This functional accessor method returns the default status message. It's declared public so that 
	  *    Boggle and Maze class can access it.
	  *       @param	none        	
	  *       @return String - the default status message
	  */
	public String getDefaultMessage() { 
		return _defaultMessage; 									// Return the default status message
	}
	
	// --------------------------------------------------------------------
	// public Mutator Methods.  Accessible by Boggle and Maze
	// --------------------------------------------------------------------
	
	/** setStatusMessage Method:
	  *    This procedural mutator method sets the game's status message. It's public so that 
	  *    Boggle and Maze classes can access it.
	  *       @param  String	- The string containing the new game status message
	  *       @param  Color 	- The Color to set the status message
	  *       @return void
	  */
	public void setStatusMessage(String s, Color c) { 
		_statusMessage.setText(s); 									// Set the new status message
		_statusMessage.setFont(new Font("Serif", Font.PLAIN, 20));	// Give it a big font
		_statusMessage.setForeground(c);							// Set the color for the message text
	}
	
	// ---------------------------------------------------------------------
	// Regular Methods
	// ---------------------------------------------------------------------
	
	/** playBogglePressed Method:
	  *  This procedural method is called when the Play Boggle button is pressed. 
	  *  It sets up the title, buttons and calls the boggle object reference to set up the new game.                
	  * 
	  *	    1) Calls setTitleSet to the Game Title
	  *     2) Access and call the setUpBoggle method via the Boggle object reference
	  *
	  *       @param  none   
	  *     	
	  *       @return void
	  */
	private void playBogglePressed(int dim) {
		_titleLabel.setText("BOGGLE " + dim + "x" + dim);						// Set the title at the top of the GUI to BOGGLE
		Boggle boggle = new Boggle(this, dim);						// Create the Boggle object reference
		boggle.setupBoggle();										// Use the Boggle object reference to set up the Boggle game
	}
	
	/** playMazePressed Method:
	  *    This procedural method is called when the Play Boggle button is pressed. 
	  *    It sets up the title, buttons and calls the boggle object reference to set up the new game.                
	  * 
	  *	    1) Calls setTitleSet to the Game Title
	  *     2) Access and call the setUpBoggle method via the Boggle object reference
	  *
	  *       @param 	none   
	  *     	
	  *       @return void
	  */
	private void playMazePressed() {
		_titleLabel.setText("MAZE");											// Set the title at the top of the GUI to BOGGLE
		Maze maze = new Maze(this);									// Create the Maze game instance
		maze.setupMaze();											// Use the Maze object reference to set up the Boggle game
	}
	
	/** clearPanel Method:
	  *    This procedural helper method clears a panel to prepare for it to be redrawn. It's declared
	  *    public so that the Maze and Boggle classes can access it               
	  * 
	  *	    1) Calls the Panel methods setVisible and removeAll
	  *
	  *       @param  JPanel - the Panel to be cleared  
	  *     	
	  *       @return void
	  */
	static public void clearPanel(JPanel panel) {
		if (panel != null) {										// Check if panel is non null
			panel.setVisible(false);								// Required so that removeAll doesn't take too long
			panel.removeAll();										// Remove the existing panel
			panel.setVisible(true);									// Reset the panel to be visible again
		}
	}
	
	/** createSouthPanel Method:
	  * 	This functional method creates the game input Panel and the Status message panel.
	  *
	  *	    1) Calls the swing/awt methods to create Panels for the input and status panels
	  *
	  *       @param	none    
	  *     	
	  *       @return JPanel - the south panel which contains the Input panel and Status Panel
	  */
	private JPanel createSouthPanel() {
		JPanel southPanel = new JPanel(new BorderLayout(5,5));					// Create south panel with padding
		getInputPanel().setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	// Add padding around input panel
		southPanel.add(getInputPanel(), BorderLayout.NORTH);					// Add input panel to south Panel
		
		JPanel statusPanel = new JPanel();										// Create the Status Panel
		statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));		// Add padding around the status panel
		_statusMessage = new JLabel(getDefaultMessage());						// Create the default status Message
		statusPanel.add(_statusMessage);										// Add status message to panel
		southPanel.add(statusPanel, BorderLayout.SOUTH);						// Add status panel to south Panel
		
		return southPanel;														// Return the south panel
	}
		
	/** createMenuPanel Method:
	  *  This functional method creates and returns a Menu Panel of Buttons              
	  * 
	  * 	1) Calls, via ActionListeners, System.exit, playBogglePressed, playMazePressed and createHelp
	  *	    2) Calls the swing/awt methods to create Panels, Borders, Buttons, ActionListeners
	  *
	  *       @param    none 
	  *     	
	  *       @return   JPanel - menu panel
	  */
	private JPanel createMenuPanel() {
		
		// Create Menu Panel with a compound border
		//
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
		Border emptyBorder = BorderFactory.createEmptyBorder(10, 0, 10, 0);
		Border blueBorder = new LineBorder(Color.blue, 3);
		Border compoundBorder = BorderFactory.createCompoundBorder(emptyBorder, blueBorder);
		emptyBorder = BorderFactory.createEmptyBorder(5, 5, 0, 20);
		compoundBorder = BorderFactory.createCompoundBorder(compoundBorder, emptyBorder);
		menuPanel.setBorder(compoundBorder);

		// Add the Menu title to the Panel
		menuPanel.add(new JLabel ("    Menu"));			
		menuPanel.add(Box.createRigidArea(new Dimension(20, 10)));	
				
		// Add the Play Boggle Button with Action Listener
		JButton playBoggleButton = new JButton("Play Boggle 5x5");	
		playBoggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { playBogglePressed(5);} } ); 
		menuPanel.add(playBoggleButton);
		menuPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		
		// Add the Play Boggle Button with Action Listener
		playBoggleButton = new JButton("Play Boggle 6x6");	
		playBoggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { playBogglePressed(6);} } ); 
		menuPanel.add(playBoggleButton);
		menuPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		
		// Add the Play Maze Button with Action Listener
		JButton playMazeButton = new JButton("Play Maze");
		playMazeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { playMazePressed();} } );
		menuPanel.add(playMazeButton);
		menuPanel.add(Box.createRigidArea(new Dimension(20, 10)));		
		
		// Add the Exit button with Action Listener
		JButton exitButton = new JButton ("EXIT");
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { System.exit(0);} } );
		menuPanel.add(exitButton);
		menuPanel.add(Box.createRigidArea(new Dimension(20, 10)));
		
		// Add the Help button with Action Listener
		JButton helpButton = new JButton("Help");
		menuPanel.add(helpButton);
		helpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { createHelp(); } } );

		return menuPanel; 	// Return the menu panel so it can added to the main game panel
	}
	
	/** createTitlePanel Method:
	  *   This functional method creates and returns the top Title Panel which contains the the name of the game,
	  *   my name, the course name, the score, the timer and the points for the next correct coordinate.
      *
	  *     1) Calls gameClockListner - triggered every 1 second to decease the game clock
	  *     2) Calls pointsListner - triggered every 2secs to change the points for a correct answer
	  *	    3) Calls swing/awt methods to create Panels, Borders, Labels, and Timer   
	  *  
	  *       @param 		none
	  *       			
	  *       @return   	JPanel - the game's title Panel
	  */
	private JPanel createTitlePanel() {
		
		// Create the title panel
		JPanel titlePanel = new JPanel(new BorderLayout(5,5));
		
		// Add course name, author name and default Title
		titlePanel.add(new JLabel("  ICS 4UI"), BorderLayout.WEST);
		titlePanel.setBorder(new BevelBorder(BevelBorder.RAISED));
		JPanel gameTitlePanel = new JPanel();
		_titleLabel = new JLabel("BOGGLE");
		_titleLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		_titleLabel.setForeground(Color.blue);
		gameTitlePanel.add(_titleLabel);
		titlePanel.add(gameTitlePanel, BorderLayout.CENTER);
		titlePanel.add(new JLabel("Katherine Inglis  "), BorderLayout.EAST);

		return titlePanel;		// Return the title panel so it can added to the main game panel
	}
	
	/** createHelp Method:
	  *   This procedural method adds the Help text to the center panel                
	  * 
	  *	    1) Calls clearPanel, setTitle
	  *		2) swing/awt methods to create and set Labels, and Borders 
	  *       @param  none  
	  *     	
	  *       @return void
	  */
	private void createHelp() {
		clearPanel(getGridPanel());					// Clear away the game grid so the help text can be added
		
		_titleLabel.setText("HELP");

		getGridPanel().setLayout(new BoxLayout(getGridPanel(), BoxLayout.Y_AXIS));
		getGridPanel().setBorder(BorderFactory.createEmptyBorder(5, 10, 50, 5));
		getGridPanel().add(new JLabel("Use the Menu on the left to choose one of two games to play:"));
		getGridPanel().add(new JLabel("Boggle: "));
		getGridPanel().add(new JLabel("   Find and enter contiguous sequences of letters."));
		getGridPanel().add(new JLabel("   Letters are touching if they are horizaontally, vertically or"));
		getGridPanel().add(new JLabel("   diagonally adjacent."));
		getGridPanel().add(new JLabel("Maze: "));
		getGridPanel().add(new JLabel("   A default maze is loaded with a default start point."));
		getGridPanel().add(new JLabel("   Hit solve to find a path from the start to the exit point."));
		getGridPanel().add(new JLabel("   You can enter and set an alternative Start Point and/or"));
		getGridPanel().add(new JLabel("   enter an alternative Maze file name to load another Maze file."));
	}
}