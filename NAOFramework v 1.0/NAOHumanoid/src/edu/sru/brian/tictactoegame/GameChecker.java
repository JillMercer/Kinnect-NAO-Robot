package edu.sru.brian.tictactoegame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * File: GameChecker.java
 * @author Brian Atwell
 * Description: Checks the board model for all possible winning rows.
 * Even after the game was won. This is a heuristic for the Min Max Tree.
 *
 */
public class GameChecker extends Game {
	
	private List<WinnerRowID> winningRowIDs;
	
	/**
	 * Default constructor for GameChecker
	 */
	public GameChecker() {
		winningRowIDs=new ArrayList<WinnerRowID>();
	}
	
	/**
	 * Gets the winning rows index in {@link Game#rows} array
	 * @return
	 */
	public List<WinnerRowID> getWinningRowIDs() {
		return winningRowIDs;
	}

	/**
	 * Sets the winning row indexs of {@link Game#rows} array
	 * @param winningRowIDs
	 */
	protected void setWinningRowIDs(List<WinnerRowID> winningRowIDs) {
		this.winningRowIDs = winningRowIDs;
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
		numMarkers[0]=0;
		// 1 = O marker
		numMarkers[1]=0;
		
		if(rowNum < 0 || rowNum > rows.length)
		{
			numMarkers[0]=0;
			numMarkers[1]=0;
			return numMarkers;
		}
		
		for(int i=0;i<rows[rowNum].length; i++)
		{
			if(getBoardModel().getMarker1DPos(rows[rowNum][i]) == Markers.X)
			{
				numMarkers[0]++;
			}
			if(getBoardModel().getMarker1DPos(rows[rowNum][i]) == Markers.O)
			{
				numMarkers[1]++;
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
		
		winningRowIDs.clear();
		
		for(int i=0; i < rows.length; i++)
		{
			numMarkers = checkRow(i);
			
			if(numMarkers[0] == 3)
			{
				System.out.println("Won on row:"+i);
				String temp="";
				for(int j=0;j<rows[i].length;j++)
				{
					temp+=rows[i][j]+",";
				}
				System.out.println("rowData: ["+temp+"]");
				winningRowIDs.add(new WinnerRowID(Markers.X,i));
				if(getWinStatus().isUnfinished())
				{
					setWonRow(i);
					setWinStatus(WinStatus.X);
				}
			}
			else if(numMarkers[1] == 3)
			{
				System.out.println("Won on row:"+i);
				String temp="";
				for(int j=0;j<rows[i].length;j++)
				{
					temp+=rows[i][j]+",";
				}
				System.out.println("rowData: ["+temp+"]");
				winningRowIDs.add(new WinnerRowID(Markers.O,i));
				if(getWinStatus().isUnfinished())
				{
					setWonRow(i);
					setWinStatus(WinStatus.O);
				}
			}
			
		}
		
		if(getBoardModel().getMarkedCount()==9)
		{
			if(getWinStatus().isUnfinished())
			{
				onEnd(Markers.X, -1, true);
			}
			else 
			{
				if(getWinStatus()==WinStatus.X)
					onEnd(Markers.X, getWonRowID(), false);
				else if(getWinStatus()==WinStatus.O)
					onEnd(Markers.O, getWonRowID(), false);
				
				Iterator<WinnerRowID> iter;
				WinnerRowID row;
				
				iter = winningRowIDs.iterator();
				
				while(iter.hasNext())
				{
					row = iter.next();
					System.out.println("Marker: "+row.getMarker()+" Won Row: "+row.getRowID());
				}
			}
		}
		
	}

	/**
	 * Removes Turn from the game/boardModel
	 * @param x
	 * @param y
	 * @return
	 */
	public int removeTurn(int pos)
	{
		
		if(pos < 0 || pos > 8)
		{
			return -3;
		}
		
		if(getBoardModel().getMarker1DPos(pos) == Markers.EMPTY)
		{
			return -5;
		}
		
		getBoardModel().removeMarker1DPos(pos);
		
		Markers oldTurn = getCurTurn();
		
		setAlive(true);
		resetWonRow();
		
		resetWinStatus();
		
		onTurnStart(advanceTurn());
		
		checkBoard();
		
		return 0;
	}

}
