package com.mygdx.game.screens;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.levels.Levels;
import com.mygdx.game.Application;
import com.mygdx.game.sfx.Sounds;

public class Jogo {

    public static Application app;
    public Images images;
    public SplashScreen splashScreen;
    public static Levels levels;
    public LoadScreen loadScreen;
    public SaveScreen saveScreen;
    public PauseScreen pauseScreen;
    public static String currentScreen = "SplashScreen";

    public Jogo(Application app){
        this.app = app;
        images = new Images();
        levels = new Levels();
        pauseScreen = new PauseScreen(app);
        splashScreen = new SplashScreen(app);
        app.setScreen(splashScreen);
        Gdx.input.setInputProcessor(splashScreen);
        loadScreen = new LoadScreen(app);
        saveScreen = new SaveScreen(app);
        new Sounds();
    }

    public void dispose(){
        loadScreen.dispose();
        saveScreen.dispose();
        splashScreen.dispose();
        Levels.currentLevel.dispose();
    }

}
