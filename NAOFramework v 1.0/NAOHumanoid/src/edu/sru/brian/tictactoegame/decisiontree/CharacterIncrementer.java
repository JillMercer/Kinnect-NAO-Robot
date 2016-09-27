package edu.sru.brian.tictactoegame.decisiontree;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * 
 * File: CharacterIncrementer.java
 * @author Brian Atwell
 * Date: 3/2/2016
 * Description: Allows defining names to trees as a character array.
 * Below is a visual description.
 *        A
 *    AA     AB
 * AAA AAB ABA ABC
 * This was abandon because it was not necessary.
 * @deprecated
 *
 */
public class CharacterIncrementer
{
	protected ArrayList<Integer> charList;
	
	public CharacterIncrementer()
	{
		charList = new ArrayList<Integer>();
		charList.add((int)'A');
	}
	
	public void add()
	{
		Integer top=charList.get(charList.size()-1);
		
		if((top+1) <= 'Z')
		{
			charList.remove(charList.size()-1);
			charList.add((top+1));
		}
		else
		{
			charList.add((int)'A');
		}
	}
	
	public void add(int num)
	{
		for(int i=0;i<num;i++)
		{
			Integer top=charList.get(charList.size()-1);
			
			if((top+1) <= 'Z')
			{
				charList.remove(charList.size()-1);
				charList.add((top+1));
			}
			else
			{
				charList.add((int)'A');
			}
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer strBuff;
		strBuff = new StringBuffer();
		
		Iterator<Integer> iter = charList.listIterator();
		
		while(iter.hasNext())
		{
			strBuff.append((char)iter.next().intValue());
		}
		
		
		return strBuff.toString();
	}
	
	public static void main(String[] args) {
		CharacterIncrementer charInc = new CharacterIncrementer();
		
		charInc.add(1000);
		
		System.out.println(charInc);
	}
	
	
}