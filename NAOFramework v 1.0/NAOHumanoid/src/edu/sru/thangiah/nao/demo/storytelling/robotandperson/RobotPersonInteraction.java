package edu.sru.thangiah.nao.demo.storytelling.robotandperson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAnimatedSpeech;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALSpeechRecognition;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.DebugSettings;
import edu.sru.thangiah.nao.awareness.Awareness;
import edu.sru.thangiah.nao.awareness.enums.EngagementModes;
import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.demo.storytelling.StoryElementNode;
import edu.sru.thangiah.nao.demo.storytelling.XML.StoryFromXML;
import edu.sru.thangiah.nao.demo.storytelling.gui.IBroadcast;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;
import edu.sru.thangiah.nao.system.MemoryHelper;

/**
 * @author Justin Cather
 *
 */

public class RobotPersonInteraction extends Thread implements IBroadcast{
	
	private static final String STORY = "personToRobot";
	private static final String PORT = "9559";
	private static final String[] PHRASES = {
			"option one", "option two", "option three", 
			"repeat options", "repeat story", "stop",
			"option one please", "option two please", "option three please", 
			"repeat options please", "repeat story please", "stop please" 
			};
	
	private ALSpeechRecognition rec;
	private ALAnimatedSpeech atts;
	private ALTextToSpeech tts;
	private ALMemory memory;
	private Awareness awareness;
	private Session session;
	private EventPair wordHeard;
	private EventPair personNear;	
	private EventPair pause;
	private EventPair resume;
	private StoryElementNode[] currentNode;
	private Connect connect;
	
	private volatile boolean isPaused = false;
	private volatile boolean isListening = false;
	private volatile boolean nodeChanged = false;
	private volatile boolean isRunning = false;
	private volatile int currentOption;
	private volatile String currentHeard;
	private volatile States state;
			
	public RobotPersonInteraction(String ip, String xmlLocation) throws Exception{
		//this.session = new Session("tcp://" + ip + ":" + PORT);
		connect = new Connect(ip);
		connect.run();
		this.session = connect.getSession();
		
		currentNode = new StoryElementNode[1];
		currentNode[0] = new StoryFromXML(xmlLocation).getStartNode();
		wordHeard = new EventPair();
		personNear = new EventPair();
		
		pause = new EventPair();
		pause.eventName = NaoEvents.FrontTactilTouched;
		resume = new EventPair();
		resume.eventName = NaoEvents.RearTactilTouched;
		
		// NAO stuff.
		awareness = new Awareness(session);
		rec = new ALSpeechRecognition(session);
		memory = new ALMemory(session);
		atts = new ALAnimatedSpeech(session);
		tts = new ALTextToSpeech(session);
		tts.setParameter("speed", 50f);
		tts.setParameter("pitchShift", 1.0f);
		
		// Set the vocabulary
		ArrayList<String> wordsForNAO = new ArrayList<String>(Arrays.asList(PHRASES));
		rec.setVocabulary(wordsForNAO, false);
		
		wordHeard.eventName = NaoEvents.WordRecognized;	
		personNear.eventName = NaoEvents.EngagementZones_PersonEnteredZone1;
		currentOption = -1;
	}
	
	/** Checks if the Nao is speaking currently.
	 * @return True if speaking, false if not.
	 */
	public boolean isTalking(){
		return MemoryHelper.isTalking(session);
	}
	
	/** Checks if the story telling is over or not.
	 * @return True if story is being told, false if not.
	 */
	public boolean isRunning(){
		return isRunning;
	}
	
	@Override
	public String getStoryText(){
		return currentNode[0].getStoryText();
	}
	
	@Override
	public String getOptions(){
		String options = "";
		Iterator<String> i = currentNode[0].getOptions();
		int count = 1;
		
		while(i.hasNext()){
			options += "Option " + count + ": " + i.next() + "\n";
			count++;
		}
		
		return options;
	}
	
	@Override
	public boolean storyChanged(){
		return nodeChanged;
	}
	
	@Override
	public void updatedStory(){
		nodeChanged = false;
	}
	
	@Override
	public String currentWord(){
		return MemoryHelper.getCurrentTTSWord(session);
	}
	
	/**
	 * The current state that the story FSM is in.
	 * @return The current state of the FSM.
	 */
	public States getCurrentState(){
		return state;
	}
	
	private boolean getIsListening(){
		return this.isListening;
	}
	
	private void setIsListening(boolean b) throws CallError, InterruptedException{
		this.isListening = b;
		this.rec.pause(!this.isListening);
	}
	
	public void run(){
		try {
			atts.setBodyLanguageMode(2);
			awareness.setEngagementMode(EngagementModes.FullyEngaged);
			awareness.setFirstZoneDistance(0.45f);
			
			// init the story.
			rec.subscribe(STORY);
			Thread.sleep(500);
			//rec.pause(true);
			this.setIsListening(false);
			Thread.sleep(1000);
			state = States.Start;
			
			personNear.eventID = memory.subscribeToEvent(personNear.eventName, (value) -> {
				if (isRunning && state != States.IsNear) {
					System.out.println("Entered to near");
					synchronized (rec) 
					{			
						if (this.isTalking())
						{
							tts.stopAll();
							
							while (this.isTalking())
							{
								Thread.sleep(500);	
								System.out.println("Still talking...");
							}
						}
						
						if (this.getIsListening())
						{
							this.setIsListening(false);
							Thread.sleep(500);
						}
						
						state = States.IsNear;					
						//Thread.sleep(500);
						tts.say("You're a little close to me. Try moving away and I'll continue.");
						Thread.sleep(2500);
						
						while (awareness.getPeopleInZone1().size() > 0) {
							state = States.IsNear;
							tts.say("You're a little close to me. Try moving away and I'll continue.");		
							Thread.sleep(2500);
						}

						tts.say("Thanks for moving away. Lets continue the story.");
						state = States.ReadStory;
					}
				}
			});
			
			wordHeard.eventID = memory.subscribeToEvent(wordHeard.eventName, (value) -> {
				if (state == States.WaitingForInput || state == States.KillTime){
					DebugSettings.printDebug(DebugSettings.COMMENT, "Heard: " + value.toString() + " from " + Thread.currentThread());
					
					// Pause recognition
					//rec.pause(true);
					this.setIsListening(false);
					
					// getting data from event
					currentOption = -1;
					currentHeard = ((ArrayList) value).get(0).toString();
					float confidence = (float)((ArrayList) value).get(1);
					
					// check the input and set next state.
					if(confidence < 0.5f){
						state = States.LowConf;
					} 
					else if (currentHeard.indexOf("please") == -1) {
						state = States.NoPlease;
					} 
					else {
						switch (currentHeard) {
						case "option one please":
							currentOption = 0;
							atts.say("Thank you.");
							state = States.AdvanceStory;
							break;

						case "option two please":
							currentOption = 1;
							atts.say("Thank you.");
							state = States.AdvanceStory;
							break;

						case "option three please":
							currentOption = 2;
							atts.say("Thank you.");
							state = States.AdvanceStory;
							break;

						case "repeat options please":
							atts.say("No problem. Here is the options again.");
							state = States.ReadOptions;
							break;

						case "repeat story please":
							atts.say("No problem. Here is the story again.");
							state = States.ReadStory;
							break;
							
						case "stop please":
							state = States.EndStory;
							break;

						default:
							currentOption = -1;
							state = States.WaitingForInput;
							break;
						}
					}
					
				} // waiting for input //
				else {
					//rec.pause(true);
					this.setIsListening(false);
				}
				
			}); // end of NAO event //
			
			pause.eventID = memory.subscribeToEvent(pause.eventName, value -> {
				if (((Float) value) > 0.0) {
					synchronized (rec) {
						this.state = States.Pause;
						
						if (this.isTalking()) {
							tts.stopAll();

							while (this.isTalking()) {
								Thread.sleep(500);
								System.out.println("Still talking...");
							}
						}

						if (this.getIsListening()) {
							this.setIsListening(false);
							Thread.sleep(500);
						}
					}
				}
			});
			
			resume.eventID = memory.subscribeToEvent(resume.eventName, value -> {
				if (((Float) value) > 0.0 && state == States.Pause) {
					state = States.Resume;
				}
			});
			
			isRunning = true;
			this.storyHandler();
		} catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
		}
	}
	
	/** FSM for handling story stage. An infinite loop until the end of the story is reached.
	 * @throws Exception
	 */
	private void storyHandler() throws Exception{
		while (isRunning) {		
			switch (state) {
				case AdvanceStory:
					this.advanceStory(currentOption);
					break;
	
				case LowConf:
					this.badInput();
					break;
	
				case NoPlease:
					this.badInput();
					break;
					
				case IsNear:
					this.badInput();
					break;
	
				case ReadOptions:
					this.readOptions();
					break;
	
				case ReadStory:
					this.readStory();
					break;
	
				case Start:
					state = States.ReadStory;
					break;
	
				case WaitingForInput:
					this.waitForInput();
					break;
					
				case KillTime:
					this.waitForInput();
					break;
					
				case Resume:
					state = States.ReadStory;
					break;
					
				case Pause:
					/* Do Nothing */
					break;
	
				case EndStory:
					//rec.pause(true);
					this.setIsListening(false);
					rec.unsubscribe(STORY);
					memory.unsubscribeToEvent(wordHeard.eventID);
					memory.unsubscribeToEvent(personNear.eventID);
					atts.say("That's the end of the story. I hope you liked it!");
					tts.resetSpeed();
					tts.setParameter("pitchShift", 0f);
					isRunning = false;
	
				default:
					this.badInput();
					break;

			}
		}
	}
	
	/** Reads the story text and checks to continue or not.
	 * @throws Exception
	 */
	private void readStory() throws Exception{
		if (state == States.ReadStory) {
			DebugSettings.printDebug(DebugSettings.COMMENT, "Entering readStory with " + Thread.currentThread());
			nodeChanged = true;
			
			atts.say(currentNode[0].repeatText());
			/*atts.async().say(currentNode[0].repeatText());
			Thread.sleep(500);
			while(isTalking()){
				Thread.sleep(500);
			}*/
			
			if (state == States.ReadStory) {
				synchronized (state) {
					if (currentNode[0].getNumberOfNodes() > 1) {
						state = States.ReadOptions;
					} else if (currentNode[0].getNumberOfNodes() == 1) {
						currentOption = 0;
						state = States.AdvanceStory;
					} else {
						state = States.EndStory;
					}
				}
			}
		}
	}
	
	/** Reads story options.
	 * @throws Exception
	 */
	private void readOptions() throws Exception{
		if (state == States.ReadOptions) {
			DebugSettings.printDebug(DebugSettings.COMMENT, "Entering readOptions with " + Thread.currentThread());
			
			atts.say(currentNode[0].repeatOptions());
			/*atts.async().say(currentNode[0].repeatOptions());
			Thread.sleep(500);
			while(isTalking()){
				Thread.sleep(500);
			}*/
			
			if (state == States.ReadOptions) {
				synchronized (state) {
					state = States.WaitingForInput;
				}
			}
		}
	}
	
	/** Either turn recognition off, or burn CPU cycles.
	 * @throws Exception
	 */
	private void waitForInput() throws Exception{	
		if (state == States.WaitingForInput) {
			DebugSettings.printDebug(DebugSettings.COMMENT, "Entering waitForInput with " + Thread.currentThread());
			//rec.pause(false);
			this.setIsListening(true);
			
			if (state == States.WaitingForInput) {
				synchronized (state) {
					state = States.KillTime;
				}
			}
		} 
		else if (state == States.KillTime){/* wasting cycles*/}
	}
		
	/** Moves to the next story node.
	 * @param option Option to move to.
	 * @throws Exception
	 */
	private void advanceStory(int option) throws Exception{
		if (state == States.AdvanceStory) {
			// Make sure that the current node is not a last node and has options attached to it.
			if (currentNode[0].getNumberOfNodes() > option) {
				DebugSettings.printDebug(DebugSettings.COMMENT, "Entering advanceStory with " + Thread.currentThread());
				
				StoryElementNode nextNode = (StoryElementNode) currentNode[0].chooseOption(option);
				currentNode[0].destroy();
				currentNode[0] = nextNode;
				
				if (state == States.AdvanceStory) {
					synchronized (state) {
						state = States.ReadStory;
					}
				}
			} 
			else {
				atts.say("Sorry, but there is no option" + currentHeard + " for this part of the story.");
				
				synchronized (state){
					state = States.WaitingForInput;
				}
			}
		}
	}
	
	
	/** Something stopped the robot from advancing the story. 
	 *  Low confidence in a word, absence of please, or to near to robot.
	 * @throws Exception
	 */
	private void badInput() throws Exception{
		if (state == States.LowConf){
			atts.say("I'm sorry, but I'm having a hard time hearing you. "
					+ "Could you try repeating that? Thanks!");
			
			if (state == States.LowConf) {
				synchronized (state) {
					state = States.WaitingForInput;
				}
			}
		}
		else if (state == States.NoPlease){
			atts.say("You need to say please. Try saying " + currentHeard + " please.");
			
			if (state == States.NoPlease) {
				synchronized (state) {
					state = States.WaitingForInput;
				}
			}
		} 
		else if (state == States.IsNear){
			Thread.sleep(1500); // Waste time...QiMessaging thread takes care of this state.
		}
	}
}
