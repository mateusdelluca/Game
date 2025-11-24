//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.mygdx.game.sfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.io.Serializable;

public class Sounds implements Serializable{

    public static final Sound JACK_RELOADING = Gdx.audio.newSound(Gdx.files.internal("Jack/Jack_Reloading_Voice.mp3"));
    public static final Sound SABER = Gdx.audio.newSound(Gdx.files.internal("sounds/Saber.wav"));
    public static final Sound JUMP = Gdx.audio.newSound(Gdx.files.internal("sounds/Jump.wav"));
    public static final Sound HIYAH = Gdx.audio.newSound(Gdx.files.internal("sounds/HoYah.wav"));
    public static final Sound GUNSHOT = Gdx.audio.newSound(Gdx.files.internal("sounds/gun shot.wav"));
    public static final Sound WHOOSH = Gdx.audio.newSound(Gdx.files.internal("sounds/Whoosh.wav"));
    public static final Sound PUNCHED = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.wav"));
    public static final Sound HURT = Gdx.audio.newSound(Gdx.files.internal(("sounds/hurt.mp3")));
    public static final Sound MONSTER_HURT = Gdx.audio.newSound(Gdx.files.internal(("sounds/monster_hurt.mp3")));

    public static final Sound LASER_HEADSET = Gdx.audio.newSound(Gdx.files.internal("sounds/Laser_Headset.mp3"));

    public static final Music PAUSE_SONG = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunglasses.mp3"));

    public static Music LEVEL4 = Gdx.audio.newMusic(Gdx.files.internal("sounds/Funk Metro.mp3"));

    public static Music LEVEL3 = Gdx.audio.newMusic(Gdx.files.internal("sounds/Funk Metro 2.mp3"));

    public static Music LEVEL2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/Song 2.mp3"));

    public static Music LEVEL1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/Song 1.mp3"));

    public static final Sound TELETRANSPORT = Gdx.audio.newSound(Gdx.files.internal("sounds/Eletric Whoosh.wav"));

    public static final Sound SHOTGUN = Gdx.audio.newSound(Gdx.files.internal("sounds/Shotgun.mp3"));

    public static final Sound JETPACK = Gdx.audio.newSound(Gdx.files.internal("sounds/jetpack_burning.mp3"));
    public static final Sound EAGLE = Gdx.audio.newSound(Gdx.files.internal("sounds/eagle.mp3"));
    public static final Sound TRIGGER = Gdx.audio.newSound(Gdx.files.internal("sounds/trigger.mp3"));
    public static float pause_musicPosition;

    public static final Sound clink = Gdx.audio.newSound(Gdx.files.internal("sounds/clink.mp3"));
    public static final Sound clink2 = Gdx.audio.newSound(Gdx.files.internal("sounds/clink2.mp3"));

    public Sounds() {
    }

    /*Faixa: "Vanilla", Aguava
    Música pela https://slip.stream
    Download gratuito / Stream: https://get.slip.stream/qnbobP
    Ouça no Spotify: https://go-stream.link/sp-aguava*/

    /*Faixa: "Flyga", Aguava
    Música pela https://slip.stream
    Download gratuito / Stream: https://get.slip.stream/BBJ7KI
    Ouça no Spotify: https://go-stream.link/sp-aguava*/
}
