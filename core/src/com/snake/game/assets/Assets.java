package com.snake.game.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.game.SnakeGame;


public class Assets {
    public Texture background, snakeLeftTex, snakeRightTex, snakeUpTex, snakeDownTex, snakeFoodTex, snakeHeadTex, snakeBodyTex,
            snakeTitle, creatorName, gameMsg, gameMessage, gameOverMsg, gameOverMessage, gameOverTitle, transparentBG, winTitle;
    public SnakeGame game;
    public OrthographicCamera camera;
    public SpriteBatch batch;

    public void loadAssets(){
        background = new Texture(Gdx.files.internal("snakegameboard.jpg"));
        snakeFoodTex = new Texture(Gdx.files.internal("snakefood.png"));
        snakeLeftTex = new Texture(Gdx.files.internal("snakeleft.png"));
        snakeUpTex = new Texture(Gdx.files.internal("snakeup.png"));
        snakeDownTex = new Texture(Gdx.files.internal("snakedown.png"));
        snakeRightTex = new Texture(Gdx.files.internal("snakeright.png"));
        snakeHeadTex = new Texture(Gdx.files.internal("snakeleft.png"));
        snakeBodyTex = new Texture(Gdx.files.internal("snakebody.jpg"));
        creatorName = new Texture(Gdx.files.internal("username.png"));
        snakeTitle = new Texture(Gdx.files.internal("snaketitle.png"));
        gameMsg = new Texture(Gdx.files.internal("titlescreenmsg.png"));
        gameMessage = new Texture(Gdx.files.internal("titlescreenmsg.png"));
        transparentBG = new Texture(Gdx.files.internal("transparentbg.png"));
        gameOverMsg = new Texture(Gdx.files.internal("gameovermsg.png"));
        gameOverMessage = new Texture(Gdx.files.internal("gameovermsg.png"));
        gameOverTitle = new Texture(Gdx.files.internal("gameover.png"));
        winTitle = new Texture(Gdx.files.internal("victoryscreen.png"));
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 680, 680);
    }

    public void dispose(){
        background.dispose();
        snakeBodyTex.dispose();
        snakeLeftTex.dispose();
        snakeUpTex.dispose();
        snakeDownTex.dispose();
        snakeRightTex.dispose();
        snakeHeadTex.dispose();
        snakeBodyTex.dispose();
        creatorName.dispose();
        snakeTitle.dispose();
        gameMsg.dispose();
        gameMessage.dispose();
        transparentBG.dispose();
        gameOverMsg.dispose();
        gameOverMessage.dispose();
        gameOverTitle.dispose();
        winTitle.dispose();
    }
}
