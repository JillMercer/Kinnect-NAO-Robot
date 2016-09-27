package edu.sru.thangiah.nao.demo.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.AbstractTableModel;

import edu.sru.thangiah.nao.connection.RobotList;

/**
 * Connect Dialog Last Edited, 4/29/2016
 * 
 * @author Zachary Kearney
 */

public class SynchronizedConnectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private RobotList naoList;
	private ArrayList<String> Ips;

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */

	class naoConnectTable extends AbstractTableModel {

		private String[] columnNames = {"Robot Name", "Robot IP"};
		List<Object[]> data = new ArrayList<Object[]>();
		//private Object[][] data;

		naoConnectTable() throws Exception {
			data = getObjectList();
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

		ArrayList<Object[]> getObjectList() throws Exception{
			ArrayList<Object[]> returnVal = new ArrayList<Object[]>();
			int numRobot = naoList.getNumRobot();
			for(int i = 0; i < numRobot; i++){
				returnVal.add(new Object[]{naoList.getName(i), naoList.getIp(i)});
			}
			return returnVal;
		}
		/**
		Object[][] getObjectList() throws Exception {
			int numRobot = naoList.getNumRobot();
			Object[][] newList = new Object[numRobot][2];
			for (int i = 0; i < numRobot; i++) {
				newList[i][0] = naoList.getName(i);
				newList[i][1] = naoList.getIp(i);
			}
			return newList;

		}
		*/

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

	String getSelectedIp() {
		return (String)table.getValueAt(table.getSelectedRow(), 1);
	}
	
	ArrayList<String> getSelectedIps(){
		ArrayList<String> returnVal = new ArrayList<String>();
		for(int i = 0; i < table.getRowCount(); i++){
			if(table.isRowSelected(i)){
				returnVal.add((String) table.getValueAt(i, 1));
			}
		}
		return returnVal;
	}
	
	void addMainTable() throws Exception {
		contentPanel.setLayout(null);
		table = new JTable(new naoConnectTable());
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setLocation(0, 0);
		table.setSize(430, 227);
		table.setSelectionModel(new ForcedListSelectionModel2());
		contentPanel.add(table);
		table.changeSelection(0, 0, false, false);
	}
	
	public ArrayList<String> getIps() {
		Thread t = new Thread(){
			public void run(){
				dispose();
			}
		};
		t.start();
		if(Ips.isEmpty()){
			return null;
		}
		return Ips;
	}

	public SynchronizedConnectDialog() throws Exception {
		
		setModal(true);
		setAlwaysOnTop(true);
		setTitle("Connect To Nao");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		Ips = new ArrayList<String>();
		naoList = new RobotList();
		naoList.run();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		addMainTable();

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{			
				JButton connectButton = new JButton("Select All");
				connectButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						for(String str : getSelectedIps()){
							Ips.add(str);
							}
						}				
				});
				
				buttonPane.add(connectButton);
				getRootPane().setDefaultButton(connectButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						Ips.clear();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		setVisible(true);
	}
}
