package edu.sru.thangiah.nao.vision;

/** Author: Zachary Kearney
Last Edited, 9/3/2015
* @author zrk1002
*
*/

import java.awt.image.BufferedImage;

import com.aldebaran.qi.CallError;
import com.aldebaran.qi.helper.proxies.ALVideoDevice;

import edu.sru.thangiah.nao.module.ModuleInterface;

public interface VisionInterface extends ModuleInterface{	

	 public void run() throws Exception;
	
	 public void setResolution(Integer res) throws Exception;
	 
	 public void setCameraIndex(Integer index) throws Exception;
	 
     public Integer getCameraIndex();
	 
	 public Integer getResolution();
	 
	 public Integer getFrameRate();
	 
	 public Integer getColorspace();
	 
	 public int getHeight();
	 
	 public int getWidth();
	 
	 public String getModuleName();
	 	 
	 
	 
	 /**
	 public void setModuleName(String name);
	 */
}
