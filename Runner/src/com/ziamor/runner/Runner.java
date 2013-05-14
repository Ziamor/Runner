package com.ziamor.runner;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;

public class Runner extends JPanel implements KeyListener {
	private boolean isRunning = true;
	public static int width, height;
	public GamePlayScreen gamePlayScreen = new GamePlayScreen();

	public Runner() {
		this.setFocusable(true);
		this.addKeyListener(this);
		// Set the game window to equal that of a 10 by 20 grid
		this.setPreferredSize(new Dimension(1000, 600));
		// Get the dimensions of the screen
		this.width = this.getSize().width;
		this.height = this.getSize().height;

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

	public void update(Graphics g) {
		this.gamePlayScreen.update(g);
	}

	public void paintComponent(Graphics g) {
		this.update(g);
		this.gamePlayScreen.paintComponent(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			gamePlayScreen.player.pressRight = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			gamePlayScreen.player.pressLeft = true;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			gamePlayScreen.player.pressJump = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			gamePlayScreen.player.pressRight = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			gamePlayScreen.player.pressLeft = false;
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			gamePlayScreen.player.pressJump = false;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {

	}
}
