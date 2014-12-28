package com.mianuddin.flappyB.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mianuddin.flappyB.SoundManager;
import com.mianuddin.flappyB.TextureManager;
import com.mianuddin.flappyB.camera.OrthoCamera;
import com.mianuddin.flappyB.components.LilB;
import com.mianuddin.flappyB.components.Pipes;
import com.mianuddin.flappyB.flappyB;

public class GameScreen extends Screen {

    private OrthoCamera camera;
    final int GRAVITY_RATE = 7;
    final int FLAP_DISTANCE = 190;
    final int PIPE_GAP = FLAP_DISTANCE+(FLAP_DISTANCE/2)+TextureManager.LILB.getHeight()+100;
    final int PIPE_MOVE_RATE = 5;
    boolean playing = true;
    int points = 0;
    LilB lilb = new LilB();
    Pipes pipes1;
    Pipes pipes2;

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();
        pipes1 = new Pipes(PIPE_GAP);
        pipes2 = new Pipes(PIPE_GAP,
                flappyB.WIDTH + (flappyB.WIDTH / 2) + (TextureManager.PIPE_UP.getWidth() / 2)); // Offset the second pair of pipes.
    }

    @Override
    public void update() {
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        moveComponents();

        if(checkPoint(pipes1, lilb) || checkPoint(pipes2, lilb)) {
            SoundManager.POINT.play();
            points++;
        }

        sb.setProjectionMatrix(camera.combined);
        sb.begin();
        sb.draw(TextureManager.BG, 0, 0);
        pipes1.render(sb);
        pipes2.render(sb);
        lilb.render(sb, playing);
        sb.end();

        checkLose();
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

    public void moveComponents() {
        if(playing) {
            // Check for input.
            if(Gdx.input.justTouched()) {
                lilb.flap(FLAP_DISTANCE);
            }
            pipes1.move(PIPE_MOVE_RATE);
            pipes2.move(PIPE_MOVE_RATE);
            if(pipes1.getPositionX(2) <= 0)
                pipes1 = new Pipes(PIPE_GAP);
            if(pipes2.getPositionX(2) <= 0)
                pipes2 = new Pipes(PIPE_GAP);
            lilb.pullDown(GRAVITY_RATE);
        }

        if(!playing && lilb.getPositionY(1) > 460) {
            lilb.pullDown(8);
        }
    }

    public void checkLose() {
        if((pipes1.checkCollision(lilb) || pipes2.checkCollision(lilb)) && playing) {
            playing = false;
            SoundManager.HIT.play();
            float delay = 1; // Length of hitSound
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    SoundManager.FALL.play();
                }
            }, delay);
        }

        if(lilb.getPositionY(1) <= 460 && playing) {
            playing = false;
            SoundManager.HIT.play(1);
        }
    }

    public boolean checkPoint(Pipes pipes, LilB lilb) {
        int centerPipes = pipes.getPositionX(1) + ((pipes.getPositionX(2) - pipes.getPositionX(1)) / 2);
        int centerLilB = lilb.getPositionX(1) + ((lilb.getPositionX(2) - lilb.getPositionX(1)) / 2);
        if(pipes.getPassed() != true && centerLilB >= centerPipes) {
            pipes.setPassed();
            return true;
        }
        else
            return false;
    }
}
