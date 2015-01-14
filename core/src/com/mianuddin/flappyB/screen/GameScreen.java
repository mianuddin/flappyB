package com.mianuddin.flappyB.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.mianuddin.flappyB.SoundManager;
import com.mianuddin.flappyB.TextureManager;
import com.mianuddin.flappyB.camera.OrthoCamera;
import com.mianuddin.flappyB.components.Button;
import com.mianuddin.flappyB.components.LilB;
import com.mianuddin.flappyB.components.Pipes;
import com.mianuddin.flappyB.flappyB;

public class GameScreen extends Screen {

    private OrthoCamera camera;
    final int GRAVITY_RATE = 12;
    final int FLAP_DISTANCE = 240;
    final int PIPE_GAP = FLAP_DISTANCE+TextureManager.LILB.getHeight()+(TextureManager.LILB.getHeight()/3);
    final int PIPE_MOVE_RATE = 8;
    boolean playing = false;
    boolean splash = true;
    public Integer points = 0;
    LilB lilb = new LilB(FLAP_DISTANCE, GRAVITY_RATE);
    Pipes pipes1;
    Pipes pipes2;
    BitmapFont font;
    Preferences pref = Gdx.app.getPreferences("Flappy Scores");

    @Override
    public void create() {
        camera = new OrthoCamera();
        camera.resize();
        pipes1 = new Pipes(PIPE_GAP);
        pipes2 = new Pipes(PIPE_GAP,
                flappyB.WIDTH + (flappyB.WIDTH / 2) + (TextureManager.PIPE_UP.getWidth() / 2)); // Offset the second pair of pipes.
        font = new BitmapFont(Gdx.files.internal("numbers_180pt.fnt"));
        SoundManager.WOOSH.play();
    }

    @Override
    public void update() {
        camera.update();
    }

    @Override
    public void render(SpriteBatch sb) {

        if(Gdx.input.justTouched()) {
            Vector2 touchPoint = new Vector2(camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY()));
            System.out.println("(" + touchPoint.x + ", " + touchPoint.y + ")");
        }

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
        lilb.render(sb, playing, splash);
        if(playing && !splash)
            drawScore(sb);
        drawSplash(sb);
        drawGameOver(sb);
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

    public void drawSplash(SpriteBatch sb) {
        if(splash) {
            sb.draw(TextureManager.GET_READY,
                    flappyB.WIDTH /2 - TextureManager.GET_READY.getWidth() / 2,
                    flappyB.HEIGHT /2 - TextureManager.GET_READY.getHeight() / 2);
            if(Gdx.input.justTouched()) {
                lilb.flap();
                splash = false;
                playing = true;
            }
        }
    }

    public void drawGameOver(SpriteBatch sb) {

        if(!playing && !splash)  {
            if(points >= pref.getInteger("High Score", points)) {
                pref.putInteger("High Score", points);
                pref.flush();
            }
            Integer highScore = pref.getInteger("High Score");

            // Game Over Screen
            sb.draw(TextureManager.GAME_OVER,
                    flappyB.WIDTH /2 - TextureManager.GAME_OVER.getWidth() / 2,
                    flappyB.HEIGHT /2 - TextureManager.GAME_OVER.getHeight() / 2);
            font.draw(sb, points.toString(),
                    216,
                    1084);
            font.draw(sb, highScore.toString(),
                    550,
                    1084);

            // Replay Button
            Button replay = new Button(TextureManager.REPLAY, flappyB.WIDTH / 2 - TextureManager.REPLAY.getWidth() /2,
                    780 - 120 - TextureManager.REPLAY.getHeight());
            replay.render(sb);
            if(Gdx.input.justTouched()) {
                Vector2 touchPoint = new Vector2(camera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY()));
                if(replay.contains(touchPoint.x, touchPoint.y))
                    ScreenManager.setScreen(new GameScreen());
            }
        }
    }

    public void moveComponents() {
        if(playing) {
            // Check for input.
            if(Gdx.input.justTouched()) {
                lilb.flap();
            }
            pipes1.move(PIPE_MOVE_RATE);
            pipes2.move(PIPE_MOVE_RATE);
            if(pipes1.getPositionX(2) <= 0)
                pipes1 = new Pipes(PIPE_GAP);
            if(pipes2.getPositionX(2) <= 0)
                pipes2 = new Pipes(PIPE_GAP);
            lilb.pullDown();
        }

        if(!playing && lilb.getPositionY(1) > 460 && !splash) {
            lilb.pullDown();
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
        if(!pipes.getPassed() && centerLilB >= centerPipes) {
            pipes.setPassed();
            return true;
        }
        else
            return false;
    }

    public void drawScore(SpriteBatch sb) {
        BitmapFont.TextBounds fontBounds = font.getBounds(points.toString());
        font.draw(sb, points.toString(),
                flappyB.WIDTH / 2 - fontBounds.width / 2,
                flappyB.HEIGHT - 120);
    }
}
