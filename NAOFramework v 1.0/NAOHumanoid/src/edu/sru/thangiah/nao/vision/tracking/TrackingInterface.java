package edu.sru.thangiah.nao.vision.tracking;

public interface TrackingInterface{
					
		public boolean getStatus() throws Exception;
		
		public void setMode(String Mode) throws Exception;
		
		public String getMode() throws Exception;
		
		public void startTracking(String target, float parameter) throws Exception;
		
		public void stopTracking() throws Exception;
		
		public void pointAt(String effector) throws Exception;
		
		public void lookAt() throws Exception;
	}


