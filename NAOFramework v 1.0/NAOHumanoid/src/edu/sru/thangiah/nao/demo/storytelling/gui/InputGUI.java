package edu.sru.thangiah.nao.demo.storytelling.gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EtchedBorder;

import edu.sru.thangiah.nao.DebugSettings;
import edu.sru.thangiah.nao.connection.Network;
import edu.sru.thangiah.nao.demo.storytelling.robotandperson.RobotPersonInteraction;
import edu.sru.thangiah.nao.demo.storytelling.robotandrobot.MasterNAO;
import edu.sru.thangiah.nao.demo.storytelling.robotandrobot.Mediator;
import edu.sru.thangiah.nao.demo.storytelling.robotandrobot.SlaveNAO;

public class InputGUI {

	private static final String PATH_TO_XML = System.getProperty("user.dir") + "\\src\\edu\\sru\\thangiah\\nao\\demo\\storytelling\\Stories\\";
	
	// Swing stuff
	private JFrame frmStoryTelling;
	private JTextField masterInput;
	private DefaultListModel<String> xmlList;
	private DefaultListModel<String> slaveNaoList;
	private DefaultListModel<String> naoList;	
	
	// My Vars
	private boolean isMasterSlaveStyle;
	private OutputGUI outputGUI;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					InputGUI window = new InputGUI();
					window.frmStoryTelling.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public InputGUI() {
		this.isMasterSlaveStyle = false;
		this.xmlList = new DefaultListModel<String>();
		this.naoList = new DefaultListModel<String>();
		this.slaveNaoList = new DefaultListModel<String>();
		this.outputGUI = new OutputGUI();
		this.outputGUI.getFrame().setVisible(false);;
		
		this.fillXMLBox();
		this.fillSlaveBox();
		initialize();
	}
	
	/**
	 * Scans the network for Naos and fills the Naos on Network list.
	 */
	private void fillSlaveBox(){
		HashMap<String, String> naos;
		Network network = new Network();
		
		try {
			naos = network.searchForNAOs();
			for(Entry<String, String> pair : naos.entrySet()){
				naoList.addElement(pair.getKey() + " " + pair.getValue());
			}
			
			//naoList.addElement("Aegon" + " " + "192.168.0.103");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Scans the PATH_TO_XML directory for story XML files.
	 */
	private void fillXMLBox(){
		File f = new File(PATH_TO_XML);
		String[] files = f.list();

		for (int i = 0; i < files.length; i++){
			xmlList.addElement(files[i]);
		}
	}
	
	/** Get the IP address of the Nao in the Master box.
	 * @return IP address for the Master Nao.
	 */
	private String getMasterIP(){
		String ip = masterInput.getText().trim();
		ip = ip.split(" ")[1];
		return ip;
	}
	
	/** Filters out "\\" from a string.
	 * @param word The text to filter.
	 * @return The filtered text.
	 */
	private boolean wordFilter(String word){
		return word.startsWith("\\");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmStoryTelling = new JFrame();
		frmStoryTelling.setResizable(false);
		frmStoryTelling.setTitle("Story Telling");
		frmStoryTelling.setBounds(100, 100, 598, 274);
		frmStoryTelling.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frmStoryTelling.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JList<String> slaveNaosBox = new JList<String>(slaveNaoList);
		slaveNaosBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		slaveNaosBox.setEnabled(false);
		slaveNaosBox.setBounds(183, 68, 163, 135);
		panel.add(slaveNaosBox);
		slaveNaosBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JButton addToSlaves = new JButton("->");
		addToSlaves.setToolTipText("Move selected to slaves.");
		addToSlaves.setEnabled(false);
		addToSlaves.setBounds(128, 214, 45, 23);
		panel.add(addToSlaves);
		
		JButton removeFromSlaves = new JButton("<-");
		removeFromSlaves.setToolTipText("Remove selected from slaves.");
		removeFromSlaves.setEnabled(false);
		removeFromSlaves.setBounds(183, 214, 45, 23);
		panel.add(removeFromSlaves);
		
		JList<String> xmlStoriesBox = new JList<String>(xmlList);
		xmlStoriesBox.setBounds(362, 68, 203, 135);
		panel.add(xmlStoriesBox);
		xmlStoriesBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		xmlStoriesBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		xmlStoriesBox.setSelectedIndex(0);
		
		JList<String> networkNaosBox = new JList<String>(naoList);
		networkNaosBox.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		networkNaosBox.setEnabled(true);
		networkNaosBox.setBounds(10, 68, 163, 135);
		panel.add(networkNaosBox);
		networkNaosBox.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblMaster = new JLabel("Master");
		lblMaster.setHorizontalAlignment(SwingConstants.CENTER);
		lblMaster.setBounds(79, 11, 197, 14);
		panel.add(lblMaster);
		
		JLabel lblSlaveNaos = new JLabel("Slave NAOs");
		lblSlaveNaos.setHorizontalAlignment(SwingConstants.CENTER);
		lblSlaveNaos.setBounds(183, 53, 163, 14);
		panel.add(lblSlaveNaos);
		lblSlaveNaos.setLabelFor(slaveNaosBox);
		
		JLabel lblNaosOnNetwork = new JLabel("NAOs On Network");
		lblNaosOnNetwork.setHorizontalAlignment(SwingConstants.CENTER);
		lblNaosOnNetwork.setBounds(10, 53, 163, 14);
		panel.add(lblNaosOnNetwork);
		
		JLabel lblXmlStories = new JLabel("XML Stories");
		lblXmlStories.setHorizontalAlignment(SwingConstants.CENTER);
		lblXmlStories.setBounds(362, 53, 203, 14);
		panel.add(lblXmlStories);
		lblXmlStories.setLabelFor(lblXmlStories);
		
		masterInput = new JTextField();
		masterInput.setEditable(false);
		masterInput.setBounds(79, 24, 203, 20);
		panel.add(masterInput);
		masterInput.setColumns(10);
		lblMaster.setLabelFor(masterInput);
		
		JCheckBox chckbxUseMasterslave = new JCheckBox("Use Master/Slave");
		chckbxUseMasterslave.setToolTipText("Toggle for master/slave style story \r\nand robot-to-person story.\r\n\r\n");
		chckbxUseMasterslave.setBounds(288, 23, 163, 23);
		panel.add(chckbxUseMasterslave);
		
		JButton btnRun = new JButton("Run");
		btnRun.setToolTipText("Run the story with the current configuration.");
		btnRun.setBounds(476, 214, 89, 23);
		panel.add(btnRun);
		
		JButton btnMakeMaster = new JButton("Make Master");
		btnMakeMaster.setToolTipText("Make the selected Nao the master.");
		btnMakeMaster.setBounds(10, 214, 108, 23);
		panel.add(btnMakeMaster);
		
		JToggleButton tglbtnShowhideWindow = new JToggleButton("Show / Hide");
		tglbtnShowhideWindow.setToolTipText("Show or hide the output window.");
		tglbtnShowhideWindow.setBounds(362, 214, 107, 23);
		panel.add(tglbtnShowhideWindow);
		
		////////////////////// Events //////////////////////
		////////////////////////////////////////////////////
		
		tglbtnShowhideWindow.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED){
					outputGUI.getFrame().setVisible(true);
				}
				else {
					outputGUI.getFrame().setVisible(false);
				}
			}
		});
		
		btnMakeMaster.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(networkNaosBox.isSelectionEmpty()){
					MessageBox.show("A Nao must be highlighted in the network box to make it the master.", "No Nao Selected");
				}
				else {
					if(masterInput.getText().isEmpty()){
						int index = networkNaosBox.getSelectedIndex();			
						masterInput.setText(naoList.getElementAt(index));
						naoList.remove(index);
					}
					else {
						String temp = masterInput.getText();
						int index = networkNaosBox.getLeadSelectionIndex();
						
						masterInput.setText(naoList.getElementAt(index));
						naoList.remove(index);
						naoList.addElement(temp);
					}
				}
				
			}
			
		});
		
		// Add slave button clicked.
		addToSlaves.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = networkNaosBox.getSelectedIndex();
				if (index != -1){
					slaveNaoList.addElement(naoList.remove(index));
				}
			}
		}); // end add slave button clicked.
		
		// Removed Slave button clicked.
		removeFromSlaves.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int index = slaveNaosBox.getSelectedIndex();
				if (index != -1){
					naoList.addElement(slaveNaoList.remove(index));
				}
				
			}		
		}); // end remove slave button clicked.
		
		// Master/Slave checkbox.
		chckbxUseMasterslave.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				// IF checked enable use of the Network - > Slave boxes.
				if (e.getStateChange() == ItemEvent.SELECTED){
					slaveNaosBox.setEnabled(true);
					removeFromSlaves.setEnabled(true);
					addToSlaves.setEnabled(true);
					
					isMasterSlaveStyle = true;
				}
				else {
					slaveNaosBox.setEnabled(false);
					removeFromSlaves.setEnabled(false);
					addToSlaves.setEnabled(false);
					
					isMasterSlaveStyle = false;
				}
			}
		}); // end master/slave checkbox.
		
		// Run button clicked
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {			
				// Need a master to do anything.
				if(masterInput.getText().isEmpty()){
					MessageBox.show("Must put an IP in master text box.", "No Master Error");
				}
				else{
					// Make the output window visible.
					outputGUI.getFrame().setVisible(true);
					
					if(isMasterSlaveStyle){
					// Using a master and slave style story. //
					//////////////////////////////////////////
						if(slaveNaoList.size() > 0){
							MasterNAO master = null;
							try {
								master = new MasterNAO(getMasterIP(), PATH_TO_XML + xmlStoriesBox.getSelectedValue());
							} catch (Exception e2) {
								outputGUI.getFrame().setVisible(false);
								MessageBox.show("Error connecting to Nao. " + e2.getMessage(), "Error connecting to Nao.");
							}
							Mediator mediator = new Mediator(master);
							String currentWord = "";
							
							// Get the slaves. Split on " " to separate the name and IP.
							for(int i = 0; i < slaveNaoList.size(); i++){
								String[] seperated = slaveNaoList.getElementAt(i).split(" ");
								SlaveNAO slave = new SlaveNAO(seperated[0],seperated[1]);
								mediator.addSlave(slave);	
							}
							
							// Start thread...waste time until it starts.
							mediator.start();
							while (!mediator.isRunning()){}
							
							// While run update the output window with story text.
							while(mediator.isRunning()){
								
								// Update current word displayed.
								currentWord = master.currentWord();
								if ( !wordFilter(currentWord) && !currentWord.equals(outputGUI.getWord()) ){
									outputGUI.appendStory(outputGUI.getWord());
									outputGUI.setWord(currentWord);
								}
								
								// Update the story display.
								if(master.storyChanged()){
									master.updatedStory();
									outputGUI.clearStory();
								}
							}
							
							// Story over. Join threads and hide output window.
							try {
								mediator.join();
								outputGUI.getFrame().setVisible(false);
							} 
							catch (InterruptedException e1) {
								MessageBox.show(e1.getMessage(), "Error");
								DebugSettings.printDebug(DebugSettings.ERROR, e1.getMessage());
							}
							
						}
						else{
							MessageBox.show("Must have at least one slave.", "No slaves selected");
						}
						
					}
					else{
					// Using a Robot and person type story. //
					//////////////////////////////////////////
						try {
							RobotPersonInteraction robotPerson = new RobotPersonInteraction(getMasterIP(), 
									PATH_TO_XML + xmlStoriesBox.getSelectedValue());
							String currentWord = "";
							
							// Start the thread...waste time until it starts.
							robotPerson.start();
							while(!robotPerson.isRunning()){}
							
							// While running, update the output window with story text.
							while(robotPerson.isRunning()){
								
								// Update current word displayed.
								currentWord = robotPerson.currentWord();
								if ( !wordFilter(currentWord) && !currentWord.equals(outputGUI.getWord()) ){
									outputGUI.appendStory(outputGUI.getWord());
									outputGUI.setWord(currentWord);
								}
								
								// Update the story display.
								if(robotPerson.storyChanged()){							
									robotPerson.updatedStory();
									outputGUI.clearStory();
								}				
							}
							
							// Story over. Join threads and hide output window.
							robotPerson.join();
							robotPerson = null;
							outputGUI.getFrame().setVisible(false);						
						} 
						catch (Exception e1) {
							outputGUI.getFrame().setVisible(false);
							MessageBox.show(e1.getMessage(), "Error");
							e1.printStackTrace();
						}
					}
				}
				
			}
		}); // end run button clicked.
	}
}
