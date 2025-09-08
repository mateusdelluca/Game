package com.mygdx.game.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.*;
import com.mygdx.game.screens.levels.Level_Manager;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

public class StateManager extends Game {


    public enum States implements StateInterface{
         MAIN_MENU(new MainPage()),
         LOAD(new LoadPage()),
         PAUSE(new PausePage()),
         SAVE(new SavePage()),
         LEVEL(new Level_Manager()),
         INVENTORY(new Inventory()),
         GAME_OVER(new GameOver());

         @Getter @Setter
         private State state;
         States(State state) {
            this.state = state;
         }

         @Override
         public void create() {
            state.create();
         }

         @Override
         public void resize(int width, int height) {
            state.resize(width, height);
         }

         @Override
         public void render() {
            state.render();
         }

         public void update(){
            state.update();
         }

         @Override
         public void pause() {
            state.pause();
         }

         @Override
         public void resume() {
            state.resume();
         }

         @Override
         public void dispose() {
            state.dispose();
         }

         @Override
         public boolean keyDown(int keycode) {
             state.keyDown(keycode);
            return false;
         }

         @Override
         public boolean keyUp(int keycode) {
            state.keyUp(keycode);
             return false;
         }

         @Override
         public boolean keyTyped(char character) {
             state.keyTyped(character);
            return false;
         }

         @Override
         public boolean touchDown(int screenX, int screenY, int pointer, int button) {
             state.touchDown(screenX, screenY, pointer, button);
             return false;
         }

         @Override
         public boolean touchUp(int screenX, int screenY, int pointer, int button) {
             state.touchUp(screenX, screenY, pointer, button);
             return false;
         }

         @Override
         public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
            state.touchCancelled(screenX, screenY, pointer, button);
            return false;
         }

         @Override
         public boolean touchDragged(int screenX, int screenY, int pointer) {
            state.touchDragged(screenX, screenY, pointer);
            return false;
         }

         @Override
         public boolean mouseMoved(int screenX, int screenY) {
             state.mouseMoved(screenX, screenY);
             return false;
         }

         @Override
         public boolean scrolled(float amountX, float amountY) {
             state.scrolled(amountX, amountY);
             return false;
         }
    };

    public static States currentState;
    public static String oldState;

    public static Images images;

    public static Sounds sounds;

    public StateManager(){
        init();
    }

    public static void init(){
        images = new Images();
        sounds = new Sounds();
    }

    public static void setStates(States newState) {
        if (currentState != null && newState != null)
            oldState = currentState.name();
        currentState = newState;
        Gdx.input.setInputProcessor(currentState);
    }

    @Override
    public void create() {
    }


}
