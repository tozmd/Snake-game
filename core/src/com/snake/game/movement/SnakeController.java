package com.snake.game.movement;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;
import com.snake.game.screens.GameOver;
import com.snake.game.SnakeGame;
import com.snake.game.model.FoodObj;
import com.snake.game.model.SnakeBody;
import com.snake.game.assetss.Assets;
import com.snake.game.model.SnakeHead;

public class SnakeController {
    public void inputToMovement(Array<SnakeBody> snakeBodies) {
        if (Gdx.input.isKeyPressed(Input.Keys.W) && !snakeBodies.get(0).getDirection().equals(Direction.MOVE_DOWN)){
            DirectionToMove = Direction.MOVE_UP;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && !snakeBodies.get(0).getDirection().equals(Direction.MOVE_UP)) {
            DirectionToMove = Direction.MOVE_DOWN;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && !snakeBodies.get(0).getDirection().equals(Direction.MOVE_RIGHT)){
            DirectionToMove = Direction.MOVE_LEFT;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && !snakeBodies.get(0).getDirection().equals(Direction.MOVE_LEFT)){
            DirectionToMove = Direction.MOVE_RIGHT;
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
                if(getDir().equals(Direction.STATIONARY)){
                    SnakeBody newSnakeBody = new SnakeBody(Direction.STATIONARY, snakeHead.x + 40, snakeHead.y);
                    snakeBodies.add(newSnakeBody);
                }
                else if (getDir().equals(Direction.MOVE_LEFT)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_LEFT, snakeHead.x + 40, snakeHead.y);
                    snakeBodies.add(newSnakeBody);
                } else if (getDir().equals(Direction.MOVE_RIGHT)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_RIGHT, snakeHead.x - 40, snakeHead.y);
                    snakeBodies.add(newSnakeBody);
                } else if (getDir().equals(Direction.MOVE_DOWN)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_DOWN, snakeHead.x, snakeHead.y + 40);
                    snakeBodies.add(newSnakeBody);
                } else if (getDir().equals(Direction.MOVE_UP)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_UP, snakeHead.x, snakeHead.y - 40);
                    snakeBodies.add(newSnakeBody);
                }
            }
            if (!snakeBodies.isEmpty()) {
                if (snakeBodies.get(snakeLength).getDirection().equals(Direction.STATIONARY)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.STATIONARY, snakeBodies.get(snakeLength).getX() + 40, snakeBodies.get(snakeLength).getY());
                    snakeBodies.add(newSnakeBody);
                }
                else if (snakeBodies.get(snakeLength).getDirection().equals(Direction.MOVE_LEFT)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_LEFT, snakeBodies.get(snakeLength).getX() + 40, snakeBodies.get(snakeLength).getY());
                    snakeBodies.add(newSnakeBody);
                } else if (snakeBodies.get(snakeLength).getDirection().equals(Direction.MOVE_RIGHT)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_RIGHT, snakeBodies.get(snakeLength).getX() - 40, snakeBodies.get(snakeLength).getY());
                    snakeBodies.add(newSnakeBody);
                } else if (snakeBodies.get(snakeLength).getDirection().equals(Direction.MOVE_DOWN)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_DOWN, snakeBodies.get(snakeLength).getX(), snakeBodies.get(snakeLength).getY() + 40);
                    snakeBodies.add(newSnakeBody);
                } else if (snakeBodies.get(snakeLength).getDirection().equals(Direction.MOVE_UP)) {
                    SnakeBody newSnakeBody = new SnakeBody(Direction.MOVE_UP, snakeBodies.get(snakeLength).getX(), snakeBodies.get(snakeLength).getY() - 40);
                    snakeBodies.add(newSnakeBody);
                }
            }
            SnakeBody.incrementSnakeLength();
        }
    }

    public void collisionDetection(SnakeGame game, SnakeHead snakeHead, FoodObj food, Array<SnakeBody> snakeBodies, Assets assets){
        for(SnakeBody s : snakeBodies){
            if(snakeHead.overlaps(s)){
                gameReset(snakeHead, food, snakeBodies, assets);
                game.setScreen(new GameOver(game));
            }
        }
        if(snakeHead.getX()<0 || snakeHead.getX()>=680 || snakeHead.getY()<0 || snakeHead.getY()>=680){
            gameReset(snakeHead, food, snakeBodies, assets);
            game.setScreen(new GameOver(game));
        }
    }

    public void gameReset(SnakeHead snakeHead, FoodObj food, Array<SnakeBody> snakeBodies, Assets assets){
        snakeHead.setPosition(360,360);
        food.setPosition(120,120);
        snakeBodies.clear();
        setDir(Direction.STATIONARY);
        assets.snakeHeadTex = assets.snakeLeftTex;
        SnakeBody.setSnakeLength(0);
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

    public void setDir(Direction newDir){
        DirectionToMove = newDir;
    }

    public Direction getDir(){
        return DirectionToMove;
    }
    private Direction DirectionToMove = Direction.STATIONARY;
}
