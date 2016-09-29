package edu.sru.thangiah.nao.vision;

import java.awt.EventQueue;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.connection.SynchronizedConnect;

public class Demo {
	
	private SynchronizedConnect connect;
	private CamFrame frame;

	public static void main(String args[]){
		try {
			Demo demo = new Demo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Demo() throws Exception{
		
		connect = new SynchronizedConnect("192.168.0.100");
		String name = connect.getAllNames().get(0);

	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				try {
				    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				        if ("Nimbus".equals(info.getName())) {
				            UIManager.setLookAndFeel(info.getClassName());
				            break;
				        }
				    }
				} catch (Exception e) {
				    // If Nimbus is not available, you can set the GUI to another look and feel.
				}
				//new AppThread().start();
				frame = new CamFrame(connect, name);
				frame.setVisible(true);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});	
	}
	
}
