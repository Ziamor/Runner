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
import com.ziamor.runner.gameObjects.*;
import com.ziamor.runner.screens.GamePlayScreen;
import com.ziamor.runner.screens.LevelEditScreen;

public class Level {
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
				if (c == '0')
					x++;
				else if (c == '1') {
					Wall wall = new Wall();
					wall.setX(x * _tileWidth);
					wall.setY(y * _tileHeight);
					level.add(wall);
					x++;
				} else if (c == '2') {
					level.add(new Portal(x * _tileWidth - 32));
					Player player = new Player();
					Player.x = x * _tileWidth;
					Player.y = y * _tileHeight + 400;
					Player.yStart = y * _tileHeight;
					level.add(player);
					x++;
				} else if (c == '3') {
					level.add(new Portal(x * _tileWidth - 32));
					GamePlayScreen.endPortalX = x * _tileWidth - 32;
					x++;
				} else if (c == '4') {
					BreakableWall wall = new BreakableWall();
					wall.setX(x * _tileWidth);
					wall.setY(y * _tileHeight);
					level.add(wall);
					x++;
				} else if (c == '5') {
					Coin coin = new Coin();
					coin.setX(x * _tileWidth);
					coin.setY(y * _tileHeight);
					level.add(coin);
					x++;
				} else if (c == '6') {
					Star star = new Star();
					star.setX(x * _tileWidth);
					star.setY(y * _tileHeight);
					level.add(star);
					x++;
				} else if (c == '|') {
					x = 0;
					y++;
				}

				if (x * 32 > GamePlayScreen.levelWidth)
					GamePlayScreen.levelWidth = x * 32;
				if (y * 48 > GamePlayScreen.levelHeight)
					GamePlayScreen.levelHeight = y * 48;
			}
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
				} else if (c == ',') {
					// do nothing
				} else if (c == '0') {
					map[x][y] = 0;
					x++;
				} else if (c == '1') {
					map[x][y] = 1;
					x++;
				} else if (c == '2') {
					map[x][y] = 2;
					x++;
				} else if (c == '3') {
					map[x][y] = 3;
					x++;
				} else if (c == '4') {
					map[x][y] = 4;
					x++;
				}

				if (x * _tileWidth > LevelEditScreen.levelWidth)
					LevelEditScreen.levelWidth = x * _tileWidth;
				if (y * _tileHeight > LevelEditScreen.levelHeight)
					LevelEditScreen.levelHeight = y * _tileHeight;
			}
		}
		return map;
	}
}
