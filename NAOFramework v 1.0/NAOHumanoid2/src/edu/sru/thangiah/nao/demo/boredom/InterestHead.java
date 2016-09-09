package edu.sru.thangiah.nao.demo.boredom;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALBasicAwareness;
import com.aldebaran.qi.helper.proxies.ALMotion;

import edu.sru.thangiah.nao.movement.head.Head;
import edu.sru.thangiah.nao.movement.head.HeadDoublyLinkedList;
import edu.sru.thangiah.nao.movement.head.HeadNode;

import com.aldebaran.qi.Session;

public class InterestHead extends Head
{

	private JFrame frmHead;
	private JSlider yawSlider = new JSlider(-100,100);
	private JSlider pitchSlider = new JSlider(-100,100);
	private float yaw;
	private float pitch;
	private static boolean running = true;
	private static HeadNode current;
	private static HeadDoublyLinkedList coords = new HeadDoublyLinkedList();
	private static ALMotion motion;
	private static ALBasicAwareness awareness;
	private static float yawVal;
	private static float pitchVal;
	private static float currentPitch;
	private static float midVal;

	public static void setRunning(boolean running) 
	{
		InterestHead.running = running;	
	}


	public boolean isRunning() 
	{
		return running;
	}

	public InterestHead(Session session) throws Exception
	{
		initialize(session);
	}

	private void initialize(Session session) throws Exception 
	{
		motion = new ALMotion(session);
		awareness = new ALBasicAwareness(session);

		awareness.stopAwareness();
		motion.setBreathEnabled("All", false);
		motion.setStiffnesses("Head", 1.0f);

		//Adds nodes to a DoublyLinkedList that contains the minimum and maximum y-values that correspond to each x value as dictated on Aldebaran's NAO documentation
		coords.add(-100, -2.0857f, -0.449073f, 0.330041f);
		coords.add(-90, -1.87713f, -0.330041f, 0.200015f);
		coords.add(-80, -1.66856f, -0.330041f, 0.200015f);
		coords.add(-73, -1.526988f, -0.330041f, 0.200015f);
		coords.add(-70, -1.45999f, -0.430049f, 0.30022f);
		coords.add(-60, -1.25142f, -0.430049f, 0.30022f);
		coords.add(-52, -1.089958f, -0.430049f, 0.300022f);
		coords.add(-50, -1.04285f, -0.479965f, 0.330041f);
		coords.add(-43, -0.903033f, -0.479965f, 0.330041f);
		coords.add(-40, -0.83428f, -0.548033f, 0.370010f);
		coords.add(-36, -0.756077f, -0.548033f, 0.370010f);
		coords.add(-30, -0.62571f, -0.671951f, 0.422021f);
		coords.add(-23, -0.486074f, -0.671951f, 0.422021f);
		coords.add(-20, -0.41714f, -0.671951f, 0.515047f);
		coords.add(-10, -0.20857f, -0.671951f, 0.515047f);
		coords.add(0, 0.0f, -0.671951f, 0.515047f);
		coords.add(10, 0.20857f, -0.671951f, 0.515047f);
		coords.add(20, 0.41714f, -0.671951f, 0.515047f);
		coords.add(23, 0.486074f, -0.671951f, 0.422021f);
		coords.add(30, 0.62571f, -0.671951f, 0.422021f);
		coords.add(36, 0.756077f, -0.548033f, 0.370010f);
		coords.add(40, 0.83428f, -0.548033f, 0.370010f);
		coords.add(43, 0.903033f, -0.479965f, 0.330041f);
		coords.add(50, 1.04285f, -0.479965f, 0.330041f);
		coords.add(52, 1.089958f, -0.430049f, 0.300022f);
		coords.add(60, 1.25142f, -0.430049f, 0.30022f);
		coords.add(70, 1.45999f, -0.430049f, 0.30022f);
		coords.add(73, 1.526988f, -0.330041f, 0.200015f);
		coords.add(80, 1.66856f, -0.330041f, 0.200015f);
		coords.add(90, 1.87713f, -0.330041f, 0.200015f);
		coords.add(100, 2.0857f, -0.449073f, 0.330041f);

		//sets the current position in the linked list to the head node which is x = -100
		current = coords.getHead();

		//sets the current position in the linked list to neutral position, which is x = 0.  At startup, this will always set the head to neutral position.
		while(current.getXPercent() != 0)
		{
			current = current.getNext();
		}

		//Changes the maximum and minimum values of the y-axis to correspond to the values held in the linked list.
		pitchMin = current.getYMin();
		pitchMax = current.getYMax();

		motion.setAngles("HeadYaw", current.getX(), SPEED);
		Thread.sleep(500);
		motion.setAngles("HeadPitch", -.059516f, SPEED);
		Thread.sleep(500);

		frmHead = new JFrame();
		frmHead.setTitle("Head Movement");
		frmHead.setBounds(100, 100, 708, 446);
		frmHead.addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent arg0) 
			{
				System.out.println("Window is closing");
				try
				{
					Thread.sleep(1000);
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
		frmHead.getContentPane().setLayout(null);

		JLabel lblHeadYaw = new JLabel("Head Yaw (x-axis)");
		lblHeadYaw.setBounds(306, 126, 111, 16);
		frmHead.getContentPane().add(lblHeadYaw);

		yawSlider.setBounds(12, 58, 666, 55);
		yawSlider.setMajorTickSpacing(10);
		yawSlider.setPaintTicks(true);
		yawSlider.setPaintLabels(true);
		yawSlider.setSnapToTicks(true);
		yawSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e)
			{
				if (!yawSlider.getValueIsAdjusting()) 
				{
					//Gets the current position on the Yaw Slider (x-axis)
					try 
					{
						motion.setStiffnesses("HeadYaw", 1.0f);
					} 
					catch (CallError | InterruptedException e3) 
					{
						e3.printStackTrace();
					}

					yaw = yawSlider.getValue();

					//checks to see if the desired slider position is less than the x-value held by the current position in the linked list.
					if((int)yaw < current.getXPercent())
					{
						while(yaw < current.getXPercent())
						{
							current = current.getPrevious();
						}
						pitchMin = current.getYMin();
						pitchMax = current.getYMax();

						try 
						{
							//Gets the current position of the head to put be certain it is not out of bounds of the new max and min.
							currentPitch = motion.getAngles("HeadPitch", false).get(0);							
						} 
						catch (CallError | InterruptedException e2) 
						{
							e2.printStackTrace();
						}

						try 
						{
							//checks to see if the pitch is out of bounds.  If it is, puts the pitch back in bounds.
							checkPitchBounds(pitchMin, pitchMax, currentPitch, motion);
						} 
						catch (InterruptedException | CallError e2) 
						{
							e2.printStackTrace();
						}

						//Gets the desired x-value.
						yawVal = current.getXPercent();
						yawVal = (yawVal/100) * yawMax;

						try 
						{
							//sets the Yaw to the desired x-value
							motion.setAngles("HeadYaw", yawVal, SPEED);
							Thread.sleep(500);

							if(motion.getAngles("HeadYaw", false).get(0) == yawVal)
							{
								motion.setStiffnesses("HeadYaw", 0.0f);
							}
						} 
						catch (CallError | InterruptedException e1) 
						{
							e1.printStackTrace();
						}
					}
					else if(yaw > current.getXPercent())
					{
						//If the desired yaw value on the slider is greater than the value contained in the current node, go to the next node.
						while((int)yaw > current.getXPercent())
						{
							current = current.getNext();
						}

						//Sets the new minimum and maximum values for the Pitch (y-axis).
						pitchMin = current.getYMin();
						pitchMax = current.getYMax();

						try
						{
							//checks to make sure the HeadPitch is in bounds for the new x-value
							checkPitchBounds(pitchMin, pitchMax, currentPitch, motion);
						} 
						catch (InterruptedException | CallError e2) 
						{
							e2.printStackTrace();
						}

						yawVal = current.getXPercent();
						yawVal = (yawVal/100) * yawMax;

						try 
						{
							//gets the current y-value
							motion.setAngles("HeadYaw", yawVal, SPEED);
							Thread.sleep(500);

							if(motion.getAngles("HeadYaw", false).get(0) == yawVal)
							{
								motion.setStiffnesses("HeadYaw", 0.0f);
							}

							//setHeadYaw(yawVal, motion);
						} 
						catch (CallError | InterruptedException e2) 
						{
							e2.printStackTrace();
						}
					}
				}
			}
		});
		frmHead.getContentPane().add(yawSlider);

		pitchSlider.setBounds(12, 226, 666, 59);
		pitchSlider.setMajorTickSpacing(10);
		pitchSlider.setPaintTicks(true);
		pitchSlider.setPaintLabels(true);
		pitchSlider.setSnapToTicks(true);
		pitchSlider.addChangeListener(new ChangeListener() 
		{
			public void stateChanged(ChangeEvent e)
			{
				//gets the current position on the pitch slider.
				if(!pitchSlider.getValueIsAdjusting())
				{
					pitchMin = current.getYMax();
					pitchMax = current.getYMin();

					midVal = Math.abs(pitchMin) + pitchMax;
					midVal = midVal/2;
					midVal = pitchMin + midVal;

					pitch = pitchSlider.getValue();
					pitchVal = pitchSlider.getValue();


					//Sets the 100 and -100 values on the slider to lower than the actual max/min because after hvaing done many tests, the ranges of the robot's head are actually much less than the documentation claims.
					if(pitch == 90)
					{
						pitch = 85;
					}
					else if(pitch == -90)
					{
						pitch = -85;
					}
					else if(pitch == 100)
					{
						pitch = 92;
					}
					else if (pitch == -100)
					{
						pitch = -92;
					}

					pitch = pitch/100;					
					pitchVal = ((Math.abs(midVal) + pitchMax) * pitch) + midVal;

					try 
					{
						motion.setAngles("HeadPitch", pitchVal, SPEED);
						Thread.sleep(500);
					} 
					catch (InterruptedException | CallError e1) 
					{
						e1.printStackTrace();
					}
				}
			}
		});
		frmHead.getContentPane().add(pitchSlider);

		JLabel lblHeadPitch = new JLabel("Head Pitch (y-axis)");
		lblHeadPitch.setBounds(306, 317, 111, 16);
		frmHead.getContentPane().add(lblHeadPitch);

		frmHead.setVisible(true);
	} 
}
