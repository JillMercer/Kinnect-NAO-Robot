package edu.sru.thangiah.nao.demo.old;

import edu.sru.thangiah.nao.connection.Connect;
import edu.sru.thangiah.nao.speech.texttospeech.Gestures;
import edu.sru.thangiah.nao.speech.texttospeech.TextToSpeech;
import tests.RobotConfig;

import java.util.Timer;
import java.util.TimerTask;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.RawApplication;
import com.aldebaran.qi.Session;

/**
 *
 * @author Justin Cather
 *
 */

public class TimedWave
{
    private static TextToSpeech speech;
    private static boolean isActive; //stop methods from interfering with each other
    private static final long timeBetweenRec = 30000; //30 seconds

    public static void timedWave(Session session) throws Exception
    {
    	speech = new TextToSpeech(session);
        isActive = false;

        // Timer setup.
        Timer timer = new Timer ();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run ()
            {
                try
                {
                    robotWave(session);
                }
                catch(Exception ex)
                {
                    System.out.println("Error with wave.");
                }
            }
        };

        // schedule the task to run starting now and then every timeBetweenRec...
        timer.schedule(task, 0l, timeBetweenRec);
    }

    private static void robotWave(Session session) throws Exception
    {
        if(!isActive)
        {
            isActive = true;

            System.out.println("Waving!");

            //greet the new person and tell them their shirt color
            speech.animatedSay("Hello, I am a NAO Robot.", Gestures.Hey_1);
            Thread.sleep(1500);

            isActive = false;
        }
    }

    public static void main(String[] args) throws InterruptedException, CallError, Exception
    {
    	Connect c = new Connect();
    	c.run();
        timedWave(c.getSession());
    }
}