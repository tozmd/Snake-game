package com.snake.game.model;

import com.snake.game.movement.Direction;

public class SnakeBody extends Snake {
    //Length of snake's tail
    private static int snakeLength = 0;
    public SnakeBody(Direction dir, float x, float y, boolean verticalFacing){
        super(dir,x,y, verticalFacing);
    }

    /**
     * Returns the current snake tail length
     * @return The snake tail length
     */
    public static int getSnakeLength(){
        return snakeLength;
    }

    /**
     * Set the length of the snakes tail
     * @param number The new snake tail length
     */
    public static void setSnakeLength(int number){
        snakeLength = number;
    }

    /**
     * Increments length of snake tail
     */
    public static void incrementSnakeLength(){
        setSnakeLength(snakeLength + 1);
    }
}
