package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;

public class GamePauseScreen extends GameScreen {

	public void update() {
		// check to see if the user unpaused the game
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			// set the GamePlayScreen to update
			Stack<GameScreen> gameScreens = Runner._gameScreenManager.getGameScreens();
			GameScreen gameScreen = gameScreens.get(0);
			gameScreen.setBlockUpdate(true);
			// remove the pause screen
			Runner._gameScreenManager.removeScreen(this);
		}

	}

	public void paintComponent(Graphics g) {
		
		for (GameObject gameObject : gameObjects) {
			gameObject.paintComponent(g);
		}
		
		Color c = new Color(0, 0, 0, 100);
		g.setColor(c);
		g.fillRect(0, 0, 1024, 608);
		
		g.setColor(Color.red);
		g.fillRect(0, 0, 1024, 30);
		g.fillRect(0, 608-30, 1024, 30);
		g.setColor(Color.black);
		g.drawString("PAUSED", 500, 20);
		g.drawString("PAUSED", 500, 598);
		
	}
}
