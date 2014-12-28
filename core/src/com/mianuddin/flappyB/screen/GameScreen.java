package com.mianuddin.flappyB.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mianuddin.flappyB.camera.OrthoCamera;

public class GameScreen extends Screen {

    private OrthoCamera camera;

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();
    }

    @Override
    public void update() {
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        // Check for input.
        if(Gdx.input.justTouched()) {
            Vector2 touchPoint = new Vector2(camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY()));
        }

        sb.setProjectionMatrix(camera.combined);
        sb.begin();

        sb.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.resize();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    public void checkWin() {

    }
}
