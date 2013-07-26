package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import com.ziamor.runner.GameScreen;
import com.ziamor.runner.Runner;
import com.ziamor.runner.TextureCache;
import com.ziamor.runner.menuObjects.AchieveButton;
import com.ziamor.runner.menuObjects.BackButton;
import com.ziamor.runner.menuObjects.LevelSelectButton;

public class LevelSelectScreen extends GameScreen {

	public LevelSelectScreen() {
		// make the level buttons
		for (int i = 1; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				this.addGameObject(new LevelSelectButton(205 * i - 135,
						139 * j + 110, i + j * 3));
			}
		}

		// make the corner buttons button
		this.addGameObject(new BackButton("WorldSelect"));
		this.addGameObject(new AchieveButton());
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (getDisableUpdate())
			return;
	}

	public void paintComponent(Graphics g) {

		// draw the border
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, 80, 80);
		g.fillRect(720 - 80, 0, 80, 80);
		g.fillRect(0, 0, 720, 60);
		g.fillRect(0, 608 - 80, 80, 80);
		g.fillRect(720 - 80, 608 - 80, 80, 80);
		g.fillRect(0, 608 - 60, 720, 60);

		// draw the objects
		super.paintComponent(g);


		Runner.scoreTotal = 200*(100*500+3*10000);
		Runner.starsTotal = 3*200;
		
		// draw the score display
		g.setColor(new Color(20,110,255,255));
		g.setFont(Runner.fontSmall);
		g.drawString("Score:",338,18);
		g.setFont(Runner.fontLarge);
		g.drawString("" + Runner.scoreTotal,
				361 - String.valueOf(Runner.scoreTotal).length() * 9, 50);
		
		// draw the star display
		g.setColor(new Color(225,225,0,255));
		g.drawImage(TextureCache._textures.get("star").getTexture(0,32,32), 100, 14,
				null);
		g.drawString("x " + Runner.starsTotal, 140, 40);
		
		// draw the trophy display

	}
}
