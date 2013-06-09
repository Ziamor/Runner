package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.screens.*;

public class Button extends GameObject {

	public String purpose;

	public Button(String p) {
		purpose = p;
		x = 10;
		y = 10;
		width = 50;
		height = 50;
	}

	public void update() {
		if (Runner._input.isMouseClicked(x, y, width, height)) {
			System.out.println("Mouse clicked on the button");
			if (purpose == "MainMenu")
				Runner._gameScreenManager.addScreen(new MainMenuScreen());
			if (purpose == "WorldSelect")
				Runner._gameScreenManager.addScreen(new WorldSelectScreen(
						parent.world));
			if (purpose == "LevelSelect") {
				int size = GameScreenManager.getGameScreens().size();
				Runner._gameScreenManager.addScreen(new LevelSelectScreen(
						parent.world));
				// clear the GamePauseScreen and GamePlayScreen
				for (int i = 0; i < size; i++) {
					GameScreenManager.getGameScreens().remove(0);
				}
			}

			parent.setRemove(true);
		}
	}

	public void paintComponent(Graphics g) {
		g.setColor(Color.lightGray);
		g.fillRect(x, y, width, height);
		g.setColor(Color.black);
		g.drawString("Back",21,40);
	}

}
