package com.ziamor.runner.gameObjects.levels;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.gameObjects.Cannon;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.Portal;
import com.ziamor.runner.gameObjects.Wall;
import com.ziamor.runner.screens.GamePlayScreen;

public class Level {
	private static final int _tileWidth = 32; // Temp width for the game;
	private static final int _tileHeight = 48; // Temp height for the game;

	public static ArrayList<GameObject> loadLevel(String fileName) {
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
					Portal startPort = new Portal(x * _tileWidth -32);
					level.add(startPort);
					Player player = new Player();
					Player.x = x * _tileWidth;
					Player.y = y * _tileHeight + 400;
					player.yStart = y * _tileHeight;
					level.add(player);
					x++;
				} else if (c == '3') {
					Portal endPort = new Portal(x * _tileWidth);
					level.add(endPort);
					GamePlayScreen.endPortalX = x * _tileWidth;
					x++;
				} else if (c == '4') {
					Cannon cannon = new Cannon();
					cannon.setX(x * _tileWidth);
					cannon.setY(y * _tileHeight);
					level.add(cannon);
					x++;
				} else if (c == '|') {
					x = 0;
					y++;
				}
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
}
