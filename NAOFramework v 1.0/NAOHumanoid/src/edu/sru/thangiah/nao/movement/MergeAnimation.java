package edu.sru.thangiah.nao.movement;

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
import java.util.ArrayList;


public class MergeAnimation {

	private static final String dir = System.getProperty("user.dir").concat("\\animations\\");
	
	public static void main(String args[]) throws Exception{
		merge("dabarms_0", "dabhead_3", "dab");
	}

	private static class AnimationHolder{
		
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
	
		private void loadAnimation(File f) throws NumberFormatException, IOException{
			names = new ArrayList<String>();
			angles = new ArrayList<ArrayList<Float>>();
			times = new ArrayList<ArrayList<Float>>();
			initPosition = new ArrayList<Float>();
			FileInputStream fis = new FileInputStream(f);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			int angleIndex = 0;
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
				line = br.readLine();
				String[] timeArray = line.split(",");
				for(int x = 0; x < times.size(); x++){
					for(int i = 0; i < timeArray.length; i++){
						times.get(x).add(Float.parseFloat(timeArray[i]));
					}
					}
			}
			br.close();
			for(int i = 0; i < names.size(); i++){
				initPosition.add(angles.get(i).get(0));
			}
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
	
	public static void merge(String ani1, String ani2, String newName){
		AnimationHolder ani_1 = new AnimationHolder(ani1);
		AnimationHolder ani_2 = new AnimationHolder(ani2);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<ArrayList<Float>> angles = new ArrayList<ArrayList<Float>>();
		ArrayList<ArrayList<Float>> times = new ArrayList<ArrayList<Float>>();
		
		for(String str : ani_1.names){
			names.add(str);
		}
		for(String str : ani_2.names){
			names.add(str);
		}
		System.out.println(names);
		for(String str: names){
			angles.add(new ArrayList<Float>());
			times.add(new ArrayList<Float>());
		}
		
		for(int i = 0; i < ani_1.angles.size(); i++){
			for(int x = 0; x < ani_1.angles.get(0).size(); x++){
				angles.get(i).add(ani_1.angles.get(i).get(x));
				times.get(i).add(ani_1.times.get(i).get(x));
			}
		}
		for(int i = 0, y = ani_1.angles.size(); i < ani_2.angles.size(); i++, y++){
			for(int x = 0; x < ani_2.angles.get(0).size(); x++){
				angles.get(y).add(ani_2.angles.get(i).get(x));
				times.get(y).add(ani_2.times.get(i).get(x));
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
	
	private static void exportAnimation(String name, AnimationHolder ani) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		
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
