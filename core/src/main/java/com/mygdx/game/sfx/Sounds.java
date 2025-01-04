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
//    public static Audio MENU = new Audio("src\\main\\res\\sounds\\kickstarter.wav");
//    public static Audio LEVEL1 = new Audio("src\\main\\res\\sounds\\Flyga.wav");
//    public static Audio SUBMENU = new Audio("src\\main\\res\\sounds\\FutureBeat.wav");
//    public static Audio PUNCH = new Audio("src\\main\\res\\sounds\\punch.wav");
//    public static Audio GAME_OVER = new Audio("src\\main\\res\\sounds\\The-Wild-Side.wav");
//    public static Audio SWOOSH = new Audio("src\\main\\res\\sounds\\Swoosh.wav");
//    public static Audio WHOOSH = new Audio("src\\main\\res\\sounds\\Whoosh.wav");
//    public static Audio ELETRIC_WHOOSH = new Audio("src\\main\\res\\sounds\\Eletric Whoosh.wav");
//    public static Audio HIYAH = new Audio("src\\main\\res\\sounds\\HoYah.wav");
//    public static Audio SABER = new Audio("src\\main\\res\\sounds\\Saber.wav");
//    public static Audio GUN_SHOT = new Audio("src\\main\\res\\sounds\\gun-shot.wav");
//    public static Audio JUMP = new Audio("sounds/Jump.wav");
//    public static Audio CAMINHOSDEVIDRO = new Audio("sounds/Caminhos de Vidro.wav");
    public static final Sound SABER = Gdx.audio.newSound(Gdx.files.internal("sounds/Saber.wav"));
    public static final Sound JUMP = Gdx.audio.newSound(Gdx.files.internal("sounds/Jump.wav"));
    public static final Sound HIYAH = Gdx.audio.newSound(Gdx.files.internal("sounds/HoYah.wav"));
    public static final Sound GUNSHOT = Gdx.audio.newSound(Gdx.files.internal("sounds/gun shot.wav"));
    public static final Sound WHOOSH = Gdx.audio.newSound(Gdx.files.internal("sounds/Whoosh.wav"));
    public static final Sound PUNCHED = Gdx.audio.newSound(Gdx.files.internal("sounds/punch.wav"));
    public static final Sound HURT = Gdx.audio.newSound(Gdx.files.internal(("sounds/hurt.mp3")));
    public static final Sound MONSTER_HURT = Gdx.audio.newSound(Gdx.files.internal(("sounds/monster_hurt.mp3")));

    public static final Music PAUSE_SCREEN = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunglasses2.mp3"));

    public static Music LEVEL1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/Sunrise2.mp3"));

    public static final Sound TELETRANSPORT = Gdx.audio.newSound(Gdx.files.internal("sounds/Eletric Whoosh.wav"));

    public static final Sound SHOTGUN = Gdx.audio.newSound(Gdx.files.internal("sounds/Shotgun.mp3"));

    public static final Sound GIRL_HURT = Gdx.audio.newSound(Gdx.files.internal("sounds/girl_saying_oh.mp3"));

    public static final Sound JETPACK = Gdx.audio.newSound(Gdx.files.internal("sounds/jetpack_burning.mp3"));

    public static final Sound TRIGGER = Gdx.audio.newSound(Gdx.files.internal("sounds/trigger.mp3"));
    public static float pause_musicPosition;
    public static boolean isLevel3running = true;

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
