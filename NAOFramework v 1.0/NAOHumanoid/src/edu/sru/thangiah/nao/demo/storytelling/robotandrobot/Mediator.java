package edu.sru.thangiah.nao.demo.storytelling.robotandrobot;

import java.util.ArrayList;
import java.util.LinkedList;

import edu.sru.thangiah.nao.DebugSettings;

public class Mediator extends Thread{

	private LinkedList<SlaveNAO> slaves;
	private MasterNAO master;
	private boolean isRunning;
	
	public Mediator(MasterNAO master)
	{
		this.master = master;
		slaves = new LinkedList<SlaveNAO>();
		isRunning = false;
	}
	
	/** Checks if the story is still being told.
	 * @return True if story is still being told, false if not.
	 */
	public boolean isRunning(){ return isRunning;}
	
	/** Gets the current story text from the Master Nao.
	 * @return The text of the current node.
	 */
	public String getStoryText(){ return master.getStoryText();}
	
	/** Slaves an Nao to the master Nao.
	 * @param slave The Nao to add to slave list.
	 * @return True if added successfully, false if failed.
	 */
	public boolean addSlave(SlaveNAO slave)
	{
		if (!slaves.contains(slave)) 
		{
			slaves.add(slave);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void run()
	{
		if (slaves.size() > 0) 
		{
			isRunning = true;
		}
		else
		{
			isRunning = false;
		}
		
		while(isRunning)
		{
			ArrayList<SlaveNAO> naos = null; 
			SlaveNAO selectedSlave = null;
			int avlOptions = -1;
			int choosenOption = -1;
			
			// Read story/options.
			master.readStory();
			if (master.hasOptions()) 
			{
				avlOptions = master.readOptions();
				// Pick NAOs to raise hands.
				master.say("Raise your hand if you want to answer.");
				naos = pickSlaves();
				
				// Make sure we don't pick zero NAOs...
				while (naos.size() <= 0) {
					naos = pickSlaves();
				}
				// Raise some of their hands.
				for (SlaveNAO slave : naos)
					slave.raiseHand();
				
				// Master thinks....
				master.say("Hmmmm. Let me see...");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}
				
				// Pick a slave.
				selectedSlave = naos.get(Dice.roll(naos.size()));
				master.say("How about you " + selectedSlave.getName() + "? Which option would you like?");
				
				// Put the hands down.
				for (SlaveNAO slave : naos)
					slave.lowerHand();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {}
				
				// Slave chooses an option.
				choosenOption = selectedSlave.pickOption(avlOptions);
				
				// Check the answer.
				if (choosenOption != -1) {
					master.advanceStory(choosenOption);
				} else {
					master.say("Sorry, but that's not an option. Lets try again.");
				} 
			}
			else
			{
				master.advanceStory(0);
			}
			
			isRunning = master.endOfStory();
			// Read last part of the story.
			if(!isRunning)
			{
				master.readStory();
			}
		}
		
		master.say("That's the end of the story. I hope you liked it.");	
	}
	
	/** Pick random slaves from the list.
	 * @return A list of the randomly chosen slaves.
	 */
	private ArrayList<SlaveNAO> pickSlaves()
	{
		// pick a random number of slaves > 0.
		int size = Dice.roll(slaves.size());
		while (size < 1)
			size++;
		
		ArrayList<SlaveNAO> list =  new ArrayList<SlaveNAO>(size);
		DebugSettings.printDebug(DebugSettings.COMMENT, "Selected " + size + " slaves.");
		
		// Add random slave NAOs to the list up to size. The final size and the initial size
		// might be different if the same NAO is selected more than once.
		// i will continue to increment even if it is a duplicate, but the NAO will only be added once.
		for (int i = 0; i < size; i++)
		{
			SlaveNAO slaveToAdd = slaves.get(Dice.roll(slaves.size()));
			
			if (!list.contains(slaveToAdd))
			{
				list.add(slaveToAdd);
				DebugSettings.printDebug(DebugSettings.COMMENT, "Added " + slaveToAdd.getName() + " to raised hands.");
			}			
		}
		
		list.trimToSize();
		DebugSettings.printDebug(DebugSettings.COMMENT, "Selected final " + list.size() + " slaves.");
		return list;
	}
	
}
