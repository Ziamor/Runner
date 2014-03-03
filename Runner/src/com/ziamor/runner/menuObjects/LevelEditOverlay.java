package com.ziamor.runner.menuObjects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.gameObjects.GameObjectFactory;
import com.ziamor.runner.screens.GamePlayScreen;
import com.ziamor.runner.screens.LevelEditScreen;

public class LevelEditOverlay extends GameObject {
	private ArrayList<GameObject> tiles;
	private int viewTile; // which tile is first to be displayed at the bottom
	private final int TileXStart = 80;
	public static boolean snapToGrid;

	public LevelEditOverlay(int x, int y) {
		super(x, y);
		this.width = Runner._width;
		this.height = 60;
		this.y = y - height;
		snapToGrid = true;
		tiles = new ArrayList<GameObject>();
		viewTile = 0;
		for (int i = 0; i < GameObjectFactory.getSize(); i++) {
			try {
				GameObject gobj = GameObjectFactory.getById(i).create(
						i * 50 - 40, y + 6);
				if (gobj != null)
					tiles.add(gobj);
			} catch (IllegalArgumentException e) {
				break;
			}
		}
	}

	public void update() {
		// change the level size with WASD
		if (Runner._input.isKeyHit(InputManager._keys.get("x")))
			viewTile--;
		if (Runner._input.isKeyHit(InputManager._keys.get("x")))
			viewTile++;

		if (viewTile > GameObjectFactory.getSize() - 2)
			viewTile = GameObjectFactory.getSize() - 2;
		if (viewTile < 0)
			viewTile = 0;

		if (Runner._input.isKeyHit(InputManager._keys.get("z")))
			snapToGrid = !snapToGrid;
	}

	public void paintComponent(Graphics g) {

		// draw the editor panel
		g.setColor(new Color(200, 0, 200, 200));
		g.fillRect(x, y, width, height);

		// draw all the tiles
		for (int i = viewTile; i < viewTile + 10; i++) {
			if (i < tiles.size()) {
				if (tiles.get(i) != null) {
					GameObject gobj = tiles.get(i);
					int backupX = gobj.getX();
					int backupY = gobj.getY();
					gobj.setX((i - viewTile) * 50 + 10 + TileXStart);
					gobj.setY(this.y + 6);
					GamePlayScreen.viewX = 0;
					GamePlayScreen.viewY = 0;
					tiles.get(i).paintComponent(g);
					gobj.setX(backupX);
					gobj.setY(backupY);
				}
			}
		}

		// draw the current tile marker
		if (LevelEditScreen.selectedTile > viewTile) {
			g.setColor(Color.yellow);
			g.drawRect((LevelEditScreen.selectedTile - viewTile) * 50 - 43
					+ TileXStart, 551, 37, 53);
			g.drawRect((LevelEditScreen.selectedTile - viewTile) * 50 - 44
					+ TileXStart, 550, 39, 55);
		}

		// draw the z and x controls
		// to move the tile select view
		g.setColor(Color.black);
		g.drawString("<< (Z)", 25, this.y + 35);
		g.drawString(">> (X)", 700, this.y + 35);

		// draw the grid snap info
		g.setColor(new Color(0, 0, 0, 150));
		if (snapToGrid)
			g.drawString("Grid snap is ON (Toggle with 'C')", 5, 15);
		else
			g.drawString("Grid snap is OFF (Toggle with 'C')", 5, 15);

	}

	public void selectTile() {
		if (Runner._input.isMouseClicked(this))
			// select a different tile
			if (InputManager.mouse_y > 548) {
				for (int tile = viewTile; tile < viewTile + 10; tile++) {
					if (Runner._input.isMouseClicked((tile - viewTile) * 50
							- 40 + TileXStart, 554, 32, 48)) {
						LevelEditScreen.selectedTile = tile;
					}
				}
			}
	}
}
