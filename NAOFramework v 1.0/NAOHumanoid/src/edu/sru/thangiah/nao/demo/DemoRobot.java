package edu.sru.thangiah.nao.demo;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.thangiah.nao.connection.SynchronizedConnectDemo;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;


/**
 * Author: Zachary Kearney Last Edited, 5/5/2016
 * 
 * @author zrk1002 Abstract model for a DemoRobot used by the Demo class. Front
 *         tactil, rear tactil, and middle tactil are already subscribed to, and
 *         do not need resubscribed. ALMemory is accessed using memory.
 *         SynchronizedConnect is accessed using connect.
 *         
 * Updated: Sam R. T. Add hand events 8/15/2016
 *
 */
public abstract class DemoRobot {

	protected String name;
	protected SynchronizedConnectDemo connect;
	protected ALMemory memory;
	//head events
	protected EventPair front, middle, rear; 
	//Left hand events
	protected EventPair handLeftBack, handLeftLeft,handLeftRight;
	//Right hand events
	protected EventPair handRightBack, handRightRight,handRightLeft;
	private boolean isFin = false;


	public DemoRobot(String name, String appName, SynchronizedConnectDemo connect) throws Exception {
		this.name = name;
		this.connect = connect;
		// if(!connect.runApplication(name, appName)){
		// throw new Exception("ERROR: Application is already running on " +
		// name);
		// }

		memory = new ALMemory(connect.getSession(name));

		//Head events
		front = new EventPair(NaoEvents.FrontTactilTouched,
				memory.subscribeToEvent(NaoEvents.FrontTactilTouched, (value) -> {
					if ((Float) value > 0) {
						try {
							frontTactil();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));

		middle = new EventPair(NaoEvents.MiddleTactilTouched,
				memory.subscribeToEvent(NaoEvents.MiddleTactilTouched, (value) -> {
					if ((Float) value > 0) {
						try {
							middleTactil();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));

		rear = new EventPair(NaoEvents.RearTactilTouched,
				memory.subscribeToEvent(NaoEvents.RearTactilTouched, (value) -> {
					if ((Float) value > 0) {
						try {
							rearTactil();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
		
		//Left hand events
		handLeftBack = new EventPair(NaoEvents.HandLeftBackTouched,
				memory.subscribeToEvent(NaoEvents.HandLeftBackTouched, (value) -> {
					if ((Float) value > 0) {
						try {
							handLeftBack();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));

		handLeftLeft = new EventPair(NaoEvents.HandLeftLeftTouched,
				memory.subscribeToEvent(NaoEvents.HandLeftLeftTouched, (value) -> {
					if ((Float) value > 0) {
						try {
							handLeftLeft();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));

		handLeftRight = new EventPair(NaoEvents.HandLeftRightTouched,
				memory.subscribeToEvent(NaoEvents.HandLeftRightTouched, (value) -> {
					if ((Float) value > 0) {
						try {
							handLeftRight();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}));
		
		//Right hand events
				handRightBack = new EventPair(NaoEvents.HandRightBackTouched,
						memory.subscribeToEvent(NaoEvents.HandRightBackTouched, (value) -> {
							if ((Float) value > 0) {
								try {
									handRightBack();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}));

				handRightLeft = new EventPair(NaoEvents.HandRightLeftTouched,
						memory.subscribeToEvent(NaoEvents.HandRightLeftTouched, (value) -> {
							if ((Float) value > 0) {
								try {
									handRightLeft();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}));

				handRightRight = new EventPair(NaoEvents.HandRightRightTouched,
						memory.subscribeToEvent(NaoEvents.HandRightRightTouched, (value) -> {
							if ((Float) value > 0) {
								try {
									handRightRight();
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}));

		init();
		System.out.println(name + " ready!");
	}

	/**
	 * Define frontTactil method here.
	 */
	protected void frontTactil() {
		System.out.println("Front: NO METHOD DECLARED");
	}

	/**
	 * Define middleTactil method here.
	 */
	protected void middleTactil() {
		System.out.println("Middle: NO METHOD DECLARED");
	}
	
	/**
	 * Define rearTactil method here.
	 */
	protected void rearTactil() {
		System.out.println("Rear: NO METHOD DECLARED");
	}

	/**
	 * Define handLeftBack method here.
	 */
	protected void handLeftBack() {
		System.out.println("HandLeftBack: NO METHOD DECLARED");
	}
	
	/**
	 * Define handLeftLeftmethod here.
	 */
	protected void handLeftLeft() {
		System.out.println("HandLeftLeft: NO METHOD DECLARED");
	}

	/**
	 * Define handLeftRight method here.
	 */
	protected void handLeftRight() {
		System.out.println("HandLeftRight: NO METHOD DECLARED");
	}

	/**
	 * Define handRightBack method here.
	 */
	protected void handRightBack() {
		System.out.println("HandRightBack: NO METHOD DECLARED");
	}
	
	/**
	 * Define handRightRightmethod here.
	 */
	protected void handRightRight() {
		System.out.println("HandRightRight: NO METHOD DECLARED");
	}

	/**
	 * Define handRightLeft method here.
	 */
	protected void handRightLeft() {
		System.out.println("HandRightLeft: NO METHOD DECLARED");
	}


	/**
	 * Stops the robot demo.
	 * 
	 * @throws Exception
	 */
	public final void stop() throws Exception {
		safeClose();
		unsubscribe();
		isFin = true;
	}

	/**
	 * Methods to close the robot safely and securely. Must unsubscribe to all
	 * subscribed events, and undo any modifications made to robot settings.
	 * 
	 * @throws Exception
	 */
	protected abstract void safeClose() throws Exception;

	/**
	 * Initialize modules and subscriptions here. Called automatically.
	 * 
	 * @throws Exception
	 */
	protected abstract void init() throws Exception;

	/**
	 * Initialize the execute method here. This is the method called to all
	 * robots at once with the execute all method from demo.
	 * 
	 * @throws Exception
	 */
	public abstract void execute() throws Exception;

	private final void unsubscribe() throws InterruptedException, CallError {
		//unsubscribe head events
		memory.unsubscribeToEvent(front.eventID);
		memory.unsubscribeToEvent(middle.eventID);
		//unsubscribe left hand events
		memory.unsubscribeToEvent(handLeftBack.eventID);
		memory.unsubscribeToEvent(handLeftLeft.eventID);
		memory.unsubscribeToEvent(handLeftRight.eventID);
		//unsubscribe right  events
		memory.unsubscribeToEvent(handRightBack.eventID);
		memory.unsubscribeToEvent(handRightLeft.eventID);
		memory.unsubscribeToEvent(handRightRight.eventID);
	}

	public final boolean isFin() {
		return isFin;
	}

	public final String name() {
		return name;
	}
}
