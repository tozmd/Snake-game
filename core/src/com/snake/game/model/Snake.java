package com.snake.game.model;

import com.snake.game.movement.Direction;

public class Snake extends GameObject{
    //True if snake object is vertical (both up and down)
    private boolean verticalFacing;
    //The direction the snake object will move
    private Direction directionToMove;

    public Snake(Direction dir, float x, float y, boolean verticalFacing){
        super(x,y);
        directionToMove = dir;
        this.verticalFacing = verticalFacing;
    }

    public Direction getDirection(){
        return directionToMove;
    }

    public void setDirection(Direction direction){
        directionToMove = direction;
    }
}
