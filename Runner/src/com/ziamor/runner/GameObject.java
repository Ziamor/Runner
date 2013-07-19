package com.ziamor.runner;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.ziamor.runner.gameObjects.GameObjectFactory;
import com.ziamor.runner.screens.GamePlayScreen;

public class GameObject {

	protected String objID;
	protected String spriteID;
	protected int x;
	protected int y;
	protected int offsetX;
	protected int offsetY;
	protected int width;
	protected int height;
	protected int spriteOffsetX;
	protected int spriteOffsetY;
	protected boolean isVisible = true;
	protected boolean isActive = true;
	protected boolean offScreen = false;
	protected boolean isInterface = false;
	protected GameScreen parent;
	protected BufferedImage sprite;
	protected GameObjectFactory gobjFactorty; 

	public GameObject(int x, int y) {
		this.x = x;
		this.y = y;
		this.parent = null;
		this.gobjFactorty = GameObjectFactory.BLANK;
	}

	public GameObject() {
		this.parent = null;
		this.gobjFactorty = GameObjectFactory.BLANK;
	}

	public GameObject(GameScreen parent) {
		this.parent = parent;
		this.gobjFactorty = GameObjectFactory.BLANK;
	}

	public String getObjID() {
		return objID;
	}

	public GameObjectFactory getgobjFactorty() {
		return gobjFactorty;
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

	public void setParent(GameScreen value) {
		parent = value;
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

	public boolean getIsInterface() {
		return isInterface;
	}

	public void setIsInterface(boolean value) {
		this.isInterface = value;
	}
	
	public void update() {
	}

	public void paintComponent(Graphics g) {
		// don't draw objects that aren't on the screen
		if (x > GamePlayScreen.viewX + 800)
			offScreen = true;
		else
			offScreen = false;
	}
	
}
