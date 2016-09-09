package edu.sru.thangiah.nao.demo.storytelling.gui;

public interface IBroadcast {
	
	
	/** Method used to update current word display.
	 * @return Broadcasters current word.
	 */
	public String currentWord();
	
	/** Method used to broadcast that the story changed.
	 * @return True if story changed, false if not.
	 */
	public boolean storyChanged();
	
	/** Method used to let broadcaster know that the 
	 *  story update has been acknowledged.
	 */
	public void updatedStory();
	
	/** Method used to update story display.
	 * @return Broadcasters current story.
	 */
	public String getStoryText();

	/** Method used to get current options.
	 * @return A listing of options.
	 */
	public String getOptions();
}
