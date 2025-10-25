package com.mygdx.game.manager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.mygdx.game.images.Images;
import com.mygdx.game.screens.*;
import com.mygdx.game.sfx.Sounds;
import lombok.Getter;
import lombok.Setter;

import static com.mygdx.game.Application.level_manager;

public class StateManager extends Game {


    public enum States implements StateInterface{
         MAIN_MENU(new MainPage()),
         LOAD(new LoadPage()),
         PAUSE(new PausePage()),
         LEVEL_MANAGER(null),
         SAVE(new SavePage()),
         INVENTORY(new Inventory()),
         STATS(new Stats()),
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
    public static String oldState, currentStateName;

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
        try {
//            if (newState.name().equals(level_manager.toString()))
//                oldState = level_manager.toString();
//            else
            oldState = currentState.name();
            currentState = newState;
            currentStateName = currentState.name();
            if (currentStateName.equals(level_manager.toString())) {
                Gdx.input.setInputProcessor(level_manager);
            } else {
                Gdx.input.setInputProcessor(currentState);
//                currentStateName = currentState.name();
            }
        } catch(RuntimeException e){
            e.printStackTrace();
        }
    }

    @Override
    public void create() {
    }


}
