package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class ScreenshotHelper {

    public static void takeScreenshot(int index) {
        // Captura o conteúdo da tela
        Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Gira a imagem verticalmente
        Pixmap flippedPixmap = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), pixmap.getFormat());
        for (int i = 0; i < pixmap.getHeight(); i++) {
            for (int j = 0; j < pixmap.getWidth(); j++) {
                flippedPixmap.drawPixel(j, pixmap.getHeight() - i - 1, pixmap.getPixel(j, i));
            }
        }
        pixmap.dispose();

        PixmapIO.writePNG(Gdx.files.external("saves/Save" + index + ".png"), flippedPixmap);
        flippedPixmap.dispose();
    }
}

