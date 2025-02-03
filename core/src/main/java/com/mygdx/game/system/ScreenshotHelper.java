package com.mygdx.game.system;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

import javax.imageio.ImageIO;
import java.nio.ByteBuffer;
import java.util.zip.Deflater;

import static com.mygdx.game.images.Images.sprites;

public class ScreenshotHelper {
    public static Texture printscreen;
        public static void takeScreenshot(int index) {
            Pixmap pixmap = Pixmap.createFromFrameBuffer(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            PixmapIO.writePNG(Gdx.files.external("mypixmap.png"), pixmap, Deflater.DEFAULT_COMPRESSION, true);
            printscreen = new Texture(pixmap);
            sprites[0] = new Sprite(printscreen);
            sprites[0].flip(false, true);
            sprites[0].setSize(248,166);
            pixmap.dispose();
        }

}

