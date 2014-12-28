package com.mianuddin.flappyB.components;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Sound;
import com.mianuddin.flappyB.TextureManager;
import com.mianuddin.flappyB.flappyB;

public class LilB {

    private Texture texture = TextureManager.LILB;
    Sound flapSound = Gdx.audio.newSound(Gdx.files.internal("flap.ogg"));
    private static final int positionX = 180;
    int positionY = (460 + 720 - texture.getWidth());

    public void render(SpriteBatch sb) {
        sb.draw(texture, positionX, positionY);
    }

    public void pullDown(int gravityRate) {
        if(positionY >= 460 || positionY-gravityRate >= 460)
            positionY -= gravityRate;
    }

    public void flap(int flapDist) {
        flapSound.play(0.75f);
        if(positionY+texture.getHeight()+flapDist > flappyB.HEIGHT)
            positionY += flappyB.HEIGHT-(positionY+texture.getHeight());
        else if(positionY+texture.getHeight() <= flappyB.HEIGHT)
            positionY += flapDist;
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
}