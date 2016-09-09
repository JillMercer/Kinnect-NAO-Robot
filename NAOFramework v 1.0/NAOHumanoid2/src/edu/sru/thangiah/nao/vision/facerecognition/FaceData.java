package edu.sru.thangiah.nao.vision.facerecognition;

/**
 * Object to hold face data, a name and a confidence value.
 * @author Justin Cather
 *
 */
public class FaceData {
	public String name = null;
	public float confidence = -1.0f;
	
	public boolean isEmpty(){
		if (name == null || confidence < 0.0f){
			return true;
		} else {
			return false;
		}
	}
}
