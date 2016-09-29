package edu.sru.thangiah.nao.vision.facerecognition;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALFaceDetection;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.events.EventMethod;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;
import edu.sru.thangiah.nao.system.HouseKeeping;
import edu.sru.thangiah.nao.system.MemoryHelper;
import tests.RobotConfig;

public class FaceRecognition {	
	
	private ALMemory memory;
    private ALFaceDetection face;
    private Session session;
    
    private Date previousTime; //last time Gaze analysis called
    private boolean isLearningFace;
    private int timeBetweenRec;
    
    public FaceRecognition(Session session) throws Exception{
    	timeBetweenRec = 20000;
    	isLearningFace = false;
    	previousTime = new Date();
    	
    	this.session = session;
    	face = new ALFaceDetection(this.session);
    }
    
    public boolean learnFace(String name) throws Exception{
    	boolean isGood = false;
    	
    	if (this.getLearnedNames().contains(name)){
    		return this.relearnFace(name);
    	}
    	else {
    		isLearningFace = true;  		
        	isGood = face.learnFace(name);
        	isLearningFace = false;
        	
        	return isGood;
    	}
    }
    
    public boolean relearnFace(String name) throws Exception{
    	boolean isGood = false;
    	
    	if(this.getLearnedNames().contains(name)){
    		isLearningFace = true;	
        	isGood = face.reLearnFace(name);
        	isLearningFace = false;
        	
        	return isGood;
    	}
    	else{
    		return this.learnFace(name);
    	}
    }
    
    public ArrayList<String> getLearnedNames() throws Exception{
    	ArrayList<String> faces = (ArrayList<String>)face.getLearnedFacesList();
    	return faces;
    }
    
    /**
     * Subscribes to the FaceDetected event.
     * @param session The session to connect to the NAO.
     * @param method The method you would like to call when
     * a face is detected.
     * @return An EventPair of Name and ID.
     * @throws Exception
     */
    public EventPair startRecognizing(EventMethod method) throws Exception{
    	EventPair thisEvent = new EventPair();
    	memory = new ALMemory(this.session);
    	face.subscribe("faceRec", 500, 0.0f);
    	thisEvent.eventName = NaoEvents.FaceDetected;
    	
    	//final long id = 
    	thisEvent.eventID = memory.subscribeToEvent(NaoEvents.FaceDetected, (value)->{
				if(this.isTimeOut() && !isLearningFace){
					face.unsubscribe("faceRec");
					
					// user defined method called here.
					method.run();
					
					face.subscribe("faceRec", 500, 0.0f);
				}
			});		

    	return thisEvent;
    }
    
    /**
     * Call when the program ends to stop writing to NAO memory with
     * FaceRecognized events.
     * @param session The session to connect with.
     * @throws Exception
     */
    public void stopRecognizing() throws Exception{
    	if (face != null && face.isRecognitionEnabled()) {
			face.unsubscribe("faceRec");
		}
    	else {
    		throw new Exception("stopRecognizing() was called when Face Recognition was not running.");
    	}
    }
    
    // Checks time between recognitions.
    private boolean isTimeOut(){
    	Date currentTime = new Date();
    	
    	if(currentTime.getTime() - previousTime.getTime() > timeBetweenRec){
    		previousTime = currentTime;   		
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public static void main(String[] args) throws Exception{
    	Scanner input = new Scanner(System.in);
    	
    	Application app = new Application(args, RobotConfig.getConnectionString());	
		app.start();
		ALMemory memory = new ALMemory(app.session());
		HouseKeeping collect = new HouseKeeping(app.session());
		FaceRecognition faceRec = new FaceRecognition(app.session());
		ALTextToSpeech tts = new ALTextToSpeech(app.session());
		
		// Method to run when a face is detected.
		EventMethod method = new EventMethod() {
			@Override
			public void run() {
				FaceData faceData = MemoryHelper.getRecognizedFaceName(app.session());
				
				if (!faceData.isEmpty()) {
					if (faceData.confidence > .29) {
						try {
							tts.say(faceData.name);
						} catch (CallError | InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(faceData.name);
					} else {
						System.out.println("Low score: " + faceData.confidence);
						System.out.println(faceData.name);
					} 
				}
				
			}
		};
		
		EventPair x = faceRec.startRecognizing(method);
		long y = memory.subscribeToEvent(NaoEvents.MiddleTactilTouched, (value) -> {
			if (((Float) value) > 0.00) {
				System.out.println("_________Application is Stopping__________");
				try {
					faceRec.stopRecognizing();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                app.stop();
			}
		});
		long z = memory.subscribeToEvent(NaoEvents.FrontTactilTouched, (value) -> {
			if (((Float)value) > 0.00f){
				System.out.println("Enter the name: ");
				String name = null;
				while (name == null){
					name = input.next();
				}
				
				try {
					boolean isGood = faceRec.learnFace(name);
					
					while (!isGood){
						isGood = faceRec.learnFace(name);
						Thread.sleep(5000);
					}
					
					System.out.println(faceRec.getLearnedNames());
				} 
				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		});
		
        app.run();
        collect.addEvent(x);
        collect.addEvent(NaoEvents.MiddleTactilTouched, y);
        collect.addEvent(NaoEvents.FrontTactilTouched, z);
        collect.reset();		
    }
}
