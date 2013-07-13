package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.screens.GamePlayScreen;

public class Spikes extends GameObject{
	
	public Spikes(int x, int y) {
		this.x = x;
		this.y = y;
		this.objID = "hazard";
		width = 32;
		height = 16;
	}

	public void update() {
		
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (!isVisible || offScreen)
			return;
		
		g.setColor(Color.black);
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				// draw some spikes
				g.fillRect(x - GamePlayScreen.viewX + 8*i+4-j, y - GamePlayScreen.viewY +4*j,
						(j+1)*2, height-j*4);
			}
		}
		
		

	}

}

