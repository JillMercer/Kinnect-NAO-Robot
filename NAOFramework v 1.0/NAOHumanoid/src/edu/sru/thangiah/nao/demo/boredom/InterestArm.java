package edu.sru.thangiah.nao.demo.boredom;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JLabel;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.limbs.Arms;

import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.Session;
import javax.swing.JButton;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class InterestArm extends Arms
{
	private static ALMotion motion;
	private static ALBasicAwareness awareness;
	private static boolean running = true;
	private int lShoulderPitch;
	private int lShoulderRoll;
	private int lElbowYaw;
	private int lElbowRoll;
	private int lWristYaw;
	private int rShoulderPitch;
	private int rShoulderRoll;
	private int rElbowYaw;
	private int rElbowRoll;
	private int rWristYaw;

	private JFrame frame;
	private JSlider lSRollSlider = new JSlider(-100,100);
	private JSlider lSPitchSlider = new JSlider(-100,100);
	private JSlider lEYawSlider = new JSlider(-100,100);
	private JSlider lERollSlider = new JSlider(-100,100);
	private JSlider lWYawSlider = new JSlider(-100,100);
	private JSlider rSRollSlider = new JSlider(-100,100);
	private JSlider rSPitchSlider = new JSlider(-100,100);
	private JSlider rEYawSlider = new JSlider(-100,100);
	private JSlider rERollSlider = new JSlider(-100,100);
	private JSlider rWYawSlider = new JSlider(-100,100);

	public boolean isRunning() 
	{
		return running;
	}

	public static void setRunning(boolean running) 
	{
		InterestArm.running = running;
	}

	public InterestArm(Session session) throws Exception
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
			setLDefault(motion);
		}
		catch(InterruptedException | CallError e)
		{
			e.printStackTrace();
		}

		try
		{
			setRDefault(motion);
		}
		catch(InterruptedException | CallError e)
		{
			e.printStackTrace();
		}

		frame = new JFrame();
		frame.setBounds(100, 100, 1298, 721);
		frame.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent arg0) 
			{
				System.out.println("Window is closing");
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

		lSRollSlider.setMajorTickSpacing(10);
		lSRollSlider.setPaintTicks(true);
		lSRollSlider.setPaintLabels(true);
		lSRollSlider.setSnapToTicks(true);
		lSRollSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e) 
			{
				if(!lSRollSlider.getValueIsAdjusting())
				{
					try
					{
						motion.setStiffnesses("LArm", 1.0f);
					}
					catch(InterruptedException | CallError e1)
					{
						e1.printStackTrace();
					}

					lShoulderRoll = lSRollSlider.getValue();
				}

				System.out.println("Left Shoulder Roll Slider = " + lShoulderRoll);

				try 
				{
					setLShoulderRoll(lShoulderRoll, motion);
				} 
				catch (InterruptedException | CallError e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		lSRollSlider.setBounds(12, 13, 623, 52);
		frame.getContentPane().add(lSRollSlider);

		JLabel lblRightArmYaw = new JLabel("Left Shoulder Pitch");
		lblRightArmYaw.setBounds(266, 201, 118, 16);
		frame.getContentPane().add(lblRightArmYaw);

		lSPitchSlider.setMajorTickSpacing(10);
		lSPitchSlider.setPaintTicks(true);
		lSPitchSlider.setPaintLabels(true);
		lSPitchSlider.setSnapToTicks(true);
		lSPitchSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e2) 
			{
				if(!lSPitchSlider.getValueIsAdjusting())
				{
					lShoulderPitch = lSPitchSlider.getValue();
				}

				try
				{
					setLShoulderPitch(lShoulderPitch, motion);
				}
				catch(InterruptedException | CallError e3)
				{
					e3.printStackTrace();
				}
			}
		});
		lSPitchSlider.setBounds(12, 136, 623, 52);
		frame.getContentPane().add(lSPitchSlider);

		JLabel lblRightArmPitch = new JLabel("Left Shoulder Roll");
		lblRightArmPitch.setBounds(272, 91, 112, 16);
		frame.getContentPane().add(lblRightArmPitch);

		lEYawSlider.setMajorTickSpacing(10);
		lEYawSlider.setPaintTicks(true);
		lEYawSlider.setPaintLabels(true);
		lEYawSlider.setSnapToTicks(true);
		lEYawSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e4) 
			{
				if(!lEYawSlider.getValueIsAdjusting())
				{
					lElbowYaw = lEYawSlider.getValue();
				}

				try
				{
					setLElbowYaw(lElbowYaw, motion);
				}
				catch(InterruptedException | CallError e5)
				{
					e5.printStackTrace();
				}
			}
		});
		lEYawSlider.setBounds(12, 245, 623, 52);
		frame.getContentPane().add(lEYawSlider);

		JLabel lblRightArmRoll = new JLabel("Left Elbow Yaw");
		lblRightArmRoll.setBounds(285, 310, 118, 16);
		frame.getContentPane().add(lblRightArmRoll);

		lERollSlider.setMajorTickSpacing(10);
		lERollSlider.setPaintTicks(true);
		lERollSlider.setPaintLabels(true);
		lERollSlider.setSnapToTicks(true);
		lERollSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e6) 
			{
				if(!lERollSlider.getValueIsAdjusting())
				{
					lElbowRoll = lERollSlider.getValue();
				}

				try
				{
					setLElbowRoll(lElbowRoll, motion);
				}
				catch(InterruptedException | CallError e7)
				{
					e7.printStackTrace();
				}
			}
		});
		lERollSlider.setBounds(12, 355, 623, 52);
		frame.getContentPane().add(lERollSlider);

		JLabel lblLeftArmYaw = new JLabel("Left Elbow Roll");
		lblLeftArmYaw.setBounds(288, 438, 96, 16);
		frame.getContentPane().add(lblLeftArmYaw);

		lWYawSlider.setMajorTickSpacing(10);
		lWYawSlider.setPaintTicks(true);
		lWYawSlider.setPaintLabels(true);
		lWYawSlider.setSnapToTicks(true);
		lWYawSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e8) 
			{
				if(!lWYawSlider.getValueIsAdjusting())
				{
					lWristYaw = lWYawSlider.getValue();
				}

				try
				{
					setLWristYaw(lWristYaw, motion);
				}
				catch(InterruptedException | CallError e9)
				{
					e9.printStackTrace();
				}
			}	
		});
		lWYawSlider.setBounds(12, 482, 623, 52);
		frame.getContentPane().add(lWYawSlider);

		JLabel lblLeftArmPitch = new JLabel("Left Wrist Yaw");
		lblLeftArmPitch.setBounds(288, 548, 96, 16);
		frame.getContentPane().add(lblLeftArmPitch);

		JButton btnOpenHand = new JButton("Open Left Hand");
		btnOpenHand.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e10) 
			{
				try
				{
					setLHand("Open", motion);
				}
				catch(InterruptedException | CallError e11)
				{
					e11.printStackTrace();
				}
			}
		});
		btnOpenHand.setBounds(184, 608, 135, 25);
		frame.getContentPane().add(btnOpenHand);

		JButton btnCloseHand = new JButton("Close Left Hand");
		btnCloseHand.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e12) 
			{
				try
				{
					setLHand("Close", motion);
				}
				catch(InterruptedException | CallError e13)
				{
					e13.printStackTrace();
				}
			}
		});
		btnCloseHand.setBounds(345, 608, 135, 25);
		frame.getContentPane().add(btnCloseHand);

		rSPitchSlider.setMajorTickSpacing(10);
		rSPitchSlider.setPaintTicks(true);
		rSPitchSlider.setPaintLabels(true);
		rSPitchSlider.setSnapToTicks(true);
		rSPitchSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e14) 
			{
				if(!rSPitchSlider.getValueIsAdjusting())
				{
					rShoulderPitch = rSPitchSlider.getValue();
				}

				try 
				{
					setRShoulderPitch(rShoulderPitch, motion);
				} 
				catch (InterruptedException | CallError e15) 
				{
					e15.printStackTrace();
				}
			}
		});
		rSPitchSlider.setBounds(647, 136, 623, 52);
		frame.getContentPane().add(rSPitchSlider);

		JLabel lblRightShoulderPitch = new JLabel("Right Shoulder Pitch");
		lblRightShoulderPitch.setBounds(908, 201, 129, 16);
		frame.getContentPane().add(lblRightShoulderPitch);

		rSRollSlider.setMajorTickSpacing(10);
		rSRollSlider.setPaintTicks(true);
		rSRollSlider.setPaintLabels(true);
		rSRollSlider.setSnapToTicks(true);
		rSRollSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e16) 
			{
				if(!rSRollSlider.getValueIsAdjusting())
				{
					rShoulderRoll = rSRollSlider.getValue();
				}

				try
				{
					setRShoulderRoll(rShoulderRoll, motion);
				}
				catch(InterruptedException | CallError e17)
				{
					e17.printStackTrace();
				}
			}
		});
		rSRollSlider.setBounds(649, 13, 621, 52);
		frame.getContentPane().add(rSRollSlider);

		JLabel lblRightShoulderRoll = new JLabel("Right Shoulder Roll");
		lblRightShoulderRoll.setBounds(908, 91, 129, 16);
		frame.getContentPane().add(lblRightShoulderRoll);

		rEYawSlider.setMajorTickSpacing(10);
		rEYawSlider.setPaintTicks(true);
		rEYawSlider.setPaintLabels(true);
		rEYawSlider.setSnapToTicks(true);
		rEYawSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e18) 
			{
				if(!rEYawSlider.getValueIsAdjusting())
				{
					rElbowYaw = rEYawSlider.getValue();
				}

				try
				{
					setRElbowYaw(rElbowYaw, motion);
				}
				catch(InterruptedException | CallError e19)
				{
					e19.printStackTrace();
				}
			}
		});
		rEYawSlider.setBounds(647, 245, 621, 52);
		frame.getContentPane().add(rEYawSlider);

		rERollSlider.setMajorTickSpacing(10);
		rERollSlider.setPaintTicks(true);
		rERollSlider.setPaintLabels(true);
		rERollSlider.setSnapToTicks(true);
		rERollSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e20) 
			{
				if(!rERollSlider.getValueIsAdjusting())
				{
					rElbowRoll = rERollSlider.getValue();
				}

				try
				{
					setRElbowRoll(rElbowRoll, motion);
				}
				catch(InterruptedException | CallError e21)
				{
					e21.printStackTrace();
				}
			}
		});
		rERollSlider.setBounds(647, 355, 621, 52);
		frame.getContentPane().add(rERollSlider);

		rWYawSlider.setMajorTickSpacing(10);
		rWYawSlider.setPaintTicks(true);
		rWYawSlider.setPaintLabels(true);
		rWYawSlider.setSnapToTicks(true);
		rWYawSlider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e22) 
			{
				if(!rWYawSlider.getValueIsAdjusting())
				{
					rWristYaw = rWYawSlider.getValue();
				}

				try
				{
					setRWristYaw(rWristYaw, motion);
				}
				catch(InterruptedException | CallError e23)
				{
					e23.printStackTrace();
				}
			}
		});
		rWYawSlider.setBounds(647, 482, 621, 52);
		frame.getContentPane().add(rWYawSlider);

		JLabel lblRightElbowYaw = new JLabel("Right Elbow Yaw");
		lblRightElbowYaw.setBounds(918, 310, 112, 16);
		frame.getContentPane().add(lblRightElbowYaw);

		JLabel lblRightElbowRoll = new JLabel("Right Elbow Roll");
		lblRightElbowRoll.setBounds(918, 438, 109, 16);
		frame.getContentPane().add(lblRightElbowRoll);

		JLabel lblRightWristYaw = new JLabel("Right Wrist Yaw");
		lblRightWristYaw.setBounds(908, 548, 97, 16);
		frame.getContentPane().add(lblRightWristYaw);

		JButton btnOpenRightHand = new JButton("Open Right Hand");
		btnOpenRightHand.addMouseListener(new MouseAdapter() 
		{
			public void mouseClicked(MouseEvent e24) 
			{
				try
				{
					setRHand("Open", motion);
				}
				catch(InterruptedException | CallError e25)
				{
					e25.printStackTrace();
				}
			}
		});
		btnOpenRightHand.setBounds(824, 608, 129, 25);
		frame.getContentPane().add(btnOpenRightHand);

		JButton btnCloseRightHand = new JButton("Close Right Hand");
		btnCloseRightHand.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e26)
			{
				try
				{
					setRHand("Close", motion);
				}
				catch(InterruptedException | CallError e27)
				{
					e27.printStackTrace();
				}
			}
		});
		btnCloseRightHand.setBounds(982, 608, 129, 25);
		frame.getContentPane().add(btnCloseRightHand);

		frame.setVisible(true);
	} 
}
