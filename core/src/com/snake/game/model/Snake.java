package com.snake.game.model;

import com.snake.game.movement.Direction;

public class Snake extends GameObject{

    public Snake(Direction dir, float x, float y){
        super(x,y);
        directionToMove = dir;
    }

    public Direction getDirection(){
        return directionToMove;
    }

    public void setDirection(Direction direction){
        directionToMove = direction;
    }

    private Direction directionToMove;

}
