package edu.sru.thangiah.nao.demo.storytelling.gui;

import javax.swing.JOptionPane;

public class MessageBox {
	
	private MessageBox(){}

	public static void show(String infoMessage, String titleBar){
        JOptionPane.showMessageDialog(null, infoMessage, titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
	
}
