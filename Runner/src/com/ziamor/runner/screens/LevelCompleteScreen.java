package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;

public class LevelCompleteScreen extends GameScreen {

	public LevelCompleteScreen() {
		// update the high score
		if (Runner.score > Runner.scoreHigh[Runner.world][Runner.level])
			Runner.scoreHigh[Runner.world][Runner.level] = Runner.score;

		// update the high stars
		if (Runner.stars > Runner.starsHigh[Runner.world][Runner.level])
			Runner.starsHigh[Runner.world][Runner.level] = Runner.stars;

		// update the total score and total stars
		Runner.scoreTotal = 0;
		Runner.starsTotal = 0;
		for (int i = 1; i < 4; i++) {
			for (int j = 1; j < 10; j++) {
				Runner.scoreTotal += Runner.scoreHigh[i][j];
				Runner.starsTotal += Runner.starsHigh[i][j];
			}
		}
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (getDisableUpdate())
			return;

		// if user hits space
		if (Runner._input.isKeyHit(InputManager._keys.get("space"))
				|| Runner._input.isMouseClicked(260, 250, 200, 70)) {
			// load the next level and remove this screen
			int size = GameScreenManager.getGameScreens().size();
			for (int i = 0; i < size; i++) {
				GameScreenManager.getGameScreens().remove(0);
			}
			Runner.level++;
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
		}

		// if user hits escape
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))
				|| Runner._input.isMouseClicked(290, 355, 140, 60)) {
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
		g.fillRect(0, 0, 1024, 608 - 60);

		// draw the victory banner
		g.setColor(Color.green);
		g.fillRect(0, 60, 720, 150);
		g.setColor(Color.black);
		g.drawString("SUCCESS!", 280, 140);

		// draw the next level button
		g.setFont(Runner.fontLarge);
		g.setColor(Color.cyan);
		g.fillRect(260, 250, 200, 70);
		g.setColor(Color.black);
		g.drawString("Next Level", 280, 295);

		// draw the level select button
		g.setFont(Runner.fontSmall);
		g.setColor(Color.cyan);
		g.fillRect(290, 355, 140, 60);
		g.setColor(Color.black);
		g.drawString("Return to", 327, 379);
		g.drawString("Level Select", 316, 398);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}
}
