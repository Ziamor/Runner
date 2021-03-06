package com.ziamor.runner.gameObjects.levels;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.BreakableWall;
import com.ziamor.runner.gameObjects.Coin;
import com.ziamor.runner.gameObjects.EndPortal;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.StartPortal;
import com.ziamor.runner.gameObjects.Star;
import com.ziamor.runner.gameObjects.Wall;
import com.ziamor.runner.screens.GamePlayScreen;

public class TextLevel {
	private static final int _tileWidth = 32; // Temp width for the game;
	private static final int _tileHeight = 24; // Temp height for the game;

	public static ArrayList<GameObject> loadLevel() {
		String fileName = "res\\maps\\" + Runner.world + "-" + Runner.level;
		ArrayList<GameObject> level = new ArrayList<GameObject>();
		String levelData = readLevelData(fileName);
		if (levelData != null) {
			int x = 0;
			int y = 0;

			for (char c : levelData.toCharArray()) {
				if (c == '|') {
					x = 0;
					y = y + _tileHeight;
				} else if (c == '0') {
					x = x + _tileWidth;
				} else if (c == '1') {
					level.add(new Wall(x, y));
					x = x + _tileWidth;
				} else if (c == '2') {
					level.add(new StartPortal(x - 32,500));
					level.add(new Player(x, y));
					x = x + _tileWidth;
				} else if (c == '3') {
					level.add(new EndPortal(x - 32,500));
					GamePlayScreen.endPortalX = x - 32;
					x = x + _tileWidth;
				} else if (c == '4') {
					level.add(new BreakableWall(x, y));
					x = x + _tileWidth;
				} else if (c == '5') {
					level.add(new Coin(x, y));
					x = x + _tileWidth;
				} else if (c == '6') {
					level.add(new Star(x, y));
					x = x + _tileWidth;
				}
			}

			if (x > GamePlayScreen.levelWidth)
				GamePlayScreen.levelWidth = x;
			if (y > GamePlayScreen.levelHeight)
				GamePlayScreen.levelHeight = y;
		}

		return level;
	}

	public static String readLevelData(String fileName) {
		try {
			Path path = Paths.get(fileName);
			List<String> lines = Files.readAllLines(path,
					StandardCharsets.UTF_8);
			return String.valueOf(lines);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public static int[][] loadLevelToEdit() {
		String fileName = "res\\maps\\" + Runner.world + "-" + Runner.level;
		String levelData = readLevelData(fileName);
		int[][] map = new int[400][50];
		if (levelData != null) {
			int x = 0;
			int y = 0;

			for (char c : levelData.toCharArray()) {
				if (c == '|') {
					x = 0;
					y++;
				} else
					for (int i = 0; i < 10; i++) {
						if (c == (char) ('0' + i)) {
							map[x][y] = i;
							x++;
							break;
						}
					}

				if (x * _tileWidth > GamePlayScreen.levelWidth)
					GamePlayScreen.levelWidth = x * _tileWidth;
				if (y * _tileHeight > GamePlayScreen.levelHeight)
					GamePlayScreen.levelHeight = y * _tileHeight;
			}
		}
		return map;
	}
}
