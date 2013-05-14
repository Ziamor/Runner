package com.ziamor.runner;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

import com.ziamor.runner.screens.MainMenuScreen;

public class Runner extends JPanel implements KeyListener {
	private boolean isRunning = true;
	public static int width, height;
	public static GameScreenManager gameScreenManager;
	
	public Runner() {
		this.setFocusable(true);
		this.addKeyListener(this);
		// Set the game window to equal that of a 10 by 20 grid
		this.setPreferredSize(new Dimension(1000, 600));
		// Get the dimensions of the screen
		this.width = this.getSize().width;
		this.height = this.getSize().height;
		gameScreenManager = new GameScreenManager();
		gameScreenManager.addScreen(new MainMenuScreen());
		
		// Start the game loop
		Thread t = new Thread() {
			public void run() {
				gameLoop();
			}
		};
		t.start();
	}

	public void gameLoop() {
		while (isRunning) {
			this.repaint();
			try {
				Thread.sleep(1000 / 30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void update() {
		GameScreenManager.update();
	}

	public void paintComponent(Graphics g) {
		this.update();
		GameScreenManager.paintComponent(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			GamePlayScreen.player.pressRight = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			GamePlayScreen.player.pressLeft = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			GamePlayScreen.player.pressJump = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			GamePlayScreen.player.pressRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			GamePlayScreen.player.pressLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			GamePlayScreen.player.pressJump = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
