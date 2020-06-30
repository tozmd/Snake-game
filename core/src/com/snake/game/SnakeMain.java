package com.snake.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import static com.badlogic.gdx.math.MathUtils.random;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;

public class SnakeMain extends ApplicationAdapter {
	Texture background, snakeLeft, snakeRight, snakeUp, snakeDown, snakeFood, snakeHead, snakeBody;
	SnakeFood food;
	OrthographicCamera camera;
	SpriteBatch batch;
	Rectangle snakeHitbox;
	Rectangle foodHitbox;
	BitmapFont font;
	float clock = 0;
	Array<SnakeBody> snakeBodies;

	@Override
	public void create() {
		batch = new SpriteBatch();

		food = new SnakeFood(240,240);

		background = new Texture(Gdx.files.internal("snakegameboard.jpg"));
		snakeFood = new Texture(Gdx.files.internal("snakefood.png"));
		snakeLeft = new Texture(Gdx.files.internal("snakeleft.png"));
		snakeUp = new Texture(Gdx.files.internal("snakeUp.png"));
		snakeDown = new Texture(Gdx.files.internal("snakeDown.png"));
		snakeRight = new Texture(Gdx.files.internal("snakeRight.png"));
		snakeHead = new Texture(Gdx.files.internal("snakeleft.png"));
		snakeBody = new Texture(Gdx.files.internal("snakebody.jpg"));

		snakeBodies = new <SnakeBody>Array(288);

		font = new BitmapFont();
		font.setColor(Color.BLACK);


		camera = new OrthographicCamera();
		camera.setToOrtho(false, 680, 680);

		snakeHitbox = new Rectangle();
		snakeHitbox.x = 360;
		snakeHitbox.y = 360;
		snakeHitbox.width = 40;
		snakeHitbox.height = 40;

		foodHitbox = new Rectangle();
		foodHitbox.x = 240;
		foodHitbox.y = 240;
		foodHitbox.width = 40;
		foodHitbox.height = 40;
	}


	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(snakeHead, snakeHitbox.x, snakeHitbox.y);
		batch.draw(snakeFood, foodHitbox.x, foodHitbox.y);
		if(!snakeBodies.isEmpty()) {
			for (int i = 0; i < snakeLength; i++) {
				batch.draw(snakeBody, snakeBodies.get(i).snakeBodyX, snakeBodies.get(i).snakeBodyY);
			}
		}

		moveFood();
		inputToMovement();
		collideWithWall();

		font.draw(batch, "Pies Eaten: " + counter, 10, 670);
		batch.end();
		clock += Gdx.graphics.getRawDeltaTime();
		if (clock > 0.35) {
			move();
			updateBody();
			clock = 0;
		}
	}

	@Override
	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public void inputToMovement() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			DirectionToMove = direction.MOVE_UP;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			DirectionToMove = direction.MOVE_LEFT;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			DirectionToMove = direction.MOVE_DOWN;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.D)) {
			DirectionToMove = direction.MOVE_RIGHT;
		}
	}

	public void collideWithWall(){
		if(snakeHitbox.x<0 || snakeHitbox.x>680 || snakeHitbox.y<0 || snakeHitbox.y>680){
			DirectionToMove = direction.STATIONARY;
		}
	}

	public void move() {
		oldSnakeHeadY = snakeHitbox.y;
		oldSnakeHeadX = snakeHitbox.x;
		switch (DirectionToMove) {
			case MOVE_UP:
				snakeHead = snakeUp;
				snakeHitbox.y += 40;
				break;
			case MOVE_DOWN:
				snakeHead = snakeDown;
				snakeHitbox.y -= 40;
				break;
			case MOVE_RIGHT:
				snakeHead = snakeRight;
				snakeHitbox.x += 40;
				break;
			case MOVE_LEFT:
				snakeHead = snakeLeft;
				snakeHitbox.x -= 40;
				break;
		}
	}

	public void moveFood(){
		if(foodHitbox.overlaps(snakeHitbox))
		{
			foodHitbox.setPosition(random(0,16)*40,random(0,16)*40);
			counter++;
			addBody();
		}
	}

	public void addBody() {
		if (counter > snakeLength) {
			if (snakeBodies.isEmpty()) {
				if (DirectionToMove.equals(direction.MOVE_LEFT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, snakeHitbox.x + 40, snakeHitbox.y);
					snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_RIGHT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, snakeHitbox.x - 40, snakeHitbox.y);
					snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_DOWN)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, snakeHitbox.x, snakeHitbox.y + 40);
					snakeBodies.add(newSnakeBody);
				} else if (DirectionToMove.equals(direction.MOVE_UP)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, snakeHitbox.x, snakeHitbox.y - 40);
					snakeBodies.add(newSnakeBody);
				}
			}
			if (!snakeBodies.isEmpty() && counter > snakeLength) {
				if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_LEFT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, snakeBodies.get(snakeLength).snakeBodyX + 40, snakeBodies.get(snakeLength).snakeBodyY);
					snakeBodies.add(newSnakeBody);
				} else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_RIGHT)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, snakeBodies.get(snakeLength).snakeBodyX - 40, snakeBodies.get(snakeLength).snakeBodyY);
					snakeBodies.add(newSnakeBody);
				} else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_DOWN)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, snakeBodies.get(snakeLength).snakeBodyX, snakeBodies.get(snakeLength).snakeBodyY + 40);
					snakeBodies.add(newSnakeBody);
				} else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_UP)) {
					SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, snakeBodies.get(snakeLength).snakeBodyX, snakeBodies.get(snakeLength).snakeBodyY - 40);
					snakeBodies.add(newSnakeBody);
				}
			}
			snakeLength++;
		}
	}

	public void updateBody(){
			for(int i = snakeLength;i>=0;i--){
				if(i==0 && !snakeBodies.isEmpty()){
					snakeBodies.get(0).setNewPos(oldSnakeHeadX,oldSnakeHeadY);
					snakeBodies.get(0).setDirection(DirectionToMove);
				}
				else if(i>0){
					snakeBodies.get(i).setNewPos(snakeBodies.get(i-1).snakeBodyX,snakeBodies.get(i-1).snakeBodyY);
					snakeBodies.get(i).setDirection(snakeBodies.get(i-1).getDirection());
				}
			}
		}

		private static int counter = 0;
		private static int snakeLength = 0;
		private float oldSnakeHeadX;
		private float oldSnakeHeadY;
		private static direction DirectionToMove=direction.STATIONARY;
	}
