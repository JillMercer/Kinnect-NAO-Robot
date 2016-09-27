package edu.sru.thangiah.nao.system;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

public class NAOVersion
{
	// Version Strings
	// The format NAOV50 is NAO version 5.0
	// The same through NAOV32 is NAO version 3.2
	public static final String NAOV50 = "VERSION_50";
	public static final String NAOV40 = "VERSION_40";
	public static final String NAOV33 = "VERSION_33";
	public static final String NAOV32 = "VERSION_32";
	
	private final static String bodyVersionConfig = "Body Version";
	
	public static String getNAOBodyVersion(Session session)
	{
		ALMotion motion;
		ArrayList<ArrayList<String>> robotConfig;
		
		try {
			motion = new ALMotion(session);
			
			robotConfig = (ArrayList<ArrayList<String>>) motion.getRobotConfig();
			
			for(int i=0; i<robotConfig.get(0).size(); i++)
			{	
				if(robotConfig.get(0).get(i).equals(bodyVersionConfig))
				{
					return robotConfig.get(1).get(i);
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	public static boolean contains(String value)
	{
		Field[] fieldAry;
		String fieldValue=null;
		
		fieldAry = NAOVersion.class.getFields();
		
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