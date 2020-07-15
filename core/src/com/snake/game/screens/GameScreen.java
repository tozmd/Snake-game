package com.snake.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.snake.game.SnakeGame;
import com.snake.game.assetss.Assets;
import com.snake.game.model.FoodObj;
import com.snake.game.model.SnakeBody;
import com.snake.game.model.SnakeHead;
import com.snake.game.movement.FoodController;
import com.snake.game.movement.SnakeController;
import com.snake.game.movement.Direction;

public class GameScreen extends ScreenAdapter {
	SnakeGame game;
	SnakeHead snakeHead;
	FoodObj food;
	Array<SnakeBody> snakeBodies;
	SnakeController controller;
	FoodController foodRandomizer;
	Assets assets;

	public GameScreen(SnakeGame game){
		this.game = game;
	}

	@Override
	public void show() {
		assets = new Assets();
		assets.loadAssets();
		snakeBodies = new <SnakeBody>Array(288);
		snakeHead = new SnakeHead(Direction.MOVE_LEFT, 360,360);
		food = new FoodObj(120, 120);
		foodRandomizer = new FoodController();
		controller = new SnakeController();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		assets.camera.update();
		assets.batch.setProjectionMatrix(assets.camera.combined);
		assets.batch.begin();
		assets.batch.draw(assets.background, 0, 0);
		assets.batch.draw(assets.snakeHeadTex, snakeHead.getX(), snakeHead.getY());
		assets.batch.draw(assets.snakeFoodTex, food.getX(), food.getY());
		if(SnakeBody.getSnakeLength() == 0){
			controller.addBody(2, snakeBodies, snakeHead);
		}
		for (int i = 0; i < SnakeBody.getSnakeLength(); i++) {
			assets.batch.draw(assets.snakeBodyTex, snakeBodies.get(i).getX(), snakeBodies.get(i).getY());
		}

		assets.batch.end();
		clock += Gdx.graphics.getRawDeltaTime();
		if(clock>0.15) {
			controller.move(assets, snakeHead);
			if(!controller.getDir().equals(Direction.STATIONARY)) {
				controller.updateBody(snakeBodies, snakeHead);
			}
			clock = 0;
		}
		if(food.overlaps(snakeHead)) {
			controller.addBody(1, snakeBodies, snakeHead);
			foodRandomizer.moveFood(game, food, snakeHead, snakeBodies);
		}
		controller.inputToMovement(snakeBodies);
		controller.collisionDetection(game, snakeHead, food, snakeBodies, assets);
	}

	@Override
	public void dispose() {
		assets.batch.dispose();
	}

		private float clock;
	}
