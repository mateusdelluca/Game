package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.mygdx.game.screens.levels.Level;

import java.io.IOException;
import java.io.ObjectInputStream;

public class Load{

    public static Level load(int numSave){
        FileHandle fileHandle = Gdx.files.internal("saves/Save" + numSave + ".json");
        Level currentLevel;
        try {
            ObjectInputStream ois = new ObjectInputStream(fileHandle.read());
            currentLevel = (Level) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return currentLevel;
    }

}
