package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.manager.StateManager;

import static com.mygdx.game.manager.StateManager.currentState;
import static com.mygdx.game.manager.StateManager.setStates;

public class Application extends Game {

    StateManager stateManager;



    @Override
    public void create() {
//        stateManager.create();
//        images = new Images();
//        sounds = new Sounds();
        stateManager = new StateManager();
        setStates(StateManager.States.MAIN_MENU);
    }

    @Override
    public void resize(int width, int height) {
        currentState.resize(width, height);
    }

    public void update(){
        currentState.update();
    }

    @Override
    public void render() {
        if (!StateManager.currentState.name().equals("PAUSE")) {
            update();
            super.render();
            currentState.render();
        }
        else {
            StateManager.States.valueOf("LEVEL").render();
            StateManager.States.valueOf("PAUSE").render();
            pause();
        }
    }

    @Override
    public void pause() {
        currentState.pause();
    }

    @Override
    public void resume() {
        currentState.resume();
    }

    @Override
    public void dispose() {
        currentState.dispose();
    }

}
