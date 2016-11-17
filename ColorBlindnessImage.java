package com.everychecking.resource;

import javafx.scene.image.Image;

public class ColorBlindnessImage {
	
	private Image image;
	private String value;

	public ColorBlindnessImage(Image image, String value) {
		super();
		this.image = image;
		this.value = value;
	}

	public Image getImage() {
		return image;
	}
	public String getValue() {
		return value;
	}
	
	public void setImage(Image image) {
		this.image = image;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
