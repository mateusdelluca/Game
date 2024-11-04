package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.screens.Jogo;
import com.mygdx.game.screens.levels.Levels;

public class Application extends Game implements InputProcessor {

    public Jogo jogo;

    @Override
    public void create() {
       jogo = new Jogo(this);
       setScreen(jogo.splashScreen);
    }

    @Override
    public void resize(int width, int height) {
    }

    public void update(){

    }

    @Override
    public void render() {
        update();
        super.render();

    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() { // SpriteBatches and Textures must always be disposed

        jogo.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
//        splashScreen.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        splashScreen.touchDown(screenX, screenY, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
//        splashScreen.mouseMoved(screenX, screenY);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}
