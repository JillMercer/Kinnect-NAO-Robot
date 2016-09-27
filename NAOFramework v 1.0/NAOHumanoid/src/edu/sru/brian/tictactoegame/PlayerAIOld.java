package edu.sru.brian.tictactoegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * File: PlayerAIOld.java
 * Author: Brian Atwell
 * Date: 2/12/2016
 * Modified Date:
 * 
 * Description: This provides methods for 
 * the AI to interact with the Tic Tac Toe Board.
 * 
 * An old implementation of PlayerAI.
 * 
 * @deprecated
 * 
 */
public class PlayerAIOld {	
	
	private int nextMove =0;
	private int opponentMove=-1;
	
	private Markers myMarker;
	
	private Game game;
	private Game mainGame;
	
	private List<GameStatusListener> gameStatusListeners = new ArrayList<GameStatusListener>();
	
	private List<TurnListener> turnListeners = new ArrayList<TurnListener>();
	
	public PlayerAIOld(Markers myMarker, Markers startMarker, Game mainGame)
	{
		this.myMarker = myMarker;
		this.game = new Game();
		this.mainGame = mainGame;
		
		mainGame.addTurnListener(new TurnListener(){

			@Override
			public void onOTurnEnd(Markers marker, int pos) {
				// TODO Auto-generated method stub
				if(myMarker == Markers.O)
				{
					onUserTurn(marker, pos);
				}
				else
				{
					onOpponentTurn(marker, pos);
				}
				
			}

			@Override
			public void onXTurnEnd(Markers marker, int pos) {
				// TODO Auto-generated method stub
				if(myMarker == Markers.X)
				{
					onUserTurn(marker, pos);
				}
				else
				{
					onOpponentTurn(marker, pos);
				}
			}

			@Override
			public void onTurnEnd(Markers marker, int pos) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTurnStart(Markers marker) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		mainGame.addGameStatusListener(new GameStatusListener() {

			@Override
			public void onStart() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onEnd(Markers marker, int rowNum, boolean isTie) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onPause() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onReset(Markers startMarker) {
				// TODO Auto-generated method stub
				
			}

			
		});
	}
	
	/**
	 * Starts the game.
	 */
	public void start() {
	}
	
	/**
	 * Resets the game
	 */
	public void reset(Markers myMarker, Markers startMarker) {
		game.reset();
		this.myMarker = myMarker;
		opponentMove=-1;
	}
	
	/**
	 * User Turn event
	 * @param Markers marker
	 * @param int pos
	 */
	public int onUserTurn(Markers marker, int pos) 
	{
		
		if(Markers.O != marker && Markers.X != marker)
		{
			return -2;
		}
		
		System.out.println("Made it to X Turn End!");
		
		game.takeTurn(marker, pos);
		
		return 0;
	}
	
	/**
	 * Take this player's turn.
	 * @param marker
	 * @param pos
	 */
	protected void takeTurn(Markers marker, int pos)
	{
		
	}
	
	/**
	 * Opponent Turn event
	 * @param Markers marker
	 * @param int pos
	 */
	public int onOpponentTurn(Markers marker, int pos) 
	{
		int result = game.takeTurn(marker, pos);
		int[] numMarkers;
		int[][] rows;
		BoardModel1DArray boardModel;
		Markers opponent;
		
		opponent=Markers.X;
		
		if(myMarker == Markers.X)
		{
			opponent = Markers.O;
		}
		else if(myMarker == Markers.O)
		{
			opponent = Markers.X;
		}
		
		boardModel = game.getBoardModel();
		
		result = game.takeTurn(marker, pos);
		
		rows = game.get1DRowPos();
		
		if(boardModel.getMarkedCount() == 1)
		{
			if(boardModel.getMarker1DPos(0) == opponent)
			{
				nextMove = 8;
				System.out.println("AI Thought:"+nextMove);
				mainGame.takeTurn(nextMove);
				return result;
			}
			else if(boardModel.getMarker1DPos(2) == opponent)
			{
				nextMove = 6;
				System.out.println("AI Thought:"+nextMove);
				mainGame.takeTurn(nextMove);
				return result;
			}
			else if(boardModel.getMarker1DPos(6) == opponent)
			{
				nextMove = 2;
				System.out.println("AI Thought:"+nextMove);
				mainGame.takeTurn(nextMove);
				return result;
			}
			else if(boardModel.getMarker1DPos(8) == opponent)
			{
				nextMove = 0;
				System.out.println("AI Thought:"+nextMove);
				mainGame.takeTurn(nextMove);
				return result;
			}
		}
		
		for(int i=0; i<rows.length; i++)
		{
			numMarkers = game.checkRow(i);
			
			System.out.println("row["+i+"] numMarkers[0]:"+numMarkers[0]+" numMarkers[1]:"+numMarkers[1]);
			
			if(myMarker == Markers.X)
			{
				if(numMarkers[1] == 2)
				{
					System.out.println("AI Thought: Counter Strike");
					for(int j=0; j<rows[i].length; j++)
					{
						if(boardModel.getMarker1DPos(rows[i][j]) == Markers.EMPTY)
						{
							nextMove = rows[i][j];
							System.out.println("AI Thought:"+nextMove);
							mainGame.takeTurn(nextMove);
							return result;
						}
					}
				}
			}
			else if(myMarker == Markers.O)
			{
				if(numMarkers[0] == 2)
				{
					System.out.println("AI Thought: Counter Strike");
					for(int j=0; j<rows[i].length; j++)
					{
						if(boardModel.getMarker1DPos(rows[i][j]) == Markers.EMPTY)
						{
							nextMove = rows[i][j];
							System.out.println("AI Thought:"+nextMove);
							mainGame.takeTurn(nextMove);
							return result;
						}
					}
				}
			}
		}
		
		System.out.println("Made it to O Turn End!");
		
		nextMove = -1;
		
		return result;
	}
	
	/**
	 * on turn end event
	 * @param marker
	 * @param pos
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
	 * on game ended event
	 * @param marker
	 * @param rowNum
	 * @param isTie
	 */
	protected void onEnd(Markers marker, int rowNum, boolean isTie)
	{
		
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
	 * reset the ai.
	 */
	protected void reset()
	{
		game.reset(mainGame.getCurTurn());
	}
}
