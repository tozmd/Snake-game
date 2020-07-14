package com.snake.game.model;

import com.snake.game.movement.direction;

public class SnakeHead extends SnakeObj {
    private float prevX;
    private float prevY;
    public SnakeHead(direction dir, float x, float y){
        super(dir,x,y);
    }

    public float getPrevX(){return prevX;}

    public float getPrevY(){return prevY;}

    public void setOldPos(){
        prevX = getX();
        prevY = getY();
    }

}
