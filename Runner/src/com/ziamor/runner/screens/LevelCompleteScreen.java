package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;

public class LevelCompleteScreen extends GameScreen {

	public LevelCompleteScreen() {
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
			// load the next level and remove this screen
			int size = GameScreenManager.getGameScreens().size();
			for (int i = 0; i < size; i++) {
				GameScreenManager.getGameScreens().remove(0);
			}
			Runner.level++;
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
		}

		// if user hits escape
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			// go back to the level select screen
			int size = GameScreenManager.getGameScreens().size();
			for (int i = 0; i < size; i++) {
				GameScreenManager.getGameScreens().remove(0);
			}
			Runner._gameScreenManager.addScreen(new LevelSelectScreen());
		}

	}

	public void paintComponent(Graphics g) {

		Color c = new Color(100, 100, 100, 100);
		g.setColor(c);
		g.fillRect(0, 0, 1024, 608);

		g.setColor(Color.red);
		g.fillRect(0, 300, 720, 100);
		g.setColor(Color.black);
		g.drawString("SUCCESS!", 350, 340);
		g.drawString("hit space for next level", 350, 355);
		g.drawString("hit escape for level select", 350, 370);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}
}
