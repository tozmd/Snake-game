package com.snake.game;

import com.badlogic.gdx.Game;
import com.snake.game.screens.TitleScreen;

public class SnakeGame extends Game {
    public void create() {
        setScreen(new TitleScreen(this));
}

    @Override
    public void dispose(){
    }
}
