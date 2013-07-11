package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.menuObjects.AchieveButton;
import com.ziamor.runner.menuObjects.BackButton;
import com.ziamor.runner.menuObjects.LevelSelectButton;
import com.ziamor.runner.menuObjects.WorldSelectButton;
import com.ziamor.runner.screens.*;

public class LevelSelectScreen extends GameScreen {

	public LevelSelectScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);

		// make the level buttons
		for (int i = 1; i < 4; i++) {
			for (int j = 0; j < 3; j++) {
				LevelSelectButton tempbutton = new LevelSelectButton();
				tempbutton.setX(205 * i - 135);
				tempbutton.setY(139 * j + 110);
				tempbutton.setWidth(170);
				tempbutton.setHeight(110);
				tempbutton.level = i + j * 3;
				this.addGameObject(tempbutton);
			}
		}

		// make the corner buttons button
		this.addGameObject(new BackButton("WorldSelectScreen"));
		this.addGameObject(new AchieveButton());
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
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

	}

}
