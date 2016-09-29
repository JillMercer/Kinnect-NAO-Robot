package edu.sru.brian.tictactoegame.decisiontree;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.SystemColor;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import java.awt.FlowLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class TreeNodeMaker extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TreeNodeMaker frame = new TreeNodeMaker();
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
	public TreeNodeMaker() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 570, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JToolBar toolBar = new JToolBar();
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		JButton btnSelect = new JButton("Select");
		toolBar.add(btnSelect);
		
		JButton btnNewNode = new JButton("New Node");
		toolBar.add(btnNewNode);
		
		JButton btnDeleteNode = new JButton("Delete Node");
		toolBar.add(btnDeleteNode);
		
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(UIManager.getColor("Panel.background"));
		contentPane.add(desktopPane, BorderLayout.CENTER);
		
		JInternalFrame internalFrame = new JInternalFrame("Node 1");
		internalFrame.setClosable(true);
		internalFrame.setBounds(30, 28, 191, 152);
		desktopPane.add(internalFrame);
		internalFrame.getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel = new JPanel();
		internalFrame.getContentPane().add(panel);
		
		JLabel lblMarker = new JLabel("Marker:");
		panel.add(lblMarker);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"X", "O"}));
		panel.add(comboBox);
		
		JPanel panel_1 = new JPanel();
		internalFrame.getContentPane().add(panel_1);
		
		JLabel lblPosition = new JLabel("Position:");
		panel_1.add(lblPosition);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8"}));
		panel_1.add(comboBox_1);
		
		JPanel panel_2 = new JPanel();
		internalFrame.getContentPane().add(panel_2);
		
		JLabel lblStatus = new JLabel("Status:");
		panel_2.add(lblStatus);
		
		JComboBox comboBox_2 = new JComboBox();
		comboBox_2.setModel(new DefaultComboBoxModel(new String[] {"Undetermined", "Win", "Lose", "Tie"}));
		panel_2.add(comboBox_2);
		internalFrame.setVisible(true);
	}
}
