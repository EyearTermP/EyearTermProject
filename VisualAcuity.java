package com.everychecking.model.data;

// ÁÂ¿ìÀÇ ½Ã·Â ¸ðµ¨ÀÌ´Ù.
public class VisualAcuity {
	// Properties ..
	private Double left;
	private Double right;
	
	// Constructor
	public VisualAcuity (Double left, Double right) {
		this.left = left;
		this.right = right;
	}
	
	// Getters ..
	public Double getLeft() {
		return left;
	}
	public Double getRight() {
		return right;
	}
	
	// Setters ..
	public void setLeft(Double left) {
		this.left = left;
	}
	public void setRight(Double right) {
		this.right = right;
	}
}
