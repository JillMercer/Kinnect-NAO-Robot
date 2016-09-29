package edu.sru.thangiah.nao.awareness;

import java.util.ArrayList;

public interface DistanceInterface {

	/** Set the proximity for zone one. 
	 * @param distance The new value for zone one edge.
	 * @throws Exception
	 */
	public void setFirstZoneDistance(float distance) throws Exception;
	
	/** Set the proximity for zone two.
	 * @param distance The new value for zone two edge.
	 * @throws Exception
	 */
	public void setSecondZoneDistance(float distance) throws Exception;
	
	/** Gets a list of people IDs in zone one.
	 * @return An ArrayList of integer IDs in zone one.
	 */
	public ArrayList<Integer> getPeopleInZone1();
		
	}
