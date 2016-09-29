package edu.sru.thangiah.nao.vision;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.aldebaran.qi.helper.proxies.ALMotion;

public class CamFrameMouseAdapter extends MouseAdapter{

	private boolean movingHead = false;
	private int x = 0;
	private int y = 0;
	private ALMotion motion;
	private float widthMod = 0;
	private float heightMod = 0;
	
	public CamFrameMouseAdapter(ALMotion motion, int width, int height){
	this.motion = motion;	
	widthMod = width/2;
	heightMod = height/2;
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		movingHead = false;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		movingHead = true;
		
		try {
			motion.setStiffnesses("Head", 1.0);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		new Thread(new HeadMove()).start();			
	}

	public void mouseDragged(MouseEvent e){
		x = e.getX();
		y = e.getY();
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		movingHead = false;	
	}
	
	private class HeadMove implements Runnable{

		float yaw = 0; // -2.0857 - 2.0857
		float pitch = 0; // -.6720 - .5149
		@Override
		public void run() {
			while(movingHead){
				yaw = -((float)x - widthMod)/500;
				pitch = ((float)y - heightMod)/500;		
				try {
					motion.changeAngles("HeadYaw", yaw, .1f);
					motion.changeAngles("HeadPitch", pitch, .1f);
					Thread.sleep(50);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
}