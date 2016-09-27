package com.aldebaran.demo;


import com.aldebaran.qimessaging.Application;

public class QiApplicationThread extends Thread {
	Application app;
	
	public QiApplicationThread (Application app) {
		if(app == null) {
			throw new NullPointerException("QiAppThread cannot be instaniated with an Application null pointer!");
		}
		this.app = app;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(app == null) {
			throw new NullPointerException("QiAppThread cannot be run with an Application null pointer!");
		}
		app.run();
	}
}
