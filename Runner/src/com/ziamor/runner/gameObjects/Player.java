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
	private int xspeed;
	private boolean gravitySwitch;
	private int dashTimer;
	private boolean gravitySwitchAble;
	private boolean freshUp;
	private boolean freshDown;
	private boolean freshSpace;
	private boolean freshRight;

	public Player() {
		this.objID = "player";
		x = 80;
		y = 300;
		width = 32;
		height = 48;
		isVisible = true;
		xspeed = 5;
		gravitySwitch = false;
		gravitySwitchAble = false;
		dashTimer = 0;
		freshUp = true;
		freshDown = true;
		freshSpace = true;
		freshRight = true;
	}

	public void update() {

		// if player is dead
		if (GamePlayScreen.playerDead) {
			yspeedDouble += 0.5; // apply gravity
			int yspeed = ((int) yspeedDouble); // make yspeed an int

			// move player
			x += xspeed;
			y += yspeed;

			return; // don't complete the rest of the update()
		}

		// make the player move forward
		if (xspeed > 5) {
			xspeed -= 0.2; // slow down the player if dashing
		} else {
			xspeed = 5;
		}

		// reduce the dash timer
		if (dashTimer > 0) {
			dashTimer--;
		}

		// check for walls above and below
		boolean wallBelow = false;
		boolean wallAbove = false;
		y = y + 1; // change y to check for walls below
		for (GameObject gameObject : this.parent.getGameObjectsByID("wall")) {
			if (CollisionHandler.isColliding(this, gameObject)) {
				if (yspeedDouble > 0) // stop the player if moving down
					yspeedDouble = 0;
				wallBelow = true;
			}
		}
		y = y - 2; // change y to check for walls above
		for (GameObject gameObject : this.parent.getGameObjectsByID("wall")) {
			if (CollisionHandler.isColliding(this, gameObject)) {
				if (yspeedDouble < 0) // stop the player if moving down
					yspeedDouble = 0;
				wallAbove = true;
			}
		}
		y = y + 1; // revert y to its proper value

		// check to see if grounded
		double gravity = 0.5;
		boolean grounded = false;
		if (!gravitySwitch && wallBelow) {
			grounded = true;
		} else if (gravitySwitch) {
			gravity = -0.5;
			if (wallAbove)
				grounded = true;
		}
		if (grounded) {
			gravitySwitchAble = true;
		}

		// check for key input
		if (Runner._input.isKeyPressed(InputManager._keys.get("space"))) {
			// space bar press
			if (freshSpace) {
				freshSpace = false;
				if (gravitySwitchAble) {
					if (grounded) { // jump if on ground
						if (!gravitySwitch) {
							yspeedDouble = -12; // jump in regular gravity
						} else {
							yspeedDouble = 12; // jump in switched gravity
						}
					}
					gravity = -gravity;
					gravitySwitch = !gravitySwitch;
					gravitySwitchAble = false;
				}
			}
		} else {
			freshSpace = true; // release the key for a fresh press
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("right"))) {
			// right arrow press
			if (freshRight) {
				if (dashTimer == 0) {
					freshRight = false;
					xspeed = 20; // dash forward
					yspeedDouble = 0;
					dashTimer = 60;
				}
			}
		} else {
			freshRight = true; // release the key for a fresh press
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("left"))) {
			// left arrow press
			xspeed = -5;
			// just for debugging
			// it may not even do anything in the actual game
		}
		boolean fastFall = false;
		if (Runner._input.isKeyPressed(InputManager._keys.get("down"))) {
			// down arrow press
			if (freshDown) {
				if (gravitySwitch) {
					freshDown = false;
					if (grounded == true) {
						yspeedDouble = 13;
					}
				} else {
					fastFall = true;
				}
			}
		} else {
			freshDown = true; // release the key for a fresh press
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("up"))) {
			// up arrow press
			if (freshUp) {
				if (!gravitySwitch) {
					freshUp = false;
					if (grounded == true) {
						yspeedDouble = -13;
					}
				} else {
					fastFall = true;
				}
			}
		} else {
			freshUp = true; // release the key for a fresh press
		}

		// apply gravity
		if (fastFall)
			yspeedDouble += gravity * 3; // fast falling
		else
			yspeedDouble += gravity; // normal falling
		// make yspeed an int
		int yspeed = ((int) yspeedDouble);

		// move player
		x += xspeed;
		y += yspeed;

		// collision checking
		boolean collision = true;
		int yCount = 0;
		while (collision == true) {

			collision = false;
			// loop through every wall to check for collisions
			for (GameObject gameObject : this.parent.getGameObjectsByID("wall")) {
				if (CollisionHandler.isColliding(this, gameObject)) {
					collision = true;
				}
			}

			if (collision) { // try to correct a collision
				if (yCount < Math.abs(yspeed)) {
					if (yspeed > 0)
						y--;
					else if (yspeed < 0)
						y++;
					yCount++;
				} else {
					// kill the player if he ran into a wall
					GamePlayScreen.playerDead = true;
					xspeed = -4;
					yspeedDouble = -6;
					break;
				}
			}
		}

		// kill the player if he is too low
		if (y > 608 + 200) {
			GamePlayScreen.playerDead = true;
			xspeed = 0;
			yspeedDouble = -13;
		}

		// collect any coins that the player is touching
		for (GameObject gameObject : this.parent.getGameObjectsByID("coin")) {
			if (CollisionHandler.isColliding(this, gameObject)) {
				if (gameObject.isVisible() == true) {

					gameObject.setVisible(false);
				}

			}
		}
	}

	public void paintComponent(Graphics g) {
		if (!isVisible) {
			return;
		}

		g.setColor(Color.blue);
		if (GamePlayScreen.playerDead) {
			g.setColor(Color.red);
		}
		g.fillRect(x - GamePlayScreen.viewX, y - GamePlayScreen.viewY, width,
				height);

		g.setColor(Color.black);
		if (!gravitySwitch) {
			g.fillRect(x - GamePlayScreen.viewX + 3, y - GamePlayScreen.viewY
					+ 37, width - 6, 8);
		} else {
			g.fillRect(x - GamePlayScreen.viewX + 3, y - GamePlayScreen.viewY
					+ 3, width - 6, 8);
		}

	}

}
