package edu.sru.kearney.animation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Joints {

	public static final ArrayList<String> head =
			new ArrayList<String>() {{
				add("HeadYaw");
				add("HeadPitch");			       
			}};
			
	public static final ArrayList<String> headAndArms =  
		new ArrayList<String>() {{
			add("HeadYaw");
			add("HeadPitch");
			add("LShoulderPitch");
			add("LShoulderRoll");
			add("LElbowYaw");
			add("LElbowRoll");
			add("LWristYaw");
			add("RShoulderPitch");
	        add("RShoulderRoll");
	        add("RElbowYaw");
	        add("RElbowRoll");
	        add("RWristYaw");
    }};
    
    public static final ArrayList<String> armsAndLegs = 
    		new ArrayList<String>() {{
    			add("LShoulderPitch");
		        add("LShoulderRoll");
		        add("LElbowYaw");
		        add("LElbowRoll");
		        add("LWristYaw");
		        add("RShoulderPitch");
		        add("RShoulderRoll");
		        add("RElbowYaw");
		        add("RElbowRoll");
		        add("RWristYaw");
		        add("LHipYawPitch");
		        add("LHipRoll");
		        add("LHipPitch");
		        add("LKneePitch");
		        add("LAnklePitch");
		        add("RAnkleRoll");
		        add("RHipYawPitch");
		        add("RHipRoll");
		        add("RHipPitch");
		        add("RKneePitch");
		        add("RAnklePitch");
		        add("LAnkleRoll");
    		}};
	
	public static final ArrayList<String> leftArm =
		    new ArrayList<String>() {{
		        add("LShoulderPitch");
		        add("LShoulderRoll");
		        add("LElbowYaw");
		        add("LElbowRoll");
		        add("LWristYaw");
		    }};
	
	public static final ArrayList<String> arms = 
			new ArrayList<String>() {{
				add("LShoulderPitch");
		        add("LShoulderRoll");
		        add("LElbowYaw");
		        add("LElbowRoll");
		        add("LWristYaw");
		        add("RShoulderPitch");
		        add("RShoulderRoll");
		        add("RElbowYaw");
		        add("RElbowRoll");
		        add("RWristYaw");
			}};
			
		public static final ArrayList<String> armsAndHips = 
					new ArrayList<String>() {{
						add("LShoulderPitch");
				        add("LShoulderRoll");
				        add("LElbowYaw");
				        add("LElbowRoll");
				        add("LWristYaw");
				        add("RShoulderPitch");
				        add("RShoulderRoll");
				        add("RElbowYaw");
				        add("RElbowRoll");
				        add("RWristYaw");
				        add("LHipYawPitch");
				        add("LHipRoll");
				        add("RHipYawPitch");
				        add("RHipRoll");
					}};
	
	public static final ArrayList<String> rightArm = 
		    new ArrayList<String>() {{
		        add("RShoulderPitch");
		        add("RShoulderRoll");
		        add("RElbowYaw");
		        add("RElbowRoll");
		        add("RWristYaw");       
		    }};
	
	public static final ArrayList<String> leftLeg =
		    new ArrayList<String>() {{
		    	add("LHipYawPitch");
		        add("LHipRoll");
		        add("LHipPitch");
		        add("LKneePitch");
		        add("LAnklePitch");
		        add("RAnkleRoll");				       
		    }};
	
	public static final ArrayList<String> rightLeg = 
		    new ArrayList<String>() {{
		    	add("RHipYawPitch");
		        add("RHipRoll");
		        add("RHipPitch");
		        add("RKneePitch");
		        add("RAnklePitch");
		        add("LAnkleRoll");			       
		    }};
	
	public static final ArrayList<String> body = 
		    new ArrayList<String>() {{
		        add("HeadYaw");
		        add("HeadPitch");
		        add("LShoulderPitch");
		        add("LShoulderRoll");
		        add("LElbowYaw");
		        add("LElbowRoll");
		        add("LWristYaw2");
		        add("LHipYawPitch1");
		        add("LHipRoll");
		        add("LHipPitch");
		        add("LKneePitch");
		        add("LAnklePitch");
		        add("RAnkleRoll");
		        add("RHipYawPitch1");
		        add("RHipRoll");
		        add("RHipPitch");
		        add("RKneePitch");
		        add("RAnklePitch");
		        add("LAnkleRoll");
		        add("RHipYawPitch1");
		        add("RHipRoll");
		        add("RHipPitch");
		        add("RKneePitch");
		        add("RAnklePitch");
		        add("LAnkleRoll");
		    }};
		    
		    public static final ArrayList<String> legs =
		    		 new ArrayList<String>() {{
		 		    	add("RHipYawPitch");
				        add("RHipRoll");
				        add("RHipPitch");
				        add("RKneePitch");
				        add("RAnklePitch");
				        add("LAnkleRoll");		
				    	add("LHipYawPitch");
				        add("LHipRoll");
				        add("LHipPitch");
				        add("LKneePitch");
				        add("LAnklePitch");
				        add("RAnkleRoll");		
		 		    }};
}
