package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level_Manager;

import static com.mygdx.game.manager.StateManager.*;

public class Application extends Game {

    StateManager stateManager;

    public static Level_Manager level_manager;

    @Override
    public void create() {
//        stateManager.create();
//        images = new Images();
//        sounds = new Sounds();
        stateManager = new StateManager();
        level_manager = new Level_Manager();
        currentState = States.MAIN_MENU;
        StateManager.setStates(StateManager.States.MAIN_MENU);
        StateManager.currentStateName = "MENU";
    }

    @Override
    public void resize(int width, int height) {
        if (currentStateName.equals("LEVEL"))
            level_manager.resize(width, height);
        else
            currentState.resize(width, height);
    }

    public void update(){
        if (currentStateName.equals("LEVEL"))
            level_manager.update();
        else
            currentState.update();
    }

    @Override
    public void render() {
        if (!currentStateName.equals("PAUSE")) {
            update();
            super.render();
            if (!currentStateName.equals("LEVEL"))
                currentState.render();
            else
                level_manager.render();
        }
        else {
            level_manager.render();
            StateManager.States.valueOf("PAUSE").render();
            pause();
        }
    }

    @Override
    public void pause() {
        if (currentStateName.equals("LEVEL"))
            level_manager.pause();
        else
            currentState.pause();
    }

    @Override
    public void resume() {
        if (currentStateName.equals("LEVEL"))
            level_manager.resume();
        else
            currentState.resume();
    }

    @Override
    public void dispose() {
        if (currentStateName.equals("LEVEL"))
            level_manager.dispose();
        else
            currentState.dispose();
    }

}
