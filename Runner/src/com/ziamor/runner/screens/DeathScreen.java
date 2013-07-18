package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;

public class DeathScreen extends GameScreen {

	public DeathScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;

		// if user hits space
		if (Runner._input.isKeyHit(InputManager._keys.get("space"))) {
			// go to the GamePlayScreen and remove this screen
			int size = GameScreenManager.getGameScreens().size();
			for (int i = 0; i < size; i++) {
				GameScreenManager.getGameScreens().remove(0);
			}
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
		}
		
	}

	public void paintComponent(Graphics g) {

		Color c = new Color(100, 100, 100, 100);
		g.setColor(c);
		g.fillRect(0, 0, 1024, 608);

		g.setColor(Color.red);
		g.fillRect(0, 300, 720, 100);
		g.setColor(Color.black);
		g.drawString("FAILURE!", 350, 340);
		g.drawString("hit space", 350, 360);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}
}
