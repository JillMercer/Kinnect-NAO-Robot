package edu.sru.thangiah.nao.demo;

import com.aldebaran.qi.helper.proxies.ALAudioPlayer;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.SynchronizedAppOptionsDialog;
import edu.sru.thangiah.nao.movement.Animation;

/**
 * Author: Zachary Kearney Last Edited, 5/5/2016
 * 
 * @author zrk1002 Synchronized Dance demo. Pressing the front tactil on the
 *         master will execute the dance and music. Pressing the rear tactil
 *         will exit.
 *
 */

public class SynchronizedDance extends Demo {

	private String MASTERNAME = "";
	private String ANIMATION = "Macarena_0";
	private String MUSICPATH = "/var/persistent/home/nao/audio/macarena.wav";

	public SynchronizedDance(SynchronizedConnectDemo connection) throws Exception {
		super(connection);

		String[] danceArr = new String[] { "Macarena", "Dab", "Turndown" };

		//New window and dialog box displaying the dance options
		//Can select the type of dance and the robots involved in the dance
		SynchronizedAppOptionsDialog dialog = new SynchronizedAppOptionsDialog(connection);
		dialog.addOption("Dance", danceArr);
		dialog.setVisible(true);
		String[] opt = dialog.getOptions();
		String dance = opt[0];
		robotNames = dialog.getSelectedNames();
		MASTERNAME = robotNames.get(0);
		if (!robotNames.contains(MASTERNAME)) {
			robotNames.add(MASTERNAME);
		}
		demoName = "Synchronized Dance: " + dance;

		switch (dance) {
		case "Macarena":
			MUSICPATH = "/var/persistent/home/nao/audio/macarena.wav";
			ANIMATION = "Macarena_0";
			break;
		case "Dab":
			MUSICPATH = "/var/persistent/home/nao/audio/dab.wav";
			ANIMATION = "dab_3";
			break;
		case "Turndown":
			MUSICPATH = "/var/persistent/home/nao/audio/turndown.wav";
			ANIMATION = "turndown_0";
			break;
		}
	}

	private class MasterRobot extends DemoRobot {

		private ALMotion motion;
		private ALRobotPosture posture;
		private ALAudioPlayer player;
		private Animation animation;
		private ALBasicAwareness awareness;

		public MasterRobot(String name, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
		}

		@Override
		protected void frontTactil() {
			try {
				executeAll();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

		@Override
		protected void safeClose() throws Exception {
			player.stopAll();
			animation.stopAll();
			boolean post = posture.goToPosture("Crouch", .6f);
			awareness.startAwareness();
			motion.rest();

		}

		@Override
		public void execute() throws Exception {
			if (animation.isAnimating()) {
				player.stopAll();
				animation.stopAll();
				posture.goToPosture("Stand", .5f);
			} else {
				animation.playAnimation(ANIMATION);
				player.playFile(MUSICPATH);
			}
		}

		@Override
		protected void init() throws Exception {
			motion = new ALMotion(connection.getSession(name));
			posture = new ALRobotPosture(connection.getSession(name));
			player = new ALAudioPlayer(connection.getSession(name));
			awareness = new ALBasicAwareness(connection.getSession(name));
			awareness.stopAwareness();
			animation = new Animation(motion);
			boolean post = posture.goToPosture("Stand", 1f);
			motion.wakeUp();
			Thread.sleep(250);
		}
	}

	private class DanceRobot extends DemoRobot {

		private ALMotion motion;
		private ALRobotPosture posture;
		private Animation animation;
		private ALBasicAwareness awareness;

		public DanceRobot(String name, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
		}

		@Override
		protected void safeClose() throws Exception {
			animation.stopAll();
			awareness.startAwareness();
			boolean post = posture.goToPosture("Crouch", .6f);
			motion.rest();
		}

		@Override
		public void execute() throws Exception {
			if (animation.isAnimating()) {
				animation.stopAll();
				posture.goToPosture("Stand", .5f);
			} else {
				animation.playAnimation(ANIMATION);
			}
		}

		@Override
		protected void init() throws Exception {
			motion = new ALMotion(connect.getSession(name));
			posture = new ALRobotPosture(connect.getSession(name));
			awareness = new ALBasicAwareness(connect.getSession(name));
			awareness.stopAwareness();
			animation = new Animation(motion);
			posture.goToPosture("Stand", 1f);
			motion.wakeUp();
		}
	}

	@Override
	protected void run() throws Exception {

		for (int i = 0; i < robotNames.size(); i++) {
			String name = robotNames.get(i);
			if (name.equals(MASTERNAME)) {
				new initMaster(MASTERNAME, connection).start();
			} else {
				new initDance(name, connection).start();
			}
		}
		System.out.println("MASTER " + robotNames.get(0));
	}

	//initialize the dance robots
	private class initDance extends Thread {
		private String name;
		private SynchronizedConnectDemo connection;

		public initDance(String name, SynchronizedConnectDemo connection) {
			this.name = name;
			this.connection = connection;
		}

		@Override
		public void run() {
			try {
				robots.add(new DanceRobot(name, connection));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//Initialize the master robot
	private class initMaster extends Thread {
		private String name;
		private SynchronizedConnectDemo connection;

		public initMaster(String name, SynchronizedConnectDemo connection) {
			this.name = name;
			this.connection = connection;
		}

		@Override
		public void run() {
			try {
				robots.add(0, new MasterRobot(name, connection));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
