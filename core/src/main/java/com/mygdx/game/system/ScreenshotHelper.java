package com.mygdx.game.system;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import static com.mygdx.game.images.Images.sprites;
import static com.mygdx.game.screens.SavePage.PRINTSCREEN;

public class ScreenshotHelper {
    public static Pixmap printscreen;
    public static Sprite sprite;
    public static void takeScreenshot() {
        Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//            PixmapIO.writePNG(Gdx.files.external("mypixmap.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
        printscreen = pixmap;
        sprite = new Sprite(new Texture(ScreenshotHelper.printscreen));
        sprite.flip(false,true);
        sprites[PRINTSCREEN] = new Sprite(new Texture(printscreen));
        sprites[PRINTSCREEN].flip(false, true);
        sprites[PRINTSCREEN].setSize(248, 166);
    }

    public static Pixmap getPixmapFromTexture(Texture texture) {
        // Configura um FrameBuffer com as mesmas dimensões da textura
        FrameBuffer frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, texture.getWidth(), texture.getHeight(), false);
        frameBuffer.begin();

        // Renderiza a textura no FrameBuffer
        texture.bind();
//        texture.getTextureData().prepare();
        texture.getTextureData().consumePixmap();

        // Pega o Pixmap do FrameBuffer
        Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, texture.getWidth(), texture.getHeight());

        // Limpa o FrameBuffer
        frameBuffer.end();
        frameBuffer.dispose();

        return pixmap;
    }



}

