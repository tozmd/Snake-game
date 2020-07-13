package com.snake.game.model;

import com.badlogic.gdx.math.Rectangle;

public class GameObject extends Rectangle {
    Rectangle newGameObj;
    public GameObject(float x, float y){
        this.set(x,y,40,40);
    }
}
