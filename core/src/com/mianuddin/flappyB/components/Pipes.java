package com.mianuddin.flappyB.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mianuddin.flappyB.TextureManager;
import com.mianuddin.flappyB.flappyB;
import java.util.Random;

public class Pipes {
    private Texture textureUp = TextureManager.PIPE_UP;
    private Texture textureDown = TextureManager.PIPE_DOWN;
    private Texture ground = TextureManager.BG_GROUND;
    private int positionX, positionX2;
    private int positionTopY, positionTopY2, positionBottomY, positionBottomY2;

    public Pipes(int gap, int x) {
        this.positionX = x;
        this.positionX2 = x+textureUp.getWidth();
        this.positionTopY = flappyB.HEIGHT;
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(840)+1;
        positionTopY2 = 548 + randomInt + gap;
        positionBottomY = positionTopY2 - gap;
        positionBottomY2 = 460;
    }

    public Pipes(int gap) {
        this.positionX = flappyB.WIDTH;
        this.positionX2 = flappyB.WIDTH+textureUp.getWidth();
        this.positionTopY = flappyB.HEIGHT;
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(840)+1;
        positionTopY2 = 548 + randomInt + gap;
        positionBottomY = positionTopY2 - gap;
        positionBottomY2 = 460;
    }

    public void render(SpriteBatch sb) {
        sb.draw(textureDown, positionX, positionTopY2); // Top Pipe
        sb.draw(textureUp, positionX, positionBottomY-textureUp.getHeight()); // Bottom Pipe
        sb.draw(ground, 0, 0); // Ground
    }

    public void move(int rate) {
        positionX -= rate;
        positionX2 -= rate;
    }
    
    public int getPositionX2() {
        return positionX2;
    }

//    public boolean contains(float x, float y) {
//        if(x >= positionX && x <= positionX2 && y >= positionY && y <= positionY2)
//            return true;
//        else
//            return false;
//    }
}