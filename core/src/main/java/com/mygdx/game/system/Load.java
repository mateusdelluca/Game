package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.manager.State;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Load{

    public Load(int numSave){
        FileHandle fileHandle = Gdx.files.internal("saves/Save" + numSave + ".json");
        State currentState;
        try {
            ObjectInputStream ois = new ObjectInputStream(fileHandle.read());
            currentState = (State) ois.readObject();
            StateManager.States.LEVEL.setState(currentState);
            StateManager.setState(StateManager.States.LEVEL);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
