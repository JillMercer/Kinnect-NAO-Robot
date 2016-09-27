package edu.sru.brian.nao;

import java.awt.Color;

public interface BlobPropertiesInterface extends CircleBlobPropertiesInterface {
	/**
	 * Set the shap of the blob
	 * 
	 * Can be "Circle" if the object is circluar
	 * or "Unknown" for a generic blob.
	 */
	public void setShape(String shape);
}
