package com.mygdx.game.system;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.mygdx.game.screens.levels.Level_Manager;

import javax.imageio.ImageIO;
import java.io.*;

import static com.mygdx.game.images.Images.pixMapBox;
import static com.mygdx.game.images.Images.sprites;
import static com.mygdx.game.system.ScreenshotHelper.*;

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
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("saves/Save" + index + ".dat"));
            oos.writeObject(Level_Manager.currentLevel);
            oos.close();

            FileHandle file = Gdx.files.local("saves/Save" + index + ".png"); // Define o caminho de saída
            // Tenta salvar o pixmap
            sprites[index] = new Sprite(new Texture(printscreen));
            sprites[index].flip(false,true);
            sprites[index].setSize(248,166);
            PixmapIO.writePNG(file, printscreen);
        } catch (IOException e) {
            throw new GdxRuntimeException(e);
        }

    }

}
