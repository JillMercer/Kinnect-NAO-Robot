package edu.sru.kearney.animation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;

public class AnimationRecorder {
	
	private Connect connect;
	private ALMotion motion;
	private ALMemory memory;
	private ALBasicAwareness awareness;
	private ALTextToSpeech tts;
	private EventPair rear, front, middle;
	private boolean collecting = false;
	private ArrayList<String> names;
	private ArrayList<ArrayList<Float>> angles;
	private ArrayList<ArrayList<Float>> times;
	private final static boolean isAbsolute = true;
	private ALRobotPosture posture;
	private int option = 0;
	private boolean isRunning = false;
	
	public static void main(String args[]){
		try {
			new AnimationRecorder((ArrayList<String>) Joints.arms, "192.168.0.116");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public AnimationRecorder(ArrayList<String> names, String ip) throws Exception{
		
		this.names = names;
		angles = new ArrayList<ArrayList<Float>>();
		times = new ArrayList<ArrayList<Float>>();
		for(int i = 0; i < names.size(); i++){
			angles.add(new ArrayList<Float>());
			times.add(new ArrayList<Float>());
		}
		front = new EventPair();
		rear = new EventPair();
		middle = new EventPair();
		connect = new Connect(ip);
		connect.run();
		awareness = new ALBasicAwareness(connect.getSession());
		awareness.stopAwareness();
		motion = new ALMotion(connect.getSession());
		motion.setBreathEnabled("Body", false);
		motion.closeHand("LHand");
		motion.closeHand("RHand");
		memory = new ALMemory(connect.getSession());
		tts = new ALTextToSpeech(connect.getSession());
		posture = new ALRobotPosture(connect.getSession());
		motion.wakeUp();
		posture.goToPosture("Stand", .8f);
		rear.eventName = NaoEvents.RearTactilTouched;
		front.eventName = NaoEvents.FrontTactilTouched;
		middle.eventName = NaoEvents.MiddleTactilTouched;
		middle.eventID = memory.subscribeToEvent(middle.eventName, (value) -> {
			if(((Float) value) > 0.00){
				try{
					executeOption();
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
		});
		rear.eventID = memory.subscribeToEvent(rear.eventName, (value) -> {
			if (((Float) value) > 0.00) {
			if(!collecting){
				if(option <= 0){
					option = 4;
				}
				else option--;
				try {
					sayOption();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			}});	
		memory.subscribeToEvent(front.eventName, (value) ->{
			if (((Float) value) > 0.00) {
				if(!collecting){
					if(option >= 4){
						option = 0;
					}
					else option++;
					try {
						sayOption();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}}});
		connect.getApplication().run();
	}
	
	private void sayOption() throws Exception{
		switch(option){
		case 0: tts.say("collect");
			break;
		case 1: tts.say("play animation");
			break;
		case 2: tts.say("export animation");
			break;
		case 3: tts.say("reset animation");
			break;
		case 4: tts.say("exit");
			break;
		}
	}
	
	private void executeOption() throws Exception{
		switch(option){
		case 0: collect();
			break;
		case 1: playAnimation();
			break;
		case 2: exportAnimation();
			break;
		case 3: resetAnimation();
			break;
		case 4: exit();
			break;
		}
	}
	
	private void exit() throws Exception{
		memory.unsubscribeToEvent(rear.eventID);
		memory.unsubscribeToEvent(front.eventID);
		memory.unsubscribeToEvent(middle.eventID);
		awareness.startAwareness();
		motion.setBreathEnabled("Body", true);
		motion.rest();
		connect.stop();
	}
	
	private void collect() throws CallError, InterruptedException{
		if(!collecting){
			collecting = true;
			resetAnimation();
			posture.goToPosture("Stand", 1f);
			new Thread(new Collect()).start();
		}
		else{
			collecting = false;
			motion.wakeUp();
			posture.goToPosture("Stand", 1f);
			motion.waitUntilMoveIsFinished();
		}
		
	};
	
	class Collect implements Runnable{
		public void run(){
			try {
				motion.setStiffnesses(names, 0f);
				motion.setFallManagerEnabled(false);
			} catch (CallError e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			float time = .05f;
			long speed = 50;
			while(collecting){	
				try {
					System.out.println(motion.getAngles(names, false));
					addPos((ArrayList<Float>) motion.getAngles(names, false), time);
					Thread.sleep(speed);
					time += .05f;
				} catch (CallError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			smooth();
			try {
				motion.setFallManagerEnabled(true);
				motion.wakeUp();
				posture.goToPosture("Stand", 1f);
				Thread.sleep(500);
			} catch (CallError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	}
			
		}
	}
	
	public void smooth(){
		for(int i = 0; i < names.size(); i++){
			ArrayList<Float> angleList = angles.get(i);
			ArrayList<Float> timeList = times.get(i);
			int size = angles.get(i).size();
			for(int x = 0; x < size - 1; x++){
				float a = angleList.get(x);
				float b = angleList.get(x+1);
				float val1 = (a + (b-a) * .33f);
				float val2 = (a + (b-a) * .66f);
				angleList.add(x+1, val2);
				angleList.add(x+1, val1);
				float aa = timeList.get(x);
				float bb = timeList.get(x+1);
				float val11 = (aa + (bb-aa) * .33f);
				float val22 = (aa + (bb-aa) * .66f);
				timeList.add(x+1, val22);
				timeList.add(x+1, val11);
				x += 2;
				size +=2;			
			}
		}
	}
	
	public void addPos(ArrayList<Float> input, float time){
		for(int i = 0; i < input.size(); i++){
			angles.get(i).add(input.get(i));
			times.get(i).add(time);
		}
	}
	
	public void resetAnimation(){
		for(int i = 0; i < angles.size(); i++){
			angles.get(i).clear();
			times.get(i).clear();
		}
	}
	
	public void playAnimation() throws CallError, InterruptedException{
		System.out.println(toString());
		posture.goToPosture("Stand", 1f);
		motion.waitUntilMoveIsFinished();
		motion.setFallManagerEnabled(false);
		motion.angleInterpolation(names, angles, times, isAbsolute);
		motion.waitUntilMoveIsFinished();
		motion.setFallManagerEnabled(true);
		posture.goToPosture("Stand", 1f);
		motion.waitUntilMoveIsFinished();
		motion.wakeUp();
	}
	
	public void exportAnimation() throws UnsupportedEncodingException, FileNotFoundException, IOException{
		Scanner sc = new Scanner(System.in);
		System.out.println("Save animation as: ");
		String name = sc.next();
		sc.close();
		String dir = System.getProperty("user.dir");
		dir = dir.concat("\\animations\\");

		int index = 0;
		String filePath = dir + name + "_" + index + ".nao";
		File file = new File(filePath);
		while(file.exists()){
			index++;
			filePath = dir + name + "_" + index + ".nao";
			file = new File(filePath);
		}
		System.out.println(filePath);
		try (Writer writer = new BufferedWriter(new FileWriter(file))){
			writer.write(this.toString());
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public String getNames(){
		String output = "NAMES\n";
		String namesVal = names.toString();
		namesVal = namesVal.substring(1,namesVal.length()-1);
		namesVal = namesVal.replaceAll("\\s",  "");
		namesVal = namesVal.concat("\n");
		output = output.concat(namesVal);
		return output;
	}
	
	public String getAngles(){
		String output = "ANGLES\n";
		for(int i = 0; i < names.size(); i++){
			String angleVal = angles.get(i).toString();
			angleVal = angleVal.substring(1,angleVal.length()-1);
			angleVal = angleVal.replaceAll("\\s",  "");
			angleVal = angleVal.concat("\n");
			output = output.concat(angleVal);
		}
		return output;
	}
	
	public String getTimes(){
		String output = "TIMES\n";
		for(int i = 0; i < times.size(); i++){
			String timeVal = times.get(i).toString();
			timeVal = timeVal.substring(1,timeVal.length()-1);
			timeVal = timeVal.replaceAll("\\s",  "");
			if(i < times.size() - 1){
			timeVal = timeVal.concat("\n");
			}
			output = output.concat(timeVal);
		}
		return output;
	}
	
	public String toString(){
		String output = getNames();
		output = output.concat(getAngles());
		output = output.concat(getTimes());
		return output;
	}
}
