package edu.sru.brian.tictactoegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * File: PlayerAI.java
 * Author: Brian Atwell
 * Date: 2/12/2016
 * Modified Date:
 * 
 * Description: This provides methods for 
 * the AI to interact with the Tic Tac Toe Board.
 * 
 */
public class PlayerAIEnforcer extends PlayerAIAbstract {	
	
	/**
	 * Constructor
	 * @param AIInterface ai
	 * @param Markers myMarker
	 * @param Markers startMarker
	 * @param Game mainGame
	 */
	public PlayerAIEnforcer(AIInterface ai,Markers myMarker, Markers startMarker, Game game)
	{
		this.setMarker(myMarker);
		setAI(ai);
		setGame(game);
		
		game.addTurnListener(new TurnListener(){

			@Override
			public void onOTurnEnd(Markers marker, int pos) {
				// TODO Auto-generated method stub
				if(myMarker == Markers.O)
				{
					System.out.println("OnUserTurn");
					onUserTurn(marker, pos);
				}
				else
				{
					System.out.println("OnOpponentTurn");
					onOpponentTurn(marker, pos);
				}
			}

			@Override
			public void onXTurnEnd(Markers marker, int pos) {
				// TODO Auto-generated method stub
				if(myMarker == Markers.X)
				{
					System.out.println("OnUserTurn");
					onUserTurn(marker, pos);
				}
				else
				{
					System.out.println("OnOpponentTurn");
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
				if(game.getBoardModel().getMarkedCount()==0 && marker.equals(myMarker))
				{

					setNextMove(getAI().calculateNextMove(getGame(), getMarker(), -1));
					takeTurn();
				}
			}
			
		});
		
		game.addGameStatusListener(new GameStatusListener() {

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
				reset(myMarker, startMarker);
			}
			
		});
	}
	
	/**
	 * Opponent Turn event
	 * @param Markers marker
	 * @param int pos
	 */
	public void onOpponentTurn(Markers marker, int pos) 
	{
		this.setNextMove(this.getAI().calculateNextMove(getGame(), getMarker(), pos));
		takeTurn();
	}

	/**
	 * Take this player's turn
	 */
	@Override
	public void takeTurn() {
		// TODO Auto-generated method stub
		int nMove=this.getNextMove();
		
		if(nMove >= 0 && nMove <= 8)
		{
			getGame().takeTurn(nMove);
		}
		
	}
	
}
