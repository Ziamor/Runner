package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.LevelSelectScreen;
import com.ziamor.runner.screens.MainMenuScreen;
import com.ziamor.runner.screens.WorldSelectScreen;

public class BackButton extends GameObject {

	public String destination;

	public BackButton(String dest) {
		spriteID = "backButton";
		destination = dest;
		x = 15;
		y = 15;
		width = 50;
		height = 50;
	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)) {
			if (destination == "MainMenu")
				Runner._gameScreenManager.addScreen(new MainMenuScreen());
			if (destination == "WorldSelect")
				Runner._gameScreenManager.addScreen(new WorldSelectScreen());
			if (destination == "LevelSelect") {
				int size = GameScreenManager.getGameScreens().size();
				Runner._gameScreenManager.addScreen(new LevelSelectScreen());
				// clear the GamePauseScreen and GamePlayScreen
				for (int i = 0; i < size; i++)
					GameScreenManager.getGameScreens().remove(0);
			}

			parent.setRemove(true);
		}
	}

	public void paintComponent(Graphics g) {
		g.drawImage(TextureCache._textures.get(spriteID).getTexture(), x, y,
				null);
		// g.setColor(Color.black);
		// g.drawString("Back", 26, 45);
	}

}
