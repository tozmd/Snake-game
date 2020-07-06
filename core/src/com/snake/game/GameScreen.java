package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.RandomXS128;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.math.MathUtils.random;


public class GameScreen extends ScreenAdapter {
	SnakeGame game;

	public GameScreen(SnakeGame game){
		this.game = game;
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.batch.begin();
		game.batch.draw(game.background, 0, 0);
		game.batch.draw(game.snakeHead, game.snakeHitbox.x, game.snakeHitbox.y);
		game.batch.draw(game.snakeFood, game.foodHitbox.x, game.foodHitbox.y);
		if(snakeLength == 0){
			addBody(2);
		}
		for (int i = 0; i < snakeLength; i++) {
			game.batch.draw(game.snakeBody, game.snakeBodies.get(i).snakeBodyX, game.snakeBodies.get(i).snakeBodyY);
		}
		moveFood();
		inputToMovement();
		collisionDetection();

		game.batch.end();
		clock += Gdx.graphics.getRawDeltaTime();
		if(clock>0.18) {
			move();
			if(!DirectionToMove.equals(direction.STATIONARY)) {
				updateBody();
			}
			clock = 0;
		}
	}

	@Override
	public void dispose() {
		game.batch.dispose();
	}

	public void addBody(int numberToAdd) {
		for (int i = 0; i < numberToAdd; i++) {
			if (game.snakeBodies.isEmpty()) {
				if(DirectionToMove.equals(direction.STATIONARY)){
					SnakeBody newSnakeBody = new SnakeBody(direction.STATIONARY, game.snakeHitbox.x + 40, game.snakeHitbox.y);
					game.snakeBodies.add(newSnakeBody);
				}
				else if (DirectionToMove.equals(direction.MOVE_LEFT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, game.snakeHitbox.x + 40, game.snakeHitbox.y);
					game.snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_RIGHT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, game.snakeHitbox.x - 40, game.snakeHitbox.y);
					game.snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_DOWN)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, game.snakeHitbox.x, game.snakeHitbox.y + 40);
					game.snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_UP)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, game.snakeHitbox.x, game.snakeHitbox.y - 40);
					game.snakeBodies.add(newSnakeBody);
				}
			}
			if (!game.snakeBodies.isEmpty()) {
				if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.STATIONARY)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.STATIONARY, game.snakeBodies.get(snakeLength).snakeBodyX + 40, game.snakeBodies.get(snakeLength).snakeBodyY);
					game.snakeBodies.add(newSnakeBody);
				}
				else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_LEFT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, game.snakeBodies.get(snakeLength).snakeBodyX + 40, game.snakeBodies.get(snakeLength).snakeBodyY);
					game.snakeBodies.add(newSnakeBody);
				} else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_RIGHT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, game.snakeBodies.get(snakeLength).snakeBodyX - 40, game.snakeBodies.get(snakeLength).snakeBodyY);
					game.snakeBodies.add(newSnakeBody);
				} else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_DOWN)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, game.snakeBodies.get(snakeLength).snakeBodyX, game.snakeBodies.get(snakeLength).snakeBodyY + 40);
					game.snakeBodies.add(newSnakeBody);
				} else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_UP)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, game.snakeBodies.get(snakeLength).snakeBodyX, game.snakeBodies.get(snakeLength).snakeBodyY - 40);
					game.snakeBodies.add(newSnakeBody);
				}
			}
			snakeLength++;
		}
	}

	public void collisionDetection(){
		for(SnakeBody snakebody:game.snakeBodies){
			if(game.snakeHitbox.overlaps(snakebody.newSnakeBody)){
				gameReset();
				game.setScreen(new GameOver(game));
			}
		}
		if(game.snakeHitbox.x<0 || game.snakeHitbox.x>=680 || game.snakeHitbox.y<0 || game.snakeHitbox.y>=680){
			gameReset();
			game.setScreen(new GameOver(game));
		}
	}

	public void gameReset(){
		game.snakeHitbox.setPosition(360,360);
		game.foodHitbox.setPosition(40,40);
		game.snakeBodies.clear();
		DirectionToMove = direction.STATIONARY;
		game.snakeHead = game.snakeLeft;
		snakeLength = 0;
	}

	public void inputToMovement() {
		if (Gdx.input.isKeyPressed(Input.Keys.W) && !game.snakeBodies.get(0).getDirection().equals(direction.MOVE_DOWN)){
			DirectionToMove = direction.MOVE_UP;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S) && !game.snakeBodies.get(0).getDirection().equals(direction.MOVE_UP)) {
			DirectionToMove = direction.MOVE_DOWN;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A) && !game.snakeBodies.get(0).getDirection().equals(direction.MOVE_RIGHT)){
			DirectionToMove = direction.MOVE_LEFT;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D) && !game.snakeBodies.get(0).getDirection().equals(direction.MOVE_LEFT)){
			DirectionToMove = direction.MOVE_RIGHT;
		}
	}

	public void move() {
		oldSnakeHeadY = game.snakeHitbox.y;
		oldSnakeHeadX = game.snakeHitbox.x;
		switch (DirectionToMove) {
			case MOVE_UP:
				game.snakeHead = game.snakeUp;
				game.snakeHitbox.y += 40;
				break;
			case MOVE_DOWN:
				game.snakeHead = game.snakeDown;
				game.snakeHitbox.y -= 40;
				break;
			case MOVE_RIGHT:
				game.snakeHead = game.snakeRight;
				game.snakeHitbox.x += 40;
				break;
			case MOVE_LEFT:
				game.snakeHead = game.snakeLeft;
				game.snakeHitbox.x -= 40;
				break;
		}
	}

	public void moveFood(){
		if(game.foodHitbox.overlaps(game.snakeHitbox))
		{
			addBody(1);
			float randomX = random(0,16)*40;
			float randomY = random(0,16)*40;
			randomizeFoodPos(randomX, randomY);
			game.foodHitbox.setPosition(randomX,randomY);
		}
	}

	public void randomizeFoodPos(float x, float y) {
		if (x == game.snakeHitbox.x && y == game.snakeHitbox.y) {
			x = random(0, 16) * 40;
			y = random(0, 16) * 40;
		}
		for (SnakeBody s : game.snakeBodies) {
			if (x == s.snakeBodyX && y == s.snakeBodyY) {
				x = random(0, 16) * 40;
				y = random(0, 16) * 40;
			}
		}
		if (x == game.snakeHitbox.x && y == game.snakeHitbox.y) {
			randomizeFoodPos(x, y);
		}
		for (SnakeBody s : game.snakeBodies) {
			if (x == s.snakeBodyX && y == s.snakeBodyY) {
				randomizeFoodPos(x,y);
			}
		}
	}


	public void updateBody(){
			for(int i = snakeLength; i>=0; i--){
				if (i == 0 && !game.snakeBodies.isEmpty()) {
					game.snakeBodies.get(0).setNewPos(oldSnakeHeadX, oldSnakeHeadY);
					game.snakeBodies.get(0).setDirection(DirectionToMove);
				} else if (i > 0) {
					game.snakeBodies.get(i).setNewPos(game.snakeBodies.get(i - 1).snakeBodyX, game.snakeBodies.get(i - 1).snakeBodyY);
					game.snakeBodies.get(i).setDirection(game.snakeBodies.get(i - 1).getDirection());
				}
			}
		}

		private float clock;
		private static direction DirectionToMove = direction.STATIONARY;
		private static int snakeLength = 0;
		private float oldSnakeHeadX;
		private float oldSnakeHeadY;
	}
