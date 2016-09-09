package com.aldebaran.demo;

public class ExShutdownHook {
	
	static boolean bContinue = true;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExShutdownHook ex = new ExShutdownHook();
		ex.run();

	}
	
	void run() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
	     	   @Override
	     	   public void run() {
	     		   System.out.println("Uninitialize Called");
	     		   bContinue = false;
	     	   }
	     	  });
			
			  System.out.println("Shut Down Hook Attached.");
	}

}
