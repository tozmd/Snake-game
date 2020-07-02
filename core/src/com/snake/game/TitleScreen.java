package com.snake.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

public class TitleScreen extends ScreenAdapter {
    SnakeGame game;
    public TitleScreen(SnakeGame game){
        this.game = game;
    }

    @Override
    public void show() {
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
        game.batch.begin();
        game.batch.draw(game.background,0,0);
        game.batch.draw(game.creatorName,0,0);
        game.batch.draw(game.snakeTitle,0,0);
        game.batch.draw(game.gameMsg,0,0);
        clock += Gdx.graphics.getDeltaTime();
        if(clock>0.4) {
            msgSwitch *= -1;
            if(msgSwitch<0){
                game.gameMsg = game.transparentBG;
            }
            else if(msgSwitch>0){
                game.gameMsg = game.gameMessage;
            }
            clock = 0;
        }
        game.batch.end();
    }

    @Override
    public void hide(){
        Gdx.input.setInputProcessor(null);
    }

    private float clock;
    private float msgSwitch = 1;
}
