package edu.sru.brian.tictactoegame.decisiontree;

/** 
 * File: NodeStatus.java
 * @author Brian Atwell
 * Description: Nodes status determines if the 
 * node is a winning node.
 *
 */
public enum NodeStatus {

	UNDETERMINED, WINORTIE, WIN, TIE, LOSE;
	
	public static final String UNDETERMINED_STR = "Undetermined";
	public static final String WINORTIE_STR = "Win or Tie";
	public static final String WIN_STR = "Win";
	public static final String TIE_STR = "Tie";
	public static final String LOSE_STR = "Lose";
	
	public static final char UNDETERMINED_CHAR = 'U';
	public static final char WINORTIE_CHAR = 'w';
	public static final char WIN_CHAR = 'W';
	public static final char TIE_CHAR = 'T';
	public static final char LOSE_CHAR = 'L';
	
	/**
	 * Converts enum to string
	 */
	public String toString()
	{
		String s = new String();
		
		if(this == NodeStatus.UNDETERMINED)
		{
			s =UNDETERMINED_STR;
		}
		
		if(this == NodeStatus.WINORTIE)
		{
			s=WINORTIE_STR;
		}
		
		if(this == NodeStatus.WIN)
		{
			s=WIN_STR;
		}
		
		if(this == NodeStatus.TIE)
		{
			s=TIE_STR;
		}
		
		if(this == NodeStatus.LOSE)
		{
			s=LOSE_STR;
		}
		
		return s.toString();
	}
	
	/**
	 * Converts the NodeStatus to character
	 * @return
	 */
	public char toChar()
	{
		char s = ' ';
		
		if(this == NodeStatus.UNDETERMINED)
		{
			s=UNDETERMINED_CHAR;
		}
		
		if(this == NodeStatus.WINORTIE)
		{
			s=WINORTIE_CHAR;
		}
		
		if(this == NodeStatus.WIN)
		{
			s=WIN_CHAR;
		}
		
		if(this == NodeStatus.TIE)
		{
			s=TIE_CHAR;
		}
		
		if(this == NodeStatus.LOSE)
		{
			s=LOSE_CHAR;
		}
		
		return s;
	}
	
	/**
	 * convert string to NodeStatus
	 * @param str
	 * @return
	 */
	public static NodeStatus nodeStatusFromString(String str)
	{
		NodeStatus temp = NodeStatus.UNDETERMINED;
		
		switch(str)
		{
		case UNDETERMINED_STR:
			temp = NodeStatus.UNDETERMINED;
			break;
		case WINORTIE_STR:
			temp = NodeStatus.WINORTIE;
			break;
		case WIN_STR:
			temp = NodeStatus.WIN;
			break;
		case TIE_STR:
			temp = NodeStatus.TIE;
			break;
		case LOSE_STR:
			temp = NodeStatus.LOSE;
			break;
		}
		
		return temp;
	}
	
	/**
	 * Convert character to NodeStatus
	 * @param str
	 * @return
	 */
	public static NodeStatus nodeStatusFromChar(char str)
	{
		NodeStatus temp = NodeStatus.UNDETERMINED;
		
		switch(str)
		{
		case UNDETERMINED_CHAR:
			temp = NodeStatus.UNDETERMINED;
			break;
		case WINORTIE_CHAR:
			temp = NodeStatus.WINORTIE;
			break;
		case WIN_CHAR:
			temp = NodeStatus.WIN;
			break;
		case TIE_CHAR:
			temp = NodeStatus.TIE;
			break;
		case LOSE_CHAR:
			temp = NodeStatus.LOSE;
			break;
		}
		
		return temp;
	}
	
}
