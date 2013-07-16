package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.Runner;
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
		g.drawString("" + Runner.world + " - " + level, x + width / 2 - 10, y
				+ 30);
		g.drawString("" + Runner.scoreHigh[Runner.world][this.level], x + width
				/ 2, y + 60);

		// draw stars
		g.setColor(new Color(230, 230, 0, 255));
		for (int i = 1; i < Runner.starsHigh[Runner.world][level] + 1; i++) 
			g.fillRect(x + 9 + 32 * i, y + 80, 24, 24);
		g.setColor(Color.black);
		for (int i = 1; i < 4; i++) 
			g.drawRect(x + 9 + 32 * i, y + 80, 24, 24);
	}
}
