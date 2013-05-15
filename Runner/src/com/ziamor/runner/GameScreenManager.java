package com.ziamor.runner;

import java.awt.Graphics;
import java.util.Stack;

public class GameScreenManager {

	private static Stack<GameScreen> gameScreens = new Stack<GameScreen>();

	public void addScreen(GameScreen screen) {
		gameScreens.push(screen);
	}

	public void removeScreen(GameScreen screen) {
		gameScreens.remove(screen);
	}

	public static void update() {
		Stack<GameScreen> screensToDraw = gameScreens;

		for (GameScreen screen : screensToDraw) {
			screen.update();
		}
	}

	public static void paintComponent(Graphics g) {
		Stack<GameScreen> screensToUpdate = gameScreens;

		for (GameScreen screen : screensToUpdate) {
			screen.paintComponent(g);
		}

	}
}
