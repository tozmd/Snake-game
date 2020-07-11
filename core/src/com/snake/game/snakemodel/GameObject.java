package com.snake.game.snakemodel;

import com.badlogic.gdx.math.Rectangle;
import org.w3c.dom.css.Rect;

public class GameObject extends Rectangle {
    public GameObject(float x, float y){
        Rectangle newGameObj= new Rectangle();
        newGameObj.set(x,y,40,40);
    }
}
