package edu.sru.thangiah.nao.system;

import java.util.ArrayList;

import com.aldebaran.qi.RawApplication;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.thangiah.nao.DebugSettings;
import edu.sru.thangiah.nao.vision.facerecognition.FaceData;
import tests.RobotConfig;


/**
 * @author Justin Cather
 * @use Use this class to make static methods to get
 * values from the NAOs memory.
 */
public class MemoryHelper {
	/**
	 * Returns the scanned barcode data, or null if no data.
	 * @param session Session to use.
	 * @return Scanned barcode data.
	 * @throws Exception
	 */
	public static String getBarcodeData(Session session){
		ALMemory memory;
		String data = null;
		try {
			memory = new ALMemory(session);
			ArrayList<ArrayList> list = (ArrayList)memory.getData("BarcodeReader/BarcodeDetected");
			
			if (list.size() > 0) {
				data = (String) list.get(0).get(0);
			}
		}
		catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return data;
	}
	
	/**
	 * Gets the last recognized face values from memory.
	 * @param session Session to use.
	 * @return FaceData object which is a pair consisting of
	 * the name of the face recognized and a confidence value.
	 */
	public static FaceData getRecognizedFaceName(Session session){
		ALMemory memory;
		FaceData faceData = new FaceData();
		
		try {
			memory = new ALMemory(session);
			try {
				ArrayList list = (ArrayList)memory.getData("FaceDetected");
				DebugSettings.printDebug(DebugSettings.COMMENT, list.toString());
				
				ArrayList list2 = (ArrayList)list.get(1);
				ArrayList list3 = (ArrayList)list2.get(0);
				ArrayList list4 = (ArrayList)list3.get(1);
				
				faceData.name = (String)list4.get(2);
				faceData.confidence = (float)list4.get(1);
			} 
			catch (Exception e) {
				faceData = null;
			}
		} 
		catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
			faceData = null;
		}
		
		return faceData;
	}
	
	public static String getCurrentTTSWord(Session session){
		ALMemory memory;
		String word = null;
		
		try {
			memory = new ALMemory(session);
			word = (String) memory.getData("ALTextToSpeech/CurrentWord");
			return word;
			
		} 
		catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
			return null;
		}				
	}
	
	public static boolean isTalking(Session session){
		ALMemory memory;
		int value = -1;
		boolean isTalking = false;
		
		try {
			memory = new ALMemory(session);
			value = (int) memory.getData("ALTextToSpeech/TextDone");
			
			if(value == 0){
				isTalking = true;
			}
			else {
				isTalking = false;
			}
			
		} 
		catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
		}
		
		return isTalking;
	}
	
	public static void main(String[] args) throws Exception{
		RawApplication app = new RawApplication(args);
		Session session = new Session(RobotConfig.getConnectionString());
		while (true) {
			System.out.println(MemoryHelper.getCurrentTTSWord(session));
		}
		
	}
}
