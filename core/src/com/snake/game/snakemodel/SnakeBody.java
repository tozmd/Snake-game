package com.snake.game.snakemodel;

import com.snake.game.direction;

public class SnakeBody extends GameObject{

    public SnakeBody(direction dir, float x, float y){
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
