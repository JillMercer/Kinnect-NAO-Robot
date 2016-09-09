package edu.sru.kearney.controlgui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
 * Connect Dialog Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public class ConnectDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private RobotList naoList;
	private String selectedIP = "None";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ConnectDialog dialog = new ConnectDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 * 
	 * @throws Exception
	 */

	class naoConnectTable extends AbstractTableModel {

		private String[] columnNames = { "Robot Name", "Robot IP" };
		private Object[][] data;

		naoConnectTable() throws Exception {
			data = getObjectList();

		}

		@Override
		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public int getRowCount() {
			return data.length;
		}

		public String getColumnName(int col) {
			return columnNames[col];
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			return data[arg0][arg1];
		}

		Object[][] getObjectList() throws Exception {
			naoList = new RobotList();
			naoList.run();
			int numRobot = naoList.getNumRobot();
			Object[][] newList = new Object[numRobot][2];
			for (int i = 0; i < numRobot; i++) {
				newList[i][0] = naoList.getName(i);
				newList[i][1] = naoList.getIp(i);
			}
			return newList;

		}

	}

	class ForcedListSelectionModel extends DefaultListSelectionModel {

		public ForcedListSelectionModel() {
			setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}

		@Override
		public void clearSelection() {
		}

		@Override
		public void removeSelectionInterval(int index0, int index1) {
		}

	}

	Object getSelectedIp() {
		return table.getValueAt(table.getSelectedRow(), 1);
	}

	void addTable() throws Exception {
		contentPanel.setLayout(null);
		table = new JTable(new naoConnectTable());
		table.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		table.setLocation(0, 0);
		table.setSize(430, 227);
		table.setSelectionModel(new ForcedListSelectionModel());
		contentPanel.add(table);
		table.changeSelection(0, 0, false, false);
	}

	public String getIp() {
		return selectedIP;
	}

	public ConnectDialog() throws Exception {
		setModal(true);
		setTitle("Connect To Nao");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		addTable();

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton connectButton = new JButton("Connect");
				connectButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						selectedIP = (String) getSelectedIp();
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
						selectedIP = "None";
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}
