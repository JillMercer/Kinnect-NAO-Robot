package edu.sru.thangiah.nao.demo.storytelling.robotandperson;

import com.aldebaran.qi.Application;
import com.aldebaran.qi.helper.proxies.ALMemory;

import edu.sru.thangiah.nao.events.NaoEvents;
import tests.RobotConfig;

public class Driver {
	
	public static String FILE = System.getProperty("user.dir") + "\\src\\edu\\sru\\thangiah\\nao\\demo\\storytelling\\Stories\\story1.xml";
	
	public static void main(String[] args) throws Exception{	
		RobotPersonInteraction story = new RobotPersonInteraction("192.168.0.109",FILE);
					
        story.run();
        System.out.println("END OF PROGRAM.");
	}
}
