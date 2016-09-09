package edu.sru.brian.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import edu.sru.thangiah.nao.sensors.sonar.SonarNAOPanel;

public class ShowablePanel extends JPanel {
	public static void showDialog(JPanel customPanel, String title) {
		JPanel panel;
		JDialog dialog;
		
		if(customPanel == null)
		{
			panel = new ShowablePanel();
		}
		else
		{
			panel = customPanel;
		}
		
		dialog = new JDialog(new JFrame(), title, true);
		
		
		dialog.setBounds(100, 100, 435, 405);
		dialog.getContentPane().setLayout(new BorderLayout());
		dialog.setMinimumSize(new Dimension(240,275));
		
		//dialog.setDefaultCloseOperation(JDialog.);
		
		dialog.addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				//Uninitialize();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		//panel.setLayout(new FlowLayout());
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		dialog.getContentPane().add(panel, BorderLayout.CENTER);
		dialog.getContentPane().add(panel);
		dialog.setVisible(true);
	}
}
