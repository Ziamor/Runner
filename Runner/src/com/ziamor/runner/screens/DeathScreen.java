package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;

public class DeathScreen extends GameScreen {

	public DeathScreen() {
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (getDisableUpdate())
			return;

		// if user hits space
		// or if the user clicks the "retry level" button
		if (Runner._input.isKeyHit(InputManager._keys.get("space"))
				|| Runner._input.isMouseClicked(280, 250, 200, 70)) {
			// load the next level and remove this screen
			int size = GameScreenManager.getGameScreens().size();
			for (int i = 0; i < size; i++) {
				GameScreenManager.getGameScreens().remove(0);
			}
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
		}

		// if user hits escape
		// or if the user clicks the "return to level select" button
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))
				|| Runner._input.isMouseClicked(310, 355, 140, 60)) {
			// go back to the level select screen
			int size = GameScreenManager.getGameScreens().size();
			for (int i = 0; i < size; i++) {
				GameScreenManager.getGameScreens().remove(0);
			}
			Runner._gameScreenManager.addScreen(new MenuScreen(2));
		}

	}

	public void paintComponent(Graphics g) {

		// dim the background
		Color c = new Color(100, 100, 100, 100);
		g.setColor(c);
		g.fillRect(0, 0, 1024, 608 - 60);

		// draw the failure banner
		g.setColor(Color.red);
		g.fillRect(0, 60, 760, 150);
		g.setColor(Color.black);
		g.drawString("FAILURE!", 305, 140);

		// draw the retry level button
		g.setFont(Runner.fontLarge);
		g.drawImage(TextureCache._textures.get("largeButton").getTexture(),
				280, 250, null); // width 200, height 70
		g.setColor(Color.black);
		g.drawString("Retry Level", 294, 295);

		// draw the level select button
		g.setFont(Runner.fontSmall);
		g.drawImage(TextureCache._textures.get("smallButton").getTexture(),
				310, 355, null); // width 140, height 60
		g.setColor(Color.black);
		g.drawString("Return to", 347, 379);
		g.drawString("Level Select", 336, 398);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}
}
