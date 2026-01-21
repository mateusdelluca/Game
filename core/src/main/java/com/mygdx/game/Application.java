package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.mygdx.game.images.Images;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.PausePage;
import com.mygdx.game.screens.levels.Level_Manager;
import com.mygdx.game.sfx.Sounds;

import static com.mygdx.game.manager.StateManager.*;

public class Application extends Game {

    StateManager stateManager;

    public static Level_Manager level_manager;

    public static Sounds sounds;

    @Override
    public void create() {
//        stateManager.create();
        new Images();
        new Sounds();
        stateManager = new StateManager();
        level_manager = new Level_Manager();
        currentState = States.MAIN_MENU;
        StateManager.setStates(StateManager.States.MAIN_MENU);
        StateManager.currentStateName = States.MAIN_MENU.name();
    }

    @Override
    public void resize(int width, int height) {
        if (currentStateName.equals(level_manager.toString()))
            level_manager.resize(width, height);
        else
            currentState.resize(width, height);
    }

    public void update(){
        if (currentStateName.equals(level_manager.toString()))
            level_manager.update();
        else
            currentState.update();
    }

    @Override
    public void render() {
        if (currentState != States.PAUSE) {
            if (currentStateName.equals(level_manager.toString())) {
//                level_manager.update();
                level_manager.render();
            } else {
                update();
                super.render();
                currentState.render();
            }
        }
        else {
            PausePage.pause = true;
            pause();
        }
    }

    @Override
    public void pause() {
//        if (currentStateName.equals(level_manager.toString()))
//            level_manager.pause();
//        else
//            currentState.pause(); TODO: verificar se este método é o que está travando o leitor de teclas da classe Inventory
        level_manager.pause();
        States.PAUSE.render();
    }

    @Override
    public void resume() {
        if (currentStateName.equals(level_manager.toString()))
            level_manager.resume();
        else
            currentState.resume();
    }

    @Override
    public void dispose() {
        if (currentStateName.equals(level_manager.toString()))
            level_manager.dispose();
        else
            currentState.dispose();
    }

}
