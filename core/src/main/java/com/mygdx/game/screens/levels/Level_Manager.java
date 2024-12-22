package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.mygdx.game.Application;
import com.mygdx.game.screens.Tile;

public class Level_Manager implements Screen, InputProcessor {

    public Level level1, level2, level3;
    public Level currentLevel;
    public static String currentLevelName = "Level3";
    private Application app;

    public Level_Manager(Application app){
        this.app = app;
        level1 = new Level1(app);
//        level2 = new Level2(app);
//        level3 = new Level3(app);
        changeLevel("Level3", app);
    }

    public void changeLevel(String levelName, Application app){
        currentLevel = returnLevel(levelName);
        currentLevel.setTile(new Tile(levelName + "/" + levelName + ".tmx"));
//      currentLevel.getTile().createBodies(currentLevel.staticObjects, currentLevel.world, false, "Rects");
        Gdx.input.setInputProcessor(this);
        app.setScreen(currentLevel);
    }

    public Level returnLevel(String level){
        switch(level){
            case "Level1":{
                currentLevelName = "Level1";
                currentLevel.staticObjects = currentLevel.getTile().loadMapObjects("Rects");
                return level1;
            }
            case "Level2":{
                currentLevelName = "Level2";
                currentLevel.staticObjects = currentLevel.getTile().loadMapObjects("Rects");
                return new Level2(app);
            }
            case "Level3":{
                currentLevelName = "Level3";
//                currentLevel.staticObjects = currentLevel.getTile().loadMapObjects("Rects");
               return new Level3(app);
            }
            default: {
                return level1;
            }
        }
    }

    @Override
    public void show() {
        currentLevel.show();
    }

    @Override
    public void render(float v) {
        currentLevel.render(v);
        currentLevel.spriteBatch.begin();
        currentLevel.boy.render(currentLevel.spriteBatch);
        currentLevel.spriteBatch.end();
    }

    @Override
    public void resize(int i, int i1) {
        currentLevel.resize(i,i1);
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
    public void hide() {
        currentLevel.hide();
    }

    @Override
    public void dispose() {
        currentLevel.dispose();
    }

    @Override
    public boolean keyDown(int i) {
        currentLevel.keyDown(i);
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
        currentLevel.mouseMoved(i,i1);
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        currentLevel.scrolled(v, v1);
        return false;
    }
}
