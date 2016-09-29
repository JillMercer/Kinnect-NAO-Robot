package edu.sru.thangiah.nao.demo;

/** Author: Zachary Kearney
Last Edited, 5/5/2016
* @author zrk1002
* Automated Motion demo utilizing sonar to move robot autonomously througout the room.
*
*/

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMotion;
import com.aldebaran.qi.helper.proxies.ALRobotPosture;
import com.aldebaran.qi.helper.proxies.ALSonar;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.demo.gui.ApplicationsOptionDialog;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;

public class AutomatedMotion extends Demo {

	private String robotName;

	public AutomatedMotion(SynchronizedConnectDemo connection) throws Exception {
		super(connection);
		demoName = "Automated Motion";
		ApplicationsOptionDialog dialog = new ApplicationsOptionDialog(connection);
		dialog.setVisible(true);
		robotName = dialog.getSelectedName();
		robotNames.add(robotName);
		System.out.println("NAME" + robotName);
		System.out.println(robotNames.size());
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void run() throws Exception {
		robots.add(new AutomatedRobot(robotName, connection));
	}

	private class AutomatedRobot extends DemoRobot {

		private EventPair footR, footL;
		private ALSonar sonar;
		private ALRobotPosture posture;
		private ALMotion motion;
		private ALBasicAwareness awareness;
		private static final String sonarSub = "Sonar";
		private static final String left = "Device/SubDeviceList/US/Left/Sensor/Value";
		private static final String right = "Device/SubDeviceList/US/Right/Sensor/Value";
		private boolean moving = false;
		private boolean bumperTouched = false;

		public AutomatedRobot(String name, SynchronizedConnectDemo connect) throws Exception {
			super(name, demoName, connect);
			// TODO Auto-generated constructor stub
		}

		@Override
		protected void safeClose() throws Exception {

			motion.stopMove();
			moving = false;
			boolean post = posture.goToPosture("Stand", .8f);
			post = posture.goToPosture("Crouching", .8f);
			sonar.unsubscribe(sonarSub);
			memory.unsubscribeToEvent(footR.eventID);
			memory.unsubscribeToEvent(footL.eventID);
			motion.rest();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void init() throws Exception {

			motion = new ALMotion(connection.getSession(name));
			sonar = new ALSonar(connection.getSession(name));
			posture = new ALRobotPosture(connection.getSession(name));
			awareness = new ALBasicAwareness(connection.getSession(name));

			motion.wakeUp();
			boolean post = posture.goToPosture("Stand", .8f);
			sonar.subscribe(sonarSub);

			footR = new EventPair(NaoEvents.RightBumperPressed);
			footR.eventID = memory.subscribeToEvent(footR.eventName, (value) -> {
				if ((Float) value > 0) {
					if (moving) {
						bumperTouched = true;
					}
				}
			});

			footL = new EventPair(NaoEvents.LeftBumperPressed);
			footL.eventID = memory.subscribeToEvent(footL.eventName, (value) -> {
				if ((Integer) value > 0) {
					if (moving) {
						bumperTouched = true;
					}
				}
			});

		}

		@Override
		public void execute() throws Exception {

			if (!moving) {
				moving = true;
				awareness.stopAwareness();
				motion.moveInit();
				motion.setAngles("HeadYaw", 0f, .8f);
				motion.setAngles("HeadPitch", 0f, .8f);
				new Thread(new Move()).start();
			} else {
				moving = false;
				motion.stopMove();
				awareness.startAwareness();
				boolean post = posture.goToPosture("Stand", .8f);

			}
		}

		private class Move implements Runnable {

			@Override
			public void run() {
				while (moving) {
					try {
						float leftData = (float) memory.getData(left);
						float rightData = (float) memory.getData(right);
						System.out.println("LEFT");
						System.out.println(leftData);
						System.out.println("RIGHT");
						System.out.println(rightData);
						if (bumperTouched) {
							bumperTouched = false;
							motion.stopMove();
							Thread.sleep(200);
							motion.moveTo(-.1f, 0f, 0f);
							motion.waitUntilMoveIsFinished();
							if (leftData < rightData) {
								motion.moveTo(0f, 0f, 1f);
							} else {
								motion.moveTo(0f, 0f, -1f);
							}
							motion.waitUntilMoveIsFinished();
							Thread.sleep(150);
						} else {
							if (leftData > .5f && rightData > .5f) {
								motion.moveToward(1f, 0f, 0f);
							} else if (leftData > .5f && rightData > .25f) {
								motion.moveToward(.75f, 0f, .75f);
							} else if (leftData > .25f && rightData > .5f) {
								motion.moveToward(.75f, 0f, -.75f);
							} else if (leftData > .25f && rightData > .25f) {
								motion.moveToward(.5f, 0f, 0f);
							} else if (leftData < .25f && rightData < .25f) {
								if (leftData < rightData) {
									motion.moveToward(-.25f, 0f, -.75f);
								} else {
									motion.moveToward(-.25f, 0f, 75f);
								}
							}
						}
						Thread.sleep(100);
						if (!moving) {
							motion.stopMove();
						}
					} catch (CallError e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

}
