package com.mianuddin.flappyB;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

public class SoundManager {

    public static Sound FLAP = Gdx.audio.newSound(Gdx.files.internal("flap.ogg"));
    public static Sound HIT = Gdx.audio.newSound(Gdx.files.internal("hit.ogg"));
    public static Sound FALL = Gdx.audio.newSound(Gdx.files.internal("fall.ogg"));
    public static Sound POINT = Gdx.audio.newSound(Gdx.files.internal("point.ogg"));

}
