package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.screens.GamePlayScreen;

public class LevelSelectButton extends GameObject {

	public int level;

	public LevelSelectButton(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
		width = 170;
		height = 110;
	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)) {

			Runner.level = this.level; // set the level
			Runner._gameScreenManager.addScreen(new GamePlayScreen());

			// remove the LevelSelectScreen
			parent.setRemove(true);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.setFont(Runner.fontLarge);
		g.drawString("" + Runner.world + " - " + level, x + width / 2 - 30,
				y + 40);

		// draw the score
		int score = Runner.scoreHigh[Runner.world][this.level];
		if (score > 0) {
			g.setFont(Runner.fontSmall);
			g.drawString("" + score, x + width / 2
					- String.valueOf(score).length() * 4 + 2, y + 68);
		}

		// draw stars
		g.setColor(new Color(230, 230, 0, 255));
		int i = 1;
		for (i = 1; i < Runner.starsHigh[Runner.world][level] + 1; i++)
			g.drawImage(TextureCache._textures.get("star")
					.getTexture(0, 32, 32), x + 37 * i - 5, y + 74, null);
		for (int j = i; j < 4; j++)
			g.drawImage(TextureCache._textures.get("starOutline").getTexture(),
					x + 37 * j - 5, y + 74, null);
	}
}
