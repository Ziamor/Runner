package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.CollisionHandler;
import com.ziamor.runner.GameObject;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class Player extends GameObject {

	private int yspeed;

	public Player() {
		this.objID = "player";
		x = 400;
		y = 350;
		width = 32;
		height = 48;
	}

	public void update() {

		// initialize speed
		int xspeed = 0;

		// check if the player is on the ground
		int gravity = 1;
		boolean grounded = false;
		if (x > GamePlayScreen.wall.getX() - 32) {
			if (x < GamePlayScreen.wall.getX() + 32) {
				if (y == GamePlayScreen.wall.getY() - 48) {
					grounded = true;
					yspeed = 0;
					gravity = 0;
				}
			}
		}

		// check for key input
		if (Runner._input.isKeyPressed(InputManager._keys.get("d"))) {
			xspeed = 5;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("a"))) {
			xspeed = -5;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("s"))) {
			yspeed = 5;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("w"))) {
			if (grounded == true) {
				yspeed = -15;
			}
		}

		// apply gravity
		yspeed += gravity;

		// move player
		x += xspeed;
		y += yspeed;

		// collision checking
		while (CollisionHandler.isColliding(this, GamePlayScreen.wall)) {
			if (xspeed > 0)
				x--;
			else if (xspeed < 0)
				x++;
			if (yspeed > 0)
				y--;
			else if (yspeed < 0)
				y++;
		}

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, width, height);
	}

}
