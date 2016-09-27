package edu.sru.kearney.animation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import edu.sru.thangiah.nao.connection.Connect;

public class Animation {

	private HashMap<String,AnimationHolder> animations;
	private ALMotion motion;
	private static String animation = "test_1";
	private AnimationHolder currAnimation;
	private static final String dir = System.getProperty("user.dir").concat("\\animations\\");
	
	public Animation(ALMotion motion) throws IOException{
		this.motion = motion;
		animations = new HashMap<String,AnimationHolder>();
		Files.walk(Paths.get(dir), 1).forEach(filePath -> {
		    if (Files.isRegularFile(filePath)) {
		    	String extension = "";
		    	String path = filePath.toString();
		    	int i = path.lastIndexOf('.');
		    	if(i > 0){
		    		extension = path.substring(i+1);
		    	}
		    	if(extension.equals("nao")){
		    		File f = new File(path);
		    		String name = f.getName();
		    		name = name.substring(0, name.length() - 4);
		    		loadAnimation(f, name);
		    	}
		    }
		});
	}
	
	public ArrayList<String> getAllAnimations(){
		ArrayList<String> returnVal = new ArrayList<String>();
        Set<String> keys = animations.keySet();
        for(String key: keys){
           returnVal.add(key);
        }
        return returnVal;
	}
	
	private void loadAnimation(File f, String name){
		animations.put(name, new AnimationHolder(f));
	}
	
	public void playAnimation(String aniName){
		currAnimation = animations.get(aniName);
		currAnimation.playAnimation();
	}
	
	public void stopAll() throws CallError, InterruptedException{
		motion.killAll();
	}
	
	public boolean isAnimating(){
		if(currAnimation == null){
			return false;
		}
		return currAnimation.isAnimating();
	}
	
	private AnimationHolder getAnimationHolder(String aniName){
		return animations.get(aniName);
	}
	
	private class AnimationHolder{
		
		private boolean isAnimating = false;
		private ArrayList<String> names;
		private ArrayList<Float> initPosition;
		private ArrayList<ArrayList<Float>> angles;
		private ArrayList<ArrayList<Float>> times;
		private static final boolean isAbsolute = true;
		
		private AnimationHolder(ArrayList<String> names, ArrayList<ArrayList<Float>> angles, 
				ArrayList<ArrayList<Float>> times){
			this.names = names;
			this.angles = angles;
			this.times = times;
			initPosition = new ArrayList<Float>();
			for(int i = 0; i < names.size(); i++){
				initPosition.add(angles.get(i).get(0));
			}
			
		}
		
		private AnimationHolder(String fileName){
			File f = new File(dir.concat(fileName).concat(".nao"));
			if(f.exists()){
				try {
					loadAnimation(f);
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private AnimationHolder(File f){
			try {
				loadAnimation(f);
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void playAnimation(){
			new Thread(new Play()).start();
		}
		
		private void loadAnimation(File f) throws NumberFormatException, IOException{
			names = new ArrayList<String>();
			angles = new ArrayList<ArrayList<Float>>();
			times = new ArrayList<ArrayList<Float>>();
			initPosition = new ArrayList<Float>();
			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			int angleIndex = 0;
			int timeIndex = 0;
			String line = br.readLine();
			if(line.equals("NAMES")){
				line = br.readLine();
				String[] namesArray = line.split(",");
					for(int i = 0; i < namesArray.length; i++){
						names.add(namesArray[i]);
						angles.add(new ArrayList<Float>());
						times.add(new ArrayList<Float>());
					}	
			}
			line = br.readLine();
			if(line.equals("ANGLES")){
				while(!(line = br.readLine()).equals("TIMES")){
					String[] posArray = line.split(",");
					for(int i = 0; i < posArray.length; i++){
						angles.get(angleIndex).add(Float.parseFloat(posArray[i]));
					}
					angleIndex++;			
				}
			}
			if(line.equals("TIMES")){
				while((line = br.readLine()) != null){
					String[] timeArray = line.split(",");
					for(int i = 0; i < timeArray.length; i++){
						times.get(timeIndex).add(Float.parseFloat(timeArray[i]));
					}
					timeIndex++;
				}
			}
			br.close();
			for(int i = 0; i < names.size(); i++){
				initPosition.add(angles.get(i).get(0));
			}
			
		}
		
		private class Play implements Runnable {
			
			public void run(){
				try {
					isAnimating = true;
					motion.setAngles(names, initPosition, 1f);
					motion.angleInterpolation(names, angles, times, isAbsolute);
					isAnimating = false;
				} catch (CallError e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		private boolean isAnimating(){
			return isAnimating;
		}
		
		private String getNames(){
			String output = "NAMES\n";
			String namesVal = names.toString();
			namesVal = namesVal.substring(1,namesVal.length()-1);
			namesVal = namesVal.replaceAll("\\s",  "");
			namesVal = namesVal.concat("\n");
			output = output.concat(namesVal);
			return output;
		}
		
		private String getAngles(){
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
		
		private String getTimes(){
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
	
	public void merge(String ani1, String ani2, String newName){
		AnimationHolder ani_1 = getAnimationHolder(ani1);
		AnimationHolder ani_2 = getAnimationHolder(ani2);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<ArrayList<Float>> angles = new ArrayList<ArrayList<Float>>();
		ArrayList<ArrayList<Float>> times = new ArrayList<ArrayList<Float>>();
		
		for(String str : ani_1.names){
			names.add(str);
		}
		for(String str : ani_2.names){
			names.add(str);
		}
		
		for(String str: names){
			angles.add(new ArrayList<Float>());
			times.add(new ArrayList<Float>());
		}
		
		int secondSize = 0;
		
		if(ani_1.times.get(0).size() < ani_2.times.get(0).size()){
			secondSize = ani_1.times.get(0).size();
		}
		else{
			secondSize = ani_2.times.get(0).size();
		}
		int index = 0;
		for(int i = 0; i < ani_1.times.size(); i++){
			for(int x = 0; x < secondSize; x++){
				angles.get(i).add(ani_1.angles.get(i).get(x));
				times.get(i).add(ani_1.times.get(i).get(x));
			}
			index = i;
		}
		
		for(int i = index, y = 0; y < ani_2.times.size(); i++, y++){
			for(int x = 0; x < secondSize; x++){
				angles.get(i).add(ani_2.angles.get(i).get(x));
				times.get(i).add(ani_2.times.get(i).get(x));
			}
		}
		
		ArrayList<String> names2 = new ArrayList<String>();
		ArrayList<Integer> removeList = new ArrayList<Integer>();
		
		for(int i = 0; i < names.size(); i++){
			if(!names2.contains(names.get(i))){
				names2.add(names.get(i));
			}
			else{
				removeList.add(i);
			}
		}
		
		for(int i = 0; i < removeList.size(); i++){
			names.remove(removeList.get(i));
			angles.remove(removeList.get(i));
			times.remove(removeList.get(i));
		}
		try {
			exportAnimation(newName, new AnimationHolder(names, angles, times));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void exportAnimation(String name, AnimationHolder ani) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		
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
			writer.write(ani.toString());
			writer.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
