package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;
import com.snake.game.model.FoodObj;
import com.snake.game.model.SnakeBody;
import com.snake.game.model.SnakeHead;
import com.snake.game.movement.SnakeController;
import com.snake.game.movement.direction;
import static com.badlogic.gdx.math.MathUtils.random;


public class GameScreen extends ScreenAdapter {
	SnakeGame game;
	SnakeHead snakeHead;
	FoodObj food;
	Array<SnakeBody> snakeBodies;
	SnakeController controller;


	public GameScreen(SnakeGame game){
		this.game = game;
	}

	@Override
	public void show() {
		snakeBodies = new <SnakeBody>Array(288);
		snakeHead = new SnakeHead(direction.MOVE_LEFT, 360,360);
		food = new FoodObj(120, 120);
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
			if(!controller.getDir().equals(com.snake.game.movement.direction.STATIONARY)) {
				controller.updateBody(snakeBodies, snakeHead);
			}
			clock = 0;
		}
		moveFood();
		controller.inputToMovement(snakeBodies);
		collisionDetection();
	}

	@Override
	public void dispose() {
		game.batch.dispose();
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
		controller.setDir(direction.STATIONARY);
		game.assets.snakeHeadTex = game.assets.snakeLeftTex;
		SnakeBody.setSnakeLength(0);
	}


	public void moveFood(){
		if(food.overlaps(snakeHead))
		{
			controller.addBody(1, snakeBodies, snakeHead);
			if(SnakeBody.getSnakeLength() == 288){
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

		private float clock;
	}
