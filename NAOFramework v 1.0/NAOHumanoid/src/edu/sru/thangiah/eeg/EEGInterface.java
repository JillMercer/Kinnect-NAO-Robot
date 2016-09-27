package edu.sru.thangiah.eeg;

import java.util.ArrayList;

public interface EEGInterface {
	public String getLastCommand();
	public ArrayList<Double> getData();
	public void trainCommand(String cmdId);
	public void startEEGProcessing();
}
