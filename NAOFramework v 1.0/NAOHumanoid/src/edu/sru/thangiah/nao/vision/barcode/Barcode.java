package edu.sru.thangiah.nao.vision.barcode;

import java.util.Iterator;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBarcodeReader;
import com.aldebaran.qi.helper.proxies.ALMemory;
import com.aldebaran.qi.helper.proxies.ALTextToSpeech;

import edu.sru.thangiah.nao.events.EventMethod;
import edu.sru.thangiah.nao.events.EventPair;
import edu.sru.thangiah.nao.events.NaoEvents;
import edu.sru.thangiah.nao.speech.texttospeech.TextToSpeech;
import edu.sru.thangiah.nao.system.HouseKeeping;
import edu.sru.thangiah.nao.system.MemoryHelper;
import tests.RobotConfig;



/**
 * A class to utilize the NAO ALBarcodeReader methods and events.
 * @author Justin Cather
 */

public class Barcode {
	
	private static boolean isScanning = false;
	private static long ID;
	private ALBarcodeReader barcode = null;
	private ALMemory memory = null;
	private String name = "BarcodeClass";

	/**
	 * @param name This is where the data will be stored on the NAO.
	 */
	public Barcode(String name){
		this.name = name;
	}
	
	/** Gets the ALBarcodeReader that this instance of Barcode is using.
	 * @return Returns the ALBarcodeReader if start() has been called previously, 
	 * else it returns null.
	 */
	public ALBarcodeReader getALBarcode(){
		return barcode;
	}
	
	/**
	 * Subscribes to the BarCodeReader event. Whenever the event occurs, the NAO will
	 * try and say the data from the barcode. Call after application.start().
	 * @param session The session to connect to the robot with.
	 * @return The event ID of the subscribed BarcodeReader event.
	 * @throws Exception
	 */
	public EventPair scanForBarcodes(Session session, EventMethod method) throws Exception{
		EventPair event = new EventPair();
		barcode = new ALBarcodeReader(session);
		memory = new ALMemory(session);
		barcode.subscribe(name);
		
		event.eventName = NaoEvents.BarcodeReader_BarcodeDetected;
		event.eventID = memory.subscribeToEvent(NaoEvents.BarcodeReader_BarcodeDetected, (value)-> {
			if (!isScanning) {	
				isScanning = true;
				method.run();	
				isScanning = false;
			}
		});
		
		return event;
	}
	
	public static void main(String[] args) throws Exception{
		String memName = "BarcodeTest";
		
		Application app = new Application(args, RobotConfig.getConnectionString());	
		app.start();
		ALMemory memory = new ALMemory(app.session());
		HouseKeeping collect = new HouseKeeping(app.session());
		ALTextToSpeech tts = new ALTextToSpeech(app.session());
		
		// Method to pass to Barcode class to use
		// when a barcode is detected.
		EventMethod method = new EventMethod() {
			@Override
			public void run() {
				String data = MemoryHelper.getBarcodeData(app.session());
				if (data != null) {
					try {
						tts.say(data);
					} catch (CallError | InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		
		Barcode qr =  new Barcode(memName);
		EventPair x = qr.scanForBarcodes(app.session(), method);
		long y = memory.subscribeToEvent(NaoEvents.MiddleTactilTouched, (value) -> {
			if (((Float) value) > 0.00) {
				System.out.println("_________Application is Stopping__________");
                app.stop();
			}
		});
		
        app.run();
        collect.addEvent(x);
        collect.addEvent(NaoEvents.MiddleTactilTouched, y);
        collect.reset();
	}
}
