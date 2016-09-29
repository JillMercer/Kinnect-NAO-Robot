package edu.sru.brian.tictactoegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * File: Game.java
 * @author Brian Atwell
 * @date 1/31/2016
 * Modified Date:
 * 
 * Description: This provides methods for 
 * interacting with the game.
 * 
 */

public class Game {
	
	public static final int MAX_POSITIONS=9;
	
	private BoardModel1DArray boardModel = new BoardModel1DArray();
	
	private WinStatus winStatus = WinStatus.UNFINISHED;
	
	private boolean isAlive;
	private int wonRowID;
	
	// Set to oMarker or xMarker
	private Markers curTurn = Markers.EMPTY;
	
	private List<TurnListener> turnListeners = new ArrayList<TurnListener>();
	
	private List<GameStatusListener> gameStatusListeners = new ArrayList<GameStatusListener>();
	
	public static final int[][] rows;
	
	private RandomMarker randMarker = new RandomMarker();
	
	/**
	 * Initialize the rows variable
	 */
	
	static {
		
		int rowNum;
		
		rows = new int[8][3];
		
		rowNum=0;
		
		for(int x=0; x<3; x++)
		{
			for(int y=0; y<3; y++)
			{
				rows[rowNum][y]=x+3*y;
			}
			
			rowNum++;
		}
		
		for(int y=0; y < 3; y++)
		{
			for(int x=0; x< 3; x++)
			{
				rows[rowNum][x]=x+3*y;
			}
			
			rowNum++;
		}
		
		int x=0;
		int y=0;
		int count=0;
		
		while(x<3 && y< 3)
		{
			rows[rowNum][count]=x+(3*y);
			
			count++;
			y++;
			x++;
		}
		rowNum++;
		
		count=0;		
		x=0;
		y=2;
		
		while(y >= 0 && x<3)
		{
			rows[rowNum][count]=x+(3*y);
			
			count++;
			y--;
			x++;
		}
		
	}
	
	// Creates a new game object
	public Game() {
		isAlive=true;
		wonRowID=-1;
		advanceTurn();
	}
	
	/**
	 * Starts the game.
	 */
	public void start() {
		if(curTurn ==Markers.EMPTY)
		{
			onTurnStart(advanceTurn());
		}
		else
		{
			onTurnStart(curTurn);
		}
		
		onStart();
	}
	
	/**
	 * Resets the game
	 */
	public void reset() {
		boardModel.reset();
		
		curTurn=Markers.EMPTY;
		
		curTurn = randMarker.nextMarker();
		isAlive=true;
		wonRowID = -1;
		
		winStatus = WinStatus.UNFINISHED;
		
		onReset();
	}
	
	
	/**
	 * Resets the game
	 */
	public void reset(Markers newMarker) {
		boardModel.reset();
		
		curTurn=newMarker;
		isAlive=true;
		winStatus = WinStatus.UNFINISHED;
		
		onReset();
	}
	
	/**
	 * Pause the game
	 */
	public void pause() {
		onPause();
	}
	
	/**
	 * Simulates taking a turn and assumes the player knows their turn
	 * and appropriate marker.
	 * 
	 * @param marker
	 * @param x
	 * @param y
	 * @return
	 */
	public int takeTurn(Markers marker, int pos)
	{
		// Check if arguments are valid
		if(!isAlive)
		{
			return -6;
		}
		
		if(Markers.O != marker && Markers.X != marker)
		{
			return -2;
		}
		
		if(curTurn!=marker)
		{
			return -1;
		}
		
		if(pos < 0 || pos > 8)
		{
			return -3;
		}
		
		
		if(boardModel.getMarker1DPos(pos) != Markers.EMPTY)
		{
			return -5;
		}
		
		boardModel.setMarker1DPos(marker, pos);
		
		if(Markers.X == marker)
		{
			onXTurnEnd(marker, pos);
		}
		
		if(Markers.O == marker)
		{
			onOTurnEnd(marker, pos);
		}
		
		checkBoard();
		
		onTurnStart(advanceTurn());
		
		
		return 0;
	}
	
	/**
	 * Simulates taking a turn
	 * @param x
	 * @param y
	 * @return
	 */
	public int takeTurn(int pos)
	{
		if(!isAlive)
		{
			return -6;
		}
		
		if(pos < 0 || pos > 8)
		{
			return -3;
		}
		
		if(boardModel.getMarker1DPos(pos) != Markers.EMPTY)
		{
			return -5;
		}
		
		boardModel.setMarker1DPos(curTurn, pos);
		
		Markers oldTurn = curTurn;
		
		checkBoard();
		
		onTurnStart(advanceTurn());
		
		if(Markers.X == oldTurn)
		{
			onXTurnEnd(oldTurn, pos);
		}
		
		if(Markers.O == oldTurn)
		{
			onOTurnEnd(oldTurn, pos);
		}		
		
		return 0;
	}
	
	/**
	 * Advances the turn incrementing the
	 * current turn variable marker.
	 * @return
	 */
	protected Markers advanceTurn() {
		Markers next = Markers.EMPTY;
		
		if(!isAlive)
		{
			return Markers.EMPTY;
		}
		
		if(curTurn == Markers.X)
		{
			next = Markers.O;
		}
		else if(curTurn == Markers.O)
		{
			next = Markers.X;
		}
		else
		{
			next = Markers.O;
		}
		
		
		curTurn = next;
		
		return next;
	}
	
	/**
	 * Add listeners for player turns
	 * @param TurnListener listener
	 */
	public void addTurnListener(TurnListener listener)
	{
		turnListeners.add(listener);
	}
	
	/**
	 * Removes listeners for player turns
	 * @param TurnListener listener
	 */
	public void removeTurnListener(TurnListener listener)
	{
		turnListeners.remove(listener);
	}
	
	/**
	 * Add GameStatusListener. GameStatusListener provides callbacks
	 * for start, end, pause, and reset
	 * @param listener
	 */
	public void addGameStatusListener(GameStatusListener listener)
	{
		gameStatusListeners.add(listener);
	}
	
	/**
	 * Removes GameStatusListener. GameStatusListener provides callbacks
	 * for start, end, pause, and reset
	 * @param listener
	 */
	public void removeGameStatusListener(GameStatusListener listener)
	{
		gameStatusListeners.remove(listener);
	}
	
	/**
	 * onTurnStart is called when the turn starts by the Game class.
	 * @param Markers marker
	 */
	protected void onTurnStart(Markers marker)
	{
		Iterator<TurnListener> iter;
		TurnListener listener;
		
		iter = turnListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onTurnStart(marker);
		}
	}
	
	/**
	 * onTurnEnd is called when the turn ends by the Game class.
	 * pos is 1 dimensional @see {@link BoardModel1DArray#getMarker1DPos(int)}
	 * @param Markers marker
	 * @param int pos 
	 */
	protected void onTurnEnd(Markers marker, int pos)
	{
		Iterator<TurnListener> iter;
		TurnListener listener;
		
		iter = turnListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onTurnEnd(marker, pos);
		}
	}
	
	/**
	 * onTurnEnd is called when X turn ends by the Game class.
	 * pos is 1 dimensional @see {@link BoardModel1DArray#getMarker1DPos(int)}
	 * @param Markers marker
	 * @param int pos 
	 */
	protected void onXTurnEnd(Markers marker, int pos)
	{
		Iterator<TurnListener> iter;
		TurnListener listener;
		
		iter = turnListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onXTurnEnd(marker, pos);
		}
		onTurnEnd(marker, pos);
	}
	
	/**
	 * onTurnEnd is called when O turn ends by the Game class.
	 * pos is 1 dimensional @see {@link BoardModel1DArray#getMarker1DPos(int)}
	 * @param Markers marker
	 * @param int pos 
	 */
	protected void onOTurnEnd(Markers marker, int pos)
	{
		Iterator<TurnListener> iter;
		TurnListener listener;
		
		iter = turnListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onOTurnEnd(marker, pos);
		}
		onTurnEnd(marker, pos);
	}
	
	/**
	 * onStart is called when the game starts.
	 * It is called by the Game class.
	 */
	protected void onStart()
	{
		Iterator<GameStatusListener> iter;
		GameStatusListener listener;
		
		iter = gameStatusListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onStart();
		}
	}
	
	/**
	 * onReset is called when the game is reset.
	 * It is called by the Game class.
	 */
	protected void onReset()
	{
		Iterator<GameStatusListener> iter;
		GameStatusListener listener;
		
		iter = gameStatusListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			//listener.onReset(this., startMarker);
			listener.onReset(curTurn);
		}
	}
	
	/**
	 * onReset is called when the game is reset.
	 * It is called by the Game class.
	 * 
	 * @param Markers startMarker
	 */
	protected void onReset(Markers startMarker)
	{
		Iterator<GameStatusListener> iter;
		GameStatusListener listener;
		
		iter = gameStatusListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onReset(startMarker);
		}
	}
	
	/**
	 * Called when the game is paused.
	 * It is called by the Game class.
	 */
	protected void onPause()
	{
		Iterator<GameStatusListener> iter;
		GameStatusListener listener;
		
		iter = gameStatusListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onPause();
		}
	}
	
	/**
	 * Called when the game has ended
	 * @param Markers marker
	 * @param int rowNum {@link Game#rows}
	 * @param boolean isTie
	 */
	protected void onEnd(Markers marker, int rowNum, boolean isTie)
	{
		isAlive = false;
		
		if(marker == Markers.X)
		{
			System.out.println("X at row:"+rowNum);
		}
		else if(marker == Markers.O)
		{
			System.out.println("O at row:"+rowNum);
		}
		
		Iterator<GameStatusListener> iter;
		GameStatusListener listener;
		
		iter = gameStatusListeners.iterator();
		
		while(iter.hasNext())
		{
			listener = iter.next();
			listener.onEnd(marker, rowNum, isTie);
		}
	}
	
	/**
	 * Get the winning row index number of {@link Game#rows}
	 * @return
	 */
	public final int getWonRowID()
	{
		return this.wonRowID;
	}
	
	/**
	 * Get index of all rows that are winning rows
	 * @return int[] array
	 */
	public final int[] getWonRowPos()
	{
		if(this.wonRowID > -1)
		{
			return rows[this.wonRowID];
		}
		return null;
	}
	
	/**
	 * Get row array at index rowID
	 * @param int rowID
	 * @return int[] array
	 */
	public final int[] getRowPos(int rowID)
	{
		return rows[rowID];
	}
	
	/**
	 * Get the rows {@link Game#rows}
	 * @return int[][]
	 */
	public final int[][] get1DRowPos()
	{
		return rows;
	}
	
	/**
	 * Check row at index rowNum.
	 * Returns int[Markers.X.getInt()] number of X markers found.
	 * int[Markers.O.getInt()] number of O markers found.
	 * @param int rowNum
	 * @return int[] 
	 */
	public int[] checkRow(int rowNum )
	{
		int numMarkers[];
		numMarkers = new int[2];
		
		// 0 = X marker
		numMarkers[Markers.X.getInt()]=0;
		// 1 = O marker
		numMarkers[Markers.O.getInt()]=0;
		
		if(rowNum < 0 || rowNum > rows.length)
		{
			numMarkers[Markers.X.getInt()]=0;
			numMarkers[Markers.O.getInt()]=0;
			return numMarkers;
		}
		
		for(int i=0;i<rows[rowNum].length; i++)
		{
			if(boardModel.getMarker1DPos(rows[rowNum][i]) == Markers.X)
			{
				numMarkers[Markers.X.getInt()]++;
			}
			if(boardModel.getMarker1DPos(rows[rowNum][i]) == Markers.O)
			{
				numMarkers[Markers.O.getInt()]++;
			}
			
		}
		
		
		return numMarkers;
	}
	
	/**
	 * Check the board for winning rows.
	 */
	protected void checkBoard()
	{
		boolean isTie;
		int numMarkers[];
		
		isTie =true;
		
		for(int i=0; i < rows.length; i++)
		{
			numMarkers = checkRow(i);
			
			if(numMarkers[Markers.X.getInt()] == 3)
			{
				System.out.println("Won on row:"+i);
				String temp="";
				for(int j=0;j<rows[i].length;j++)
				{
					temp+=rows[i][j]+",";
				}
				System.out.println("rowData: ["+temp+"]");
				wonRowID = i;
				winStatus = WinStatus.X;
				onEnd(Markers.X, i, false);
				return;
			}
			else if(numMarkers[Markers.O.getInt()] == 3)
			{
				System.out.println("Won on row:"+i);
				String temp="";
				for(int j=0;j<rows[i].length;j++)
				{
					temp+=rows[i][j]+",";
				}
				System.out.println("rowData: ["+temp+"]");
				wonRowID = i;
				winStatus = WinStatus.O;
				onEnd(Markers.O, i, false);
				return;
			}
			else if(!(numMarkers[0] >= 1 && numMarkers[1] >= 1))
			{
				wonRowID=-1;
				isTie=false;
			}
			
		}
		
		if(isTie)
		{
			winStatus = WinStatus.TIE;
			onEnd(Markers.EMPTY, -1, true);
		}
		
	}
	
	/**
	 * Gets if the game is still not won
	 * @return isAlive
	 */
	public boolean isAlive()
	{
		return this.isAlive;
	}
	
	/**
	 * Sets if the game is won.
	 * @param alive
	 */
	protected void setAlive(boolean alive)
	{
		this.isAlive = alive;
	}
	
	/**
	 * get the current turn's Marker.
	 * X or O turn.
	 * @return Markers
	 */
	public Markers getCurTurn() 
	{
		return this.curTurn;
	}
	
	/**
	 * Set the current Marker turn.
	 * X or O turn.
	 * @param newTurn
	 */
	protected void setCurTurn(Markers newTurn)
	{
		this.curTurn = newTurn;
	}
	
	/**
	 * Get the boardModel.
	 * @return BoardModel1DArray
	 */
	public BoardModel1DArray getBoardModel()
	{
		return this.boardModel;
	}
	
	/**
	 * Sets the boardModel.
	 * @param BoardModel1DArray newBoardModel
	 */
	public void setBoardModel(BoardModel1DArray newBoardModel)
	{
		this.boardModel = newBoardModel;
	}
	
	/**
	 * Gets the WinStatus 
	 * @return
	 */
	public WinStatus getWinStatus()
	{
		return winStatus;
	}
	
	/**
	 * Sets the WinStatus
	 * @param status
	 */
	protected void setWinStatus(WinStatus status)
	{
		this.winStatus =status;
	}
	
	/**
	 * Resets the WinStatus
	 */
	protected void resetWinStatus()
	{
		winStatus=WinStatus.UNFINISHED;
	}
	
	/**
	 * Sets the won row. pos is the index of rows.
	 * {@link Game#rows}
	 * @param pos
	 */
	protected void setWonRow(int pos)
	{
		wonRowID=pos;
	}
	
	/**
	 * Resets the won row.
	 */
	protected void resetWonRow()
	{
		wonRowID=-1;
	}

}
