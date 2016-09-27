package edu.sru.thangiah.nao.demo.storytelling.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;

public class OutputGUI {

	private JFrame frmStory;
	private JTextField txtWord;
	private JTextArea txtrStory;
	private JPanel panel;
	private JPanel panel_1;

	public OutputGUI() {		
		initialize();
		frmStory.setSize(1440, 900);
		frmStory.setVisible(true);	
	}
	
	/** Append text to the story output box.
	 * @param s The text to append.
	 */
	public void appendStory(String s){
		if(s.equalsIgnoreCase("Option")){
			txtrStory.setText(txtrStory.getText() + "\n" + s);
		}
		else {
			txtrStory.setText(txtrStory.getText() + " " + s);
		}
			
		txtrStory.update(txtrStory.getGraphics());
	}
	
	
	/**
	 * Clear the story telling output box.
	 */
	public void clearStory(){
		txtrStory.setText("");
		txtrStory.update(txtrStory.getGraphics());
	}
	
	/** Set the current spoken word
	 * @param word The word the robot is speaking.
	 */
	public void setWord(String word){
		txtWord.setText(word);
		txtWord.update(txtWord.getGraphics());
	}
	
	/** Get the current word in the word box.
	 * @return The current word in the word box.
	 */
	public String getWord(){
		return txtWord.getText();
	}
	
	/** Gets the story display box current text.
	 * @return The story text.
	 */
	public String getStory(){
		return txtrStory.getText();
	}
	
	/** Gets the JFrame of the output GUI.
	 * @return JFrame of the output GUI.
	 */
	public JFrame getFrame(){return frmStory;}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStory = new JFrame();
		frmStory.setBackground(Color.WHITE);
		frmStory.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		
		panel = new JPanel();
		panel.setBorder(null);
		panel.setBackground(Color.WHITE);
		frmStory.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill][grow 15,fill]"));
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "The Story", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[grow,fill]", "[grow,fill]"));
		
		txtrStory = new JTextArea();
		txtrStory.setWrapStyleWord(true);;
		txtrStory.setLineWrap(true);
		txtrStory.setEditable(false);
		txtrStory.setText("Click run to start...");
		txtrStory.setFont(new Font("Monospaced", Font.PLAIN, 30));
		panel_1.add(txtrStory, "cell 0 0");
		
		txtWord = new JTextField();
		txtWord.setEditable(false);
		txtWord.setText("WORD");
		txtWord.setForeground(Color.BLUE);
		txtWord.setFont(new Font("Tahoma", Font.PLAIN, 30));
		txtWord.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(txtWord, "cell 0 1,growx");
		txtWord.setColumns(10);
	}
}
