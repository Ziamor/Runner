package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.Wall;
import com.ziamor.runner.gameObjects.StartPortal;
import com.ziamor.runner.gameObjects.levels.LevelParser;

public class GamePlayScreen extends GameScreen {

	public static int endPortalX;
	public static int viewX;
	public static int viewY;
	public static boolean levelComplete;
	public static int levelWidth;
	public static int levelHeight;
	public static StartPortal startPortal;
	private int needControlScreen;

	public GamePlayScreen() {
		// set variables
		levelComplete = false;
		Player.spawningAnimation = true;

		// reset score and stars
		Runner.score = 0;
		Runner.stars = 0;

		// add all the objects
		this.addGameObject(LevelParser.loadLevel());
		startPortal = new StartPortal(Player.x, 0);

		// initialize view
		viewX = Player.x - 64;
		viewY = Player.y - 250;
		if (viewY > levelHeight - 560) // if too low
			viewY = levelHeight - 560;
		if (viewY < 0) // if too high
			viewY = 0;
		
		
		// if there is a new move introduced in this level
		if (Runner.level == 1) {
			needControlScreen = 1;
		}
	}

	public void update() {
		// draw the controlScreen, if required
		if (needControlScreen > 0) {
			Runner._gameScreenManager.addScreen(new ControlScreen());
			this.setDisableUpdate(true); // freeze game objects
			needControlScreen = 0;
		}
		
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
		
		// draw the background
		//g.setColor(new Color(0,255,255,0));
		//g.fillRect(0, 0, 760, 608);

		// draw the startPortal
		startPortal.paintComponent(g);

		// draw the objects
		super.paintComponent(g);

		// draw the black fade at the bottom
		for (int i = 1; i < 6; i++) {
			g.setColor(new Color(0, 0, 0, 50 * i - 30));
			g.fillRect(0, levelHeight - viewY - 48 + i * 8, 760, 8);
			g.fillRect(0, -viewY + 40 - i * 8, 760, 8);
		}

		// draw the overlay
		g.setColor(Color.black);
		g.fillRect(0, 608 - 60, 760, 60);
		g.setColor(Color.darkGray);
		g.fillRect(5, 553, 750, 50);

		// draw the score display
		g.setColor(new Color(20, 110, 255, 255));
		g.setFont(Runner.fontSmall);
		g.drawString("Score:", 358, 566);
		g.setFont(Runner.fontLarge);
		g.drawString("" + Runner.score, 381 - String.valueOf(Runner.score)
				.length() * 9, 598);

		// draw the star display
		int i = 1;
		for (i = 1; i < Runner.stars + 1; i++)
			g.drawImage(TextureCache._textures.get("star")
					.getTexture(0, 32, 32), 40 * i - 23, 562, null);
		for (int j = i; j < 4; j++)
			g.drawImage(TextureCache._textures.get("starOutline").getTexture(),
					40 * j - 23, 562, null);

	}
}
