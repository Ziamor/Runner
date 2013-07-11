package com.ziamor.runner.screens;

import java.awt.Color;
import java.awt.Graphics;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.GameScreenManager;
import com.ziamor.runner.InputManager;
import com.ziamor.runner.Runner;
import com.ziamor.runner.menuObjects.BackButton;

public class AchieveScreen extends GameScreen {

	private int[] AchieveState;
	private int[] AchieveOrder;
	private int viewY;
	private boolean isScrolling;
	private int scrollerY;

	public AchieveScreen() {
		this.setBlockRender(true);
		this.setBlockUpdate(true);
		AchieveState = new int[30];
		AchieveState = new int[30];
		// 0 = hidden, 1 = viewable, 2 = completed
		scrollerY = 60;
	}

	public void update() {
		// call the gameScreen update();
		super.update();
		if (!getBlockUpdate())
			return;

		// if the user clicks the red button
		if (Runner._input.isMouseClicked(550, 20, 40, 40)) {
			// go back to the previous screen
			GameScreenManager.getGameScreens().get(0).setBlockUpdate(true);
			Runner._gameScreenManager.removeScreen(this);
		}

		if (Runner._input.isMouseClicked(550, scrollerY, 20, 50)) {
			isScrolling = true;
		}
		
		if (isScrolling){
			scrollerY = InputManager.mouse_y;
		}
		
		if (Runner._input.isMouseReleased()) {
			isScrolling = false;
		}	
	}

	public void paintComponent(Graphics g) {

		// fade the back screen
		Color c = new Color(0, 0, 0, 150);
		g.setColor(c);
		g.fillRect(0, 0, 1024, 608);

		// draw the border
		g.setColor(Color.blue);
		g.fillRect(140, 30, 440, 548);
		g.setColor(Color.black);
		g.fillRect(150, 40, 420, 528);
		g.setColor(Color.red);
		g.fillRect(550, 20, 40, 40);
		g.setColor(Color.black);
		g.fillRect(555, 25, 30, 30);
		
		// draw the scroller
		g.setColor(Color.darkGray);
		g.fillRect(550,60,20,508);
		g.setColor(Color.lightGray);
		g.fillRect(550,scrollerY,20,50);

		// draw the achievement tiles
		g.setColor(Color.blue);
		for (int i = 0; i < 30; i++) {
			g.fillRect(160, 50 + i * 90, 380, 80);
		}

		// call the gameScreen paintComponent(g);
		super.paintComponent(g);

	}
}
