package com.mygdx.game.screens.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.PausePage;
import com.mygdx.game.screens.Tile;

import java.util.ArrayList;

public class Level_Manager extends State implements ContactListener {

    private Level level1, level2, level3;
    public static Level currentLevel;
    public static String currentLevelName = "Level1";

    public static ArrayList<Objeto> objetos = new ArrayList<>();

    public static ArrayList<Body> bodiesToDestroy = new ArrayList<>();

    public static SpriteBatch spriteBatch = new SpriteBatch();

    public static World world = new World(new Vector2(0, -10), true);

    public static Viewport viewport;
    public static OrthographicCamera camera;

//    public static float PPM = 100f;
    public static boolean loaded;

    public Level_Manager() {
        try {
            level1 = new Level1();
            world.setContactListener(this);
            changeLevel("Level1");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void changeLevel(String levelName) {
        currentLevel = returnLevel(levelName);
        Images.tile = new Tile(levelName + "/" + levelName + ".tmx");
        assert currentLevel != null;
    }

    public Level returnLevel(String level) {
        switch (level) {
            case "Level1": {
                currentLevelName = "Level1";
                Images.staticObjects = Images.tile.loadMapObjects("Rects");
                return level1;
            }
            case "Level2": {
//                currentLevelName = "Level2";
//                Images.staticObjects = Images.tile.loadMapObjects("Rects");
//                return level2;
                return null;
            }
            case "Level3": {
                currentLevelName = "Level3";
                return level3;
            }
            default: {
                return level1;
            }
        }
    }

    @Override
    public void create() {

    }

    @Override
    public void render() {
        if (!StateManager.oldState.equals(StateManager.States.LEVEL.name()))
            currentLevel.render();
        if (loaded) {
            world.setContactListener(this);
            loaded = false;
        }
    }

    @Override
    public void resize(int i, int i1) {
        currentLevel.resize(i, i1);
    }

    @Override
    public void pause() {
        currentLevel.pause();
    }

    @Override
    public void resume() {
        currentLevel.resume();
    }


    @Override
    public void dispose() {
        currentLevel.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        currentLevel.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int i) {
        currentLevel.keyUp(i);
        return false;
    }

    @Override
    public boolean keyTyped(char c) {
        currentLevel.keyTyped(c);
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        currentLevel.touchDown(i, i1, i2, i3);
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        currentLevel.touchUp(i, i1, i2, i3);
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        currentLevel.touchCancelled(i, i1, i2, i3);
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        currentLevel.touchDragged(i, i1, i2);
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        currentLevel.mouseMoved(i, i1);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        currentLevel.scrolled(v, v1);
        return false;
    }

    @Override
    public void update() {
        if (!PausePage.pause)
            currentLevel.update();
    }

    @Override
    public void beginContact(Contact contact) {
        currentLevel.beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        currentLevel.endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold manifold) {
        currentLevel.preSolve(contact, manifold);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse) {
        currentLevel.postSolve(contact, contactImpulse);
    }
}
