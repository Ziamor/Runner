package com.ziamor.runner;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

import com.ziamor.runner.gameObjects.Wall;

public class GameScreenManager {

	private static Stack<GameScreen> gameScreens = new Stack<GameScreen>();

	public void addScreen(GameScreen screen) {
		gameScreens.push(screen);
	}

	public void removeScreen(GameScreen screen) {
		screen.clearGameObjects();
		gameScreens.remove(screen);
	}

	public static void update() {
		Stack<GameScreen> screensToUpdate = new Stack<GameScreen>();
		screensToUpdate.addAll(gameScreens);

		for (int i = 0; i < screensToUpdate.size(); i++) {
			GameScreen screen = screensToUpdate.pop();
			screen.update();
		}
	}

	public static void paintComponent(Graphics g) {
		Stack<GameScreen> screensToDraw = new Stack<GameScreen>();
		screensToDraw.addAll(gameScreens);

		for (int i = 0; i < screensToDraw.size(); i++) {
			GameScreen screen = screensToDraw.pop();
			screen.paintComponent(g);

		}

	}
}
