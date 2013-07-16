package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.CollisionHandler;
import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.GamePlayScreen;

public class Player extends GameObject {

	public static int x;
	public static int y;
	private double yspeedDouble;
	private int xspeed;
	private boolean gravitySwitch;
	private int dashTimer;
	private boolean gravitySwitchAble;
	public static int yStart;

	public Player(int x, int yStart) {
		Player.x = x;
		Player.y = GamePlayScreen.levelHeight;
		Player.yStart = yStart;
		this.objID = "player";
		width = 32;
		height = 48;

		yspeedDouble = 0;
		xspeed = 0;

		gravitySwitch = false;
		dashTimer = 0;
		gravitySwitchAble = true;

	}

	public void update() {
		// don't update if inactive
		if (!isActive)
			return;

		// if the player hasn't started the level yet
		if (GamePlayScreen.preLevel) {
			if (y > yStart + 100) {
				yspeedDouble -= 0.5;
				if (yspeedDouble < -15)
					yspeedDouble = -15;
			} else
				yspeedDouble += 0.5;

			if (y > yStart && yspeedDouble > 0) {
				y = yStart;
				GamePlayScreen.preLevel = false;
			}

			if (GamePlayScreen.preLevel) {
				y += yspeedDouble;
				return;
			}
		}

		// if player is dead
		if (GamePlayScreen.playerDead) {
			yspeedDouble += 0.5; // apply gravity
			int yspeed = ((int) yspeedDouble); // make yspeed an int
			x += xspeed; // update x
			y += yspeed; // update y
			return; // don't complete the rest of the update()
		}

		// check if the player has entered the end portal
		if (x > GamePlayScreen.endPortalX + 32 && !GamePlayScreen.levelComplete) {
			GamePlayScreen.levelComplete = true;
			yspeedDouble *= 0.5;
			gravitySwitch = false;
		}

		// if player is in a portal at the end of the level
		if (GamePlayScreen.levelComplete) {
			if (yspeedDouble > 0) // apply gravity
				yspeedDouble = (yspeedDouble - 1) * 0.8;
			else
				yspeedDouble -= 0.2;
			if (yspeedDouble < -10) // yspeed cap
				yspeedDouble = -10;
			int yspeed = ((int) yspeedDouble); // make yspeed an int
			if (x > GamePlayScreen.endPortalX + 32)
				xspeed += -1;
			else
				xspeed += 1;
			xspeed = (int) Math.round(xspeed * 0.9);
			x += xspeed; // update x
			y += yspeed; // update y
			return; // don't complete the rest of the update()
		}
		
		// ****************************************************
		// everything below here is for regular player movement
		// ****************************************************

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
		for (GameObject gobj : this.parent.getGameObjectsByID("wall",
				"breakablewall")) {
			if (CollisionHandler.isColliding(this, gobj) && gobj.isActive()) {
				if (yspeedDouble > 0) // stop the player if moving down
					yspeedDouble = 0;
				wallBelow = true;
			}
		}
		y = y - 2; // change y to check for walls above
		for (GameObject gobj : this.parent.getGameObjectsByID("wall",
				"breakablewall")) {
			if (CollisionHandler.isColliding(this, gobj) && gobj.isActive()) {
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
			for (GameObject gameObject : this.parent.getGameObjectsByID("wall",
					"breakablewall")) {
				if (CollisionHandler.isColliding(this, gameObject)
						&& gameObject.isActive()) {
					collision = true;
					if (xspeed > 6 && gameObject.getObjID() == "breakablewall") {
						collision = false;
						gameObject.setActive(false);
					}
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
		if (y > GamePlayScreen.levelHeight || y < -200) {
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

		// collect any stars that the player is touching
		for (GameObject gameObject : this.parent.getGameObjectsByID("star")) {
			if (CollisionHandler.isColliding(this, gameObject)) {
				if (gameObject.isActive() == true) {
					gameObject.setActive(false); // make the coin inactive
					gameObject.setVisible(false);
					Runner.score += 10000;
					Runner.stars++;
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
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;

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

	public int getX() { // need this because x is static
		return x;
	}

	public int getY() { // need this because y is static
		return y;
	}

}
