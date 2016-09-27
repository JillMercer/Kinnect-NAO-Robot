package edu.sru.thangiah.nao.demo.boredom;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.limbs.Hip;

import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InterestHip extends Hip 
{

	private JFrame frame;
	private int hipVal;
	private static ALMotion motion;
	private static ALBasicAwareness awareness;
	private static boolean running = true;

	public boolean isRunning() 
	{
		return running;
	}

	public static void setRunning(boolean running) 
	{
		InterestHip.running = running;
	}

	public InterestHip(Session session) throws Exception
	{
		initialize(session);

	}

	private void initialize(Session session) throws Exception
	{
		motion = new ALMotion(session);
		awareness = new ALBasicAwareness(session);
		awareness.stopAwareness();
		motion.setBreathEnabled("All", false);

		try
		{
			setHipDefault(motion);
		}
		catch(CallError | InterruptedException e)
		{
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 666, 436);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent arg0) 
			{
				try
				{
					Thread.sleep(2000);
				} 
				catch (InterruptedException e1) 
				{
					e1.printStackTrace();
				}

				try 
				{
					awareness.startAwareness();
					motion.wakeUp();
					motion.setBreathEnabled("All", true);
				} 
				catch (CallError | InterruptedException e)
				{
					e.printStackTrace();
				}
				setRunning(false);
			}
		});
		frame.getContentPane().setLayout(null);

		JSlider hipSlider = new JSlider(-100,100);
		hipSlider.setMajorTickSpacing(10);
		hipSlider.setPaintTicks(true);
		hipSlider.setPaintLabels(true);
		hipSlider.setSnapToTicks(true);
		hipSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent arg0) 
			{
				if(!hipSlider.getValueIsAdjusting())
				{
					hipVal = hipSlider.getValue();

					try 
					{
						setHipVal(hipVal, motion);
					} 
					catch (CallError | InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
			}
		});
		hipSlider.setBounds(12, 84, 624, 55);
		frame.getContentPane().add(hipSlider);

		JLabel lblHip = new JLabel("Hip");
		lblHip.setBounds(316, 152, 28, 16);
		frame.getContentPane().add(lblHip);

		JButton btnDefualt = new JButton("Go to Defualt");
		btnDefualt.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent arg0) 
			{
				try
				{
					setHipDefault(motion);
				}
				catch(CallError | InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		});
		btnDefualt.setBounds(277, 181, 97, 25);
		frame.getContentPane().add(btnDefualt);

		frame.setVisible(true);
	}
}
