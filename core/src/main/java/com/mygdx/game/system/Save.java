package com.mygdx.game.system;

import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class Save {

    public Save(int numSave){
        FileHandle fileHandle = new FileHandle("saves/Save" + numSave + ".json");
        try (ObjectOutputStream oos = new ObjectOutputStream(fileHandle.write(false))) {
            oos.writeObject(StateManager.States.LEVEL.getState());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
