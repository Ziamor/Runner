package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.CollisionHandler;
import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Fireball extends GameObject {

	private int xspeed;
	private double yspeed;

	public Fireball(int x, int y, int xspeed, double yspeed) {
		this.objID = "hazard";
		width = 16;
		height = 16;

		this.x = x;
		this.y = y;
		this.xspeed = xspeed;
		this.yspeed = yspeed;
	}

	public void update() {
		yspeed += 0.35;
		x += xspeed;
		y += yspeed;
		
		// check for collision with wall
		for (GameObject gameObject : this.parent.getGameObjectsByID("wall")) {
			if (CollisionHandler.isColliding(this, gameObject)) {
				// remove this object
				isActive = false;
				isVisible = false;
			}
		}
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
