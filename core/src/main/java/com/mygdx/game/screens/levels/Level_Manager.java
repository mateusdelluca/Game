package com.mygdx.game.screens.levels;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.entities.Player;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.PausePage;

import java.util.ArrayList;

import static com.mygdx.game.images.Images.tile;
import static com.mygdx.game.screens.levels.Level.*;

public class Level_Manager extends State implements ContactListener {

    public static Level currentLevel;
    public static String currentLevelName = "Level1", oldLevel;
    public static int lvl = 1;
    public static ArrayList<Body> bodiesToDestroy = new ArrayList<>();

    public static SpriteBatch spriteBatch = new SpriteBatch();



    public static final OrthographicCamera camera = new OrthographicCamera(WIDTH, HEIGHT);

    public static final Viewport viewport = new ScreenViewport(camera);;

    public static boolean loaded;

    public Level_Manager() {
        currentLevel = returnLevel();
        oldLevel = currentLevelName;
        spriteBatch = new SpriteBatch();
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

    }

    public void changeLevel() {
        if (!currentLevelName.equals(oldLevel)) {
            currentLevel = returnLevel();
            lvl = Integer.parseInt(currentLevelName.substring(5));
            oldLevel = currentLevelName;
        }
    }

    public Level returnLevel() {

        switch (currentLevelName) {
            case "Level1": {
                return new Level3();
            }
            case "Level2": {
//                if (currentLevel.objetos != null) {
//                    for (Objeto objeto : currentLevel.objetos) {
//                        objeto.getBody().setTransform(-10_000, -10_000, 0);
//
//                    }
//                }

//                for (Body bodies : tile.bodies_of_rects)
//                    bodies.setTransform(-10_000, -10_000, 0);
//                for (Body bodies : tile.bodies_of_thorns)
//                    bodies.setTransform(-10_000, -10_000, 0);
//                Images.staticObjects = Images.tile.loadMapObjects("Rects");
//                return level2;
                return new Level2();
            }
            case "Level3": {


//                if (currentLevel.objetos != null) {
//                    for (Objeto objeto : currentLevel.objetos)
//                        objeto.getBody().setTransform(-15_000, -15_000, 0);
//                    currentLevel.objetos.clear();
//                }
//                for (Body bodies : tile.bodies_of_rects)
//                    bodies.setTransform(-13_000, -13_000, 0);
//                for (Body bodies : tile.bodies_of_thorns)
//                    bodies.setTransform(-11_000, -11_000, 0);
                return new Level3();
            }
            case "Level4": {
//                if (currentLevel.objetos != null) {
//                    for (Objeto objeto : currentLevel.objetos)
//                        objeto.getBody().setTransform(-15_000, -15_000, 0);
//                    currentLevel.objetos.clear();
//                }
//                for (Body bodies : tile.bodies_of_rects)
//                    bodies.setTransform(-13_000, -13_000, 0);
//                for (Body bodies : tile.bodies_of_thorns)
//                    bodies.setTransform(-11_000, -11_000, 0);
                return new Level4();
            }
            default: {
                return null;
            }
        }

    }

    @Override
    public void create() {

    }

    @Override
    public void update() {
        if (!PausePage.pause && currentLevel != null)
            currentLevel.update();
        if (!oldLevel.equals(currentLevelName)) {
            oldLevel = currentLevelName;
            assert currentLevel != null;
            try {
                currentLevel = returnLevel();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            currentLevelName = currentLevel.toString();
        }
    }

    @Override
    public void render() {
        changeLevel();
//        if (!StateManager.oldState.equals(StateManager.States.LEVEL.name()))
        currentLevel.render();
//        if (loaded) {
//            world.setContactListener(this);
//            loaded = false;
//        }


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

    @Override
    public String toString() {
        return "LEVEL_MANAGER";
    }
}
