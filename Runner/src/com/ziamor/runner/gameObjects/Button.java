package com.ziamor.runner.gameObjects;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;

public class Button extends GameObject{

	public Button() {
		
	}

	public void update() {
		if (Runner._input.isMouseClicked(x,y,width,height)) {
			System.out.println("Mouse clicked on the button");
		}
	}
	
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.drawString("" + Runner._input.isMouseClicked(x,y,width,height),10,10);
	}
	
}
