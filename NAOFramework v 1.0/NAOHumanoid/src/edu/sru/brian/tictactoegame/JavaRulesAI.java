package edu.sru.brian.tictactoegame;

/**
 * File: JavaRulesAI.java
 * @author Brian Atwell
 * Description: This is a Java partial implementation
 * of {@link AIInterface}. This was mainly as a test ai and
 * not made for actual use.
 *
 */
public class JavaRulesAI implements AIInterface {

	/**
	 * Called when it is the AI players turn. Pass in the Game of Tic Tac Toe,
	 * The Opponent's marker and the opponent's move as a 1 Dimensional position
	 * of the board.
	 * @param game
	 * @param oppMark
	 * @param oppMove
	 * @return
	 */
	@Override
	public int calculateNextMove(Game game, Markers marker, int oppMove) {
		Markers opponent;
		Markers myMarker;
		int nextMove =0;
		myMarker = marker;
		opponent = marker.getOpponent();
		int[] numMarkers;
		int[][] rows;
		BoardModel1DArray boardModel;
		
		boardModel = game.getBoardModel();
		
		rows = game.get1DRowPos();
		
		if(boardModel.getMarkedCount() == 1)
		{
			System.out.println("AI Thought: at least one mark");
			if(boardModel.getMarker1DPos(0) == opponent)
			{
				nextMove = 8;
				System.out.println("AI Thought:"+nextMove);
				return nextMove;
			}
			else if(boardModel.getMarker1DPos(2) == opponent)
			{
				nextMove = 6;
				System.out.println("AI Thought:"+nextMove);
				return nextMove;
			}
			else if(boardModel.getMarker1DPos(6) == opponent)
			{
				nextMove = 2;
				System.out.println("AI Thought:"+nextMove);
				return nextMove;
			}
			else if(boardModel.getMarker1DPos(8) == opponent)
			{
				nextMove = 0;
				System.out.println("AI Thought:"+nextMove);
				return nextMove;
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
							return nextMove;
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
							return nextMove;
						}
					}
				}
			}
		}
		
		System.out.println("Made it to O Turn End!");
		
		nextMove = -1;
		
		return nextMove;
	}
	
	/**
	 * Called when the game class is reset.
	 */
	public void reset() {}

}
