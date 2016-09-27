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

public class SynchronizedAppOptionsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private ArrayList<JComboBox> optionslist;
	private JComboBox master;
	private String[] robotNames;
	private String[] returnOptions;
	private ButtonGroup group;
	private JTable table;
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
	public SynchronizedAppOptionsDialog(SynchronizedConnectDemo connect) {
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
		addConnectTable(connect);
		JScrollPane treeView = new JScrollPane(table);
		treeView.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		treeView.setMinimumSize(new Dimension(200,100));
		treeView.setPreferredSize(new Dimension(200,100));
		contentPanel.add(treeView);
		pack();
	}
	
	public String[] getOptions(){
		String[] returnVal = new String[optionslist.size() - 1];
		for(int i = 1; i < returnVal.length + 1; i++){
			returnVal[i-1] = (String) optionslist.get(i).getSelectedItem();
		}
		return returnVal;
	}
	
	private class OptPanel extends JPanel{
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
	
	class naoConnectTable extends AbstractTableModel {

		private String[] columnNames = {"Children"};
		List<Object[]> data = new ArrayList<Object[]>();

		naoConnectTable(SynchronizedConnectDemo connect){
			data = getObjectList(connect);
		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}
		
		public void removeRow(int row){
			data.remove(row);
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			return data.get(arg0)[arg1];
		}

		ArrayList<Object[]> getObjectList(SynchronizedConnectDemo connect){
			ArrayList<Object[]> returnVal = new ArrayList<Object[]>();
			ArrayList<String> names = connect.getAllInactiveNames();
			for(int i = 0; i < names.size(); i++){
				/**
				Object[] data = new Object[3];
				data[0] = names.get(i);
				JRadioButton but = new JRadioButton("Master");
				if(i == 0){
					but.setSelected(true);
				}
				data[1] = but;
				group.add(but);
				data[2] = new JCheckBox("Child");
				returnVal.add(data);
			*/
				returnVal.add(new Object[] {names.get(i)});
			}
			return returnVal;
		}
	}
	
	public void addConnectTable(SynchronizedConnectDemo connect){
		group = new ButtonGroup();
		table = new JTable(new naoConnectTable(connect));
		table.setSelectionModel(new ForcedListSelectionModel2());
		table.changeSelection(0, 0, false, false);
	}
	
	public ArrayList<String> getSelectedNames(){
		Thread t = new Thread(){
			public void run(){
				dispose();
			}
		};
		t.start();
		ArrayList<String> returnVal = new ArrayList<String>();
		returnVal.add((String) optionslist.get(0).getSelectedItem());
		for(int i = 0; i < table.getRowCount(); i++){
			if(table.isRowSelected(i)){
				if(!returnVal.contains((String) table.getValueAt(i, 0))){
					returnVal.add((String) table.getValueAt(i, 0));
				}
			}
		}
		return returnVal;
	}
	
	class ForcedListSelectionModel2 extends DefaultListSelectionModel {

		public ForcedListSelectionModel2() {
			setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		}

		@Override
		public void clearSelection() {
		}

		@Override
		public void removeSelectionInterval(int index0, int index1) {
		}

	}
}
