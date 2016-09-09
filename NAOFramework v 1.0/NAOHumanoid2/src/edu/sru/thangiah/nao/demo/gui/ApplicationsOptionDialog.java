package edu.sru.thangiah.nao.demo.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.SynchronizedDance;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class ApplicationsOptionDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	@SuppressWarnings("rawtypes")
	private ArrayList<JComboBox> optionslist;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		SynchronizedConnectDemo sync = null;
		SynchronizedConnectDialog dialog = null;
		try {
			dialog = new SynchronizedConnectDialog();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		sync = new SynchronizedConnectDemo(dialog.getIps());
		SynchronizedDance dance = null;
		try {
			dance = new SynchronizedDance(sync);
			dance.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		while(dance.isRunning()){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		sync.stopAll();
	}

	/**
	 * Create the dialog.
	 */
	@SuppressWarnings("rawtypes")
	public ApplicationsOptionDialog(SynchronizedConnectDemo connect) {
		setModal(true);
		setTitle("Application Options");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		optionslist = new ArrayList<JComboBox>();
		setBounds(100, 100, 263, 172);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						}				
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
		{
			ArrayList<String> names = connect.getAllInactiveNames();
			String[] masterNames = new String[names.size()];
			for(int i = 0; i < names.size(); i++){
				masterNames[i] = names.get(i);
			}
			OptPanel opt = new OptPanel("Master", masterNames);
			opt.setVisible(true);
			contentPanel.add(opt);
			
		}
		pack();
	}
	
	public String[] getOptions(){
		String[] returnVal = new String[optionslist.size() - 1];
		for(int i = 1; i < returnVal.length + 1; i++){
			returnVal[i-1] = (String) optionslist.get(i).getSelectedItem();
		}
		return returnVal;
	}
	
	@SuppressWarnings("serial")
	private class OptPanel extends JPanel{
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public OptPanel(String title, String[] options){
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			JTextField txtTitle = new JTextField();
			txtTitle.setEditable(false);
			txtTitle.setHorizontalAlignment(SwingConstants.CENTER);
			txtTitle.setText(title);
			add(txtTitle);
			JComboBox comboBox = new JComboBox(options);
			add(comboBox);
			optionslist.add(comboBox);
			setVisible(true);
		}
	}
	
	public void addOption(String title, String[] options){
		OptPanel opt = new OptPanel(title, options);
		opt.setVisible(true);
		contentPanel.add(opt);
		pack();
	}

	public String getSelectedName(){
		Thread t = new Thread(){
			public void run(){
				dispose();
			}
		};
		t.start();
		return (String) optionslist.get(0).getSelectedItem();
	}
}
