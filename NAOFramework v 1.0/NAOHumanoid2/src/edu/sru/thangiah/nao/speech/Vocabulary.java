package edu.sru.thangiah.nao.speech;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;

import tests.RobotConfig;

/** Reads in a txt of words and phrases and sets the NAOs vocabulary to the
 * words or phrases contained in the file. Each line in the input file should 
 * be a word or phrase.
 * @author Justin Cather
 *
 */
public class Vocabulary {
	
	private static String DEFAULT_FILE = System.getProperty("user.dir") + "\\vocabulary.txt";
	
	private Vocabulary(){}
	
	/**
	 * Will read in a text file and add the words in the file to the NAO's vocabulary.
	 * @param recognition The instance of ALSpeechRecognition to use.
	 * @param vocabularyFile The vocabulary text file. Each word must be on its own line in the file.
	 * @return A boolean where true means the method executed properly, or false if it fails.
	 */
	public static boolean Load(ALSpeechRecognition recognition, String vocabularyFile){
		ArrayList<String> wordList = new ArrayList<String>();
		BufferedReader input = null;
		String currentLine = null;
		boolean isSuccess = false;
		
		try {
				input = new BufferedReader(new FileReader(vocabularyFile));
			
			// Read in the words from file and add to arraylist.
			while ((currentLine = input.readLine()) != null) {
					wordList.add(currentLine);
				}
			
				isSuccess = true;
			} 
			catch (IOException e) {
				e.printStackTrace();
				isSuccess = false;
			} 
			// Try closing the bufferedreader.
			finally {
					try {
						if (input != null){
							input.close();
							recognition.setVocabulary(wordList, false);
						}
					}	 
					catch (IOException | CallError | InterruptedException ex) {
						ex.printStackTrace();
						isSuccess = false;
					}
			}
		
		return isSuccess;
	}
	
	/**
	 * Will read in a text file and add the words in the file to the NAO's vocabulary.
	 * @param recognition The instance of ALSpeechRecognition to use.
	 * @return A boolean where true means the method executed properly, or false if it fails.
	 */
	public static boolean Load(ALSpeechRecognition recognition){
		ArrayList<String> wordList = new ArrayList<String>();
		BufferedReader input = null;
		String currentLine = null;
		boolean isSuccess = false;
		
		try {
				input = new BufferedReader(new FileReader(DEFAULT_FILE));
			
			// Read in the words from file and add to arraylist.
			while ((currentLine = input.readLine()) != null) {
					wordList.add(currentLine);
				}
			
				isSuccess = true;
			} 
			catch (IOException e) {
				e.printStackTrace();
				isSuccess = false;
			} 
			// Try closing the bufferedreader.
			finally {
					try {
						if (input != null){
							input.close();
							recognition.setVocabulary(wordList, false);
						}
					}	 
					catch (IOException | CallError | InterruptedException ex) {
						ex.printStackTrace();
						isSuccess = false;
					}
			}
		
		return isSuccess;
	}
	
	public static void main(String[] args){
		Application application;
	    ALMemory alMemory;
	    ALSpeechRecognition alSpeechRecognition;
	    String APP_NAME = "VocabTest";
	    
	    application = new Application(args, RobotConfig.getConnectionString());
		
		try {
            application.start();
            alMemory = new ALMemory(application.session());
            alSpeechRecognition = new ALSpeechRecognition(application.session());
            Vocabulary.Load(alSpeechRecognition);
            alSpeechRecognition.subscribe(APP_NAME);
                       
            final long wordRecEventID = alMemory.subscribeToEvent("WordRecognized",
            		(value)->{
							System.out.println("Robot heard ::: " + value.toString());
            		});
            
            alMemory.subscribeToEvent("MiddleTactilTouched",
                    (touched)->{
                    	if ((float)touched > 0) {
                    		alMemory.unsubscribeToEvent(wordRecEventID);
                    		alSpeechRecognition.unsubscribe(APP_NAME);
                    		application.stop();
                        }
                    });
            
            application.run();
		}
		catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
