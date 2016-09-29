package edu.sru.kearney.controlgui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.BevelBorder;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.connection.Robot;

/**
 * ControlGui for the NAO robot compromised of naopanels Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public class ControlGui {

	private JFrame frmNaoControlGui;
	private String IP = null;
	private Robot robot;
	private NaoConnectPanel naoConnectPanel;
	private NaoAwarenessPanel naoAwarenessPanel;
	private NaoPosturePanel naoPosturePanel;
	private NaoTTSPanel naoTTSPanel;
	private NaoWalkPanel naoWalkPanel;
	private JMenuItem mntmRunDiagnostics;
	private static ControlGui window;

	/**
	 * Launch the application.
	 */

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new ControlGui(null);
					window.frmNaoControlGui.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Enables/disables all panels in the gui
	 * 
	 * @param t
	 */

	public void enableDisable(boolean t) {
		naoConnectPanel.enableDisable(t);
		naoAwarenessPanel.enableDisable(t);
		naoPosturePanel.enableDisable(t);
		naoTTSPanel.enableDisable(t);
		naoWalkPanel.enableDisable(t);
		mntmRunDiagnostics.setEnabled(t);
	}

	/**
	 * Passes the session to all panels in the gui
	 * 
	 * @throws Exception
	 */
	public void connect() throws Exception {
		naoConnectPanel.connect(robot.session());
		naoAwarenessPanel.connect(robot.session());
		naoPosturePanel.connect(robot.session());
		naoTTSPanel.connect(robot.session());
		naoWalkPanel.connect(robot.session());
	}

	/**
	 * Reboots the connected nao and exits
	 * 
	 * @throws Exception
	 */
	public void reboot() throws Exception {
			robot.reboot();
			frmNaoControlGui.dispose();
	}

	/**
	 * Shuts the connect nao down and exits
	 * 
	 * @throws Exception
	 */
	public void shutdown() throws Exception {
			robot.shutdown();
			frmNaoControlGui.dispose();
	}

	/**
	 * Disconnects from the connected nao and exits
	 * 
	 * @throws Exception
	 */
	public void disconnect() throws Exception {
			robot.stop();
			frmNaoControlGui.dispose();
	}

	/**
	 * Default constructor
	 */
	public ControlGui(Robot r) {
		this.robot = r;
		initialize();
	}

	/**
	 * Returns the connection status
	 * 
	 * @return
	 */
	public boolean isConnected() {
		return robot.isRunning();
	}

	/**
	 * Initialize the contents of the frame.
	 */

	private void initialize() {
		frmNaoControlGui = new JFrame();
		frmNaoControlGui.setTitle("NAO Control Gui");
		frmNaoControlGui.setBounds(100, 100, 1194, 515);
		frmNaoControlGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNaoControlGui.setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		frmNaoControlGui.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmRunDiagnostics = new JMenuItem("Run Diagnostics");
		mntmRunDiagnostics.addActionListener(runDiagnostics());
		mntmRunDiagnostics.setEnabled(false);
		mnFile.add(mntmRunDiagnostics);
		frmNaoControlGui.getContentPane().setLayout(null);

		naoConnectPanel = new NaoConnectPanel(this, robot);
		naoConnectPanel.setBounds(10, 11, 397, 105);
		frmNaoControlGui.getContentPane().add(naoConnectPanel);

		naoPosturePanel = new NaoPosturePanel();
		naoPosturePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		naoPosturePanel.setBounds(417, 11, 286, 105);
		frmNaoControlGui.getContentPane().add(naoPosturePanel);

		naoAwarenessPanel = new NaoAwarenessPanel();
		naoAwarenessPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		naoAwarenessPanel.setBounds(10, 128, 397, 198);
		frmNaoControlGui.getContentPane().add(naoAwarenessPanel);

		naoTTSPanel = new NaoTTSPanel();
		naoTTSPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		naoTTSPanel.setBounds(417, 129, 286, 311);
		frmNaoControlGui.getContentPane().add(naoTTSPanel);

		naoWalkPanel = new NaoWalkPanel();
		naoWalkPanel.setBounds(713, 11, 455, 429);
		frmNaoControlGui.getContentPane().add(naoWalkPanel);
		enableDisable(false);
		frmNaoControlGui.setVisible(true);
	}

	/**
	 * ActionListener to open up a new Diagnostics Frame
	 * 
	 * @return
	 */
	private ActionListener runDiagnostics() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					DiagnosticGUI diagnostics = new DiagnosticGUI(robot.session());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
	}
}
