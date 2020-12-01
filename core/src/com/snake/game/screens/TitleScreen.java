package com.snake.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.snake.game.SnakeGame;
import com.snake.game.assets.Assets;

public class TitleScreen extends ScreenAdapter {
    SnakeGame game;
    Assets assets;
    public TitleScreen(SnakeGame game){
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
                    game.setScreen(new GameScreen(game));
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
        assets.batch.draw(assets.creatorName,0,0);
        assets.batch.draw(assets.snakeTitle,0,0);
        assets.batch.draw(assets.gameMsg,0,0);
        clock += Gdx.graphics.getDeltaTime();
        if(clock>0.4) {
            msgSwitch *= -1;
            if(msgSwitch<0){
                assets.gameMsg = assets.transparentBG;
            }
            else if(msgSwitch>0){
                assets.gameMsg = assets.gameMessage;
            }
            clock = 0;
        }
        assets.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
        assets.batch.dispose();
    }

    private float clock;
    private float msgSwitch = 1;
}
