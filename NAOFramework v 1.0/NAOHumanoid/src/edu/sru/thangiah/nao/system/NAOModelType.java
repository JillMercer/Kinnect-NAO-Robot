package edu.sru.thangiah.nao.system;

import java.lang.reflect.Field;

public class NAOModelType
{	
	// Construction Strings
	public static final String NAOH25 = "naoH25";
	public static final String NAOH21 = "naoH21";
	public static final String NAOT14 = "naoT14";
	public static final String NAOT2 = "naoT2";
	
	
	public static boolean contains(String value)
	{
		Field[] fieldAry;
		String fieldValue=null;
		
		fieldAry = NAOModelType.class.getFields();
		
		if(fieldAry != null)
		{
			for(int i=0; i<fieldAry.length; i++)
			{
				fieldValue=null;
				try {
					fieldValue=(String)fieldAry[i].get(null);
					//System.out.println("Field Name:"+fieldAry[i].getName()+" Value: "+fieldValue);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(fieldAry[i].getType() == String.class)
				{
					if(fieldValue.equals(value))
					{
						return true;
					}
				}
			}
			
		}
		
		
		return false;
	}
}