package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.BackButton;
import com.ziamor.runner.gameObjects.levels.Level;

public class LevelEditScreen extends GameScreen {

	public static int viewX;
	public static int viewY;
	public static int levelWidth;
	public static int levelHeight;
	private int[][] map;

	public LevelEditScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		map = Level.loadLevelToEdit();
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;

		// if the user kits escape
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			Runner._gameScreenManager.addScreen(new GamePauseScreen());
			this.setBlockUpdate(false); // freeze game objects
		}

		// move the camera with the arrow keys
		if (Runner._input.isKeyPressed(InputManager._keys.get("left")))
			viewX -= 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("right")))
			viewX += 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("up")))
			viewY -= 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("down")))
			viewY += 10;

		// if the user clicks
		if (Runner._input.isMouseClicked()) {
			int x = (int) (InputManager.mouse_x + viewX) / 32;
			int y = (int) (InputManager.mouse_y + viewY) / 48;
			map[x][y] = 1;
		}
		if (Runner._input.isMouseClickedRight()) {
			int x = (int) (InputManager.mouse_x + viewX) / 32;
			int y = (int) (InputManager.mouse_y + viewY) / 48;
			map[x][y] = 0;
		}

		// ALEX
		// if the user presses a certain key, maybe s for save
		// write the map to the file
		// Level.saveLevel(map);

	}

	public void paintComponent(Graphics g) {
		int startPortalX = -1;
		int endPortalX = -1;

		for (int x = 0; x < levelWidth / 32; x++) {
			for (int y = 0; y < levelHeight / 48; y++) {
				// draw the grid
				g.setColor(Color.lightGray);
				g.drawRect(x * 32 - viewX, y * 48 - viewY, 31, 47);

				// draw all the different tiles
				if (map[x][y] == 1) { // wall
					g.setColor(Color.black);
					g.fillRect(x * 32 - viewX, y * 48 - viewY, 32, 48);
					g.setColor(Color.gray);
					g.fillRect(x * 32 + 4 - viewX, y * 48 + 4 - viewY, 24, 40);
				} else if (map[x][y] == 2) { // player start position
					g.setColor(Color.blue);
					g.fillRect(x * 32 - viewX, y * 48 - viewY, 32, 48);
					startPortalX = x * 32 - viewX - 32;
				} else if (map[x][y] == 3) { // end portal
					endPortalX = x * 32 - viewX - 32;
				}
			}
		}

		// draw the portals last so they are on top
		g.setColor(new Color(0, 255, 255, 100));
		if (startPortalX != -1)
			g.fillRect(startPortalX, -viewY, 96, levelHeight);
		if (endPortalX != -1)
			g.fillRect(endPortalX, -viewY, 96, levelHeight);

		// draw the borders
		g.setColor(Color.darkGray);
		g.fillRect(0, 0, -viewX, 608);
		g.fillRect(0, 0, 720, -viewY);
		g.fillRect(levelWidth - viewX, 0, 720 - levelWidth + viewX, 608);
		g.fillRect(0, levelHeight - viewY, 720, 608 - levelHeight + viewY);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}
}
