package edu.sru.thangiah.random;

import java.util.Calendar;
import java.util.Random;

/**
 * 
 * @editor Brian Atwell
 * Added: genAlphaNumericString, genAlphaString, char pRand
 * Date: July 06, 2015
 * 
 * pRand is from
 * Written by: Dr. Sam R. Thangiah.
 * Copyright by: Dr. Sam R. Thangiah, 2000.
 * Program: ArrayNewRand.java.
 * Date: January 20, 2002
 *
 * Generate random data. Generator random number or generator random strings
 */

public class RandomGenerator {
	
	private static Random rand;
	
	static {
		Calendar cal;
		cal =Calendar.getInstance();
		
		rand = new Random(cal.getTime().getTime());
	}
	
	public enum CASEENUM { LOWER, UPPER, BOTH };
	
	/**
	 * Generates a random number from 1 to @param mod
	 * @return number
	 */
	public static int pRand(int mod) {
	    return Math.abs(rand.nextInt()) % mod + 1;
	  }
	
	/**
	 * Generates a random number from 1 to @param mod
	 * @return number
	 */
	public static int pRand(int mod, int start) {
	    return Math.abs(rand.nextInt()) % mod + 1+start;
	  }
	
	/**
	 * Generates a random character from @param offset to @param offset
	 * @return number
	 */
	public static char pRand(char mod, char offset) {
	    return (char) (Math.abs(rand.nextInt()) % mod + offset);
	  }
	
	/**
	 * Generate random string from @param startChar to @param endChar
	 * with a length of @param length
	 * @return random string
	 */
	public static String genString(char startChar, char endChar, int length) {
		StringBuffer sb;
		char modSize;
		
		sb = new StringBuffer();
		modSize = (char) (endChar - startChar);
		System.out.println("modSize: "+(int)modSize);
		
		for(int i=0; i < length; i++) {
			sb.append(pRand(modSize, startChar));
		}
		
		return sb.toString();
	}
	
	/**
	 * Generate a random alpha numeric (numbers and letters) string with a case
	 * of @param caseVal (UPPER, LOWER, BOTH) with a
	 * length of @param length.
	 * @return random string
	 */
	public static String genAlphaNumericString(CASEENUM caseVal, int length) {
		StringBuffer sb;
		int number;
		
		
		sb = new StringBuffer();
		
		switch(caseVal) {
		case UPPER:
			for(int i=0; i < length; i++) {
				number = pRand(36);
				//sb.append(pRand(modSize, startChar));
				if(number <= 10) {
					number+=47;
					sb.append((char)number);					
				}
				else if(number <= 36) {
					number+=54;
					sb.append((char)number);
				}
			}
			break;
		case LOWER:
			for(int i=0; i < length; i++) {
				number = pRand(36);
				//sb.append(pRand(modSize, startChar));
				if(number <= 10) {
					number+=47;
					sb.append((char)number);					
				}
				else if(number <= 36) {
					number+=86;
					sb.append((char)number);
				}
			}
			break;
		case BOTH:
			for(int i=0; i < length; i++) {
				number = pRand(62);
				//sb.append(pRand(modSize, startChar));
				if(number <= 10) {
					number+=47;
					sb.append((char)number);					
				}
				else if(number <= 36) {
					number+=54;
					sb.append((char)number);
				}
				else if(number <= 62) {
					number+=60;
					sb.append((char)number);
				}
			}
			break;
		}
		
		return sb.toString();
	}
	
	/**
	 * Generate a random letters string with a case
	 * of @param caseVal (UPPER, LOWER, BOTH) with a
	 * length of @param length.
	 * @return random string
	 */
	public static String genAlphaString(CASEENUM caseVal, int length) {
		StringBuffer sb;
		int number;
		
		
		sb = new StringBuffer();
		
		switch(caseVal) {
		case UPPER:
			for(int i=0; i < length; i++) {
				number = pRand(26);
				//sb.append(pRand(modSize, startChar));
				if(number <= 26) {
					number+=64;
					sb.append((char)number);					
				}
			}
			break;
		case LOWER:
			for(int i=0; i < length; i++) {
				number = pRand(26);
				//sb.append(pRand(modSize, startChar));
				if(number <= 26) {
					number+=96;
					sb.append((char)number);					
				}
			}
			break;
		case BOTH:
			for(int i=0; i < length; i++) {
				number = pRand(52);
				//sb.append(pRand(modSize, startChar));
				if(number <= 26) {
					number+=64;
					sb.append((char)number);					
				}
				else if(number <= 52) {
					number+=70;
					sb.append((char)number);
				}
			}
			break;
		}
		
		return sb.toString();
	}
	
	static void showChar(char startChar, char endChar) {
		char countChar;
		StringBuffer sb;
		
		sb = new StringBuffer();		
		countChar = startChar;
		
		while(countChar < endChar) {
			sb.append(countChar);
			countChar++;
		}
		
		System.out.println(sb.toString());
		
	}
	
	public static void main(String[] args) {
		//System.out.println(""+RandomGenerator.genString('0', 'z', 5));
		/*
		System.out.println((35%36)+1);
		
		showChar('0', 'z');
		System.out.println("0 intValue: "+(int)'0');
		System.out.println("A intValue: "+(int)'A');
		System.out.println("a intValue: "+(int)'a');
		
		System.out.println(genAlphaNumericString(CASEENUM.UPPER, 10));
		*/
		
		System.out.println(genAlphaString(CASEENUM.LOWER, 100));
		
		/*
		StringBuffer sb;
		int number;
		
		sb = new StringBuffer();
		
		
		for(int i=1; i <= 52; i++) {
			number = i;
			sb.append(""+number+":");
			if(number <= 26) {
				number+=64;
				sb.append((char)number);					
			}
			else if(number <= 52) {
				number+=70;
				sb.append((char)number);
			}
			sb.append(" ");
		}
		
		System.out.println(sb.toString());
		*/
	}

}
