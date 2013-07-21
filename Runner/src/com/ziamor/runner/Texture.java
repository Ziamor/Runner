package com.ziamor.runner;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class Texture {

	private BufferedImage tex;

	private boolean isTileSet = false;

	public Texture(BufferedImage tex, boolean isTileSet) {
		this.tex = tex;
		this.isTileSet = isTileSet;
	}

	public BufferedImage getTexture() {
		return tex;
	}

	public BufferedImage getTexture(int index, int tileWidth, int tileHeight) {
		if (isTileSet) {
			// try {
			BufferedImage img = null;
			int x = 0;
			int y = 0;
			int xTiles = tex.getWidth() / tileWidth;
			int yTiles = tex.getHeight() / tileHeight;

			y = index / xTiles;
			x = index % xTiles;
			// System.out.println("X:" + x + "," + "Y:" + y);
			img = this.tex.getSubimage(x * tileWidth, y * tileHeight,
					tileWidth, tileHeight);
			return img;
			// } catch (Exception e) {
			// return null;
			// }
		} else
			return null;
	}

	public void setTexture(BufferedImage value) {
		this.tex = value;
	}
}
