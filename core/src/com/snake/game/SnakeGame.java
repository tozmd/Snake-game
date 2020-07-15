package com.snake.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.snake.game.assets.Assets;

public class SnakeGame extends Game {
    OrthographicCamera camera;
    SpriteBatch batch;
    Assets assets;
    public void create() {
        assets = new Assets();
        batch = new SpriteBatch();
        assets.loadAssets();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 680, 680);

        setScreen(new TitleScreen(this));
}

    @Override
    public void dispose(){
        batch.dispose();
    }
}
