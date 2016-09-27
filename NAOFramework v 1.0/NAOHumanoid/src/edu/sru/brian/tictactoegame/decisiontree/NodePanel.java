package edu.sru.brian.tictactoegame.decisiontree;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import edu.sru.brian.tictactoegame.Markers;

import java.awt.FlowLayout;

/**
 * File:NodePanel.java
 * @author Brian Atwell
 * Description: Represents node in TreeNodeMarkerV2.java
 *
 */

public class NodePanel extends JPanel {
	
	private double x;
	private double y;
	private ArrayList<NodePanel> nodeList = new ArrayList<NodePanel>();
	private ArrayList<DragListener> dragListeners = new ArrayList<DragListener>();
	private NodePanel parent = null;
	private JButton btnParentBtn;
	private String label;
	private JPanel titlePanel;
	private JComboBox<Markers> cmboMarker;
	private JComboBox<Integer> cmboPosition;
	private JComboBox<String> cmboStatus;
	private JLabel lblNode;
	private TreeNodeMarkerV2 mainGUI;
	
	private boolean isSelected=true;
	private boolean isDeterminedSelected=true;
	
	public final String UNDETERMINED="Undetermined";
	public final String WINORTIE="Win Or Tie";
	public final String WIN="Win";
	public final String LOSE="Lose";
	public final String TIE="Tie";
	
	public final static LineBorder defaultBorder = new LineBorder(new Color(0, 0, 0), 1);
	public final static LineBorder selectedBorder = new LineBorder(new Color(0, 0, 100), 4);
	public final static LineBorder determinedSelectedBorder = new LineBorder(new Color(0, 0, 100), 2);
	
	public final static Color defaultTitleColor =  UIManager.getColor("InternalFrame.activeTitleBackground");
	public final static Color determinedSelectedColor = Color.GREEN;
	
	
	public NodePanel(TreeNodeMarkerV2 mainGUI)
	{
		this(mainGUI, "",10,10);
	}
	
	public NodePanel(TreeNodeMarkerV2 mainGUI, String label, double posX, double posY)
	{
		this.mainGUI = mainGUI;
		this.label=label;
		this.setBorder(defaultBorder);
		this.setBounds(47, 44, 181, 148);
		this.setLayout(new BorderLayout(0, 0));
		
		titlePanel = new JPanel();
		
		NodePanel nodePanel = this;
		
		titlePanel.addMouseListener(new MouseListener() {
			public void mousePressed(MouseEvent e) {
				x = e.getX();
		        y = e.getY();
		        e.setSource(nodePanel);
		        nodePanel.dispatchEvent(e);
			}
			public void mouseReleased(MouseEvent e) {
				e.setSource(nodePanel);
				nodePanel.dispatchEvent(e);
			}
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				e.setSource(nodePanel);
				nodePanel.dispatchEvent(e);
			}
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				e.setSource(nodePanel);
				nodePanel.dispatchEvent(e);
				
			}
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				e.setSource(nodePanel);
				nodePanel.dispatchEvent(e);
				
			}
		});
		titlePanel.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				onMouseDragged(e);
				nodePanel.dispatchEvent(e);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				nodePanel.dispatchEvent(e);
				
			}
		});
		titlePanel.setBackground(UIManager.getColor("InternalFrame.activeTitleBackground"));
		this.add(titlePanel, BorderLayout.NORTH);
		titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		lblNode = new JLabel("Node: "+this.label);
		titlePanel.add(lblNode);
		
		btnParentBtn = new JButton("");
		btnParentBtn.setBackground(UIManager.getColor("Button.background"));
		btnParentBtn.setToolTipText("ParentLink");
		titlePanel.add(btnParentBtn);
		btnParentBtn.setMinimumSize(new Dimension(20,20));
		btnParentBtn.setPreferredSize(new Dimension(20,20));
		btnParentBtn.setActionCommand("ParentLink");
		
		JPanel panel_3 = new JPanel();
		this.add(panel_3, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		panel_3.add(panel_4);
		
		JLabel lblMarker = new JLabel("Marker:");
		panel_4.add(lblMarker);
		
		cmboMarker = new JComboBox<Markers>();
		cmboMarker.setModel(new DefaultComboBoxModel<Markers>(new Markers[] {Markers.X, Markers.O}));
		panel_4.add(cmboMarker);
		
		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		
		JLabel lblPosition = new JLabel("Position:");
		panel_5.add(lblPosition);
		
		cmboPosition = new JComboBox<Integer>();
		cmboPosition.setModel(new DefaultComboBoxModel<Integer>(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8}));
		panel_5.add(cmboPosition);
		
		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		
		JLabel lblStatus = new JLabel("Status:");
		panel_6.add(lblStatus);
		
		cmboStatus = new JComboBox<String>();
		cmboStatus.setModel(new DefaultComboBoxModel<String>(new String[] {UNDETERMINED, WINORTIE, WIN, LOSE, TIE}));
		panel_6.add(cmboStatus);
		
		this.setLocation(new Double(posX).intValue(), new Double(posY).intValue());
	}
	
	public void onMouseDragged(MouseEvent arg0)
	{
		double locx = 0;
        double locy = 0;
        
        if(mainGUI.getNodesDraggable())
        {        
	        locx=arg0.getX()+this.getX()-this.x;
	        locy=arg0.getY()+this.getY()-this.y;
	        //MouseEvent event = new MouseEvent(this, arg0.getID(), arg0.getWhen(), arg0.getModifiers(), arg0.getX(), arg0.getY(), arg0.getXOnScreen(), arg0.getYOnScreen(), arg0.getClickCount(), arg0.isPopupTrigger(), arg0.getButton());
	        //onDrag(new ComponentDragEvent(this.getX()-this.x, this.getY()-this.y);
	        onDrag(this, arg0.getX()-this.x, arg0.getY()-this.y);
	        this.setLocation(new Double(locx).intValue(), new Double(locy).intValue());
	        this.getParent().repaint();
        }

        //arg0.translatePoint(arg0.getComponent().getLocation().x-x, arg0.getComponent().getLocation().y-y);
        //this.setLocation(arg0.getX(), arg0.getY());
	}
	
	/**
	 * add Child node
	 * @param node
	 */
	public void addChildNodePanel(NodePanel node)
	{
		nodeList.add(node);
	}
	
	/**
	 * Removes child node
	 * @param node
	 */
	public void removeChildNodePanel(NodePanel node)
	{
		nodeList.add(node);
	}
	
	/**
	 * Get childNodes iterator
	 * @return
	 */
	public Iterator<NodePanel> getChildNodePanelIter()
	{
		return nodeList.iterator();
	}
	
	/**
	 * Get the number of child nodes
	 * @return
	 */
	public int getChildNodeSize()
	{
		return nodeList.size();
	}
	
	/**
	 * Get parent node panel
	 * @return
	 */
	public NodePanel getParentNodePanel()
	{
		return parent;
	}
	
	/**
	 * Set node panel parent
	 * @param newParent
	 */
	public void setParentNodePanel(NodePanel newParent)
	{		
		if(this.parent == null && newParent != null)
		{
			btnParentBtn.setBackground(Color.BLACK);
			this.parent = newParent;
		}
		else if(this.parent != null && newParent == null)
		{
			btnParentBtn.setBackground(UIManager.getColor("Button.background"));
			this.parent = newParent;
		}
	}
	
	public JButton getParentButton()
	{
		return btnParentBtn;
	}
	
	/**
	 * Get node name
	 * @return
	 */
	public String getNodeName()
	{
		return this.label;
	}
	
	/**
	 * Set node name
	 * @param label
	 */
	public void setNodeName(String label)
	{
		this.label = label;
		lblNode.setText("Node: "+this.label);
	}
	
	/**
	 * Get marker
	 * @return
	 */
	public Markers getMarker() 
	{
		
		return (Markers)cmboMarker.getSelectedItem();
	}
	
	/**
	 * Set marker
	 * @param value
	 */
	public void setMarker(Markers value)
	{
		cmboMarker.setSelectedItem(value);
	}
	
	/**
	 * Get status
	 * @return
	 */
	public String getStatus() 
	{
		return cmboStatus.getSelectedItem().toString();
	}
	
	/***
	 * Set status
	 * @param status
	 */
	public void setStatus(String status)
	{
		cmboStatus.setSelectedItem(status);
	}
	
	/**
	 * Get Board Position
	 * @return
	 */
	public Integer getBoardPosition()
	{
		return (Integer)cmboPosition.getSelectedItem();
	}
	
	/**
	 * Set Board position
	 * @param value
	 */
	public void setBoardPosition(Integer value)
	{
		cmboPosition.setSelectedItem(value);
	}
	
	/**
	 * Set if node is selected
	 * @param value
	 */
	public void setSelected(boolean value)
	{
		this.isSelected=value;
		if(value)
		{
			this.setBorder(selectedBorder);
		}
		else
		{
			this.setBorder(defaultBorder);
		}
	}
	
	/**
	 * Is selected
	 * @return
	 */
	public boolean isSelected()
	{
		return this.isSelected;
	}
	
	/**
	 * Set if determined selected
	 * @param value
	 */
	public void setDetermineSelected(boolean value)
	{
		this.isDeterminedSelected=value;
		if(value)
		{
			titlePanel.setBackground(determinedSelectedColor);
		}
		else
		{
			titlePanel.setBackground(defaultTitleColor);
		}
	}
	
	/**
	 * is determined selected
	 * @return
	 */
	public boolean isDeterminedSelected() 
	{
		return this.isDeterminedSelected;
	}
	
	/**
	 * Add drag listener
	 * @param listener
	 */
	public void addDragListener(DragListener listener)
	{
		dragListeners.add(listener);
	}
	
	/**
	 * Remove drag listener
	 * @param listener
	 */
	public void removeDragListener(DragListener listener)
	{
		dragListeners.remove(listener);
	}
	
	/**
	 * on drag event
	 * @param curNode
	 * @param x
	 * @param y
	 */
	public void onDrag(NodePanel curNode, double x, double y)
	{
		for(DragListener d: dragListeners)
		{
			d.onDrag(curNode, x,y);
		}
	}
	
}
