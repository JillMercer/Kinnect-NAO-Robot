package edu.sru.thangiah.demo.redball;

import java.awt.Color;

public class BlobProperties extends CircleBlobProperties implements BlobPropertiesInterface {
	private String shape;
	
	public BlobProperties() {
		this.shape ="Uknown";
	}
	
	public BlobProperties(Color color, int colorThreshold, int minSize, float span, String shape) {
		this.setColor(color);
		this.setColorThreshold(colorThreshold);
		this.setMinSize(minSize);
		this.setSpan(span);
		this.shape =shape;
	}
	
	
	@Override
	public void setShape(String shape) {
		// TODO Auto-generated method stub
		this.shape =shape;
	}
	
	@Override
	public String getShape() {
		return shape;
	}
}
