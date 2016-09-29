package edu.sru.thangiah.nao.demo;

import java.util.ArrayList;

import com.aldebaran.qi.helper.proxies.ALBarcodeReader;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;
import edu.sru.thangiah.nao.events.EventPair;

/**
 * Author: Zachary Kearney Last Edited, 5/5/2016
 * 
 * @author zrk1002 Barcode Scanner demo. Show the robot a barcode and its
 *         contents will print in the console.
 *
 */

public class BarcodeScanner extends Demo {

	private String robotName = "";
	protected String demoName = "Barcode Scanner";

	public BarcodeScanner(SynchronizedConnectDemo connection) throws Exception {
		super(connection);
		demoName = "Barcode Scanner";
		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog(connection);
		dialog.setVisible(true);
		robotName = dialog.getSelectedName();
		robotNames.add(robotName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void run() throws Exception {

		robots.add(new BarcodeRobot(robotName, connection));

	}

	private class BarcodeRobot extends DemoRobot {

		private EventPair barcodeEvent;
		private ALBarcodeReader barcode;
		private final static String bar = "BARCODE";

		public BarcodeRobot(String name, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void safeClose() throws Exception {

			memory.unsubscribeToEvent(barcodeEvent.eventID);
			barcode.unsubscribe(bar);
		}

		@Override
		protected void init() throws Exception {
			barcode = new ALBarcodeReader(connection.getSession(name));
			barcode.subscribe(bar);

			barcodeEvent.eventID = memory.subscribeToEvent(barcodeEvent.eventName, (value) -> {

				System.out.println("Detected");

				if (value != null) {

					ArrayList<ArrayList> list = (ArrayList<ArrayList>) value;
					if (list.size() > 0) {
						String data = (String) list.get(0).get(0);
						System.out.println(data);
					}

				}
			});
		}

		@Override
		public void execute() throws Exception {
			// TODO Auto-generated method stub

		}

		@Override
		protected void rearTactil() {
			try {
				exit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
