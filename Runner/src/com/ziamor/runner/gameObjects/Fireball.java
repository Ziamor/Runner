package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Fireball extends GameObject {

	private int xspeed;

	public Fireball(int x, int y, int xspeed) {
		this.objID = "hazard";
		width = 32;
		height = 16;

		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
	}

	public void update() {
		x += xspeed;

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

		g.setColor(Color.red);
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, width,
				height);

	}

}
