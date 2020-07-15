package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
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

	public GameScreen(SnakeGame game){
		this.game = game;
	}

	@Override
	public void show() {
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

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.batch.begin();
		game.batch.draw(game.assets.background, 0, 0);
		game.batch.draw(game.assets.snakeHeadTex, snakeHead.getX(), snakeHead.getY());
		game.batch.draw(game.assets.snakeFoodTex, food.getX(), food.getY());
		if(SnakeBody.getSnakeLength() == 0){
			controller.addBody(2, snakeBodies, snakeHead);
		}
		for (int i = 0; i < SnakeBody.getSnakeLength(); i++) {
			game.batch.draw(game.assets.snakeBodyTex, snakeBodies.get(i).getX(), snakeBodies.get(i).getY());
		}

		game.batch.end();
		clock += Gdx.graphics.getRawDeltaTime();
		if(clock>0.15) {
			controller.move(game.assets, snakeHead);
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
		controller.collisionDetection(game, snakeHead, food, snakeBodies, game.assets);
	}

	@Override
	public void dispose() {
		game.batch.dispose();
	}

		private float clock;
	}
