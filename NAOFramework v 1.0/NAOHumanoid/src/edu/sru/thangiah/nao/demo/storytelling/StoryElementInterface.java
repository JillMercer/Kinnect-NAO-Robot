package edu.sru.thangiah.nao.demo.storytelling;

import java.util.Iterator;

public abstract interface StoryElementInterface<T> {
	
	/** Repeat the options of the current story part.
	 * @return A string of the options of this story part.
	 */
	public String repeatOptions();
	
	/** Repeat the text of the current story part.
	 * @return A string of the current story part text.
	 */
	public String repeatText();
	
	/** Progresses the story to optionNumber.
	 * @param optionNumber The branch in the story to take.
	 * @return The next part of the story.
	 */
	public T chooseOption(int optionNumber);
	
	/** Progresses the story to optionName
	 * @param optionName The name of branch to take.
	 * @return The next part of the story.
	 */
	public T chooseOption(String optionName);
	
	/** Simply gets the text of this part of the story.
	 * @return String of this part of the story text.
	 */
	public String getStoryText();
	
	/** Sets the story text for this part of the story.
	 * @param storyText The text to set the story to.
	 */
	public void setStoryText(String storyText);
	
	/** Adds an option or branch to this part of the story.
	 * @param option The name of the option to add.
	 * @param optionElement The linked part of the story.
	 * @throws Exception
	 */
	public void addOption(String option, T optionElement) throws Exception;
	
	/** Adds an option to this part of the story.
	 * @param option The option to add.
	 */
	public void addOption(String option);
	
	/** Get the options that are available at this part of the story.
	 * @return An iterator of the options.
	 */
	public Iterator<String> getOptions();
	
}
