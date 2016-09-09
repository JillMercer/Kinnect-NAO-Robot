package edu.sru.thangiah.demo.redball;

public class NAOMath {
	
	/*
	 * Get the distance from two points in 3D
	 * getDistance(x1, y1, z1, x2, y2, z2)
	 * 
	 * Obsolete Use Point3D class
	 */
	public static float getDistance3D(float x1, float y1, float z1, float x2, float y2, float z2) {
		
		float x;
		float y;
		float z;
		
		x = (x2-x1) * (x2-x1);
		y = (y2-y1) * (y2-y1);
		z = (z2-z1) * (z2-z1);
		
		return (float) Math.sqrt(x + y + z);
		
	}
}
