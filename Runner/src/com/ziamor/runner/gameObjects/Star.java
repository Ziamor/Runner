package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Star extends GameObject {

	public Star(int x, int y) {
		this.x = x + 4;
		this.y = y;
		this.objID = "star";
		this.spriteID = "star";
		width = 24;
		height = 24;
		loadSprite();

	}

	public Star() {
		// TODO Auto-generated constructor stub
	}

	public void update() {

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

	    g.drawImage(sprite, x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, null);
	}

}
