package edu.sru.brian.tictactoegame.decisiontree;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;

import javax.swing.SwingConstants;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JToggleButton;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import edu.sru.brian.tictactoegame.Game;
import edu.sru.brian.tictactoegame.Markers;
import edu.sru.brian.tictactoegame.TicTacToeBoardGUI;

/**
 * File:TreeNodeMarkerV2.java
 * @author Brian Atwell
 * Description: Allows editing of an xml throug a GUI.
 *
 */
public class TreeNodeMarkerV2 extends JFrame {

	private JPanel contentPane;
	private JToolBar toolBar;
	private JToggleButton btnSelect;
	private JToggleButton btnNewNode;
	private JToggleButton btnDeleteNode;
	private JToggleButton btnMove;
	private LinkPanel panel;
	private TicTacToeBoardGUI boardFrame;
	private Game game;
	
	private int x;
    private int y;
    private boolean isMoveDragStarted =false;
    private TOOLSTATE curToolState;
    private NodePanel startNode;
    private ArrayList<NodePanel> aryNodePanel = new ArrayList<NodePanel>();
    private Hashtable<NodePanel, NodePanel> links = new Hashtable<NodePanel, NodePanel>();
    private NodePanel startLink=null;
    private NodePanel activeNodePanel=null;
    private ArrayList<NodePanel> nodeSelList = new ArrayList<NodePanel>();
    private JPanel panel_1;
    private JMenuBar menuBar;
    private String fileName=null;
    private JFileChooser fileChooser = new JFileChooser();
    private boolean areNodesDraggable=true;
    
    public static String TEST_XML = System.getProperty("user.dir") + "\\src\\edu\\sru\\brian\\tictactoegame\\decisiontree\\data\\testtree.xml";
    public static int MAX_PARENT_SEARCH = Game.MAX_POSITIONS+1;
    private JToggleButton tglbtnDetermineStatus;
    
    
    public enum TOOLSTATE {
    	SELECT, NEWNODE, DELETENODE, MOVE, NEWLINK, DELETELINK, DETERMINESTATUS
    };

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreeNodeMarkerV2 frame = new TreeNodeMarkerV2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TreeNodeMarkerV2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 461);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		menuBar = new JMenuBar();
		panel_1.add(menuBar, BorderLayout.NORTH);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mntmNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onNewFile();
			}
			
		});
		mnFile.add(mntmNew);
		
		JMenuItem mntmOpen = new JMenuItem("Open");
		mntmOpen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onOpenFile();
			}
			
		});
		mnFile.add(mntmOpen);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onSaveFile();
			}
			
		});
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As..");
		mntmSaveAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onSaveFileAs();
			}
			
		});
		mnFile.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				onExitBtn();
			}
			
		});
		mnFile.add(mntmExit);
		
		toolBar = new JToolBar();
		panel_1.add(toolBar);
		
		btnSelect = new JToggleButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startLink = null;
				panel.setStartNode(null);
				setToggleButton(TOOLSTATE.SELECT);
				setNodesDraggable(true);
			}
		});
		toolBar.add(btnSelect);
		
		btnNewNode = new JToggleButton("New Node");
		btnNewNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startLink = null;
				panel.setStartNode(null);
				setToggleButton(TOOLSTATE.NEWNODE);
				setNodesDraggable(false);
			}
		});
		toolBar.add(btnNewNode);
		
		btnDeleteNode = new JToggleButton("Delete Node");
		btnDeleteNode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startLink = null;
				panel.setStartNode(null);
				setToggleButton(TOOLSTATE.DELETENODE);
				setNodesDraggable(false);
			}
		});
		toolBar.add(btnDeleteNode);
		
		btnMove = new JToggleButton("Move");
		btnMove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				startLink = null;
				panel.setStartNode(null);
				setToggleButton(TOOLSTATE.MOVE);
				setNodesDraggable(false);
			}
		});
		toolBar.add(btnMove);
		
		ButtonGroup group = new ButtonGroup();
		
		JToggleButton tglbtnNewLink = new JToggleButton("New Link");
		tglbtnNewLink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setToggleButton(TOOLSTATE.NEWLINK);
				setNodesDraggable(false);
			}
		});
		toolBar.add(tglbtnNewLink);
		
		JToggleButton tglbtnDeleteLink = new JToggleButton("Delete Link");
		tglbtnDeleteLink.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				startLink = null;
				panel.setStartNode(null);
				setToggleButton(TOOLSTATE.DELETELINK);
				setNodesDraggable(false);
			}
			
		});
		toolBar.add(tglbtnDeleteLink);
		
		tglbtnDetermineStatus = new JToggleButton("Determine Status");
		tglbtnDetermineStatus.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setToggleButton(TOOLSTATE.DETERMINESTATUS);
				setNodesDraggable(false);
			}
		});
		toolBar.add(tglbtnDetermineStatus);
		
		group.add(btnSelect);
		group.add(btnNewNode);
		group.add(btnDeleteNode);
		group.add(btnMove);
		group.add(btnMove);
		group.add(tglbtnNewLink);
		group.add(tglbtnDeleteLink);
		group.add(tglbtnDetermineStatus);
		
		group.setSelected(btnSelect.getModel(), true);
		
		panel = new LinkPanel(links);
		panel.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				panelMouseClick(arg0);
				startLink = null;
				panel.setStartNode(null);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.MOVE)
				{
					isMoveDragStarted=true;
					x=e.getX();
					y=e.getY();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.MOVE)
				{
					isMoveDragStarted=false;
				}
			}
		});
		panel.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.MOVE && isMoveDragStarted)
				{
					onPanPanel(arg0);
				}
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.NEWLINK)
				{
					panel.setMousePosition();
					
				}
			}
			
		});
		
		startNode = new NodePanel(this, "Start", 10, 10);
		setNodePanelListeners(startNode);
		/*
		startNode.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.NEWLINK)
				{
					onClickNewLink(arg0);
				}
			}
			
		});
		startNode.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.NEWLINK)
				{
					panel.setMousePosition();
					
				}
			}
			
		});
		*/
		
		aryNodePanel.add(startNode);
		
		panel.add(startNode);
		
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		curToolState = TOOLSTATE.SELECT;
		
		boardFrame = new TicTacToeBoardGUI(false,false,false, false);
		//boardFrame = new TicTacToeBoardGUI();
		boardFrame.setVisible(true);
		boardFrame.setAlwaysOnTop(true);
		boardFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		game = boardFrame.getGame();
	}
	/**
	 * Set toggle button
	 * @param newToolState
	 */
	public void setToggleButton(TOOLSTATE newToolState)
	{
		curToolState=newToolState;
	}
	
	
	/**
	 * set node panel listener for new nodes
	 * @param temp
	 */
	public void setNodePanelListeners(NodePanel temp)
	{
		temp.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				nodePanelClick(arg0);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.SELECT)
				{
					NodePanel curNode;
					if(arg0.isShiftDown())
					{
						curNode = (NodePanel)arg0.getComponent();
						nodeSelList.add(curNode);
						curNode.setSelected(true);
					}
					else
					{
						if(!nodeSelList.contains((NodePanel)arg0.getComponent()))
						{
							for(NodePanel n: nodeSelList)
							{
								n.setSelected(false);
							}
							nodeSelList.clear();
							curNode = (NodePanel)arg0.getComponent();
							nodeSelList.add(curNode);
							curNode.setSelected(true);
						}
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		
		temp.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(curToolState==TOOLSTATE.NEWLINK)
				{
					panel.setMousePosition();
					
				}
			}
			
		});
		
		temp.addDragListener(new DragListener() {

			@Override
			public void onDrag(NodePanel curNode, double deltaX, double deltaY) {
				// TODO Auto-generated method stub
				//NodePanel curNode;
				boolean isInList =false;
				Point delta;
				
				//curNode = (NodePanel)event.getComponent();
				
				isInList = nodeSelList.remove(curNode);
				
				for(NodePanel p: nodeSelList)
				{
					p.setLocation((int)(deltaX+p.getX()), (int)(deltaY+p.getY()));
				}
				
				if(isInList)
				{
					nodeSelList.add(curNode);
				}
			}
			
		});
		
		temp.getParentButton().addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				onParentButtonClicked(arg0);
			}
			
		});
	}
	
	public void nodePanelClick(MouseEvent arg0)
	{
		if(curToolState==TOOLSTATE.SELECT)
		{
		}
		if(curToolState==TOOLSTATE.DELETENODE)
		{
			if(!((NodePanel)arg0.getComponent()).equals(startNode))
			{
				panelDeleteNode(arg0);
			}
		}
		if(curToolState==TOOLSTATE.NEWLINK)
		{
			onClickNewLink(arg0);
		}
		if(curToolState==TOOLSTATE.DETERMINESTATUS)
		{
			onDetermineStatusPanelClick(arg0);
		}
		
	}
	
	/**
	 * Main Panel Click listener
	 * @param arg0
	 */
	public void panelMouseClick(MouseEvent arg0)
	{
		switch(curToolState)
		{
		case SELECT:
			if(!arg0.isShiftDown())
			{
				for(NodePanel n: nodeSelList)
				{
					n.setSelected(false);
				}
				nodeSelList.clear();
			}
			break;
		case NEWNODE:
			NodePanel temp = new NodePanel(this, ""+aryNodePanel.size(),arg0.getX(), arg0.getY());
			
			setNodePanelListeners(temp);
			
			aryNodePanel.add(temp);
			temp.setVisible(true);
			panel.add(temp);
			this.revalidate();
			this.repaint();
			break;
		case DELETENODE:
			break;
		}
	}
	
	/**
	 * Panel delete was clicked
	 * @param arg0
	 */
	public void panelDeleteNode(MouseEvent arg0)
	{
		NodePanel nodePanel = (NodePanel)arg0.getComponent();
		//nodePanel.getParentNodePanel()
		aryNodePanel.remove(nodePanel);
		panel.remove(nodePanel);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Panel panned 
	 * @param arg0
	 */
	public void onPanPanel(MouseEvent arg0)
	{
		int deltax;
        int deltay;
        
        deltax=arg0.getX()-this.x;
        deltay=arg0.getY()-this.y;
        x=arg0.getX();
        y=arg0.getY();
        
        Iterator<NodePanel> iter = aryNodePanel.iterator();
        NodePanel curNode;
        
        while(iter.hasNext())
        {
        	curNode = iter.next();
        	
        	curNode.setLocation(curNode.getX()+deltax,curNode.getY()+deltay);
        }
	}
	
	/**
	 * parent button clicked
	 * @param arg0
	 */
	public void onParentButtonClicked(ActionEvent arg0)
	{
		if(curToolState==TOOLSTATE.DELETELINK)
		{
			Object source;
			JButton btn;
			Component par;
			source = arg0.getSource();
			if(source.getClass() == JButton.class)
			{
				btn = (JButton)source;
				par = btn.getParent().getParent();
				if(par.getClass() == NodePanel.class)
				{
					NodePanel curNode = (NodePanel)par;
					curNode.setParentNodePanel(null);
					links.remove(curNode);
					repaint();
				}
			}
		}
		
	}
	
	/**
	 * Click new link button
	 * @param arg0
	 */
	public void onClickNewLink(MouseEvent arg0)
	{
		if(startLink == null)
		{
			onNewLinkInital(arg0);
		}
		else
		{
			onNewLinkRelease(arg0);
		}
	}
	
	/**
	 * on new link inital
	 * @param arg0
	 */
	public void onNewLinkInital(MouseEvent arg0)
	{
			startLink = (NodePanel)arg0.getComponent();
			System.out.println("Pressed on Node:"+startLink.getNodeName());
			panel.setStartNode(startLink);

	}
	
	/**
	 * New link mouse release
	 * @param arg0
	 */
	public void onNewLinkRelease(MouseEvent arg0)
	{
			NodePanel curNode=null;
			Component comp;
			
			System.out.println("released at x:"+arg0.getX()+" y:"+arg0.getY());
			comp = arg0.getComponent();
			
			if(comp != null)
			{
				System.out.println("Component:"+comp.getClass());
			}
			if(comp != null && comp.getClass() == NodePanel.class)
			{
				curNode = (NodePanel)comp;
				System.out.println("released on Node:"+curNode.getNodeName());
				if(startLink != null)
				{
					if(curNode != startLink && curNode.getParentNodePanel()==null)
					{
						startLink.addChildNodePanel(curNode);
						curNode.setParentNodePanel(startLink);
						links.put(curNode, startLink);
						System.out.println("made it!!!");
					}
				}
			}
			startLink = null;
			panel.setStartNode(null);
		}
	
		/**
		 * on determine status panel clicked
		 * @param arg0
		 */
		public void onDetermineStatusPanelClick(MouseEvent arg0)
		{			
			// Get NodePanel
			NodePanel selNode=null;
			Component comp;
			Deque<NodePanel> tempStack = new ArrayDeque<NodePanel>();
			NodePanel curNode;
			int count=0;
			
			comp = arg0.getComponent();
			
			if(comp != null && comp.getClass() == NodePanel.class)
			{
				boardFrame.setVisible(true);
				selNode = (NodePanel)comp;
				if(selNode != null && selNode !=startNode && selNode != activeNodePanel)
				{
					if(activeNodePanel != null)
					{
						activeNodePanel.setDetermineSelected(false);
					}
					//selNode.setBorder();
					selNode.setDetermineSelected(true);
					activeNodePanel=selNode;
					
					curNode = selNode;
					
					while(count < MAX_PARENT_SEARCH && curNode != null)
					{
						tempStack.push(curNode);
						
						if(curNode == startNode)
						{
							break;
						}
						
						curNode = curNode.getParentNodePanel();
						count++;
					}
					
					curNode = null;
				}
			}
			
			Iterator<NodePanel> iter;
			iter = tempStack.iterator();
			
			if(!tempStack.isEmpty())
			{
				curNode = iter.next();
				
				if(curNode == startNode)
				{
					// Setup Game frame through game object model
					game.reset(startNode.getMarker());
					game.start();
					
					game.takeTurn(curNode.getBoardPosition());
					System.out.println("Node Name:"+curNode.getNodeName()+" Marker:"+curNode.getMarker());
				
					while(iter.hasNext())
					{
						curNode = iter.next();
						game.takeTurn(curNode.getBoardPosition());
						System.out.println("Node Name:"+curNode.getNodeName()+" Marker:"+curNode.getMarker());
					}
				}
			}
			
		}
	
		/**
		 * New file button clicked
		 */
		public void onNewFile()
		{
			panel.removeAll();
			links.clear();
			aryNodePanel.clear();
			fileName=null;
			aryNodePanel.add(startNode);
			panel.add(startNode);
			panel.revalidate();
			panel.repaint();
			
		}
	
		/**
		 * Open file button clicked
		 */
		public void onOpenFile()
		{
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "XML Game Decision Trees", "xml");
			fileChooser.setFileFilter(filter);
			fileChooser.setCurrentDirectory(new File(TEST_XML));
			int returnVal = fileChooser.showOpenDialog(this);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = fileChooser.getSelectedFile();
		        // What to do with the file, e.g. display it in a TextArea
		        this.fileName=file.getAbsolutePath();
		        loadFile(this.fileName);
		    } else {
		        System.out.println("File access cancelled by user.");
		    }
		}
	
		/**
		 * Load XML file
		 * @param fileName
		 */
		public void loadFile(String fileName)
		{
			System.out.println("loadFile");
			onNewFile();
			this.fileName=fileName;
			
			Hashtable<String, NodePanel> nodes = new Hashtable<String, NodePanel>();
			Integer dispX=null;
			Integer dispY=null;
			if(fileName != null)
			{
				try {
				      // First, create a new XMLInputFactory
				      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
				      // Setup a new eventReader
				      InputStream in = new FileInputStream(fileName);
				      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
				      NodePanel tempNode = null;
				      NodePanel linkNode = null;
				      String name;
				      
				      while (eventReader.hasNext()) {
				          XMLEvent event = eventReader.nextEvent();
	
				          if (event.isStartElement()) {
				        	  StartElement startElement = event.asStartElement();
				        	  String temp = startElement.getName().getLocalPart();
				        	  
				        	  switch(temp){
				        	  case GameDTreeXMLConstants.NAME:	  
				        		  event = eventReader.nextEvent();
				        		  name = event.asCharacters().getData();
				        		  if(name.equals(GameDTreeXMLConstants.START_NODE))
				        		  {
				        			  tempNode = startNode;
				        		  }
				        		  else
				        		  {
				        			  tempNode = new NodePanel(this);
				        			  setNodePanelListeners(tempNode);
				        			  tempNode.setNodeName(name);
				        			  aryNodePanel.add(tempNode);
				        			  panel.add(tempNode);
				        		  }
				        		  break;
				        	  case GameDTreeXMLConstants.DISPLAY:
				        		  dispX=null;
				      			  dispY=null;
				        		  event = eventReader.nextEvent();
				        		  break;
				        	  case GameDTreeXMLConstants.DISPLAY_X:
				        		  event = eventReader.nextEvent();
				        		  dispX=Integer.parseInt(event.asCharacters().getData());
				        		  break;
				        	  case GameDTreeXMLConstants.DISPLAY_Y:
				        		  event = eventReader.nextEvent();
				        		  dispY=Integer.parseInt(event.asCharacters().getData());
				        		  break;
				        	  case GameDTreeXMLConstants.MARKER:
				        		  event = eventReader.nextEvent();
				        		  tempNode.setMarker(Markers.getMarkerFromInt(Integer.parseInt(event.asCharacters().getData())));
				        		  nodes.put(tempNode.getNodeName(), tempNode);
				        		  
				        		  if(dispX !=null && dispY != null)
				        		  {
				        			  tempNode.setLocation(dispX, dispY);
				        		  }
				        		  break;
				        	  case GameDTreeXMLConstants.POSITION:
				        		  event = eventReader.nextEvent();
				        		  tempNode.setBoardPosition(Integer.parseInt(event.asCharacters().getData()));
				        		  break;
				        	  case GameDTreeXMLConstants.STATUS:
				        		  event = eventReader.nextEvent();
				        		  tempNode.setStatus(event.asCharacters().getData());
				        		  break;
				        	  case GameDTreeXMLConstants.LINKER:
				        		  event = eventReader.nextEvent();
				        		  tempNode = nodes.get(event.asCharacters().getData());
				        		  break;
				        	  case GameDTreeXMLConstants.LINKED_NODE:
				        		  event = eventReader.nextEvent();
				        		  linkNode = nodes.get(event.asCharacters().getData());
				        		  tempNode.addChildNodePanel(linkNode);
				        		  linkNode.setParentNodePanel(tempNode);
				        		  links.put(linkNode, tempNode);
				        		  break;
				        		  default:
				        			  break;
				        	  }
				        	  
				          }
				      }
				      
				      in.close();      
				 }
				 catch (Exception ex){
					 ex.printStackTrace();
				 }
				panel.revalidate();
				panel.repaint();
			}
			else
			{
				System.out.println("fileName is null");
			}
		}
		
		/**
		 * Save as XML file
		 */
		public void onSaveFile()
		{
			if(fileName!=null)
			{
				saveFile(this.fileName);
			}
		}
		
		/**
		 * Open save file dialog
		 */
		public void onSaveFileAs()
		{
			this.fileName=TEST_XML;
			FileNameExtensionFilter filter = new FileNameExtensionFilter(
			        "XML Game Decision Trees", "xml");
			fileChooser.setFileFilter(filter);
			fileChooser.setCurrentDirectory(new File(TEST_XML));
			int returnVal = fileChooser.showSaveDialog(this);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
		        File file = fileChooser.getSelectedFile();
		        // What to do with the file, e.g. display it in a TextArea
		        this.fileName=file.getAbsolutePath();
		        onSaveFile();
		    } else {
		        System.out.println("File access cancelled by user.");
		    }
		}
		
		/**
		 * Save as XML file
		 * @param fileName
		 */
		public void saveFile(String fileName)
		{
			if(fileName != null)
			{
			 try {

					DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
					DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

					// root tree elements
					Document doc = docBuilder.newDocument();
					Element treeElement = doc.createElement(GameDTreeXMLConstants.TREE);
					doc.appendChild(treeElement);

					// nodesConfig elements
					Element nodesConfig = doc.createElement(GameDTreeXMLConstants.NODE_CONFIG);
					treeElement.appendChild(nodesConfig);
					
					Element curNode;
					Element nameNode;
					Element markerNode;
					Element posNode;
					Element statusNode;
					Element displayNode;
					Element dispYNode;
					Element dispXNode;
					
					Iterator<NodePanel> iter;
					NodePanel curPanel;
					
					iter = aryNodePanel.iterator();
					
					while(iter.hasNext())
					{
						curPanel = iter.next();
						
						// nodesConfig elements
						curNode = doc.createElement(GameDTreeXMLConstants.NODE);
						nodesConfig.appendChild(curNode);
						
						// nameNode
						nameNode = doc.createElement(GameDTreeXMLConstants.NAME);
						nameNode.appendChild(doc.createTextNode(curPanel.getNodeName()));
						curNode.appendChild(nameNode);
						
						// Display Node
						displayNode = doc.createElement(GameDTreeXMLConstants.DISPLAY);
						curNode.appendChild(displayNode);
						
						// Display X Node
						dispXNode = doc.createElement(GameDTreeXMLConstants.DISPLAY_X);
						dispXNode.appendChild(doc.createTextNode(""+curPanel.getX()));
						displayNode.appendChild(dispXNode);
						
						// Display Y Node
						dispYNode = doc.createElement(GameDTreeXMLConstants.DISPLAY_Y);
						dispYNode.appendChild(doc.createTextNode(""+curPanel.getY()));
						displayNode.appendChild(dispYNode);
						
						// markerNode
						markerNode = doc.createElement(GameDTreeXMLConstants.MARKER);
						markerNode.appendChild(doc.createTextNode(""+curPanel.getMarker().getInt()));
						curNode.appendChild(markerNode);
						
						// position Node
						posNode = doc.createElement(GameDTreeXMLConstants.POSITION);
						posNode.appendChild(doc.createTextNode(""+curPanel.getBoardPosition()));
						curNode.appendChild(posNode);
						
						// Status Node
						statusNode = doc.createElement(GameDTreeXMLConstants.STATUS);
						statusNode.appendChild(doc.createTextNode(""+curPanel.getStatus()));
						System.out.println(curPanel.getStatus());
						curNode.appendChild(statusNode);
					}
					
					Iterator<NodePanel> childIter;
					NodePanel childPanel;
					Element linker;
					Element linkedNode;
					iter=aryNodePanel.iterator();
					curNode = null;
					
					Element linkingPhase = doc.createElement(GameDTreeXMLConstants.LINKING_PHASE);
					treeElement.appendChild(linkingPhase);
					
					while(iter.hasNext())
					{
						curPanel = iter.next();
						childIter=curPanel.getChildNodePanelIter();
						
						if(curPanel.getChildNodeSize()>0)
						{
							curNode = doc.createElement(GameDTreeXMLConstants.NODE);
							linkingPhase.appendChild(curNode);
							
							linker = doc.createElement(GameDTreeXMLConstants.LINKER);
							linker.appendChild(doc.createTextNode(curPanel.getNodeName()));
							curNode.appendChild(linker);
							
							while(childIter.hasNext())
							{
								childPanel=childIter.next();
								linkedNode = doc.createElement(GameDTreeXMLConstants.LINKED_NODE);
								linkedNode.appendChild(doc.createTextNode(childPanel.getNodeName()));
								curNode.appendChild(linkedNode);
							}
						}
						
					}
					

					// write the content into xml file
					TransformerFactory transformerFactory = TransformerFactory.newInstance();
					Transformer transformer = transformerFactory.newTransformer();
					transformer.setOutputProperty(OutputKeys.INDENT, "yes");
					DOMSource source = new DOMSource(doc);
					StreamResult result = new StreamResult(fileName);

					// Output to console for testing
					// StreamResult result = new StreamResult(System.out);

					transformer.transform(source, result);

					System.out.println("File saved!");

				  } catch (ParserConfigurationException pce) {
					pce.printStackTrace();
				  } catch (TransformerException tfe) {
					tfe.printStackTrace();
				  }
			}
		}
		
		/**
		 * Exit button clicked
		 */
		public void onExitBtn()
		{
			this.dispose();
		}
		
		/**
		 * Set nodes as draggable
		 * @param value
		 */
		public void setNodesDraggable(boolean value)
		{
			this.areNodesDraggable=value;
		}
		
		/**
		 * get if nodes are draggable
		 * @return
		 */
		public boolean getNodesDraggable()
		{
			return this.areNodesDraggable;
		}
}
