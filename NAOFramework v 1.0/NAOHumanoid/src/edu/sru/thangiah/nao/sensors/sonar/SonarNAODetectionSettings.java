package edu.sru.thangiah.nao.sensors.sonar;

import java.util.ArrayList;
import java.util.List;

import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.system.NAOModelType;
import edu.sru.thangiah.nao.system.NAOVersion;

/**
 * Provides a common interface for Sonar Detection Settings
 * Such as: 
 * 			* minimum object detection distance
 * 			* Whether each location is blocked
 * 			* distance of the object at each location
 * 
 * @author Brian Atwell
 *
 */
public class SonarNAODetectionSettings implements SonarDetectionSettings {
	private ALMotion motion;
	private final String sonarVersion;
	private final static String modelTypeConfig = "Model Type";
	private final static String bodyVersionConfig = "Body Version";
	
	public SonarNAODetectionSettings(Session session) {
		//List<> robotconfig;
		ALMotion motion;
		ArrayList<ArrayList<String>> robotConfig;
		boolean isModelTypeValid=false;
		boolean isBodyVersionValid=false;
		String modelTypeStr=null;
		String bodyVersionStr=null;
		String tempSonarVersion=null;
		
		try {
			motion = new ALMotion(session);
			
			//System.out.println(motion.getRobotConfig());
			
			robotConfig = (ArrayList<ArrayList<String>>) motion.getRobotConfig();
			
			for(int i=0; i<robotConfig.get(0).size(); i++)
			{
				if(robotConfig.get(0).get(i).equals(modelTypeConfig))
				{
					modelTypeStr = robotConfig.get(1).get(i);
					//modelTypeField = NAOModelType.class.getField(modelTypeStr);
					isModelTypeValid = NAOModelType.contains(modelTypeStr);
					
					// Check if body version has been set
					if(isBodyVersionValid)
					{
						// Body version and model type has been set
						// End loop
						break;
					}
				}
				
				if(robotConfig.get(0).get(i).equals(bodyVersionConfig))
				{
					bodyVersionStr = robotConfig.get(1).get(i);
					
					isBodyVersionValid = NAOVersion.contains(bodyVersionStr);
					
					// Check if model type has been set
					if(isModelTypeValid)
					{
						// model type and body version has been set
						// End loop
						break;
					}
				}
			}
			
			if(isModelTypeValid && isBodyVersionValid)
			{
				tempSonarVersion = bodyVersionStr;
				
				//System.out.println("Body Version: "+bodyVersionStr);
				//System.out.println("Model Type: "+modelTypeStr);
			}
			else
			{
				if(modelTypeStr.equals(modelTypeConfig))
				{
					System.out.println("Error Unknown NAO Model Type: "+modelTypeStr);
				}
				else
				{
					System.out.println("Error:\""+modelTypeConfig+"\" Not Found!");
				}
				
				if(bodyVersionStr.equals(bodyVersionConfig))
				{
					System.out.println("Error Unknown NAO Model Type: "+bodyVersionStr);
				}
				else
				{
					System.out.println("Error:\""+bodyVersionConfig+"\" Not Found!");
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		sonarVersion =tempSonarVersion;
	}
	
	/**
	 * Gets the minimium distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	public float getMinimumDistance(int loc) {
		if(sonarVersion == null || !SonarNAOLocation.LEFT.contains(loc))
		{
			return -1;
		}
		
		switch(sonarVersion)
		{
		case NAOVersion.NAOV50:
			return SonarNAODetectionConstants.NAOV5MIN;
		case NAOVersion.NAOV40:
		case NAOVersion.NAOV33:
		case NAOVersion.NAOV32:
			return SonarNAODetectionConstants.NAOV4MIN;
		default:
			return -1;
		}
		
	}
	
	/**
	 * Gets the maximum distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	public float getMaximumDistance(int loc) {
		if(sonarVersion == null || !SonarNAOLocation.LEFT.contains(loc))
		{
			return -1;
		}
		
		switch(sonarVersion)
		{
		case NAOVersion.NAOV50:
			return SonarNAODetectionConstants.NAOV5MAX;
		case NAOVersion.NAOV40:
		case NAOVersion.NAOV33:
		case NAOVersion.NAOV32:
			return SonarNAODetectionConstants.NAOV4MAX;
		default:
			return -1;
		}
	}

	/**
	 * Gets the recommended minimum distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	@Override
	public float getRecommendedMinimumDistance(int loc) {
		if(sonarVersion == null || !SonarNAOLocation.LEFT.contains(loc))
		{
			return -1;
		}
		
		switch(sonarVersion)
		{
		case NAOVersion.NAOV50:
			return SonarNAODetectionConstants.NAOV5RECMIN;
		case NAOVersion.NAOV40:
		case NAOVersion.NAOV33:
		case NAOVersion.NAOV32:
			return SonarNAODetectionConstants.NAOV4MIN;
		default:
			return -1;
		}
	}

	/**
	 * Gets the recommended maximum distance an object can be to the robot.
	 * @param loc
	 * @return
	 */
	@Override
	public float getRecommendedMaximumDistance(int loc) {
		if(sonarVersion == null || !SonarNAOLocation.LEFT.contains(loc))
		{
			return -1;
		}
		
		switch(sonarVersion)
		{
		case NAOVersion.NAOV50:
			return SonarNAODetectionConstants.NAOV5RECMAX;
		case NAOVersion.NAOV40:
		case NAOVersion.NAOV33:
		case NAOVersion.NAOV32:
			return SonarNAODetectionConstants.NAOV4MAX;
		default:
			return -1;
		}
	}

}
