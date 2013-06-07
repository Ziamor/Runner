package com.ziamor.runner;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

public class InputManager implements KeyListener, MouseListener {

	private boolean[] key_pressed = new boolean[256];
	private boolean[] key_released = new boolean[256];
	private boolean[] key_hit = new boolean[256];

	public static Map<String, Integer> _keys = new HashMap<String, Integer>() {
		{
			// Use site:
			// http://www.cambiaresearch.com/articles/15/javascript-char-codes-key-codes
			// for more key codes
			put("w", 87);
			put("a", 65);
			put("d", 68);
			put("s", 83);
			put("enter", 13);

			put("left", 37);
			put("up", 38);
			put("right", 39);
			put("down", 40);
			put("space", 32);
			put("escape", 27);
		}
	};

	public boolean isKeyPressed(int keyCode) {
		// activates constantly while key is pressed
		return key_pressed[keyCode];
	}

	public boolean isKeyHit(int keyCode) {
		// activates only once per key press
		return key_hit[keyCode];
	}

	public boolean isKeyReleased(int keyCode) {
		return key_released[keyCode];
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode < 256 && keyCode > 0) {
			key_hit[keyCode] = !key_pressed[keyCode];
			key_pressed[keyCode] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode < 256 && keyCode > 0) {
			key_pressed[keyCode] = false;
			key_hit[keyCode] = false;
			key_released[keyCode] = true;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("Mouse clicked");
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	public void update() {
		for (int i = 0; i < 256; i++) {
			key_hit[i] = false;
		}
	}

}
