package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.Hazard;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.Wall;
import com.ziamor.runner.gameObjects.Coin;

public class GamePlayScreen extends GameScreen {

	public static Player player;
	public static int viewX;
	public static int viewY;
	public static boolean playerDead;
	public static boolean levelComplete;
	private int playerDeadTimer;

	public GamePlayScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);

		Runner.score = 0; // initialize score
		Runner.stars = 0; // initialize stars

		// read the XML file and create all the objects in the level
		// Use Runner.world and Runner.level to find the right file
		//
		//
		// Do it Alex

		// add the player (remove after XML level creation works)
		player = new Player();
		this.addGameObject(player);
		playerDead = false;
		playerDeadTimer = 0;

		// initialize view
		viewX = player.getX() - 40;
		viewY = player.getY() - 250;

		// create a bunch of walls (remove after XML level creation works)
		int tempY = 450;
		for (int i = 0; i < 100; i++) {
			for (int j = 2; j < 18; j++) {
				// bottom wall
				Wall tempWall1 = new Wall();
				tempWall1.setX((i * 20 + j) * 32);
				tempWall1.setY(tempY);
				this.addGameObject(tempWall1);

				// top wall
				Wall tempWall2 = new Wall();
				tempWall2.setX((i * 20 + j) * 32);
				tempWall2.setY(tempY - 48 * 7);
				this.addGameObject(tempWall2);

				// coins
				if ((j > 4) & (j < 15)) {
					Coin tempCoin = new Coin();
					tempCoin.setX((i * 20 + j) * 32 + 8);
					tempCoin.setY(tempY + 4 - 48 * 6);
					this.addGameObject(tempCoin);
					Coin tempCoin2 = new Coin();
					tempCoin2.setX((i * 20 + j) * 32 + 8);
					tempCoin2.setY(tempY + 28 - 48 * 6);
					this.addGameObject(tempCoin2);
				}

				// more coins
				if ((j > 7) & (j < 12)) {
					Coin tempCoin3 = new Coin();
					tempCoin3.setX((i * 20 + j) * 32 + 8);
					tempCoin3.setY(tempY + 4 - 48 * 4);
					this.addGameObject(tempCoin3);
					Coin tempCoin4 = new Coin();
					tempCoin4.setX((i * 20 + j) * 32 + 8);
					tempCoin4.setY(tempY + 28 - 48 * 4);
					this.addGameObject(tempCoin4);
					Coin tempCoin5 = new Coin();
					tempCoin5.setX((i * 20 + j) * 32 + 8);
					tempCoin5.setY(tempY + 4 - 48 * 3);
					this.addGameObject(tempCoin5);
				}

				// hazards
				if ((j > 8) & (j < 11) && i > 2) {
					Hazard tempHazard = new Hazard();
					tempHazard.setX((i * 20 + j) * 32);
					tempHazard.setY(tempY - 16);
					this.addGameObject(tempHazard);
				}
			}
			tempY = (int) (tempY + Math.random() * 200 - 100);
			tempY = Math.round(tempY / 48) * 48;
			if (tempY < 96)
				tempY = 96;
			if (tempY > 48 * 13)
				tempY = 48 * 13;
		}

	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;

		// check to see if the user paused the game
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			Runner._gameScreenManager.addScreen(new GamePauseScreen());
			this.setBlockUpdate(false); // freeze game objects
		}

		// move the view smoothly
		if (!playerDead) {
			viewX += (int) ((player.getX() - 40 - viewX) / 10);
			viewY += (int) ((player.getY() - 250 - viewY) / 20);
			// prevent the view from being too low
			if (viewY > 240)
				viewY = 240;
		} else {
			playerDeadTimer++;
			if (playerDeadTimer == 100) { // shortly after the player dies
				// go back to the main menu screen
				// (eventually there will be a death screen)
				Runner._gameScreenManager.addScreen(new MainMenuScreen());
				Runner._gameScreenManager.removeScreen(this);
			}
		}

		if (levelComplete) { // run once the player completes a level

			// update the high score
			if (Runner.score > Runner.scoreHigh[Runner.world][Runner.level])
				Runner.scoreHigh[Runner.world][Runner.level] = Runner.score;

			// update the high stars
			if (Runner.stars > Runner.starsHigh[Runner.world][Runner.level])
				Runner.starsHigh[Runner.world][Runner.level] = Runner.stars;

			// eventually,
			// we'll have some sort of
			// completion splash screen

			Runner._gameScreenManager.addScreen(new LevelSelectScreen());
			Runner._gameScreenManager.removeScreen(this);
		}

	}
	
	public void paintComponent(Graphics g) {
		// call the gameScreen paintComponent(g);
		super.paintComponent(g);
		
		g.setColor(Color.gray);
		g.fillRect(0, 560, 720, 48);
		g.setColor(Color.black);
		g.drawString("Score: " + Runner.score, 20, 590);
		
		
	}

}
