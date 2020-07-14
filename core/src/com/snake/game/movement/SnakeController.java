package com.snake.game.movement;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.snake.game.GameOver;
import com.snake.game.model.SnakeBody;
import com.snake.game.Assets.Assets;
import com.snake.game.model.SnakeHead;

public class SnakeController {
    public void inputToMovement(Array<SnakeBody> snakeBodies) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && !snakeBodies.get(0).getDirection().equals(com.snake.game.movement.direction.MOVE_DOWN)){
            DirectionToMove = com.snake.game.movement.direction.MOVE_UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && !snakeBodies.get(0).getDirection().equals(com.snake.game.movement.direction.MOVE_UP)) {
            DirectionToMove = com.snake.game.movement.direction.MOVE_DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && !snakeBodies.get(0).getDirection().equals(com.snake.game.movement.direction.MOVE_RIGHT)){
            DirectionToMove = com.snake.game.movement.direction.MOVE_LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && !snakeBodies.get(0).getDirection().equals(com.snake.game.movement.direction.MOVE_LEFT)){
            DirectionToMove = com.snake.game.movement.direction.MOVE_RIGHT;
        }
    }

    public void move(Assets assets, SnakeHead snakehead) {
        snakehead.setOldPos();
        switch (DirectionToMove) {
            case MOVE_UP:
                assets.snakeHeadTex = assets.snakeUpTex;
                snakehead.y += 40;
                break;
            case MOVE_DOWN:
                assets.snakeHeadTex = assets.snakeDownTex;
                snakehead.y -= 40;
                break;
            case MOVE_RIGHT:
                assets.snakeHeadTex = assets.snakeRightTex;
                snakehead.x += 40;
                break;
            case MOVE_LEFT:
                assets.snakeHeadTex = assets.snakeLeftTex;
                snakehead.x -= 40;
                break;
        }
    }

    public void addBody(int numberToAdd, Array<SnakeBody> snakeBodies, SnakeHead snakeHead) {
        int snakeLength = SnakeBody.getSnakeLength();
        for (int i = 0; i < numberToAdd; i++) {
            if (snakeBodies.isEmpty()) {
                if(getDir().equals(com.snake.game.movement.direction.STATIONARY)){
                    SnakeBody newSnakeBody = new SnakeBody(direction.STATIONARY, snakeHead.x + 40, snakeHead.y);
                    snakeBodies.add(newSnakeBody);
                }
                else if (getDir().equals(com.snake.game.movement.direction.MOVE_LEFT)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, snakeHead.x + 40, snakeHead.y);
                    snakeBodies.add(newSnakeBody);
                } else if (getDir().equals(direction.MOVE_RIGHT)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, snakeHead.x - 40, snakeHead.y);
                    snakeBodies.add(newSnakeBody);
                } else if (getDir().equals(direction.MOVE_DOWN)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, snakeHead.x, snakeHead.y + 40);
                    snakeBodies.add(newSnakeBody);
                } else if (getDir().equals(com.snake.game.movement.direction.MOVE_UP)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, snakeHead.x, snakeHead.y - 40);
                    snakeBodies.add(newSnakeBody);
                }
            }
            if (!snakeBodies.isEmpty()) {
                if (snakeBodies.get(snakeLength).getDirection().equals(direction.STATIONARY)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.STATIONARY, snakeBodies.get(snakeLength).getX() + 40, snakeBodies.get(snakeLength).getY());
                    snakeBodies.add(newSnakeBody);
                }
                else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_LEFT)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_LEFT, snakeBodies.get(snakeLength).getX() + 40, snakeBodies.get(snakeLength).getY());
                    snakeBodies.add(newSnakeBody);
                } else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_RIGHT)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_RIGHT, snakeBodies.get(snakeLength).getX() - 40, snakeBodies.get(snakeLength).getY());
                    snakeBodies.add(newSnakeBody);
                } else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_DOWN)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_DOWN, snakeBodies.get(snakeLength).getX(), snakeBodies.get(snakeLength).getY() + 40);
                    snakeBodies.add(newSnakeBody);
                } else if (snakeBodies.get(snakeLength).getDirection().equals(direction.MOVE_UP)) {
                    SnakeBody newSnakeBody = new SnakeBody(direction.MOVE_UP, snakeBodies.get(snakeLength).getX(), snakeBodies.get(snakeLength).getY() - 40);
                    snakeBodies.add(newSnakeBody);
                }
            }
            SnakeBody.incrementSnakeLength();
        }
    }

    public void updateBody(Array<SnakeBody> snakeBodies, SnakeHead snakeHead){
        for(int i = SnakeBody.getSnakeLength(); i>=0; i--){
            if (i == 0 && !snakeBodies.isEmpty()) {
                snakeBodies.get(0).setPosition(snakeHead.getPrevX(), snakeHead.getPrevY());
                snakeBodies.get(0).setDirection(getDir());
            } else if (i > 0) {
                snakeBodies.get(i).setPosition(snakeBodies.get(i - 1).getX(), snakeBodies.get(i - 1).getY());
                snakeBodies.get(i).setDirection(snakeBodies.get(i - 1).getDirection());
            }
        }
    }

    public void setDir(direction newDir){
        DirectionToMove = newDir;
    }

    public direction getDir(){
        return DirectionToMove;
    }
    private direction DirectionToMove = direction.STATIONARY;
}
