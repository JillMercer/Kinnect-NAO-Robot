package com.aldebaran.demo;


import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.java2s.RegexFormatter;

/*
 * Provides common dialogs
 */
public class CommonDialogs {
	
	public static String showIPDialog () {
		String ipStr;
		ipStr=null;
		
		try {
			JPanel panel = new JPanel( new BorderLayout(5,0) );
			panel.add( new JLabel("Input: "), BorderLayout.WEST );
	
			//MaskFormatter formatter = new MaskFormatter("###.###.###.###");
			//RegEx (2[0-5][0-5])|([0-1]\d\d)
			RegexFormatter formatter = new RegexFormatter("\\(2\\[0-5\\]\\[0-5\\]\\)\\|\\(\\(0\\|1\\)\\d\\d\\)");
			JFormattedTextField text = new JFormattedTextField(formatter);
			panel.add( text );
	
			JOptionPane.showMessageDialog( null, panel );
			
			ipStr = text.getText();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ipStr;
	}
}
