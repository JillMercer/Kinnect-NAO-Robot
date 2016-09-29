package edu.sru.thangiah.nao.demo.boredom;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.Session;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.balance.Balance;
import edu.sru.thangiah.nao.movement.limbs.Legs;

import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class InterestLeg extends Legs 
{

	private JFrame frame;

	private int lHipRoll;
	private int lHipPitch;
	private int lKneePitch;
	private int lAnklePitch;
	private int lAnkleRoll;
	private int rHipRoll;
	private int rHipPitch;
	private int rKneePitch;
	private int rAnklePitch;
	private int rAnkleRoll;
	private static Balance balance;
	private static ALMotion motion;
	private static ALBasicAwareness awareness;
	private static boolean running = true;

	public InterestLeg(Session session) throws Exception 
	{
		initialize(session);
	}

	private void initialize(Session session) throws Exception 
	{
		balance = new Balance(session);
		balance.wbEnable(true);
		balance.wbEnableBalanceConstraint(true, "Legs");
		motion = new ALMotion(session);
		awareness = new ALBasicAwareness(session);
		awareness.stopAwareness();
		motion.setBreathEnabled("All", false);

		frame = new JFrame();
		frame.setBounds(100, 100, 1238, 577);
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

		JSlider lHipRollSlider = new JSlider(-100, 100);
		lHipRollSlider.setBounds(12, 37, 563, 52);
		lHipRollSlider.setMajorTickSpacing(10);
		lHipRollSlider.setPaintTicks(true);
		lHipRollSlider.setPaintLabels(true);
		lHipRollSlider.setSnapToTicks(true);
		lHipRollSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent arg0) 
			{
				if(!lHipRollSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("LLeg", 1.0f);
					}
					catch(InterruptedException | CallError e1)
					{
						e1.printStackTrace();
					}

					lHipRoll = lHipRollSlider.getValue();
				}

				System.out.println("Left Hip Roll Slider = " + lHipRoll);

				try 
				{
					setLHipRoll(lHipRoll, motion, balance);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(lHipRollSlider);

		JSlider rHipRollSlider = new JSlider(-100, 100);
		rHipRollSlider.setBounds(606, 37, 602, 52);
		rHipRollSlider.setMajorTickSpacing(10);
		rHipRollSlider.setPaintTicks(true);
		rHipRollSlider.setPaintLabels(true);
		rHipRollSlider.setSnapToTicks(true);
		rHipRollSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent arg1) 
			{
				if(!rHipRollSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("RLeg", 1.0f);
					}
					catch(InterruptedException | CallError e1)
					{
						e1.printStackTrace();
					}

					rHipRoll = rHipRollSlider.getValue();
				}

				System.out.println("Right Hip Roll Slider = " + rHipRoll);

				try 
				{
					setRHipRoll(rHipRoll, motion, balance);
				} 
				catch (InterruptedException | CallError e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		frame.getContentPane().add(rHipRollSlider);

		JLabel lblLeftHipRoll = new JLabel("Left Hip Roll");
		lblLeftHipRoll.setBounds(260, 112, 85, 16);
		frame.getContentPane().add(lblLeftHipRoll);

		JLabel lblRightHipRoll = new JLabel("Right Hip Roll");
		lblRightHipRoll.setBounds(865, 112, 85, 16);
		frame.getContentPane().add(lblRightHipRoll);

		JSlider lHipPitchSlider = new JSlider(-100, 100);
		lHipPitchSlider.setBounds(12, 141, 563, 56);
		lHipPitchSlider.setMajorTickSpacing(10);
		lHipPitchSlider.setPaintTicks(true);
		lHipPitchSlider.setPaintLabels(true);
		lHipPitchSlider.setSnapToTicks(true);
		lHipPitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg2)
			{
				if(!lHipPitchSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("LLeg", 1.0f);
					}
					catch(InterruptedException | CallError e1)
					{
						e1.printStackTrace();
					}

					lHipPitch = lHipPitchSlider.getValue();

					System.out.println("Left Hip Pitch Slider = " + lHipPitch);

					try 
					{
						setLHipPitch(lHipPitch, motion, balance);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}

		});
		frame.getContentPane().add(lHipPitchSlider);

		JSlider rHipPitchSlider = new JSlider(-100, 100);
		rHipPitchSlider.setBounds(606, 141, 602, 56);
		rHipPitchSlider.setMajorTickSpacing(10);
		rHipPitchSlider.setPaintTicks(true);
		rHipPitchSlider.setPaintLabels(true);
		rHipPitchSlider.setSnapToTicks(true);
		rHipPitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg2)
			{
				if(!rHipPitchSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("RLeg", 1.0f);
					}
					catch(InterruptedException | CallError e1)
					{
						e1.printStackTrace();
					}

					rHipPitch = rHipPitchSlider.getValue();

					System.out.println("Right Hip Pitch Slider = " + rHipPitch);

					try 
					{
						setRHipPitch(rHipPitch, motion, balance);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}

		});
		frame.getContentPane().add(rHipPitchSlider);

		JLabel lblLeftHipPitch = new JLabel("Left Hip Pitch");
		lblLeftHipPitch.setBounds(260, 210, 85, 16);
		frame.getContentPane().add(lblLeftHipPitch);

		JLabel lblRightHipPitch = new JLabel("Right Hip Pitch");
		lblRightHipPitch.setBounds(865, 210, 85, 16);
		frame.getContentPane().add(lblRightHipPitch);

		JSlider lKneePitchSlider = new JSlider(-100, 100);
		lKneePitchSlider.setBounds(12, 239, 563, 52);
		lKneePitchSlider.setMajorTickSpacing(10);
		lKneePitchSlider.setPaintTicks(true);
		lKneePitchSlider.setPaintLabels(true);
		lKneePitchSlider.setSnapToTicks(true);
		lKneePitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg3)
			{
				if(!lKneePitchSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("LLeg", 1.0f);
					}
					catch(InterruptedException | CallError e1)
					{
						e1.printStackTrace();
					}

					lKneePitch = lKneePitchSlider.getValue();

					System.out.println("Left Knee Pitch Slider = " + lKneePitch);

					try 
					{
						setLKneePitch(lKneePitch, motion, balance);
					} 
					catch (Exception e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().add(lKneePitchSlider);

		JSlider rKneePitchSlider = new JSlider(-100, 100);
		rKneePitchSlider.setBounds(606, 239, 602, 52);
		rKneePitchSlider.setMajorTickSpacing(10);
		rKneePitchSlider.setPaintTicks(true);
		rKneePitchSlider.setPaintLabels(true);
		rKneePitchSlider.setSnapToTicks(true);
		rKneePitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg4)
			{
				if(!rKneePitchSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("RLeg", 1.0f);
					}
					catch(InterruptedException | CallError e2)
					{
						e2.printStackTrace();
					}

					rKneePitch = rKneePitchSlider.getValue();

					System.out.println("Right Knee Pitch Slider = " + rKneePitch);

					try 
					{
						setRKneePitch(rKneePitch, motion, balance);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().add(rKneePitchSlider);

		JLabel lblLeftKneePitch = new JLabel("Left Knee Pitch");
		lblLeftKneePitch.setBounds(260, 304, 85, 16);
		frame.getContentPane().add(lblLeftKneePitch);

		JLabel lblRightKneePitch = new JLabel("Right Knee Pitch");
		lblRightKneePitch.setBounds(865, 304, 100, 16);
		frame.getContentPane().add(lblRightKneePitch);

		JSlider lAnklePitchSlider = new JSlider(-100, 100);
		lAnklePitchSlider.setBounds(12, 333, 563, 52);
		lAnklePitchSlider.setMajorTickSpacing(10);
		lAnklePitchSlider.setPaintTicks(true);
		lAnklePitchSlider.setPaintLabels(true);
		lAnklePitchSlider.setSnapToTicks(true);
		lAnklePitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg5)
			{
				if(!lAnklePitchSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("LLeg", 1.0f);
					}
					catch(InterruptedException | CallError e2)
					{
						e2.printStackTrace();
					}

					lAnklePitch = lAnklePitchSlider.getValue();

					System.out.println("Left Ankle Pitch Slider = " + lAnklePitch);

					try 
					{
						setLAnklePitch(lAnklePitch, motion, balance);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().add(lAnklePitchSlider);

		JSlider rAnklePitchSlider = new JSlider(-100, 100);
		rAnklePitchSlider.setBounds(606, 333, 602, 52);
		rAnklePitchSlider.setMajorTickSpacing(10);
		rAnklePitchSlider.setPaintTicks(true);
		rAnklePitchSlider.setPaintLabels(true);
		rAnklePitchSlider.setSnapToTicks(true);
		rAnklePitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg6)
			{
				if(!rAnklePitchSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("RLeg", 1.0f);
					}
					catch(InterruptedException | CallError e2)
					{
						e2.printStackTrace();
					}

					rAnklePitch = rAnklePitchSlider.getValue();

					System.out.println("Right Ankle Pitch Slider = " + rAnklePitch);

					try 
					{
						setRAnklePitch(rAnklePitch, motion, balance);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().add(rAnklePitchSlider);

		JLabel lblLeftAnklePitch = new JLabel("Left Ankle Pitch");
		lblLeftAnklePitch.setBounds(259, 398, 98, 16);
		frame.getContentPane().add(lblLeftAnklePitch);

		JLabel lblRightAnklePitch = new JLabel("Right Ankle Pitch");
		lblRightAnklePitch.setBounds(865, 398, 100, 16);
		frame.getContentPane().add(lblRightAnklePitch);

		JSlider lAnkleRollSlider = new JSlider(-100, 100);
		lAnkleRollSlider.setBounds(12, 427, 563, 52);
		lAnkleRollSlider.setMajorTickSpacing(10);
		lAnkleRollSlider.setPaintTicks(true);
		lAnkleRollSlider.setPaintLabels(true);
		lAnkleRollSlider.setSnapToTicks(true);
		lAnkleRollSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg6)
			{
				if(!lAnkleRollSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("LLeg", 1.0f);
					}
					catch(InterruptedException | CallError e2)
					{
						e2.printStackTrace();
					}

					lAnkleRoll = lAnkleRollSlider.getValue();

					System.out.println("Left Ankle Roll Slider = " + lAnkleRoll);

					try 
					{
						setLAnkleRoll(lAnkleRoll, motion, balance);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().add(lAnkleRollSlider);

		JSlider rAnkleRollSlider = new JSlider(-100, 100);
		rAnkleRollSlider.setBounds(606, 427, 602, 52);
		rAnkleRollSlider.setMajorTickSpacing(10);
		rAnkleRollSlider.setPaintTicks(true);
		rAnkleRollSlider.setPaintLabels(true);
		rAnkleRollSlider.setSnapToTicks(true);
		rAnkleRollSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent arg7)
			{
				if(!rAnkleRollSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("RLeg", 1.0f);
					}
					catch(InterruptedException | CallError e2)
					{
						e2.printStackTrace();
					}

					rAnkleRoll = rAnkleRollSlider.getValue();

					System.out.println("Right Ankle Roll Slider = " + rAnkleRoll);

					try 
					{
						setRAnkleRoll(rAnkleRoll, motion, balance);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		frame.getContentPane().add(rAnkleRollSlider);

		JLabel lblLeftAnkleRoll = new JLabel("Left Ankle Roll");
		lblLeftAnkleRoll.setBounds(260, 492, 85, 16);
		frame.getContentPane().add(lblLeftAnkleRoll);

		JLabel lblRightAnkleRoll = new JLabel("Right Ankle Roll");
		lblRightAnkleRoll.setBounds(865, 492, 100, 16);
		frame.getContentPane().add(lblRightAnkleRoll);
	} 

	public boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean running) {
		InterestLeg.running = running;
	}
}
