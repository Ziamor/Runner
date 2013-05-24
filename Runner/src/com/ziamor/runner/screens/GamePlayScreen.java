package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.List;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.Wall;

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

		// add one wall
		wall = new Wall();
		wall.setX(100);
		wall.setY(350 + 80);
		this.addGameObject(wall);

		// initialize view
		viewX = player.getX() - 200;
		viewY = player.getY() - 300;

		int tempY = 450;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 12; j++) {
				Wall tempWall = new Wall();
				tempWall.setX((i * 15 + j) * 32);
				tempWall.setY(tempY);
				this.addGameObject(tempWall);
			}
			tempY = (int) (tempY + Math.random() * 200 - 100);
		}
	}

	public void update() {
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}

		// move the view smoothly
		if (!playerDead) {
			viewX += (int) ((player.getX() - 200 - viewX) / 5);
			viewY += (int) ((player.getY() - 300 - viewY) / 20);
			// prevent the view from being too low
			if (viewY > 200)
				viewY = 200;

		} else {
			playerDeadTimer++;
			if (playerDeadTimer == 100) {
				Runner._gameScreenManager.addScreen(new MainMenuScreen());
				Runner._gameScreenManager.removeScreen(this);
			}
		}

	}

}
