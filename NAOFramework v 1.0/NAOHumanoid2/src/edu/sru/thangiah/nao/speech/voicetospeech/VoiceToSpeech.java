package edu.sru.thangiah.nao.speech.voicetospeech;

import java.io.IOException;
import java.util.Date;

import com.aldebaran.qi.RawApplication;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.module.Module;
import tests.RobotConfig;

/** Class requires the use of a microphone to function.
 * Use getVoiceInput() to listen to microphone input for seconds.
 * Then use relayVoiceInput() to have the NAO repeat what the microphone heard.
 * @author Justin Cather
 *
 */

public class VoiceToSpeech extends Module implements VoiceToSpeechInterface {
	
	private StringBuilder heardWords;
	private ALTextToSpeech tts;
	
	public VoiceToSpeech(Session session) throws Exception
	{
		super(session);
		tts = new ALTextToSpeech(session);
	}
	
	@Override
	public void exit() throws Exception {
		tts.exit();
	}

	@Override
	public void reset() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Listens to microphone for a number of seconds and then saves the heard
	 * words, which can then be relayed to a robot by calling sendVoiceInput.
	 * @param seconds The number of seconds the microphone will listen for.
	 * @throws Exception
	 */
	@Override
	public void getVoiceInput(int seconds){
		System.out.println("Listening for " + seconds + "...");
		heardWords = this.listenToHuman(seconds * 1000);		
	}
	
	/**
	 * Sends the words collected by getVoiceInput to a robot.
	 * @param session The session to use to communicate with the robot.
	 * @throws Exception
	 */
	@Override
	public void relayVoiceInput() throws Exception{
		System.out.println("Sending human speech to robot...");
		tts.say(heardWords.toString());	
	}
	
	/**
	 * If any words have been recorded from microphone, they will
	 * be cleared.
	 */
	@Override
	public void clearHeardWordsBuffer() {
		this.heardWords = null;
		this.heardWords = new StringBuilder();
	}
	
	/**
	 * Checks to see if the number of words heard is greater than 0.
	 * @return True if word count greater than 0. False if not.
	 */
	@Override
	public boolean HasWordsToRelay(){
		if (heardWords.length() > 0){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Gets the words heard from microphone.
	 * @return The words heard.
	 */
	@Override
	public String WordsToRelay(){
		if (heardWords.length() > 0){
			return heardWords.toString();
		} else {
			return "";
		}
	}
	
	/** Causes any microphone input to be recorded for so many milliseconds.
	 * @param miliseconds The number of milliseconds to listen to the microphone for.
	 * @return A StringBuilder object of heard words.
	 */
	private StringBuilder listenToHuman(int milliseconds){
		// Uses CMU Sphinx jars to listen to microphone input and
		// attempts process a humans voice to text.
		
		// CMU Sphinx conifgs
		Configuration configuration;
		LiveSpeechRecognizer recognizer;
		// Track time.
		Date startTime;
		// Current heard word and all heard words.
		String utterance;
		StringBuilder words;
		 
		// Configure CMU Sphinx.
		configuration = new Configuration();
		// Set path to acoustic model.
		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		// Set path to dictionary.
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		// Set language model.
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.dmp");
		
		words = new StringBuilder();	
		utterance = null;
		
			try {
				// Start the recognizer.
				recognizer = new LiveSpeechRecognizer(configuration);
				recognizer.startRecognition(true);
				// Get the time to know when to end.
				startTime = new Date();
				
				// Uses isListening method to calculate the time left when entering loop.
				while(isListening(milliseconds, startTime))
				{
					// Get word(s) from microphone.
					utterance = recognizer.getResult().getHypothesis();
					System.out.println("proccessing...");
					Thread.sleep(2000);

					System.out.println("HEARD :: " + utterance);
					words.append(utterance + " ");
				}
				
				// Stop the recognizer.
				recognizer.stopRecognition();
				System.out.println("stopping....");
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
				System.out.println("Error recognizing...");
			}
				
		return words;
	}
	
	/** Method to track how many seconds have passed.
	 * @param miliseconds
	 * @param startTime
	 * @return
	 */
	private boolean isListening(int miliseconds, Date startTime)
	{
		// Helper method to track time.
		if( (startTime.getTime() + miliseconds) >= System.currentTimeMillis())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
		
	public static void main(String[] args){
		try {
			Connect c = new Connect();
			c.run();
			VoiceToSpeech vts = new VoiceToSpeech(c.getSession());
			
			vts.getVoiceInput(15);
			if (vts.HasWordsToRelay()){
				System.out.println("Relaying the following to NAO: " + vts.WordsToRelay());
			    vts.relayVoiceInput();
			}		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}		
}
