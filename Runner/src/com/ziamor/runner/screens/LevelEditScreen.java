package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.GameObjectFactory;
import com.ziamor.runner.gameObjects.Player;
import com.ziamor.runner.gameObjects.levels.LevelParser;
import com.ziamor.runner.gameObjects.levels.TextLevel;
import com.ziamor.runner.menuObjects.LevelEditOverlay;

public class LevelEditScreen extends GameScreen {

	public static int viewX;
	public static int viewY;
	public static int levelWidth;
	public static int levelHeight;
	private int startPortalX;
	private int endPortalX;
	private int selectedTile;

	public LevelEditScreen(){		
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		this.gameObjects = LevelParser.loadLevel();
		Player.y = Player.yStart;
		this.gameObjects.add(new LevelEditOverlay(0,608));
		selectedTile = 1;
	}

	public void update() {
		// call the gameScreen update();
		//super.update();
		if (!getBlockUpdate())
			return;

		// move the camera with the arrow keys
		if (Runner._input.isKeyPressed(InputManager._keys.get("left")))
			viewX -= 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("right")))
			viewX += 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("up")))
			viewY -= 10;
		if (Runner._input.isKeyPressed(InputManager._keys.get("down")))
			viewY += 10;

		// change the level size with WASD
		if (Runner._input.isKeyHit(InputManager._keys.get("a")))
			levelWidth -= 32;
		if (Runner._input.isKeyHit(InputManager._keys.get("d")))
			levelWidth += 32;
		if (Runner._input.isKeyHit(InputManager._keys.get("w")))
			levelHeight -= 24;
		if (Runner._input.isKeyHit(InputManager._keys.get("s")))
			levelHeight += 24;

		if (levelHeight < 552)
			levelHeight = 552;
		if (levelHeight > 50 * 24)
			levelHeight = 50 * 24;

		GamePlayScreen.viewX = LevelEditScreen.viewX;
		GamePlayScreen.viewY = LevelEditScreen.viewY;
		
		// if the user clicks
		if (Runner._input.isMouseClicked()) {
			// put a tile onto the map
			int x = (int) (InputManager.mouse_x + viewX) / 32;
			int y = (int) (InputManager.mouse_y + viewY) / 24;
			/*if (Runner._input.isMouseClicked(-viewX, -viewY, levelWidth * 32,
					levelHeight * 24) && InputManager.mouse_y < 548)*/
				gameObjects.add(GameObjectFactory.getById(selectedTile).create(
						x * 32, y * 24));
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

		/*if (Runner._input.isMouseClickedRight()) {
			// remove a tile from the map
			int x = (int) (InputManager.mouse_x + viewX) / 32;
			int y = (int) (InputManager.mouse_y + viewY) / 24;
			if (Runner._input.isMouseClickedRight(-viewX, -viewY,
					levelWidth * 32, levelHeight * 24)
					&& InputManager.mouse_y < 548)
				;//map[x][y] = 0;
		}

		/*
		 * // when the user presses "escape", save and play if
		 * (Runner._input.isKeyPressed(InputManager._keys.get("escape"))) {
		 * FileWriter writer = null; try { String fileName = "res\\maps\\" +
		 * Runner.world + "-" + Runner.level; String newLine =
		 * System.getProperty("line.separator"); writer = new
		 * FileWriter(fileName); for (int y = 0; y < levelHeight / 24; y++) {
		 * for (int x = 0; x < levelWidth / 32; x++) { writer.write(map[x][y] +
		 * ","); } writer.write("|" + newLine); }
		 * 
		 * writer.flush(); writer.close(); } catch (IOException e) {
		 * e.printStackTrace(); } finally { try { // Close the writer regardless
		 * of what happens... writer.close(); } catch (Exception e) { } }
		 * 
		 * // go to the GamePlayScreen Runner._gameScreenManager.addScreen(new
		 * GamePlayScreen()); Runner._gameScreenManager.removeScreen(this); }
		 *
		// when the user presses "escape", save and play
		if (Runner._input.isKeyPressed(InputManager._keys.get("escape"))) {
			ArrayList<GameObject> GameObjectData = new ArrayList<GameObject>();
			for (int y = 0; y < levelHeight / 24; y++) {
				for (int x = 0; x < levelWidth / 32; x++) {
					//GameObject gobj = GameObjectFactory.getById(map[x][y])
					//		.create(x * 32, y * 24);
					
					//GameObjectData.add(gobj);
				}
			}
			//LevelParser.saveLevel(GameObjectData);
			// go to the GamePlayScreen
			Runner._gameScreenManager.addScreen(new GamePlayScreen());
			Runner._gameScreenManager.removeScreen(this);
		}
*/
		startPortalX = -1;
		endPortalX = -1;
	}

	public void paintComponent(Graphics g) {
		// draw the grid
		for (int x = 0; x < levelWidth / 32; x++) {
			for (int y = 0; y < levelHeight / 24; y++) {
				if (y % 2 == 0) {
					g.setColor(Color.lightGray);
					g.drawRect(x * 32 - viewX, y * 24 - viewY, 31, 47);
				} else {
					g.setColor(new Color(0, 0, 0, 40));
					g.drawLine(x * 32 - viewX, y * 24 - viewY, x * 32 - viewX
							+ 32, y * 24 - viewY);
				}
			}
		}

		// draw the tiles


		// draw the portals last so they are on top
		g.setColor(new Color(0, 255, 255, 100));
		if (startPortalX != -1)
			g.fillRect(startPortalX, -viewY, 96, levelHeight);
		if (endPortalX != -1)
			g.fillRect(endPortalX, -viewY, 96, levelHeight);

		/*// draw the borders
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
*/
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
		} else if (id == 6) { // star
			g.setColor(new Color(230, 230, 0, 255));
			g.fillRect(x + 4, y, 24, 24);
		}
	}
}
