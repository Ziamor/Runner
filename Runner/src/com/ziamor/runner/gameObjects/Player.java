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

	private double yspeedDouble = 0;
	private int xspeed = 5;
	private boolean gravitySwitch = false;
	private int dashTimer = 0;
	private boolean gravitySwitchAble = true;

	public Player() {
		this.objID = "player";
		x = 80;
		y = 300;
		width = 32;
		height = 48;
	}

	public void update() {
		// don't update if inactive
		if (!isActive)
			return;

		// if player is dead
		if (GamePlayScreen.playerDead) {
			yspeedDouble += 0.5; // apply gravity
			int yspeed = ((int) yspeedDouble); // make yspeed an int
			x += xspeed; // update x
			y += yspeed; // update y
			return; // don't complete the rest of the update()
		}

		// make the player move forward
		if (xspeed > 5) {
			xspeed -= 0.2; // slow down the player if dashing
		} else {
			xspeed = 5;
		}

		// reduce the dash timer
		if (dashTimer > 0)
			dashTimer--;

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
		if ((!gravitySwitch && wallBelow) || (gravitySwitch && wallAbove)) {
			grounded = true;
			gravitySwitchAble = true;
		}
		if (gravitySwitch)
			gravity = -0.5;

		// check for space bar press
		if (Runner._input.isKeyHit(InputManager._keys.get("space"))) {
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

		// check for right arrow press
		if (Runner._input.isKeyHit(InputManager._keys.get("right"))
				&& dashTimer == 0) {
			xspeed = 20; // dash forward
			yspeedDouble = 0;
			dashTimer = 60;
		}

		// check for left arrow press
		if (Runner._input.isKeyPressed(InputManager._keys.get("left"))) {
			xspeed = -5;
			// just for debugging
			// it may not even do anything in the actual game
		}

		// check for down arrow press
		boolean fastFall = false;
		if (Runner._input.isKeyHit(InputManager._keys.get("down"))) {
			if (gravitySwitch && grounded == true)
				yspeedDouble = 13;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("down"))
				&& !gravitySwitch)
			fastFall = true;

		// check for up arrow press
		if (Runner._input.isKeyHit(InputManager._keys.get("up"))) {
			if (!gravitySwitch && grounded == true)
				yspeedDouble = -13;
		}
		if (Runner._input.isKeyPressed(InputManager._keys.get("up"))
				&& gravitySwitch)
			fastFall = true;

		// apply gravity
		if (fastFall)
			yspeedDouble += gravity * 3; // fast falling
		else
			yspeedDouble += gravity; // normal falling
		int yspeed = ((int) yspeedDouble); // make yspeed an int

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

		// kill the player if he is too low or too high
		if (y > 608 + 200 || y < -600) {
			GamePlayScreen.playerDead = true;
			xspeed = 0;
			yspeedDouble = -13;
		}

		// collect any coins that the player is touching
		for (GameObject gameObject : this.parent.getGameObjectsByID("coin")) {
			if (CollisionHandler.isColliding(this, gameObject)) {
				if (gameObject.isActive() == true) {
					gameObject.setActive(false); // make the coin inactive
					Runner.score += 100;
				}

			}
		}

		// check if the player is touching any hazards
		for (GameObject gameObject : this.parent.getGameObjectsByID("hazard")) {
			if (CollisionHandler.isColliding(this, gameObject)) {
				if (gameObject.isActive() == true) {
					// kill the player and knock him back
					GamePlayScreen.playerDead = true;
					xspeed = -4;
					yspeedDouble = -6;
					break;
				}

			}
		}
	}

	public void paintComponent(Graphics g) {
		if (!isVisible) {
			return; // don't paint if invisible
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
