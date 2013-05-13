package com.ziamor.runner;

import java.awt.Graphics;

public class GameScreen {
	private boolean disableRender;
	private boolean disableUpdate;
	
	public void setDisableRender(boolean value)
	{
		this.disableRender = value;
	}
	
	public void setDisableUpdate(boolean value)
	{
		this.disableUpdate = value;
	}
	
	public void update(Graphics g) {
	}

	public void paintComponent(Graphics g) {
	}
}
