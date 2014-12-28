package com.mianuddin.flappyB.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Rectangle;
import com.mianuddin.flappyB.SoundManager;
import com.mianuddin.flappyB.TextureManager;
import com.mianuddin.flappyB.flappyB;

public class LilB {

    private Texture texture = TextureManager.LILB;
    private static final int positionX = 180;
    int positionY = (460 + 720 - texture.getWidth());

    boolean flap = false;
    int flapcount = 0;

    public void render(SpriteBatch sb, boolean playing) {
        Sprite lilbSprite = new Sprite(texture);
        lilbSprite.setPosition(positionX, positionY);

        // Flap position
        if(playing && flap) {
            lilbSprite.setRotation(lilbSprite.getRotation() + 25);
            lilbSprite.draw(sb);
            flapcount++;
            if(flapcount == 30) {
                flap = false;
                flapcount = 0;
            }
        }

        // Normal Position
        if(playing && !flap)
            sb.draw(texture, positionX, positionY);

        // Falling Position
        if(!playing) {
            lilbSprite.setRotation(lilbSprite.getRotation() + 90);
            lilbSprite.draw(sb);
        }
    }

    public void pullDown(int gravityRate) {
        if(positionY >= 460 || positionY-gravityRate >= 460)
            positionY -= gravityRate;
    }

    public void flap(int flapDist) {
        SoundManager.FLAP.play();
        if(positionY+texture.getHeight()+flapDist > flappyB.HEIGHT)
            positionY += flappyB.HEIGHT-(positionY+texture.getHeight());
        else if(positionY+texture.getHeight() <= flappyB.HEIGHT)
            positionY += flapDist;
        flap = true;
    }

    public int getPositionX(int x) {
        if(x == 1)
            return positionX;
        if(x == 2)
            return positionX+texture.getWidth();
        return 0;
    }

    public int getPositionY(int x) {
        if(x == 1)
            return positionY;
        if(x == 2)
            return positionY+texture.getHeight();
        return 0;
    }

    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, texture.getWidth(), texture.getHeight());
    }
}