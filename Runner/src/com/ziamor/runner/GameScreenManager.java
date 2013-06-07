package com.ziamor.runner;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Stack;

import com.ziamor.runner.gameObjects.Wall;

public class GameScreenManager {

	private static Stack<GameScreen> gameScreens = new Stack<GameScreen>();

	public void addScreen(GameScreen screen) {
		getGameScreens().push(screen);
	}

	public void removeScreen(GameScreen screen) {
		screen.clearGameObjects();
		getGameScreens().remove(screen);
	}

	public static void update() {
		Stack<GameScreen> screensToUpdate = new Stack<GameScreen>();
		screensToUpdate.addAll(getGameScreens());

		for (int i = 0; i < screensToUpdate.size(); i++) {
			GameScreen screen = screensToUpdate.get(i);
			screen.update();
		}
	}

	public static void paintComponent(Graphics g) {
		Stack<GameScreen> screensToDraw = new Stack<GameScreen>();
		screensToDraw.addAll(getGameScreens());

		for (int i = 0; i < screensToDraw.size(); i++) {
			GameScreen screen = screensToDraw.get(i);
			screen.paintComponent(g);

		}

	}

	public static Stack<GameScreen> getGameScreens() {
		return gameScreens;
	}

	public static void setGameScreens(Stack<GameScreen> gameScreens) {
		GameScreenManager.gameScreens = gameScreens;
	}
}
