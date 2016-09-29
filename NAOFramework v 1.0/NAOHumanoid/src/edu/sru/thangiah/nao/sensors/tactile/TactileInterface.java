package edu.sru.thangiah.nao.sensors.tactile;

import com.aldebaran.qi.Session;

public interface TactileInterface {

	String getMostRecentTouch() throws Exception;

	boolean isRightFootBumper() throws Exception;

	boolean isLeftFootBumper() throws Exception;

	boolean isLeftHandBottom() throws Exception;

	boolean isLeftHandTop() throws Exception;

	boolean isLeftHandBack() throws Exception;

	boolean isRightHandTop() throws Exception;

	boolean isRightHandBottom() throws Exception;

	boolean isRightHandBack() throws Exception;

	boolean isRearTactile() throws Exception;

	boolean isMiddleTactile() throws Exception;

	boolean isFrontTactile() throws Exception;

}
