package com.ziamor.runner;

import java.awt.Graphics;

public class GameObject {

	protected String objID;
	protected int x, y, width, height;

	public String getObjID() {
		return objID;
	}

	public int getX() {
		return x;
	}

	public void setX(int value) {
		x = value;
	}

	public int getY() {
		return y;
	}

	public void setY(int value) {
		y = value;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int value) {
		width = value;
	}
	
	public int getHeight() {
		return height;
	}

	public void setHeight(int value) {
		height = value;
	}
	
	public void update() {
	}

	public void paintComponent(Graphics g) {
	}
}
