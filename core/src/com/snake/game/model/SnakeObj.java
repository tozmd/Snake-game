package com.snake.game.model;

import com.snake.game.movement.direction;

public class SnakeObj extends GameObject{

    public SnakeObj(direction dir, float x, float y){
        super(x,y);
        directionToMove = dir;
    }

    public direction getDirection(){
        return directionToMove;
    }

    public void setDirection(direction direction){
        directionToMove = direction;
    }

    private direction directionToMove;

}
