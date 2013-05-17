package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class Player extends GameObject {

	public int x;
	public int y;
	private int yspeed;

	public Player() {

		x = 400;
		y = 350;

	}

	public void update() {

		// initialize speed
		int xspeed = 0;

		// check if the player is on the ground
		int gravity = 1;
		boolean grounded = false;
		if (x > GamePlayScreen.wall.x - 32) {
			if (x < GamePlayScreen.wall.x + 32) {
				if (y == GamePlayScreen.wall.y - 48) {
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
		yspeed = yspeed + gravity;
		
		// move player
		x = x + xspeed;
		y = y + yspeed;

		// collision checking
		boolean collision = true;
		while (collision == true) {
			collision = false; // initialize collision

			// check for collision
			if (x > GamePlayScreen.wall.x - 32) {
				if (x < GamePlayScreen.wall.x + 32) {
					if (y > GamePlayScreen.wall.y - 48) {
						if (y < GamePlayScreen.wall.y + 48) {
							collision = true;
						}
					}

				}
			}

			// correct collision
			if (collision == true) {
				if (xspeed > 0) {
					x = x - 1;
				}
				if (xspeed < 0) {
					x = x + 1;
				}
				if (yspeed > 0) {
					y = y - 1;
				}
				if (yspeed < 0) {
					y = y + 1;
				}
			}

		}

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, 32, 48);
	}

}
