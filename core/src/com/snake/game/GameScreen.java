package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.snake.game.snakemodel.SnakeBody;
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
			game.batch.draw(game.snakeBody, game.snakeBodies.get(i).getX(), game.snakeBodies.get(i).getY());
		}

		game.batch.end();
		clock += Gdx.graphics.getRawDeltaTime();
		if(clock>0.15) {
			move();
			if(!DirectionToMove.equals(direction.STATIONARY)) {
				updateBody();
			}
			clock = 0;
		}
		moveFood();
		inputToMovement();
		collisionDetection();
	}

	@Override
	public void dispose() {
		game.batch.dispose();
	}

	public void addBody(int numberToAdd) {
		for (int i = 0; i < numberToAdd; i++) {
			if (game.snakeBodies.isEmpty()) {
				if(DirectionToMove.equals(direction.STATIONARY)){
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.STATIONARY, game.snakeHitbox.x + 40, game.snakeHitbox.y);
					game.snakeBodies.add(newSnakeBody);
				}
				else if (DirectionToMove.equals(direction.MOVE_LEFT)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_LEFT, game.snakeHitbox.x + 40, game.snakeHitbox.y);
					game.snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_RIGHT)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_RIGHT, game.snakeHitbox.x - 40, game.snakeHitbox.y);
					game.snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_DOWN)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_DOWN, game.snakeHitbox.x, game.snakeHitbox.y + 40);
					game.snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_UP)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_UP, game.snakeHitbox.x, game.snakeHitbox.y - 40);
					game.snakeBodies.add(newSnakeBody);
				}
			}
			if (!game.snakeBodies.isEmpty()) {
				if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.STATIONARY)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.STATIONARY, game.snakeBodies.get(snakeLength).getX() + 40, game.snakeBodies.get(snakeLength).getY());
					game.snakeBodies.add(newSnakeBody);
				}
				else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_LEFT)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_LEFT, game.snakeBodies.get(snakeLength).getX() + 40, game.snakeBodies.get(snakeLength).getY());
					game.snakeBodies.add(newSnakeBody);
				} else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_RIGHT)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_RIGHT, game.snakeBodies.get(snakeLength).getX() - 40, game.snakeBodies.get(snakeLength).getY());
					game.snakeBodies.add(newSnakeBody);
				} else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_DOWN)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_DOWN, game.snakeBodies.get(snakeLength).getX(), game.snakeBodies.get(snakeLength).getY() + 40);
					game.snakeBodies.add(newSnakeBody);
				} else if (game.snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_UP)) {
					com.snake.game.snakemodel.SnakeBody newSnakeBody = new com.snake.game.snakemodel.SnakeBody(direction.MOVE_UP, game.snakeBodies.get(snakeLength).getX(), game.snakeBodies.get(snakeLength).getY() - 40);
					game.snakeBodies.add(newSnakeBody);
				}
			}
			snakeLength++;
		}
	}

	public void collisionDetection(){
		for(SnakeBody s : game.snakeBodies){
			if(game.snakeHitbox.overlaps(s)){
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
		game.foodHitbox.setPosition(120,120);
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
			if(snakeLength == 288){
				game.setScreen(new WinScreen(game));
			}
			else{
				randomizeFoodPos();
			}
		}
	}

	public void randomizeFoodPos() {
		if (game.foodHitbox.overlaps(game.snakeHitbox)) {
			game.foodHitbox.setPosition(random(0, 16) * 40,random(0, 16) * 40);
		}
		for (SnakeBody s : game.snakeBodies) {
			if (game.foodHitbox.overlaps(s)) {
				game.foodHitbox.setPosition(random(0, 16) * 40,random(0, 16) * 40);
			}
		}
		if (game.foodHitbox.overlaps(game.snakeHitbox)){
			randomizeFoodPos();
		}
		for (SnakeBody s : game.snakeBodies) {
			if (game.foodHitbox.overlaps(s)) {
				randomizeFoodPos();
			}
		}
	}


	public void updateBody(){
			for(int i = snakeLength; i>=0; i--){
				if (i == 0 && !game.snakeBodies.isEmpty()) {
					game.snakeBodies.get(0).setPosition(oldSnakeHeadX, oldSnakeHeadY);
					game.snakeBodies.get(0).setDirection(DirectionToMove);
				} else if (i > 0) {
					game.snakeBodies.get(i).setPosition(game.snakeBodies.get(i - 1).getX(), game.snakeBodies.get(i - 1).getY());
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
