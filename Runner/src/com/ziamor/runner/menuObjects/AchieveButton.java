package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class AchieveButton extends GameObject {

	public AchieveButton() {
		x = 655;
		y = 15;
		width = 50;
		height = 50;
	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)) {
			Runner._gameScreenManager.addScreen(new AchieveScreen());
			parent.setBlockUpdate(false); // freeze game objects
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString("Ach", x+11, 45);
	}

}
