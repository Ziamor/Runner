package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.StartPortal;
import com.ziamor.runner.gameObjects.levels.LevelParser;

public class GamePlayScreen extends GameScreen {

	public static int endPortalX;
	public static int viewX;
	public static int viewY;
	public static boolean levelComplete;
	private static boolean levelCompleteSplash;
	public static int levelWidth;
	public static int levelHeight;
	public static StartPortal startPortal;

	public GamePlayScreen() {
		levelComplete = false;
		levelCompleteSplash = false;
		Player.spawningAnimation = true;

		Runner.score = 0; // initialize score
		Runner.stars = 0; // initialize stars

		this.addGameObject(LevelParser.loadLevel());
		startPortal = new StartPortal(Player.x, 0);

		// initialize view
		viewX = Player.x - 64;
		viewY = Player.y - 250;
		if (viewY > levelHeight - 560) // if too low
			viewY = levelHeight - 560;
		if (viewY < 0) // if too high
			viewY = 0;
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (getDisableUpdate())
			return;

		// if the user paused the game
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			Runner._gameScreenManager.addScreen(new GamePauseScreen());
			this.setDisableUpdate(true); // freeze game objects
		}

		// if the user enters editor mode
		if (Runner._input.isKeyHit(InputManager._keys.get("L"))) {
			Runner._gameScreenManager.addScreen(new LevelEditScreen());
			Runner._gameScreenManager.removeScreen(this); // removes this screen
		}

		// move the view smoothly
		if (!Player.isDead && !Player.spawningAnimation) {
			// the view won't go past the end portal
			int offXDest = Player.x - 40;
			if (Player.x > endPortalX - 582)
				offXDest = endPortalX - 582;
			viewX += (int) ((offXDest - viewX) / 10);

			int offYDest = Player.y - 250;
			if (offYDest > levelHeight - 548) // if too low
				offYDest = levelHeight - 548;
			if (offYDest < 0) // if too high
				offYDest = 0;
			if (!levelComplete) // if player isn't in endPortal
				viewY += (int) ((offYDest - viewY) / 1);
		}

		if (Player.isDead) {
			if (Player.y > levelHeight + 100) { // shortly after the player dies
				// go to the death screen
				Runner._gameScreenManager.addScreen(new DeathScreen());
				this.setDisableUpdate(true); // freeze game objects
			}
		}

		if (levelComplete && Player.y < viewY - 500) {
			levelCompleteSplash = true;
		}

		// if the player has completed a level
		if (levelCompleteSplash) {

			// update the high score
			if (Runner.score > Runner.scoreHigh[Runner.world][Runner.level])
				Runner.scoreHigh[Runner.world][Runner.level] = Runner.score;

			// update the high stars
			if (Runner.stars > Runner.starsHigh[Runner.world][Runner.level])
				Runner.starsHigh[Runner.world][Runner.level] = Runner.stars;

			// go to the level complete screen
			Runner._gameScreenManager.addScreen(new LevelCompleteScreen());
			this.setDisableUpdate(true); // freeze game objects
		}

		// update the startPortal
		startPortal.update();
	}

	public void paintComponent(Graphics g) {
		if (getDisableRender())
			return;

		// draw the black fade at the bottom
		for (int i = 1; i < 6; i++) {
			g.setColor(new Color(0, 0, 0, 50 * i - 30));
			g.fillRect(0, levelHeight - viewY - 48 + i * 8, 720, 8);
			g.fillRect(0, -viewY + 40 - i * 8, 720, 8);
		}

		// draw the startPortal
		startPortal.paintComponent(g);

		// draw the objects
		super.paintComponent(g);

		g.setColor(Color.black);
		g.fillRect(0, 608 - 60, 720, 60);
		g.setColor(Color.gray);
		g.fillRect(5, 553, 710, 50);
		g.setColor(Color.black);
		g.drawString("Score: " + Runner.score, 20, 582);

	}
}
