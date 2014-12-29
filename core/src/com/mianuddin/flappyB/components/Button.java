package com.mianuddin.flappyB.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Button {
    private Texture texture;
    private int positionX, positionY, positionX2, positionY2;

    public Button(Texture texture, int x, int y) {
        this.texture = texture;
        this.positionX = x;
        this.positionY = y;
        this.positionX2 = x+texture.getWidth();
        this.positionY2 = y+texture.getHeight();
    }

    public void render(SpriteBatch sb) {
        sb.draw(texture, positionX, positionY);
    }

    public boolean contains(float x, float y) {
        if(x >= positionX && x <= positionX2 && y >= positionY && y <= positionY2)
            return true;
        else
            return false;
    }
}