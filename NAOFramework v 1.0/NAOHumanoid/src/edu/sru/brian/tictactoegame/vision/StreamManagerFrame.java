package edu.sru.brian.tictactoegame.vision;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * StreamManagerFrame
 * @author Brian Atwell
 * Frame for displaying a stream
 *
 */
public class StreamManagerFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StreamManagerFrame frame = new StreamManagerFrame();
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
	public StreamManagerFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmOpenLayout = new JMenuItem("Open Layout");
		mntmOpenLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnFile.add(mntmOpenLayout);
		
		JMenuItem mntmSaveLayout = new JMenuItem("Save Layout");
		mntmSaveLayout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnFile.add(mntmSaveLayout);
		
		JMenuItem mntmSaveLayoutAs = new JMenuItem("Save Layout As");
		mntmSaveLayoutAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnFile.add(mntmSaveLayoutAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnSetSource = new JMenu("Set Source");
		mnEdit.add(mnSetSource);
		
		JMenu mnNao = new JMenu("NAO");
		mnSetSource.add(mnNao);
		
		JMenuItem mntmNaoTopCamera = new JMenuItem("Top Camera");
		mntmNaoTopCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnNao.add(mntmNaoTopCamera);
		
		JMenuItem mntmBottomCamera = new JMenuItem("BottomCamera");
		mntmBottomCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnNao.add(mntmBottomCamera);
		
		JMenuItem mntmWebCamera = new JMenuItem("Web Camera");
		mntmWebCamera.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnSetSource.add(mntmWebCamera);
		
		JMenuItem mntmImageFile = new JMenuItem("Image File");
		mntmImageFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		mnSetSource.add(mntmImageFile);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
