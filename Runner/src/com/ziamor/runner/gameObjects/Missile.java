package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.CollisionHandler;
import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Missile extends GameObject {

	private int speed;
	private double direction;

	public Missile(int x, int y, int speed) {
		this.objID = "hazard";
		width = 16;
		height = 16;

		this.x = x;
		this.y = y;
		this.speed = speed;

		direction = Math.PI * 1.5;
		System.out.println(getDirection(10, 10, 5, 9));
	}

	public void update() {
		// home in on the player
		double directionToPlayer = getDirection(x, y, Player.x + 8,
				Player.y + 16);
		// System.out.println(directionToPlayer);
		double diff = directionToPlayer - direction;
		if (diff > Math.PI)
			diff = diff - Math.PI * 2;
		if (diff < -Math.PI)
			diff = Math.PI * 2 + diff;
		if (diff > 0)
			direction += Math.PI / 90; // change the turn speed here
		else
			direction -= Math.PI / 90; // and here

		x += speed * Math.cos(direction);
		y += speed * Math.sin(direction);

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

	private double getDirection(int x1, int y1, int x2, int y2) {
		// returns a value between 0 and 2 pi
		double dir = Math.PI / 2;
		if (!((x2 - x1) == 0))
			dir = Math.atan2(y2 - y1, x2 - x1);
		if (dir < 0)
			dir += Math.PI * 2;
		return dir;
	}
}
