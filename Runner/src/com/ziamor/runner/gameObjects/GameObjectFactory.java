package com.ziamor.runner.gameObjects;

import com.ziamor.runner.GameObject;
import com.ziamor.runner.GameScreen;
import com.ziamor.runner.screens.GamePlayScreen;

public enum GameObjectFactory {

	BLANK(-1) {
		@Override
		public GameObject create(int x, int y) {
			return null;
		}
	},

	PLAYER(0) {
		@Override
		public GameObject create(int x, int y) {
			return new Player(x, y);
		}
	},
	WALL(1) {
		@Override
		public GameObject create(int x, int y) {
			return new Wall(x, y);
		}
	},

	SPORTAL(2) {
		@Override
		public GameObject create(int x, int y) {
			return new Portal(x, false);
		}
	},

	EPORTAL(3) {
		@Override
		public GameObject create(int x, int y) {
			GamePlayScreen.endPortalX = x - 32;
			return new Portal(x, true);
		}
	},
	BREAKABLE_WALL(4) {
		@Override
		public GameObject create(int x, int y) {
			return new BreakableWall(x, y);
		}
	},
	COIN(5) {
		@Override
		public GameObject create(int x, int y) {
			return new Coin(x, y);
		}
	},
	STAR(6) {
		@Override
		public GameObject create(int x, int y) {
			return new Star(x, y);
		}
	},
	SPIKES(7) {
		@Override
		public GameObject create(int x, int y) {
			return new Spikes(x, y);
		}
	},
	CANNON(8) {
		@Override
		public GameObject create(int x, int y) {
			return new Cannon(x, y);
		}
	};

	private final int id;
	private static final int size = GameObjectFactory.values().length;

	private GameObjectFactory(int id) {
		this.id = id;
	}

	public abstract GameObject create(int x, int y);

	public static GameObjectFactory getById(int id) {
		for (GameObjectFactory factory : values()) {
			if (factory.id == id) {
				return factory;
			}
		}
		throw new IllegalArgumentException("Invalid ID: " + id);
	}

	public int getId() {
		return id;
	}

	public static int getSize() {
		return size;
	}
}