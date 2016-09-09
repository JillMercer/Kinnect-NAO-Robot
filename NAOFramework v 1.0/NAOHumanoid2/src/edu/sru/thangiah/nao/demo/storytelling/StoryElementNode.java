package edu.sru.thangiah.nao.demo.storytelling;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;

import com.aldebaran.qi.Session;

import edu.sru.thangiah.nao.DebugSettings;

public class StoryElementNode extends Node implements StoryElementInterface<Node> {

	private static final String SPEECH_PAUSE = "\\pau=1000\\";
	
	private StringBuilder storyText;
	private ArrayList<String> options;
	private int speechRate;
	
	public StoryElementNode(){
		storyText = new StringBuilder();
		options = new ArrayList<String>();
		speechRate = 60;
	}
	
	
	@Override
	public String repeatOptions() {
		//String yourOptions = "\\rspd=" + speechRate + "\\";
		String yourOptions = "";
		Iterator<String> iterator = options.iterator();
		int counter = 1;
		
		while(iterator.hasNext()){
			yourOptions += "Option " + counter + " is " + iterator.next() + SPEECH_PAUSE;
			counter++;
		}	
		//DebugSettings.printDebug(DebugSettings.COMMENT, "STORY OPTIONS: " + yourOptions);
		
		return yourOptions;
	}

	@Override
	public String repeatText() {	
		//DebugSettings.printDebug(DebugSettings.COMMENT, "STORY TEXT: " + this.getStoryText());
		//return "\\rspd=" + speechRate + "\\" + this.getStoryText();
		return this.getStoryText();
	}

	@Override
	public Node chooseOption(int optionNumber) {
		return this.getLinkedNode(optionNumber);
	}

	@Override
	public Node chooseOption(String optionName) {
		return this.getLinkedNode(optionName);
	}

	@Override
	public String getStoryText() {
		return this.storyText.toString();
	}

	@Override
	public void setStoryText(String storyText) {
		this.storyText.append(storyText);
	}

	@Override
	public void addOption(String option, Node optionElement) throws Exception {
		if (option != null && optionElement != null){
			options.add(option);
			this.linkNode(optionElement);
		}
		else {
			throw new InvalidParameterException("Option or OptionElement was null.");
		}
		
	}
	
	@Override
	public void addOption(String option){
		if (option != null && !option.isEmpty()){
			options.add(option);
		}
		else {
			throw new InvalidParameterException("Option was null or empty.");
		}
	}

	@Override
	public Iterator<String> getOptions() {
		return options.iterator();
	}

}
