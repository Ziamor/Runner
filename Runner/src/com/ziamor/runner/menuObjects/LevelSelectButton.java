package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;
import com.ziamor.runner.screens.MenuScreen;

public class LevelSelectButton extends GameObject {

	public int level;

	public LevelSelectButton(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
		width = 170;
		height = 105;
		objID = "levelSelectButton";
	}

	public void update() {
		// if the menu is on level select
		if (MenuScreen.mode == 2) {
			// if the level is unlocked
			if (Runner.isUnlocked[Runner.world][this.level] == true) {
				if (Runner._input.isMouseClicked(x, y, width, height)) {
					// set the current level to this level
					Runner.level = this.level;
					// go to the GamePlayScreen
					Runner._gameScreenManager.addScreen(new GamePlayScreen());
					// remove the LevelSelectScreen
					parent.setRemove(true);
				}
			}
		}
	}

	public void paintComponent(Graphics g) {
		if (MenuScreen.mode == 0)
			return;

		// draw the button
		if (Runner.isUnlocked[Runner.world][this.level])
			g.drawImage(TextureCache._textures.get("levelButton").getTexture(),
					x, y - MenuScreen.viewY - 608, null);
		else
			g.drawImage(TextureCache._textures.get("levelButtonLocked")
					.getTexture(), x, y - MenuScreen.viewY - 608,
					null);

		// draw the text
		g.setColor(Color.black);
		g.setFont(Runner.fontLarge);
		g.drawString("" + Runner.world + " - " + level, x
				+ width / 2 - 32, y - MenuScreen.viewY - 608 + 49);

		// draw the score
		int score = Runner.scoreHigh[Runner.world][this.level];
		if (score > 0) {
			g.setFont(Runner.fontSmall);
			g.drawString("" + score, x + width / 2
					- String.valueOf(score).length() * 4 + 2, y
					- MenuScreen.viewY - 608 + 113);
		}

		// draw stars
		g.setColor(new Color(230, 230, 0, 255));
		int i = 1;
		for (i = 1; i < Runner.starsHigh[Runner.world][level] + 1; i++)
			g.drawImage(TextureCache._textures.get("star")
					.getTexture(0, 32, 32), x + 37 * i - 5,
					y - MenuScreen.viewY - 608 + 65, null);
		for (int j = i; j < 4; j++)
			g.drawImage(TextureCache._textures.get("starOutline").getTexture(),
					x + 37 * j - 5, y - MenuScreen.viewY - 608
							+ 65, null);
	}
}
