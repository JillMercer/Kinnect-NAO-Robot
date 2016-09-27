package edu.sru.thangiah.nao.demo.old;

import com.aldebaran.qi.Session;
import edu.sru.thangiah.nao.speech.texttospeech.TextToSpeech;

public class GoodbyeWorld {

	//Demo for the NAO to say goodBye World
	public static void goodbyeWorld(Session session)
	{
			//Nao utters ...			
			try {
				TextToSpeech txtToSpeech = new TextToSpeech(session);
				txtToSpeech.say("Goodbye World");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
