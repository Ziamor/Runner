package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Star extends GameObject {

	public Star() {
		this.objID = "star";
		width = 24;
		height = 24;
		
	}

	public void update() {
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;
		
		g.setColor(new Color(230, 230, 0, 255));
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY,
				width, height);

	}

}
