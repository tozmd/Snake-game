package com.snake.game.model;

import com.snake.game.movement.Direction;

public class SnakeHead extends Snake {
    private float prevX;
    private float prevY;
    public SnakeHead(Direction dir, float x, float y, boolean verticalFacing){
        super(dir,x,y, verticalFacing);
    }

    public float getPrevX(){return prevX;}

    public float getPrevY(){return prevY;}

    public void setOldPos(){
        prevX = getX();
        prevY = getY();
    }

}
