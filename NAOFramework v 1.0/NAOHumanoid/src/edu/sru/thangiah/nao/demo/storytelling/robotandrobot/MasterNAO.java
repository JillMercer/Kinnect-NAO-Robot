package edu.sru.thangiah.nao.demo.storytelling.robotandrobot;

import java.util.Iterator;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAnimatedSpeech;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.DebugSettings;
import edu.sru.thangiah.nao.demo.storytelling.StoryElementNode;
import edu.sru.thangiah.nao.demo.storytelling.XML.StoryFromXML;
import edu.sru.thangiah.nao.demo.storytelling.gui.IBroadcast;
import edu.sru.thangiah.nao.speech.texttospeech.TextToSpeech;
import edu.sru.thangiah.nao.system.MemoryHelper;

public class MasterNAO implements IBroadcast{

	public static String PORT = "9559";
	
	private StoryElementNode node;
	private ALTextToSpeech tts;
	private ALAnimatedSpeech atts;
	private Session session;
	private boolean nodeChanged = false;
	
	public MasterNAO(String ip, String xml) throws Exception
	{
			session = new Session("tcp://" + ip + ":" + PORT);
			tts = new ALTextToSpeech(session);
			atts = new ALAnimatedSpeech(session);
			node = new StoryFromXML(xml).getStartNode();
			
			tts.setParameter("speed", 50f);
			tts.setParameter("pitchShift", 1.0f);
	}
	
	@Override
	public String currentWord(){
		return MemoryHelper.getCurrentTTSWord(session);
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
	public String getStoryText(){
		return node.getStoryText();
	}
	
	@Override
	public String getOptions(){
		String options = "";
		Iterator<String> i = node.getOptions();
		int count = 1;
		
		while(i.hasNext()){
			options += "Option " + count + ": " + i.next() + "\n";
			count++;
		}
		
		return options;
	}
	
	/** Gets the name of the current node of the story.
	 * @return The name of the current node.
	 */
	public String getNodeName()
	{
		return node.getNodeName();
	}
	
	/** Master will say the given text.
	 * @param text The text for the master to say.
	 */
	public void say(String text)
	{
		try {
			atts.say(text);
		} catch (CallError | InterruptedException e) {
			DebugSettings.printDebug(DebugSettings.ERROR, "From Master: " + e.getMessage());
		}
		DebugSettings.printDebug(DebugSettings.COMMENT, "From Master: " + text);
	}
	
	/** Check if the current node is a final node.
	 * @return True if at end of the story, false if not.
	 */
	public boolean endOfStory()
	{
		return node.getNumberOfNodes() > 0;
	}
	
	/** Checks if the current node has at least one option.
	 * @return
	 */
	public boolean hasOptions()
	{
		return node.getNumberOfNodes() > 1;
	}
	
	/**
	 *  The master will speak the current nodes story.
	 */
	public void readStory()
	{
		try {
			atts.say(node.repeatText());
		} catch (CallError | InterruptedException e) {
			DebugSettings.printDebug(DebugSettings.ERROR, "From Master: " + e.getMessage());
		}
		DebugSettings.printDebug(DebugSettings.COMMENT, "From Master: " + node.repeatText());
	}
	
	/** The master will speak the current nodes options.
	 * @return The number of options available at this node.
	 */
	public int readOptions()
	{
		try {
			atts.say(node.repeatOptions());
		} catch (CallError | InterruptedException e) {
			DebugSettings.printDebug(DebugSettings.ERROR, "From Master: " + e.getMessage());
		}
		DebugSettings.printDebug(DebugSettings.COMMENT, "From Master: " + node.repeatOptions());
		return node.getNumberOfNodes();
	}
	
	/** Progresses the story to the chosen node.
	 * @param option The option to progress to.
	 * @return True if story progressed, false if it failed.
	 */
	public boolean advanceStory(int option)
	{
		if(node.getNumberOfNodes() > option && option > -1)
		{
			StoryElementNode nextNode = (StoryElementNode) node.chooseOption(option);
			node.destroy();
			node = nextNode;
			nodeChanged = true;
			return true;
		}
		else
		{
			return false;
		}
	}
}
