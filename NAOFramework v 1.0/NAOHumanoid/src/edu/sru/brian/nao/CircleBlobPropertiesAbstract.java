package edu.sru.brian.nao;

import java.awt.Color;

public abstract class CircleBlobPropertiesAbstract implements  CircleBlobPropertiesInterface{
	
	private int threshold;
	private int minSize;
	private float span;
	private Color color;

	public CircleBlobPropertiesAbstract() {
		this.color = Color.RED;
		this.threshold = 0;
		this.minSize = 10;
		this.span = 01.f;
	}
	
	public Color getColor() {
		return color;
	}

	@Override
	public int getColorThreshold() {
		// TODO Auto-generated method stub
		return threshold;
	}

	@Override
	public int getMinSize() {
		// TODO Auto-generated method stub
		return minSize;
	}

	@Override
	public float getSpan() {
		// TODO Auto-generated method stub
		return span;
	}
	
	@Override
	public void setColor(Color color) {
		// TODO Auto-generated method stub
		this.color = color;
	}

	@Override
	public void setColorThreshold(int threshold) {
		// TODO Auto-generated method stub
		if(threshold >= 0 && threshold <= 255)
		{
			this.threshold = threshold;
		}
	}

	@Override
	public void setMinSize(int sizePixels) {
		// TODO Auto-generated method stub
		this.minSize = sizePixels;
	}

	@Override
	public void setSpan(float span) {
		// TODO Auto-generated method stub
		this.span = span;
	}

}
