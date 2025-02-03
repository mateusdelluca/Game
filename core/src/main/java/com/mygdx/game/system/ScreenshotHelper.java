package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import java.nio.ByteBuffer;

public class ScreenshotHelper {
        public static void takeScreenshot(int index) {
            // Obtém a largura e a altura da tela
            int width = Gdx.graphics.getWidth();
            int height = Gdx.graphics.getHeight();

            // Cria um Pixmap a partir do conteúdo da tela
            Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(0, 0, width, height);

            // Inverte a imagem verticalmente
            ByteBuffer pixels = pixmap.getPixels();
            int numBytes = width * height * 4;
            byte[] lines = new byte[numBytes];
            pixels.get(lines);
            pixels.clear();
            for (int i = 0; i < height; i++) {
                pixels.position((height - i - 1) * width * 4);
                pixels.put(lines, i * width * 4, width * 4);
            }

            // Salva o Pixmap em um arquivo
            PixmapIO.writePNG(Gdx.files.external("Saves.png"), pixmap);

            // Libera os recursos do Pixmap
            pixmap.dispose();
        }

}

