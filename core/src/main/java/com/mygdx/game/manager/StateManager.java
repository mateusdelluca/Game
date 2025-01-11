package com.mygdx.game.manager;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.mygdx.game.screens.*;
import com.mygdx.game.screens.levels.Level_Manager;
import lombok.Getter;

public class StateManager extends Game {


    public enum States implements ApplicationListener, InputProcessor{
         MAIN_MENU(new MainPage()),
         LEVEL(new Level_Manager()),
         PAUSE(new PausePage()),
         SAVE(new SavePage()),
         LOAD(new LoadPage()),
         GAME_OVER(new GameOver());

         @Getter
         private State state;

         States(State state) {
            this.state = state;
         }

         @Override
         public void create() {
             state.create();
         }

         @Override
         public void render() {
             if (state != null) {
                 state.render();
             }
         }

         public void resize(int width, int height) {
             if (state != null) {
                 state.resize(width, height);
             }
         }

         public void pause() {
             if (state != null) {
                 state.pause();
             }
         }

         public void resume() {
             if (state != null) {
                 state.resume();
             }
         }


         public void dispose() {
             if (state != null) {
                 state.dispose();
             }
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

    public static States currentState = States.MAIN_MENU;

    public StateManager(){
        Gdx.input.setInputProcessor(currentState);
    }
    public static void setState(States newState) {
        if (currentState != null) {
            currentState.dispose();
        }
        currentState = newState;
        currentState.create();
        Gdx.input.setInputProcessor(currentState);
    }

    @Override
    public void create() {

    }
}
