package com.snake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.snake.game.model.FoodObj;
import com.snake.game.model.SnakeBody;
import com.snake.game.model.SnakeHead;

public class SnakeGame extends Game {
    Texture background, snakeLeftTex, snakeRightTex, snakeUpTex, snakeDownTex, snakeFoodTex, snakeHeadTex, snakeBodyTex,
            snakeTitle, creatorName, gameMsg, gameMessage, gameOverMsg, gameOverMessage, gameOverTitle, transparentBG, winTitle;
    OrthographicCamera camera;
    SpriteBatch batch;

    public void create() {
        batch = new SpriteBatch();
        background = new Texture(Gdx.files.internal("snakegameboard.jpg"));
        snakeFoodTex = new Texture(Gdx.files.internal("snakefood.png"));
        snakeLeftTex = new Texture(Gdx.files.internal("snakeleft.png"));
        snakeUpTex = new Texture(Gdx.files.internal("snakeUp.png"));
        snakeDownTex = new Texture(Gdx.files.internal("snakeDown.png"));
        snakeRightTex = new Texture(Gdx.files.internal("snakeRight.png"));
        snakeHeadTex = new Texture(Gdx.files.internal("snakeleft.png"));
        snakeBodyTex = new Texture(Gdx.files.internal("snakebody.jpg"));
        creatorName = new Texture(Gdx.files.internal("toastmakerr.png"));
        snakeTitle = new Texture(Gdx.files.internal("snaketitle.png"));
        gameMsg = new Texture(Gdx.files.internal("titlescreenmsg.png"));
        gameMessage = new Texture(Gdx.files.internal("titlescreenmsg.png"));
        transparentBG = new Texture(Gdx.files.internal("transparentbg.png"));
        gameOverMsg = new Texture(Gdx.files.internal("gameovermsg.png"));
        gameOverMessage = new Texture(Gdx.files.internal("gameovermsg.png"));
        gameOverTitle = new Texture(Gdx.files.internal("gameover.png"));
        winTitle = new Texture(Gdx.files.internal("victoryscreen.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 680, 680);


        setScreen(new TitleScreen(this));
}

    @Override
    public void dispose(){
        batch.dispose();
    }
}
