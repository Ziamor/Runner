package com.ziamor.runner;

import java.awt.Color;
import java.awt.Graphics;

public class GamePlayScreen extends GameScreen{

	public GamePlayScreen()
	{
		this.setDisableRender(true);
		this.setDisableUpdate(true);
	}
	
	public void update(Graphics g) {
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(0, 0, 500, 500);
	}
}
