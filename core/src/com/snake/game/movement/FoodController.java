package com.snake.game.movement;

import com.badlogic.gdx.utils.Array;
import com.snake.game.SnakeGame;
import com.snake.game.WinScreen;
import com.snake.game.model.FoodObj;
import com.snake.game.model.SnakeBody;
import com.snake.game.model.SnakeHead;
import com.snake.game.movement.SnakeController;

import static com.badlogic.gdx.math.MathUtils.random;

public class FoodController {
    public void moveFood(SnakeGame game, FoodObj food, SnakeHead snakeHead, Array<SnakeBody> snakeBodies){
        if(SnakeBody.getSnakeLength() == 288){
            game.setScreen(new WinScreen(game));
        }
        else{
            randomizeFoodPos(food, snakeHead, snakeBodies);
        }
    }

    public void randomizeFoodPos(FoodObj food, SnakeHead snakeHead, Array<SnakeBody> snakeBodies) {
        if (food.overlaps(snakeHead)) {
            food.setPosition(random(0, 16) * 40,random(0, 16) * 40);
        }
        for (SnakeBody s : snakeBodies) {
            if (food.overlaps(s)) {
                food.setPosition(random(0, 16) * 40,random(0, 16) * 40);
            }
        }
        if (food.overlaps(snakeHead)){
            randomizeFoodPos(food, snakeHead, snakeBodies);
        }
        for (SnakeBody s : snakeBodies) {
            if (food.overlaps(s)) {
                randomizeFoodPos(food, snakeHead, snakeBodies);
            }
        }
    }

}
