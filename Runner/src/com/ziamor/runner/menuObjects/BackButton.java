package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePauseScreen;
import com.ziamor.runner.screens.LevelSelectScreen;
import com.ziamor.runner.screens.MainMenuScreen;
import com.ziamor.runner.screens.MenuScreen;
import com.ziamor.runner.screens.WorldSelectScreen;

public class BackButton extends GameObject {

	public BackButton() {
		objID = "backButton";
		spriteID = "backButton";
		x = 15;
		y = 15;
		width = 50;
		height = 50;
	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)
				|| Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			if (parent instanceof MenuScreen && MenuScreen.mode != 0)
				MenuScreen.mode--;
			if (parent instanceof GamePauseScreen) {
				int size = GameScreenManager.getGameScreens().size();
				Runner._gameScreenManager.addScreen(new MenuScreen(2));
				// clear the GamePauseScreen and GamePlayScreen
				for (int i = 0; i < size; i++)
					GameScreenManager.getGameScreens().remove(0);
				parent.setRemove(true);
			}
		}
	}

	public void paintComponent(Graphics g) {
		if (parent instanceof MenuScreen && MenuScreen.mode == 0)
			return;

		g.drawImage(TextureCache._textures.get(spriteID).getTexture(), x, y,
				null);
	}

}
