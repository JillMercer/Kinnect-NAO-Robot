package edu.sru.kearney.controlgui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.aldebaran.qi.Session;

import edu.sru.thangiah.nao.demo.old.Demo;
import edu.sru.thangiah.nao.demo.old.DemoEnum;

/**
 * Application Dialog Last Edited, 11/30/2015
 * 
 * @author Zachary Kearney
 */

public class NaoAppDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	private Session sess;
	private Demo newDemo;

	/**
	 * Create the dialog.
	 */
	public NaoAppDialog(Session sess, DemoEnum demo) throws Exception {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				try {
					end();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		this.sess = sess;
		setBounds(100, 100, 280, 181);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setModal(true);
		//newDemo = new Demo(sess);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton btnRunApplication = new JButton("Run Application");
				buttonPane.add(btnRunApplication);
				//newDemo.runDemo(demo);
			}
			{
				JButton okButton = new JButton("End Application");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}

	public void end() throws Exception {
		dispose();
		newDemo.exit();
	}

}
