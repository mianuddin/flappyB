package com.mianuddin.flappyB.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mianuddin.flappyB.TextureManager;
import com.mianuddin.flappyB.camera.OrthoCamera;
import com.mianuddin.flappyB.components.LilB;
import com.mianuddin.flappyB.components.Pipes;
import com.mianuddin.flappyB.flappyB;

public class GameScreen extends Screen {

    private OrthoCamera camera;
    final int GRAVITY_RATE = 5;
    final int FLAP_DISTANCE = 180;
    final int PIPE_GAP = FLAP_DISTANCE+(FLAP_DISTANCE/3)+TextureManager.LILB.getHeight();
    final int PIPE_MOVE_RATE = 5;
    LilB lilb = new LilB();
    Pipes pipes1;
    Pipes pipes2;

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();
        pipes1 = new Pipes(PIPE_GAP);
        pipes2 = new Pipes(PIPE_GAP, flappyB.WIDTH+(flappyB.WIDTH/2));
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
            lilb.flap(FLAP_DISTANCE);
        }
        pipes1.move(PIPE_MOVE_RATE);
        pipes2.move(PIPE_MOVE_RATE);
        if(pipes1.getPositionX2() <= 0)
            pipes1 = new Pipes(PIPE_GAP);
        if(pipes2.getPositionX2() <= 0)
            pipes2 = new Pipes(PIPE_GAP);
        lilb.pullDown(GRAVITY_RATE);

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(TextureManager.BG, 0, 0);
        lilb.render(sb);
        pipes1.render(sb);
        pipes2.render(sb);
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
