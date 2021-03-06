package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.menuObjects.BackButton;

public class GamePauseScreen extends GameScreen {

	public GamePauseScreen() {
		// make the back button
		this.addGameObject(new BackButton());
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (getDisableUpdate())
			return;

		// check to see if the user unpaused the game
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			// set the GamePlayScreen to update
			GameScreenManager.getGameScreens().get(0).setDisableUpdate(false);
			// remove the pause screen
			Runner._gameScreenManager.removeScreen(this);
		}

	}

	public void paintComponent(Graphics g) {

		Color c = new Color(0, 0, 0, 100);
		g.setColor(c);
		g.fillRect(0, 0, 760, 608);

		g.setColor(Color.red);
		g.fillRect(0, 0, 760, 30);
		g.fillRect(0, 608 - 30, 760, 30);
		g.setColor(Color.black);
		g.drawString("PAUSED", 350, 20);
		g.drawString("PAUSED", 350, 598);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}
}
