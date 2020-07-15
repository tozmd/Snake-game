package com.snake.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.snake.game.SnakeGame;
import com.snake.game.assets.Assets;
import com.snake.game.screens.TitleScreen;

public class WinScreen extends ScreenAdapter {
    SnakeGame game;
    Assets assets;
    public WinScreen(SnakeGame game){
        this.game = game;
    }

    @Override
    public void show() {
        assets = new Assets();
        assets.loadAssets();
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                if (keyCode == Input.Keys.SPACE) {
                    game.setScreen(new TitleScreen(game));
                }
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, .25f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        assets.batch.begin();
        assets.batch.draw(assets.background,0,0);
        assets.batch.draw(assets.winTitle,0,0);
        assets.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        assets.batch.dispose();
    }

}
