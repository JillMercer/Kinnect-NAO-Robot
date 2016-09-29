package edu.sru.brian.tictactoegame;

/**
 * File: BoardModelTest.java
 * @author Brian Atwell
 * 
 * Description: Provides a test for the BoardModel1DArray.java
 *
 */
public class BoardModelTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BoardModel1DArray board;
		board = new BoardModel1DArray();
		
		for(int i=0; i<9; i++)
		{
			System.out.println("forloop pos:"+i);
			board.getMarker2DPos(i%3, i/3);
		}
		
		for(int y=0; y<3; y++)
		{
			for(int x=0; x<3; x++)
			{
				System.out.println("forloop x:"+x+" y:"+y);
				board.getMarker2DPos(x, y);
			}
		}
	}

}
