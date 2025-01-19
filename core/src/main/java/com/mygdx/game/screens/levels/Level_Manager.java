package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.entities.Monster1;
import com.mygdx.game.entities.Objeto;
import com.mygdx.game.images.Images;
import com.mygdx.game.items.Crystal;
import com.mygdx.game.items.JetPack;
import com.mygdx.game.items.Portal;
import com.mygdx.game.items.Rifle;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.Tile;

import java.io.*;
import java.util.ArrayList;

public class Level_Manager extends State {

    private Level level1;
    public Level currentLevel;
    public static String currentLevelName = "Level3";

    public static ArrayList<Objeto> objetos = new ArrayList<>();

    public static ArrayList<Body> bodiesToDestroy = new ArrayList<>();

    public static SpriteBatch spriteBatch = new SpriteBatch();

    public static World world = new World(new Vector2(0, -10), true);

    public Level_Manager() {
        level1 = new Level();
//        level2 = new Level2(app);
//        level3 = new Level3(app);
        changeLevel("Level3");
    }

    public void changeLevel(String levelName) {
        currentLevel = returnLevel(levelName);
        Images.tile = new Tile(levelName + "/" + levelName + ".tmx");
        assert currentLevel != null;
//        currentLevel.getTile().createBodies(currentLevel.staticObjects, currentLevel.world, false, "Rects");
//        Gdx.input.setInputProcessor(this);
//        app.setScreen(currentLevel);
    }

    public Level returnLevel(String level) {
        switch (level) {
            case "Level1": {
                currentLevelName = "Level1";
                Images.staticObjects = Images.tile.loadMapObjects("Rects");
                return level1;
            }
            case "Level2": {
                currentLevelName = "Level2";
                Images.staticObjects = Images.tile.loadMapObjects("Rects");
                return level1;
            }
            case "Level3": {
                currentLevelName = "Level3";
                return level1;
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
        if (keycode == Input.Keys.P) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.dat"));
                oos.writeObject(currentLevel);
                oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (keycode == Input.Keys.M) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.dat"));
                for (Objeto objeto : objetos) {
//                    objeto.getBody().setUserData("null");
                    bodiesToDestroy.add(objeto.getBody());
                    world.destroyBody(objeto.getBody());
                }

                currentLevel = (Level) ois.readObject();
                world = new World(new Vector2(0,-10), true);
                currentLevel.init();
////                changeLevel("Level3");
                currentLevel.boy.loadWorldAndBody(BodyDef.BodyType.DynamicBody, false);
//                boy.setBody(boy.getBodyData().convertDataToBody(world, BodyDef.BodyType.DynamicBody, false));
                currentLevel.boy.setViewport(currentLevel.viewport);
                for (Objeto objeto : objetos) {
                    if (objeto instanceof Crystal || objeto instanceof Rifle || objeto instanceof JetPack
                        || objeto instanceof Portal)
                        objeto.loadWorldAndBody(BodyDef.BodyType.StaticBody, true);
                    if (objeto instanceof Monster1)
                        objeto.loadWorldAndBody(BodyDef.BodyType.DynamicBody, false);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
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
        currentLevel.update();
    }

    public void write() {
//        Json json = new Json();
//        json.toJson();
//        json.writeObjectStart();
//        json.writeValue(bodiesData);
//        json.writeValue(this);
//        json.writeObjectEnd();


//        Json json = new Json();
//        String jsonString = json.toJson(this);
//
//        FileHandle file = Gdx.files.local("data.json");
//        file.writeString(jsonString, false); // false para sobrescrever o arquivo


    }
//
//    @Override
//    public void read(Json json, JsonValue jsonData) {
//
//    }
}
