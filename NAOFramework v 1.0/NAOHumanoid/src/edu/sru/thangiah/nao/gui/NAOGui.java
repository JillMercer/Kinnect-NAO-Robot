package edu.sru.thangiah.nao.gui;


import javax.swing.JLabel;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;

public class NAOGui  implements NAOGUIInterface{
	
	public NAOGui() {
	}
	
	


	public static void main (String[] args)
	{
		try {
			
			NAOFrame frmNaoHumanoidRobot = new NAOFrame();
			frmNaoHumanoidRobot.setTitle("NAO: Humanoid Robot");
			frmNaoHumanoidRobot.getContentPane().setLayout(null);
			
			//set the panle navigation
			//JPanel panelNavigation = new JPanel();
			//panelNavigation.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			//panelNavigation.setBounds(10, 11, 118, 160);
			//frmNaoHumanoidRobot.getContentPane().add(panelNavigation);
			JPanel naoNavigationPanel = new NAONavigationPanel();
			naoNavigationPanel.setBounds(10, 11, 128, 160);
			frmNaoHumanoidRobot.getContentPane().add(naoNavigationPanel);
			
			//set the detail panel
			//JPanel panelDetail = new JPanel();
			//panelDetail.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			//panelDetail.setBounds(138, 11, 286, 240);
			//frmNaoHumanoidRobot.getContentPane().add(panelDetail);
			JPanel naoComDetailPanel = new NAOComDetailPanel();
			naoComDetailPanel.setBounds(158, 11, 327, 258);
			frmNaoHumanoidRobot.getContentPane().add(naoComDetailPanel);
			
			//Set the log area
			//JTextArea textAreaLogs = new JTextArea();
			//textAreaLogs.setBounds(10, 188, 118, 63);
			//frmNaoHumanoidRobot.getContentPane().add(textAreaLogs);
			//frmNaoHumanoidRobot.setVisible(true);
			
			JPanel naoLogPanel = new NAOLogPanel();
			naoLogPanel.setBounds(17, 445, 468, 78);
			frmNaoHumanoidRobot.getContentPane().add(naoLogPanel);
			
			//display the frame
			frmNaoHumanoidRobot.setVisible(true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
