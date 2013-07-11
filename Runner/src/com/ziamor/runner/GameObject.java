package com.ziamor.runner;

import java.awt.Graphics;

import com.ziamor.runner.screens.GamePlayScreen;

public class GameObject {

	protected String objID;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected int xOffset;
	protected int yOffset;
	protected boolean isVisible = true;
	protected boolean isActive = true;
	protected boolean offScreen = false;
	protected GameScreen parent;
	
	public GameObject(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.parent = null;
	}
	
	public GameObject()
	{
		this.parent = null;
	}
	
	public GameObject(GameScreen parent)
	{
		this.parent = parent;
	}
	
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
	
	public boolean isVisible() {
		return isVisible;
	}

	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	
	public void update() {
	}

	public void paintComponent(Graphics g) {
		// don't draw objects that aren't on the screen
		if (x > GamePlayScreen.viewX+800)
			offScreen = true;
		else
			offScreen = false;
	}
	

	
}
