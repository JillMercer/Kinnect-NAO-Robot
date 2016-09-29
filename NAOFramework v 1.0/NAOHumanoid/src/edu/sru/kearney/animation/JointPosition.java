package edu.sru.kearney.animation;

import java.util.ArrayList;

public class JointPosition {

	public ArrayList<Float> vals;
	public ArrayList<String> chain;
	
	public JointPosition(ArrayList<String> chain, ArrayList<Float> vals){
		this.vals = vals;
		this.chain = chain;
	}
	
	public String toString(){
		String returnVal = vals.toString();
		returnVal = returnVal.substring(1,returnVal.length()-1);
		returnVal = returnVal.concat("\n");
		return returnVal;
	}
	
	public void smooth(){
		int size = vals.size();
		for(int i = 0; i < size - 1; i++){
			float val1 = (vals.get(i) + (vals.get(i+1) - vals.get(i)) * .33f);
			float val2 = (vals.get(i) + (vals.get(i+1) - vals.get(i)) * .66f);
			vals.add(i+1, val2);
			vals.add(i+1, val1);
			i += 2;
			size += 2;
		}
	}

}
