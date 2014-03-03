package com.ziamor.runner;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.RGBImageFilter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class TextureCache {

	public TextureCache() {
		// TODO Auto-generated constructor stub
	}

	public static Map<String, Texture> _textures = new HashMap<String, Texture>() {
		{
			// use loadTextureTransparent to remove a white background
			// otherwise, use loadTexture
			put("star", loadTexture("star", true));
			put("starOutline", loadTexture("starOutline", false));
			put("coin", loadTexture("coin", true));
			put("basicWall", loadTexture("basicWall", false));
			put("wall", loadTexture("wall", true));
			put("wallGrass", loadTexture("wallGrass", true));
			put("breakableOverlay", loadTexture("breakableOverlay", false));
			put("player", loadTexture("player", true));
			put("spikes", loadTexture("spikes", false));
			put("backButton", loadTexture("backButton", false));
			put("largeButton", loadTexture("largeButton", false));
			put("smallButton", loadTexture("smallButton", false));
			put("levelButton", loadTexture("levelButton", false));
			put("levelButtonLocked", loadTexture("levelButtonLocked", false));
			put("achieveButton", loadTexture("achieveButton", false));
			put("worldButton", loadTexture("worldButton", false));
		}
	};

	public static Texture loadTexture(String textureName, boolean tileSet) {
		Texture tex = null;
		// load the file as a buffered image
		try {
			String fileName = "res\\textures\\" + textureName + ".png";
			tex = new Texture(ImageIO.read(new File(fileName)), tileSet);
		} catch (IOException e) {
		}
		return tex;
	}

	public static Texture loadTextureBW(String textureName,
			boolean tileSet) {
		// Loads a texture in black and white (greyscale)
		
		Texture tex = null;
		// load the file as a buffered image
		tex = loadTexture(textureName, tileSet);

		// defines the filter
		ImageFilter filter = new RGBImageFilter() {
			public final int filterRGB(int x, int y, int rgb) {
				if ((rgb & 0x00FFFFFF) == 0x00FFFFFF)
					return rgb & 0x00000000;
				else
					return rgb;
			}
		};

		// filters the buffered image to make all white pixels transparent
		ImageProducer ip = new FilteredImageSource(
				tex.getTexture().getSource(), filter);
		Image image = Toolkit.getDefaultToolkit().createImage(ip);

		// converts the image back to a buffered image
		BufferedImage dest = new BufferedImage(tex.getTexture().getWidth(), tex
				.getTexture().getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return tex;
	}
}
