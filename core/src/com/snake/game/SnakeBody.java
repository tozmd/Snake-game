package com.snake.game;

import com.badlogic.gdx.math.Rectangle;

public class SnakeBody{
    float snakeBodyX;
    float snakeBodyY;
    Rectangle newSnakeBody;

    public SnakeBody(direction direction, float x, float y){
        snakeBodyX = x;
        snakeBodyY = y;
        directionToMove=direction;
        newSnakeBody = new Rectangle();
        newSnakeBody.set(snakeBodyX,snakeBodyY,40,40);
    }

    public direction getDirection(){
        return directionToMove;
    }

    public void setDirection(direction direction){
        directionToMove = direction;
    }

    public void setNewPos(float x, float y){
        snakeBodyX = x;
        snakeBodyY = y;
        newSnakeBody.setPosition(x,y);
    }

    private static direction directionToMove;

}
