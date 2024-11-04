package com.mygdx.game.images;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.screens.levels.Level;

public class PowerBar {

    public static final float WIDTH = Images.hp.getWidth()/2f, HEIGHT = Images.hp.getHeight()/2f;
    public static final float WIDTH2 = Images.hp2.getWidth()/3f, HEIGHT2 = Images.hp2.getHeight()/2f;
    private final SpriteBatch s = new SpriteBatch();
    private OrthographicCamera camera = new OrthographicCamera(Level.WIDTH, Level.HEIGHT);

    public static float hp = WIDTH2, sp = WIDTH2;

    public PowerBar(){
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }

    public void render(){
        camera.update();
        s.setProjectionMatrix(camera.combined);
        s.begin();
        s.draw(Images.hp, 70, 1000, WIDTH, HEIGHT);
        s.draw(Images.hp2, 70 + 40, 1000, hp, HEIGHT);
        s.draw(Images.sp, 70, 950, WIDTH, HEIGHT);
        s.draw(Images.sp2, 70 + 40, 950, sp, HEIGHT);
        s.end();

    }

    public void dispose(){
        s.dispose();
        Images.hp.dispose();
        Images.hp2.dispose();
        Images.sp.dispose();
        Images.sp2.dispose();
    }

}
