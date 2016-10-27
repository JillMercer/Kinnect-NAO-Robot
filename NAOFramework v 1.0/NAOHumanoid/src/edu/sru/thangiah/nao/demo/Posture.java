package edu.sru.thangiah.nao.demo;

import java.util.ArrayList;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;

/**
 * Author: Zachary Kearney Last Edited, 5/5/2016
 * 
 * @author zrk1002 Posture demo. Front tactile and bottom tactile adjust which
 *         posture will be selected. Middle tactile will execute posture.
 *
 */
public class Posture extends Demo {

	private String robotName = "";

	public Posture(SynchronizedConnectDemo connection) throws Exception {
		super(connection);
		demoName = "Posture";
		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog(connection);
		dialog.setVisible(true);
		robotName = dialog.getSelectedName();
		robotNames.add(robotName);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void run() throws Exception {
		robots.add(new PostureRobot(robotName, connection));
	}

	private class PostureRobot extends DemoRobot {

		private ALTextToSpeech tts;
		private ALMotion motion;
		private ALRobotPosture posture;
		private boolean moving = false;
		private int option = 0;
		private ArrayList<String> postureList;
		private ArrayList<String> ttsList;

		public PostureRobot(String name, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void safeClose() throws Exception {
			tts.stopAll();
			goToPosture("Crouch");
			motion.rest();
		}

		@Override
		protected void init() throws Exception {
			postureList = new ArrayList<String>();
			ttsList = new ArrayList<String>();
			postureList.add("Sit"); // 0
			ttsList.add("Sit");
			postureList.add("SitRelax"); // 1
			ttsList.add("Sit Relaxed");
			postureList.add("Stand"); // 2
			ttsList.add("Stand");
			postureList.add("Crouch"); // 3
			ttsList.add("Crouch");
			postureList.add("LyingBack"); // 4
			ttsList.add("Lie on my back");
			postureList.add("LyingBelly"); // 5
			ttsList.add("Lie on my belly");
			ttsList.add("Exit");
			tts = new ALTextToSpeech(connection.getSession(name));
			motion = new ALMotion(connection.getSession(name));
			posture = new ALRobotPosture(connection.getSession(name));
			motion.wakeUp();
		}

		@Override
		public void execute() throws Exception {
			// TODO Auto-generated method stub

		}

		private void goToPosture(String post) {
			try {
				moving = true;
				moving = !posture.goToPosture(post, .75f);
			} catch (CallError e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void frontTactil() {
			if (!moving) {
				try {
					tts.stopAll();
				} catch (CallError e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (option >= 6) {
					option = 0;
				} else
					option++;
				try {
					tts.say(ttsList.get(option));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		@Override
		protected void middleTactil() {
			if (option == 6) {
				try {
					exit();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				goToPosture(postureList.get(option));
			}
		}

		@Override
		protected void rearTactil() {
			if (!moving) {
				try {
					tts.stopAll();
				} catch (CallError e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (option <= 0) {
					option = 6;
				} else
					option--;
				try {
					tts.say(ttsList.get(option));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

}
