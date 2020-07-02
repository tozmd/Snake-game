package com.snake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class SnakeGame extends Game {
    Texture background, snakeLeft, snakeRight, snakeUp, snakeDown, snakeFood, snakeHead, snakeBody, snakeTitle, creatorName, gameMsg, gameMessage, gameOverMsg, gameOverMessage, gameOverTitle, transparentBG;
    SnakeFood food;
    OrthographicCamera camera;
    SpriteBatch batch;
    Rectangle snakeHitbox;
    Rectangle foodHitbox;
    BitmapFont font;
    Array<SnakeBody> snakeBodies;

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
        creatorName = new Texture(Gdx.files.internal("toastmakerr.png"));
        snakeTitle = new Texture(Gdx.files.internal("snaketitle.png"));
        gameMsg = new Texture(Gdx.files.internal("titlescreenmsg.png"));
        gameMessage = new Texture(Gdx.files.internal("titlescreenmsg.png"));
        transparentBG = new Texture(Gdx.files.internal("transparentbg.png"));
        gameOverMsg = new Texture(Gdx.files.internal("gameovermsg.png"));
        gameOverMessage = new Texture(Gdx.files.internal("gameovermsg.png"));
        gameOverTitle = new Texture(Gdx.files.internal("gameover.png"));
        snakeBodies = new <SnakeBody>Array(288);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 680, 680);
        snakeHitbox = new Rectangle();
        snakeHitbox.set(360,360,40,40);
        foodHitbox = new Rectangle();
        foodHitbox.set(120,120,40,40);
        setScreen(new TitleScreen(this));
}

    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
}
