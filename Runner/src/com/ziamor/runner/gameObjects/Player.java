package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class Player extends GameObject{

	public int x;
	public int y;
	public boolean pressRight;
	public boolean pressLeft;
	public boolean pressJump;

	public Player() {

		x = 400;
		y = 350;

	}

	public void update() {

		// initialize speed
		int xspeed = 0;
		int yspeed = 0;

		// check for key input
		if (Runner._input.isKeyPressed(InputManager._keys.get("d"))) {
			xspeed = 5;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("a"))) {
			xspeed = -5;
		}

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
					collision = true;
				}
			}

			// correct collision
			if (collision == true) {
				x = x - 1;
			}

		}

	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.blue);
		g.fillRect(x, y, 32, 48);
	}

}
