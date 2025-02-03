package com.mygdx.game.system;

import com.badlogic.gdx.files.FileHandle;
import com.google.gson.Gson;
import com.mygdx.game.manager.StateManager;
import com.mygdx.game.screens.levels.Level;
import com.mygdx.game.screens.levels.Level_Manager;

import java.io.*;

import static com.mygdx.game.system.ScreenshotHelper.takeScreenshot;

public class Save {

//    public Save(int numSave){
//        Gson gson = new Gson();
//        String json = gson.toJson(StateManager.States.LEVEL.getState());
//        // Salvar o JSON em um arquivo
//        try (FileWriter writer = new FileWriter("saves/Save" + numSave + ".json")) {
//            writer.write(json);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try (FileReader reader = new FileReader("saves/Save" + numSave + ".json")) {
//            // Ler e converter o JSON para um objeto
//            Level_Manager levelManager = gson.fromJson(reader, Level_Manager.class);
//
//            // Exibir os dados carregados
//            System.out.println(levelManager);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public Save(int index){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Save" + index + ".dat"));
            oos.writeObject(Level_Manager.currentLevel);
            oos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        takeScreenshot(index);
    }


}
