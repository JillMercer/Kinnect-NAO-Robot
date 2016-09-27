package edu.sru.thangiah.nao.awareness;

import java.util.ArrayList;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALAutonomousLife;
import com.aldebaran.qi.helper.proxies.ALAutonomousMoves;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALEngagementZones;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.DebugSettings;
import edu.sru.thangiah.nao.awareness.enums.Appendage;
import edu.sru.thangiah.nao.awareness.enums.EngagementModes;
import edu.sru.thangiah.nao.awareness.enums.LifeState;
import edu.sru.thangiah.nao.awareness.enums.TrackingModes;
import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.events.EventMethod;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;
import edu.sru.thangiah.nao.module.Module;


/** Provides methods and events dealing with the NAOs awareness of people
 *  and its surrounds. This includes touch, sound and motion.
 * @author Zachary Kearney && Justin Cather
 */
public class Awareness extends Module
		implements AwarenessInterface, EngagementInterface, StimulusInterface, BreathingInterface, DistanceInterface {

	// SEE
	// http://doc.aldebaran.com/2-1/nao/basic_channel_conversation.html#bchannel-text
	private ALAutonomousLife life;
	private ALBasicAwareness interaction;
	private ALMotion breathing;
	private ALAutonomousMoves listening;
	private ALMemory memory;
	private ALEngagementZones zones;
	private float zoneOne, zoneTwo;

	public Awareness(Session session) throws Exception {

		super(session);
		interaction = new ALBasicAwareness(session);
		breathing = new ALMotion(session);
		listening = new ALAutonomousMoves(session);
		life = new ALAutonomousLife(session);
		memory = new ALMemory(session);
		zones = new ALEngagementZones(session);
		zoneOne = zones.getFirstLimitDistance();
		zoneTwo = zones.getSecondLimitDistance();
	}
	
	@Override
	public void exit() throws Exception {

		reset();
		life.exit();
		listening.exit();
		breathing.exit();
		interaction.exit();
	}
	
	@Override
	public void reset() throws Exception {

		setAllBreathing(true);
		interaction.resetAllParameters();
	}

	public void setBreathEnabled(Appendage name, boolean enable) throws Exception {
		
		if (name != null)
			breathing.setBreathEnabled(name.name(), enable);
		else
			throw new IllegalArgumentException("Name can not be null.");
	}

	public boolean getBreathEnabled(Appendage name) throws Exception {

		if (name != null)
			return breathing.getBreathEnabled(name.name());
		else
			throw new IllegalArgumentException("Name can not be null.");

	}

	public void setIdlePostureEnabled(Appendage name, boolean enable) throws Exception {

		if(name != null)
			breathing.setIdlePostureEnabled(name.name(), enable);
		else
			throw new IllegalArgumentException("Name can not be null.");
	}

	public boolean getIdlePostureEnabled(Appendage name) throws Exception {

		if (name != null)
			return breathing.getIdlePostureEnabled(name.name());
		else
			throw new IllegalArgumentException("Name can not be null.");
	}

	public float[] getBreathConfig() throws Exception {

		ArrayList<Object> breath = (ArrayList<Object>) breathing.getBreathConfig();
		float BPM = (float) ((ArrayList<Object>) breath.get(0)).get(1);
		float AMP = (float) ((ArrayList<Object>) breath.get(0)).get(1);
		return new float[] { BPM, AMP };
	}

	public void setBreathConfig(float bpm, float amp) throws Exception {

		if ((bpm >= 5f && bpm <= 30f) && (amp >= 0f && amp <= 1)) {
			ArrayList<Object> BPM = new ArrayList<Object>();
			BPM.set(0, "Bpm");
			BPM.set(1, bpm);
			ArrayList<Object> AMP = new ArrayList<Object>();
			AMP.set(0, "Amplitude");
			AMP.set(1, amp);
			ArrayList<Object> config = new ArrayList<Object>();
			config.set(0, AMP);
			config.set(1, BPM);
			breathing.setBreathConfig(config);
		} else
			throw new Exception("Invalid Argument");

	}
	
	public void setExpressiveListening(boolean enable) throws Exception {

		listening.setExpressiveListeningEnabled(enable);

	}

	public boolean getExpressiveListening() throws Exception {

		return listening.getExpressiveListeningEnabled();

	}

	public void setBackgroundMotion(boolean enable) throws Exception {

		if (enable) {
			listening.setBackgroundStrategy("backToNeutral");
		} else if (!enable) {
			listening.setBackgroundStrategy("none");
		}

	}

	public boolean getBackgroundMotion() throws Exception {

		if (listening.getBackgroundStrategy().equals("backToNeutral")) {
			return true;
		}

		else if (listening.getBackgroundStrategy().equals("none")) {
			return false;
		}

		else {

			throw new Exception("Error");

		}

	}

	public void setAllBreathing(boolean enable) throws Exception {

		setBackgroundMotion(enable);
		setExpressiveListening(enable);
		setBreathEnabled(Appendage.Body, enable);

	}

	public void setAwareness(boolean enable) throws Exception {

		if (enable) {

			interaction.startAwareness();

		}

		else if (!enable) {

			interaction.stopAwareness();

		}
	}

	public boolean getAwareness() throws Exception {

		return interaction.isAwarenessRunning();
	}

	public void setSoundStimulus(boolean enable) throws Exception {

		interaction.setStimulusDetectionEnabled("Sound", enable);
	}

	public void setMoveStimulus(boolean enable) throws Exception {

		interaction.setStimulusDetectionEnabled("Movement", enable);
	}

	public void setTouchStimulus(boolean enable) throws Exception {

		interaction.setStimulusDetectionEnabled("Touch", enable);
	}

	public void setPeopleStimulus(boolean enable) throws Exception {

		interaction.setStimulusDetectionEnabled("People", enable);
	}

	public void setAllStimulus(boolean enable) throws Exception {

		interaction.setStimulusDetectionEnabled("Movement", enable);
		interaction.setStimulusDetectionEnabled("Touch", enable);
		interaction.setStimulusDetectionEnabled("Sound", enable);
		interaction.setStimulusDetectionEnabled("People", enable);
	}

	public boolean getSoundStimulus() throws Exception {

		return interaction.isStimulusDetectionEnabled("Sound");
	}

	public boolean getMoveStimulus() throws Exception {

		return interaction.isStimulusDetectionEnabled("Movement");
	}

	public boolean getTouchStimulus() throws Exception {

		return interaction.isStimulusDetectionEnabled("Touch");
	}

	public boolean getPeopleStimulus() throws Exception {

		return interaction.isStimulusDetectionEnabled("People");
	}

	public boolean getAllStimulus() throws Exception {

		if (getSoundStimulus() && getTouchStimulus() && getMoveStimulus() && getPeopleStimulus()) {
			return true;
		}

		else {

			return false;
		}
	}

	public void setEngagementMode(EngagementModes mode) throws Exception {
		
		if (mode != null)
			interaction.setEngagementMode(mode.name());
		else
			throw new IllegalArgumentException("Mode cannot be null.");
	}

	public EngagementModes getEngagementMode() throws Exception {
		
		String temp = interaction.getEngagementMode();
		EngagementModes mode = EngagementModes.valueOf(temp);
		return mode;		
	}

	public void setTrackingMode(TrackingModes mode) throws Exception {
		
		if (mode != null)
			interaction.setTrackingMode(mode.name());
		else
			throw new IllegalArgumentException("Mode cannot be null.");
	}

	public TrackingModes getTrackingMode() throws Exception {

		String temp = interaction.getTrackingMode();
		TrackingModes mode = TrackingModes.valueOf(temp);		
		return mode;
	}

	public void setLifeState(LifeState state) throws Exception {
		
		if (state != null)
			life.setState(state.name());
		else
			throw new IllegalArgumentException("Mode cannot be null.");
	}

	public LifeState getLifeState() throws Exception {

		String temp = life.getState();
		LifeState state = LifeState.valueOf(temp);
		return state;
	}

	public void setFirstZoneDistance(float distance) throws Exception {
		try {
			if (distance < zoneTwo) {
				zones.setFirstLimitDistance(distance);
			} else {
				throw new Exception("Tried to set ZoneOne distance to a value more than ZoneTwo's distance.");
			}
		} catch (CallError | InterruptedException e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
		}
	}

	public void setSecondZoneDistance(float distance) throws Exception {
		try {
			if (distance > zoneOne) {
				zones.setSecondLimitDistance(distance);
			} else {
				throw new Exception("Tried to set ZoneTwo distance to a value less than ZoneOne's distance.");
			}
		} catch (CallError | InterruptedException e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
		}
	}

	public ArrayList<Integer> getPeopleInZone1() {
		try {
			return (ArrayList<Integer>) memory.getData("EngagementZones/PeopleInZone1");
		} catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
			return null;
		}
	}

	/** Subscribes to the NAO event to detect people entering zone one.
	 * @param method The method to run when someone enters the zone.
	 * @return The EventPair to identify this event.
	 */
	public synchronized EventPair personEnteredFirstZone(EventMethod method) {
		EventPair event = new EventPair();
		event.eventName = NaoEvents.EngagementZones_PersonEnteredZone1;

		try {
			event.eventID = memory.subscribeToEvent(event.eventName, (value) -> {
				method.run();
			});
		} catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
			event = null;
		}

		return event;
	}

	/** Subscribes to the NAO event to detect people entering zone two.
	 * @param method The method to run when someone enters the zone.
	 * @return The EventPair to identify this event.
	 */
	public synchronized EventPair personEnteredSecondZone(EventMethod method) {
		EventPair event = new EventPair();
		event.eventName = NaoEvents.EngagementZones_PersonEnteredZone2;

		try {
			event.eventID = memory.subscribeToEvent(event.eventName, (value) -> {
				method.run();
			});
		} catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
			event = null;
		}

		return event;
	}

	/** Subscribes to the NAO event to detect people entering zone three.
	 * @param method The method to run when someone enters the zone.
	 * @return  The EventPair to identify this event.
	 */
	public synchronized EventPair personEnteredThirdZone(EventMethod method) {
		EventPair event = new EventPair();
		event.eventName = NaoEvents.EngagementZones_PersonEnteredZone3;

		try {
			event.eventID = memory.subscribeToEvent(event.eventName, (value) -> {
				method.run();
			});
		} catch (Exception e) {
			DebugSettings.printDebug(DebugSettings.ERROR, e.getMessage());
			event = null;
		}

		return event;
	}
}
