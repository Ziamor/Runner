package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;

public class MainMenuScreen extends GameScreen {

	public MainMenuScreen() {
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (getDisableUpdate())
			return;

		if (Runner._input.isKeyPressed(InputManager._keys.get("space"))) {
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);
		}

		if (Runner._input.isKeyPressed(InputManager._keys.get("w"))) {
			Runner._gameScreenManager.addScreen(new WorldSelectScreen());
			Runner._gameScreenManager.removeScreen(this);
		}

	}

	public void paintComponent(Graphics g) {
		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

		// paint other stuff (temporary)
		g.setColor(Color.green);
		g.fillRect(0, 260, 720, 56);
		g.setColor(Color.lightGray);
		g.fillRect(100, 500, 300, 40);
		g.fillRect(450, 500, 40, 40);
		g.fillRect(500, 500, 40, 40);
		g.fillRect(550, 500, 40, 40);
		g.fillRect(500, 450, 40, 40);
		g.setColor(Color.black);
		g.drawString("Main Menu Screen", 325, 280);
		g.drawString("Press 'Space' to play", 317, 300);
		g.drawString("Press 'w' to go to the world select screen", 263, 350);
		g.setColor(Color.darkGray);
		g.drawString("Change Gravity", 220, 525);
		g.drawString("Jump", 505, 468);
		g.drawString("Up", 512, 482);
		g.drawString("Jump", 505, 518);
		g.drawString("Down", 504, 532);
		g.drawString("Dash", 555, 525);
	}
}
