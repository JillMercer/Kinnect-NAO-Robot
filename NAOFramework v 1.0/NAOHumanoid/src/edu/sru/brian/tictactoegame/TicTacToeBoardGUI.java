package edu.sru.brian.tictactoegame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.FlowLayout;

import javax.swing.UIManager;

import edu.sru.brian.tictactoegame.decisiontree.GeneratePurnedDescisionTree;

/**
 * File:TicTacToeBoardGUI.java
 * @author Brian
 * Description: This is the main GUI and provides a tic tac toe board interface
 * for testing the AIs.
 *
 */
public class TicTacToeBoardGUI extends JFrame implements TurnListener, GameStatusListener {

	private JPanel contentPane;
	private JPanel settingsPanel;
	private XOButton[] btnArray = new XOButton[9];
	private Game game = new Game();
	private PlayerAIEnforcer playerAI;
	private MinMaxTreeAI mmTreeAI;
	
	private boolean isChangable =true;
	private boolean isResetable=true;
	private boolean useCustomCursor=true;
	private boolean showAlerts=true;
	
	private Cursor xCursor;
	private Cursor oCursor;
	
	private Markers curTurn = Markers.EMPTY;
	
	public static final String RESOURCE_DIR = "resources\\";
	public static final String XPNG_FILENAME = "xcursor.png";
	public static final String OPNG_FILENAME = "ocursor.png";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TicTacToeBoardGUI frame = new TicTacToeBoardGUI(true,true,true,true);
					//TicTacToeBoardGUI frame = new TicTacToeBoardGUI(false,false,false,false);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public TicTacToeBoardGUI(boolean isResetable, boolean isChangable, boolean useCustomCursor, boolean showAlerts)
	{
		this();
		setChangable(isChangable);
		//this.isChangable=true;
		setResetable(isResetable);
		///this.isResetable = isResetable;
		setUseCustomCursor(useCustomCursor);
		setShowAlerts(showAlerts);
	}

	/**
	 * Create the frame.
	 */
	public TicTacToeBoardGUI() {
		
		xCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(RESOURCE_DIR+XPNG_FILENAME).getImage(),
				new Point(15,15),"X Cursor");
		
		oCursor = Toolkit.getDefaultToolkit().createCustomCursor(
				new ImageIcon(RESOURCE_DIR+OPNG_FILENAME).getImage(),
				new Point(15,15),"O Cursor");
		
		game.addTurnListener(this);
		game.addGameStatusListener(this);
		
		//playerAI = new PlayerAIEnforcer(new JavaRulesAI(), Markers.X, game.getCurTurn(), game);
		mmTreeAI = new MinMaxTreeAI();
		mmTreeAI.setFileName(MinMaxTreeAI.DEFAULT_FILE_NAME);
		mmTreeAI.loadTree();
		playerAI = new PlayerAIEnforcer(mmTreeAI, Markers.X, game.getCurTurn(), game);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		settingsPanel = new JPanel();
		contentPane.add(settingsPanel, BorderLayout.NORTH);
		settingsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onResetBtnClicked(e);
			}
		});
		settingsPanel.add(btnReset);
		
		if(!this.isResetable)
		{
			settingsPanel.setVisible(false);
		}
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(3, 3, 0, 0));
		
		for(int i=0;i<btnArray.length;i++)
		{
			btnArray[i] = new XOButton("Button "+i, i);
			final XOButton temp = btnArray[i];
			btnArray[i].setEnabled(this.isChangable);
			btnArray[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					onBoardBtnClicked(e);
				}
				
			});
			panel_1.add(btnArray[i]);
		}
		
		System.out.println(btnArray[0].getBackground());
		
		getUserChoice();
		
		game.start();
	}

	/**
	 * called when end turn event from Game class
	 */
	@Override
	public void onOTurnEnd(Markers marker, int pos) {
		// TODO Auto-generated method stub
	}

	/**
	 * called when x end turn event from Game class
	 */
	@Override
	public void onXTurnEnd(Markers marker, int pos) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * called when O end turn event from Game class
	 */
	@Override
	public void onTurnEnd(Markers marker, int pos) {
		// TODO Auto-generated method stub
		btnArray[pos].setDisplayState(marker);
		btnArray[pos].repaint();
	}

	/**
	 * called when turn start event from Game Class
	 */
	@Override
	public void onTurnStart(Markers marker) {
		// TODO Auto-generated method stub
		curTurn=marker;
		System.out.println("Start turn! Marker: "+marker);
		
		if(marker == Markers.X)
		{
			this.setTitle("X Turn");
		}
		else if(marker == Markers.O)
		{
			this.setTitle("O Turn");
		}
		setCustomCursor();
	}
	
	/**
	 * Sets the custom cursor for the GUI
	 */
	protected void setCustomCursor()
	{
		if(useCustomCursor)
		{
			if(curTurn == Markers.X)
			{
				this.setCursor(xCursor);
			}
			else if(curTurn == Markers.O)
			{
				this.setCursor(oCursor);
			}
		}
	}
	
	/**
	 * called when a button on the GUI is clicked
	 * @param e
	 */
	public void onBoardBtnClicked(ActionEvent e)
	{
		int pos=0;
		XOButton temp = (XOButton) e.getSource();
		pos = temp.getPosOnBoard();
		
		Markers lastTurn;
		lastTurn = curTurn;
		
		int results = game.takeTurn(pos);
		
		System.out.println("TakeTurn: "+lastTurn);
		
		
		System.out.println("pos:"+pos+"!");
		System.out.println("Button Clicked!");
		
	}
	
	/**
	 * Called when the reset button is clicked
	 * @param e
	 */
	public void onResetBtnClicked(ActionEvent e)
	{
		getUserChoice();
		game.start();
	}
	
	public void getUserChoice()
	{
		int choice;
		choice = JOptionPane.showConfirmDialog(this, "Would you like to go first?", "Choose who goes first!", JOptionPane.YES_NO_OPTION);
		if(choice == JOptionPane.YES_OPTION)
		{
			game.reset(Markers.O);
		}
		else
		{
			game.reset(Markers.X);
		}
	}

	/**
	 * Called on start of game
	 */
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called on the end of game
	 */
	@Override
	public void onEnd(Markers marker, int rowNum, boolean isTie) {
		// TODO Auto-generated method stub
		System.out.println("Game Ended!");
		int[] pos;
		
		pos = game.getWonRowPos();
		
		for(int i=0;i<btnArray.length;i++)
		{
			btnArray[i].setEnabled(false);
		}
		
		if(pos != null)
		{
			for(int i=0; i<pos.length; i++)
			{
				btnArray[pos[i]].setBackground(Color.GREEN);
			}
		}
		
		if(Markers.X == marker)
		{
			setMessage("X won!!");
		}
		
		if(Markers.O == marker)
		{
			setMessage("O won!!");
		}
		
		if(isTie)
		{
			System.out.println("No body wins it is a tie!");
			setMessage("No body wins it is a tie!");
		}
	}

	/**
	 * Called when the game is paused
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Called on reset
	 */
	@Override
	public void onReset(Markers startMarker) {
		// TODO Auto-generated method stub
		this.setTitle("");
		for(int i=0;i<btnArray.length;i++)
		{
			btnArray[i].setEnabled(this.isChangable);
			btnArray[i].setDisplayState(Markers.EMPTY);
			btnArray[i].setBackground(UIManager.getColor("Button.background"));
			//btnArray[i].validate();
			btnArray[i].repaint();
		}
	}
	
	/**
	 * Display a dialog message
	 * @param msg
	 */
	public void setMessage(String msg)
	{
		this.setTitle(msg);
		System.out.println(msg);
		if(showAlerts)
		{
			JOptionPane.showMessageDialog(this, msg);
		}
	}
	
	/**
	 * set the title of the JFrame
	 */
	public void setTitle(String msg)
	{
		super.setTitle("TicTacToe: "+msg);
	}
	
	/**
	 * Set whether the GUI is changable
	 * @param value
	 */
	public void setChangable(boolean value)
	{
		this.isChangable = value;
		for(int i=0;i<btnArray.length;i++)
		{
			btnArray[i].setEnabled(value);
		}
		
	}
	
	/**
	 * Get if the GUI is changable
	 * @return
	 */
	public boolean getChangable()
	{
		return this.isChangable;
	}
	
	/**
	 * Set if the GUI is resetable
	 * @param value
	 */
	public void setResetable(boolean value)
	{
		this.isResetable=value;
		settingsPanel.setVisible(value);
	}
	
	/**
	 * is the GUI resetable
	 * @return
	 */
	public boolean getResetable()
	{
		return this.isResetable;
	}
	
	/**
	 * Set the if the gui should use a custom cursor
	 * @param useCustomCursor
	 */
	public void setUseCustomCursor(boolean useCustomCursor)
	{
		this.useCustomCursor = useCustomCursor;
		if(this.useCustomCursor)
		{
			setCustomCursor();
		}
		else
		{
			this.setCursor(null);
		}
	}
	
	/**
	 * get if the GUI should use a custom cursor
	 * @return
	 */
	public boolean getUseCustomCursor()
	{
		return useCustomCursor;
	}
	
	/**
	 * Get the Game class
	 * @return
	 */
	public Game getGame()
	{
		return this.game;
	}
	
	/**
	 * Set the GUI is alertable
	 */
	public void setShowAlerts(boolean value)
	{
		showAlerts=value;
	}
	
	/**
	 * Get if the GUI is alertable
	 * @return
	 */
	public boolean getAlerts()
	{
		return showAlerts;
	}

}
