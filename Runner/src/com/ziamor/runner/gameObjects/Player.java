package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.CollisionHandler;
import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class Player extends GameObject {

	private double yspeedDouble;

	public Player() {
		this.objID = "player";
		x = 100;
		y = 350;
		width = 32;
		height = 48;
	}

	public void update() {

		// initialize speed
		int xspeed = 5;

		// check if the player is on the ground
		double gravity = 0.5;
		boolean grounded = false;
		y = y + 1;
		for (GameObject gameObject : GameScreen.gameObjects) {
			if (gameObject.getObjID() == "wall") {
				if (CollisionHandler.isColliding(this, gameObject)) {
					grounded = true;
					yspeedDouble = 0;
					gravity = 0;
				}
			}
		}
		y = y - 1;

		// check for key input
		if (Runner._input.isKeyPressed(InputManager._keys.get("d"))) {
			xspeed = 15;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("a"))) {
			xspeed = -5;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("s"))) {
			gravity = gravity * 3;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("w"))) {
			if (grounded == true) {
				yspeedDouble = -13;
			}
		}

		yspeedDouble += gravity; // apply gravity
		int yspeed = ((int) yspeedDouble); // make yspeed an int

		// move player
		x += xspeed;
		y += yspeed;

		// collision checking
		boolean collision = true;
		int yCount = 0;
		while (collision == true) {

			collision = false;
			for (GameObject gameObject : GameScreen.gameObjects) {
				if (gameObject.getObjID() == "wall") {
					if (CollisionHandler.isColliding(this, gameObject)) {
						collision = true;
					}
				}
			}

			if (collision) {
				if (yCount < Math.abs(yspeed)) {
					if (yspeed > 0)
						y--;
					else if (yspeed < 0)
						y++;
					yCount++;
				} else {
					if (xspeed > 0)
						x--;
					else if (xspeed < 0)
						x++;
				}
			}
		}

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, width, height);
	}

}
