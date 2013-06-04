package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.Wall;
import com.ziamor.runner.gameObjects.Coin;

public class GamePlayScreen extends GameScreen {

	public static Player player;
	public static Wall wall;
	public static int viewX;
	public static int viewY;
	public static boolean playerDead;
	private int playerDeadTimer;

	public GamePlayScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);

		// add the player
		player = new Player();
		this.addGameObject(player);
		playerDead = false;
		playerDeadTimer = 0;

		// initialize view
		viewX = player.getX() - 200;
		viewY = player.getY() - 300;

		// create a bunch of walls for testing
		int tempY = 450;
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 15; j++) {
				// middle wall
				Wall tempWall1 = new Wall();
				tempWall1.setX((i * 15 + j) * 32);
				tempWall1.setY(tempY);
				this.addGameObject(tempWall1);

				// top wall
				Wall tempWall2 = new Wall();
				tempWall2.setX((i * 15 + j) * 32);
				tempWall2.setY(0);
				this.addGameObject(tempWall2);

				// bottom wall
				Wall tempWall3 = new Wall();
				tempWall3.setX((i * 15 + j) * 32);
				tempWall3.setY(48 * 15);
				this.addGameObject(tempWall3);

				// coins
				if ((j > 2) & (j < 12)) {
					Coin tempCoin = new Coin();
					tempCoin.setX((i * 15 + j) * 32 + 8);
					tempCoin.setY(tempY - 32);
					this.addGameObject(tempCoin);
				}
			}
			tempY = (int) (tempY + Math.random() * 200 - 100);
			tempY = Math.round(tempY/48)*48;
			if (tempY < 96)
				tempY = 96;
			if (tempY > 48 * 13)
				tempY = 48 * 13;
		}

	}

	public void update() {
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}

		// move the view smoothly
		if (!playerDead) {
			viewX += (int) ((player.getX() - 150 - viewX) / 10);
			viewY += (int) ((player.getY() - 300 - viewY) / 20);
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

	}

}
