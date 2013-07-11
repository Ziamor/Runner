package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.levels.Level;
import com.ziamor.runner.menuObjects.BackButton;

public class LevelEditScreen extends GameScreen {

	public static int viewX;
	public static int viewY;
	public static int levelWidth;
	public static int levelHeight;
	private int[][] map;
	private int startPortalX;
	private int endPortalX;
	private int selectedTile;

	public LevelEditScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		map = Level.loadLevelToEdit();
		selectedTile = 1;
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;

		// if the user hits escape
		if (Runner._input.isKeyHit(InputManager._keys.get("escape"))) {
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);
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
			// put a tile onto the map
			int x = (int) (InputManager.mouse_x + viewX) / 32;
			int y = (int) (InputManager.mouse_y + viewY) / 48;
			if (x < levelWidth && y < levelHeight
					&& InputManager.mouse_x > -viewX
					&& InputManager.mouse_y > -viewY
					&& InputManager.mouse_y < 548)
				map[x][y] = selectedTile;
			// select a different tile
			if (InputManager.mouse_y > 548) {
				for (int tile = 1; tile < 10; tile++) {
					if (Runner._input.isMouseClicked(tile * 50 - 40, 554, 32,
							48)) {
						selectedTile = tile;
					}
				}
			}
		}

		if (Runner._input.isMouseClickedRight()) {
			// remove a tile from the map
			int x = (int) (InputManager.mouse_x + viewX) / 32;
			int y = (int) (InputManager.mouse_y + viewY) / 48;
			if (Runner._input.isMouseClicked(-viewX, -viewY, levelWidth * 32,
					levelHeight * 48) && InputManager.mouse_y < 548)
				map[x][y] = 0;  
		}

		// ALEX
		// if the user presses a certain key, maybe s for save
		// write the map to the file
		// Level.saveLevel(map);

		startPortalX = -1;
		endPortalX = -1;
	}

	public void paintComponent(Graphics g) {

		for (int x = 0; x < levelWidth / 32; x++) {
			for (int y = 0; y < levelHeight / 48; y++) {
				// draw the grid
				g.setColor(Color.lightGray);
				g.drawRect(x * 32 - viewX, y * 48 - viewY, 31, 47);

				// draw the tile
				drawTile(map[x][y], x * 32 - viewX, y * 48 - viewY, g);
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

		// draw the editor panel
		g.setColor(new Color(200, 0, 200, 200));
		g.fillRect(0, 608 - 60, 720, 60);
		for (int tile = 1; tile < 10; tile++) {
			drawTile(tile, tile * 50 - 40, 554, g);
		}
		g.setColor(Color.yellow);
		g.drawRect(selectedTile * 50 - 43, 551, 37, 53);
		g.drawRect(selectedTile * 50 - 44, 550, 39, 55);

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}

	private void drawTile(int id, int x, int y, Graphics g) {
		if (id == 1) { // wall
			g.setColor(Color.black);
			g.fillRect(x, y, 32, 48);
			g.setColor(Color.gray);
			g.fillRect(x + 4, y + 4, 24, 40);
		} else if (id == 2) { // player start position
			g.setColor(Color.blue);
			g.fillRect(x, y, 32, 48);
			startPortalX = x - 32;
		} else if (id == 3) { // end portal
			g.setColor(Color.blue);
			g.drawRect(x + 2, y + 2, 28, 44);
			g.drawRect(x + 4, y + 4, 24, 40);
			endPortalX = x - 32;
		} else if (id == 4) { // breakable wall
			g.setColor(Color.gray);
			g.fillRect(x, y, 32, 48);
			g.setColor(Color.lightGray);
			g.fillRect(x + 4, y + 4, 24, 40);
		} else if (id == 5) { // coin
			g.setColor(new Color(230, 230, 0, 255));
			g.fillRect(x + 9, y + 17, 14, 14);
			g.fillRect(x + 13, y + 15, 6, 18);
			g.fillRect(x + 7, y + 21, 18, 6);
		}
	}
}
