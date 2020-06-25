package com.snake.game;

import static com.badlogic.gdx.math.MathUtils.random;

public class SnakeFood {

    float foodPosX;
    float foodPosY;

    public SnakeFood(float x, float y)
    {
        foodPosX=x;
        foodPosY=y;
    }

    public void moveFood(){
        foodPosX = random(0,17)*40;
        foodPosY = random(0,17)*40;
    }
}
