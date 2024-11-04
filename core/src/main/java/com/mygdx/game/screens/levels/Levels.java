package com.mygdx.game.screens.levels;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.mygdx.game.Application;
import com.mygdx.game.screens.Tile;

public class Levels implements Screen, InputProcessor {

    public static final Level level1 = new Level1(), level2 = new Level2();
    public static Level currentLevel;

    public Levels(){
//        level1 = new Level1(app);
//        level2 = new Level2(app);
        currentLevel = level1;
    }

    public static void changeLevel(String level, Application app){
        currentLevel = returnLevel(level);
        currentLevel.setTile(new Tile("Level2/Level2.tmx"));
        currentLevel.staticObjects = currentLevel.getTile().loadMapObjects("Rects");
        currentLevel.getTile().createBodies(currentLevel.staticObjects, currentLevel.world, false);
        Gdx.input.setInputProcessor(Levels.currentLevel);
        app.setScreen(currentLevel);
    }

    public static Level returnLevel(String level){
        switch(level){
            case "Level2":{
                return level2;
            }
            default:{
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
        currentLevel.render(0);
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
