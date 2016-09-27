package edu.sru.thangiah.eeg;

import java.util.*;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

import edu.sru.thangiah.eeg.emotiv.Edk;
import edu.sru.thangiah.eeg.emotiv.Edk.EE_DataChannels_t;
import edu.sru.thangiah.eeg.emotiv.EdkErrorCode;
import edu.sru.thangiah.eeg.emotiv.EmoState;
import edu.sru.thangiah.eeg.emotiv.EmoState.EE_CognitivAction_t;

/**
 * This class contains the methods and attributes related to the 
 * processing of the EEG Data retrieved from the Emotiv EEG 
 * headset.
 * 
 * Much of this class has been adapted from Marc's EEGprocessing class.
 * 
 * @author Dan Martin
 *
 */

public class EEG implements EEGInterface {

	/**
	 * Number of possible commands that can be used through the Emotiv Sdk
	 */
	private static final int NUM_COMMANDS = 14;
	/**
	 * Pointer to the current state of the Emotiv Device
	 */
	private Pointer eState;
	private Pointer emoState;
	private Pointer eEvent;
	/**
	 * An array that represents the available actions to be taken
	 */
	public static Vector<EE_CognitivAction_t> cognitivActionList;
	
	public static Vector<EE_DataChannels_t> dataChannels;
	/**
	 * An array that holds the information regarding the status of an action
	 */
	public static Boolean[] cognitivActionsEnabled = new Boolean[NUM_COMMANDS];
	/**
	 * String array holding human readable forms of the battery power
	 */
	private String[] BatteryPower = { "None", "Critical", "Low", "Medium",
			"High" };
	/**
	 * String array holding human readable forms of the wireless signal
	 */
	private String[] WirelessSignal = { "Poor", "Fair", "Good", "Great" };
	/**
	 * String representing the human readable battery power
	 */
	private String currentBatteryPower;
	/**
	 * String representing the human readable wireless signal
	 */
	private String currentWirelessSignal;
	/**
	 * Int representing the current charge level
	 */
	private IntByReference chargeLevel = new IntByReference();
	/**
	 * Int representing the max charge level
	 */
	private IntByReference maxCharge = new IntByReference(5);
	private boolean done = false;
	private int state = 0;
	private IntByReference userID = new IntByReference(0);
	private int count = 0;
	private Pointer hData;
	private IntByReference nSamplesTaken = new IntByReference(0);
	private int array[] = new int[5];
	private boolean readyToCollect = false;
	
	private ArrayList<Double> dataSignal;
	private String lastCommand = "Neutral";
	private Vector<String> commandIds = new Vector<String>();
	private int commandNum = 0;

	
	public EEG(){
		cognitivActionList = new Vector<EE_CognitivAction_t>();
		dataChannels = new Vector<EE_DataChannels_t>(14);
		
		cognitivActionList.add(EE_CognitivAction_t.COG_NEUTRAL);
		cognitivActionList.add(EE_CognitivAction_t.COG_PUSH);
		cognitivActionList.add(EE_CognitivAction_t.COG_PULL);
		cognitivActionList.add(EE_CognitivAction_t.COG_LIFT);
		cognitivActionList.add(EE_CognitivAction_t.COG_DROP);
		cognitivActionList.add(EE_CognitivAction_t.COG_LEFT);
		cognitivActionList.add(EE_CognitivAction_t.COG_RIGHT);
		cognitivActionList.add(EE_CognitivAction_t.COG_ROTATE_LEFT);
		cognitivActionList.add(EE_CognitivAction_t.COG_ROTATE_RIGHT);
		cognitivActionList.add(EE_CognitivAction_t.COG_ROTATE_CLOCKWISE);
		cognitivActionList.add(EE_CognitivAction_t.COG_ROTATE_COUNTER_CLOCKWISE);
		cognitivActionList.add(EE_CognitivAction_t.COG_ROTATE_FORWARDS);
		cognitivActionList.add(EE_CognitivAction_t.COG_ROTATE_REVERSE);
		cognitivActionList.add(EE_CognitivAction_t.COG_DISAPPEAR);
		
		dataChannels.add(EE_DataChannels_t.ED_AF3);
		dataChannels.add(EE_DataChannels_t.ED_F7);
		dataChannels.add(EE_DataChannels_t.ED_F3);
		dataChannels.add(EE_DataChannels_t.ED_FC5);
		dataChannels.add(EE_DataChannels_t.ED_T7);
		dataChannels.add(EE_DataChannels_t.ED_P7);
		dataChannels.add(EE_DataChannels_t.ED_O1);
		dataChannels.add(EE_DataChannels_t.ED_O2);
		dataChannels.add(EE_DataChannels_t.ED_P8);
		dataChannels.add(EE_DataChannels_t.ED_T8);
		dataChannels.add(EE_DataChannels_t.ED_FC6);
		dataChannels.add(EE_DataChannels_t.ED_F4);
		dataChannels.add(EE_DataChannels_t.ED_F8);
		dataChannels.add(EE_DataChannels_t.ED_AF4);
		
		eState = Edk.INSTANCE.EE_EmoStateCreate();
		emoState = EmoState.INSTANCE.ES_Create();
		eEvent = Edk.INSTANCE.EE_EmoEngineEventCreate();

		cognitivActionsEnabled[0] = true;
		for (int i = 1; i < cognitivActionList.size(); i++) {
			cognitivActionsEnabled[i] = false;
		}
		if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK
				.ToInt()) {
			System.out
					.println("ErrorEmotiv Engine Failed to start up, now exiting");
			System.exit(-1);
		}
	}
	
	@Override
	public void trainCommand(String cmdId) {
		int commandToTrain = 0;
		if(commandNum == 0){
			System.out.println("Train neutral first with EEG.trainNeutral()");
		}
		else{
			Edk.INSTANCE.EE_CognitivSetTrainingControl(0,
					Edk.EE_CognitivTrainingControl_t.COG_START.getType());
			if(commandNum < NUM_COMMANDS){
				if(!commandIds.contains(cmdId)){
					commandIds.add(cmdId);
					commandToTrain = commandNum;
					commandNum++;
				}
				else{
					commandToTrain = commandIds.indexOf(cmdId);
				}
				try{
					System.out.println(commandToTrain);
					EnableCognitivAction(cognitivActionList.get(commandToTrain), true);
					EnableCognitivActionsList();
					StartTrainingCognitiv(cognitivActionList.get(commandToTrain));
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	public void trainNeutral(){
		if(!commandIds.contains("Neutral")){
			commandIds.add(0, "Neutral");
			commandNum++;
		}
		Edk.INSTANCE.EE_CognitivSetTrainingControl(0,
				Edk.EE_CognitivTrainingControl_t.COG_START.getType());
		try{
			EnableCognitivAction(cognitivActionList.get(0), true);
			EnableCognitivActionsList();
			StartTrainingCognitiv(cognitivActionList.get(0));
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void EnableCognitivActionsList() {
		long cognitivActions = 0x0000;
		for (int i = 1; i < cognitivActionList.size(); i++) {
			if (cognitivActionsEnabled[i]) {
				cognitivActions = cognitivActions
						| ((long) cognitivActionList.get(i).ToInt());
			}
		}
		Edk.INSTANCE.EE_CognitivSetActiveActions(0, cognitivActions);

	}
	public static void EnableCognitivAction(
			EmoState.EE_CognitivAction_t cognitivAction, Boolean iBool) {
		for (int i = 1; i < cognitivActionList.size(); i++) {
			if (cognitivAction == cognitivActionList.get(i)) {
				cognitivActionsEnabled[i] = iBool;
			}
		}

	}
	public static void StartTrainingCognitiv(EmoState.EE_CognitivAction_t cognitivAction) {
		if (cognitivAction == EmoState.EE_CognitivAction_t.COG_NEUTRAL) {
			Edk.INSTANCE.EE_CognitivSetTrainingAction(0,
					EmoState.EE_CognitivAction_t.COG_NEUTRAL.ToInt());
			Edk.INSTANCE.EE_CognitivSetTrainingControl(0,
					Edk.EE_CognitivTrainingControl_t.COG_START.getType());
		} else {
			for (int i = 1; i < cognitivActionList.size(); i++) {
				if (cognitivAction == cognitivActionList.get(i)) {
					if (cognitivActionsEnabled[i]) {
						Edk.INSTANCE.EE_CognitivSetTrainingAction(0,
								cognitivAction.ToInt());
						Edk.INSTANCE.EE_CognitivSetTrainingControl(0,
								Edk.EE_CognitivTrainingControl_t.COG_START
										.getType());
					}

				}
			}
		}

	}
	@Override
	public String getLastCommand() {
		return lastCommand;
	}

	@Override
	public synchronized ArrayList<Double> getData() {
		return dataSignal;
	}

	public static void main(String args[]){
		
	}
	public void startEEGProcessing() {
		Thread t = new Thread(new processingHandler());
		t.start();
	}
	class processingHandler implements Runnable {
		@Override
		public void run() {
			dataSignal = new ArrayList<Double>();
			hData = Edk.INSTANCE.EE_DataCreate();
			Edk.INSTANCE.EE_DataSetBufferSizeInSec(1);
			EmoState.INSTANCE.ES_GetBatteryChargeLevel(eState, chargeLevel,
					maxCharge);
			while(true){
				state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);
				if (state == EdkErrorCode.EDK_OK.ToInt()) {
					int eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
					Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);
				
					if (userID != null) {
						Edk.INSTANCE.EE_DataAcquisitionEnable(
								userID.getValue(), true);
						readyToCollect = true;
					}
			
					if (eventType == Edk.EE_Event_t.EE_CognitivEvent.ToInt()) {
						int cogType = Edk.INSTANCE.EE_CognitivEventGetType(eEvent);

						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingStarted.getType()) {
							System.out.println("Training started");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingCompleted.getType()) {
							System.out.println("Training completed");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingSucceeded.getType()) {
							Edk.INSTANCE.EE_CognitivSetTrainingControl(0,
									Edk.EE_CognitivTrainingControl_t.COG_ACCEPT.getType());
							System.out.println("Training succeeded");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingFailed.getType()) {
							System.out.println("Training failed");
						}
						if (cogType == Edk.EE_CognitivEvent_t.EE_CognitivTrainingRejected.getType()) {
							System.out.println("Training rejected");
						}
					}
					//check if an action has taken place
					if (eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()) {
						Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent,eState);
						int action = EmoState.INSTANCE.ES_CognitivGetCurrentAction(eState);
						double power = EmoState.INSTANCE.ES_CognitivGetCurrentActionPower(eState);
						
						if (power > .5) {
							for(int i = 0; i < NUM_COMMANDS; i++){
								if (action == cognitivActionList.get(i).ToInt()) {
									lastCommand = commandIds.get(i);
								}
							}
						}// end power
					}
					
				}
				else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
					System.out.println("Internal error in Emotiv Engine!");
					System.exit(1);
				}
				
				if(readyToCollect){
					Edk.INSTANCE.EE_DataUpdateHandle(0, hData);
					Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);
					if (nSamplesTaken.getValue() != 0) {
						double[] data = new double[nSamplesTaken.getValue()];
						dataSignal.clear();
						for(int i = 0; i < dataChannels.size(); i++){
							Edk.INSTANCE.EE_DataGet(hData,
									dataChannels.get(i).ordinal(),data,nSamplesTaken.getValue());
							dataSignal.add(i, data[0]);
						}
					}
				}
			}//end while
		}
	}
}
