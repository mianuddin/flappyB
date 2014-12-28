package com.mianuddin.flappyB.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
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
        int randomInt;
        randomInt = randomGenerator.nextInt(800)+1;
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

    public Rectangle getBoundsTop() {
        return new Rectangle(positionX, positionTopY2, textureUp.getWidth(), textureUp.getHeight());
    }

    public Rectangle getBoundsBottom() {
        return new Rectangle(positionX, positionBottomY2, textureUp.getWidth(), positionBottomY-positionBottomY2);
    }

    public boolean checkCollision(LilB bird) {
        return bird.getBounds().overlaps(getBoundsTop()) || bird.getBounds().overlaps(getBoundsBottom());
    }
}