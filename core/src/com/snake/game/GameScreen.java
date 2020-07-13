package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.snake.game.model.FoodObj;
import com.snake.game.model.SnakeBody;
import com.snake.game.model.SnakeHead;

import static com.badlogic.gdx.math.MathUtils.random;


public class GameScreen extends ScreenAdapter {
	SnakeGame game;
	SnakeHead snakeHead;
	FoodObj food;
	Array<SnakeBody> snakeBodies;


	public GameScreen(SnakeGame game){
		this.game = game;
	}

	@Override
	public void show() {
		snakeBodies = new <SnakeBody>Array(288);
		snakeHead = new SnakeHead(direction.MOVE_LEFT, 360,360);
		food = new FoodObj(120, 120);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		game.camera.update();
		game.batch.setProjectionMatrix(game.camera.combined);
		game.batch.begin();
		game.batch.draw(game.background, 0, 0);
		game.batch.draw(game.snakeHeadTex, snakeHead.getX(), snakeHead.getY());
		game.batch.draw(game.snakeFoodTex, food.getX(), food.getY());
		if(snakeLength == 0){
			addBody(2);
		}
		for (int i = 0; i < snakeLength; i++) {
			game.batch.draw(game.snakeBodyTex, snakeBodies.get(i).getX(), snakeBodies.get(i).getY());
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
			if (snakeBodies.isEmpty()) {
				if(DirectionToMove.equals(direction.STATIONARY)){
					SnakeBody newSnakeBody = new SnakeBody(direction.STATIONARY, snakeHead.x + 40, snakeHead.y);
					snakeBodies.add(newSnakeBody);
				}
				else if (DirectionToMove.equals(direction.MOVE_LEFT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, snakeHead.x + 40, snakeHead.y);
					snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_RIGHT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, snakeHead.x - 40, snakeHead.y);
					snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_DOWN)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, snakeHead.x, snakeHead.y + 40);
					snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_UP)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, snakeHead.x, snakeHead.y - 40);
					snakeBodies.add(newSnakeBody);
				}
			}
			if (!snakeBodies.isEmpty()) {
				if (snakeBodies.get(snakeLength).getDirection().equals(direction.STATIONARY)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.STATIONARY, snakeBodies.get(snakeLength).getX() + 40, snakeBodies.get(snakeLength).getY());
					snakeBodies.add(newSnakeBody);
				}
				else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_LEFT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, snakeBodies.get(snakeLength).getX() + 40, snakeBodies.get(snakeLength).getY());
					snakeBodies.add(newSnakeBody);
				} else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_RIGHT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, snakeBodies.get(snakeLength).getX() - 40, snakeBodies.get(snakeLength).getY());
					snakeBodies.add(newSnakeBody);
				} else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_DOWN)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, snakeBodies.get(snakeLength).getX(), snakeBodies.get(snakeLength).getY() + 40);
					snakeBodies.add(newSnakeBody);
				} else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_UP)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, snakeBodies.get(snakeLength).getX(), snakeBodies.get(snakeLength).getY() - 40);
					snakeBodies.add(newSnakeBody);
				}
			}
			snakeLength++;
		}
	}

	public void collisionDetection(){
		for(SnakeBody s : snakeBodies){
			if(snakeHead.overlaps(s)){
				gameReset();
				game.setScreen(new GameOver(game));
			}
		}
		if(snakeHead.getX()<0 || snakeHead.getX()>=680 || snakeHead.getY()<0 || snakeHead.getY()>=680){
			gameReset();
			game.setScreen(new GameOver(game));
		}
	}

	public void gameReset(){
		snakeHead.setPosition(360,360);
		food.setPosition(120,120);
		snakeBodies.clear();
		DirectionToMove = direction.STATIONARY;
		game.snakeHeadTex = game.snakeLeftTex;
		snakeLength = 0;
	}

	public void inputToMovement() {
		if (Gdx.input.isKeyPressed(Input.Keys.W) && !snakeBodies.get(0).getDirection().equals(direction.MOVE_DOWN)){
			DirectionToMove = direction.MOVE_UP;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S) && !snakeBodies.get(0).getDirection().equals(direction.MOVE_UP)) {
			DirectionToMove = direction.MOVE_DOWN;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A) && !snakeBodies.get(0).getDirection().equals(direction.MOVE_RIGHT)){
			DirectionToMove = direction.MOVE_LEFT;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D) && !snakeBodies.get(0).getDirection().equals(direction.MOVE_LEFT)){
			DirectionToMove = direction.MOVE_RIGHT;
		}
	}

	public void move() {
		snakeHead.setOldPos();
		switch (DirectionToMove) {
			case MOVE_UP:
				game.snakeHeadTex = game.snakeUpTex;
				snakeHead.y += 40;
				break;
			case MOVE_DOWN:
				game.snakeHeadTex = game.snakeDownTex;
				snakeHead.y -= 40;
				break;
			case MOVE_RIGHT:
				game.snakeHeadTex = game.snakeRightTex;
				snakeHead.x += 40;
				break;
			case MOVE_LEFT:
				game.snakeHeadTex = game.snakeLeftTex;
				snakeHead.x -= 40;
				break;
		}
	}

	public void moveFood(){
		if(food.overlaps(snakeHead))
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
		if (food.overlaps(snakeHead)) {
			food.setPosition(random(0, 16) * 40,random(0, 16) * 40);
		}
		for (SnakeBody s : snakeBodies) {
			if (food.overlaps(s)) {
				food.setPosition(random(0, 16) * 40,random(0, 16) * 40);
			}
		}
		if (food.overlaps(snakeHead)){
			randomizeFoodPos();
		}
		for (SnakeBody s : snakeBodies) {
			if (food.overlaps(s)) {
				randomizeFoodPos();
			}
		}
	}


	public void updateBody(){
			for(int i = snakeLength; i>=0; i--){
				if (i == 0 && !snakeBodies.isEmpty()) {
					snakeBodies.get(0).setPosition(snakeHead.getPrevX(), snakeHead.getPrevY());
					snakeBodies.get(0).setDirection(DirectionToMove);
				} else if (i > 0) {
					snakeBodies.get(i).setPosition(snakeBodies.get(i - 1).getX(), snakeBodies.get(i - 1).getY());
					snakeBodies.get(i).setDirection(snakeBodies.get(i - 1).getDirection());
				}
			}
		}

		private float clock;
		private static direction DirectionToMove = direction.STATIONARY;
		private static int snakeLength = 0;
	}
