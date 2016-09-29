package edu.sru.kearney.animation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

public class NaoAnimation {

	private static String dir = System.getProperty("user.dir");
	private ArrayList<String> storedAnimations;
	private ArrayList<StoredAnimation> animations;
	private ALMotion motion;
	private Boolean animating = false;
	
	public NaoAnimation(ALMotion motion){
		storedAnimations = new ArrayList<String>();
		animations = new ArrayList<StoredAnimation>();
		dir = dir.concat("\\animations\\");
		this.motion = motion;
	}
	
	public void loadAnimation(String animationName){
		if(storedAnimations.contains(animationName)) return;
		else{
			try {
				animations.add(new StoredAnimation(animationName));				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void loadAnimation(String animationName, int speed){
		if(storedAnimations.contains(animationName)) return;
		else{
			try {
				animations.add(new StoredAnimation(animationName, speed));				
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private class StoredAnimation implements Runnable{

		ArrayList<String> names;
		ArrayList<JointPosition> positions;
		String animationName = "";
		int speed = 10;
		
		private void playAnimation() throws CallError, InterruptedException{
			animating = true;
			motion.setStiffnesses(names, 1f);
			motion.setAngles(names, positions.get(0).vals, .4f);
			Thread.sleep(50);
			int i = 0;
			while(animating && i < positions.size()){
				motion.setAngles(names, positions.get(i).vals, .8f);
				i++;
				Thread.sleep(speed);
			}
			motion.setAngles(names, positions.get(0).vals, .4f);
			animating = false;
		}

		private StoredAnimation(String animationName, int speed) throws NumberFormatException, IOException{
			this.speed = speed;
			this.animationName = animationName;
			String filePath = dir.concat(animationName).concat(".nao");
			names = new ArrayList<String>();
			positions = new ArrayList<JointPosition>();
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			
			if((line = br.readLine()) != null){
				String[] namesArray = line.split(",");
				
				for(int i = 0; i < namesArray.length; i++){
					String currName = namesArray[i].replaceAll("\\s","");
					names.add(currName);
				}
				while((line = br.readLine()) != null){
					String[] pos = line.split(",");
					ArrayList<Float>positionArray = new ArrayList<Float>();
					for(int i = 0; i < pos.length; i++){
						String currFloat = pos[i].replaceAll("\\s", "");
						positionArray.add(Float.parseFloat(currFloat));
					}
					positions.add(new JointPosition(names, positionArray));
				}
			}
			br.close();
			storedAnimations.add(animationName);
		}
		
		private StoredAnimation(String animationName) throws NumberFormatException, IOException{
			this.animationName = animationName;
			String filePath = dir.concat(animationName).concat(".nao");
			names = new ArrayList<String>();
			positions = new ArrayList<JointPosition>();
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String line = null;
			
			if((line = br.readLine()) != null){
				String[] namesArray = line.split(",");
				
				for(int i = 0; i < namesArray.length; i++){
					String currName = namesArray[i].replaceAll("\\s","");
					names.add(currName);
				}
				while((line = br.readLine()) != null){
					String[] pos = line.split(",");
					ArrayList<Float>positionArray = new ArrayList<Float>();
					for(int i = 0; i < pos.length; i++){
						String currFloat = pos[i].replaceAll("\\s", "");
						positionArray.add(Float.parseFloat(currFloat));
					}
					positions.add(new JointPosition(names, positionArray));
				}
			}
			br.close();
			storedAnimations.add(animationName);
		}

		@Override
		public void run() {
			try {
				playAnimation();
			} catch (CallError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	public boolean blockCall(){
		while(animating){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public void stopAll(){
		animating = false;
	}
	
	public boolean isAnimating(){
		return animating;
	}
	
	public void playAnimation(String animationName){
		for(StoredAnimation animation : animations){
			if(animation.animationName.equals(animationName)){
				new Thread(animation).start();
			}
		}
	}
}