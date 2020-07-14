package com.snake.game.model;

import com.snake.game.movement.direction;

public class SnakeBody extends SnakeObj{
    public SnakeBody(direction dir, float x, float y){
        super(dir,x,y);
    }
    public static int getSnakeLength(){
        return snakeLength;
    }

    public static void setSnakeLength(int number){
        snakeLength = number;
    }

    public static void incrementSnakeLength(){
        setSnakeLength(snakeLength + 1);
    }
    private static int snakeLength = 0;
}
