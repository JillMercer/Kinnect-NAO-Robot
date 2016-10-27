package edu.sru.thangiah.nao;

import com.aldebaran.qi.helper.proxies.ALMotion;
import edu.ufl.digitalworlds.j4k.Skeleton;

public class RoboPosition
{

	
	public RoboPosition()   
	{
		//significant difference in coords is 0.1 meters,
		final double difference = 0.1;
	}

	public void copyPosition(Skeleton[] skeletons, double difference)
	{	
		
	if (Math.abs(skeletons[0].get3DJointX(5) - skeletons[0].get3DJointX(6)) < difference)
        {       //left arm by side indices 5 and 6 //no change in X, change in Y, no change in Z
            if ((Math.abs(skeletons[0].get3DJointY(5) - skeletons[0].get3DJointY(6)) > difference)&&(Math.abs(skeletons[0].get3DJointZ(5) - skeletons[0].get3DJointZ(6)) < difference))
            {
                System.out.println("left arm by side");
		//motion.setAngles("LShoulderPitch", 1.5708, 0.5f); 
		//motion.setAngles("LShoulderRoll", 0, 0.5f);
            }   //left arm out to front //no change in X or Y, change in Z
            else if ((Math.abs(skeletons[0].get3DJointY(5) - skeletons[0].get3DJointY(6)) < difference)&&(Math.abs(skeletons[0].get3DJointZ(5) - skeletons[0].get3DJointZ(6)) > difference))	
            {
                System.out.println("left arm out to front");
		//motion.setAngles("LShoulderPitch", 0, 0.5f);
		//motion.setAngles("LShoulderRoll", -0.3142, 0.5f);
            }
        }       //left arm out to side //change in X, no change in Y, no change in Z
        else if ((Math.abs(skeletons[0].get3DJointY(5) - skeletons[0].get3DJointY(6)) < difference)&&(Math.abs(skeletons[0].get3DJointZ(5) - skeletons[0].get3DJointZ(6)) < difference))	
	{     
           System.out.println("left arm out to side");
		//motion.setAngles("LShoulderPitch", 0, 0.5f);
		//motion.setAngles("LShoulderRoll", 1.3265, 0.5f);	
	}       
        
        if (Math.abs(skeletons[0].get3DJointX(9) - skeletons[0].get3DJointX(10)) < difference)
        {    //right arm by side indices 9 and 10 //no change in X, change in Y, no change in Z
            if ((Math.abs(skeletons[0].get3DJointY(9) - skeletons[0].get3DJointY(10)) > difference)&&(Math.abs(skeletons[0].get3DJointZ(9) - skeletons[0].get3DJointZ(10)) < difference))
            {
                System.out.println("right arm by side");
		//motion.setAngles("RShoulderPitch", 1.5708, 0.5f); 
		//motion.setAngles("RShoulderRoll", 0, 0.5f); 
            }   //right arm out to front //no change in X or Y, change in Z
            else if ((Math.abs(skeletons[0].get3DJointY(9) - skeletons[0].get3DJointY(10)) < difference)&&(Math.abs(skeletons[0].get3DJointZ(9) - skeletons[0].get3DJointZ(10)) > difference))	
            {
                System.out.println("right arm out to front");
		//motion.setAngles("RShoulderPitch", 0.5f);
		//motion.setAngles("RShoulderRoll", 0.3142, 0.5f);
            }
        }       //right arm out to side //change in X, no change in Y, no change in Z
        else if ((Math.abs(skeletons[0].get3DJointY(9) - skeletons[0].get3DJointY(10)) < difference)&&(Math.abs(skeletons[0].get3DJointZ(9) - skeletons[0].get3DJointZ(10)) < difference))	
	{     
            System.out.println("right arm out to side");
	//motion.setAngles("RShoulderPitch", 0, 0.5f);
	//motion.setAngles("RShoulderRoll", -1.3265, 0.5f);
	}	
		
		
	//left leg vertical indices 13 and 16 
	//no change in X, change in Y, no change in Z
	if (((skeletons[0].get3DJointX(13) - skeletons[0].get3DJointX(16)) < difference)&&
			((skeletons[0].get3DJointY(13) - skeletons[0].get3DJointY(16)) > difference)&&
			((skeletons[0].get3DJointZ(13) - skeletons[0].get3DJointZ(16)) < difference))
	{
		motion.setAngles("LHipRoll", 0, 0.5f); 
	}
	//right leg vertical indices 17 and 20
	if (((skeletons[0].get3DJointX(17) - skeletons[0].get3DJointX(20)) < difference)&&
			((skeletons[0].get3DJointY(17) - skeletons[0].get3DJointY(20)) > difference)&&
			((skeletons[0].get3DJointZ(17) - skeletons[0].get3DJointZ(20)) < difference))
	{
		motion.setAngles("RHipRoll", 0, 0.5f); 
	}
	//left leg to side indices 13 and 16
	//change in X, change in Y, no change in Z
	if (((skeletons[0].get3DJointX(13) - skeletons[0].get3DJointX(16)) > difference)&&
			((skeletons[0].get3DJointY(13) - skeletons[0].get3DJointY(16)) > difference)&&
			((skeletons[0].get3DJointZ(13) - skeletons[0].get3DJointZ(16)) < difference))
	{
		motion.setAngles("LHipRoll", 0.26, 0.5f); 
	}
	//right leg to side indices 17 and 20, not both legs at the same time
	else if (((skeletons[0].get3DJointX(17) - skeletons[0].get3DJointX(20)) > difference)&&
			((skeletons[0].get3DJointY(17) - skeletons[0].get3DJointY(20)) > difference)&&
			((skeletons[0].get3DJointZ(17) - skeletons[0].get3DJointZ(20)) < difference))
	{
		motion.setAngles("RHipRoll", -0.26, 0.5f); 
	}*/
	}
}
