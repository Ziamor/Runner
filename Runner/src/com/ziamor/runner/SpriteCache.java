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

public class SpriteCache {

	public SpriteCache() {
		// TODO Auto-generated constructor stub
	}

	public static Map<String, BufferedImage> _sprites = new HashMap<String, BufferedImage>() {
		{
			// use loadSpriteTransparent to remove a white background
			// otherwise, use loadSprite
			put("star", loadSpriteTransparent("star"));
			put("coin", loadSpriteTransparent("coin"));
			put("basicWall", loadSpriteTransparent("basicWall"));
			put("breakableWall", loadSpriteTransparent("breakableWall"));
		}
	};

	public static BufferedImage loadSprite(String spriteName) {
		// load the file as a buffered image
		BufferedImage img = null;
		try {
			String fileName = "res\\sprites\\" + spriteName + ".png";
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
		}
		return img;
	}
	
	public static BufferedImage loadSpriteTransparent(String spriteName) {
		// load the file as a buffered image
		BufferedImage img = null;
		try {
			String fileName = "res\\sprites\\" + spriteName + ".png";
			img = ImageIO.read(new File(fileName));
		} catch (IOException e) {
		}

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
		ImageProducer ip = new FilteredImageSource(img.getSource(), filter);
		Image image = Toolkit.getDefaultToolkit().createImage(ip);

		// converts the image back to a buffered image
		BufferedImage dest = new BufferedImage(img.getWidth(), img.getHeight(),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = dest.createGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
		return dest;
	}
	
}
