package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.io.File;

import static com.mygdx.game.images.Images.pixMapBox;
import static com.mygdx.game.images.Images.sprites;

public class Delete {

    public Delete(int index){
        File file0 = new File("saves/Save" + index + ".dat");
        file0.delete();
        FileHandle file = Gdx.files.local("saves/Save" + index + ".png"); // Define o caminho de saída
        // Tenta salvar o pixmap
        sprites[index] = new Sprite(new Texture(pixMapBox));
//            sprites[index].flip(false,true);
//            sprites[index].setSize(248,166);
        PixmapIO.writePNG(file, pixMapBox);
    }
}
