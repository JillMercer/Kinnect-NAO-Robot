package edu.sru.thangiah.nao.demo;

import java.util.ArrayList;
import java.util.List;

import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALTracker;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;

/**
 * Author: Zachary Kearney Last Edited, 5/5/2016
 * 
 * @author zrk1002 Tracking demo. Pressing the front tactil initializes
 *         tracking. Pressing the rear tactil exits.
 *
 */

public class Track extends Demo {

	private String target = "RedBall";
	private Object param = .06f;
	private String mode = "Head";
	private String robotName = "";

	public Track(SynchronizedConnectDemo connection) throws Exception {
		super(connection);

		String[] tar = new String[] { "RedBall", "Sound", "Face" };

		String[] move = new String[] { "Move", "Head" };

		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog(connection);
		dialog.addOption("Target", tar);
		dialog.addOption("Move", move);
		dialog.setVisible(true);

		String[] options = dialog.getOptions();
		robotName = dialog.getSelectedName();
		robotNames.add(robotName);
		target = options[0];
		mode = options[1];

		demoName = "Track: " + target + " " + mode;
		switch (target) {
		case "RedBall":
			this.target = "RedBall";
			param = .06f;
			break;
		case "Sound":
			this.target = "Sound";
			ArrayList<Float> args = new ArrayList<Float>();
			args.add(2f);
			args.add(.3f);
			param = args;
			break;
		case "Face":
			this.target = "Face";
			param = .15f;
			break;
		default:
			throw new Exception("ERR INVALID TARGET");
		}
	}

	@Override
	protected void run() throws Exception {
		robots.add(new TrackRobot(robotName, connection));
	}

	private class TrackRobot extends DemoRobot {

		private ALMotion motion;
		private ALTracker tracker;
		private ALRobotPosture posture;
		private ALBasicAwareness awareness;

		private boolean tracking = false;
		boolean pointing = false;
		boolean pointingLeft = false;
		boolean pointingRight = false;
		private EventPair right, left;

		public TrackRobot(String name, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void safeClose() throws Exception {
			if (tracking) {
				tracking = false;
				tracker.stopTracker();
				tracker.unregisterAllTargets();
			}
			awareness.startAwareness();
			memory.unsubscribeToEvent(right.eventID);
			memory.unsubscribeToEvent(left.eventID);
			boolean post = posture.goToPosture("Crouch", .6f);
			motion.rest();
		}

		@Override
		protected void init() throws Exception {

			right = new EventPair(NaoEvents.HandRightBackTouched);
			left = new EventPair(NaoEvents.HandLeftBackTouched);

			motion = new ALMotion(connection.getSession(name));
			tracker = new ALTracker(connection.getSession(name));
			posture = new ALRobotPosture(connection.getSession(name));
			awareness = new ALBasicAwareness(connection.getSession(name));

			motion.wakeUp();

			tracker.setMaximumDistanceDetection(2f);
			tracker.setMode(mode);
			tracker.unregisterAllTargets();

			motion.setBreathEnabled("Body", false);

			right.eventID = right(right);

			left.eventID = left(left);
		}

		@Override
		public void execute() throws Exception {
			if (tracking) {
				tracking = false;
				tracker.stopTracker();
				tracker.unregisterAllTargets();
				boolean post = posture.goToPosture("Stand", .6f);
				awareness.startAwareness();
				motion.wakeUp();
			} else {
				tracker.setMode(mode);
				tracker.registerTarget(target, param);
				awareness.stopAwareness();
				Thread.sleep(50);
				if (mode.equals("Move")) {
					motion.moveInit();
					Thread.sleep(50);
				}
				tracking = true;
				System.out.println("Tracking");
				tracker.track(target);
			}
		}

		@Override
		protected void frontTactil() {
			try {
				execute();
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
				e.printStackTrace();
			}
		}

		private long right(EventPair eve) throws Exception {
			return memory.subscribeToEvent(eve.eventName, (value) -> {
				if ((Float) value > 0) {
					try {
						pointRight();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		private long left(EventPair eve) throws Exception {
			return memory.subscribeToEvent(eve.eventName, (value) -> {
				if ((Float) value > 0) {
					try {
						pointLeft();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}

		private void pointRight() throws Exception {
			if (tracking && !pointingRight) {
				List<Float> stiff = motion.getStiffnesses("RArm");
				motion.setStiffnesses("RArm", 1f);
				pointingRight = true;
				ArrayList<Float> positions = new ArrayList<Float>();
				positions = (ArrayList<Float>) motion.getAngles("RArm", true);
				tracker.pointAt("RArm", tracker.getTargetPosition(2), 2, .4f);
				Thread.sleep(1200);
				motion.setAngles("RArm", positions, .6f);
				Thread.sleep(400);
				motion.setStiffnesses("RArm", stiff.get(0));
				pointingRight = false;
			}
		}

		private void pointLeft() throws Exception {
			if (tracking && !pointingLeft) {
				List<Float> stiff = motion.getStiffnesses("LArm");
				motion.setStiffnesses("LArm", 1f);
				pointingLeft = true;
				ArrayList<Float> positions = new ArrayList<Float>();
				positions = (ArrayList<Float>) motion.getAngles("LArm", true);
				tracker.pointAt("LArm", tracker.getTargetPosition(2), 2, .4f);
				Thread.sleep(1200);
				motion.setAngles("LArm", positions, .6f);
				Thread.sleep(400);
				motion.setStiffnesses("LArm", stiff.get(0));
				pointingLeft = false;
			}
		}
	}
}
